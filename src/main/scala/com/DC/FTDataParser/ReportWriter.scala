package com.DC.FTDataParser

object ReportWriter extends DBConnection with Parameters{
  
  import java.text.SimpleDateFormat
  import com.DC.FTDataParser.FileParser._
  import com.mongodb.casbah.Imports._
  import org.slf4j.Logger
  import org.slf4j.LoggerFactory
  import scala.math._
  import java.text.SimpleDateFormat
  import java.io.StringWriter
  import au.com.bytecode.opencsv.CSVWriter
  import scala.collection.JavaConversions._
  import java.io.FileWriter
  import java.io.BufferedWriter
  
  val ReportWriterLogger = LoggerFactory.getLogger(this.getClass)
  def Desc[T : Ordering] = implicitly[Ordering[T]].reverse	 
   
  def ValuePricetoBook (country: String, dates: String) = {
    
    val sdf = new SimpleDateFormat("dd-MM-yyyy")
    val sf = new SimpleDateFormat("yyyyMMdd")
  
  
  	case class PBDivi(
         val country : String,
         val PB : Double,
         val Diviyield : Double)
     
     
     val in = io.Source.fromFile(PBConfFile,"UTF-8")
     
     val PB = in.getLines.map(_.split(",")).map {p =>
    	 val country = p(0)
    	 val PB = p(1).toDouble
    	 val Diviyield = p(2).toDouble
 
    	 PBDivi(country,PB,Diviyield)
    }.toList
    
    in.close()
    
    ReportWriterLogger.info("Got country diviyield and bookvalue data")
    val conf = for (i <- PB; if i.country == country ) yield (i.PB,i.Diviyield)
                                                 
    val (pb, diviyield) = conf.toList.head
    
    ReportWriterLogger.info("pb and divi yield received")
    
    ReportWriterLogger.info("Entering into MongoDB Cashbah for query object creation.")
    
    val query = MongoDBObject("RunDate" -> sdf.format(sf.parse(dates))) ++ ("Country" -> country) ++ ("Ratios.AnnualDivYield" $gte diviyield) ++ ("Ratios.PriceToBook" $lte pb)
    
    ReportWriterLogger.info("Query object created.")
    
    
    val PBReport = (for (d <- price.find(query)) yield  (d("Symbol"),//1
										  sdf.parse(d("RunDate").toString), //2
										  d("Country"), //3
										  d("Exchange"), //4
										  d("IssueName"), //5
										  d("Industry"), //6
										  d("DividendExDate"), //7
										  d("DividendPayDate"), //8
										  fmt((d.getAs[DBObject]("PricesandVolume").get)("Close")).toDouble, //9
										  fmt((d.getAs[DBObject]("PricesandVolume").get)("PreviousClose")).toDouble, //10
										  fmt((d.getAs[DBObject]("PricesandVolume").get)("YearLowPrice")).toDouble, //11
										  fmt((d.getAs[DBObject]("PricesandVolume").get)("YearHighPrice")).toDouble, //12
										  (d.getAs[DBObject]("Currency").get)("ExchangeCCY"), //13
										  (d.getAs[DBObject]("Currency").get)("AnnualReportCCY"), //14
										  fmt((d.getAs[DBObject]("Ratios").get)("PriceToBook")).toDouble, //15
										  fmt((d.getAs[DBObject]("Ratios").get)("AnnualDivYield")).toDouble, //16
										  fmt((d.getAs[DBObject]("Ratios").get)("DebtToEquity")).toDouble, //17
										  fmt((d.getAs[DBObject]("Ratios").get)("ReturnOnEquity")).toDouble, //18
										  fmt((d.getAs[DBObject]("Ratios").get)("OperatingProfitMargin")).toDouble, //19
										  fmt((d.getAs[DBObject]("Ratios").get)("PriceToEarning")).toDouble, //20
										  d("FinanceStatements").toString.takeRight(4) //21 Annual Report Year
										  )).toList.sortBy( r =>  r._16)(Desc).sortBy(w => w._15) map {
      x => Array(x._1.toString,
				 x._2.toString ,
				 x._3.toString ,
				 x._4.toString ,
				 x._5.toString ,
				 x._6.toString ,
				 x._7.toString ,
				 x._8.toString ,
				 x._9.toString ,
				 x._10.toString ,
				 x._11.toString ,
				 x._12.toString ,
				 x._13.toString ,
				 x._14.toString ,
				 x._15.toString ,
				 x._16.toString ,
				 x._17.toString ,
				 x._18.toString ,
				 x._19.toString ,
				 x._20.toString ,
				 x._21.toString)
    }
								
    
     val reportName = dates + "_" + country + "_" + "PricetoBookAndDiviReport" + ".csv"    
	 val out = new BufferedWriter(new FileWriter(ReportPath + "/" + country + "/" + reportName))
     
     val writer = new CSVWriter(out);
     ReportWriterLogger.info("Start writing Report.")
     val parameters = Array("Rundate :",sdf.format(sf.parse(dates)),"Country :" , country, "Annual Dividend Yield selector :",diviyield.toString,"Price to Book Value selector : ", pb.toString)
     val Header = Array("Symbol","RunDate","Country","Exchange","IssueName","Industry","DividendExDate","DividendPayDate","Close",
         "PreviousClose","YearLowPrice","YearHighPrice","ExchangeCCY","AnnualReportCCY","PriceToBook","AnnualDivYield",
         "DebtToEquity","ReturnOnEquity","OperatingProfitMargin","PriceToEarning","AnnualReportYear")
     writer.writeAll(parameters::Header::PBReport)
     out.close()
     ReportWriterLogger.info("Report writing Complete.")
  }

}