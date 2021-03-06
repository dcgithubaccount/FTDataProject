package com.DC.FTDataParser

import scala.util._
import java.text.SimpleDateFormat
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import util.control.Breaks._

object FileParser extends Parameters {

val FileParserlogger = LoggerFactory.getLogger(this.getClass)
FileParserlogger.info("Entering FileParser")  
  


def exchangelookup(s :String): Try[String] = Try (exchange_currency_map(s))
  
def getdatafromFT (dir: String, dates: String) : Array[Array[Any]] = {

	import com.DC.FTDataParser.XMLPersister._
	XMLPersisterlogger.info("Fetching Currency Pairs")
	//val nodes = getXML.right
	
	val node = getXML(ccyurl,6).toOption
	
	
	val nodes = node match {case Some(nodes) => nodes}    
	  	
	XMLPersisterlogger.info("Testing USD GBP Currency Pair {}", getexchange(nodes,"GBP"))
	XMLPersisterlogger.info("Extracting Currency Complete")
	
	val sf = new SimpleDateFormat("MMM dd yyyy")
	val sdf = new SimpleDateFormat("dd-MM-yyyy")
	
	val dirpath = path + dir + "/" + dates + "_" + dir + file_map(dir)
	
	FileParserlogger.info("Processing for {}", dir)
	FileParserlogger.info("File and Directory path is {}" , dirpath )
	
	
	val bufferedSource = io.Source.fromFile(path + dir + "/" + dates + "_" + dir + file_map(dir), "UTF-8")
	val filesize = io.Source.fromFile(path + dir + "/" + dates + "_" + dir + file_map(dir), "UTF-8").getLines.size
	
	FileParserlogger.info("Record Count in File is  {}" , filesize )
	val multiarray = Array.ofDim[Any](filesize,53)
	
	FileParserlogger.info("Start Line Parsing")
	
	var count = 0
	for ( line <- bufferedSource.getLines) {
			
			
			val Cols = line.split("\\|").map(_.trim)
			
			// Static Array Sequence 
			
			
			
			val static  = Array(Cols(0), //Symbol 0
							   Cols(1), // Date  1
							   dir,     //Country 2
							   Cols(0).takeRight(3), //Exchange 3
							   //exchange_currency_map(Cols(0).takeRight(3)), //ExchangeCurrency 4
							   //exchangelookup(Cols(0).takeRight(3)) match {case Success(x) => x} , //ExchangeCurrency 4
							   exchangelookup(Cols(0).takeRight(3)) match { case Failure(x) => FileParserlogger.error("Issue with exchange rate map. Check error message!!!! {}", x.getMessage() ); Cols(16)
							   case Success(x) => x  }  , //ExchangeCurrency 4
							   Cols(2), //RIC 5
							   Cols(3), //IssueName 6
							   Cols(16), //Market Capitalization Currency 7
							   Cols(18), //Earning Per Share Currency 8
							   Cols(20), //Annual Dividend Currency 9
							   Cols(28).takeRight(3), //Annual Report Currency 10
							   Cols(35), //Industry 11
							   Cols(42) //Company Website 12
							   ) 	
			
			val fxconversion = fxconvertor(getexchange(nodes,static(10).toString),getexchange(nodes, exchange_to_real_ccy_map.getOrElse(static(4).toString,static(4).toString)))
			//logger.info("fxconversion {} ", fxconversion)
			val dynamic = Array(
								exphandler(BigDecimal(Cols(4))/adjust_currency_map(static(4).toString)),  //Closing Price 0
								exphandler(BigDecimal(Cols(5))/adjust_currency_map(static(4).toString)), //Open price 1
								exphandler(BigDecimal(Cols(6))/adjust_currency_map(static(4).toString)), //Day High Price 2
								exphandler(BigDecimal(Cols(7))/adjust_currency_map(static(4).toString)), //Day Low Price 3
								exphandler(BigDecimal(Cols(8))/adjust_currency_map(static(4).toString)), //Previous Closing Price 4
	 							exphandler(BigDecimal(Cols(9))/adjust_currency_map(static(4).toString)), //Bid Price 5
								exphandler(BigDecimal(Cols(10))/adjust_currency_map(static(4).toString)), //Offer Price 6
								exphandler(fmt(stringconvertor(Cols(11)))), //Average Volume 7
								exphandler(fmt(stringconvertor(Cols(12)))), //Shares Outstanding 8
								exphandler(fmt(stringconvertor(Cols(13)))), //Free Float 9
								exphandler(fmt(stringconvertor(Cols(15)))), //Market Capitalization 10
								exphandler(fmt(stringconvertor(Cols(17)))), //Earning Per Share 11
								exphandler(fmt(stringconvertor(Cols(19)))), //Annual Dividend 12
								exphandler(sdf.format(sf.parse(Cols(22)))), // Dividend Ex Date 13
								exphandler(sdf.format(sf.parse(Cols(23)))), // Dividend Pay Date 14
								exphandler(BigDecimal(Cols(24))/adjust_currency_map(static(4).toString)), //YearLowPrice 15
								exphandler(sdf.format(sf.parse(Cols(25)))), //YearLowDate 16
								exphandler(BigDecimal(Cols(26))/adjust_currency_map(static(4).toString)), //YearHighPrice 17
								exphandler(sdf.format(sf.parse(Cols(27)))), //YearHighDate 18
								exphandler(Cols(29)), //Year of Annual Report 19
								exphandler(fmt(stringconvertor(Cols(30)))), //Balance Sheet Total Assets 20
								exphandler(fmt(stringconvertor(Cols(31)))), //Balance Sheet Total Liabilities 21
								exphandler(fmt(stringconvertor(Cols(32)))), //Balance Sheet Total Equities 22
								exphandler(fmt(stringconvertor(Cols(33)))), //Balance Sheet Common Shares 23
								exphandler(fmt(stringconvertor(Cols(34)))), //Balance Sheet Treasury Shares 24
								exphandler(fmt(stringconvertor(Cols(36)))), //Income Statement Total Revenue 25
								exphandler(fmt(stringconvertor(Cols(37)))), //Income Statement Total Operational Expenses 26
								exphandler(fmt(stringconvertor(Cols(38)))), //Income Statement Op Income 27
								exphandler(fmt(stringconvertor(Cols(39)))), //Income Statement EBT 28
								exphandler(fmt(stringconvertor(Cols(40)))), //Income Statement EAT 29
								exphandler(fmt(stringconvertor(Cols(41)))),  //Income Statement Net Income 30
								exphandler(fmt(BigDecimal(Cols(32))/BigDecimal(Cols(33)))), //BookValue Per Share 31
								exphandler(fmt(BigDecimal(Cols(31))/BigDecimal(Cols(32)))),  //Debt to Equity Ratio 32
								exphandler(fmt(BigDecimal(Cols(40))/BigDecimal(Cols(32)))),  // Return on Equity 33
								exphandler(fmt(BigDecimal(Cols(38))/BigDecimal(Cols(36)))),  // Operating Profit Margin 34
								exphandler(fmt(BigDecimal(Cols(39))/BigDecimal(Cols(38)))),  // Financial Cost Ratio 35
								exphandler(fmt(BigDecimal(Cols(40))/BigDecimal(Cols(39)))),  // Tax Effect Ratio 36
								exphandler(fmt(stringconvertor(Cols(14)))), // P/E TTM 37
								exphandler(fmt(stringconvertor(Cols(21)))) // Annual Dividend Yield 38
															
							   )
							   
							   
			   				   
			   
			   val ratios = Array(
					   			exphandler(fmt(dynamic(0).toString.toDouble / (dynamic(31).toString.toDouble * fxconversion))) // Price to Book Value 0
			       )
			   val final_array = static ++ dynamic ++ ratios
			   multiarray(count) = final_array
			   count = count + 1 
			   
			   FileParserlogger.info("Final Computational Data Array {}", final_array)
			 
					  
		}
		
		bufferedSource.close
		FileParserlogger.info("End Line Parsing. File Closed")
		multiarray
	 }

}
