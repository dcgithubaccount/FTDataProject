object test5 {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(58); 
  println("Welcome to the Scala worksheet")
  
  import com.DC.FTDataParser.YahooIndustryParserandLoader._;$skip(592); 
  //val cdata = yahooDataExtractor()
  
  //((cdata map {x => x.Industry}).distinct).length
  //setIndDatainMongo(cdata)
  
  val yahootoCountryMap = Map (
    	"USA" -> "US",
    	"France" -> "PA",
    	"Holland" -> "AS",
    	"Italy" -> "MI",
    	"UK" -> "L",
    	"Germany" -> "DE",
    	"Australia" -> "AX",
    	"Swiss" -> "SW",
    	"HongKong" -> "HK",
    	"Sweden" -> "ST",
    	"Canada" -> "TO",
    	"Denmark" -> "CO",
    	"Norway" -> "OL",
    	"Singapore" -> "SI",
    	"NewZealand" -> "NZ",
    	"India" -> "NS"
);System.out.println("""yahootoCountryMap  : scala.collection.immutable.Map[String,String] = """ + $show(yahootoCountryMap ));$skip(51); val res$0 = 

yahootoCountryMap getOrElse("NewZealand","Error");System.out.println("""res0: String = """ + $show(res$0));$skip(31); val res$1 = 
yahootoCountryMap get("Spain");System.out.println("""res1: Option[String] = """ + $show(res$1));$skip(45); 

val a = indExtractorfromMongo("NewZealand");System.out.println("""a  : List[com.DC.FTDataParser.YahooIndustryParserandLoader.companyData] = """ + $show(a ));$skip(31); 


val string = "www.shell.com";System.out.println("""string  : String = """ + $show(string ));$skip(35); 
val str = "Industrial Engineering";System.out.println("""str  : String = """ + $show(str ));$skip(41); 
val ordinary=(('0' to '9') ++ "+").toSet;System.out.println("""ordinary  : scala.collection.immutable.Set[Char] = """ + $show(ordinary ));$skip(60); 
def isOrdinary (s :String) = s.forall(ordinary.contains(_));System.out.println("""isOrdinary: (s: String)Boolean""");$skip(42); val res$2 = 
str.contains("www")  ||   isOrdinary(str);System.out.println("""res2: Boolean = """ + $show(res$2));$skip(16); val res$3 = 
isOrdinary(str);System.out.println("""res3: Boolean = """ + $show(res$3));$skip(11); val res$4 = 

a.isEmpty;System.out.println("""res4: Boolean = """ + $show(res$4));$skip(7); val res$5 = 
a.size;System.out.println("""res5: Int = """ + $show(res$5));$skip(38); 
val m = a.find(_.firstSplit == "WHS");System.out.println("""m  : Option[com.DC.FTDataParser.YahooIndustryParserandLoader.companyData] = """ + $show(m ))}
}
