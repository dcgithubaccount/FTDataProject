object cashbahtest {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  
  import com.mongodb.casbah.Imports._
  import scala.math._
  import java.math.BigDecimal
  import scala.util.Try
  val mongoClient = MongoClient("localhost", 27017)
                                                  //> 01:06:23.156 [main] DEBUG c.m.c.c.c.s.RegisterConversionHelpers$ - Registeri
                                                  //| ng Scala Conversions.
                                                  //| 01:06:23.160 [main] DEBUG c.m.c.c.c.s.RegisterConversionHelpers$ - Deseriali
                                                  //| zers for Scala Conversions registering
                                                  //| 01:06:23.161 [main] DEBUG c.m.c.c.c.s.RegisterConversionHelpers$ - Serialize
                                                  //| rs for Scala Conversions registering
                                                  //| 01:06:23.161 [main] DEBUG c.m.c.c.c.s.RegisterConversionHelpers$ - Setting u
                                                  //| p OptionSerializer
                                                  //| 01:06:23.163 [main] DEBUG c.m.c.c.c.s.RegisterConversionHelpers$ - Setting u
                                                  //| p ScalaProductSerializer
                                                  //| 01:06:23.165 [main] DEBUG c.m.c.c.c.s.RegisterConversionHelpers$ - Setting u
                                                  //| p ScalaCollectionSerializer
                                                  //| 01:06:23.166 [main] DEBUG c.m.c.c.c.s.RegisterConversionHelpers$ - Setting u
                                                  //| p ScalaRegexSerializers
                                                  //| 01:06:23.166 [main] DEBUG c.m.c.c.c.s.RegisterConversionHelpers$ - Hooking u
                                                  //| p scala.util.matching.Regex serializer
                                                  //| 01:06:23.167 [main] DEBUG c.m.c.c.c.s.RegisterConversionHelpers$ - Reached b
                                                  //| as
                                                  //| Output exceeds cutoff limit.
	val db = mongoClient("CompanyDatabase")   //> db  : com.mongodb.casbah.MongoDB = CompanyDatabase
	val price = db("PriceAndRatio")           //> price  : com.mongodb.casbah.MongoCollection = PriceAndRatio
	val statement = db("FinancialStatements") //> statement  : com.mongodb.casbah.MongoCollection = FinancialStatements
	val country = "India"                     //> country  : String = India
	val query = MongoDBObject("RunDate" -> "06-04-2015") ++ ("Country" -> country) //++ ("Ratios.AnnualDivYield" $gte 0.025) ++ ("Ratios.PriceToBook" $lte 2.00)
                                                  //> query  : com.mongodb.casbah.commons.Imports.DBObject = { "RunDate" : "06-04-
                                                  //| 2015" , "Country" : "India"}
  //val query =  MongoDBObject("Symbol" -> "RCOM:NSI")
  /*val fields = MongoDBObject("Symbol" -> 1,
  													 "RunDate" -> 2,
  													 "Exchange" -> 3,
  													 "Currency" -> 4,
  													 "Ratios" -> 5,
  													 "FinanceStatements" -> 6,
  													 "IssueName" -> 7,
  													 "PricesandVolume" -> 8)*/
  	
val ct = price.count(query)                       //> ct  : Int = 99
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
                                                  //> a  : List[cashbahtest.Stocks] = List(Stocks(LUPIN:NSI,Mon Apr 06 00:00:00 B
                                                  //| ST 2015,India,NSI,LUPN.NS,Lupin Ltd,Pharmaceuticals-and-Biotechnology,53.53
                                                  //| ,INR,154.723,INR,0.0,NA,21-07-2014,29-07-2014,2088.25,2039.65,2095.0,2039.0
                                                  //| ,2037.5,902.6,902.6,2095.0,902.6,INR,1350000.000,449490000.000,236040000.00
                                                  //| 0,914730000000.000,INR,0.472,0.27,0.248,1.011,0.66,38.06,0.0,13.5,2014,2.43
                                                  //| ), Stocks(IOC:NSI,Mon Apr 06 00:00:00 BST 2015,India,NSI,IOC.NS,Indian Oil 
                                                  //| Corpn Ltd,Oil-and-Gas-Producers,21.11,INR,279.708,INR,8.7,INR,14-08-2014,26
                                                  //| -09-2014,374.0,369.55,375.5,368.55,370.6,258.15,258.15,411.2,258.15,INR,939
                                                  //| 660.000,2430000000.000,248940000.000,899800000000.000,INR,2.927,0.103,0.033
                                                  //| ,0.614,0.698,17.55,0.024,1.34,2014,0.909), Stocks(BOSCHLTD:NSI,Mon Apr 06 0
                                                  //| 0:00:00 BST 2015,India,NSI,BOSH.NS,Bosch Ltd,Automobiles-and-Parts,334.46,I
                                                  //| NR,2380.871,INR,55.0,INR,12-05-2014,05-07-2014,25343.8,25550.0,25560.0,2517
                                                  //| 0.0,25570.7,10052.0,100
                                                  //| Output exceeds cutoff limit.

((a map { x => (x.Symbol,x.IssueName,x.RIC,x.PriceToBook,x.Close,x.YearLowPrice,x.YearHighPrice,(((x.Close - x.YearLowPrice)/(x.YearHighPrice - x.YearLowPrice))*100))}).toList.
sortBy(r => r._8).filter {r => (r._8 >=50 && r._8 < 100)})
                                                  //> res2: List[(String, String, String, Double, Double, Double, Double, Double)
                                                  //| ] = List((CANBK:NSI,Canara Bank Ltd,CNBK.NS,0.59,385.05,259.0,498.0,52.7405
                                                  //| 8577405858), (ICICIBANK:NSI,ICICI Bank Ltd,ICBK.NS,2.42,321.25,240.48,393.4
                                                  //| ,52.81846717237772), (ZEEL:NSI,Zee Entertainment Enterprises Ltd,ZEE.NS,6.8
                                                  //| 5,338.2,258.95,402.4,55.24573021958871), (COALINDIA:NSI,Coal India Ltd,COAL
                                                  //| .NS,5.37,360.75,276.55,423.7,57.22052327556915), (SBIN:NSI,State Bank of In
                                                  //| dia,SBI.NS,1.47,274.65,189.84,336.0,58.025451559934304), (PFC:NSI,Power Fin
                                                  //| ance Corporation Ltd,PWFC.NS,1.33,278.1,179.6,344.75,59.64274901604604), (F
                                                  //| EDERALBNK:NSI,Federal Bank Ltd,FED.NS,1.63,130.2,86.75,154.4,64.22764227642
                                                  //| 274), (OFSS:NSI,Oracle Financial Services Software Ltd,ORCL.NS,3.09,3229.9,
                                                  //| 2430.89,3668.27,64.57272624416106), (ULTRACEMCO:NSI,UltraTech Cement Ltd,UL
                                                  //| TC.NS,4.61,2892.55,1950.5,3398.0,65.08117443868741), (TCS:NSI,Tata Consulta
                                                  //| ncy Services Ltd,TCS.NS
                                                  //| Output exceeds cutoff limit.

val stockquery =  MongoDBObject("Symbol" -> "SREN:VTX")
                                                  //> stockquery  : com.mongodb.casbah.commons.Imports.DBObject = { "Symbol" : "S
                                                  //| REN:VTX"}

val qq = (for (d <- price.find(stockquery)) yield(
										  sf.parse(d("RunDate").toString),
										  fmt((d.getAs[DBObject]("PricesandVolume").get)("Close")).toDouble)).toList.sortBy{x => x._1}
                                                  //> qq  : List[(java.util.Date, Double)] = List((Thu Aug 28 00:00:00 BST 2014,7
                                                  //| 4.8), (Tue Sep 02 00:00:00 BST 2014,75.55), (Wed Sep 03 00:00:00 BST 2014,7
                                                  //| 5.7), (Thu Sep 04 00:00:00 BST 2014,76.25), (Wed Sep 17 00:00:00 BST 2014,7
                                                  //| 6.0), (Wed Oct 01 00:00:00 BST 2014,76.1), (Thu Oct 02 00:00:00 BST 2014,75
                                                  //| .0), (Fri Oct 03 00:00:00 BST 2014,75.25), (Mon Oct 06 00:00:00 BST 2014,75
                                                  //| .0), (Tue Oct 07 00:00:00 BST 2014,74.9), (Wed Oct 08 00:00:00 BST 2014,75.
                                                  //| 6), (Thu Oct 09 00:00:00 BST 2014,75.1), (Fri Oct 10 00:00:00 BST 2014,73.8
                                                  //| ), (Mon Oct 13 00:00:00 BST 2014,73.65), (Tue Oct 14 00:00:00 BST 2014,73.0
                                                  //| 5), (Wed Oct 15 00:00:00 BST 2014,72.05), (Thu Oct 16 00:00:00 BST 2014,71.
                                                  //| 05), (Fri Oct 17 00:00:00 BST 2014,71.7), (Mon Oct 20 00:00:00 BST 2014,71.
                                                  //| 55), (Tue Oct 21 00:00:00 BST 2014,72.3), (Wed Oct 22 00:00:00 BST 2014,73.
                                                  //| 9), (Thu Oct 23 00:00:00 BST 2014,74.9), (Fri Oct 24 00:00:00 BST 2014,74.7
                                                  //| ), (Tue Oct 28 00:00:00
                                                  //| Output exceeds cutoff limit.
//val qq = List(1,2,4,5,7,8,9,22,13,23) map {x => x.toDouble}
qq.length                                         //> res3: Int = 125

val priceqq = qq map {x => x._2}                  //> priceqq  : List[Double] = List(74.8, 75.55, 75.7, 76.25, 76.0, 76.1, 75.0, 
                                                  //| 75.25, 75.0, 74.9, 75.6, 75.1, 73.8, 73.65, 73.05, 72.05, 71.05, 71.7, 71.5
                                                  //| 5, 72.3, 73.9, 74.9, 74.7, 75.65, 77.7, 76.95, 76.75, 78.05, 78.1, 79.65, 8
                                                  //| 0.55, 80.7, 80.5, 80.6, 80.85, 80.55, 81.0, 82.6, 82.65, 83.3, 83.95, 82.95
                                                  //| , 83.95, 83.75, 83.4, 82.95, 83.7, 82.2, 80.8, 81.3, 81.3, 83.2, 83.9, 84.5
                                                  //| 5, 84.9, 84.9, 84.9, 84.9, 84.7, 83.65, 83.65, 82.55, 81.9, 82.2, 84.2, 84.
                                                  //| 15, 84.6, 86.25, 85.3, 80.5, 77.85, 78.65, 79.15, 78.4, 78.95, 80.4, 81.05,
                                                  //|  81.95, 82.1, 83.0, 83.1, 82.75, 83.7, 84.05, 83.8, 84.0, 84.25, 84.35, 84.
                                                  //| 3, 84.55, 84.85, 84.15, 84.8, 85.4, 85.5, 86.1, 86.75, 87.05, 87.45, 87.65,
                                                  //|  87.85, 87.75, 88.2, 89.0, 89.35, 89.7, 89.55, 90.6, 91.1, 91.55, 92.4, 92.
                                                  //| 45, 93.0, 93.2, 94.85, 94.65, 95.0, 93.25, 93.1, 94.9, 94.1, 94.5, 94.7, 94
                                                  //| .7, 94.7)

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
                                                  //> mva15  : List[Double] = List(93.967)
 val mva50 = (movingAverage(qq map {x => x._2},50) map {x => fmt(x).toDouble}).reverse.take(1)
                                                  //> mva50  : List[Double] = List(88.224)
 

                                                   
 
                                                  
}