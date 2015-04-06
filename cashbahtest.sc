object cashbahtest {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  
  import com.mongodb.casbah.Imports._
  import scala.math._
  import java.math.BigDecimal
  import scala.util.Try
  val mongoClient = MongoClient("localhost", 27017)
                                                  //> 11:01:49.192 [main] DEBUG c.m.c.c.c.s.RegisterConversionHelpers$ - Registeri
                                                  //| ng Scala Conversions.
                                                  //| 11:01:49.206 [main] DEBUG c.m.c.c.c.s.RegisterConversionHelpers$ - Deseriali
                                                  //| zers for Scala Conversions registering
                                                  //| 11:01:49.207 [main] DEBUG c.m.c.c.c.s.RegisterConversionHelpers$ - Serialize
                                                  //| rs for Scala Conversions registering
                                                  //| 11:01:49.207 [main] DEBUG c.m.c.c.c.s.RegisterConversionHelpers$ - Setting u
                                                  //| p OptionSerializer
                                                  //| 11:01:49.208 [main] DEBUG c.m.c.c.c.s.RegisterConversionHelpers$ - Setting u
                                                  //| p ScalaProductSerializer
                                                  //| 11:01:49.210 [main] DEBUG c.m.c.c.c.s.RegisterConversionHelpers$ - Setting u
                                                  //| p ScalaCollectionSerializer
                                                  //| 11:01:49.211 [main] DEBUG c.m.c.c.c.s.RegisterConversionHelpers$ - Setting u
                                                  //| p ScalaRegexSerializers
                                                  //| 11:01:49.211 [main] DEBUG c.m.c.c.c.s.RegisterConversionHelpers$ - Hooking u
                                                  //| p scala.util.matching.Regex serializer
                                                  //| 11:01:49.220 [main] DEBUG c.m.c.c.c.s.RegisterConversionHelpers$ - Reached b
                                                  //| as
                                                  //| Output exceeds cutoff limit.
	val db = mongoClient("CompanyDatabase")   //> db  : com.mongodb.casbah.MongoDB = CompanyDatabase
	val price = db("PriceAndRatio")           //> price  : com.mongodb.casbah.MongoCollection = PriceAndRatio
	val statement = db("FinancialStatements") //> statement  : com.mongodb.casbah.MongoCollection = FinancialStatements
	val country = "USA"                       //> country  : String = USA
	val query = MongoDBObject("RunDate" -> "03-04-2015") ++ ("Country" -> country) //++ ("Ratios.AnnualDivYield" $gte 0.025) ++ ("Ratios.PriceToBook" $lte 2.00)
                                                  //> query  : com.mongodb.casbah.commons.Imports.DBObject = { "RunDate" : "03-04-
                                                  //| 2015" , "Country" : "USA"}
  //val query =  MongoDBObject("Symbol" -> "ADM:NYQ")
  /*val fields = MongoDBObject("Symbol" -> 1,
  													 "RunDate" -> 2,
  													 "Exchange" -> 3,
  													 "Currency" -> 4,
  													 "Ratios" -> 5,
  													 "FinanceStatements" -> 6,
  													 "IssueName" -> 7,
  													 "PricesandVolume" -> 8)*/
  	
val ct = price.count(query)                       //> ct  : Int = 590
val stquery = MongoDBObject("_id" -> "CM:TOR2014")//> stquery  : com.mongodb.casbah.commons.Imports.DBObject = { "_id" : "CM:TOR20
                                                  //| 14"}
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
                   val AverageVolume : Double,
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
      AverageVolume = fmt((o.getAs[DBObject]("PricesandVolume").get)("AverageVolume")).toDouble,
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


val a = (m map {x => objectRead(x)}).toList.sortBy(_.SharesOutstanding)(Desc)
                                                  //> a  : List[cashbahtest.Stocks] = List(Stocks(CSX:NYQ,Fri Apr 03 00:00:00 BST
                                                  //|  2015,USA,NYQ,CSX,CSX Corp,Industrial-Transportation,1.93,USD,11.242,USD,0.
                                                  //| 64,USD,25-02-2015,13-03-2015,33.32,33.28,33.4,32.94,33.45,27.14,27.14,37.99
                                                  //| ,27.14,USD,9110000.0,991590000.000,983370000.000,33040000000.000,USD,1.964,
                                                  //| 0.173,0.285,0.843,0.633,17.29,0.019,2.96,2014,-0.39), Stocks(THC:NYQ,Fri Ap
                                                  //| r 03 00:00:00 BST 2015,USA,NYQ,THC,Tenet Healthcare Corp,Health-Care-Equipm
                                                  //| ent-and-Services,0.282,USD,6.643,USD,0.0,NA, , ,49.16,48.91,49.68,48.6,48.7
                                                  //| 9,37.95,37.95,63.61,37.95,USD,1920000.0,99150000.000,97790000.000,487000000
                                                  //| 0.000,USD,26.866,0.151,0.054,0.163,0.667,174.2,0.0,7.4,2014,0.753), Stocks(
                                                  //| UHS:NYQ,Fri Apr 03 00:00:00 BST 2015,USA,NYQ,UHS,Universal Health Services 
                                                  //| Inc,Health-Care-Equipment-and-Services,5.42,USD,37.737,USD,0.4,USD,26-02-20
                                                  //| 15,16-03-2015,116.69,112.86,116.96,112.4,112.96,73.06,73.06,121.95,73.06,US
                                                  //| D,993260.0,98980000.000
                                                  //| Output exceeds cutoff limit.

(a map { x => (x.Symbol,x.IssueName,x.RIC,x.PriceToBook,x.Close,x.YearLowPrice,x.YearHighPrice,(((x.Close - x.YearLowPrice)/(x.YearHighPrice - x.YearLowPrice))*100))}).toList.
sortBy(r => r._8).filter {r => (r._8 >=0 && r._8 < 10)}
                                                  //> res2: List[(String, String, String, Double, Double, Double, Double, Double)
                                                  //| ] = List((KORS:NYQ,Michael Kors Holdings Ltd,KORS.K,7.16,63.39,63.21,98.96,
                                                  //| 0.5034965034965028), (MAT:NSQ,Mattel Inc,MAT.O,2.6,22.65,22.32,40.79,1.7866
                                                  //| 811044937647), (PTGCY:PKC,Portugal Telecom SGPS SA,PTGCY.PK,0.32,0.64,0.531
                                                  //| ,4.63,2.6591851671139297), (SDRL:NYQ,Seadrill Ltd,SDRL.K,0.49,9.66,8.58,40.
                                                  //| 44,3.389830508474576), (SNDK:NSQ,SanDisk Corp,SNDK.O,2.14,64.57,63.0,108.77
                                                  //| ,3.430194450513422), (GRMN:NSQ,Garmin Ltd,GRMN.O,2.62,46.49,45.92,62.05,3.5
                                                  //| 337879727216395), (HPQ:NYQ,Hewlett-Packard Co,HPQ,2.16,31.4,31.03,41.1,3.67
                                                  //| 4280039721921), (EMR:NYQ,Emerson Electric Co,EMR,3.83,55.54,54.95,69.94,3.9
                                                  //| 3595730486989), (EMN:NYQ,Eastman Chemical Co,EMN,2.89,68.07,67.13,90.55,4.0
                                                  //| 13663535439785), (RNO:NYQ,Rhino Resource Partners LP,RNO,0.21,2.36,1.77,14.
                                                  //| 65,4.5807453416149055), (JOY:NYQ,Joy Global Inc,JOY,1.35,39.05,37.77,65.36,
                                                  //| 4.639362087712919), (ST
                                                  //| Output exceeds cutoff limit.

/*m map { x =>
 val Symbol = x("Symbol").toString
 val Close  = fmt((x.getAs[DBObject]("PricesandVolume").get)("Close")).toDouble
 val PreviousClose = fmt((x.getAs[DBObject]("PricesandVolume").get)("PreviousClose")).toDouble
 val DebtToEquity = fmt((x.getAs[DBObject]("Ratios").get)("DebtToEquity")).toDouble
 val DailyChange = fmt(((Close - PreviousClose)/Close)*100).toDouble
 Stocks(Symbol,Close,PreviousClose,DebtToEquity,DailyChange)
 //println(Stocks.Symbol)
 }
*/
                                                   
 
                                                  
}