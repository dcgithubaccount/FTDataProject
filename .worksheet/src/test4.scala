object test4 {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(58); 
  println("Welcome to the Scala worksheet")
  
	import scala.xml._
	import java.net._
	import scala.io.Source
  import java.util.Calendar
  import scala.util.{Try, Success, Failure}
  import com.DC.FTDataParser.XMLPersister._


case class IndProfile (
	val Sector :String,
	val Industry : String,
	val IndustryId :Int,
	val YahooURL :String
);$skip(588); 

val lines = (Source.fromFile("/Volumes/MyData/SharesProject/YahooIndustry/YahooIndustry.csv").getLines map {x => x.split(",") } map {x =>
	val Sector = x(0)
	val industry = x(1)
	val IndustryId = x(2)
	val YahooURL = x(3)
	IndProfile(Sector,industry,IndustryId.toInt,YahooURL)

}).toList
case class companyData (
 val Symbol :String,
 val Name :String ,
 val Industry :String,
 val Sector :String,
 val firstSplit :String,
 val exchange :String
);System.out.println("""lines  : List[test4.IndProfile] = """ + $show(lines ));$skip(335); 

def exchangeFinder(ex :String) :(String,String) = {
	val index = ex.indexOf(".")
	if ( index != -1)  (ex.substring(0,index),ex.substring(index+1,ex.length))
	else (ex,"US")
};System.out.println("""exchangeFinder: (ex: String)(String, String)""");$skip(1027); 

def companyExtractor (ind :IndProfile) :List[companyData] = {

	
	
	val front = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.industry%20where%20id%3D"
	val back = "&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys"
  val finalString = front + "%22" + ind.IndustryId + "%22" + back
  //val industry = XML.loadString(Source.fromURL(new URL(finalString)).mkString)
  
  val abc = getXML(finalString,6) /*match {
  		case Success(node) => node
  		case Failure(ex) => println(s"This is a Failure")
  	}*/
  	
  val industry = abc match {
  	case Success(node) => node
  }
  	
  
 
  
  (industry \ "results" \"industry" \ "company" map {x =>
  	val Symbol = (x \\ "@symbol").text
  	val exchangedata = exchangeFinder(Symbol)
  	val Name   = (x \\ "@name").text
  	val Industry = ind.Industry
  	val Sector   = ind.Sector
  	val firstSplit = exchangedata._1
  	val exchange = exchangedata._2
  	
		companyData(Symbol,Name,Industry,Sector,firstSplit,exchange)
  }).toList
  
};System.out.println("""companyExtractor: (ind: test4.IndProfile)List[test4.companyData]""");$skip(33); val res$0 = 
Calendar.getInstance().getTime();System.out.println("""res0: java.util.Date = """ + $show(res$0));$skip(90); 

val cdata :List[companyData] = (for ( l <- lines ) yield { companyExtractor(l)}) flatten;System.out.println("""cdata  : List[test4.companyData] = """ + $show(cdata ));$skip(34); val res$1 = 

Calendar.getInstance().getTime();System.out.println("""res1: java.util.Date = """ + $show(res$1));$skip(44); val res$2 = 
cdata.groupBy(_.exchange).mapValues(_.size);System.out.println("""res2: scala.collection.immutable.Map[String,Int] = """ + $show(res$2));$skip(55); val res$3 = 

cdata map {x => if(x.firstSplit =="RDSA") println(x)};System.out.println("""res3: List[Unit] = """ + $show(res$3));$skip(68); 
val uniqdata :List[String] = (cdata map {x => x.exchange}).distinct;System.out.println("""uniqdata  : List[String] = """ + $show(uniqdata ))}

//Source.fromFile("/Volumes/MyData/SharesProject/YahooIndustry/133.csv").getLines map {x => x.split(",") }

 
                                                  
  
  
}
