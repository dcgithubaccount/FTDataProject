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
                                                  //> temp  : scala.xml.Elem = <query yahoo:lang="en-US" yahoo:created="2015-12-1
                                                  //| 6T22:04:43Z" yahoo:count="10" xmlns:yahoo="http://www.yahooapis.com/v1/base
                                                  //| .rng"><diagnostics><url execution-time="2" execution-stop-time="2" executio
                                                  //| n-start-time="0">http://www.datatables.org/yahoo/finance/quote/yahoo.financ
                                                  //| e.quote.xml</url><publiclyCallable>true</publiclyCallable><cache type="MEMC
                                                  //| ACHED" method="GET" execution-time="1" execution-stop-time="6" execution-st
                                                  //| art-time="5">5d1e1de680846a307c9874dc3d6878dc</cache><url execution-time="2
                                                  //| 1" execution-stop-time="27" execution-start-time="6">http://download.financ
                                                  //| e.yahoo.com/d/quotes.csv?f=aa2bb2b3b4cc1c3c4c6c8dd1d2ee1e7e8e9ghjkg1g3g4g5g
                                                  //| 6ii5j1j3j4j5j6k1k2k4k5ll1l2l3mm2m3m4m5m6m7m8nn4opp1p2p5p6qrr1r2r5r6r7ss1s7t
                                                  //| 1t7t8vv1v7ww1w4xy&amp;s=%5EFTSE,%5EGDAXI,%5EFCHI,FTSEMIB.MI,%5EAEX,OMXC20.C
                                                  //| O,%5EOMX,%5EOSEAX,%5ESSMI,%5EBFX</url><query params="{url=[http://download.
                                                  //| finance.yahoo.com/d/quo
                                                  //| Output exceeds cutoff limit.
val result = temp \ "results" \ "quote"           //> result  : scala.xml.NodeSeq = NodeSeq(<quote symbol="^FTSE" xmlns:yahoo="ht
                                                  //| tp://www.yahooapis.com/v1/base.rng"><AverageDailyVolume/><Change>+43.40</Ch
                                                  //| ange><DaysLow>6016.25</DaysLow><DaysHigh>6089.31</DaysHigh><YearLow>5768.22
                                                  //| </YearLow><YearHigh>7122.74</YearHigh><MarketCapitalization/><LastTradePric
                                                  //| eOnly>6061.19</LastTradePriceOnly><DaysRange>6016.25 - 6089.31</DaysRange><
                                                  //| Name>FTSE 100</Name><Symbol>^FTSE</Symbol><Volume>0</Volume><StockExchange>
                                                  //| FSI</StockExchange></quote>, <quote symbol="^GDAXI" xmlns:yahoo="http://www
                                                  //| .yahooapis.com/v1/base.rng"><AverageDailyVolume/><Change>+18.88</Change><Da
                                                  //| ysLow>10423.78</DaysLow><DaysHigh>10572.55</DaysHigh><YearLow>9219.05</Year
                                                  //| Low><YearHigh>12390.75</YearHigh><MarketCapitalization/><LastTradePriceOnly
                                                  //| >10469.26</LastTradePriceOnly><DaysRange>10423.78 - 10572.55</DaysRange><Na
                                                  //| me>DAX</Name><Symbol>^GDAXI</Symbol><Volume>0</Volume><StockExchange>GER</S
                                                  //| tockExchange></quote>, 
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
}                                                 //> index  : scala.collection.immutable.Seq[test.Index] = List(Index(^FTSE,+43.
                                                  //| 40,6016.25,6089.31,5768.22,7122.74,6061.19,6016.25 - 6089.31,FTSE 100,0,FSI
                                                  //| ), Index(^GDAXI,+18.88,10423.78,10572.55,9219.05,12390.75,10469.26,10423.78
                                                  //|  - 10572.55,DAX,0,GER), Index(^FCHI,+10.27,4593.92,4664.20,3926.34,5283.71,
                                                  //| 4624.67,4593.92 - 4664.20,CAC 40,0,PAR), Index(FTSEMIB.MI,-62.45,21095.44,2
                                                  //| 1373.88,17729.07,24157.39,21210.23,21095.44 - 21373.88,FTSE MIB,0,MIL), Ind
                                                  //| ex(^AEX,+6.87,431.15,441.60,389.31,510.55,440.50,431.15 - 441.60,AEX,0,AMS)
                                                  //| , Index(OMXC20.CO,+3.68,976.97,985.98,673.84,1003.35,979.05,976.97 - 985.98
                                                  //| ,** SEE<^OMXC20>,0,CPH), Index(^OMX,+1.547852,1418.871338,1439.501099,1369.
                                                  //| 290161,1720.020142,1428.022217,1418.871338 - 1439.501099,OMXS30,0,STO), Ind
                                                  //| ex(^OSEAX,+2.73,635.32,644.65,571.50,717.78,643.54,635.32 - 644.65,OSLO EXC
                                                  //| H ALL SHARE,0,OSL), Index(^SSMI,+22.52,8565.67,8657.66,7852.83,9537.90,8604
                                                  //| .08,8565.67 - 8657.66,S
                                                  //| Output exceeds cutoff limit.

//index.tail.tail.tail.head.Symbol

for ( i <- index) {
println(i.Symbol,i.Name, i.StockExchange,i.LastTradePriceOnly,i.Change,i.Volume,i.DaysLow,i.DaysHigh,i.YearLow,i.YearHigh)
                                                  //> (^FTSE,FTSE 100,FSI,6061.19,+43.40,0,6016.25,6089.31,5768.22,7122.74)
                                                  //| (^GDAXI,DAX,GER,10469.26,+18.88,0,10423.78,10572.55,9219.05,12390.75)
                                                  //| (^FCHI,CAC 40,PAR,4624.67,+10.27,0,4593.92,4664.20,3926.34,5283.71)
                                                  //| (FTSEMIB.MI,FTSE MIB,MIL,21210.23,-62.45,0,21095.44,21373.88,17729.07,24157
                                                  //| .39)
                                                  //| (^AEX,AEX,AMS,440.50,+6.87,0,431.15,441.60,389.31,510.55)
                                                  //| (OMXC20.CO,** SEE<^OMXC20>,CPH,979.05,+3.68,0,976.97,985.98,673.84,1003.35)
                                                  //| 
                                                  //| (^OMX,OMXS30,STO,1428.022217,+1.547852,0,1418.871338,1439.501099,1369.29016
                                                  //| 1,1720.020142)
                                                  //| (^OSEAX,OSLO EXCH ALL SHARE,OSL,643.54,+2.73,0,635.32,644.65,571.50,717.78)
                                                  //| 
                                                  //| (^SSMI,SMI,VTX,8604.08,+22.52,0,8565.67,8657.66,7852.83,9537.90)
                                                  //| (^BFX,EURONEXT BEL-20,BRU,3578.00,-10.00,0,3595.00,3629.00,3130.25,3910.33)
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
                                                  //| meFormatter@50a638b5

val dt = new DateTime()                           //> dt  : org.joda.time.DateTime = 2015-12-16T22:04:43.440Z
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
                                                  //> newDate  : org.joda.time.DateTime = 2015-01-16T22:05:00.000Z
newDate.getSecondOfDay() - dt.getSecondOfDay()    //> res4: Int = 17

dt.getDayOfWeek()                                 //> res5: Int = 3

dt.getHourOfDay()                                 //> res6: Int = 22
dt.getMinuteOfHour()                              //> res7: Int = 4


val init = "http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.xchange%20where%20pair%20in%20("
                                                  //> init  : String = http://query.yahooapis.com/v1/public/yql?q=select%20*%20fr
                                                  //| om%20yahoo.finance.xchange%20where%20pair%20in%20(
val last = ")&env=store://datatables.org/alltableswithkeys"
                                                  //> last  : String = )&env=store://datatables.org/alltableswithkeys
//val ccy = Array("AED",	"AFN",	"ALL",	"AMD",	"ANG",	"AOA",	"ARS",	"AUD",	"AWG",	"AZN",	"BAM",	"BBD",	"BDT",	"BGN",	"BHD",	"BIF",	"BMD",	"BND",	"BOB",	"BRL",	"BSD",	"BTC",	"BTN",	"BWP",	"BYR",	"BZD",	"CAD",	"CDF",	"CHF",	"CLF",	"CLP",	"CNY",	"COP",	"CRC",	"CUC",	"CUP",	"CVE",	"CZK",	"DJF",	"DKK",	"DOP",	"DZD",	"EEK",	"EGP",	"ERN",	"ETB",	"EUR",	"FJD",	"FKP",	"GBP",	"GEL",	"GGP",	"GHS",	"GIP",	"GMD",	"GNF",	"GTQ",	"GYD",	"HKD",	"HNL",	"HRK",	"HTG",	"HUF",	"IDR",	"ILS",	"IMP",	"INR",	"IQD",	"IRR",	"ISK",	"JEP",	"JMD",	"JOD",	"JPY",	"KES",	"KGS",	"KHR",	"KMF",	"KPW",	"KRW",	"KWD",	"KYD",	"KZT",	"LAK",	"LBP",	"LKR",	"LRD",	"LSL",	"LTL",	"LVL",	"LYD",	"MAD",	"MDL",	"MGA",	"MKD",	"MMK",	"MNT",	"MOP",	"MRO",	"MTL",	"MUR",	"MVR",	"MWK",	"MXN",	"MYR",	"MZN",	"NAD",	"NGN",	"NIO",	"NOK",	"NPR",	"NZD",	"OMR",	"PAB",	"PEN",	"PGK",	"PHP",	"PKR",	"PLN",	"PYG",	"QAR",	"RON",	"RSD",	"RUB",	"RWF",	"SAR",	"SBD",	"SCR",	"SDG",	"SEK",	"SGD",	"SHP",	"SLL",	"SOS",	"SRD",	"STD",	"SVC",	"SYP",	"SZL",	"THB",	"TJS",	"TMT",	"TND",	"TOP",	"TRY",	"TTD",	"TWD",	"TZS",	"UAH",	"UGX",	"USD",	"UYU",	"UZS",	"VEF",	"VND",	"VUV",	"WST",	"XAF",	"XAG",	"XAU",	"XCD",	"XDR",	"XOF",	"XPF",	"YER",	"ZAR",	"ZMK",	"ZMW",	"ZWL")
val ccy = Array("RUB","MXN","INR","KRW","TRY","GBP","PLN","NZD","COP","AUD","CAD","THB","CLP","USD","EUR","SEK","SGD","NOK","JPY","ZAR","MYR","TWD","CHF","PHP","ILS","IDR","CNY","BRL","PEN","HUF","DKK")
                                                  //> ccy  : Array[String] = Array(RUB, MXN, INR, KRW, TRY, GBP, PLN, NZD, COP, A
                                                  //| UD, CAD, THB, CLP, USD, EUR, SEK, SGD, NOK, JPY, ZAR, MYR, TWD, CHF, PHP, I
                                                  //| LS, IDR, CNY, BRL, PEN, HUF, DKK)
val base = "USD"                                  //> base  : String = USD

val ccyarray = ccy map {x => "%22"+base + x + "%22"}
                                                  //> ccyarray  : Array[String] = Array(%22USDRUB%22, %22USDMXN%22, %22USDINR%22,
                                                  //|  %22USDKRW%22, %22USDTRY%22, %22USDGBP%22, %22USDPLN%22, %22USDNZD%22, %22U
                                                  //| SDCOP%22, %22USDAUD%22, %22USDCAD%22, %22USDTHB%22, %22USDCLP%22, %22USDUSD
                                                  //| %22, %22USDEUR%22, %22USDSEK%22, %22USDSGD%22, %22USDNOK%22, %22USDJPY%22, 
                                                  //| %22USDZAR%22, %22USDMYR%22, %22USDTWD%22, %22USDCHF%22, %22USDPHP%22, %22US
                                                  //| DILS%22, %22USDIDR%22, %22USDCNY%22, %22USDBRL%22, %22USDPEN%22, %22USDHUF%
                                                  //| 22, %22USDDKK%22)
val ccystring = ccyarray.mkString(",")            //> ccystring  : String = %22USDRUB%22,%22USDMXN%22,%22USDINR%22,%22USDKRW%22,%
                                                  //| 22USDTRY%22,%22USDGBP%22,%22USDPLN%22,%22USDNZD%22,%22USDCOP%22,%22USDAUD%2
                                                  //| 2,%22USDCAD%22,%22USDTHB%22,%22USDCLP%22,%22USDUSD%22,%22USDEUR%22,%22USDSE
                                                  //| K%22,%22USDSGD%22,%22USDNOK%22,%22USDJPY%22,%22USDZAR%22,%22USDMYR%22,%22US
                                                  //| DTWD%22,%22USDCHF%22,%22USDPHP%22,%22USDILS%22,%22USDIDR%22,%22USDCNY%22,%2
                                                  //| 2USDBRL%22,%22USDPEN%22,%22USDHUF%22,%22USDDKK%22

val finalurlString = init+ccystring+last          //> finalurlString  : String = http://query.yahooapis.com/v1/public/yql?q=selec
                                                  //| t%20*%20from%20yahoo.finance.xchange%20where%20pair%20in%20(%22USDRUB%22,%2
                                                  //| 2USDMXN%22,%22USDINR%22,%22USDKRW%22,%22USDTRY%22,%22USDGBP%22,%22USDPLN%22
                                                  //| ,%22USDNZD%22,%22USDCOP%22,%22USDAUD%22,%22USDCAD%22,%22USDTHB%22,%22USDCLP
                                                  //| %22,%22USDUSD%22,%22USDEUR%22,%22USDSEK%22,%22USDSGD%22,%22USDNOK%22,%22USD
                                                  //| JPY%22,%22USDZAR%22,%22USDMYR%22,%22USDTWD%22,%22USDCHF%22,%22USDPHP%22,%22
                                                  //| USDILS%22,%22USDIDR%22,%22USDCNY%22,%22USDBRL%22,%22USDPEN%22,%22USDHUF%22,
                                                  //| %22USDDKK%22)&env=store://datatables.org/alltableswithkeys
val xchange =  XML.loadString(Source.fromURL(new URL(finalurlString)).mkString)
                                                  //> xchange  : scala.xml.Elem = <query yahoo:lang="en-US" yahoo:created="2015-1
                                                  //| 2-16T22:04:43Z" yahoo:count="31" xmlns:yahoo="http://www.yahooapis.com/v1/b
                                                  //| ase.rng"><results><rate id="USDRUB"><Name>USD/RUB</Name><Rate>70.2885</Rate
                                                  //| ><Date>12/16/2015</Date><Time>10:03pm</Time><Ask>70.4780</Ask><Bid>70.2885<
                                                  //| /Bid></rate><rate id="USDMXN"><Name>USD/MXN</Name><Rate>16.9878</Rate><Date
                                                  //| >12/16/2015</Date><Time>10:04pm</Time><Ask>16.9962</Ask><Bid>16.9878</Bid><
                                                  //| /rate><rate id="USDINR"><Name>USD/INR</Name><Rate>66.4399</Rate><Date>12/16
                                                  //| /2015</Date><Time>10:03pm</Time><Ask>66.4500</Ask><Bid>66.4399</Bid></rate>
                                                  //| <rate id="USDKRW"><Name>USD/KRW</Name><Rate>1172.0551</Rate><Date>12/16/201
                                                  //| 5</Date><Time>10:04pm</Time><Ask>1174.0601</Ask><Bid>1172.0551</Bid></rate>
                                                  //| <rate id="USDTRY"><Name>USD/TRY</Name><Rate>2.9353</Rate><Date>12/16/2015</
                                                  //| Date><Time>10:04pm</Time><Ask>2.9374</Ask><Bid>2.9353</Bid></rate><rate id=
                                                  //| "USDGBP"><Name>USD/GBP<
                                                  //| Output exceeds cutoff limit.


//val ccyurl = "http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.xchange%20where%20pair%20in%20(%22USDAUD%22,%22USDEUR%22,%22USDCAD%22,%22USDDKK%22,%22USDHKD%22,%22USDINR%22,%22USDJPY%22,%22USDKRW%22,%22USDBRL%22,%22USDMXN%22,%22USDCLP%22,%22USDPEN%22,%22USDCOP%22,%22USDNZD%22,%22USDNOK%22,%22USDSGD%22,%22USDSEK%22,%22USDCHF%22,%22USDGBP%22,%22USDUSD%22,%22USDZAR%22,%22USDTRY%22,%22USDCNY%22,%22USDTHB%22,%22USDIDR%22,%22USDILS%22,%22USDRUB%22)&env=store://datatables.org/alltableswithkeys"

//val xchange = XML.loadString(Source.fromURL(new URL("http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.xchange%20where%20pair%20in%20(%22USDAUD%22,%22USDEUR%22,%22USDCAD%22,%22USDDKK%22,%22USDHKD%22,%22USDINR%22,%22USDJPY%22,%22USDKRW%22,%22USDBRL%22,%22USDMXN%22,%22USDCLP%22,%22USDPEN%22,%22USDCOP%22,%22USDNZD%22,%22USDNOK%22,%22USDSGD%22,%22USDSEK%22,%22USDCHF%22,%22USDGBP%22,%22USDUSD%22,%22USDZAR%22,%22USDTRY%22,%22USDCNY%22,%22USDTHB%22,%22USDIDR%22,%22USDILS%22,%22USDRUB%22)&env=store://datatables.org/alltableswithkeys")).mkString)

case class Currency(
val Name : String,
val Rate : String,
val Date : String,
val Time : String,
val Bid : String,
val Ask : String)



val currency1 = (xchange \ "results" \ "rate").map {
n =>
val Name = (n \ "Name").text
val Rate = (n \ "Rate").text
val Date = (n \ "Date").text
val Time = (n \ "Time").text
val Bid  = (n \ "Bid").text
val Ask  = (n \ "Ask").text

Currency(Name,Rate,Date,Time,Bid,Ask)

}                                                 //> currency1  : scala.collection.immutable.Seq[test.Currency] = List(Currency(
                                                  //| USD/RUB,70.2885,12/16/2015,10:03pm,70.2885,70.4780), Currency(USD/MXN,16.98
                                                  //| 78,12/16/2015,10:04pm,16.9878,16.9962), Currency(USD/INR,66.4399,12/16/2015
                                                  //| ,10:03pm,66.4399,66.4500), Currency(USD/KRW,1172.0551,12/16/2015,10:04pm,11
                                                  //| 72.0551,1174.0601), Currency(USD/TRY,2.9353,12/16/2015,10:04pm,2.9353,2.937
                                                  //| 4), Currency(USD/GBP,0.6666,12/16/2015,10:04pm,0.6666,0.6667), Currency(USD
                                                  //| /PLN,3.9406,12/16/2015,10:04pm,3.9406,3.9484), Currency(USD/NZD,1.4707,12/1
                                                  //| 6/2015,10:04pm,1.4707,1.4711), Currency(USD/COP,3334.4500,12/16/2015,10:04p
                                                  //| m,3334.4500,3336.7400), Currency(USD/AUD,1.3828,12/16/2015,10:04pm,1.3828,1
                                                  //| .3833), Currency(USD/CAD,1.3783,12/16/2015,10:04pm,1.3783,1.3786), Currency
                                                  //| (USD/THB,35.8995,12/16/2015,10:04pm,35.8995,35.9520), Currency(USD/CLP,705.
                                                  //| 9950,12/16/2015,10:03pm,705.9950,706.5000), Currency(USD/USD,1.0000,N/A,N/A
                                                  //| ,1.0000,1.0000), Curren
                                                  //| Output exceeds cutoff limit.

currency1 filter {x => x.Name != "N/A"} map {x => println(x.Name,x.Rate,x.Date,x.Time)}
                                                  //> (USD/RUB,70.2885,12/16/2015,10:03pm)
                                                  //| (USD/MXN,16.9878,12/16/2015,10:04pm)
                                                  //| (USD/INR,66.4399,12/16/2015,10:03pm)
                                                  //| (USD/KRW,1172.0551,12/16/2015,10:04pm)
                                                  //| (USD/TRY,2.9353,12/16/2015,10:04pm)
                                                  //| (USD/GBP,0.6666,12/16/2015,10:04pm)
                                                  //| (USD/PLN,3.9406,12/16/2015,10:04pm)
                                                  //| (USD/NZD,1.4707,12/16/2015,10:04pm)
                                                  //| (USD/COP,3334.4500,12/16/2015,10:04pm)
                                                  //| (USD/AUD,1.3828,12/16/2015,10:04pm)
                                                  //| (USD/CAD,1.3783,12/16/2015,10:04pm)
                                                  //| (USD/THB,35.8995,12/16/2015,10:04pm)
                                                  //| (USD/CLP,705.9950,12/16/2015,10:03pm)
                                                  //| (USD/USD,1.0000,N/A,N/A)
                                                  //| (USD/EUR,0.9164,12/16/2015,10:04pm)
                                                  //| (USD/SEK,8.5005,12/16/2015,10:04pm)
                                                  //| (USD/SGD,1.4076,12/16/2015,10:04pm)
                                                  //| (USD/NOK,8.7591,12/16/2015,10:04pm)
                                                  //| (USD/JPY,122.2150,12/16/2015,10:04pm)
                                                  //| (USD/ZAR,14.9460,12/16/2015,10:04pm)
                                                  //| (USD/MYR,4.2881,12/16/2015,10:03pm)
                                                  //| (USD/TWD,32.7540,12/16/2015,10:03pm)
                                                  //| (USD/CHF,0.9898,12/16/2015,10:04pm)
                                                  //| (USD/PHP,47.2900,12/16/2015,10:04pm)
                                                  //| (USD/
                                                  //| Output exceeds cutoff limit.

//import com.DC.FTDataParser._
//CorrelationMatricCalc.getCorMatrix("USA", "28-08-2015", 100)



  
  


}