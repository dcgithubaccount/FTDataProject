object test4 {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
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
)

val lines = (Source.fromFile("/Volumes/MyData/SharesProject/YahooIndustry/YahooIndustry.csv").getLines map {x => x.split(",") } map {x =>
	val Sector = x(0)
	val industry = x(1)
	val IndustryId = x(2)
	val YahooURL = x(3)
	IndProfile(Sector,industry,IndustryId.toInt,YahooURL)

}).toList                                         //> lines  : List[test4.IndProfile] = List(IndProfile(Basic Material,Agricultura
                                                  //| l Chemicals,112,http://biz.yahoo.com/ic/112.html), IndProfile(Basic Material
                                                  //| ,Aluminum,132,http://biz.yahoo.com/ic/132.html), IndProfile(Basic Material,C
                                                  //| hemicals - Major Diversified,110,http://biz.yahoo.com/ic/110.html), IndProfi
                                                  //| le(Basic Material,Copper,131,http://biz.yahoo.com/p/131conameu.html), IndPro
                                                  //| file(Basic Material,Gold,134,http://biz.yahoo.com/ic/134.html), IndProfile(B
                                                  //| asic Material,Independent Oil & Gas,121,http://biz.yahoo.com/ic/121.html), I
                                                  //| ndProfile(Basic Material,Industrial Metals & Minerals,133,http://biz.yahoo.c
                                                  //| om/ic/133.html), IndProfile(Basic Material,Major Integrated Oil & Gas,120,ht
                                                  //| tp://biz.yahoo.com/ic/120.html), IndProfile(Basic Material,Nonmetallic Miner
                                                  //| al Mining,136,http://biz.yahoo.com/p/136conameu.html), IndProfile(Basic Mate
                                                  //| rial,Oil & Gas Drilling & Exploration,123,http://biz.yahoo.com/ic/123.html),
                                                  //|  IndProfile(Basic Materi
                                                  //| Output exceeds cutoff limit.
case class companyData (
 val Symbol :String,
 val Name :String ,
 val Industry :String,
 val Sector :String,
 val firstSplit :String,
 val exchange :String
)

def exchangeFinder(ex :String) :(String,String) = {
	val index = ex.indexOf(".")
	if ( index != -1)  (ex.substring(0,index),ex.substring(index+1,ex.length))
	else (ex,"US")
}                                                 //> exchangeFinder: (ex: String)(String, String)

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
  
}                                                 //> companyExtractor: (ind: test4.IndProfile)List[test4.companyData]
Calendar.getInstance().getTime()                  //> res0: java.util.Date = Wed Nov 04 12:49:29 GMT 2015

val cdata :List[companyData] = (for ( l <- lines ) yield { companyExtractor(l)}) flatten
                                                  //> 12:49:29.664 [main] INFO  com.DC.FTDataParser.XMLPersister$ - Entering XML 
                                                  //| Persister
                                                  //| cdata  : List[test4.companyData] = List(companyData(ADARSHPL.BO,Adarsh
                                                  //| Plant Protect Ltd,Agricultural Chemicals,Basic Material,ADARSHPL,BO), compa
                                                  //| nyData(AFPO.L,African
                                                  //| Potash Ltd,Agricultural Chemicals,Basic Material,AFPO,L), companyData(AGRIT
                                                  //| ECH.NS,Agri-Tech
                                                  //| (India) Ltd,Agricultural Chemicals,Basic Material,AGRITECH,NS), companyData
                                                  //| (AGU.DE,Agrium
                                                  //| Inc,Agricultural Chemicals,Basic Material,AGU,DE), companyData(AGU.TO,Agriu
                                                  //| m
                                                  //| Inc,Agricultural Chemicals,Basic Material,AGU,TO), companyData(AGU,Agrium
                                                  //| Inc.,Agricultural Chemicals,Basic Material,AGU,US), companyData(AIMCO.BO,Ai
                                                  //| mco
                                                  //| Pesticides Ltd,Agricultural Chemicals,Basic Material,AIMCO,BO), companyData
                                                  //| (AVD,American
                                                  //| Vanguard Corp.,Agricultural Chemicals,Basic Material,AVD,US), companyData(R
                                                  //| KDA,Arcadia
                                                  //| Biosciences, Inc.,Agricultural Chemicals,Basic Material,
                                                  //| Output exceeds cutoff limit.

Calendar.getInstance().getTime()                  //> res1: java.util.Date = Wed Nov 04 12:50:46 GMT 2015
cdata.groupBy(_.exchange).mapValues(_.size)       //> res2: scala.collection.immutable.Map[String,Int] = Map(AT -> 203, NS -> 134
                                                  //| 0, TW -> 738, NZ -> 144, AX -> 1478, US -> 6931, SI -> 554, HK -> 1274, SA 
                                                  //| -> 603, MI -> 361, BO -> 3054, OL -> 215, ST -> 378, V -> 1090, L -> 1645, 
                                                  //| BA -> 143, CO -> 92, BR -> 148, PA -> 813, MX -> 271, TO -> 808, DE -> 5844
                                                  //| )

cdata map {x => if(x.firstSplit =="RDSA") println(x)}
                                                  //> companyData(RDSA.L,Royal
                                                  //| Dutch Shell PLC,Major Integrated Oil & Gas,Basic Material,RDSA,L)
                                                  //| companyData(RDSA.MX,Royal
                                                  //| Dutch Shell PLC,Major Integrated Oil & Gas,Basic Material,RDSA,MX)
                                                  //| res3: List[Unit] = List((), (), (), (), (), (), (), (), (), (), (), (), (),
                                                  //|  (), (), (), (), (), (), (), (), (), (), (), (), (), (), (), (), (), (), ()
                                                  //| , (), (), (), (), (), (), (), (), (), (), (), (), (), (), (), (), (), (), (
                                                  //| ), (), (), (), (), (), (), (), (), (), (), (), (), (), (), (), (), (), (), 
                                                  //| (), (), (), (), (), (), (), (), (), (), (), (), (), (), (), (), (), (), (),
                                                  //|  (), (), (), (), (), (), (), (), (), (), (), (), (), (), (), (), (), (), ()
                                                  //| , (), (), (), (), (), (), (), (), (), (), (), (), (), (), (), (), (), (), (
                                                  //| ), (), (), (), (), (), (), (), (), (), (), (), (), (), (), (), (), (), (), 
                                                  //| (), (), (), (), (), (), (), (), (), (), (), (), (), (), (), (), (), (), (),
                                                  //|  (), (), (), (), (), (), (), (), (), (), (), (), (), (), (), (
                                                  //| Output exceeds cutoff limit.
val uniqdata :List[String] = (cdata map {x => x.exchange}).distinct
                                                  //> uniqdata  : List[String] = List(BO, L, NS, DE, TO, US, HK, BA, MX, PA, SA, 
                                                  //| V, AX, MI, SI, BR, TW, AT, OL, ST, NZ, CO)

//Source.fromFile("/Volumes/MyData/SharesProject/YahooIndustry/133.csv").getLines map {x => x.split(",") }

 
                                                  
  
  
}