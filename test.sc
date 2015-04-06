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
  
  
  def findFirst[A] (as:Array[A], p:A => Boolean): Int = {
  	def loop(n: Int): Int = {
  		if (n >= as.length) -1
  		else if (p(as(n))) n
  		else loop(n + 1)
  		}
  		
  	loop(0)
  
  }                                               //> findFirst: [A](as: Array[A], p: A => Boolean)Int

findFirst(Array("one","two","three"),(x: String) => x == "five")
                                                  //> res1: Int = -1

	def isSorted[A] (as: Array[A], p:(A,A) => Boolean): Boolean = {
		if (as.length == 1) true
		else if (p(as(0),as(1))) false
		else (isSorted(as.tail,p))
	
	}                                         //> isSorted: [A](as: Array[A], p: (A, A) => Boolean)Boolean

isSorted(Array(1,5,5,4,7,3),(x: Int, y: Int) => x == y)
                                                  //> res2: Boolean = false

def curry[A,B,C] (f :(A,B) => C): A => (B => C) = {
	a => b => f(a,b)
}                                                 //> curry: [A, B, C](f: (A, B) => C)A => (B => C)

val f = (x: Double) => math.Pi / 2 - x            //> f  : Double => Double = <function1>

val cos = f andThen math.sin                      //> cos  : Double => Double = <function1>


import scala.xml._
import java.net._
import scala.io.Source
val temp = XML.loadString(Source.fromURL(new URL("https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quote%20where%20symbol%20in%20(%22%5EFTSE%22,%22%5EGDAXI%22,%22%5EFCHI%22,%22FTSEMIB.MI%22,%22%5EAEX%22,%22OMXC20.CO%22,%22%5EOMX%22,%22%5EOSEAX%22,%22%5ESSMI%22,%22%5EBFX%22)&diagnostics=true&env=store://datatables.org/alltableswithkeys")).mkString)
                                                  //> temp  : scala.xml.Elem = <query yahoo:lang="en-US" yahoo:created="2015-03-2
                                                  //| 8T23:48:20Z" yahoo:count="10" xmlns:yahoo="http://www.yahooapis.com/v1/base
                                                  //| .rng"><diagnostics><url execution-time="1" execution-stop-time="1" executio
                                                  //| n-start-time="0">http://www.datatables.org/yahoo/finance/quote/yahoo.financ
                                                  //| e.quote.xml</url><publiclyCallable>true</publiclyCallable><cache type="MEMC
                                                  //| ACHED" method="GET" execution-time="1" execution-stop-time="6" execution-st
                                                  //| art-time="5">5d1e1de680846a307c9874dc3d6878dc</cache><url execution-time="2
                                                  //| 2" execution-stop-time="29" execution-start-time="7">http://download.financ
                                                  //| e.yahoo.com/d/quotes.csv?f=aa2bb2b3b4cc1c3c4c6c8dd1d2ee1e7e8e9ghjkg1g3g4g5g
                                                  //| 6ii5j1j3j4j5j6k1k2k4k5ll1l2l3mm2m3m4m5m6m7m8nn4opp1p2p5p6qrr1r2r5r6r7ss1s7t
                                                  //| 1t7t8vv1v7ww1w4xy&amp;s=%5EFTSE,%5EGDAXI,%5EFCHI,FTSEMIB.MI,%5EAEX,OMXC20.C
                                                  //| O,%5EOMX,%5EOSEAX,%5ESSMI,%5EBFX</url><query params="{url=[http://download.
                                                  //| finance.yahoo.com/d/quo
                                                  //| Output exceeds cutoff limit.
val result = temp \ "results" \ "quote"           //> result  : scala.xml.NodeSeq = NodeSeq(<quote symbol="^FTSE" xmlns:yahoo="ht
                                                  //| tp://www.yahooapis.com/v1/base.rng"><AverageDailyVolume/><Change>-40.31</Ch
                                                  //| ange><DaysLow>6839.88</DaysLow><DaysHigh>6910.55</DaysHigh><YearLow>6072.70
                                                  //| </YearLow><YearHigh>7065.10</YearHigh><MarketCapitalization/><LastTradePric
                                                  //| eOnly>6855.02</LastTradePriceOnly><DaysRange>6839.88 - 6910.55</DaysRange><
                                                  //| Name>FTSE 100</Name><Symbol>^FTSE</Symbol><Volume>0</Volume><StockExchange>
                                                  //| FSI</StockExchange></quote>, <quote symbol="^GDAXI" xmlns:yahoo="http://www
                                                  //| .yahooapis.com/v1/base.rng"><AverageDailyVolume/><Change>+24.65</Change><Da
                                                  //| ysLow>11799.03</DaysLow><DaysHigh>11954.09</DaysHigh><YearLow>8354.97</Year
                                                  //| Low><YearHigh>12219.00</YearHigh><MarketCapitalization/><LastTradePriceOnly
                                                  //| >11868.33</LastTradePriceOnly><DaysRange>11799.03 - 11954.09</DaysRange><Na
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
}                                                 //> index  : scala.collection.immutable.Seq[test.Index] = List(Index(^FTSE,-40.
                                                  //| 31,6839.88,6910.55,6072.70,7065.10,6855.02,6839.88 - 6910.55,FTSE 100,0,FSI
                                                  //| ), Index(^GDAXI,+24.65,11799.03,11954.09,8354.97,12219.00,11868.33,11799.03
                                                  //|  - 11954.09,DAX,0,GER), Index(^FCHI,+27.71,5003.31,5048.37,3789.11,5106.04,
                                                  //| 5034.06,5003.31 - 5048.37,CAC 40,0,PAR), Index(FTSEMIB.MI,+83.96,22775.37,2
                                                  //| 3048.06,18079.00,23048.06,22984.23,22775.37 - 23048.06,FTSE MIB,0,MIL), Ind
                                                  //| ex(^AEX,-0.64,483.44,488.60,366.84,500.66,485.73,483.44 - 488.60,AEX,7263,A
                                                  //| MS), Index(OMXC20.CO,+45.00,901.35,957.35,669.12,957.35,945.62,901.35 - 957
                                                  //| .35,OMX COPENHAGEN,0,CPH), Index(^OMX,-13.04,1662.31,1681.62,1269.91,1691.0
                                                  //| 3,1662.56,1662.31 - 1681.62,OMXS30,0,STO), Index(^OSEAX,+2.99,658.76,664.06
                                                  //| ,571.50,706.45,661.76,658.76 - 664.06,OSLO EXCH ALL SHARE,0,OSL), Index(^SS
                                                  //| MI,+1.01,9072.64,9159.93,7852.80,9397.20,9083.52,9072.64 - 9159.93,SMI,0,VT
                                                  //| X), Index(^BFX,+19.00,3
                                                  //| Output exceeds cutoff limit.

//index.tail.tail.tail.head.Symbol

for ( i <- index) {
println(i.Symbol,i.Name, i.StockExchange,i.LastTradePriceOnly,i.Change,i.Volume,i.DaysLow,i.DaysHigh,i.YearLow,i.YearHigh)
                                                  //> (^FTSE,FTSE 100,FSI,6855.02,-40.31,0,6839.88,6910.55,6072.70,7065.10)
                                                  //| (^GDAXI,DAX,GER,11868.33,+24.65,0,11799.03,11954.09,8354.97,12219.00)
                                                  //| (^FCHI,CAC 40,PAR,5034.06,+27.71,0,5003.31,5048.37,3789.11,5106.04)
                                                  //| (FTSEMIB.MI,FTSE MIB,MIL,22984.23,+83.96,0,22775.37,23048.06,18079.00,23048
                                                  //| .06)
                                                  //| (^AEX,AEX,AMS,485.73,-0.64,7263,483.44,488.60,366.84,500.66)
                                                  //| (OMXC20.CO,OMX COPENHAGEN,CPH,945.62,+45.00,0,901.35,957.35,669.12,957.35)
                                                  //| (^OMX,OMXS30,STO,1662.56,-13.04,0,1662.31,1681.62,1269.91,1691.03)
                                                  //| (^OSEAX,OSLO EXCH ALL SHARE,OSL,661.76,+2.99,0,658.76,664.06,571.50,706.45)
                                                  //| 
                                                  //| (^SSMI,SMI,VTX,9083.52,+1.01,0,9072.64,9159.93,7852.80,9397.20)
                                                  //| (^BFX,EURONEXT BEL-20,BRU,3715.00,+19.00,3,3709.00,3720.00,2809.31,3786.10)
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
                                                  //| 3-28T23:48:21Z" yahoo:count="169" xmlns:yahoo="http://www.yahooapis.com/v1/
                                                  //| base.rng"><results><rate id="INRAED"><Name>INR/AED</Name><Rate>0.0587</Rate
                                                  //| ><Date>3/28/2015</Date><Time>12:19pm</Time><Ask>0.0588</Ask><Bid>0.0587</Bi
                                                  //| d></rate><rate id="INRAFN"><Name>INR/AFN</Name><Rate>0.9248</Rate><Date>3/2
                                                  //| 8/2015</Date><Time>12:19pm</Time><Ask>0.9266</Ask><Bid>0.9248</Bid></rate><
                                                  //| rate id="INRALL"><Name>INR/ALL</Name><Rate>2.0680</Rate><Date>3/28/2015</Da
                                                  //| te><Time>12:19pm</Time><Ask>2.1050</Ask><Bid>2.0680</Bid></rate><rate id="I
                                                  //| NRAMD"><Name>INR/AMD</Name><Rate>7.5425</Rate><Date>3/28/2015</Date><Time>1
                                                  //| 2:19pm</Time><Ask>7.5436</Ask><Bid>7.5425</Bid></rate><rate id="INRANG"><Na
                                                  //| me>INR/ANG</Name><Rate>0.0286</Rate><Date>3/28/2015</Date><Time>12:19pm</Ti
                                                  //| me><Ask>0.0286</Ask><Bid>0.0286</Bid></rate><rate id="INRAOA"><Name>INR/AOA
                                                  //| </Name><Rate>1.7296</Ra
                                                  //| Output exceeds cutoff limit.


//val ccyurl = "http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.xchange%20where%20pair%20in%20(%22USDAUD%22,%22USDEUR%22,%22USDCAD%22,%22USDDKK%22,%22USDHKD%22,%22USDINR%22,%22USDJPY%22,%22USDKRW%22,%22USDBRL%22,%22USDMXN%22,%22USDCLP%22,%22USDPEN%22,%22USDCOP%22,%22USDNZD%22,%22USDNOK%22,%22USDSGD%22,%22USDSEK%22,%22USDCHF%22,%22USDGBP%22,%22USDUSD%22,%22USDZAR%22,%22USDTRY%22,%22USDCNY%22,%22USDTHB%22,%22USDIDR%22,%22USDILS%22,%22USDRUB%22)&env=store://datatables.org/alltableswithkeys"

//val xchange = XML.loadString(Source.fromURL(new URL("http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.xchange%20where%20pair%20in%20(%22USDAUD%22,%22USDEUR%22,%22USDCAD%22,%22USDDKK%22,%22USDHKD%22,%22USDINR%22,%22USDJPY%22,%22USDKRW%22,%22USDBRL%22,%22USDMXN%22,%22USDCLP%22,%22USDPEN%22,%22USDCOP%22,%22USDNZD%22,%22USDNOK%22,%22USDSGD%22,%22USDSEK%22,%22USDCHF%22,%22USDGBP%22,%22USDUSD%22,%22USDZAR%22,%22USDTRY%22,%22USDCNY%22,%22USDTHB%22,%22USDIDR%22,%22USDILS%22,%22USDRUB%22)&env=store://datatables.org/alltableswithkeys")).mkString)

(xchange \ "results" \ "rate").foreach{
n =>
println((n \ "Name").text, (n \ "Rate").text)
}                                                 //> (INR/AED,0.0587)
                                                  //| (INR/AFN,0.9248)
                                                  //| (INR/ALL,2.0680)
                                                  //| (INR/AMD,7.5425)
                                                  //| (INR/ANG,0.0286)
                                                  //| (INR/AOA,1.7296)
                                                  //| (INR/ARS,0.1409)
                                                  //| (INR/AUD,0.0206)
                                                  //| (INR/AWG,0.0286)
                                                  //| (INR/AZN,0.0168)
                                                  //| (INR/BAM,0.0287)
                                                  //| (INR/BBD,0.0320)
                                                  //| (INR/BDT,1.2455)
                                                  //| (INR/BGN FIXING,0.0287)
                                                  //| (INR/BHD,0.0060)
                                                  //| (INR/BIF,24.9035)
                                                  //| (INR/BMD,0.0160)
                                                  //| (INR/BND,0.0219)
                                                  //| (INR/BOB,0.1105)
                                                  //| (INR/BRL,0.0519)
                                                  //| (INR/BSD,0.0160)
                                                  //| (INR/BTC,0.0001)
                                                  //| (INR/BTN,0.9991)
                                                  //| (INR/BWP,0.1579)
                                                  //| (INR/BYR,233.4900)
                                                  //| (INR/BZD,0.0319)
                                                  //| (INR/CAD,0.0202)
                                                  //| (INR/CDF,14.7770)
                                                  //| (INR/CHF,0.0154)
                                                  //| (INR/CLF,0.0004)
                                                  //| (INR/CLP,9.9648)
                                                  //| (INR/CNY,0.0993)
                                                  //| (INR/COP,40.9167)
                                                  //| (INR/CRC,8.5146)
                                                  //| (N/A,N/A)
                                                  //| (INR/CUP,0.0160)
                                                  //| (INR/CVE,1.6076)
                                                  //| (INR/CZK,0.4035)
                                                  //| (INR/DJF,2.8430)
                                                  //| (INR/DKK,0.1097)
                                                  //| (INR/DOP,0.7147)
                                                  //| (INR/DZD,1.5535)
                                                  //| (N/A,N/A)
                                                  //| (INR/EGP,0.1220)
                                                  //| (INR/ERN,0.2443)
                                                  //| (INR/ET
                                                  //| Output exceeds cutoff limit.




  
  


}