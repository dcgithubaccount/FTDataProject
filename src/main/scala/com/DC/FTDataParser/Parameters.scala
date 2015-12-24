package com.DC.FTDataParser
import scala.util._
import com.mongodb.casbah.Imports._
import java.util.Date
import java.text.SimpleDateFormat

trait Parameters {
  // All Statics and Maps from here below
  val path = "/Volumes/MyData/SharesProject/FTCompanyData/Output/"
  val ReportPath = "/Volumes/MyData/SharesProject/FTCompanyData/Reports/"
  val PBConfFile = "/Volumes/MyData/SharesProject/FTCompanyData/Configuration/PBDivi.csv"  
  val YahooIndustryFile = "/Volumes/MyData/SharesProject/YahooIndustry/YahooIndustry.csv"
  val sdf = new SimpleDateFormat("yyyyMMdd")
  val sf = new SimpleDateFormat("dd-MM-yyyy")  
    
  val file_map = Map(
    "Australia" -> "_ASX100.csv",
    "NewZealand" -> "_NZSX50.csv",
    "HongKong" -> "_HSI50.csv",
    "Korea" -> "_KOSPI100.csv",
    "Japan" -> "_NIKKEI225.csv",
    "Singapore" -> "_STI30.csv",
    "India" -> "_NIFTY100.csv",
    "Italy" -> "_FTSEMIB.csv",
    "Swiss" -> "_SMI20.csv",
    "Germany" -> "_DAX80.csv",
    "France" -> "_CAC40.csv",
    "Sweden" -> "_OMXS30.csv",
    "UK" -> "_FTSE100.csv",
    "Holland" -> "_AEX25.csv",
    "Belgium" -> "_BEL20.csv",
    "Denmark" -> "_OMX20.csv",
    "Finland" -> "_OMX25.csv",
    "Norway" -> "_OSLOAllShare.csv",
    "Spain" -> "_IBEX35.csv",
    "Portugal" -> "_PSI20.csv",
    "SouthAfrica" -> "_JSE40.csv",
    "USA" -> "_NYQ_NSQ.csv",
    "Canada" -> "_TSX60.csv",
    "Brazil" -> "_BOVESPA.csv",
    "Mexico" -> "_IPC.csv",
    "LATAM" -> "_LATAM40.csv",
    "China" -> "_SSEComposite.csv",
    "Russia" -> "_MICEX.csv",
    "Austria" -> "_VIE20.csv",
    "Ireland" -> "_ISEQ20.csv",
    "Poland" -> "_WIG20.csv")
    
val exchange_currency_map = Map(

    "ASX" -> "AUD",
    "NZC" -> "NZD",
    "HKG" -> "HKD",
    "KSC" -> "KRW",
    "TYO" -> "JPY",
    "SES" -> "SGD",
    "NSI" -> "INR",
    "MIL" -> "EUR",
    "VTX" -> "CHF",
    "SWX" -> "CHF",
    "GER" -> "EUR",
    "PAR" -> "EUR",
    "STO" -> "SEK",
    "LSE" -> "GBX",
    "AEX" -> "EUR",
    "BRU" -> "EUR",
    "MCE" -> "EUR",
    "CPH" -> "DKK",
    "HEX" -> "EUR",
    "OSL" -> "NOK",
    "LIS" -> "EUR",
    "NSQ" -> "USD",
    "NYQ" -> "USD",
    "NAQ" -> "USD",
    "TOR" -> "CAD",
    "SAO" -> "BRL",
    "MEX" -> "MXN",
    "SGO" -> "CLP",
    "JNB" -> "ZAX",
    "MCX" -> "RUB",
    "SHH" -> "CNY",
    "VIE" -> "EUR",
    "WSE" -> "PLN",
    "ISE" -> "EUR"
    
    )
    
    
val adjust_currency_map = Map(
   "GBX" -> 100,
   "ZAX" -> 100
    ).withDefaultValue(1)     

val exchange_to_real_ccy_map = Map(
	"GBX" -> "GBP",
	"ZAX" -> "ZAR"
)

val ccyurl = "http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.xchange%20where%20pair%20in%20(%22USDAUD%22,%22USDEUR%22,%22USDCAD%22,%22USDDKK%22,%22USDHKD%22,%22USDINR%22,%22USDJPY%22,%22USDKRW%22,%22USDBRL%22,%22USDMXN%22,%22USDCLP%22,%22USDPEN%22,%22USDCOP%22,%22USDNZD%22,%22USDNOK%22,%22USDSGD%22,%22USDSEK%22,%22USDCHF%22,%22USDGBP%22,%22USDUSD%22,%22USDZAR%22,%22USDCNY%22,%22USDRUB%22,%22USDTWD%22,%22USDPLN%22)&env=store://datatables.org/alltableswithkeys"

  //  All common functions  from down below
def stringconvertor(prefix: String) : Any = {

		prefix.takeRight(1) match {
		case "k" => prefix.reverse.substring(1).reverse.toDouble * 1000
		case "m" => prefix.reverse.substring(1).reverse.toDouble * 1000000
		case "n" => prefix.reverse.substring(2).reverse.toDouble * 1000000000
		case "%" => BigDecimal(prefix.reverse.substring(1).reverse) / 100 
		case ")" => BigDecimal(prefix.dropRight(1).drop(1)) * -1
		case "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9" => BigDecimal(prefix)
		case _   => " "
		}

	}	 


def exphandler(i: => Any) = Try(i) match {
  case Success(v) => v
  case Failure(_) => " "
}


def fmt(v: Any): String = v match {
	case d : Double => "%1.2f" format d
	case i : Int => i.toString
	case b : BigDecimal => "%.3f" format b
	case _ => " "
	}
  
def Desc[T : Ordering] = implicitly[Ordering[T]].reverse	   


// All common case class an its reader

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
  }                                             




  
}