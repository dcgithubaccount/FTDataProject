package com.DC.FTDataParser


  object MainApp extends App {

	
		//println("First line of Code")
		import com.DC.FTDataParser.FileParser._
        import com.DC.FTDataParser.DataLoader._
        import com.DC.FTDataParser.ReportWriter._
        import com.DC.FTDataParser.CorrelationMatricCalc._
        val FTData = getdatafromFT(args(0),args(1))
        //val FTData = getdatafromFT("Brazil","20150211")
        setDataInMongo(FTData)
        ValuePricetoBook(args(0),args(1))
        //val cor = getCorMatrix("India", "14-09-2015", 100)
        //saveCorMatrix(cor)
  
	

}