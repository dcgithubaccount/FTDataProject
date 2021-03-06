object test {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(57); 
  println("Welcome to the Scala worksheet");$skip(188); 
  
  def fibo(n :Int): Int = {
  	def fibo_tailrec (n : Int, a: Int, b : Int ): Int = n match {
  		case 0 => a
  		case _ => fibo_tailrec(n-1, b , a + b )
  	}
  	fibo_tailrec(n,0,1)
  };System.out.println("""fibo: (n: Int)Int""");$skip(22); val res$0 = 
  
  
  fibo(2000005);System.out.println("""res0: Int = """ + $show(res$0));$skip(108); 
  def mean (xs: Seq[Double]): Option[Double] = {
  	if (xs.isEmpty) None
  	else Some(xs.sum/xs.length)
  };System.out.println("""mean: (xs: Seq[Double])Option[Double]""");$skip(18); val res$1 = 
  
  mean(List());System.out.println("""res1: Option[Double] = """ + $show(res$1));$skip(195); 
  
  
  def findFirst[A] (as:Array[A], p:A => Boolean): Int = {
  	def loop(n: Int): Int = {
  		if (n >= as.length) -1
  		else if (p(as(n))) n
  		else loop(n + 1)
  		}
  		
  	loop(0)
  
  };System.out.println("""findFirst: [A](as: Array[A], p: A => Boolean)Int""");$skip(66); val res$2 = 

findFirst(Array("one","two","three"),(x: String) => x == "five");System.out.println("""res2: Int = """ + $show(res$2));$skip(160); 

	def isSorted[A] (as: Array[A], p:(A,A) => Boolean): Boolean = {
		if (as.length == 1) true
		else if (p(as(0),as(1))) false
		else (isSorted(as.tail,p))
	
	};System.out.println("""isSorted: [A](as: Array[A], p: (A, A) => Boolean)Boolean""");$skip(57); val res$3 = 

isSorted(Array(1,5,5,4,7,3),(x: Int, y: Int) => x == y);System.out.println("""res3: Boolean = """ + $show(res$3));$skip(73); 

def curry[A,B,C] (f :(A,B) => C): A => (B => C) = {
	a => b => f(a,b)
};System.out.println("""curry: [A, B, C](f: (A, B) => C)A => (B => C)""");$skip(40); 

val f = (x: Double) => math.Pi / 2 - x;System.out.println("""f  : Double => Double = """ + $show(f ));$skip(30); 

val cos = f andThen math.sin


import scala.xml._
import java.net._
import scala.io.Source;System.out.println("""cos  : Double => Double = """ + $show(cos ));$skip(441); 
val temp = XML.loadString(Source.fromURL(new URL("https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quote%20where%20symbol%20in%20(%22%5EFTSE%22,%22%5EGDAXI%22,%22%5EFCHI%22,%22FTSEMIB.MI%22,%22%5EAEX%22,%22OMXC20.CO%22,%22%5EOMX%22,%22%5EOSEAX%22,%22%5ESSMI%22,%22%5EBFX%22)&diagnostics=true&env=store://datatables.org/alltableswithkeys")).mkString);System.out.println("""temp  : scala.xml.Elem = """ + $show(temp ));$skip(40); 
val result = temp \ "results" \ "quote"

case class Index(val Symbol : String,
                 val Change     : String,
                 val DaysLow    : String,
                 val DaysHigh   : String,
                 val YearLow    : String,
                 val YearHigh   : String,
                 val LastTradePriceOnly : String,
                 val DaysRange  : String,
                 val Name       : String,
                 val Volume     : String,
                 val StockExchange : String);System.out.println("""result  : scala.xml.NodeSeq = """ + $show(result ));$skip(1097); 
								
val index = (temp \ "results" \ "quote").map {
n =>
val Symbol     = 	(n \ "Symbol").text
val Change     =  (n \ "Change").text
val DaysLow    =  (n \ "DaysLow").text
val DaysHigh   =  (n \ "DaysHigh").text
val YearLow    =  (n \ "YearLow").text
val YearHigh   =  (n \ "YearHigh").text
val LastTradePriceOnly = (n \ "LastTradePriceOnly").text
val DaysRange  =  (n \ "DaysRange").text
val Name       =  (n \ "Name").text
val Volume     =  (n \ "Volume").text
val StockExchange = (n \ "StockExchange").text
Index(Symbol,Change,DaysLow,DaysHigh,YearLow,YearHigh,LastTradePriceOnly,DaysRange,Name,Volume ,StockExchange)
};System.out.println("""index  : scala.collection.immutable.Seq[test.Index] = """ + $show(index ));$skip(180); 

//index.tail.tail.tail.head.Symbol

for ( i <- index) {
println(i.Symbol,i.Name, i.StockExchange,i.LastTradePriceOnly,i.Change,i.Volume,i.DaysLow,i.DaysHigh,i.YearLow,i.YearHigh)
}
/*result.foreach {
n =>
val Symbol     = 	(n \ "Symbol").text
val Change     =  (n \ "Change").text
val DaysLow    =  (n \ "DaysLow").text
val DaysHigh   =  (n \ "DaysHigh").text
val YearLow    =  (n \ "YearLow").text
val YearHigh   =  (n \ "YearHigh").text
val LastTradePriceOnly = (n \ "LastTradePriceOnly").text
val DaysRange  =  (n \ "DaysRange").text
val Name       =  (n \ "Name").text
val Volume     =  (n \ "Volume").text
val StockExchange = (n \ "StockExchange").text
//val static = Array(Symbol,Change,DaysLow,DaysHigh,YearLow,YearHigh,LastTradePriceOnly,DaysRange,Name,Volume ,StockExchange)
println(s"$Symbol",s"$Volume",s"$StockExchange",s"$DaysLow",s"$DaysHigh",s"$YearLow")

}

*/

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat;$skip(906); 


//val dt = new DateTime()
//dt.getMinuteOfHour()
//dt.plusMinutes(1)
val fmt = DateTimeFormat.forPattern("yyyy-mm-dd HH:mm:ss:SSS");System.out.println("""fmt  : org.joda.time.format.DateTimeFormatter = """ + $show(fmt ));$skip(25); 

val dt = new DateTime();System.out.println("""dt  : org.joda.time.DateTime = """ + $show(dt ));$skip(287); 
def getsetTime(date : org.joda.time.DateTime) : org.joda.time.DateTime = {
	val d1 = date.getMinuteOfHour()
	
	d1%5 match {
	case 0 => date.plusMinutes(5)
	case 1 => date.plusMinutes(4)
	case 2 => date.plusMinutes(3)
	case 3 => date.plusMinutes(2)
	case 4 => date.plusMinutes(1)
	}

	
};System.out.println("""getsetTime: (date: org.joda.time.DateTime)org.joda.time.DateTime""");$skip(107); 

val newDate = fmt.parseDateTime(getsetTime(dt).toString(fmt)).withSecondOfMinute(0).withMillisOfSecond(0);System.out.println("""newDate  : org.joda.time.DateTime = """ + $show(newDate ));$skip(47); val res$4 = 
newDate.getSecondOfDay() - dt.getSecondOfDay();System.out.println("""res4: Int = """ + $show(res$4));$skip(19); val res$5 = 

dt.getDayOfWeek();System.out.println("""res5: Int = """ + $show(res$5));$skip(19); val res$6 = 

dt.getHourOfDay();System.out.println("""res6: Int = """ + $show(res$6));$skip(21); val res$7 = 
dt.getMinuteOfHour()

import au.com.bytecode.opencsv.CSVWriter
import scala.collection.JavaConversions._
import java.io.FileWriter
import java.io.BufferedWriter;System.out.println("""res7: Int = """ + $show(res$7));$skip(1859); 
	

def currencyWriter(baseccy :String) = {

case class Currency(
	val Name : String,
	val Rate : String,
	val Date : String,
	val Time : String,
	val Bid : String,
	val Ask : String
	)

	val d1 = new DateTime()
	val filename = d1.getYear().toString + d1.getMonthOfYear().toString + d1.getDayOfMonth().toString + d1.getHourOfDay().toString + d1.getMinuteOfHour().toString + ".csv"
	
	val init = "http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.xchange%20where%20pair%20in%20("
	val last = ")&env=store://datatables.org/alltableswithkeys"
	val ccy = Array("RUB","MXN","INR","KRW","TRY","GBP","PLN","NZD","COP","AUD","CAD","THB","CLP","USD","EUR","SEK","SGD","NOK","JPY","ZAR","MYR","TWD","CHF","PHP","ILS","IDR","CNY","BRL","PEN","HUF","DKK")
	val base = baseccy
	val ccyarray = ccy map {x => "%22"+base + x + "%22"}
	val ccystring = ccyarray.mkString(",")
	val finalurlString = init+ccystring+last
	val xchange =  XML.loadString(Source.fromURL(new URL(finalurlString)).mkString)
	
	val listCcy = (xchange \ "results" \ "rate").map {
					n =>
					val Name = (n \ "Name").text
					val Rate = (n \ "Rate").text
					val Date = (n \ "Date").text
					val Time = (n \ "Time").text
					val Bid  = (n \ "Bid").text
					val Ask  = (n \ "Ask").text

					Currency(Name,Rate,Date,Time,Bid,Ask)

				} filter {x => x.Name != "N/A"} map {x => Array(x.Name.toString,
																												x.Rate.toString,
																												x.Date.toString,
																												x.Time.toString)}
					
	
	val out = new BufferedWriter(new FileWriter("/home/nidhideepak/workspace/FTDataProject/" + filename))
  val writer = new CSVWriter(out)
  		writer.writeAll(listCcy)
      out.close()


};System.out.println("""currencyWriter: (baseccy: String)Unit""");$skip(23); 

currencyWriter("USD")}


//val ccy = Array("AED",	"AFN",	"ALL",	"AMD",	"ANG",	"AOA",	"ARS",	"AUD",	"AWG",	"AZN",	"BAM",	"BBD",	"BDT",	"BGN",	"BHD",	"BIF",	"BMD",	"BND",	"BOB",	"BRL",	"BSD",	"BTC",	"BTN",	"BWP",	"BYR",	"BZD",	"CAD",	"CDF",	"CHF",	"CLF",	"CLP",	"CNY",	"COP",	"CRC",	"CUC",	"CUP",	"CVE",	"CZK",	"DJF",	"DKK",	"DOP",	"DZD",	"EEK",	"EGP",	"ERN",	"ETB",	"EUR",	"FJD",	"FKP",	"GBP",	"GEL",	"GGP",	"GHS",	"GIP",	"GMD",	"GNF",	"GTQ",	"GYD",	"HKD",	"HNL",	"HRK",	"HTG",	"HUF",	"IDR",	"ILS",	"IMP",	"INR",	"IQD",	"IRR",	"ISK",	"JEP",	"JMD",	"JOD",	"JPY",	"KES",	"KGS",	"KHR",	"KMF",	"KPW",	"KRW",	"KWD",	"KYD",	"KZT",	"LAK",	"LBP",	"LKR",	"LRD",	"LSL",	"LTL",	"LVL",	"LYD",	"MAD",	"MDL",	"MGA",	"MKD",	"MMK",	"MNT",	"MOP",	"MRO",	"MTL",	"MUR",	"MVR",	"MWK",	"MXN",	"MYR",	"MZN",	"NAD",	"NGN",	"NIO",	"NOK",	"NPR",	"NZD",	"OMR",	"PAB",	"PEN",	"PGK",	"PHP",	"PKR",	"PLN",	"PYG",	"QAR",	"RON",	"RSD",	"RUB",	"RWF",	"SAR",	"SBD",	"SCR",	"SDG",	"SEK",	"SGD",	"SHP",	"SLL",	"SOS",	"SRD",	"STD",	"SVC",	"SYP",	"SZL",	"THB",	"TJS",	"TMT",	"TND",	"TOP",	"TRY",	"TTD",	"TWD",	"TZS",	"UAH",	"UGX",	"USD",	"UYU",	"UZS",	"VEF",	"VND",	"VUV",	"WST",	"XAF",	"XAG",	"XAU",	"XCD",	"XDR",	"XOF",	"XPF",	"YER",	"ZAR",	"ZMK",	"ZMW",	"ZWL")

//val base = "USD"






//val ccyurl = "http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.xchange%20where%20pair%20in%20(%22USDAUD%22,%22USDEUR%22,%22USDCAD%22,%22USDDKK%22,%22USDHKD%22,%22USDINR%22,%22USDJPY%22,%22USDKRW%22,%22USDBRL%22,%22USDMXN%22,%22USDCLP%22,%22USDPEN%22,%22USDCOP%22,%22USDNZD%22,%22USDNOK%22,%22USDSGD%22,%22USDSEK%22,%22USDCHF%22,%22USDGBP%22,%22USDUSD%22,%22USDZAR%22,%22USDTRY%22,%22USDCNY%22,%22USDTHB%22,%22USDIDR%22,%22USDILS%22,%22USDRUB%22)&env=store://datatables.org/alltableswithkeys"

//val xchange = XML.loadString(Source.fromURL(new URL("http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.xchange%20where%20pair%20in%20(%22USDAUD%22,%22USDEUR%22,%22USDCAD%22,%22USDDKK%22,%22USDHKD%22,%22USDINR%22,%22USDJPY%22,%22USDKRW%22,%22USDBRL%22,%22USDMXN%22,%22USDCLP%22,%22USDPEN%22,%22USDCOP%22,%22USDNZD%22,%22USDNOK%22,%22USDSGD%22,%22USDSEK%22,%22USDCHF%22,%22USDGBP%22,%22USDUSD%22,%22USDZAR%22,%22USDTRY%22,%22USDCNY%22,%22USDTHB%22,%22USDIDR%22,%22USDILS%22,%22USDRUB%22)&env=store://datatables.org/alltableswithkeys")).mkString)







//import com.DC.FTDataParser._
//CorrelationMatricCalc.getCorMatrix("USA", "28-08-2015", 100)



  
  


}
