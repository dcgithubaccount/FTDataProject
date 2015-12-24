

object cashbahtest  {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  
  import com.mongodb.casbah.Imports._
  import scala.math._
  import java.math.BigDecimal
  import scala.util.Try
  import breeze.stats._
  import breeze.linalg._
  import java.util.Date
  import java.text.SimpleDateFormat
  import java.util.Calendar
  
  val mongoClient = MongoClient("localhost", 27017)
                                                  //> 14:11:46.811 [main] DEBUG c.m.c.c.c.s.RegisterConversionHelpers$ - Registeri
                                                  //| ng Scala Conversions.
                                                  //| 14:11:46.815 [main] DEBUG c.m.c.c.c.s.RegisterConversionHelpers$ - Deseriali
                                                  //| zers for Scala Conversions registering
                                                  //| 14:11:46.815 [main] DEBUG c.m.c.c.c.s.RegisterConversionHelpers$ - Serialize
                                                  //| rs for Scala Conversions registering
                                                  //| 14:11:46.816 [main] DEBUG c.m.c.c.c.s.RegisterConversionHelpers$ - Setting u
                                                  //| p OptionSerializer
                                                  //| 14:11:46.817 [main] DEBUG c.m.c.c.c.s.RegisterConversionHelpers$ - Setting u
                                                  //| p ScalaProductSerializer
                                                  //| 14:11:46.817 [main] DEBUG c.m.c.c.c.s.RegisterConversionHelpers$ - Setting u
                                                  //| p ScalaCollectionSerializer
                                                  //| 14:11:46.818 [main] DEBUG c.m.c.c.c.s.RegisterConversionHelpers$ - Setting u
                                                  //| p ScalaRegexSerializers
                                                  //| 14:11:46.818 [main] DEBUG c.m.c.c.c.s.RegisterConversionHelpers$ - Hooking u
                                                  //| p scala.util.matching.Regex serializer
                                                  //| 14:11:46.819 [main] DEBUG c.m.c.c.c.s.RegisterConversionHelpers$ - Reached b
                                                  //| as
                                                  //| Output exceeds cutoff limit.
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

case class companyData (
   val Symbol :String,
   val Name :String ,
   val Industry :String,
   val Sector :String,
   val firstSplit :String,
   val exchange :String
  )
  val db = mongoClient("CompanyDatabase")         //> db  : com.mongodb.casbah.MongoDB = CompanyDatabase
	val price = db("PriceAndRatio")           //> price  : com.mongodb.casbah.MongoCollection = PriceAndRatio
	val statement = db("FinancialStatements") //> statement  : com.mongodb.casbah.MongoCollection = FinancialStatements
	val Industry = db("Industry")             //> Industry  : com.mongodb.casbah.MongoCollection = Industry
	
	val xchange = yahootoCountryMap getOrElse("USA","Error")
                                                  //> xchange  : String = US
     
     val queryind = MongoDBObject("exchange" -> xchange)
                                                  //> queryind  : com.mongodb.casbah.commons.Imports.DBObject = { "exchange" : "U
                                                  //| S"}
     
     val indData = (for (d <- Industry.find(queryind)) yield (companyData(d("_id").toString,
         d("Name").toString,
         d("Industry").toString,
         d("Sector").toString,
         d("FirstSplit").toString,
         d("exchange").toString))).toList         //> indData  : List[cashbahtest.companyData] = List(companyData(AGU,Agrium
                                                  //| Inc.,Agricultural Chemicals,Basic Material,AGU,US), companyData(AVD,America
                                                  //| n
                                                  //| Vanguard Corp.,Agricultural Chemicals,Basic Material,AVD,US), companyData(R
                                                  //| KDA,Arcadia
                                                  //| Biosciences, Inc.,Agricultural Chemicals,Basic Material,RKDA,US), companyDa
                                                  //| ta(CVAT,Cavitation
                                                  //| Technologies, Inc.,Agricultural Chemicals,Basic Material,CVAT,US), companyD
                                                  //| ata(CERE,Ceres,
                                                  //| Inc.,Agricultural Chemicals,Basic Material,CERE,US), companyData(CF,CF
                                                  //| Industries Holdings, Inc.,Agricultural Chemicals,Basic Material,CF,US), com
                                                  //| panyData(CGA,China
                                                  //| Green Agriculture, Inc.,Agricultural Chemicals,Basic Material,CGA,US), comp
                                                  //| anyData(UAN,CVR
                                                  //| Partners, LP,Agricultural Chemicals,Basic Material,UAN,US), companyData(DD,
                                                  //| E.
                                                  //| I. du Pont de Nemours and C,Agricultural Chemicals,Basic Material,DD,US), c
                                                  //| ompanyData(GROG,GroGenesis,
                                                  //| Inc.,Agricultural Chemicals,Basic Materi
                                                  //| Output exceeds cutoff limit.
	
	val sdf = new SimpleDateFormat("yyyyMMdd")//> sdf  : java.text.SimpleDateFormat = java.text.SimpleDateFormat@ef87e460
	val sf = new SimpleDateFormat("dd-MM-yyyy")
                                                  //> sf  : java.text.SimpleDateFormat = java.text.SimpleDateFormat@9586200
                                                  
	sf.format(sdf.parse("20150220"))          //> res0: String = 20-02-2015
	
	def parseDate(date : String) : Try[Date] = {
		Try(sf.parse(date))
	}                                         //> parseDate: (date: String)scala.util.Try[java.util.Date]
	
	def Desc[T : Ordering] = implicitly[Ordering[T]].reverse
                                                  //> Desc: [T](implicit evidence$1: scala.math.Ordering[T])scala.math.Ordering[T
                                                  //| ]
	def fmt(v: Any): String = v match {
		case d : Double => "%1.3f" format d
		case i : Int => i.toString
		case b : BigDecimal => "%.3f" format b
		case _ => " "
	}                                         //> fmt: (v: Any)String
	
	case class Stocks (val Symbol : String,
				   val RunDate : Date,
				   val Country : String,
				   val Exchange : String,
				   val RIC : String,
				   val IssueName : String,
				   val Industry : String,
				   val EPS : Double,
				   val EPSCCY : String,
				   val BookValuePerShare : Double,
				   val AnnualReportCCY : String,
				   val AnnualDividend : Double,
				   val AnnualDiviCCY : String,
				   val DividendExDate : String,
				   val DividendPayDate : String,
                   val Close  : Double,
                   val Open : Double,
                   val DayHigh : Double,
                   val DayLow : Double,
                   val PreviousClose  : Double,
                   val YearLowPrice : Double,
                   val YearLowDate : String,
                   val YearHighPrice : Double,
                   val YearHighDate : String,
                   val ExchangeCCY : String,
                   val AverageVolume : String,
                   val SharesOutstanding : String,
                   val FreeFloat : String,
                   val MarketCap : String,
                   val MarketCapCCY : String,
                   val DebtToEquity    : Double,
                   val ReturnOnEquity : Double,
                   val OperatingProfitMargin : Double,
                   val FinancialCostRatio : Double,
                   val TaxEffectRatio : Double,
                   val PriceToEarning : Double,
                   val AnnualDivYield : Double,
                   val PriceToBook : Double,
                   val FinancialStmtYear : String,
                   val DailyChange : Double
                   )

def objectRead(o: DBObject): Stocks = {
    Stocks(
      Symbol = o.as[String]("Symbol"),
      RunDate = sf.parse(o("RunDate").toString),
      Country = o("Country").toString,
      Exchange = o("Exchange").toString,
      RIC = o("RIC").toString,
      IssueName = o("IssueName").toString,
      Industry = o("Industry").toString,
      EPS = fmt(o("EarningPerShare")).toDouble,
      EPSCCY = ((o.getAs[DBObject]("Currency").get)("EPSCCY")).toString,
      BookValuePerShare = fmt(o("BookValuePerShare")).toDouble,
      AnnualReportCCY = ((o.getAs[DBObject]("Currency").get)("AnnualReportCCY")).toString,
      AnnualDividend = fmt(o("AnnualDividend")).toDouble,
      AnnualDiviCCY = ((o.getAs[DBObject]("Currency").get)("AnnualDiviCCY")).toString,
      DividendExDate = o("DividendExDate").toString,
      DividendPayDate = o("DividendPayDate").toString,
      Close = fmt((o.getAs[DBObject]("PricesandVolume").get)("Close")).toDouble,
      Open = fmt((o.getAs[DBObject]("PricesandVolume").get)("Open")).toDouble,
      DayHigh = fmt((o.getAs[DBObject]("PricesandVolume").get)("DayHigh")).toDouble,
      DayLow = fmt((o.getAs[DBObject]("PricesandVolume").get)("DayLow")).toDouble,
      PreviousClose = fmt((o.getAs[DBObject]("PricesandVolume").get)("PreviousClose")).toDouble,
      YearLowPrice = fmt((o.getAs[DBObject]("PricesandVolume").get)("YearLowPrice")).toDouble,
      YearLowDate = ((o.getAs[DBObject]("PricesandVolume").get)("YearLowPrice")).toString,
      YearHighPrice = fmt((o.getAs[DBObject]("PricesandVolume").get)("YearHighPrice")).toDouble,
      YearHighDate = ((o.getAs[DBObject]("PricesandVolume").get)("YearLowPrice")).toString,
      ExchangeCCY = ((o.getAs[DBObject]("Currency").get)("ExchangeCCY")).toString,
      AverageVolume = fmt((o.getAs[DBObject]("PricesandVolume").get)("AverageVolume")),
      SharesOutstanding = fmt((o.getAs[DBObject]("PricesandVolume").get)("SharesOutstanding")),
      FreeFloat = fmt((o.getAs[DBObject]("PricesandVolume").get)("FreeFloat")),
      MarketCap = fmt((o.getAs[DBObject]("PricesandVolume").get)("MarketCap")),
      MarketCapCCY = ((o.getAs[DBObject]("Currency").get)("MarketCapCCY")).toString,
      DebtToEquity = fmt((o.getAs[DBObject]("Ratios").get)("DebtToEquity")).toDouble,
      ReturnOnEquity = fmt((o.getAs[DBObject]("Ratios").get)("ReturnOnEquity")).toDouble,
      OperatingProfitMargin = fmt((o.getAs[DBObject]("Ratios").get)("OperatingProfitMargin")).toDouble,
      FinancialCostRatio = fmt((o.getAs[DBObject]("Ratios").get)("FinancialCostRatio")).toDouble,
      TaxEffectRatio = fmt((o.getAs[DBObject]("Ratios").get)("TaxEffectRatio")).toDouble,
      PriceToEarning = fmt((o.getAs[DBObject]("Ratios").get)("PriceToEarning")).toDouble,
      AnnualDivYield = fmt((o.getAs[DBObject]("Ratios").get)("AnnualDivYield")).toDouble,
      PriceToBook = fmt((o.getAs[DBObject]("Ratios").get)("PriceToBook")).toDouble,
      FinancialStmtYear = o("FinanceStatements").toString.takeRight(4),
      DailyChange = fmt((((fmt((o.getAs[DBObject]("PricesandVolume").get)("Close")).toDouble - fmt((o.getAs[DBObject]("PricesandVolume").get)("PreviousClose")).toDouble)/fmt((o.getAs[DBObject]("PricesandVolume").get)("Close")).toDouble))*100).toDouble
    )
  }                                               //> objectRead: (o: com.mongodb.casbah.Imports.DBObject)cashbahtest.Stocks
	  
  def logReturn(q :String, n : Int) :List[Double] = {
			val sq = 	MongoDBObject("Symbol" -> q)
			val qq = 	((for (d <- price.find(sq)) yield(
										  sf.parse(d("RunDate").toString),
										  fmt((d.getAs[DBObject]("PricesandVolume").get)("Close")).toDouble)).toList sortBy{x => x._1} reverse) filter { x => x._2 >0} map {x => x._2} take(n)
			
			val lg = ((qq.init) zip (qq drop(1)) ) map {x => log(x._1/x._2).toDouble} //map {x => (rint(x*100)/100).toDouble}
			lg
}                                                 //> logReturn: (q: String, n: Int)List[Double]


def excessReturn1 (q :String, n : Int) :List[Double] = {
		val mu = mean(logReturn(q,n))
		logReturn(q,n) map {x => x - mu} //map {x => (rint(x*100)/100).toDouble}

}                                                 //> excessReturn1: (q: String, n: Int)List[Double]


def excessReturn (lg :List[Double]) :List[Double] = {
		val mu = mean(lg)
		lg map {x => x - mu} //map {x => (rint(x*100)/100).toDouble}

}                                                 //> excessReturn: (lg: List[Double])List[Double]

def stDev(v :List[Double]) : Double = {
		sqrt(variance(v))
		
}                                                 //> stDev: (v: List[Double])Double
  
  case class Stats (
  						val Symbol :String,
  						val logReturn :List[Double],
  						val mu :Double,
  						val sigma :Double,
  						val length :Int,
  						val excessReturn :List[Double]
  						
  )
  
  def assetStats(s :String,n :Int):Stats = {
  	val lr = logReturn(s,n)
  	Stats(
  			Symbol = s,
  			logReturn = lr,
  			mu = mean(lr),
  			sigma = stDev(lr),
  			length = lr.length,
  			excessReturn = excessReturn(lr)
  	)
  }                                               //> assetStats: (s: String, n: Int)cashbahtest.Stats
  
	val country = "Portugal"                  //> country  : String = Portugal
	val query = MongoDBObject("RunDate" -> "07-09-2015") ++ ("Country" -> country) //++ ("Ratios.AnnualDivYield" $gte 0.025) ++ ("Ratios.PriceToBook" $lte 2.00)
                                                  //> query  : com.mongodb.casbah.commons.Imports.DBObject = { "RunDate" : "07-09
                                                  //| -2015" , "Country" : "Portugal"}
  	
	val ct = price.count(query)               //> ct  : Int = 20
	val stquery = MongoDBObject("_id" -> "CM:TOR2014")
                                                  //> stquery  : com.mongodb.casbah.commons.Imports.DBObject = { "_id" : "CM:TOR2
                                                  //| 014"}
	for (s <- statement.find(stquery)) println(s)
                                                  //> { "_id" : "CM:TOR2014" , "AnnualReportYear" : "2014" , "AnnualReportCCY" : 
                                                  //| "CAD" , "BSTotalAssets" : 414903.0 , "BSTotalLiabilities" : 396284.0 , "BST
                                                  //| otalEquities" : 18619.0 , "BSCommonShares" : 397.0 , "BSTreasuryShares" : 0
                                                  //| .0 , "IncomeStatementTotalRevenue" : 0 , "IncomeStatementTotalOperationalEx
                                                  //| penses" : 0 , "IncomeStatementOpIncome" : 0 , "IncomeStatementEBT" : 3914.0
                                                  //|  , "IncomeStatementEAT" : 3215.0 , "IncomeStatementNetIncome" : 3218.0}
	


val m = for (d <- price.find(query)) yield(d)     //> m  : Iterator[com.mongodb.casbah.Imports.DBObject] = non-empty iterator


val a = (m map {x => objectRead(x)}).toList//.sortBy(_.MarketCap.toDouble)(Desc)
                                                  //> a  : List[cashbahtest.Stocks] = List(Stocks(ALTR:LIS,Mon Sep 07 00:00:00 BS
                                                  //| T 2015,Portugal,LIS,ALSS.LS,Altri SGPS SA,Forestry-and-Paper,0.365,EUR,1.32
                                                  //| 7,EUR,0.058,EUR,07-05-2015,11-05-2015,3.18,3.2,3.24,3.15,3.18,1.88,1.88,4.3
                                                  //| 5,1.88,EUR,588360.000,205130000.000,67680000.000,651500000.000,EUR,3.555,0.
                                                  //| 136,0.118,0.631,0.902,8.69,0.018,2.4,2014,0.0), Stocks(BANIF:LIS,Mon Sep 07
                                                  //|  00:00:00 BST 2015,Portugal,LIS,BANIF.LS,Banif Banco Internacional do Funch
                                                  //| al SA,Banks,-0.002,EUR,0.006,EUR,0.0,NA, , ,0.004,0.005,0.005,0.004,0.005,0
                                                  //| .004,0.0037,0.009,0.0037,EUR,88030000.000,45640000000.000,45640000000.000,2
                                                  //| 09940000.000,EUR,16.881,0.0,0.0,0.0,0.0,0.0,0.0,0.7,2014,-25.0), Stocks(BCP
                                                  //| :LIS,Mon Sep 07 00:00:00 BST 2015,Portugal,LIS,BCP.LS,Banco Comercial Portu
                                                  //| gues SA,Banks,0.001,EUR,0.078,EUR,0.0,NA, , ,0.054,0.057,0.058,0.053,0.056,
                                                  //| 0.053,0.0533,0.114,0.0533,EUR,232890000.000,59040000000.000,49460000000.000
                                                  //| ,3330000000.000,EUR,17.
                                                  //| Output exceeds cutoff limit.
Calendar.getInstance().getTime()                  //> res1: java.util.Date = Wed Nov 04 14:11:48 GMT 2015
lazy val sa = a map {x => x.Symbol} map { x=> assetStats(x,100) } //filterNot {x => x.length < 99}
                                                  //> sa: => List[cashbahtest.Stats]
val sab = sa filterNot {x => x.length < 99}       //> sab  : List[cashbahtest.Stats] = List(Stats(ALTR:LIS,List(0.043485111939738
                                                  //| 89, 0.04546237407675741, 0.0, 0.01641302964133005, 0.009501259124140215, -0
                                                  //| .032866808080351986, 0.023365548956211693, -0.002361276185679709, 0.0142520
                                                  //| 22707201632, 0.016888218028521577, 0.004878058453432994, 0.0272631507583639
                                                  //| 96, -0.01000008333458331, 0.0458095360312942, -0.0026007817000574785, -0.01
                                                  //| 033600933066206, -0.012771565679487505, 0.002541297428672548, 0.01538491883
                                                  //| 9479456, -0.015384918839479456, 0.007662872745569097, 0.025975486403260736,
                                                  //|  0.02127739844728488, 0.03278982282299097, 0.03966525639243157, -0.01434744
                                                  //| 840814154, -0.04184710993550049, 0.008230499136515444, -0.01095901378971954
                                                  //| 3, 0.013717636228799126, -0.040600975622239646, 0.021448543407483693, 0.008
                                                  //| 163310639160835, 0.016529301951210506, 0.02531780798429, 0.0143474484081415
                                                  //| 75, -0.028491955794306273, -0.005602255548669898, 0.008415196925284498, 0.0
                                                  //| 7606003871004387, 0.034
                                                  //| Output exceeds cutoff limit.

//val s = (m map {x => objectRead(x)}).toList

val t1 = DenseMatrix((1,3),(2,4),(0,5))           //> t1  : breeze.linalg.DenseMatrix[Int] = 1  3  
                                                  //| 2  4  
                                                  //| 0  5  
val t2 = DenseMatrix((1,0),(2,3))                 //> t2  : breeze.linalg.DenseMatrix[Int] = 1  0  
                                                  //| 2  3  
 
 t1 * t2                                          //> res2: breeze.linalg.DenseMatrix[Int] = 7   9   
                                                  //| 10  12  
                                                  //| 10  15  

val dm = DenseMatrix((for(i <- sab) yield i.excessReturn).map(_.toArray):_*)
                                                  //> dm  : breeze.linalg.DenseMatrix[Double] = 0.04065061794752991    0.04262788
                                                  //| 008454843    ... (99 total)
                                                  //| 0.00855856424633539    0.00855856424633539    ...
                                                  //| -0.013648499782446708  0.06079010553679341    ...
                                                  //| 0.0                    0.0                    ...
                                                  //| -0.007100939043942387  0.037034667975728075   ...
                                                  //| 0.006777613743632289   4.451507342418694E-5   ...
                                                  //| 0.005666507245004131   0.017500054193162456   ...
                                                  //| -0.004641437510194016  -9.2528983824888E-5    ...
                                                  //| 0.04026434796135018    0.037654945585077655   ...
                                                  //| 0.0                    0.0                    ...
                                                  //| 0.04907538170570936    -0.009244678825123642  ...
                                                  //| 0.005269226912672625   -0.024238351881466884  ...
                                                  //| 0.02324874047938565    -0.020133102963631522  ...
                                                  //| 8.867514764310984E-5   9.042020063614107E-5   ...
                                                  //| -0.02317902281973693   0.04039995882313445    ...
                                                  //| 0.00957110853513018    0.028372893676014605   ...
                                                  //| -7.541568562527202E-4  -7.541568562527202E-4  ...
                                                  //| 0
                                                  //| Output exceeds cutoff limit.

val sdm = DenseMatrix(for (i <- sab ) yield i.sigma)
                                                  //> sdm  : breeze.linalg.DenseMatrix[Double] = 0.02881509388181353  0.091206005
                                                  //| 72858108  0.0413739591080956  ... (20 total)
val sdmt = sdm.t                                  //> sdmt  : breeze.linalg.DenseMatrix[Double] = 0.02881509388181353   
                                                  //| 0.09120600572858108   
                                                  //| 0.0413739591080956    
                                                  //| 0.0                   
                                                  //| 0.03484589602414433   
                                                  //| 0.015886678480359313  
                                                  //| 0.017238027462021337  
                                                  //| 0.018731674398730743  
                                                  //| 0.03596611399703866   
                                                  //| 0.0                   
                                                  //| 0.023756542666388464  
                                                  //| 0.026503487170090743  
                                                  //| 0.022860034608039974  
                                                  //| 0.01533268005877224   
                                                  //| 0.05264351528320479   
                                                  //| 0.022024941057857902  
                                                  //| 0.01296578968410987   
                                                  //| 0.02288569328308633   
                                                  //| 0.020919507449334983  
                                                  //| 0.027825901053836647  

val eigen = breeze.numerics.rint(( sdmt * sdm) :* 1000000.0) :/ 1000000.0
                                                  //> Nov 04, 2015 2:11:54 PM com.github.fommil.jni.JniLoader liberalLoad
                                                  //| INFO: successfully loaded /var/folders/q1/yjdlc6x94jj914k4_8lvyrwr0000gn/T/
                                                  //| jniloader6084997503386501215netlib-native_system-osx-x86_64.jnilib
                                                  //| eigen  : breeze.linalg.DenseMatrix[Double] = 8.3E-4    0.002628  0.001192  
                                                  //| 0.0  0.001004  4.58E-4   4.97E-4   ... (20 total)
                                                  //| 0.002628  0.008319  0.003774  0.0  0.003178  0.001449  0.001572  ...
                                                  //| 0.001192  0.003774  0.001712  0.0  0.001442  6.57E-4   7.13E-4   ...
                                                  //| 0.0       0.0       0.0       0.0  0.0       0.0       0.0       ...
                                                  //| 0.001004  0.003178  0.001442  0.0  0.001214  5.54E-4   6.01E-4   ...
                                                  //| 4.58E-4   0.001449  6.57E-4   0.0  5.54E-4   2.52E-4   2.74E-4   ...
                                                  //| 4.97E-4   0.001572  7.13E-4   0.0  6.01E-4   2.74E-4   2.97E-4   ...
                                                  //| 5.4E-4    0.001708  7.75E-4   0.0  6.53E-4   2.98E-4   3.23E-4   ...
                                                  //| 0.001036  0.00328   0.001488  0.0  0.001253  5.71E-4   6.2E-4    ...
                                                  //| 0.0       0.0       0.0       0.0  0.0   
                                                  //| Output exceeds cutoff limit.

val dmt = dm.t                                    //> dmt  : breeze.linalg.DenseMatrix[Double] = 0.04065061794752991     0.008558
                                                  //| 56424633539   ... (20 total)
                                                  //| 0.04262788008454843     0.00855856424633539   ...
                                                  //| -0.002834493992208979   0.00855856424633539   ...
                                                  //| 0.013578535649121072    0.41402367235449977   ...
                                                  //| 0.006666765131931237    0.00855856424633539   ...
                                                  //| -0.03570130207256097    -0.39690654386182905  ...
                                                  //| 0.020531054964002714    0.00855856424633539   ...
                                                  //| -0.005195770177888688   0.00855856424633539   ...
                                                  //| 0.011417528714992653    0.00855856424633539   ...
                                                  //| 0.014053724036312599    0.00855856424633539   ...
                                                  //| 0.0020435644612240146   0.00855856424633539   ...
                                                  //| 0.024428656766155018    -0.2791235082054455   ...
                                                  //| -0.01283457732679229    0.00855856424633539   ...
                                                  //| 0.04297504203908522     0.00855856424633539   ...
                                                  //| -0.0054352756922664575  0.00855856424633539   ...
                                                  //| -0.013170503322871039   0.00855856424633539   ...
                                                  //| -0.015606059671696483   0.00855856424633539   ...
                                                  //| 
                                                  //| Output exceeds cutoff limit.


val mm = breeze.numerics.rint(((dm * dmt):/ 99.0) :* 1000000.0) :/ 1000000.0
                                                  //> mm  : breeze.linalg.DenseMatrix[Double] = 8.22E-4  5.0E-6    7.03E-4   0.0 
                                                  //|  6.24E-4   2.72E-4  3.19E-4  ... (20 total)
                                                  //| 5.0E-6   0.008235  1.2E-4    0.0  1.07E-4   -7.3E-5  9.9E-5   ...
                                                  //| 7.03E-4  1.2E-4    0.001695  0.0  8.13E-4   3.67E-4  4.21E-4  ...
                                                  //| 0.0      0.0       0.0       0.0  0.0       0.0      0.0      ...
                                                  //| 6.24E-4  1.07E-4   8.13E-4   0.0  0.001202  2.49E-4  3.21E-4  ...
                                                  //| 2.72E-4  -7.3E-5   3.67E-4   0.0  2.49E-4   2.5E-4   1.63E-4  ...
                                                  //| 3.19E-4  9.9E-5    4.21E-4   0.0  3.21E-4   1.63E-4  2.94E-4  ...
                                                  //| 3.32E-4  8.5E-5    4.53E-4   0.0  3.17E-4   1.58E-4  2.41E-4  ...
                                                  //| 5.09E-4  2.2E-4    7.6E-4    0.0  3.7E-4    3.31E-4  3.3E-4   ...
                                                  //| 0.0      0.0       0.0       0.0  0.0       0.0      0.0      ...
                                                  //| 1.31E-4  5.52E-4   1.91E-4   0.0  1.11E-4   5.0E-5   9.2E-5   ...
                                                  //| 1.6E-5   3.85E-4   1.36E-4   0.0  1.1E-5    1.0E-5   1.2E-5   ...
                                                  //| 3.93E-4  -6.0E-6   4.93E-4   0.0  4.04E-4   2.08E-4  2.14E-4  ...
                                                  //| 2.31E-4  2.4E
                                                  //| Output exceeds cutoff limit.

val sym = DenseVector((for (i <- sab ) yield i.Symbol).toArray)
                                                  //> sym  : breeze.linalg.DenseVector[String] = DenseVector(ALTR:LIS, BANIF:LIS,
                                                  //|  BCP:LIS, BES:LIS, BPI:LIS, CTT:LIS, EDP:LIS, EDPR:LIS, EGL:LIS, ESF:LIS, G
                                                  //| ALP:LIS, IPR:LIS, JMT:LIS, NOS:LIS, PHR:LIS, PTI:LIS, RENE:LIS, SEM:LIS, SO
                                                  //| N:LIS, TDSA:LIS)

sym.t                                             //> res3: breeze.linalg.Transpose[breeze.linalg.DenseVector[String]] = Transpos
                                                  //| e(DenseVector(ALTR:LIS, BANIF:LIS, BCP:LIS, BES:LIS, BPI:LIS, CTT:LIS, EDP:
                                                  //| LIS, EDPR:LIS, EGL:LIS, ESF:LIS, GALP:LIS, IPR:LIS, JMT:LIS, NOS:LIS, PHR:L
                                                  //| IS, PTI:LIS, RENE:LIS, SEM:LIS, SON:LIS, TDSA:LIS))
val cor = breeze.numerics.rint((mm :/ eigen) :* 10000.0) :/ 10000.0
                                                  //> cor  : breeze.linalg.DenseMatrix[Double] = 0.9904  0.0019   0.5898  NaN  0.
                                                  //| 6215   0.5939   0.6419  0.6148  ... (20 total)
                                                  //| 0.0019  0.9899   0.0318  NaN  0.0337   -0.0504  0.063   0.0498  ...
                                                  //| 0.5898  0.0318   0.9901  NaN  0.5638   0.5586   0.5905  0.5845  ...
                                                  //| NaN     NaN      NaN     NaN  NaN      NaN      NaN     NaN     ...
                                                  //| 0.6215  0.0337   0.5638  NaN  0.9901   0.4495   0.5341  0.4855  ...
                                                  //| 0.5939  -0.0504  0.5586  NaN  0.4495   0.9921   0.5949  0.5302  ...
                                                  //| 0.6419  0.063    0.5905  NaN  0.5341   0.5949   0.9899  0.7461  ...
                                                  //| 0.6148  0.0498   0.5845  NaN  0.4855   0.5302   0.7461  0.9886  ...
                                                  //| 0.4913  0.0671   0.5108  NaN  0.2953   0.5797   0.5323  0.4718  ...
                                                  //| NaN     NaN      NaN     NaN  NaN      NaN      NaN     NaN     ...
                                                  //| 0.1912  0.2547   0.1943  NaN  0.1341   0.1326   0.2244  0.2494  ...
                                                  //| 0.0209  0.1593   0.124   NaN  0.0119   0.0238   0.0263  0.1956  ...
                                                  //| 0.5964  -0.0029  0.5211  NaN  0.5069   0.573    0.5431  0.4
                                                  //| Output exceeds cutoff limit.
import java.io.File
breeze.linalg.csvwrite(new File("/Users/deepakchoudhary/Desktop/eclipse/Workspace/FTDataProject/matrix.csv"), cor, separator = ',')

Calendar.getInstance().getTime()                  //> res4: java.util.Date = Wed Nov 04 14:11:54 GMT 2015






//val dm = (DenseMatrix(excessReturn("AAL:LSE",100),excessReturn("BLT:LSE",100),excessReturn("VOD:LSE",100))) //* (DenseMatrix(excessReturn("ULVR:LSE",100),excessReturn("BLT:LSE",100),excessReturn("VOD:LSE",100)).t)
((a map { x => (x.Symbol,x.IssueName,x.RIC,x.PriceToBook,x.Close,x.YearLowPrice,x.YearHighPrice,(((x.Close - x.YearLowPrice)/(x.YearHighPrice - x.YearLowPrice))*100))}).toList.
sortBy(r => r._8).filter {r => (r._8 >=0 && r._8 < 10)})
                                                  //> res5: List[(String, String, String, Double, Double, Double, Double, Double
                                                  //| )] = List((BANIF:LIS,Banif Banco Internacional do Funchal SA,BANIF.LS,0.7,
                                                  //| 0.004,0.004,0.009,0.0), (PHR:LIS,Pharol SGPS SA,PHRA.LS,0.19,0.244,0.229,1
                                                  //| .85,0.9253547193090677), (BCP:LIS,Banco Comercial Portugues SA,BCP.LS,0.69
                                                  //| ,0.054,0.053,0.114,1.6393442622950833), (EGL:LIS,Mota Engil SGPS SA,MOTA.L
                                                  //| S,1.31,2.0,1.92,5.44,2.2727272727272747), (TDSA:LIS,Teixeira Duarte SA,TDS
                                                  //| A.LS,0.41,0.45,0.431,0.914,3.933747412008285), (BES:LIS,Banco Espirito San
                                                  //| to SA,BES.LS,0.09,0.12,0.1,0.47,5.405405405405403), (IPR:LIS,Impresa Socie
                                                  //| dade Gestora de Participacoes Sociais SA,IMPA.LS,0.87,0.711,0.655,1.39,7.6
                                                  //| 190476190476115))
/*
val stockquery =  MongoDBObject("Symbol" -> "TSCO:LSE")

val qq = (for (d <- price.find(stockquery)) yield(
										  sf.parse(d("RunDate").toString),
										  fmt((d.getAs[DBObject]("PricesandVolume").get)("Close")).toDouble)).toList.take(100).sortBy{x => x._1} filter { x => x._2 >0}
										  
//val qq = List(1,2,4,5,7,8,9,22,13,23) map {x => x.toDouble}
qq.length

val priceqq = qq map {x => x._2} reverse

val logreturn = ((priceqq.init) zip (priceqq drop(1)) ) map {x => (fmt(log(x._1/x._2))).toDouble}


logReturn("BLT:LSE",100)
excessReturn("ULVR:LSE",100)
val mu = mean(logReturn("ULVR:LSE",5))

logReturn("ULVR:LSE",5) map {x => x - mu}

excessReturn("ULVR:LSE",5)
excessReturn("BLT:LSE",5)
excessReturn("VOD:LSE",5)


//case class

 

val dm = (DenseMatrix(excessReturn("ULVR:LSE",100),excessReturn("BLT:LSE",100),excessReturn("VOD:LSE",100))) * (DenseMatrix(excessReturn("ULVR:LSE",100),excessReturn("BLT:LSE",100),excessReturn("VOD:LSE",100)).t)
																										

val dm1 = breeze.numerics.rint((dm :/ 99.0) :* 100000.0) :/ 100000.0

//val dm2 = breeze.numerics.rint(dm1 :* 100000.toDouble) :/ 100000.toDouble



val sdm = (DenseMatrix(stDev(logReturn("ULVR:LSE",100)),stDev(logReturn("BLT:LSE",100)), stDev(logReturn("VOD:LSE",100)))) * (DenseMatrix(stDev(logReturn("ULVR:LSE",100)),stDev(logReturn("BLT:LSE",100)), stDev(logReturn("VOD:LSE",100))).t)
val sdm1 = breeze.numerics.rint(sdm :* 100000.toDouble) :/ 100000.toDouble
//println(dm)
//val cor = dm1 :/ sdm
val cor = breeze.numerics.rint((dm1 :/ sdm1) :* 10000.0) :/ 10000.0
println(cor)


mean(logreturn)
sqrt(variance(logreturn))

MeanAndVariance(mean(logreturn),variance(logreturn),logreturn.length)


//LR("TSCO:LSE",100)



MeanAndVariance(mean(logReturn("BLT:LSE",100)),sqrt(variance(logReturn("BLT:LSE",100))),logReturn("BLT:LSE",100).length)
MeanAndVariance(mean(logReturn("TSCO:LSE",100)),sqrt(variance(logReturn("TSCO:LSE",100))),logReturn("TSCO:LSE",100).length)
MeanAndVariance(mean(logReturn("VOD:LSE",100)),sqrt(variance(logReturn("VOD:LSE",100))),logReturn("VOD:LSE",100).length)




def movingAverage(values: List[Double], period: Int): List[Double] = {
   val first = (values take period).sum / period
   val subtract = values map (_ / period)
   val add = subtract drop period
   val addAndSubtract = add zip subtract map Function.tupled(_ - _)
   val res = (addAndSubtract.foldLeft(first :: List.fill(period - 1)(0.0)) {
     (acc, add) => (add + acc.head) :: acc
   }).reverse
   res
 }
 
 val mva15 = (movingAverage(qq map {x => x._2},15) map {x => fmt(x).toDouble}).reverse.take(1)
 val mva30 = (movingAverage(qq map {x => x._2},30) map {x => fmt(x).toDouble}).reverse.take(1)
 val mva45 = (movingAverage(qq map {x => x._2},45) map {x => fmt(x).toDouble}).reverse.take(1)
                                                  
 
 val mva60 = (movingAverage(qq map {x => x._2},60) map {x => fmt(x).toDouble}).reverse.take(1)
 val mva100 = (movingAverage(qq map {x => x._2},100) map {x => fmt(x).toDouble}).reverse.take(1)
*/

                                                   
 
                                                  
}