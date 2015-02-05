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
                                                  //> temp  : scala.xml.Elem = <query yahoo:lang="en-US" yahoo:created="2015-02-0
                                                  //| 2T00:42:53Z" yahoo:count="10" xmlns:yahoo="http://www.yahooapis.com/v1/base
                                                  //| .rng"><diagnostics><url execution-time="1" execution-stop-time="2" executio
                                                  //| n-start-time="1">http://www.datatables.org/yahoo/finance/quote/yahoo.financ
                                                  //| e.quote.xml</url><publiclyCallable>true</publiclyCallable><cache type="MEMC
                                                  //| ACHED" method="GET" execution-time="1" execution-stop-time="6" execution-st
                                                  //| art-time="5">5d1e1de680846a307c9874dc3d6878dc</cache><url execution-time="6
                                                  //| " execution-stop-time="12" execution-start-time="6">http://download.finance
                                                  //| .yahoo.com/d/quotes.csv?f=aa2bb2b3b4cc1c3c4c6c8dd1d2ee1e7e8e9ghjkg1g3g4g5g6
                                                  //| ii5j1j3j4j5j6k1k2k4k5ll1l2l3mm2m3m4m5m6m7m8nn4opp1p2p5p6qrr1r2r5r6r7ss1s7t1
                                                  //| t7t8vv1v7ww1w4xy&amp;s=%5EFTSE,%5EGDAXI,%5EFCHI,FTSEMIB.MI,%5EAEX,OMXC20.CO
                                                  //| ,%5EOMX,%5EOSEAX,%5ESSMI,%5EBFX</url><query params="{url=[http://download.f
                                                  //| inance.yahoo.com/d/quot
                                                  //| Output exceeds cutoff limit.
val result = temp \ "results" \ "quote"           //> result  : scala.xml.NodeSeq = NodeSeq(<quote symbol="^FTSE" xmlns:yahoo="ht
                                                  //| tp://www.yahooapis.com/v1/base.rng"><AverageDailyVolume>0</AverageDailyVolu
                                                  //| me><Change>-61.20</Change><DaysLow>6749.40</DaysLow><DaysHigh>6843.98</Days
                                                  //| High><YearLow>6072.68</YearLow><YearHigh>6904.86</YearHigh><MarketCapitaliz
                                                  //| ation/><LastTradePriceOnly>6749.40</LastTradePriceOnly><DaysRange>6749.40 -
                                                  //|  6843.98</DaysRange><Name>FTSE 100</Name><Symbol>^FTSE</Symbol><Volume>0</V
                                                  //| olume><StockExchange>FSI</StockExchange></quote>, <quote symbol="^GDAXI" xm
                                                  //| lns:yahoo="http://www.yahooapis.com/v1/base.rng"><AverageDailyVolume>0</Ave
                                                  //| rageDailyVolume><Change>-43.55</Change><DaysLow>10642.59</DaysLow><DaysHigh
                                                  //| >10804.04</DaysHigh><YearLow>8354.97</YearLow><YearHigh>10810.60</YearHigh>
                                                  //| <MarketCapitalization/><LastTradePriceOnly>10694.32</LastTradePriceOnly><Da
                                                  //| ysRange>10642.59 - 10804.04</DaysRange><Name>DAX</Name><Symbol>^GDAXI</Symb
                                                  //| ol><Volume>0</Volume><S
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
}                                                 //> index  : scala.collection.immutable.Seq[test.Index] = List(Index(^FTSE,-61.
                                                  //| 20,6749.40,6843.98,6072.68,6904.86,6749.40,6749.40 - 6843.98,FTSE 100,0,FSI
                                                  //| ), Index(^GDAXI,-43.55,10642.59,10804.04,8354.97,10810.60,10694.32,10642.59
                                                  //|  - 10804.04,DAX,0,XETRA), Index(^FCHI,-27.18,4583.68,4660.71,3789.11,4679.2
                                                  //| 6,4604.25,4583.68 - 4660.71,CAC 40,0,Paris), Index(FTSEMIB.MI,-90.340,20411
                                                  //| .020,20723.221,18079.000,22216.600,20503.381,20411.020 - 20723.221,FTSE MIB
                                                  //| ,0,Milan), Index(^AEX,-2.08,448.90,454.65,366.84,459.44,450.39,448.90 - 454
                                                  //| .65,AEX,7263,Amsterdam), Index(OMXC20.CO,-4.383,810.938,824.931,624.165,773
                                                  //| .539,810.938,810.938 - 824.931,OMX COPENHAGEN,0,Copenhagen), Index(^OMX,+3.
                                                  //| 234,1572.551,1582.104,1269.91,1478.93,1573.622,1572.551 - 1582.104,OMXS30,0
                                                  //| ,Stockholm), Index(^OSEAX,-0.45,633.00,640.21,571.50,706.45,635.71,633.00 -
                                                  //|  640.21,OSLO EXCH ALL SHA,0,Oslo), Index(^SSMI,-50.21,8385.13,8493.05,7852.
                                                  //| 80,9291.00,8385.13,8385
                                                  //| Output exceeds cutoff limit.

//index.tail.tail.tail.head.Symbol

for ( i <- index) {
println(i.Symbol,i.Name, i.StockExchange,i.LastTradePriceOnly,i.Change,i.Volume,i.DaysLow,i.DaysHigh,i.YearLow,i.YearHigh)
                                                  //> (^FTSE,FTSE 100,FSI,6749.40,-61.20,0,6749.40,6843.98,6072.68,6904.86)
                                                  //| (^GDAXI,DAX,XETRA,10694.32,-43.55,0,10642.59,10804.04,8354.97,10810.60)
                                                  //| (^FCHI,CAC 40,Paris,4604.25,-27.18,0,4583.68,4660.71,3789.11,4679.26)
                                                  //| (FTSEMIB.MI,FTSE MIB,Milan,20503.381,-90.340,0,20411.020,20723.221,18079.00
                                                  //| 0,22216.600)
                                                  //| (^AEX,AEX,Amsterdam,450.39,-2.08,7263,448.90,454.65,366.84,459.44)
                                                  //| (OMXC20.CO,OMX COPENHAGEN,Copenhagen,810.938,-4.383,0,810.938,824.931,624.1
                                                  //| 65,773.539)
                                                  //| (^OMX,OMXS30,Stockholm,1573.622,+3.234,0,1572.551,1582.104,1269.91,1478.93)
                                                  //| 
                                                  //| (^OSEAX,OSLO EXCH ALL SHA,Oslo,635.71,-0.45,0,633.00,640.21,571.50,706.45)
                                                  //| (^SSMI,SMI,VTX,8385.13,-50.21,0,8385.13,8493.05,7852.80,9291.00)
                                                  //| (^BFX,EURONEXT BEL-20,Brussels,3529.00,-2.00,3,3524.00,3560.00,2809.31,3565
                                                  //| .83)
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


val xchange = XML.loadString(Source.fromURL(new URL("http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.xchange%20where%20pair%20in%20(%22USDAUD%22,%22USDEUR%22,%22USDCAD%22,%22USDDKK%22,%22USDHKD%22,%22USDINR%22,%22USDJPY%22,%22USDKRW%22,%22USDBRL%22,%22USDMXN%22,%22USDCLP%22,%22USDPEN%22,%22USDCOP%22,%22USDNZD%22,%22USDNOK%22,%22USDSGD%22,%22USDSEK%22,%22USDCHF%22,%22USDGBP%22,%22USDUSD%22,%22USDZAR%22,%22USDTRY%22,%22USDCNY%22,%22USDTHB%22,%22USDIDR%22,%22USDILS%22,%22USDRUB%22)&env=store://datatables.org/alltableswithkeys")).mkString)
                                                  //> xchange  : scala.xml.Elem = <query yahoo:lang="en-US" yahoo:created="2015-0
                                                  //| 2-02T00:42:53Z" yahoo:count="27" xmlns:yahoo="http://www.yahooapis.com/v1/b
                                                  //| ase.rng"><results><rate id="USDAUD"><Name>USD to AUD</Name><Rate>1.2856</Ra
                                                  //| te><Date>2/2/2015</Date><Time>7:42pm</Time><Ask>1.2858</Ask><Bid>1.2854</Bi
                                                  //| d></rate><rate id="USDEUR"><Name>USD to EUR</Name><Rate>0.8842</Rate><Date>
                                                  //| 2/2/2015</Date><Time>7:42pm</Time><Ask>0.8843</Ask><Bid>0.884</Bid></rate><
                                                  //| rate id="USDCAD"><Name>USD to CAD</Name><Rate>1.2703</Rate><Date>2/2/2015</
                                                  //| Date><Time>7:42pm</Time><Ask>1.2704</Ask><Bid>1.2702</Bid></rate><rate id="
                                                  //| USDDKK"><Name>USD to DKK</Name><Rate>6.5837</Rate><Date>2/2/2015</Date><Tim
                                                  //| e>7:42pm</Time><Ask>6.5868</Ask><Bid>6.5806</Bid></rate><rate id="USDHKD"><
                                                  //| Name>USD to HKD</Name><Rate>7.7526</Rate><Date>2/2/2015</Date><Time>7:42pm<
                                                  //| /Time><Ask>7.7532</Ask><Bid>7.7519</Bid></rate><rate id="USDINR"><Name>USD 
                                                  //| to INR</Name><Rate>62.0
                                                  //| Output exceeds cutoff limit.

(xchange \ "results" \ "rate").foreach{
n =>
println((n \ "Name").text, (n \ "Rate").text)
}                                                 //> (USD to AUD,1.2856)
                                                  //| (USD to EUR,0.8842)
                                                  //| (USD to CAD,1.2703)
                                                  //| (USD to DKK,6.5837)
                                                  //| (USD to HKD,7.7526)
                                                  //| (USD to INR,62.085)
                                                  //| (USD to JPY,117.3445)
                                                  //| (USD to KRW,1099.40)
                                                  //| (USD to BRL,2.6822)
                                                  //| (USD to MXN,14.9631)
                                                  //| (USD to CLP,634.155)
                                                  //| (USD to PEN,3.063)
                                                  //| (USD to COP,2440.00)
                                                  //| (USD to NZD,1.3767)
                                                  //| (USD to NOK,7.7266)
                                                  //| (USD to SGD,1.3528)
                                                  //| (USD to SEK,8.2663)
                                                  //| (USD to CHF,0.9211)
                                                  //| (USD to GBP,0.6632)
                                                  //| (USD to USD,1.00)
                                                  //| (USD to ZAR,11.6299)
                                                  //| (USD to TRY,2.4436)
                                                  //| (USD to CNY,6.2386)
                                                  //| (USD to THB,32.713)
                                                  //| (USD to IDR,12730.00)
                                                  //| (USD to ILS,3.9367)
                                                  //| (USD to RUB,69.6005)


import java.net.URL
import scala.xml.XML
import org.xml.sax.InputSource
import scala.xml.parsing.NoBindingFactoryAdapter
import org.ccil.cowan.tagsoup.jaxp.SAXFactoryImpl
import java.net.HttpURLConnection
import scala.xml.Node
 
object HTML {
  lazy val adapter = new NoBindingFactoryAdapter
  lazy val parser = (new SAXFactoryImpl).newSAXParser
 
  def load(url: URL, headers: Map[String, String] = Map.empty): Node = {
    val conn = url.openConnection().asInstanceOf[HttpURLConnection]
    for ((k, v) <- headers)
      conn.setRequestProperty(k, v)
    val source = new InputSource(conn.getInputStream)
    adapter.loadXML(source, parser)
  }
}


import java.net.URL
 
val site = new URL("http://markets.ft.com/research/Markets/Tearsheets/Summary?s=4038993")
                                                  //> site  : java.net.URL = http://markets.ft.com/research/Markets/Tearsheets/Su
                                                  //| mmary?s=4038993
  
val content = HTML.load(site)                     //> content  : scala.xml.Node = <html lang="en"><head><meta content="IE=edge,ch
                                                  //| rome=1" http-equiv="X-UA-Compatible"/><meta charset="UTF-8"/><title>CNX 100
                                                  //|  Index-NSE chart, prices and performance - FT.com</title><meta content="CNX
                                                  //|  100 Index-NSE index chart, prices and performance, plus recent news and an
                                                  //| alysis." name="description"/><meta content="CNX 100 Index-NSE index chart, 
                                                  //| prices and performance, plus recent news and analysis." name="keywords"/><l
                                                  //| ink media="all" href="http://s1.ft-static.com/m/style/2d49d277/bundles/core
                                                  //| .css" rel="stylesheet"/><link media="all" href="http://navigation.webservic
                                                  //| es.ft.com/v1/navigation/ft/css/style.min.css" rel="stylesheet"/><link media
                                                  //| ="all" href="http://s1.ft-static.com/m/style/5c37627a/bundles/nonArticle.cs
                                                  //| s" rel="stylesheet"/><link rel="stylesheet" type="text/css" href="http://cd
                                                  //| n.markets.ft.com/Research/ResourceManager/ndE73rxj7N1HOXjf8lLC-A2/u/c/c/0/v
                                                  //| TX341L1feEjO1uCAk2bUQ2/
                                                  //| Output exceeds cutoff limit.
/*
<div class="wsodModuleContent"><h1 class="contains">
<div class="emptyExchangeLogo exchangeLogo"></div>
<span class="formatIssueName">CNX 100 Index-NSE</span>
<span class="formatIssueSymbol">NNX:NSI</span></h1>
<div class="wsodModule contains tearsheetOverviewComponent" data-oda-name="Tearsheet overview">
<div class="contains wsodModuleContent"><table><tbody><tr><td class="text first"><span>8,734.6</span></td>
<td><span><div class="wsod-icon wsod-icon-v wsod-icon-quote-positive-vertical pos color  colorArrow"><span class="pos color ">29.60 / 0.34%</span></div></span></td>
<td>256.79k</td>
<td class="last">
<div class="wsod-icon wsod-icon-v wsod-icon-quote-positive-vertical pos color  colorArrow">40.37%</div></td></tr><tr><th class="text first">Latest price in INR</th><th><span class="spacer" />Today's change</th><th>Shares traded</th><th class="last"><span class="spacer" />1 year change</th></tr></tbody></table></div>
*/

content \\ "table"                                //> res3: scala.xml.NodeSeq = NodeSeq(<table><tbody><tr><td class="text first" 
                                                  //| rowspan="1" colspan="1"><span>8,794.95</span></td><td rowspan="1" colspan="
                                                  //| 1"><span/><div class="wsod-icon wsod-icon-v wsod-icon-quote-negative-vertic
                                                  //| al neg color colorArrow"><span class="neg color">129.35 / 1.45%</span></div
                                                  //| ></td><td rowspan="1" colspan="1">386.92m</td><td class="last" rowspan="1" 
                                                  //| colspan="1"><div class="wsod-icon wsod-icon-v wsod-icon-quote-positive-vert
                                                  //| ical pos color colorArrow">47.99%</div></td></tr><tr><th class="text first"
                                                  //|  rowspan="1" colspan="1">Latest price in INR</th><th rowspan="1" colspan="1
                                                  //| "><span class="spacer"/>Today's change</th><th rowspan="1" colspan="1">Shar
                                                  //| es traded</th><th class="last" rowspan="1" colspan="1"><span class="spacer"
                                                  //| />1 year change</th></tr></tbody></table>, <table class="horizontalTable co
                                                  //| l1of3"><tbody><tr class="first"><th rowspan="1" colspan="1">Open</th><td ro
                                                  //| wspan="1" colspan="1">8
                                                  //| Output exceeds cutoff limit.

for {
	 h <- content \\ "table"
	if (h \ "@class").text == "horizontalTable col1of3"
		node <- h \ "tr" \ "th" \ "td"
	} yield {node.text }                      //> res4: scala.collection.immutable.Seq[String] = List()

println(content)                                  //> <html lang="en"><head><meta content="IE=edge,chrome=1" http-equiv="X-UA-Com
                                                  //| patible"/><meta charset="UTF-8"/><title>CNX 100 Index-NSE chart, prices and
                                                  //|  performance - FT.com</title><meta content="CNX 100 Index-NSE index chart, 
                                                  //| prices and performance, plus recent news and analysis." name="description"/
                                                  //| ><meta content="CNX 100 Index-NSE index chart, prices and performance, plus
                                                  //|  recent news and analysis." name="keywords"/><link media="all" href="http:/
                                                  //| /s1.ft-static.com/m/style/2d49d277/bundles/core.css" rel="stylesheet"/><lin
                                                  //| k media="all" href="http://navigation.webservices.ft.com/v1/navigation/ft/c
                                                  //| ss/style.min.css" rel="stylesheet"/><link media="all" href="http://s1.ft-st
                                                  //| atic.com/m/style/5c37627a/bundles/nonArticle.css" rel="stylesheet"/><link r
                                                  //| el="stylesheet" type="text/css" href="http://cdn.markets.ft.com/Research/Re
                                                  //| sourceManager/ndE73rxj7N1HOXjf8lLC-A2/u/c/c/0/vTX341L1feEjO1uCAk2bUQ2/$wrXM
                                                  //| w9rb3N3et7i5uru8zr7Sx7_
                                                  //| Output exceeds cutoff limit.


  
  


}