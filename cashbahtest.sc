import breeze.stats.MeanAndVariance
import org.apache.commons.math3.stat.descriptive.rank.Percentile

object cashbahtest {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  
  import com.mongodb.casbah.Imports._
  import scala.math._
  import java.math.BigDecimal
  import scala.util.Try
  import breeze.stats._
  import breeze.linalg._
  import java.util.Date
  import java.text.SimpleDateFormat
  import java.util.Calendar
  
  val mongoClient = MongoClient("localhost", 27017)
                                                  //> 07:58:32.000 [main] DEBUG c.m.c.c.c.s.RegisterConversionHelpers$ - Registeri
                                                  //| ng Scala Conversions.
                                                  //| 07:58:32.003 [main] DEBUG c.m.c.c.c.s.RegisterConversionHelpers$ - Deseriali
                                                  //| zers for Scala Conversions registering
                                                  //| 07:58:32.005 [main] DEBUG c.m.c.c.c.s.RegisterConversionHelpers$ - Serialize
                                                  //| rs for Scala Conversions registering
                                                  //| 07:58:32.005 [main] DEBUG c.m.c.c.c.s.RegisterConversionHelpers$ - Setting u
                                                  //| p OptionSerializer
                                                  //| 07:58:32.006 [main] DEBUG c.m.c.c.c.s.RegisterConversionHelpers$ - Setting u
                                                  //| p ScalaProductSerializer
                                                  //| 07:58:32.007 [main] DEBUG c.m.c.c.c.s.RegisterConversionHelpers$ - Setting u
                                                  //| p ScalaCollectionSerializer
                                                  //| 07:58:32.007 [main] DEBUG c.m.c.c.c.s.RegisterConversionHelpers$ - Setting u
                                                  //| p ScalaRegexSerializers
                                                  //| 07:58:32.008 [main] DEBUG c.m.c.c.c.s.RegisterConversionHelpers$ - Hooking u
                                                  //| p scala.util.matching.Regex serializer
                                                  //| 07:58:32.009 [main] DEBUG c.m.c.c.c.s.RegisterConversionHelpers$ - Reached b
                                                  //| as
                                                  //| Output exceeds cutoff limit.
  val db = mongoClient("CompanyDatabase")         //> db  : com.mongodb.casbah.MongoDB = CompanyDatabase
	val price = db("PriceAndRatio")           //> price  : com.mongodb.casbah.MongoCollection = PriceAndRatio
	val statement = db("FinancialStatements") //> statement  : com.mongodb.casbah.MongoCollection = FinancialStatements
	
	val sdf = new SimpleDateFormat("yyyyMMdd")//> sdf  : java.text.SimpleDateFormat = java.text.SimpleDateFormat@ef87e460
	val sf = new SimpleDateFormat("dd-MM-yyyy")
                                                  //> sf  : java.text.SimpleDateFormat = java.text.SimpleDateFormat@9586200
                                                  
	sf.format(sdf.parse("20150220"))          //> res0: String = 20-02-2015
	
	def parseDate(date : String) : Try[Date] = {
		Try(sf.parse(date))
	}                                         //> parseDate: (date: String)scala.util.Try[java.util.Date]
	
	def Desc[T : Ordering] = implicitly[Ordering[T]].reverse
                                                  //> Desc: [T](implicit evidence$1: scala.math.Ordering[T])scala.math.Ordering[T]
                                                  //| 
	def fmt(v: Any): String = v match {
		case d : Double => "%1.3f" format d
		case i : Int => i.toString
		case b : BigDecimal => "%.3f" format b
		case _ => " "
	}                                         //> fmt: (v: Any)String
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
                   )

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
  }                                               //> objectRead: (o: com.mongodb.casbah.Imports.DBObject)cashbahtest.Stocks
  
  def logReturn(q :String, n : Int) :List[Double] = {
			val sq = 	MongoDBObject("Symbol" -> q)
			val qq = 	(for (d <- price.find(sq)) yield(
										  sf.parse(d("RunDate").toString),
										  fmt((d.getAs[DBObject]("PricesandVolume").get)("Close")).toDouble)).toList.
										  take(n)sortBy{x => x._1} filter { x => x._2 >0} map {x => x._2} reverse
			
			val lg = ((qq.init) zip (qq drop(1)) ) map {x => log(x._1/x._2).toDouble} //map {x => (rint(x*100)/100).toDouble}
			lg
}                                                 //> logReturn: (q: String, n: Int)List[Double]


def excessReturn (q :String, n : Int) :List[Double] = {
		val mu = mean(logReturn(q,n))
		logReturn(q,n) map {x => x - mu} //map {x => (rint(x*100)/100).toDouble}

}                                                 //> excessReturn: (q: String, n: Int)List[Double]


def stDev(v :List[Double]) : Double = {
		sqrt(variance(v))
		
}                                                 //> stDev: (v: List[Double])Double
  
  case class Stats (
  						val Symbol :String,
  						val logReturn :List[Double],
  						val mu :Double,
  						val sigma :Double,
  						val length :Int,
  						val excessReturn :List[Double]
  						
  )
  
  def assetStats(s :String,n :Int):Stats = {
  	val lr = logReturn(s,n)
  	Stats(
  			Symbol = s,
  			logReturn = lr,
  			mu = mean(lr),
  			sigma = stDev(lr),
  			length = lr.length,
  			excessReturn = excessReturn(s,n)
  	)
  }                                               //> assetStats: (s: String, n: Int)cashbahtest.Stats
  
	val country = "USA"                       //> country  : String = USA
	val query = MongoDBObject("RunDate" -> "11-08-2015") ++ ("Country" -> country) //++ ("Ratios.AnnualDivYield" $gte 0.025) ++ ("Ratios.PriceToBook" $lte 2.00)
                                                  //> query  : com.mongodb.casbah.commons.Imports.DBObject = { "RunDate" : "11-08
                                                  //| -2015" , "Country" : "USA"}
  	
	val ct = price.count(query)               //> ct  : Int = 582
	val stquery = MongoDBObject("_id" -> "CM:TOR2014")
                                                  //> stquery  : com.mongodb.casbah.commons.Imports.DBObject = { "_id" : "CM:TOR2
                                                  //| 014"}
	for (s <- statement.find(stquery)) println(s)
                                                  //> { "_id" : "CM:TOR2014" , "AnnualReportYear" : "2014" , "AnnualReportCCY" : 
                                                  //| "CAD" , "BSTotalAssets" : 414903.0 , "BSTotalLiabilities" : 396284.0 , "BST
                                                  //| otalEquities" : 18619.0 , "BSCommonShares" : 397.0 , "BSTreasuryShares" : 0
                                                  //| .0 , "IncomeStatementTotalRevenue" : 0 , "IncomeStatementTotalOperationalEx
                                                  //| penses" : 0 , "IncomeStatementOpIncome" : 0 , "IncomeStatementEBT" : 3914.0
                                                  //|  , "IncomeStatementEAT" : 3215.0 , "IncomeStatementNetIncome" : 3218.0}
	


val m = for (d <- price.find(query)) yield(d)     //> m  : Iterator[com.mongodb.casbah.Imports.DBObject] = non-empty iterator


val a = (m map {x => objectRead(x)}).toList//.sortBy(_.MarketCap.toDouble)(Desc)
                                                  //> a  : List[cashbahtest.Stocks] = List(Stocks(A:NYQ,Tue Aug 11 00:00:00 BST 2
                                                  //| 015,USA,NYQ,A,Agilent Technologies Inc,Electronic-and-Electrical-Equipment,
                                                  //| 1.07,USD,15.815,USD,0.4,USD,26-06-2015,22-07-2015,39.42,40.19,40.19,39.3,40
                                                  //| .47,35.61,35.61,43.59,35.61,USD,1800000.000,333190000.000,332080000.000,133
                                                  //| 20000000.000,USD,1.044,0.095,0.119,0.777,0.78,37.52,0.01,2.49,2014,-2.664),
                                                  //|  Stocks(AA:NYQ,Tue Aug 11 00:00:00 BST 2015,USA,NYQ,AA,Alcoa Inc,Industrial
                                                  //| -Metals,0.496,USD,10.112,USD,0.12,USD,05-08-2015,25-08-2015,9.48,9.7,9.74,9
                                                  //| .42,10.08,9.36,9.36,17.75,9.36,USD,24970000.000,1310000000.000,1310000000.0
                                                  //| 00,13200000000.000,USD,2.039,0.014,0.043,0.489,0.356,20.34,0.013,0.94,2014,
                                                  //| -6.329), Stocks(AAPL:NSQ,Tue Aug 11 00:00:00 BST 2015,USA,NSQ,AAPL.O,Apple 
                                                  //| Inc,Technology-Hardware-and-Equipment,8.66,USD,19.016,USD,2.08,USD,06-08-20
                                                  //| 15,13-08-2015,113.49,117.81,118.18,113.33,119.72,94.84,94.84,134.54,94.84,U
                                                  //| SD,61500000.000,5700000
                                                  //| Output exceeds cutoff limit.
Calendar.getInstance().getTime()                  //> res1: java.util.Date = Wed Aug 12 07:58:33 BST 2015
lazy val s = a map {x => x.Symbol} map { x=> assetStats(x,100) } filterNot {x => x.length < 99}
                                                  //> s: => List[cashbahtest.Stats]
//val s = (m map {x => objectRead(x)}).toList

val dm = DenseMatrix((for(i <- s) yield i.excessReturn).map(_.toArray):_*)
                                                  //> dm  : breeze.linalg.DenseMatrix[Double] = 0.008258348136737575    -0.013263
                                                  //| 48064716722    ... (99 total)
                                                  //| -0.05336637725709499    0.008936211531065833    ...
                                                  //| 0.002043783011133909    -7.106315844434734E-4   ...
                                                  //| -0.0014601278152383025  0.001974826184734959    ...
                                                  //| 0.006115450299916256    -0.020969702398382802   ...
                                                  //| -0.010656208706814253   0.01384716017053561     ...
                                                  //| 0.007734353719948775    -0.01030932366416307    ...
                                                  //| -0.01983004817211291    8.598159124034506E-4    ...
                                                  //| -0.010958077156599896   2.6250320824304225E-4   ...
                                                  //| 0.01684724340292818     -5.597039197614304E-4   ...
                                                  //| 0.0027069711542865725   -0.03731741646875567    ...
                                                  //| 0.0062383183169421      -0.016275098694449448   ...
                                                  //| 0.00210108872033875     -0.010391998428437785   ...
                                                  //| -0.002234893679564732   -0.001766229124951696   ...
                                                  //| 0.005854345544193332    8.035821260736155E-4    ...
                                                  //| -0.006407510304087028   -0.01307225474381637    ...
                                                  //| -0.006574448489861453   
                                                  //| Output exceeds cutoff limit.

val sdm = DenseMatrix(for (i <- s ) yield i.sigma)//> sdm  : breeze.linalg.DenseMatrix[Double] = 0.03401392366335859  0.025051377
                                                  //| 60926507  0.01647524806527001  ... (394 total)
val sdmt = sdm.t                                  //> sdmt  : breeze.linalg.DenseMatrix[Double] = 0.03401392366335859   
                                                  //| 0.02505137760926507   
                                                  //| 0.01647524806527001   
                                                  //| 0.013436118599606804  
                                                  //| 0.01340938702229758   
                                                  //| 0.010716051735120568  
                                                  //| 0.01812330330090166   
                                                  //| 0.019756018228404032  
                                                  //| 0.010447561825666776  
                                                  //| 0.01431177563548417   
                                                  //| 0.013595162896192503  
                                                  //| 0.01599989614090826   
                                                  //| 0.011104151675292183  
                                                  //| 0.0099126389682424    
                                                  //| 0.013109860853377632  
                                                  //| 0.010015795666728552  
                                                  //| 0.01767453377278988   
                                                  //| 0.009564528092851177  
                                                  //| 0.020926903776488728  
                                                  //| 0.020094821812310513  
                                                  //| 0.009872479004186818  
                                                  //| 0.010751876311367502  
                                                  //| 0.01813050656769053   
                                                  //| 0.02309365817943488   
                                                  //| 0.01191098180148188   
                                                  //| 0.014427953808160705  
                                                  //| 0.030661790775605918  
                                                  //| 0.026358211443607695  
                                                  //| 0.015533026056564888  
                                                  //| 0.07879363200728229   
                                                  //| 0.011313171302463312  
                                                  //| 0.01312403162865455   
                                                  //| 0.02471401045228983   
                                                  //| 0.02503523701660605   

val eigen = breeze.numerics.rint(( sdmt * sdm) :* 10000.0) :/ 10000.0
                                                  //> Aug 12, 2015 8:04:38 AM com.github.fommil.jni.JniLoader liberalLoad
                                                  //| INFO: successfully loaded /var/folders/q1/yjdlc6x94jj914k4_8lvyrwr0000gn/T/
                                                  //| jniloader5469758113831042805netlib-native_system-osx-x86_64.jnilib
                                                  //| eigen  : breeze.linalg.DenseMatrix[Double] = 0.0012  9.0E-4  6.0E-4  5.0E-4
                                                  //|   5.0E-4  4.0E-4  6.0E-4  7.0E-4  ... (394 total)
                                                  //| 9.0E-4  6.0E-4  4.0E-4  3.0E-4  3.0E-4  3.0E-4  5.0E-4  5.0E-4  ...
                                                  //| 6.0E-4  4.0E-4  3.0E-4  2.0E-4  2.0E-4  2.0E-4  3.0E-4  3.0E-4  ...
                                                  //| 5.0E-4  3.0E-4  2.0E-4  2.0E-4  2.0E-4  1.0E-4  2.0E-4  3.0E-4  ...
                                                  //| 5.0E-4  3.0E-4  2.0E-4  2.0E-4  2.0E-4  1.0E-4  2.0E-4  3.0E-4  ...
                                                  //| 4.0E-4  3.0E-4  2.0E-4  1.0E-4  1.0E-4  1.0E-4  2.0E-4  2.0E-4  ...
                                                  //| 6.0E-4  5.0E-4  3.0E-4  2.0E-4  2.0E-4  2.0E-4  3.0E-4  4.0E-4  ...
                                                  //| 7.0E-4  5.0E-4  3.0E-4  3.0E-4  3.0E-4  2.0E-4  4.0E-4  4.0E-4  ...
                                                  //| 4.0E-4  3.0E-4  2.0E-4  1.0E-4  1.0E-4  1.0E-4  2.0E-4  2.0E-4  ...
                                                  //| 5.0E-4  4.0E-4  2.0E-4  2.0E-4  2.0E-4  2.0E-4  3
                                                  //| Output exceeds cutoff limit.

val dmt = dm.t                                    //> dmt  : breeze.linalg.DenseMatrix[Double] = 0.008258348136737575    -0.05336
                                                  //| 637725709499    ... (394 total)
                                                  //| -0.01326348064716722    0.008936211531065833    ...
                                                  //| 0.008438928267151364    -0.012491833375397384   ...
                                                  //| -0.0029568897741972253  -0.03763623256639335    ...
                                                  //| -0.007143403916323215   0.01727364899718458     ...
                                                  //| 0.01475626457051664     0.012766199980383204    ...
                                                  //| -3.32856808453217E-4    -0.019399322695543602   ...
                                                  //| 0.006998803821584498    0.009312752048834196    ...
                                                  //| 0.006537554897757446    -0.04614886502065089    ...
                                                  //| 0.01109137980143766     0.016726442667205986    ...
                                                  //| -0.002043634179408966   -0.015885674212707025   ...
                                                  //| 0.022469009920481616    -0.011205717105834368   ...
                                                  //| -0.0062183871945424     0.01142539878288776     ...
                                                  //| 0.028075643197828622    0.008335311918043731    ...
                                                  //| 0.012387743118057256    -0.0017961090833423696  ...
                                                  //| 0.006457615367528275    0.012177911101614583    ...
                                                  //| 0.02060624684221433   
                                                  //| Output exceeds cutoff limit.


val mm = breeze.numerics.rint(((dm * dmt):/ 99.0) :* 10000.0) :/ 10000.0
                                                  //> mm  : breeze.linalg.DenseMatrix[Double] = 0.0011  1.0E-4   1.0E-4   -0.0   
                                                  //|   2.0E-4  0.0      2.0E-4   ... (394 total)
                                                  //| 1.0E-4  6.0E-4   1.0E-4   -0.0     1.0E-4  -0.0     1.0E-4   ...
                                                  //| 1.0E-4  1.0E-4   3.0E-4   -0.0     1.0E-4  0.0      1.0E-4   ...
                                                  //| -0.0    -0.0     -0.0     2.0E-4   -0.0    0.0      -0.0     ...
                                                  //| 2.0E-4  1.0E-4   1.0E-4   -0.0     2.0E-4  0.0      1.0E-4   ...
                                                  //| 0.0     -0.0     0.0      0.0      0.0     1.0E-4   0.0      ...
                                                  //| 2.0E-4  1.0E-4   1.0E-4   -0.0     1.0E-4  0.0      3.0E-4   ...
                                                  //| 1.0E-4  1.0E-4   1.0E-4   1.0E-4   1.0E-4  0.0      1.0E-4   ...
                                                  //| -0.0    -0.0     -0.0     0.0      0.0     0.0      -0.0     ...
                                                  //| 0.0     0.0      0.0      -0.0     0.0     0.0      -0.0     ...
                                                  //| 0.0     1.0E-4   0.0      -0.0     1.0E-4  -0.0     1.0E-4   ...
                                                  //| 2.0E-4  1.0E-4   1.0E-4   -0.0     1.0E-4  0.0      2.0E-4   ...
                                                  //| 1.0E-4  1.0E-4   0.0      -0.0     1.0E-4  0.0      1.0E-4   ...
                                                  //| 0.0     0.0      0.0     
                                                  //| Output exceeds cutoff limit.

val sym = for (i <- s ) yield i.Symbol            //> sym  : List[String] = List(A:NYQ, AA:NYQ, AAPL:NSQ, ABBV:NYQ, ABT:NYQ, ACN:
                                                  //| NYQ, ADI:NSQ, ADP:NSQ, ADS:NYQ, ADT:NYQ, AEP:NYQ, AET:NYQ, AFL:NYQ, AIG:NYQ
                                                  //| , AIV:NYQ, AIZ:NYQ, AKAM:NSQ, ALLE:NYQ, ALXN:NSQ, AMAT:NSQ, AME:NYQ, AMG:NY
                                                  //| Q, AMGN:NSQ, AMZN:NSQ, AN:NYQ, ANTM:NYQ, APA:NYQ, APC:NYQ, APD:NYQ, APH:NYQ
                                                  //| , ARG:NYQ, AVB:NYQ, AVGO:NSQ, AVP:NYQ, AVY:NYQ, AXP:NYQ, BA:NYQ, BAC:NYQ, B
                                                  //| AX:NYQ, BBBY:NSQ, BBT:NYQ, BCR:NYQ, BF.B:NYQ, BHI:NYQ, BK:NYQ, BLK:NYQ, BMY
                                                  //| :NYQ, BRCM:NSQ, BRK.B:NYQ, BSX:NYQ, BWA:NYQ, BXP:NYQ, CA:NSQ, CAM:NYQ, CAT:
                                                  //| NYQ, CB:NYQ, CBG:NYQ, CBS:NYQ, CELG:NSQ, CERN:NSQ, CF:NYQ, CHK:NYQ, CMG:NYQ
                                                  //| , CNP:NYQ, COF:NYQ, COG:NYQ, CPB:NYQ, CTL:NYQ, CVC:NYQ, CVX:NYQ, GAS:NYQ, K
                                                  //| MX:NYQ, MMM:NYQ, MO:NYQ, SCHW:NYQ, T:NYQ, BEN:NYQ, C:NYQ, CAG:NYQ, CCI:NYQ,
                                                  //|  CI:NYQ, CL:NYQ, CLX:NYQ, CMA:NYQ, CMCSA:NSQ, CME:NSQ, CMS:NYQ, COH:NYQ, CO
                                                  //| P:NYQ, COST:NSQ, CSCO:NSQ, CTAS:NSQ, CTSH:NSQ, CTXS:NSQ, CVS:NYQ, D:NYQ, DA
                                                  //| L:NYQ, DD:NYQ, DE:NYQ, 
                                                  //| Output exceeds cutoff limit.
val cor = breeze.numerics.rint((mm :/ eigen) :* 100.0) :/ 100.0
                                                  //> cor  : breeze.linalg.DenseMatrix[Double] = 0.92  0.11   0.17   -0.0   0.4  
                                                  //|  0.0    0.33   0.14  -0.0   ... (394 total)
                                                  //| 0.11  1.0    0.25   -0.0   0.33  -0.0   0.2    0.2   -0.0   ...
                                                  //| 0.17  0.25   1.0    -0.0   0.5   0.0    0.33   0.33  -0.0   ...
                                                  //| -0.0  -0.0   -0.0   1.0    -0.0  0.0    -0.0   0.33  0.0    ...
                                                  //| 0.4   0.33   0.5    -0.0   1.0   0.0    0.5    0.33  0.0    ...
                                                  //| 0.0   -0.0   0.0    0.0    0.0   1.0    0.0    0.0   0.0    ...
                                                  //| 0.33  0.2    0.33   -0.0   0.5   0.0    1.0    0.25  -0.0   ...
                                                  //| 0.14  0.2    0.33   0.33   0.33  0.0    0.25   1.0   -0.0   ...
                                                  //| -0.0  -0.0   -0.0   0.0    0.0   0.0    -0.0   -0.0  1.0    ...
                                                  //| 0.0   0.0    0.0    -0.0   0.0   0.0    -0.0   -0.0  0.0    ...
                                                  //| 0.0   0.33   0.0    -0.0   0.5   -0.0   0.5    0.33  0.0    ...
                                                  //| 0.4   0.25   0.33   -0.0   0.5   0.0    0.67   0.33  0.0    ...
                                                  //| 0.25  0.33   0.0    -0.0   1.0   0.0    0.5    0.5   -0.0   ...
                                                  //| 0.0   0.0    0.0    0.0    0.0   0.0 
                                                  //| Output exceeds cutoff limit.
Calendar.getInstance().getTime()                  //> res2: java.util.Date = Wed Aug 12 08:04:39 BST 2015






//val dm = (DenseMatrix(excessReturn("AAL:LSE",100),excessReturn("BLT:LSE",100),excessReturn("VOD:LSE",100))) //* (DenseMatrix(excessReturn("ULVR:LSE",100),excessReturn("BLT:LSE",100),excessReturn("VOD:LSE",100)).t)
((a map { x => (x.Symbol,x.IssueName,x.RIC,x.PriceToBook,x.Close,x.YearLowPrice,x.YearHighPrice,(((x.Close - x.YearLowPrice)/(x.YearHighPrice - x.YearLowPrice))*100))}).toList.
sortBy(r => r._8).filter {r => (r._8 >=0 && r._8 < 10)})
                                                  //> res3: List[(String, String, String, Double, Double, Double, Double, Double)
                                                  //| ] = List((MIL:NYQ,MFC Industrial Ltd,MIL,0.32,3.36,3.36,8.2,0.0), (HPQ:NYQ,
                                                  //| Hewlett-Packard Co,HPQ,2.02,29.32,29.3,41.1,0.1694915254237252), (PTGCY:PKC
                                                  //| ,Pharol SGPS SA,PTGCY.PK,0.18,0.258,0.254,2.35,0.19083969465648873), (PWE:N
                                                  //| YQ,Penn West Petroleum Ltd,PWE,0.13,1.12,1.1,7.88,0.2949852507374634), (WFM
                                                  //| :NSQ,Whole Foods Market Inc,WFM.O,3.21,33.97,33.84,57.57,0.5478297513695554
                                                  //| ), (TC:NYQ,Thompson Creek Metals Company Inc,TC,0.13,0.52,0.502,3.09,0.6955
                                                  //| 177743431227), (RL:NYQ,Ralph Lauren Corp,RL,2.63,119.0,118.51,187.49,0.7103
                                                  //| 50826326464), (GMCR:NSQ,Keurig Green Mountain Inc,GMCR.O,2.48,52.96,52.13,1
                                                  //| 58.87,0.777590406595464), (FCX:NYQ,Freeport-McMoRan Inc,FCX,0.58,10.22,10.0
                                                  //| ,36.85,0.8193668528864082), (EMR:NYQ,Emerson Electric Co,EMR,3.37,48.93,48.
                                                  //| 77,65.94,0.9318578916715006), (NRG:NYQ,NRG Energy Inc,NRG,0.64,19.23,19.09,
                                                  //| 33.92,0.944032366824009
                                                  //| Output exceeds cutoff limit.
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