package com.DC.FTDataParser

trait Parameters {

  val path = "/Volumes/MyData/SharesProject/FTCompanyData/Output/"
  val FlagPath = "/Volumes/MyData/SharesProject/FTCompanyData/Complete/"
  
  val file_map = Map(
    "Australia" -> "_ASX100.csv",
    "NewZealand" -> "_NZSX50.csv",
    "HongKong" -> "_HSI50.csv",
    "Korea" -> "_KOSPI100.csv",
    "Japan" -> "_NIKKEI225.csv",
    "Singapore" -> "_STI30.csv",
    "India" -> "_NIFTY100.csv",
    "Italy" -> "_FTSEMIB.csv",
    "Swiss" -> "_SMI20.csv",
    "Germany" -> "_DAX80.csv",
    "France" -> "_CAC40.csv",
    "Sweden" -> "_OMXS30.csv",
    "UK" -> "_FTSE100.csv",
    "Holland" -> "_AEX25.csv",
    "Belgium" -> "_BEL20.csv",
    "Denmark" -> "_OMX20.csv",
    "Finland" -> "_OMX25.csv",
    "Norway" -> "_OSLOAllShare.csv",
    "Spain" -> "_IBEX35.csv",
    "Portugal" -> "_PSI20.csv",
    "USA" -> "_NYQ_NSQ.csv",
    "Canada" -> "_TSX60.csv",
    "LATAM" -> "_LATAM40.csv")
    
val exchange_currency_map = Map(

    "ASX" -> "AUD",
    "NZC" -> "NZD",
    "HKG" -> "HKD",
    "KSC" -> "KRW",
    "TYO" -> "JPY",
    "SES" -> "SGD",
    "NSI" -> "INR",
    "MIL" -> "EUR",
    "VTX" -> "CHF",
    "GER" -> "EUR",
    "PAR" -> "EUR",
    "STO" -> "SEK",
    "LSE" -> "GBX",
    "AEX" -> "EUR",
    "BRU" -> "EUR",
    "MCE" -> "EUR",
    "CPH" -> "DKK",
    "HEX" -> "EUR",
    "OSL" -> "NOK",
    "LIS" -> "EUR",
    "NSQ" -> "USD",
    "NYQ" -> "USD",
    "TOR" -> "CAD",
    "SAO" -> "BRL",
    "MEX" -> "MXN",
    "SGO" -> "CLP")
    
    
val adjust_currency_map = Map(
    "GBX" -> 100
    
    ).withDefaultValue(1)     

  
}