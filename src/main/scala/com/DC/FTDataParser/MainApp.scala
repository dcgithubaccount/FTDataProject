package com.DC.FTDataParser


  object MainApp extends App {

	
		println("First line of Code")
		import com.DC.FTDataParser.FileParser._
        import com.DC.FTDataParser.DataLoader._

        val FTData = getdatafromFT(args(0),args(1))
        //val FTData = getdatafromFT("SouthAfrica","20141126")
        setDataInMongo(FTData)
  
  
  
	

}