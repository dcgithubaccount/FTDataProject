package com.DC.FTDataParser

trait Parameters {

  val path = "/Volumes/MyData/SharesProject/FTCompanyData/Output/"
  val ReportPath = "/Volumes/MyData/SharesProject/FTCompanyData/Reports/"
  val PBConfFile = "/Volumes/MyData/SharesProject/FTCompanyData/Configuration/PBDivi.csv"  
  
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
    "SouthAfrica" -> "_JSE40.csv",
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
    "SWX" -> "CHF",
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
    "NAQ" -> "USD",
    "TOR" -> "CAD",
    "SAO" -> "BRL",
    "MEX" -> "MXN",
    "SGO" -> "CLP",
    "JNB" -> "ZAX")
    
    
val adjust_currency_map = Map(
   "GBX" -> 100,
   "ZAX" -> 100
    ).withDefaultValue(1)     

val exchange_to_real_ccy_map = Map(
	"GBX" -> "GBP",
	"ZAX" -> "ZAR"
)

val ccyurl = "http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.xchange%20where%20pair%20in%20(%22USDAUD%22,%22USDEUR%22,%22USDCAD%22,%22USDDKK%22,%22USDHKD%22,%22USDINR%22,%22USDJPY%22,%22USDKRW%22,%22USDBRL%22,%22USDMXN%22,%22USDCLP%22,%22USDPEN%22,%22USDCOP%22,%22USDNZD%22,%22USDNOK%22,%22USDSGD%22,%22USDSEK%22,%22USDCHF%22,%22USDGBP%22,%22USDUSD%22,%22USDZAR%22)&env=store://datatables.org/alltableswithkeys"
  
}