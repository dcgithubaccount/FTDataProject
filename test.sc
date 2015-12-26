object test {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  def fibo(n :Int): Int = {
  	def fibo_tailrec (n : Int, a: Int, b : Int ): Int = n match {
  		case 0 => a
  		case _ => fibo_tailrec(n-1, b , a + b )
  	}
  	fibo_tailrec(n,0,1)
  }                                               //> fibo: (n: Int)Int
  
  
  fibo(2000005)                                   //> res0: Int = 761581241
  def mean (xs: Seq[Double]): Option[Double] = {
  	if (xs.isEmpty) None
  	else Some(xs.sum/xs.length)
  }                                               //> mean: (xs: Seq[Double])Option[Double]
  
  mean(List())                                    //> res1: Option[Double] = None
  
  
  def findFirst[A] (as:Array[A], p:A => Boolean): Int = {
  	def loop(n: Int): Int = {
  		if (n >= as.length) -1
  		else if (p(as(n))) n
  		else loop(n + 1)
  		}
  		
  	loop(0)
  
  }                                               //> findFirst: [A](as: Array[A], p: A => Boolean)Int

findFirst(Array("one","two","three"),(x: String) => x == "five")
                                                  //> res2: Int = -1

	def isSorted[A] (as: Array[A], p:(A,A) => Boolean): Boolean = {
		if (as.length == 1) true
		else if (p(as(0),as(1))) false
		else (isSorted(as.tail,p))
	
	}                                         //> isSorted: [A](as: Array[A], p: (A, A) => Boolean)Boolean

isSorted(Array(1,5,5,4,7,3),(x: Int, y: Int) => x == y)
                                                  //> res3: Boolean = false

def curry[A,B,C] (f :(A,B) => C): A => (B => C) = {
	a => b => f(a,b)
}                                                 //> curry: [A, B, C](f: (A, B) => C)A => (B => C)

val f = (x: Double) => math.Pi / 2 - x            //> f  : Double => Double = <function1>

val cos = f andThen math.sin                      //> cos  : Double => Double = <function1>


import scala.xml._
import java.net._
import scala.io.Source
val temp = XML.loadString(Source.fromURL(new URL("https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quote%20where%20symbol%20in%20(%22%5EFTSE%22,%22%5EGDAXI%22,%22%5EFCHI%22,%22FTSEMIB.MI%22,%22%5EAEX%22,%22OMXC20.CO%22,%22%5EOMX%22,%22%5EOSEAX%22,%22%5ESSMI%22,%22%5EBFX%22)&diagnostics=true&env=store://datatables.org/alltableswithkeys")).mkString)
                                                  //> temp  : scala.xml.Elem = <query yahoo:lang="en-US" yahoo:created="2015-12-2
                                                  //| 6T00:53:16Z" yahoo:count="10" xmlns:yahoo="http://www.yahooapis.com/v1/base
                                                  //| .rng"><diagnostics><url execution-time="2" execution-stop-time="3" executio
                                                  //| n-start-time="1">http://www.datatables.org/yahoo/finance/quote/yahoo.financ
                                                  //| e.quote.xml</url><publiclyCallable>true</publiclyCallable><cache type="MEMC
                                                  //| ACHED" method="GET" execution-time="1" execution-stop-time="9" execution-st
                                                  //| art-time="8">5d1e1de680846a307c9874dc3d6878dc</cache><url execution-time="4
                                                  //| 7" execution-stop-time="57" execution-start-time="10">http://download.finan
                                                  //| ce.yahoo.com/d/quotes.csv?f=aa2bb2b3b4cc1c3c4c6c8dd1d2ee1e7e8e9ghjkg1g3g4g5
                                                  //| g6ii5j1j3j4j5j6k1k2k4k5ll1l2l3mm2m3m4m5m6m7m8nn4opp1p2p5p6qrr1r2r5r6r7ss1s7
                                                  //| t1t7t8vv1v7ww1w4xy&amp;s=%5EFTSE,%5EGDAXI,%5EFCHI,FTSEMIB.MI,%5EAEX,OMXC20.
                                                  //| CO,%5EOMX,%5EOSEAX,%5ESSMI,%5EBFX</url><query params="{url=[http://download
                                                  //| .finance.yahoo.com/d/qu
                                                  //| Output exceeds cutoff limit.
val result = temp \ "results" \ "quote"           //> result  : scala.xml.NodeSeq = NodeSeq(<quote symbol="^FTSE" xmlns:yahoo="ht
                                                  //| tp://www.yahooapis.com/v1/base.rng"><AverageDailyVolume/><Change>+13.66</Ch
                                                  //| ange><DaysLow>6236.85</DaysLow><DaysHigh>6259.86</DaysHigh><YearLow>5768.22
                                                  //| </YearLow><YearHigh>7122.74</YearHigh><MarketCapitalization/><LastTradePric
                                                  //| eOnly>6254.64</LastTradePriceOnly><DaysRange>6236.85 - 6259.86</DaysRange><
                                                  //| Name>FTSE 100</Name><Symbol>^FTSE</Symbol><Volume>0</Volume><StockExchange>
                                                  //| FSI</StockExchange></quote>, <quote symbol="^GDAXI" xmlns:yahoo="http://www
                                                  //| .yahooapis.com/v1/base.rng"><AverageDailyVolume/><Change>+238.89</Change><D
                                                  //| aysLow>10595.27</DaysLow><DaysHigh>10743.32</DaysHigh><YearLow>9325.05</Yea
                                                  //| rLow><YearHigh>12390.75</YearHigh><MarketCapitalization/><LastTradePriceOnl
                                                  //| y>10727.64</LastTradePriceOnly><DaysRange>10595.27 - 10743.32</DaysRange><N
                                                  //| ame>DAX</Name><Symbol>^GDAXI</Symbol><Volume>0</Volume><StockExchange>GER</
                                                  //| StockExchange></quote>,
                                                  //| Output exceeds cutoff limit.

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
                 val StockExchange : String)
								
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
}                                                 //> index  : scala.collection.immutable.Seq[test.Index] = List(Index(^FTSE,+13.
                                                  //| 66,6236.85,6259.86,5768.22,7122.74,6254.64,6236.85 - 6259.86,FTSE 100,0,FSI
                                                  //| ), Index(^GDAXI,+238.89,10595.27,10743.32,9325.05,12390.75,10727.64,10595.2
                                                  //| 7 - 10743.32,DAX,0,GER), Index(^FCHI,-11.35,4651.44,4671.68,4076.16,5283.71
                                                  //| ,4663.18,4651.44 - 4671.68,CAC 40,0,PAR), Index(FTSEMIB.MI,+396.23,21185.36
                                                  //| ,21462.87,17991.76,24157.39,21456.36,21185.36 - 21462.87,FTSE MIB,0,MIL), I
                                                  //| ndex(^AEX,+0.40,442.32,444.12,401.87,510.55,444.12,442.32 - 444.12,AEX,0,AM
                                                  //| S), Index(OMXC20.CO,+3.68,976.97,985.98,673.84,1003.35,979.05,976.97 - 985.
                                                  //| 98,** SEE<^OMXC20>,0,CPH), Index(^OMX,+32.96,1415.01,1446.69,1369.29,1720.0
                                                  //| 2,1446.69,1415.01 - 1446.69,OMXS30,0,STO), Index(^OSEAX,+17.42,627.11,645.0
                                                  //| 7,580.29,717.78,644.39,627.11 - 645.07,OSLO EXCH ALL SHARE,0,OSL), Index(^S
                                                  //| SMI,+189.92,8607.14,8730.43,7852.83,9537.90,8705.74,8607.14 - 8730.43,SMI,0
                                                  //| ,VTX), Index(^BFX,+32.5
                                                  //| Output exceeds cutoff limit.

//index.tail.tail.tail.head.Symbol

for ( i <- index) {
println(i.Symbol,i.Name, i.StockExchange,i.LastTradePriceOnly,i.Change,i.Volume,i.DaysLow,i.DaysHigh,i.YearLow,i.YearHigh)
                                                  //> (^FTSE,FTSE 100,FSI,6254.64,+13.66,0,6236.85,6259.86,5768.22,7122.74)
                                                  //| (^GDAXI,DAX,GER,10727.64,+238.89,0,10595.27,10743.32,9325.05,12390.75)
                                                  //| (^FCHI,CAC 40,PAR,4663.18,-11.35,0,4651.44,4671.68,4076.16,5283.71)
                                                  //| (FTSEMIB.MI,FTSE MIB,MIL,21456.36,+396.23,0,21185.36,21462.87,17991.76,2415
                                                  //| 7.39)
                                                  //| (^AEX,AEX,AMS,444.12,+0.40,0,442.32,444.12,401.87,510.55)
                                                  //| (OMXC20.CO,** SEE<^OMXC20>,CPH,979.05,+3.68,0,976.97,985.98,673.84,1003.35)
                                                  //| 
                                                  //| (^OMX,OMXS30,STO,1446.69,+32.96,0,1415.01,1446.69,1369.29,1720.02)
                                                  //| (^OSEAX,OSLO EXCH ALL SHARE,OSL,644.39,+17.42,0,627.11,645.07,580.29,717.78
                                                  //| )
                                                  //| (^SSMI,SMI,VTX,8705.74,+189.92,0,8607.14,8730.43,7852.83,9537.90)
                                                  //| (^BFX,EURONEXT BEL-20,BRU,3658.00,+32.50,0,3658.00,3666.00,3175.25,3910.33)
                                                  //| 
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
import org.joda.time.format.DateTimeFormat


//val dt = new DateTime()
//dt.getMinuteOfHour()
//dt.plusMinutes(1)
val fmt = DateTimeFormat.forPattern("yyyy-mm-dd HH:mm:ss:SSS")
                                                  //> fmt  : org.joda.time.format.DateTimeFormatter = org.joda.time.format.DateTi
                                                  //| meFormatter@57156285

val dt = new DateTime()                           //> dt  : org.joda.time.DateTime = 2015-12-26T00:53:17.215Z
def getsetTime(date : org.joda.time.DateTime) : org.joda.time.DateTime = {
	val d1 = date.getMinuteOfHour()
	
	d1%5 match {
	case 0 => date.plusMinutes(5)
	case 1 => date.plusMinutes(4)
	case 2 => date.plusMinutes(3)
	case 3 => date.plusMinutes(2)
	case 4 => date.plusMinutes(1)
	}

	
}                                                 //> getsetTime: (date: org.joda.time.DateTime)org.joda.time.DateTime

val newDate = fmt.parseDateTime(getsetTime(dt).toString(fmt)).withSecondOfMinute(0).withMillisOfSecond(0)
                                                  //> newDate  : org.joda.time.DateTime = 2015-01-26T00:55:00.000Z
newDate.getSecondOfDay() - dt.getSecondOfDay()    //> res4: Int = 103

dt.getDayOfWeek()                                 //> res5: Int = 6

dt.getHourOfDay()                                 //> res6: Int = 0
dt.getMinuteOfHour()                              //> res7: Int = 53

import au.com.bytecode.opencsv.CSVWriter
import scala.collection.JavaConversions._
import java.io.FileWriter
import java.io.BufferedWriter
	

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


}                                                 //> currencyWriter: (baseccy: String)Unit

currencyWriter("USD")


//val ccy = Array("AED",	"AFN",	"ALL",	"AMD",	"ANG",	"AOA",	"ARS",	"AUD",	"AWG",	"AZN",	"BAM",	"BBD",	"BDT",	"BGN",	"BHD",	"BIF",	"BMD",	"BND",	"BOB",	"BRL",	"BSD",	"BTC",	"BTN",	"BWP",	"BYR",	"BZD",	"CAD",	"CDF",	"CHF",	"CLF",	"CLP",	"CNY",	"COP",	"CRC",	"CUC",	"CUP",	"CVE",	"CZK",	"DJF",	"DKK",	"DOP",	"DZD",	"EEK",	"EGP",	"ERN",	"ETB",	"EUR",	"FJD",	"FKP",	"GBP",	"GEL",	"GGP",	"GHS",	"GIP",	"GMD",	"GNF",	"GTQ",	"GYD",	"HKD",	"HNL",	"HRK",	"HTG",	"HUF",	"IDR",	"ILS",	"IMP",	"INR",	"IQD",	"IRR",	"ISK",	"JEP",	"JMD",	"JOD",	"JPY",	"KES",	"KGS",	"KHR",	"KMF",	"KPW",	"KRW",	"KWD",	"KYD",	"KZT",	"LAK",	"LBP",	"LKR",	"LRD",	"LSL",	"LTL",	"LVL",	"LYD",	"MAD",	"MDL",	"MGA",	"MKD",	"MMK",	"MNT",	"MOP",	"MRO",	"MTL",	"MUR",	"MVR",	"MWK",	"MXN",	"MYR",	"MZN",	"NAD",	"NGN",	"NIO",	"NOK",	"NPR",	"NZD",	"OMR",	"PAB",	"PEN",	"PGK",	"PHP",	"PKR",	"PLN",	"PYG",	"QAR",	"RON",	"RSD",	"RUB",	"RWF",	"SAR",	"SBD",	"SCR",	"SDG",	"SEK",	"SGD",	"SHP",	"SLL",	"SOS",	"SRD",	"STD",	"SVC",	"SYP",	"SZL",	"THB",	"TJS",	"TMT",	"TND",	"TOP",	"TRY",	"TTD",	"TWD",	"TZS",	"UAH",	"UGX",	"USD",	"UYU",	"UZS",	"VEF",	"VND",	"VUV",	"WST",	"XAF",	"XAG",	"XAU",	"XCD",	"XDR",	"XOF",	"XPF",	"YER",	"ZAR",	"ZMK",	"ZMW",	"ZWL")

//val base = "USD"






//val ccyurl = "http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.xchange%20where%20pair%20in%20(%22USDAUD%22,%22USDEUR%22,%22USDCAD%22,%22USDDKK%22,%22USDHKD%22,%22USDINR%22,%22USDJPY%22,%22USDKRW%22,%22USDBRL%22,%22USDMXN%22,%22USDCLP%22,%22USDPEN%22,%22USDCOP%22,%22USDNZD%22,%22USDNOK%22,%22USDSGD%22,%22USDSEK%22,%22USDCHF%22,%22USDGBP%22,%22USDUSD%22,%22USDZAR%22,%22USDTRY%22,%22USDCNY%22,%22USDTHB%22,%22USDIDR%22,%22USDILS%22,%22USDRUB%22)&env=store://datatables.org/alltableswithkeys"

//val xchange = XML.loadString(Source.fromURL(new URL("http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.xchange%20where%20pair%20in%20(%22USDAUD%22,%22USDEUR%22,%22USDCAD%22,%22USDDKK%22,%22USDHKD%22,%22USDINR%22,%22USDJPY%22,%22USDKRW%22,%22USDBRL%22,%22USDMXN%22,%22USDCLP%22,%22USDPEN%22,%22USDCOP%22,%22USDNZD%22,%22USDNOK%22,%22USDSGD%22,%22USDSEK%22,%22USDCHF%22,%22USDGBP%22,%22USDUSD%22,%22USDZAR%22,%22USDTRY%22,%22USDCNY%22,%22USDTHB%22,%22USDIDR%22,%22USDILS%22,%22USDRUB%22)&env=store://datatables.org/alltableswithkeys")).mkString)







//import com.DC.FTDataParser._
//CorrelationMatricCalc.getCorMatrix("USA", "28-08-2015", 100)



  
  


}