object test {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  def fibo(n :Int): Int = {
  	def fibo_tailrec (n : Int, a: Int, b : Int ): Int = n match {
  		case 0 => a
  		case _ => fibo_tailrec(n-1, b , a + b )
  	}
  	fibo_tailrec(n,0,1)
  }                                               //> fibo: (n: Int)Int
  
  
  fibo(20000005)                                  //> res0: Int = -94541383
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
                                                  //> temp  : scala.xml.Elem = <query yahoo:lang="en-US" yahoo:created="2015-08-0
                                                  //| 4T20:40:48Z" yahoo:count="10" xmlns:yahoo="http://www.yahooapis.com/v1/base
                                                  //| .rng"><diagnostics><url execution-time="2" execution-stop-time="2" executio
                                                  //| n-start-time="0">http://www.datatables.org/yahoo/finance/quote/yahoo.financ
                                                  //| e.quote.xml</url><publiclyCallable>true</publiclyCallable><cache type="MEMC
                                                  //| ACHED" method="GET" execution-time="0" execution-stop-time="6" execution-st
                                                  //| art-time="6">5d1e1de680846a307c9874dc3d6878dc</cache><url execution-time="2
                                                  //| 3" execution-stop-time="30" execution-start-time="7">http://download.financ
                                                  //| e.yahoo.com/d/quotes.csv?f=aa2bb2b3b4cc1c3c4c6c8dd1d2ee1e7e8e9ghjkg1g3g4g5g
                                                  //| 6ii5j1j3j4j5j6k1k2k4k5ll1l2l3mm2m3m4m5m6m7m8nn4opp1p2p5p6qrr1r2r5r6r7ss1s7t
                                                  //| 1t7t8vv1v7ww1w4xy&amp;s=%5EFTSE,%5EGDAXI,%5EFCHI,FTSEMIB.MI,%5EAEX,OMXC20.C
                                                  //| O,%5EOMX,%5EOSEAX,%5ESSMI,%5EBFX</url><query params="{url=[http://download.
                                                  //| finance.yahoo.com/d/quo
                                                  //| Output exceeds cutoff limit.
val result = temp \ "results" \ "quote"           //> result  : scala.xml.NodeSeq = NodeSeq(<quote symbol="^FTSE" xmlns:yahoo="ht
                                                  //| tp://www.yahooapis.com/v1/base.rng"><AverageDailyVolume/><Change>-2.05</Cha
                                                  //| nge><DaysLow>6644.65</DaysLow><DaysHigh>6715.51</DaysHigh><YearLow>6072.68<
                                                  //| /YearLow><YearHigh>7122.74</YearHigh><MarketCapitalization/><LastTradePrice
                                                  //| Only>6686.57</LastTradePriceOnly><DaysRange>6644.65 - 6715.51</DaysRange><N
                                                  //| ame>FTSE 100</Name><Symbol>^FTSE</Symbol><Volume>0</Volume><StockExchange>F
                                                  //| SI</StockExchange></quote>, <quote symbol="^GDAXI" xmlns:yahoo="http://www.
                                                  //| yahooapis.com/v1/base.rng"><AverageDailyVolume/><Change>+12.35</Change><Day
                                                  //| sLow>11386.01</DaysLow><DaysHigh>11476.90</DaysHigh><YearLow>8354.97</YearL
                                                  //| ow><YearHigh>12390.75</YearHigh><MarketCapitalization/><LastTradePriceOnly>
                                                  //| 11456.07</LastTradePriceOnly><DaysRange>11386.01 - 11476.90</DaysRange><Nam
                                                  //| e>DAX</Name><Symbol>^GDAXI</Symbol><Volume>0</Volume><StockExchange>GER</St
                                                  //| ockExchange></quote>, <
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
}                                                 //> index  : scala.collection.immutable.Seq[test.Index] = List(Index(^FTSE,-2.0
                                                  //| 5,6644.65,6715.51,6072.68,7122.74,6686.57,6644.65 - 6715.51,FTSE 100,0,FSI)
                                                  //| , Index(^GDAXI,+12.35,11386.01,11476.90,8354.97,12390.75,11456.07,11386.01 
                                                  //| - 11476.90,DAX,0,GER), Index(^FCHI,-8.38,5075.88,5114.63,3789.11,5283.71,51
                                                  //| 12.14,5075.88 - 5114.63,CAC 40,0,PAR), Index(FTSEMIB.MI,-241.13,23341.76,23
                                                  //| 657.52,17555.77,24157.39,23473.25,23341.76 - 23657.52,FTSE MIB,0,MIL), Inde
                                                  //| x(^AEX,-0.41,497.11,500.83,366.84,510.55,500.02,497.11 - 500.83,AEX,7263,AM
                                                  //| S), Index(OMXC20.CO,+3.68,976.97,985.98,673.84,1003.35,979.05,976.97 - 985.
                                                  //| 98,** SEE<^OMXC20>,0,CPH), Index(^OMX,-6.404785,1606.535400,1616.897583,124
                                                  //| 6.562866,1720.020142,1611.879272,1606.535400 - 1616.897583,OMXS30,0,STO), I
                                                  //| ndex(^OSEAX,-0.85,679.07,683.45,571.50,717.78,681.97,679.07 - 683.45,OSLO E
                                                  //| XCH ALL SHARE,0,OSL), Index(^SSMI,+11.23,9435.27,9496.20,7852.83,9525.79,94
                                                  //| 80.20,9435.27 - 9496.20
                                                  //| Output exceeds cutoff limit.

//index.tail.tail.tail.head.Symbol

for ( i <- index) {
println(i.Symbol,i.Name, i.StockExchange,i.LastTradePriceOnly,i.Change,i.Volume,i.DaysLow,i.DaysHigh,i.YearLow,i.YearHigh)
                                                  //> (^FTSE,FTSE 100,FSI,6686.57,-2.05,0,6644.65,6715.51,6072.68,7122.74)
                                                  //| (^GDAXI,DAX,GER,11456.07,+12.35,0,11386.01,11476.90,8354.97,12390.75)
                                                  //| (^FCHI,CAC 40,PAR,5112.14,-8.38,0,5075.88,5114.63,3789.11,5283.71)
                                                  //| (FTSEMIB.MI,FTSE MIB,MIL,23473.25,-241.13,0,23341.76,23657.52,17555.77,2415
                                                  //| 7.39)
                                                  //| (^AEX,AEX,AMS,500.02,-0.41,7263,497.11,500.83,366.84,510.55)
                                                  //| (OMXC20.CO,** SEE<^OMXC20>,CPH,979.05,+3.68,0,976.97,985.98,673.84,1003.35)
                                                  //| 
                                                  //| (^OMX,OMXS30,STO,1611.879272,-6.404785,0,1606.535400,1616.897583,1246.56286
                                                  //| 6,1720.020142)
                                                  //| (^OSEAX,OSLO EXCH ALL SHARE,OSL,681.97,-0.85,0,679.07,683.45,571.50,717.78)
                                                  //| 
                                                  //| (^SSMI,SMI,VTX,9480.20,+11.23,0,9435.27,9496.20,7852.83,9525.79)
                                                  //| (^BFX,EURONEXT BEL-20,BRU,3800.00,+94.00,3,3771.00,3805.50,2809.31,3910.33)
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
val init = "http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.xchange%20where%20pair%20in%20("
                                                  //> init  : String = http://query.yahooapis.com/v1/public/yql?q=select%20*%20fr
                                                  //| om%20yahoo.finance.xchange%20where%20pair%20in%20(
val last = ")&env=store://datatables.org/alltableswithkeys"
                                                  //> last  : String = )&env=store://datatables.org/alltableswithkeys
val ccy = Array("AED",	"AFN",	"ALL",	"AMD",	"ANG",	"AOA",	"ARS",	"AUD",	"AWG",	"AZN",	"BAM",	"BBD",	"BDT",	"BGN",	"BHD",	"BIF",	"BMD",	"BND",	"BOB",	"BRL",	"BSD",	"BTC",	"BTN",	"BWP",	"BYR",	"BZD",	"CAD",	"CDF",	"CHF",	"CLF",	"CLP",	"CNY",	"COP",	"CRC",	"CUC",	"CUP",	"CVE",	"CZK",	"DJF",	"DKK",	"DOP",	"DZD",	"EEK",	"EGP",	"ERN",	"ETB",	"EUR",	"FJD",	"FKP",	"GBP",	"GEL",	"GGP",	"GHS",	"GIP",	"GMD",	"GNF",	"GTQ",	"GYD",	"HKD",	"HNL",	"HRK",	"HTG",	"HUF",	"IDR",	"ILS",	"IMP",	"INR",	"IQD",	"IRR",	"ISK",	"JEP",	"JMD",	"JOD",	"JPY",	"KES",	"KGS",	"KHR",	"KMF",	"KPW",	"KRW",	"KWD",	"KYD",	"KZT",	"LAK",	"LBP",	"LKR",	"LRD",	"LSL",	"LTL",	"LVL",	"LYD",	"MAD",	"MDL",	"MGA",	"MKD",	"MMK",	"MNT",	"MOP",	"MRO",	"MTL",	"MUR",	"MVR",	"MWK",	"MXN",	"MYR",	"MZN",	"NAD",	"NGN",	"NIO",	"NOK",	"NPR",	"NZD",	"OMR",	"PAB",	"PEN",	"PGK",	"PHP",	"PKR",	"PLN",	"PYG",	"QAR",	"RON",	"RSD",	"RUB",	"RWF",	"SAR",	"SBD",	"SCR",	"SDG",	"SEK",	"SGD",	"SHP",	"SLL",	"SOS",	"SRD",	"STD",	"SVC",	"SYP",	"SZL",	"THB",	"TJS",	"TMT",	"TND",	"TOP",	"TRY",	"TTD",	"TWD",	"TZS",	"UAH",	"UGX",	"USD",	"UYU",	"UZS",	"VEF",	"VND",	"VUV",	"WST",	"XAF",	"XAG",	"XAU",	"XCD",	"XDR",	"XOF",	"XPF",	"YER",	"ZAR",	"ZMK",	"ZMW",	"ZWL")
                                                  //> ccy  : Array[String] = Array(AED, AFN, ALL, AMD, ANG, AOA, ARS, AUD, AWG, A
                                                  //| ZN, BAM, BBD, BDT, BGN, BHD, BIF, BMD, BND, BOB, BRL, BSD, BTC, BTN, BWP, B
                                                  //| YR, BZD, CAD, CDF, CHF, CLF, CLP, CNY, COP, CRC, CUC, CUP, CVE, CZK, DJF, D
                                                  //| KK, DOP, DZD, EEK, EGP, ERN, ETB, EUR, FJD, FKP, GBP, GEL, GGP, GHS, GIP, G
                                                  //| MD, GNF, GTQ, GYD, HKD, HNL, HRK, HTG, HUF, IDR, ILS, IMP, INR, IQD, IRR, I
                                                  //| SK, JEP, JMD, JOD, JPY, KES, KGS, KHR, KMF, KPW, KRW, KWD, KYD, KZT, LAK, L
                                                  //| BP, LKR, LRD, LSL, LTL, LVL, LYD, MAD, MDL, MGA, MKD, MMK, MNT, MOP, MRO, M
                                                  //| TL, MUR, MVR, MWK, MXN, MYR, MZN, NAD, NGN, NIO, NOK, NPR, NZD, OMR, PAB, P
                                                  //| EN, PGK, PHP, PKR, PLN, PYG, QAR, RON, RSD, RUB, RWF, SAR, SBD, SCR, SDG, S
                                                  //| EK, SGD, SHP, SLL, SOS, SRD, STD, SVC, SYP, SZL, THB, TJS, TMT, TND, TOP, T
                                                  //| RY, TTD, TWD, TZS, UAH, UGX, USD, UYU, UZS, VEF, VND, VUV, WST, XAF, XAG, X
                                                  //| AU, XCD, XDR, XOF, XPF, YER, ZAR, ZMK, ZMW, ZWL)
val base = "INR"                                  //> base  : String = INR

val ccyarray = ccy map {x => "%22"+base + x + "%22"}
                                                  //> ccyarray  : Array[String] = Array(%22INRAED%22, %22INRAFN%22, %22INRALL%22,
                                                  //|  %22INRAMD%22, %22INRANG%22, %22INRAOA%22, %22INRARS%22, %22INRAUD%22, %22I
                                                  //| NRAWG%22, %22INRAZN%22, %22INRBAM%22, %22INRBBD%22, %22INRBDT%22, %22INRBGN
                                                  //| %22, %22INRBHD%22, %22INRBIF%22, %22INRBMD%22, %22INRBND%22, %22INRBOB%22, 
                                                  //| %22INRBRL%22, %22INRBSD%22, %22INRBTC%22, %22INRBTN%22, %22INRBWP%22, %22IN
                                                  //| RBYR%22, %22INRBZD%22, %22INRCAD%22, %22INRCDF%22, %22INRCHF%22, %22INRCLF%
                                                  //| 22, %22INRCLP%22, %22INRCNY%22, %22INRCOP%22, %22INRCRC%22, %22INRCUC%22, %
                                                  //| 22INRCUP%22, %22INRCVE%22, %22INRCZK%22, %22INRDJF%22, %22INRDKK%22, %22INR
                                                  //| DOP%22, %22INRDZD%22, %22INREEK%22, %22INREGP%22, %22INRERN%22, %22INRETB%2
                                                  //| 2, %22INREUR%22, %22INRFJD%22, %22INRFKP%22, %22INRGBP%22, %22INRGEL%22, %2
                                                  //| 2INRGGP%22, %22INRGHS%22, %22INRGIP%22, %22INRGMD%22, %22INRGNF%22, %22INRG
                                                  //| TQ%22, %22INRGYD%22, %22INRHKD%22, %22INRHNL%22, %22INRHRK%22, %22INRHTG%22
                                                  //| , %22INRHUF%22, %22INRI
                                                  //| Output exceeds cutoff limit.
val ccystring = ccyarray.mkString(",")            //> ccystring  : String = %22INRAED%22,%22INRAFN%22,%22INRALL%22,%22INRAMD%22,%
                                                  //| 22INRANG%22,%22INRAOA%22,%22INRARS%22,%22INRAUD%22,%22INRAWG%22,%22INRAZN%2
                                                  //| 2,%22INRBAM%22,%22INRBBD%22,%22INRBDT%22,%22INRBGN%22,%22INRBHD%22,%22INRBI
                                                  //| F%22,%22INRBMD%22,%22INRBND%22,%22INRBOB%22,%22INRBRL%22,%22INRBSD%22,%22IN
                                                  //| RBTC%22,%22INRBTN%22,%22INRBWP%22,%22INRBYR%22,%22INRBZD%22,%22INRCAD%22,%2
                                                  //| 2INRCDF%22,%22INRCHF%22,%22INRCLF%22,%22INRCLP%22,%22INRCNY%22,%22INRCOP%22
                                                  //| ,%22INRCRC%22,%22INRCUC%22,%22INRCUP%22,%22INRCVE%22,%22INRCZK%22,%22INRDJF
                                                  //| %22,%22INRDKK%22,%22INRDOP%22,%22INRDZD%22,%22INREEK%22,%22INREGP%22,%22INR
                                                  //| ERN%22,%22INRETB%22,%22INREUR%22,%22INRFJD%22,%22INRFKP%22,%22INRGBP%22,%22
                                                  //| INRGEL%22,%22INRGGP%22,%22INRGHS%22,%22INRGIP%22,%22INRGMD%22,%22INRGNF%22,
                                                  //| %22INRGTQ%22,%22INRGYD%22,%22INRHKD%22,%22INRHNL%22,%22INRHRK%22,%22INRHTG%
                                                  //| 22,%22INRHUF%22,%22INRIDR%22,%22INRILS%22,%22INRIMP%22,%22INRINR%22,%22INRI
                                                  //| QD%22,%22INRIRR%22,%22I
                                                  //| Output exceeds cutoff limit.

val finalurlString = init+ccystring+last          //> finalurlString  : String = http://query.yahooapis.com/v1/public/yql?q=selec
                                                  //| t%20*%20from%20yahoo.finance.xchange%20where%20pair%20in%20(%22INRAED%22,%2
                                                  //| 2INRAFN%22,%22INRALL%22,%22INRAMD%22,%22INRANG%22,%22INRAOA%22,%22INRARS%22
                                                  //| ,%22INRAUD%22,%22INRAWG%22,%22INRAZN%22,%22INRBAM%22,%22INRBBD%22,%22INRBDT
                                                  //| %22,%22INRBGN%22,%22INRBHD%22,%22INRBIF%22,%22INRBMD%22,%22INRBND%22,%22INR
                                                  //| BOB%22,%22INRBRL%22,%22INRBSD%22,%22INRBTC%22,%22INRBTN%22,%22INRBWP%22,%22
                                                  //| INRBYR%22,%22INRBZD%22,%22INRCAD%22,%22INRCDF%22,%22INRCHF%22,%22INRCLF%22,
                                                  //| %22INRCLP%22,%22INRCNY%22,%22INRCOP%22,%22INRCRC%22,%22INRCUC%22,%22INRCUP%
                                                  //| 22,%22INRCVE%22,%22INRCZK%22,%22INRDJF%22,%22INRDKK%22,%22INRDOP%22,%22INRD
                                                  //| ZD%22,%22INREEK%22,%22INREGP%22,%22INRERN%22,%22INRETB%22,%22INREUR%22,%22I
                                                  //| NRFJD%22,%22INRFKP%22,%22INRGBP%22,%22INRGEL%22,%22INRGGP%22,%22INRGHS%22,%
                                                  //| 22INRGIP%22,%22INRGMD%22,%22INRGNF%22,%22INRGTQ%22,%22INRGYD%22,%22INRHKD%2
                                                  //| 2,%22INRHNL%22,%22INRHR
                                                  //| Output exceeds cutoff limit.
val xchange =  XML.loadString(Source.fromURL(new URL(finalurlString)).mkString)
                                                  //> xchange  : scala.xml.Elem = <query yahoo:lang="en-US" yahoo:created="2015-0
                                                  //| 8-04T20:40:49Z" yahoo:count="169" xmlns:yahoo="http://www.yahooapis.com/v1/
                                                  //| base.rng"><results><rate id="INRAED"><Name>INR/AED</Name><Rate>0.0576</Rate
                                                  //| ><Date>8/4/2015</Date><Time>9:39pm</Time><Ask>0.0576</Ask><Bid>0.0576</Bid>
                                                  //| </rate><rate id="INRAFN"><Name>INR/AFN</Name><Rate>0.9643</Rate><Date>8/4/2
                                                  //| 015</Date><Time>9:39pm</Time><Ask>0.9660</Ask><Bid>0.9643</Bid></rate><rate
                                                  //|  id="INRALL"><Name>INR/ALL</Name><Rate>1.9906</Rate><Date>8/4/2015</Date><T
                                                  //| ime>9:39pm</Time><Ask>2.0266</Ask><Bid>1.9906</Bid></rate><rate id="INRAMD"
                                                  //| ><Name>INR/AMD</Name><Rate>7.4922</Rate><Date>8/4/2015</Date><Time>9:39pm</
                                                  //| Time><Ask>7.4947</Ask><Bid>7.4922</Bid></rate><rate id="INRANG"><Name>INR/A
                                                  //| NG</Name><Rate>0.0281</Rate><Date>8/4/2015</Date><Time>9:39pm</Time><Ask>0.
                                                  //| 0281</Ask><Bid>0.0281</Bid></rate><rate id="INRAOA"><Name>INR/AOA</Name><Ra
                                                  //| te>1.9749</Rate><Date>8
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
                                                  //| INR/AED,0.0576,8/4/2015,9:39pm,0.0576,0.0576), Currency(INR/AFN,0.9643,8/4/
                                                  //| 2015,9:39pm,0.9643,0.9660), Currency(INR/ALL,1.9906,8/4/2015,9:39pm,1.9906,
                                                  //| 2.0266), Currency(INR/AMD,7.4922,8/4/2015,9:39pm,7.4922,7.4947), Currency(I
                                                  //| NR/ANG,0.0281,8/4/2015,9:39pm,0.0281,0.0281), Currency(INR/AOA,1.9749,8/4/2
                                                  //| 015,9:39pm,1.9749,1.9830), Currency(INR/ARS,0.1442,8/4/2015,9:40pm,0.1442,0
                                                  //| .1443), Currency(INR/AUD,0.0212,8/4/2015,9:40pm,0.0212,0.0212), Currency(IN
                                                  //| R/AWG,0.0281,8/4/2015,9:39pm,0.0281,0.0281), Currency(INR/AZN,0.0165,8/4/20
                                                  //| 15,9:39pm,0.0165,0.0166), Currency(INR/BAM,0.0281,8/4/2015,9:39pm,0.0281,0.
                                                  //| 0282), Currency(INR/BBD,0.0313,8/4/2015,9:39pm,0.0313,0.0314), Currency(INR
                                                  //| /BDT,1.2185,8/4/2015,9:39pm,1.2185,1.2443), Currency(INR/BGN FIXING,0.0282,
                                                  //| 8/4/2015,9:40pm,0.0282,0.0282), Currency(INR/BHD,0.0059,8/4/2015,9:39pm,0.0
                                                  //| 059,0.0059), Currency(I
                                                  //| Output exceeds cutoff limit.

currency1 filter {x => x.Name != "N/A"} map {x => println(x.Name,x.Rate,x.Date,x.Time)}
                                                  //> (INR/AED,0.0576,8/4/2015,9:39pm)
                                                  //| (INR/AFN,0.9643,8/4/2015,9:39pm)
                                                  //| (INR/ALL,1.9906,8/4/2015,9:39pm)
                                                  //| (INR/AMD,7.4922,8/4/2015,9:39pm)
                                                  //| (INR/ANG,0.0281,8/4/2015,9:39pm)
                                                  //| (INR/AOA,1.9749,8/4/2015,9:39pm)
                                                  //| (INR/ARS,0.1442,8/4/2015,9:40pm)
                                                  //| (INR/AUD,0.0212,8/4/2015,9:40pm)
                                                  //| (INR/AWG,0.0281,8/4/2015,9:39pm)
                                                  //| (INR/AZN,0.0165,8/4/2015,9:39pm)
                                                  //| (INR/BAM,0.0281,8/4/2015,9:39pm)
                                                  //| (INR/BBD,0.0313,8/4/2015,9:39pm)
                                                  //| (INR/BDT,1.2185,8/4/2015,9:39pm)
                                                  //| (INR/BGN FIXING,0.0282,8/4/2015,9:40pm)
                                                  //| (INR/BHD,0.0059,8/4/2015,9:39pm)
                                                  //| (INR/BIF,24.2635,8/4/2015,9:39pm)
                                                  //| (INR/BMD,0.0157,8/4/2015,9:39pm)
                                                  //| (INR/BND,0.0216,8/4/2015,9:39pm)
                                                  //| (INR/BOB,0.1080,8/4/2015,9:39pm)
                                                  //| (INR/BRL,0.0543,8/4/2015,9:40pm)
                                                  //| (INR/BSD,0.0157,8/4/2015,9:39pm)
                                                  //| (INR/BTC,0.0001,8/4/2015,9:39pm)
                                                  //| (INR/BTN,0.9997,8/4/2015,9:39pm)
                                                  //| (INR/BWP,0.1584,8/4/2015,9:39pm)
                                                  //| (INR/BYR,243.0664,8/4/2015,9:39pm)
                                                  //| (INR/BZD,0.0316,8/4/2015,9:39pm)





  
  


}