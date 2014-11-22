package com.DC.FTDataParser


  object MainApp extends App  {

        import com.DC.FTDataParser.FileParser._
        import com.DC.FTDataParser.DataLoader._

        val FTData = getdatafromFT(args(0),args(1))
        //val FTData = getdatafromFT("LATAM","20140828")
        setDataInMongo(FTData)
  
  
  
  

}