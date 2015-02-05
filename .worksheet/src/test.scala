object test {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(57); 
  println("Welcome to the Scala worksheet");$skip(188); 
  
  def fibo(n :Int): Int = {
  	def fibo_tailrec (n : Int, a: Int, b : Int ): Int = n match {
  		case 0 => a
  		case _ => fibo_tailrec(n-1, b , a + b )
  	}
  	fibo_tailrec(n,0,1)
  };System.out.println("""fibo: (n: Int)Int""");$skip(23); val res$0 = 
  
  
  fibo(20000005);System.out.println("""res0: Int = """ + $show(res$0));$skip(195); 
  
  
  def findFirst[A] (as:Array[A], p:A => Boolean): Int = {
  	def loop(n: Int): Int = {
  		if (n >= as.length) -1
  		else if (p(as(n))) n
  		else loop(n + 1)
  		}
  		
  	loop(0)
  
  };System.out.println("""findFirst: [A](as: Array[A], p: A => Boolean)Int""");$skip(66); val res$1 = 

findFirst(Array("one","two","three"),(x: String) => x == "five");System.out.println("""res1: Int = """ + $show(res$1));$skip(160); 

	def isSorted[A] (as: Array[A], p:(A,A) => Boolean): Boolean = {
		if (as.length == 1) true
		else if (p(as(0),as(1))) false
		else (isSorted(as.tail,p))
	
	};System.out.println("""isSorted: [A](as: Array[A], p: (A, A) => Boolean)Boolean""");$skip(57); val res$2 = 

isSorted(Array(1,5,5,4,7,3),(x: Int, y: Int) => x == y);System.out.println("""res2: Boolean = """ + $show(res$2));$skip(73); 

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
};$skip(1271); 
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


val xchange = XML.loadString(Source.fromURL(new URL("http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.xchange%20where%20pair%20in%20(%22USDAUD%22,%22USDEUR%22,%22USDCAD%22,%22USDDKK%22,%22USDHKD%22,%22USDINR%22,%22USDJPY%22,%22USDKRW%22,%22USDBRL%22,%22USDMXN%22,%22USDCLP%22,%22USDPEN%22,%22USDCOP%22,%22USDNZD%22,%22USDNOK%22,%22USDSGD%22,%22USDSEK%22,%22USDCHF%22,%22USDGBP%22,%22USDUSD%22,%22USDZAR%22,%22USDTRY%22,%22USDCNY%22,%22USDTHB%22,%22USDIDR%22,%22USDILS%22,%22USDRUB%22)&env=store://datatables.org/alltableswithkeys")).mkString);System.out.println("""xchange  : scala.xml.Elem = """ + $show(xchange ));$skip(94); 

(xchange \ "results" \ "rate").foreach{
n =>
println((n \ "Name").text, (n \ "Rate").text)
}


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


import java.net.URL;$skip(765); 
 
val site = new URL("http://markets.ft.com/research/Markets/Tearsheets/Summary?s=4038993");System.out.println("""site  : java.net.URL = """ + $show(site ));$skip(33); 
  
val content = HTML.load(site);System.out.println("""content  : scala.xml.Node = """ + $show(content ));$skip(958); val res$3 = 
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

content \\ "table";System.out.println("""res3: scala.xml.NodeSeq = """ + $show(res$3));$skip(141); val res$4 = 

for {
	 h <- content \\ "table"
	if (h \ "@class").text == "horizontalTable col1of3"
		node <- h \ "tr" \ "th" \ "td"
	} yield {node.text };System.out.println("""res4: scala.collection.immutable.Seq[String] = """ + $show(res$4));$skip(18); 

println(content)}


  
  


}
