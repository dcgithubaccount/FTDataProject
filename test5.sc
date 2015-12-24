object test5 {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  import com.DC.FTDataParser.YahooIndustryParserandLoader._
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
)                                                 //> yahootoCountryMap  : scala.collection.immutable.Map[String,String] = Map(USA
                                                  //|  -> US, HongKong -> HK, Denmark -> CO, Swiss -> SW, Canada -> TO, India -> N
                                                  //| S, NewZealand -> NZ, Sweden -> ST, Australia -> AX, UK -> L, Singapore -> SI
                                                  //| , Italy -> MI, Holland -> AS, Norway -> OL, France -> PA, Germany -> DE)

yahootoCountryMap getOrElse("NewZealand","Error") //> res0: String = NZ
yahootoCountryMap get("Spain")                    //> res1: Option[String] = None

val a = indExtractorfromMongo("NewZealand")       //> 21:08:42.314 [main] DEBUG c.m.c.c.c.s.RegisterConversionHelpers$ - Registeri
                                                  //| ng Scala Conversions.
                                                  //| 21:08:42.318 [main] DEBUG c.m.c.c.c.s.RegisterConversionHelpers$ - Deseriali
                                                  //| zers for Scala Conversions registering
                                                  //| 21:08:42.318 [main] DEBUG c.m.c.c.c.s.RegisterConversionHelpers$ - Serialize
                                                  //| rs for Scala Conversions registering
                                                  //| 21:08:42.319 [main] DEBUG c.m.c.c.c.s.RegisterConversionHelpers$ - Setting u
                                                  //| p OptionSerializer
                                                  //| 21:08:42.320 [main] DEBUG c.m.c.c.c.s.RegisterConversionHelpers$ - Setting u
                                                  //| p ScalaProductSerializer
                                                  //| 21:08:42.320 [main] DEBUG c.m.c.c.c.s.RegisterConversionHelpers$ - Setting u
                                                  //| p ScalaCollectionSerializer
                                                  //| 21:08:42.321 [main] DEBUG c.m.c.c.c.s.RegisterConversionHelpers$ - Setting u
                                                  //| p ScalaRegexSerializers
                                                  //| 21:08:42.321 [main] DEBUG c.m.c.c.c.s.RegisterConversionHelpers$ - Hooking u
                                                  //| p scala.util.matching.Regex serializer
                                                  //| 21:08:42.322 [main] DEBUG c.m.c.c.c.s.RegisterConversionHelpers$ - Reached b
                                                  //| as
                                                  //| Output exceeds cutoff limit.


val string = "www.shell.com"                      //> string  : String = www.shell.com
val str = "Industrial Engineering"                //> str  : String = Industrial Engineering
val ordinary=(('0' to '9') ++ "+").toSet          //> ordinary  : scala.collection.immutable.Set[Char] = Set(8, 4, 9, 5, 6, 1, 0, 
                                                  //| 2, +, 7, 3)
def isOrdinary (s :String) = s.forall(ordinary.contains(_))
                                                  //> isOrdinary: (s: String)Boolean
str.contains("www")  ||   isOrdinary(str)         //> res2: Boolean = false
isOrdinary(str)                                   //> res3: Boolean = false

a.isEmpty                                         //> res4: Boolean = false
a.size                                            //> res5: Int = 146
val m = a.find(_.firstSplit == "WHS")             //> m  : Option[com.DC.FTDataParser.YahooIndustryParserandLoader.companyData] =
                                                  //|  Some(companyData(WHS.NZ,Warehouse
                                                  //| Group Ltd (The),Department Stores,Services,WHS,NZ))
}