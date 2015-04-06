package com.DC.FTDataParser


  object MainApp extends App {

	
		//println("First line of Code")
		import com.DC.FTDataParser.FileParser._
        import com.DC.FTDataParser.DataLoader._
        import com.DC.FTDataParser.ReportWriter._
        val FTData = getdatafromFT(args(0),args(1))
        //val FTData = getdatafromFT("Swiss","20150211")
        setDataInMongo(FTData)
        ValuePricetoBook(args(0),args(1))
  
  
	

}