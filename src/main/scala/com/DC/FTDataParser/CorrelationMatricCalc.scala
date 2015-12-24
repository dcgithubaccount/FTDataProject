package com.DC.FTDataParser

object CorrelationMatricCalc extends Parameters with DBConnection  {
  
  import com.mongodb.casbah.Imports._
  import scala.math._
  import java.math.BigDecimal
  import scala.util.Try
  import breeze.stats._
  import breeze.linalg._
  import org.slf4j.Logger
  import org.slf4j.LoggerFactory

  val CorMatrixlogger = LoggerFactory.getLogger(this.getClass)
  CorMatrixlogger.info("Entering Correlation Matrix Calculation")  
  
  def logReturn(q :String, n : Int) :List[Double] = {
			val sq = 	MongoDBObject("Symbol" -> q)
			val qq = 	((for (d <- price.find(sq)) yield(
										  sf.parse(d("RunDate").toString),
										  fmt((d.getAs[DBObject]("PricesandVolume").get)("Close")).toDouble)).toList sortBy{x => x._1} reverse) filter { x => x._2 >0} map {x => x._2} take(n)
			
			val lg = ((qq.init) zip (qq drop(1)) ) map {x => log(x._1/x._2).toDouble} 
			lg
  } 
  
  def excessReturn (lg :List[Double]) :List[Double] = {
		val mu = mean(lg)
		lg map {x => x - mu} //map {x => (rint(x*100)/100).toDouble}

  }                                                 //> excessReturn: (lg: List[Double])List[Double]

  def stDev(v :List[Double]) : Double = {
		sqrt(variance(v))
		
  }                                                 //> stDev: (v: List[Double])Double
  
  def skew(er :List[Double], sigma :Double) : Double = {
	  val numerator = ((er map {x => pow(x,3)}).sum)/er.length
	  val denominator = pow(sigma,3)
      numerator/denominator
    
  }
  
  def kurt(er :List[Double]) :Double = {
	  val numerator = ((er map {x => pow(x,4)}).sum)/er.length
	  val denominator = pow(((er map { x => pow(x,2)}).sum)/er.length,2)
	  val excessKurtosis = (numerator/denominator) - 3
      return  excessKurtosis 
  }
  
  def movingAverage(values: List[Double], period: Int): List[Double] = {
   val first = (values take period).sum / period
   val subtract = values map (_ / period)
   val add = subtract drop period
   val addAndSubtract = add zip subtract map Function.tupled(_ - _)
   val res = (addAndSubtract.foldLeft(first :: List.fill(period - 1)(0.0)) {
     (acc, add) => (add + acc.head) :: acc
   }).reverse
   res
 }
  
  case class Stats (
  						val Symbol :String,
  						val logReturn :List[Double],
  						val mu :Double,
  						val sigma :Double,
  						val length :Int,
  						val excessReturn :List[Double],
  						val skewness : Double,
  						val excessKurtosis : Double
  )
  
  def assetStats(s :String,n :Int):Stats = {
  	val lr = logReturn(s,n)
  	val er = excessReturn(lr)
  	val std = stDev(lr)
  	Stats(
  			Symbol = s,
  			logReturn = lr,
  			mu = mean(lr),
  			sigma = std,
  			length = lr.length,
  			excessReturn = er,
  			skewness = skew(er,std),
  			excessKurtosis = kurt(er)
  	)
  } 
  
  def getCorMatrix(c :String,d :String,n :Int) :breeze.linalg.DenseMatrix[String] = {
    
    CorMatrixlogger.info("Inside generating Correlation Matrix")  
    
    val query = MongoDBObject("RunDate" -> d) ++ ("Country" -> c)
    CorMatrixlogger.info("Query Object created for {}", c)  
    
    val dbObject = for (d <- price.find(query)) yield(d)
    val objectReader = (dbObject map {x => objectRead(x)}).toList
    val fetchAssetData = objectReader map {x => x.Symbol} map { x=> assetStats(x,n) } filterNot {x => x.length < n-1}
    //for (i <- fetchAssetData) { println( "Symbol is ", i.Symbol," Skewness is ",i.skewness, " Kurtosis is ", i.excessKurtosis)}
    CorMatrixlogger.info("Asset Data fetched")
    
    val excessReturnMatrix = DenseMatrix((for(i <- fetchAssetData) yield i.excessReturn).map(_.toArray):_*)
    CorMatrixlogger.info("Excess Return matrix generated")  
    
    val transposeExcessreturnMatrix = excessReturnMatrix.t
    val vcvMatrix = breeze.numerics.rint(((excessReturnMatrix * transposeExcessreturnMatrix):/ (n-1).toDouble ) :* 1000000.0) :/ 1000000.0
    CorMatrixlogger.info("VcV Matrix Generated")  
    
    val transposeStDevVector = DenseMatrix(for (i <- fetchAssetData ) yield i.sigma)
    val stDevVector = transposeStDevVector.t
    val stDevMatrix = breeze.numerics.rint(( stDevVector * transposeStDevVector) :* 1000000.0) :/ 1000000.0
    CorMatrixlogger.info("Correlation Matrix Generated")  
    
    val sym = DenseVector((for (i <- fetchAssetData ) yield i.Symbol).toArray)
    val tsym = sym.t
    
    val corMat = lowerTriangular(breeze.numerics.rint((vcvMatrix :/ stDevMatrix) :* 10000.0) :/ 10000.0)
    val scorMat = DenseMatrix.tabulate(corMat.rows,corMat.cols)(corMat(_,_).toString)
    val withHeader:DenseMatrix[String] = DenseMatrix.tabulate(scorMat.rows+1, scorMat.cols+1) { (i, j) =>
        if (i == 0 && j == 0) " "
        else if (i == 0) sym(j -1)
        else if (j == 0 ) tsym (i -1)
        else scorMat(i-1,j-1)

  }   
    return withHeader
  }
 
  def saveCorMatrix(cor :breeze.linalg.DenseMatrix[String]) = {
    
    import java.io.File
    import breeze.io.CSVWriter._
    //println(cor)
    writeFile(new File("/Users/deepakchoudhary/Desktop/eclipse/Workspace/FTDataProject/matrix.csv"),IndexedSeq.tabulate(cor.rows,cor.cols)(cor(_,_)),separator = ',')
    //breeze.linalg.csvwrite(new File("/Users/deepakchoudhary/Desktop/eclipse/Workspace/FTDataProject/matrix.csv"), cor, separator = ',')
    CorMatrixlogger.info("Matrix File Saved")  
  }
  

}