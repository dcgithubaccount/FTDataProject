object cashbahtest {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(64); 
  println("Welcome to the Scala worksheet")
  
  
  import com.mongodb.casbah.Imports._
  import scala.math._
  import java.math.BigDecimal
  import scala.util.Try;$skip(172); 
  val mongoClient = MongoClient("localhost", 27017);System.out.println("""mongoClient  : com.mongodb.casbah.MongoClient = """ + $show(mongoClient ));$skip(41); 
	val db = mongoClient("CompanyDatabase");System.out.println("""db  : com.mongodb.casbah.MongoDB = """ + $show(db ));$skip(33); 
	val price = db("PriceAndRatio");System.out.println("""price  : com.mongodb.casbah.MongoCollection = """ + $show(price ));$skip(43); 
	val statement = db("FinancialStatements");System.out.println("""statement  : com.mongodb.casbah.MongoCollection = """ + $show(statement ));$skip(23); 
	val country = "India";System.out.println("""country  : String = """ + $show(country ));$skip(158); 
	val query = MongoDBObject("RunDate" -> "06-04-2015") ++ ("Country" -> country);System.out.println("""query  : com.mongodb.casbah.commons.Imports.DBObject = """ + $show(query ));$skip(380);  //++ ("Ratios.AnnualDivYield" $gte 0.025) ++ ("Ratios.PriceToBook" $lte 2.00)
  //val query =  MongoDBObject("Symbol" -> "RCOM:NSI")
  /*val fields = MongoDBObject("Symbol" -> 1,
  													 "RunDate" -> 2,
  													 "Exchange" -> 3,
  													 "Currency" -> 4,
  													 "Ratios" -> 5,
  													 "FinanceStatements" -> 6,
  													 "IssueName" -> 7,
  													 "PricesandVolume" -> 8)*/
  	
val ct = price.count(query);System.out.println("""ct  : Int = """ + $show(ct ));$skip(51); 
val stquery = MongoDBObject("_id" -> "CM:TOR2014");System.out.println("""stquery  : com.mongodb.casbah.commons.Imports.DBObject = """ + $show(stquery ));$skip(46); 
for (s <- statement.find(stquery)) println(s)

import java.util.Date
import java.text.SimpleDateFormat;$skip(102); 


val sdf = new SimpleDateFormat("yyyyMMdd");System.out.println("""sdf  : java.text.SimpleDateFormat = """ + $show(sdf ));$skip(44); 
val sf = new SimpleDateFormat("dd-MM-yyyy");System.out.println("""sf  : java.text.SimpleDateFormat = """ + $show(sf ));$skip(33); val res$0 = 
sf.format(sdf.parse("20150220"));System.out.println("""res0: String = """ + $show(res$0));$skip(58); 

def Desc[T : Ordering] = implicitly[Ordering[T]].reverse;System.out.println("""Desc: [T](implicit evidence$2: scala.math.Ordering[T])scala.math.Ordering[T]""");$skip(159); 
def fmt(v: Any): String = v match {
	case d : Double => "%1.3f" format d
	case i : Int => i.toString
	case b : BigDecimal => "%.3f" format b
	case _ => " "
	};System.out.println("""fmt: (v: Any)String""");$skip(47); 

val m = for (d <- price.find(query)) yield(d);System.out.println("""m  : Iterator[com.mongodb.casbah.Imports.DBObject] = """ + $show(m ));$skip(72); 


def parseDate(date : String) : Try[Date] = {
		Try(sf.parse(date))
	};System.out.println("""parseDate: (date: String)scala.util.Try[java.util.Date]""");$skip(53); val res$1 = 
	
	parseDate("01-Mar-2015") getOrElse("UnknownDate")
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
                   );System.out.println("""res1: Comparable[_ >: java.util.Date with String <: Comparable[_ >: java.util.Date with String <: java.io.Serializable] with java.io.Serializable] with java.io.Serializable = """ + $show(res$1));$skip(4945); 

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
  };System.out.println("""objectRead: (o: com.mongodb.casbah.Imports.DBObject)cashbahtest.Stocks""");$skip(81); 


val a = (m map {x => objectRead(x)}).toList.sortBy(_.MarketCap.toDouble)(Desc);System.out.println("""a  : List[cashbahtest.Stocks] = """ + $show(a ));$skip(237); val res$2 = 

((a map { x => (x.Symbol,x.IssueName,x.RIC,x.PriceToBook,x.Close,x.YearLowPrice,x.YearHighPrice,(((x.Close - x.YearLowPrice)/(x.YearHighPrice - x.YearLowPrice))*100))}).toList.
sortBy(r => r._8).filter {r => (r._8 >=50 && r._8 < 100)});System.out.println("""res2: List[(String, String, String, Double, Double, Double, Double, Double)] = """ + $show(res$2));$skip(57); 

val stockquery =  MongoDBObject("Symbol" -> "SREN:VTX");System.out.println("""stockquery  : com.mongodb.casbah.commons.Imports.DBObject = """ + $show(stockquery ));$skip(202); 

val qq = (for (d <- price.find(stockquery)) yield(
										  sf.parse(d("RunDate").toString),
										  fmt((d.getAs[DBObject]("PricesandVolume").get)("Close")).toDouble)).toList.sortBy{x => x._1};System.out.println("""qq  : List[(java.util.Date, Double)] = """ + $show(qq ));$skip(72); val res$3 = 
//val qq = List(1,2,4,5,7,8,9,22,13,23) map {x => x.toDouble}
qq.length;System.out.println("""res3: Int = """ + $show(res$3));$skip(34); 

val priceqq = qq map {x => x._2};System.out.println("""priceqq  : List[Double] = """ + $show(priceqq ));$skip(409); 

def movingAverage(values: List[Double], period: Int): List[Double] = {
   val first = (values take period).sum / period
   val subtract = values map (_ / period)
   val add = subtract drop period
   val addAndSubtract = add zip subtract map Function.tupled(_ - _)
   val res = (addAndSubtract.foldLeft(first :: List.fill(period - 1)(0.0)) {
     (acc, add) => (add + acc.head) :: acc
   }).reverse
   res
 };System.out.println("""movingAverage: (values: List[Double], period: Int)List[Double]""");$skip(97); 
 
 val mva15 = (movingAverage(qq map {x => x._2},15) map {x => fmt(x).toDouble}).reverse.take(1);System.out.println("""mva15  : List[Double] = """ + $show(mva15 ));$skip(95); 
 val mva50 = (movingAverage(qq map {x => x._2},50) map {x => fmt(x).toDouble}).reverse.take(1);System.out.println("""mva50  : List[Double] = """ + $show(mva50 ))}
 

                                                   
 
                                                  
}
