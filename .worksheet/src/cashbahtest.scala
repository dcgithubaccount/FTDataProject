

object cashbahtest  {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(67); 
  println("Welcome to the Scala worksheet")
  
  
  import com.mongodb.casbah.Imports._
  import scala.math._
  import java.math.BigDecimal
  import scala.util.Try
  import breeze.stats._
  import breeze.linalg._
  import java.util.Date
  import java.text.SimpleDateFormat
  import java.util.Calendar;$skip(312); 
  
  val mongoClient = MongoClient("localhost", 27017);System.out.println("""mongoClient  : com.mongodb.casbah.MongoClient = """ + $show(mongoClient ));$skip(405); 
  val yahootoCountryMap = Map (
    	"USA" -> "US",
    	"France" -> "PA",
    	"Holland" -> "AS",
    	"Italy" -> "MI",
    	"UK" -> "L",
    	"Germany" -> "DE",
    	"Australia" -> "AX",
    	"Swiss" -> "SW",
    	"HongKong" -> "HK",
    	"Sweden" -> "ST",
    	"Canada" -> "TO",
    	"Denmark" -> "CO",
    	"Norway" -> "OL",
    	"Singapore" -> "SI",
    	"NewZealand" -> "NZ",
    	"India" -> "NS"
)

case class companyData (
   val Symbol :String,
   val Name :String ,
   val Industry :String,
   val Sector :String,
   val firstSplit :String,
   val exchange :String
  );System.out.println("""yahootoCountryMap  : scala.collection.immutable.Map[String,String] = """ + $show(yahootoCountryMap ));$skip(216); 
  val db = mongoClient("CompanyDatabase");System.out.println("""db  : com.mongodb.casbah.MongoDB = """ + $show(db ));$skip(33); 
	val price = db("PriceAndRatio");System.out.println("""price  : com.mongodb.casbah.MongoCollection = """ + $show(price ));$skip(43); 
	val statement = db("FinancialStatements");System.out.println("""statement  : com.mongodb.casbah.MongoCollection = """ + $show(statement ));$skip(31); 
	val Industry = db("Industry");System.out.println("""Industry  : com.mongodb.casbah.MongoCollection = """ + $show(Industry ));$skip(60); 
	
	val xchange = yahootoCountryMap getOrElse("USA","Error");System.out.println("""xchange  : String = """ + $show(xchange ));$skip(63); 
     
     val queryind = MongoDBObject("exchange" -> xchange);System.out.println("""queryind  : com.mongodb.casbah.commons.Imports.DBObject = """ + $show(queryind ));$skip(269); 
     
     val indData = (for (d <- Industry.find(queryind)) yield (companyData(d("_id").toString,
         d("Name").toString,
         d("Industry").toString,
         d("Sector").toString,
         d("FirstSplit").toString,
         d("exchange").toString))).toList;System.out.println("""indData  : List[cashbahtest.companyData] = """ + $show(indData ));$skip(46); 
	
	val sdf = new SimpleDateFormat("yyyyMMdd");System.out.println("""sdf  : java.text.SimpleDateFormat = """ + $show(sdf ));$skip(45); 
	val sf = new SimpleDateFormat("dd-MM-yyyy");System.out.println("""sf  : java.text.SimpleDateFormat = """ + $show(sf ));$skip(85); val res$0 = 
                                                  
	sf.format(sdf.parse("20150220"));System.out.println("""res0: String = """ + $show(res$0));$skip(73); 
	
	def parseDate(date : String) : Try[Date] = {
		Try(sf.parse(date))
	};System.out.println("""parseDate: (date: String)scala.util.Try[java.util.Date]""");$skip(60); 
	
	def Desc[T : Ordering] = implicitly[Ordering[T]].reverse;System.out.println("""Desc: [T](implicit evidence$1: scala.math.Ordering[T])scala.math.Ordering[T]""");$skip(164); 
	def fmt(v: Any): String = v match {
		case d : Double => "%1.3f" format d
		case i : Int => i.toString
		case b : BigDecimal => "%.3f" format b
		case _ => " "
	}
	
	case class Stocks (val Symbol : String,
				   val RunDate : Date,
				   val Country : String,
				   val Exchange : String,
				   val RIC : String,
				   val IssueName : String,
				   val Industry : String,
				   val EPS : Double,
				   val EPSCCY : String,
				   val BookValuePerShare : Double,
				   val AnnualReportCCY : String,
				   val AnnualDividend : Double,
				   val AnnualDiviCCY : String,
				   val DividendExDate : String,
				   val DividendPayDate : String,
                   val Close  : Double,
                   val Open : Double,
                   val DayHigh : Double,
                   val DayLow : Double,
                   val PreviousClose  : Double,
                   val YearLowPrice : Double,
                   val YearLowDate : String,
                   val YearHighPrice : Double,
                   val YearHighDate : String,
                   val ExchangeCCY : String,
                   val AverageVolume : String,
                   val SharesOutstanding : String,
                   val FreeFloat : String,
                   val MarketCap : String,
                   val MarketCapCCY : String,
                   val DebtToEquity    : Double,
                   val ReturnOnEquity : Double,
                   val OperatingProfitMargin : Double,
                   val FinancialCostRatio : Double,
                   val TaxEffectRatio : Double,
                   val PriceToEarning : Double,
                   val AnnualDivYield : Double,
                   val PriceToBook : Double,
                   val FinancialStmtYear : String,
                   val DailyChange : Double
                   );System.out.println("""fmt: (v: Any)String""");$skip(4906); 

def objectRead(o: DBObject): Stocks = {
    Stocks(
      Symbol = o.as[String]("Symbol"),
      RunDate = sf.parse(o("RunDate").toString),
      Country = o("Country").toString,
      Exchange = o("Exchange").toString,
      RIC = o("RIC").toString,
      IssueName = o("IssueName").toString,
      Industry = o("Industry").toString,
      EPS = fmt(o("EarningPerShare")).toDouble,
      EPSCCY = ((o.getAs[DBObject]("Currency").get)("EPSCCY")).toString,
      BookValuePerShare = fmt(o("BookValuePerShare")).toDouble,
      AnnualReportCCY = ((o.getAs[DBObject]("Currency").get)("AnnualReportCCY")).toString,
      AnnualDividend = fmt(o("AnnualDividend")).toDouble,
      AnnualDiviCCY = ((o.getAs[DBObject]("Currency").get)("AnnualDiviCCY")).toString,
      DividendExDate = o("DividendExDate").toString,
      DividendPayDate = o("DividendPayDate").toString,
      Close = fmt((o.getAs[DBObject]("PricesandVolume").get)("Close")).toDouble,
      Open = fmt((o.getAs[DBObject]("PricesandVolume").get)("Open")).toDouble,
      DayHigh = fmt((o.getAs[DBObject]("PricesandVolume").get)("DayHigh")).toDouble,
      DayLow = fmt((o.getAs[DBObject]("PricesandVolume").get)("DayLow")).toDouble,
      PreviousClose = fmt((o.getAs[DBObject]("PricesandVolume").get)("PreviousClose")).toDouble,
      YearLowPrice = fmt((o.getAs[DBObject]("PricesandVolume").get)("YearLowPrice")).toDouble,
      YearLowDate = ((o.getAs[DBObject]("PricesandVolume").get)("YearLowPrice")).toString,
      YearHighPrice = fmt((o.getAs[DBObject]("PricesandVolume").get)("YearHighPrice")).toDouble,
      YearHighDate = ((o.getAs[DBObject]("PricesandVolume").get)("YearLowPrice")).toString,
      ExchangeCCY = ((o.getAs[DBObject]("Currency").get)("ExchangeCCY")).toString,
      AverageVolume = fmt((o.getAs[DBObject]("PricesandVolume").get)("AverageVolume")),
      SharesOutstanding = fmt((o.getAs[DBObject]("PricesandVolume").get)("SharesOutstanding")),
      FreeFloat = fmt((o.getAs[DBObject]("PricesandVolume").get)("FreeFloat")),
      MarketCap = fmt((o.getAs[DBObject]("PricesandVolume").get)("MarketCap")),
      MarketCapCCY = ((o.getAs[DBObject]("Currency").get)("MarketCapCCY")).toString,
      DebtToEquity = fmt((o.getAs[DBObject]("Ratios").get)("DebtToEquity")).toDouble,
      ReturnOnEquity = fmt((o.getAs[DBObject]("Ratios").get)("ReturnOnEquity")).toDouble,
      OperatingProfitMargin = fmt((o.getAs[DBObject]("Ratios").get)("OperatingProfitMargin")).toDouble,
      FinancialCostRatio = fmt((o.getAs[DBObject]("Ratios").get)("FinancialCostRatio")).toDouble,
      TaxEffectRatio = fmt((o.getAs[DBObject]("Ratios").get)("TaxEffectRatio")).toDouble,
      PriceToEarning = fmt((o.getAs[DBObject]("Ratios").get)("PriceToEarning")).toDouble,
      AnnualDivYield = fmt((o.getAs[DBObject]("Ratios").get)("AnnualDivYield")).toDouble,
      PriceToBook = fmt((o.getAs[DBObject]("Ratios").get)("PriceToBook")).toDouble,
      FinancialStmtYear = o("FinanceStatements").toString.takeRight(4),
      DailyChange = fmt((((fmt((o.getAs[DBObject]("PricesandVolume").get)("Close")).toDouble - fmt((o.getAs[DBObject]("PricesandVolume").get)("PreviousClose")).toDouble)/fmt((o.getAs[DBObject]("PricesandVolume").get)("Close")).toDouble))*100).toDouble
    )
  };System.out.println("""objectRead: (o: com.mongodb.casbah.Imports.DBObject)cashbahtest.Stocks""");$skip(483); 
	  
  def logReturn(q :String, n : Int) :List[Double] = {
			val sq = 	MongoDBObject("Symbol" -> q)
			val qq = 	((for (d <- price.find(sq)) yield(
										  sf.parse(d("RunDate").toString),
										  fmt((d.getAs[DBObject]("PricesandVolume").get)("Close")).toDouble)).toList sortBy{x => x._1} reverse) filter { x => x._2 >0} map {x => x._2} take(n)
			
			val lg = ((qq.init) zip (qq drop(1)) ) map {x => log(x._1/x._2).toDouble} //map {x => (rint(x*100)/100).toDouble}
			lg
};System.out.println("""logReturn: (q: String, n: Int)List[Double]""");$skip(169); 


def excessReturn1 (q :String, n : Int) :List[Double] = {
		val mu = mean(logReturn(q,n))
		logReturn(q,n) map {x => x - mu} //map {x => (rint(x*100)/100).toDouble}

};System.out.println("""excessReturn1: (q: String, n: Int)List[Double]""");$skip(142); 


def excessReturn (lg :List[Double]) :List[Double] = {
		val mu = mean(lg)
		lg map {x => x - mu} //map {x => (rint(x*100)/100).toDouble}

};System.out.println("""excessReturn: (lg: List[Double])List[Double]""");$skip(66); 

def stDev(v :List[Double]) : Double = {
		sqrt(variance(v))
		
}
  
  case class Stats (
  						val Symbol :String,
  						val logReturn :List[Double],
  						val mu :Double,
  						val sigma :Double,
  						val length :Int,
  						val excessReturn :List[Double]
  						
  );System.out.println("""stDev: (v: List[Double])Double""");$skip(455); 
  
  def assetStats(s :String,n :Int):Stats = {
  	val lr = logReturn(s,n)
  	Stats(
  			Symbol = s,
  			logReturn = lr,
  			mu = mean(lr),
  			sigma = stDev(lr),
  			length = lr.length,
  			excessReturn = excessReturn(lr)
  	)
  };System.out.println("""assetStats: (s: String, n: Int)cashbahtest.Stats""");$skip(29); 
  
	val country = "Portugal";System.out.println("""country  : String = """ + $show(country ));$skip(158); 
	val query = MongoDBObject("RunDate" -> "07-09-2015") ++ ("Country" -> country);System.out.println("""query  : com.mongodb.casbah.commons.Imports.DBObject = """ + $show(query ));$skip(33);  //++ ("Ratios.AnnualDivYield" $gte 0.025) ++ ("Ratios.PriceToBook" $lte 2.00)
  	
	val ct = price.count(query);System.out.println("""ct  : Int = """ + $show(ct ));$skip(52); 
	val stquery = MongoDBObject("_id" -> "CM:TOR2014");System.out.println("""stquery  : com.mongodb.casbah.commons.Imports.DBObject = """ + $show(stquery ));$skip(47); 
	for (s <- statement.find(stquery)) println(s);$skip(50); 
	


val m = for (d <- price.find(query)) yield(d);System.out.println("""m  : Iterator[com.mongodb.casbah.Imports.DBObject] = """ + $show(m ));$skip(83); 


val a = (m map {x => objectRead(x)}).toList;System.out.println("""a  : List[cashbahtest.Stocks] = """ + $show(a ));$skip(33); val res$1 = //.sortBy(_.MarketCap.toDouble)(Desc)
Calendar.getInstance().getTime();System.out.println("""res1: java.util.Date = """ + $show(res$1));$skip(99); 
lazy val sa = a map {x => x.Symbol} map { x=> assetStats(x,100) };System.out.println("""sa: => List[cashbahtest.Stats]""");$skip(44);  //filterNot {x => x.length < 99}
val sab = sa filterNot {x => x.length < 99};System.out.println("""sab  : List[cashbahtest.Stats] = """ + $show(sab ));$skip(88); 

//val s = (m map {x => objectRead(x)}).toList

val t1 = DenseMatrix((1,3),(2,4),(0,5));System.out.println("""t1  : breeze.linalg.DenseMatrix[Int] = """ + $show(t1 ));$skip(34); 
val t2 = DenseMatrix((1,0),(2,3));System.out.println("""t2  : breeze.linalg.DenseMatrix[Int] = """ + $show(t2 ));$skip(11); val res$2 = 
 
 t1 * t2;System.out.println("""res2: breeze.linalg.DenseMatrix[Int] = """ + $show(res$2));$skip(78); 

val dm = DenseMatrix((for(i <- sab) yield i.excessReturn).map(_.toArray):_*);System.out.println("""dm  : breeze.linalg.DenseMatrix[Double] = """ + $show(dm ));$skip(54); 

val sdm = DenseMatrix(for (i <- sab ) yield i.sigma);System.out.println("""sdm  : breeze.linalg.DenseMatrix[Double] = """ + $show(sdm ));$skip(17); 
val sdmt = sdm.t;System.out.println("""sdmt  : breeze.linalg.DenseMatrix[Double] = """ + $show(sdmt ));$skip(75); 

val eigen = breeze.numerics.rint(( sdmt * sdm) :* 1000000.0) :/ 1000000.0;System.out.println("""eigen  : breeze.linalg.DenseMatrix[Double] = """ + $show(eigen ));$skip(16); 

val dmt = dm.t;System.out.println("""dmt  : breeze.linalg.DenseMatrix[Double] = """ + $show(dmt ));$skip(79); 


val mm = breeze.numerics.rint(((dm * dmt):/ 99.0) :* 1000000.0) :/ 1000000.0;System.out.println("""mm  : breeze.linalg.DenseMatrix[Double] = """ + $show(mm ));$skip(65); 

val sym = DenseVector((for (i <- sab ) yield i.Symbol).toArray);System.out.println("""sym  : breeze.linalg.DenseVector[String] = """ + $show(sym ));$skip(7); val res$3 = 

sym.t;System.out.println("""res3: breeze.linalg.Transpose[breeze.linalg.DenseVector[String]] = """ + $show(res$3));$skip(68); 
val cor = breeze.numerics.rint((mm :/ eigen) :* 10000.0) :/ 10000.0
import java.io.File;System.out.println("""cor  : breeze.linalg.DenseMatrix[Double] = """ + $show(cor ));$skip(152); 
breeze.linalg.csvwrite(new File("/Users/deepakchoudhary/Desktop/eclipse/Workspace/FTDataProject/matrix.csv"), cor, separator = ',');$skip(34); val res$4 = 

Calendar.getInstance().getTime();System.out.println("""res4: java.util.Date = """ + $show(res$4));$skip(456); val res$5 = 






//val dm = (DenseMatrix(excessReturn("AAL:LSE",100),excessReturn("BLT:LSE",100),excessReturn("VOD:LSE",100))) //* (DenseMatrix(excessReturn("ULVR:LSE",100),excessReturn("BLT:LSE",100),excessReturn("VOD:LSE",100)).t)
((a map { x => (x.Symbol,x.IssueName,x.RIC,x.PriceToBook,x.Close,x.YearLowPrice,x.YearHighPrice,(((x.Close - x.YearLowPrice)/(x.YearHighPrice - x.YearLowPrice))*100))}).toList.
sortBy(r => r._8).filter {r => (r._8 >=0 && r._8 < 10)});System.out.println("""res5: List[(String, String, String, Double, Double, Double, Double, Double)] = """ + $show(res$5))}
/*
val stockquery =  MongoDBObject("Symbol" -> "TSCO:LSE")

val qq = (for (d <- price.find(stockquery)) yield(
										  sf.parse(d("RunDate").toString),
										  fmt((d.getAs[DBObject]("PricesandVolume").get)("Close")).toDouble)).toList.take(100).sortBy{x => x._1} filter { x => x._2 >0}
										  
//val qq = List(1,2,4,5,7,8,9,22,13,23) map {x => x.toDouble}
qq.length

val priceqq = qq map {x => x._2} reverse

val logreturn = ((priceqq.init) zip (priceqq drop(1)) ) map {x => (fmt(log(x._1/x._2))).toDouble}


logReturn("BLT:LSE",100)
excessReturn("ULVR:LSE",100)
val mu = mean(logReturn("ULVR:LSE",5))

logReturn("ULVR:LSE",5) map {x => x - mu}

excessReturn("ULVR:LSE",5)
excessReturn("BLT:LSE",5)
excessReturn("VOD:LSE",5)


//case class

 

val dm = (DenseMatrix(excessReturn("ULVR:LSE",100),excessReturn("BLT:LSE",100),excessReturn("VOD:LSE",100))) * (DenseMatrix(excessReturn("ULVR:LSE",100),excessReturn("BLT:LSE",100),excessReturn("VOD:LSE",100)).t)
																										

val dm1 = breeze.numerics.rint((dm :/ 99.0) :* 100000.0) :/ 100000.0

//val dm2 = breeze.numerics.rint(dm1 :* 100000.toDouble) :/ 100000.toDouble



val sdm = (DenseMatrix(stDev(logReturn("ULVR:LSE",100)),stDev(logReturn("BLT:LSE",100)), stDev(logReturn("VOD:LSE",100)))) * (DenseMatrix(stDev(logReturn("ULVR:LSE",100)),stDev(logReturn("BLT:LSE",100)), stDev(logReturn("VOD:LSE",100))).t)
val sdm1 = breeze.numerics.rint(sdm :* 100000.toDouble) :/ 100000.toDouble
//println(dm)
//val cor = dm1 :/ sdm
val cor = breeze.numerics.rint((dm1 :/ sdm1) :* 10000.0) :/ 10000.0
println(cor)


mean(logreturn)
sqrt(variance(logreturn))

MeanAndVariance(mean(logreturn),variance(logreturn),logreturn.length)


//LR("TSCO:LSE",100)



MeanAndVariance(mean(logReturn("BLT:LSE",100)),sqrt(variance(logReturn("BLT:LSE",100))),logReturn("BLT:LSE",100).length)
MeanAndVariance(mean(logReturn("TSCO:LSE",100)),sqrt(variance(logReturn("TSCO:LSE",100))),logReturn("TSCO:LSE",100).length)
MeanAndVariance(mean(logReturn("VOD:LSE",100)),sqrt(variance(logReturn("VOD:LSE",100))),logReturn("VOD:LSE",100).length)




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
 
 val mva15 = (movingAverage(qq map {x => x._2},15) map {x => fmt(x).toDouble}).reverse.take(1)
 val mva30 = (movingAverage(qq map {x => x._2},30) map {x => fmt(x).toDouble}).reverse.take(1)
 val mva45 = (movingAverage(qq map {x => x._2},45) map {x => fmt(x).toDouble}).reverse.take(1)
                                                  
 
 val mva60 = (movingAverage(qq map {x => x._2},60) map {x => fmt(x).toDouble}).reverse.take(1)
 val mva100 = (movingAverage(qq map {x => x._2},100) map {x => fmt(x).toDouble}).reverse.take(1)
*/

                                                   
 
                                                  
}
