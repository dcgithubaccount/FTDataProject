package com.DC.FTDataParser

object YahooIndustryParserandLoader extends Parameters with DBConnection {
  
  import com.mongodb.casbah.Imports._
  import scala.xml._
	import java.net._
	import scala.io.Source
	import scala.util.{Try, Success, Failure}
  import org.slf4j.Logger
  import org.slf4j.LoggerFactory
  
  
  val yahooIndustrylogger = LoggerFactory.getLogger(this.getClass)
  yahooIndustrylogger.info("Entering Yahoo Industry XML extractor")
  
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
)         
  
  case class IndProfile (
	  val Sector :String,
	  val Industry : String,
	  val IndustryId :Int,
	  val YahooURL :String
  )
  
  
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
  }
  
   
  def companyExtractor (ind :IndProfile) :List[companyData] = {

    val File133 = "/Volumes/MyData/SharesProject/YahooIndustry/133.csv"
    val front = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.industry%20where%20id%3D"
	  val back = "&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys"
    val finalString = front + "%22" + ind.IndustryId + "%22" + back
  
    import com.DC.FTDataParser.XMLPersister._
    val indXML = getXML(finalString,6)
    val expCatcher = indXML match {
      case Success(x) => x 
      case Failure(ex) => yahooIndustrylogger.error(s"There is a failure in parsing Yahoo XML")
      
    }
    
    val industry = indXML.toOption match {case Some(node) => node}
  
    val cdata = (industry \ "results" \"industry" \ "company" map {x =>
  	    val Symbol = (x \\ "@symbol").text
  	    val exchangedata = exchangeFinder(Symbol)
  	    val Name   = (x \\ "@name").text
  	    val Industry = ind.Industry
  	    val Sector   = ind.Sector
  	    val firstSplit = exchangedata._1
  	    val exchange = exchangedata._2
  	
		    companyData(Symbol,Name,Industry,Sector,firstSplit,exchange)
      }).toList
      
      if (cdata.isEmpty) {
         //val data = (Array(ind.Sector,ind.Industry,ind.IndustryId.toString)).toList
         yahooIndustrylogger.info("This Indistry Id didn't fetch any data {}",ind.IndustryId)
      }
    
      if (cdata.isEmpty && ind.IndustryId == 133) 
        (Source.fromFile(File133).getLines map {x => x.split(",") }
            map {x => 
                  val Symbol = x(0)
  	              val exchangedata = exchangeFinder(Symbol)
  	              val Name   = x(1)
  	              val Industry = ind.Industry
  	              val Sector   = ind.Sector
  	              val firstSplit = exchangedata._1
  	              val exchange = exchangedata._2 
  	              companyData(Symbol,Name,Industry,Sector,firstSplit,exchange)}).toList
      else 
        cdata 
     
  }
  
  def yahooDataExtractor() :List[companyData] = {
    
    yahooIndustrylogger.info("Reading Yahoo Industry basefile for data extraction")
    
    val lines = (Source.fromFile(YahooIndustryFile).getLines map {x => x.split(",") } map {x =>
	    val Sector = x(0)
	    val industry = x(1)
	    val IndustryId = x(2)
	    val YahooURL = x(3)
	    IndProfile(Sector,industry,IndustryId.toInt,YahooURL)

  }).toList  
  
  yahooIndustrylogger.info("Extracting company data from Yahoo for Industry")
  
  val cdata :List[companyData] = (for ( l <- lines ) yield { companyExtractor(l)}) flatten
  
  yahooIndustrylogger.info("Extracted company, Industry and Sector from Yahoo Rest API")
  
  return cdata
  
  }
  
  def setIndDatainMongo(cdata :List[companyData]) = {
    
    yahooIndustrylogger.info("Start Loading Data in Mongo")
    
    val IndData = cdata
    
    for ( i <- 0 until IndData.length) {
      val indId = IndData(i).Symbol
      val indObj = Industry.findOne(MongoDBObject("_id" -> indId ))
      indObj match {
        case Some(x) => None
        case _       => Industry.insert(MongoDBObject(
            "_id"          -> IndData(i).Symbol,
            "Name"         -> IndData(i).Name,
            "Industry"     -> IndData(i).Industry,
            "Sector"       -> IndData(i).Sector,
            "FirstSplit"   -> IndData(i).firstSplit,
            "exchange"     -> IndData(i).exchange
        ))
      }
      
    }
    
    yahooIndustrylogger.info("Finished loading Data in Mongo")
  }
  
  def indExtractorfromMongo(c: String):List[companyData] = {
     val xchange = yahootoCountryMap getOrElse(c,"Error")  
     
     val query = MongoDBObject("exchange" -> xchange)
     
     val indData = (for (d <- Industry.find(query)) yield (companyData(d("_id").toString,
         d("Name").toString,
         d("Industry").toString,
         d("Sector").toString,
         d("FirstSplit").toString,
         d("exchange").toString))).toList 
      
     return indData
  }
  
  
  
  
}