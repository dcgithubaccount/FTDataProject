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
  
  val mongoClient = MongoClient("localhost", 27017)
                                                  //> 00:32:42.336 [main] DEBUG c.m.c.c.c.s.RegisterConversionHelpers$ - Registeri
                                                  //| ng Scala Conversions.
                                                  //| 00:32:42.339 [main] DEBUG c.m.c.c.c.s.RegisterConversionHelpers$ - Deseriali
                                                  //| zers for Scala Conversions registering
                                                  //| 00:32:42.340 [main] DEBUG c.m.c.c.c.s.RegisterConversionHelpers$ - Serialize
                                                  //| rs for Scala Conversions registering
                                                  //| 00:32:42.340 [main] DEBUG c.m.c.c.c.s.RegisterConversionHelpers$ - Setting u
                                                  //| p OptionSerializer
                                                  //| 00:32:42.342 [main] DEBUG c.m.c.c.c.s.RegisterConversionHelpers$ - Setting u
                                                  //| p ScalaProductSerializer
                                                  //| 00:32:42.343 [main] DEBUG c.m.c.c.c.s.RegisterConversionHelpers$ - Setting u
                                                  //| p ScalaCollectionSerializer
                                                  //| 00:32:42.343 [main] DEBUG c.m.c.c.c.s.RegisterConversionHelpers$ - Setting u
                                                  //| p ScalaRegexSerializers
                                                  //| 00:32:42.344 [main] DEBUG c.m.c.c.c.s.RegisterConversionHelpers$ - Hooking u
                                                  //| p scala.util.matching.Regex serializer
                                                  //| 00:32:42.345 [main] DEBUG c.m.c.c.c.s.RegisterConversionHelpers$ - Reached b
                                                  //| as
                                                  //| Output exceeds cutoff limit.
	val db = mongoClient("CompanyDatabase")   //> db  : com.mongodb.casbah.MongoDB = CompanyDatabase
	val price = db("PriceAndRatio")           //> price  : com.mongodb.casbah.MongoCollection = PriceAndRatio
	val statement = db("FinancialStatements") //> statement  : com.mongodb.casbah.MongoCollection = FinancialStatements
	val country = "UK"                        //> country  : String = UK
	val query = MongoDBObject("RunDate" -> "21-05-2015") ++ ("Country" -> country) //++ ("Ratios.AnnualDivYield" $gte 0.025) ++ ("Ratios.PriceToBook" $lte 2.00)
                                                  //> query  : com.mongodb.casbah.commons.Imports.DBObject = { "RunDate" : "21-05-
                                                  //| 2015" , "Country" : "UK"}
  //val query =  MongoDBObject("Symbol" -> "RCOM:NSI")
  /*val fields = MongoDBObject("Symbol" -> 1,
  													 "RunDate" -> 2,
  													 "Exchange" -> 3,
  													 "Currency" -> 4,
  													 "Ratios" -> 5,
  													 "FinanceStatements" -> 6,
  													 "IssueName" -> 7,
  													 "PricesandVolume" -> 8)*/
  	
val ct = price.count(query)                       //> ct  : Int = 98
val stquery = MongoDBObject("_id" -> "CM:TOR2014")//> stquery  : com.mongodb.casbah.commons.Imports.DBObject = { "_id" : "CM:TOR2
                                                  //| 014"}
for (s <- statement.find(stquery)) println(s)     //> { "_id" : "CM:TOR2014" , "AnnualReportYear" : "2014" , "AnnualReportCCY" : 
                                                  //| "CAD" , "BSTotalAssets" : 414903.0 , "BSTotalLiabilities" : 396284.0 , "BST
                                                  //| otalEquities" : 18619.0 , "BSCommonShares" : 397.0 , "BSTreasuryShares" : 0
                                                  //| .0 , "IncomeStatementTotalRevenue" : 0 , "IncomeStatementTotalOperationalEx
                                                  //| penses" : 0 , "IncomeStatementOpIncome" : 0 , "IncomeStatementEBT" : 3914.0
                                                  //|  , "IncomeStatementEAT" : 3215.0 , "IncomeStatementNetIncome" : 3218.0}

import java.util.Date
import java.text.SimpleDateFormat


val sdf = new SimpleDateFormat("yyyyMMdd")        //> sdf  : java.text.SimpleDateFormat = java.text.SimpleDateFormat@ef87e460
val sf = new SimpleDateFormat("dd-MM-yyyy")       //> sf  : java.text.SimpleDateFormat = java.text.SimpleDateFormat@9586200
sf.format(sdf.parse("20150220"))                  //> res0: String = 20-02-2015

def Desc[T : Ordering] = implicitly[Ordering[T]].reverse
                                                  //> Desc: [T](implicit evidence$2: scala.math.Ordering[T])scala.math.Ordering[T
                                                  //| ]
def fmt(v: Any): String = v match {
	case d : Double => "%1.3f" format d
	case i : Int => i.toString
	case b : BigDecimal => "%.3f" format b
	case _ => " "
	}                                         //> fmt: (v: Any)String

val m = for (d <- price.find(query)) yield(d)     //> m  : Iterator[com.mongodb.casbah.Imports.DBObject] = non-empty iterator


def parseDate(date : String) : Try[Date] = {
		Try(sf.parse(date))
	}                                         //> parseDate: (date: String)scala.util.Try[java.util.Date]
	
	parseDate("01-Mar-2015") getOrElse("UnknownDate")
                                                  //> res1: Comparable[_ >: java.util.Date with String <: Comparable[_ >: java.ut
                                                  //| il.Date with String <: java.io.Serializable] with java.io.Serializable] wit
                                                  //| h java.io.Serializable = UnknownDate
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


val a = (m map {x => objectRead(x)}).toList.sortBy(_.MarketCap.toDouble)(Desc)
                                                  //> a  : List[cashbahtest.Stocks] = List(Stocks(RDSA:LSE,Thu May 21 00:00:00 BS
                                                  //| T 2015,UK,LSE,RDSa.L,Royal Dutch Shell PLC,www.shell.com,2.1,EUR,27.318,USD
                                                  //| ,116.06,GBX,14-05-2015,22-06-2015,19.85,19.76,19.95,19.74,19.7,19.168,19.16
                                                  //| 79,24.755,19.1679,GBX,5930000.000,6330000000.000,6330000000.000,17664000000
                                                  //| 0.000,EUR,1.053,0.086,0.047,1.423,0.52,13.27,0.059,1.14,2014,0.756), Stocks
                                                  //| (HSBA:LSE,Thu May 21 00:00:00 BST 2015,UK,LSE,HSBA.L,HSBC Holdings PLC,Bank
                                                  //| s,0.437,GBP,9.91,USD,33.41,GBX,21-05-2015,08-07-2015,6.145,6.164,6.173,6.13
                                                  //| ,6.229,5.507,5.5067,6.746,5.5067,GBX,23940000.000,19510000000.000,194700000
                                                  //| 00.000,121340000000.000,GBP,12.831,0.077,0.0,0.0,0.787,14.23,0.054,0.97,201
                                                  //| 4,-1.367), Stocks(ULVR:LSE,Thu May 21 00:00:00 BST 2015,UK,LSE,ULVR.L,Unile
                                                  //| ver PLC,Personal-Goods,1.28,GBP,4.812,EUR,88.62,GBX,23-04-2015,03-06-2015,2
                                                  //| 8.74,28.8,28.86,28.62,28.95,23.97,23.97,30.87,23.97,GBX,2340000.000,3000000
                                                  //| 000.000,1370000000.000,
                                                  //| Output exceeds cutoff limit.

((a map { x => (x.Symbol,x.IssueName,x.RIC,x.PriceToBook,x.Close,x.YearLowPrice,x.YearHighPrice,(((x.Close - x.YearLowPrice)/(x.YearHighPrice - x.YearLowPrice))*100))}).toList.
sortBy(r => r._8).filter {r => (r._8 >=0 && r._8 < 10)})
                                                  //> res2: List[(String, String, String, Double, Double, Double, Double, Double)
                                                  //| ] = List()

val stockquery =  MongoDBObject("Symbol" -> "TSCO:LSE")
                                                  //> stockquery  : com.mongodb.casbah.commons.Imports.DBObject = { "Symbol" : "T
                                                  //| SCO:LSE"}

val qq = (for (d <- price.find(stockquery)) yield(
										  sf.parse(d("RunDate").toString),
										  fmt((d.getAs[DBObject]("PricesandVolume").get)("Close")).toDouble)).toList.take(100).sortBy{x => x._1} filter { x => x._2 >0}
                                                  //> qq  : List[(java.util.Date, Double)] = List((Thu Aug 28 00:00:00 BST 2014,2
                                                  //| .463), (Tue Sep 02 00:00:00 BST 2014,2.309), (Wed Sep 03 00:00:00 BST 2014,
                                                  //| 2.326), (Thu Sep 04 00:00:00 BST 2014,2.295), (Wed Sep 17 00:00:00 BST 2014
                                                  //| ,2.248), (Tue Sep 23 00:00:00 BST 2014,1.945), (Tue Sep 30 00:00:00 BST 201
                                                  //| 4,1.862), (Wed Oct 01 00:00:00 BST 2014,1.802), (Thu Oct 02 00:00:00 BST 20
                                                  //| 14,1.782), (Fri Oct 03 00:00:00 BST 2014,1.722), (Mon Oct 06 00:00:00 BST 2
                                                  //| 014,1.768), (Tue Oct 07 00:00:00 BST 2014,1.826), (Wed Oct 08 00:00:00 BST 
                                                  //| 2014,1.851), (Thu Oct 09 00:00:00 BST 2014,1.867), (Fri Oct 10 00:00:00 BST
                                                  //|  2014,1.853), (Mon Oct 13 00:00:00 BST 2014,1.806), (Tue Oct 14 00:00:00 BS
                                                  //| T 2014,1.798), (Wed Oct 15 00:00:00 BST 2014,1.749), (Thu Oct 16 00:00:00 B
                                                  //| ST 2014,1.72), (Fri Oct 17 00:00:00 BST 2014,1.746), (Mon Oct 20 00:00:00 B
                                                  //| ST 2014,1.793), (Tue Oct 21 00:00:00 BST 2014,1.859), (Wed Oct 22 00:00:00 
                                                  //| BST 2014,1.83), (Thu Oc
                                                  //| Output exceeds cutoff limit.
										  
//val qq = List(1,2,4,5,7,8,9,22,13,23) map {x => x.toDouble}
qq.length                                         //> res3: Int = 100

val priceqq = qq map {x => x._2} reverse          //> priceqq  : List[Double] = List(2.425, 2.46, 2.433, 2.45, 2.467, 2.454, 2.46
                                                  //| , 2.409, 2.384, 2.404, 2.406, 2.4, 2.427, 2.444, 2.416, 2.435, 2.424, 2.419
                                                  //| , 2.334, 2.287, 2.324, 2.296, 2.321, 2.284, 2.248, 2.27, 2.277, 2.303, 2.3,
                                                  //|  2.353, 2.359, 2.29, 2.249, 2.214, 2.191, 2.193, 2.14, 2.12, 2.046, 2.041, 
                                                  //| 2.093, 1.82, 1.788, 1.816, 1.88, 1.869, 1.885, 1.86, 1.86, 1.86, 1.846, 1.8
                                                  //| 1, 1.854, 1.758, 1.682, 1.675, 1.648, 1.658, 1.713, 1.757, 1.749, 1.927, 1.
                                                  //| 927, 1.95, 1.912, 1.915, 1.95, 1.929, 1.845, 1.815, 1.758, 1.719, 1.738, 1.
                                                  //| 736, 1.698, 1.688, 1.71, 1.83, 1.859, 1.793, 1.746, 1.72, 1.749, 1.798, 1.8
                                                  //| 06, 1.853, 1.867, 1.851, 1.826, 1.768, 1.722, 1.782, 1.802, 1.862, 1.945, 2
                                                  //| .248, 2.295, 2.326, 2.309, 2.463)

val logreturn = ((priceqq.init) zip (priceqq drop(1)) ) map {x => (fmt(log(x._1/x._2))).toDouble}
                                                  //> logreturn  : List[Double] = List(-0.014, 0.011, -0.007, -0.007, 0.005, -0.0
                                                  //| 02, 0.021, 0.01, -0.008, -0.001, 0.002, -0.011, -0.007, 0.012, -0.008, 0.00
                                                  //| 5, 0.002, 0.036, 0.02, -0.016, 0.012, -0.011, 0.016, 0.016, -0.01, -0.003, 
                                                  //| -0.011, 0.001, -0.023, -0.003, 0.03, 0.018, 0.016, 0.01, -0.001, 0.024, 0.0
                                                  //| 09, 0.036, 0.002, -0.025, 0.14, 0.018, -0.016, -0.035, 0.006, -0.009, 0.013
                                                  //| , 0.0, 0.0, 0.008, 0.02, -0.024, 0.053, 0.044, 0.004, 0.016, -0.006, -0.033
                                                  //| , -0.025, 0.005, -0.097, 0.0, -0.012, 0.02, -0.002, -0.018, 0.011, 0.045, 0
                                                  //| .016, 0.032, 0.022, -0.011, 0.001, 0.022, 0.006, -0.013, -0.068, -0.016, 0.
                                                  //| 036, 0.027, 0.015, -0.017, -0.028, -0.004, -0.026, -0.008, 0.009, 0.014, 0.
                                                  //| 032, 0.026, -0.034, -0.011, -0.033, -0.044, -0.145, -0.021, -0.013, 0.007, 
                                                  //| -0.065)
def logReturn(q :String, n : Int) :List[Double] = {
			val sq = 	MongoDBObject("Symbol" -> q)
			val qq = 	(for (d <- price.find(sq)) yield(
										  sf.parse(d("RunDate").toString),
										  fmt((d.getAs[DBObject]("PricesandVolume").get)("Close")).toDouble)).toList.
										  take(n)sortBy{x => x._1} filter { x => x._2 >0} map {x => x._2} reverse
			
			val lg = ((qq.init) zip (qq drop(1)) ) map {x => (log(x._1/x._2)).toDouble} //map {x => (rint(x*100)/100).toDouble}
			lg
}                                                 //> logReturn: (q: String, n: Int)List[Double]


def excessReturn (q :String, n : Int) :List[Double] = {
		val mu = mean(logReturn(q,n))
		logReturn(q,n) map {x => x - mu} //map {x => (rint(x*100)/100).toDouble}

}                                                 //> excessReturn: (q: String, n: Int)List[Double]


def stDev(v :List[Double]) : Double = {
		sqrt(variance(v))
		
}                                                 //> stDev: (v: List[Double])Double

logReturn("BLT:LSE",100)                          //> res4: List[Double] = List(-0.004524894598289689, -0.016946848422534654, 0.0
                                                  //| 022218704585768205, -0.01576325199543456, 0.0018785227815979943, -0.0130761
                                                  //| 51447189568, 6.188119009346944E-4, -0.015051735553691005, -0.00213187228603
                                                  //| 0016, 0.060510542531207766, -0.021739986636405875, 0.005708864220320201, -0
                                                  //| .009811757777694828, 0.0, 0.011721978033814237, 0.04864677100904009, 0.0141
                                                  //| 51179546243182, -0.014485683647037355, -0.03127826995475621, 0.024611578596
                                                  //| 56688, -0.021689972882613207, 0.007177844846956983, -0.020418793988759378, 
                                                  //| 0.048982675480066074, 0.02801686495345487, 0.020655381640225786, -0.0063469
                                                  //| 88866840929, 0.0035211303985787394, -0.009828088936262538, 0.00455103183541
                                                  //| 88995, -0.018770102681990492, 0.01736757790790492, 0.024473645675397164, 0.
                                                  //| 01775087361894338, -0.01451404288425407, 0.0292418495944976, 0.047863294142
                                                  //| 932215, -0.054517662505549, 0.004431321874670332, -0.01833568053309009, -0.
                                                  //| 008322827127300631, 0.0
                                                  //| Output exceeds cutoff limit.
excessReturn("ULVR:LSE",100)                      //> res5: List[Double] = List(-0.01533980171780663, 0.010576458950006988, 0.002
                                                  //| 313168129491551, -0.0022089979898228715, 0.0023186199638976896, -0.00151431
                                                  //| 82108298616, 0.008642094985887075, -0.007831836075358265, 0.005846459513628
                                                  //| 189, 0.014071887614314762, -0.002600846672937457, 0.01173949389597645, -0.0
                                                  //| 11945900958160435, 0.0034766627531836567, -0.0029657228411465206, -0.009366
                                                  //| 830404349118, 0.0020251953281447705, 0.0016765978726152333, -1.032036219076
                                                  //| 86E-4, -0.012525214144336182, -0.017953007122423256, -0.005659349355224029,
                                                  //|  -0.006322588753958494, -0.006292440135398921, -0.008297208588686097, 0.017
                                                  //| 990463071576823, 0.0029875335560982496, -0.0028935688242752427, 0.012410060
                                                  //| 36243763, 0.02365692079596115, 0.0016999041676593035, 0.022859485775921357,
                                                  //|  -0.006696932928842704, -0.007390890266371417, 0.004658740525157077, 0.0187
                                                  //| 73626266636076, -0.009735401611274987, 0.008475295145893872, 0.018791746508
                                                  //| 940322, -0.016684077052
                                                  //| Output exceeds cutoff limit.
val mu = mean(logReturn("ULVR:LSE",5))            //> mu  : Double = -0.002753018137296719

logReturn("ULVR:LSE",5) map {x => x - mu}         //> res6: List[Double] = List(-0.03802315631834407, 0.013069985108228988, 0.010
                                                  //| 18799662481471, 0.014765174585300371)

excessReturn("ULVR:LSE",5)                        //> res7: List[Double] = List(-0.03802315631834407, 0.013069985108228988, 0.010
                                                  //| 18799662481471, 0.014765174585300371)
excessReturn("BLT:LSE",5)                         //> res8: List[Double] = List(-0.03731546909905858, 0.017355968967915445, -0.00
                                                  //| 5239646297798077, 0.025199146428941208)
excessReturn("VOD:LSE",5)                         //> res9: List[Double] = List(-0.0334785206219132, 0.006528866522196696, 0.0117
                                                  //| 82775338988735, 0.015166878760727774)

val dm = (DenseMatrix(excessReturn("ULVR:LSE",100),excessReturn("BLT:LSE",100),excessReturn("VOD:LSE",100))) * (DenseMatrix(excessReturn("ULVR:LSE",100),excessReturn("BLT:LSE",100),excessReturn("VOD:LSE",100)).t)
                                                  //> Aug 10, 2015 12:32:49 AM com.github.fommil.jni.JniLoader liberalLoad
                                                  //| INFO: successfully loaded /var/folders/q1/yjdlc6x94jj914k4_8lvyrwr0000gn/T/
                                                  //| jniloader2818954532629144104netlib-native_system-osx-x86_64.jnilib
                                                  //| dm  : breeze.linalg.DenseMatrix[Double] = 0.016114976458444882  0.003821964
                                                  //| 207076334  0.011393834334016941  
                                                  //| 0.003821964207076334  0.06709118031756633   0.004265166164031304  
                                                  //| 0.011393834334016941  0.004265166164031304  0.02565575713656118   
																										

val dm1 = breeze.numerics.rint((dm :/ 99.0) :* 100000.0) :/ 100000.0
                                                  //> dm1  : breeze.linalg.DenseMatrix[Double] = 1.6E-4  4.0E-5  1.2E-4  
                                                  //| 4.0E-5  6.8E-4  4.0E-5  
                                                  //| 1.2E-4  4.0E-5  2.6E-4  

//val dm2 = breeze.numerics.rint(dm1 :* 100000.toDouble) :/ 100000.toDouble



val sdm = (DenseMatrix(stDev(logReturn("ULVR:LSE",100)),stDev(logReturn("BLT:LSE",100)), stDev(logReturn("VOD:LSE",100)))) * (DenseMatrix(stDev(logReturn("ULVR:LSE",100)),stDev(logReturn("BLT:LSE",100)), stDev(logReturn("VOD:LSE",100))).t)
                                                  //> sdm  : breeze.linalg.DenseMatrix[Double] = 1.6443853529025384E-4  3.3552236
                                                  //| 79747398E-4  2.074823603345251E-4   
                                                  //| 3.355223679747398E-4   6.846038807914932E-4  4.23349507033429E-4    
                                                  //| 2.074823603345251E-4   4.23349507033429E-4   2.6179344016899177E-4  
val sdm1 = breeze.numerics.rint(sdm :* 100000.toDouble) :/ 100000.toDouble
                                                  //> sdm1  : breeze.linalg.DenseMatrix[Double] = 1.6E-4  3.4E-4  2.1E-4  
                                                  //| 3.4E-4  6.8E-4  4.2E-4  
                                                  //| 2.1E-4  4.2E-4  2.6E-4  
//println(dm)
//val cor = dm1 :/ sdm
val cor = breeze.numerics.rint((dm1 :/ sdm1) :* 10000.0) :/ 10000.0
                                                  //> cor  : breeze.linalg.DenseMatrix[Double] = 1.0     0.1176  0.5714  
                                                  //| 0.1176  1.0     0.0952  
                                                  //| 0.5714  0.0952  1.0     


mean(logreturn)                                   //> res10: Double = -2.0202020202020283E-4
sqrt(variance(logreturn))                         //> res11: Double = 0.03110055442710077

MeanAndVariance(mean(logreturn),variance(logreturn),logreturn.length)
                                                  //> res12: breeze.stats.MeanAndVariance = MeanAndVariance(-2.0202020202020283E-
                                                  //| 4,9.672444856730572E-4,99)


//LR("TSCO:LSE",100)



MeanAndVariance(mean(logReturn("BLT:LSE",100)),sqrt(variance(logReturn("BLT:LSE",100))),logReturn("BLT:LSE",100).length)
                                                  //> res13: breeze.stats.MeanAndVariance = MeanAndVariance(-0.002051042673901443
                                                  //| 6,0.026164936093778124,99)
MeanAndVariance(mean(logReturn("TSCO:LSE",100)),sqrt(variance(logReturn("TSCO:LSE",100))),logReturn("TSCO:LSE",100).length)
                                                  //> res14: breeze.stats.MeanAndVariance = MeanAndVariance(-1.570565126157023E-4
                                                  //| ,0.0310387138611371,99)
MeanAndVariance(mean(logReturn("VOD:LSE",100)),sqrt(variance(logReturn("VOD:LSE",100))),logReturn("VOD:LSE",100).length)
                                                  //> res15: breeze.stats.MeanAndVariance = MeanAndVariance(5.911474925786685E-4,
                                                  //| 0.016180032143632835,99)




def movingAverage(values: List[Double], period: Int): List[Double] = {
   val first = (values take period).sum / period
   val subtract = values map (_ / period)
   val add = subtract drop period
   val addAndSubtract = add zip subtract map Function.tupled(_ - _)
   val res = (addAndSubtract.foldLeft(first :: List.fill(period - 1)(0.0)) {
     (acc, add) => (add + acc.head) :: acc
   }).reverse
   res
 }                                                //> movingAverage: (values: List[Double], period: Int)List[Double]
 
 val mva15 = (movingAverage(qq map {x => x._2},15) map {x => fmt(x).toDouble}).reverse.take(1)
                                                  //> mva15  : List[Double] = List(2.429)
 val mva30 = (movingAverage(qq map {x => x._2},30) map {x => fmt(x).toDouble}).reverse.take(1)
                                                  //> mva30  : List[Double] = List(2.377)
 val mva45 = (movingAverage(qq map {x => x._2},45) map {x => fmt(x).toDouble}).reverse.take(1)
                                                  //> mva45  : List[Double] = List(2.279)
                                                  
 
 val mva60 = (movingAverage(qq map {x => x._2},60) map {x => fmt(x).toDouble}).reverse.take(1)
                                                  //> mva60  : List[Double] = List(2.155)
 val mva100 = (movingAverage(qq map {x => x._2},100) map {x => fmt(x).toDouble}).reverse.take(1)
                                                  //> mva100  : List[Double] = List(2.045)
 

                                                   
 
                                                  
}