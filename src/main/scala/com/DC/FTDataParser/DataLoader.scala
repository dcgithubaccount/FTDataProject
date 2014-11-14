package com.DC.FTDataParser

object DataLoader extends DBConnection{

  import java.text.SimpleDateFormat
  import com.DC.FTDataParser.FileParser._
  import com.mongodb.casbah.Imports._
  import org.slf4j.Logger
  import org.slf4j.LoggerFactory
  
  val DataLoaderlogger = LoggerFactory.getLogger(this.getClass)
  DataLoaderlogger.info("Entering DataLoader")  
  
  
  def parseDouble(s: String) = {
	  try { Some(s.toDouble) } catch { case e:Exception => None }
  } 
  
  
  def setDataInMongo(A :Array[Array[Any]]) = {
	  
	  val strfmt = new SimpleDateFormat("yyyyMMdd")
	  val oldfmt = new SimpleDateFormat("dd-MM-yyyy")
	  val FTData = A
	  
	  for ( i <- 0 until FTData.length) {
  		val priceid  =  FTData(i)(0) + strfmt.format(oldfmt.parse(FTData(i)(1).toString())) 
  		val stmtId   =  FTData(i)(0) + FTData(i)(32).toString()
  	    //val query = MongoDBObject("_id" -> stmtId )
  	    val obj = statement.findOne(MongoDBObject("_id" -> stmtId ))
  	    
  	    obj match {
  		  case Some(x) => None 
  		  case _ => statement.insert(MongoDBObject(
  				  	"_id" -> stmtId,
  				  	"AnnualReportYear" -> FTData(i)(32),
  				  	"AnnualReportCCY" -> FTData(i)(10),
  				  	"BSTotalAssets" -> parseDouble(FTData(i)(33).toString).getOrElse(0),
  				  	"BSTotalLiabilities" -> parseDouble(FTData(i)(34).toString).getOrElse(0),
  				  	"BSTotalEquities" -> parseDouble(FTData(i)(35).toString).getOrElse(0),
  				  	"BSCommonShares" -> parseDouble(FTData(i)(36).toString).getOrElse(0),
					"BSTreasuryShares" -> parseDouble(FTData(i)(37).toString).getOrElse(0),
					"IncomeStatementTotalRevenue" -> parseDouble(FTData(i)(38).toString).getOrElse(0),
					"IncomeStatementTotalOperationalExpenses" -> parseDouble(FTData(i)(39).toString).getOrElse(0),
					"IncomeStatementOpIncome" -> parseDouble(FTData(i)(40).toString).getOrElse(0),
					"IncomeStatementEBT" -> parseDouble(FTData(i)(41).toString).getOrElse(0),
					"IncomeStatementEAT" -> parseDouble(FTData(i)(42).toString).getOrElse(0),
					"IncomeStatementNetIncome" -> parseDouble(FTData(i)(43).toString).getOrElse(0)
					
  		  ))
  		}
  	    //DataLoaderlogger.info("The query is {}", query)
  	    DataLoaderlogger.info("Statement Id is {}", stmtId )
  	    DataLoaderlogger.info("Price Id is {}", priceid)
  	    val conf = price.findOne(MongoDBObject("_id" -> priceid ))
  	    conf match{
  	     case Some(x) => None 
  	     case _ => price.insert(MongoDBObject(
  			"_id" -> priceid, 
  			"Symbol" -> FTData(i)(0),
  			"RunDate" -> FTData(i)(1),
  			"Country" -> FTData(i)(2),
  			"Exchange" -> FTData(i)(3),
  			"RIC" -> FTData(i)(5),
  			"IssueName" -> FTData(i)(6),
  			"Industry" -> FTData(i)(11),
  			"CompanyWebsite" -> FTData(i)(12),
  			"EarningPerShare" -> parseDouble(FTData(i)(24).toString).getOrElse(0),
  			"BookValuePerShare" -> parseDouble(FTData(i)(44).toString).getOrElse(0),
  			"AnnualDividend" -> parseDouble(FTData(i)(25).toString).getOrElse(0),
  			"DividendExDate" -> FTData(i)(26),
  			"DividendPayDate" -> FTData(i)(27),
  			"PricesandVolume" -> MongoDBObject( "Close" -> parseDouble(FTData(i)(13).toString).getOrElse(0),
  									   "Open"  -> parseDouble(FTData(i)(14).toString).getOrElse(0),
  									   "DayHigh" -> parseDouble(FTData(i)(15).toString).getOrElse(0),
  									   "DayLow" -> parseDouble(FTData(i)(16).toString).getOrElse(0),
  									   "PreviousClose" -> parseDouble(FTData(i)(17).toString).getOrElse(0),
  									   "Bid" -> parseDouble(FTData(i)(18).toString).getOrElse(0),
  									   "Offer" -> parseDouble(FTData(i)(19).toString).getOrElse(0),
  									   "YearLowPrice" -> parseDouble(FTData(i)(28).toString).getOrElse(0),
  									   "YearHighPrice" -> parseDouble(FTData(i)(30).toString).getOrElse(0),
  									   "YearLowDate" -> FTData(i)(29),
  									   "YearHighDate" -> FTData(i)(31),
  									   "AverageVolume" -> parseDouble(FTData(i)(20).toString).getOrElse(0),
  									   "SharesOutstanding" -> parseDouble(FTData(i)(21).toString).getOrElse(0),
  									   "FreeFloat" -> parseDouble(FTData(i)(22).toString).getOrElse(0),
  									   "MarketCap" -> parseDouble(FTData(i)(23).toString).getOrElse(0)
  			    ),
  			"Currency" -> MongoDBObject ("ExchangeCCY" -> FTData(i)(4),
  										 "MarketCapCCY" -> FTData(i)(7),
  										 "EPSCCY" -> FTData(i)(8),
  										 "AnnualDiviCCY" -> FTData(i)(9),
  										 "AnnualReportCCY" -> FTData(i)(10)),
  			
  			"Ratios" -> MongoDBObject("DebtToEquity" -> parseDouble(FTData(i)(45).toString).getOrElse(0), 
  									  "ReturnOnEquity" -> parseDouble(FTData(i)(46).toString).getOrElse(0),
  									  "OperatingProfitMargin" -> parseDouble(FTData(i)(47).toString).getOrElse(0),
  									  "FinancialCostRatio" -> parseDouble(FTData(i)(48).toString).getOrElse(0),
  									  "TaxEffectRatio" -> parseDouble(FTData(i)(49).toString).getOrElse(0),
  									  "PriceToEarning" -> parseDouble(FTData(i)(50).toString).getOrElse(0),
  									  "AnnualDivYield" -> parseDouble(FTData(i)(51).toString).getOrElse(0), 
  									  "PriceToBook" -> parseDouble(FTData(i)(52).toString).getOrElse(0)),
  			"FinanceStatements" -> stmtId
  			
  									  
  		))
  	    }
	  }
	DataLoaderlogger.info("DataLoading in MongoDB Complete")  
  }  
  
}