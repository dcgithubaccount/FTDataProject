package com.DC.FTDataParser

object XMLPersister //(fromccy: String, toccy: String)  
{
  import scala.xml._
  import java.net._
  import scala.io.Source
  import org.slf4j.Logger
  import org.slf4j.LoggerFactory
  
  val XMLPersisterlogger = LoggerFactory.getLogger(this.getClass)
  
  XMLPersisterlogger.info("Entering XML Persister")
  
  
  def getXML = {
    //log.info("Fetching Currency Pairs")
    
    XML.loadString(Source.fromURL(new URL("http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.xchange%20where%20pair%20in%20(%22USDAUD%22,%22USDEUR%22,%22USDCAD%22,%22USDDKK%22,%22USDHKD%22,%22USDINR%22,%22USDJPY%22,%22USDKRW%22,%22USDBRL%22,%22USDMXN%22,%22USDCLP%22,%22USDPEN%22,%22USDCOP%22,%22USDNZD%22,%22USDNOK%22,%22USDSGD%22,%22USDSEK%22,%22USDCHF%22,%22USDGBP%22,%22USDUSD%22)&env=store://datatables.org/alltableswithkeys")).mkString)     
    
    //log.info("Extraction Complete")
  }  
   
 
  def getexchange(node: scala.xml.Node, ccy:String): Seq[Double] = {
  		 for {
  				      rates <- node \\ "rate"
					    if ( rates \\ "Name").text.takeRight(3) == ccy
						  nodes <- rates \ "Rate"
		
					} yield nodes.text.toDouble
					
			//println(fromccy.head)
  } 

  
  def fxconvertor(fromccy: Seq[Double], toccy: Seq[Double]): Double = {
    //if (!fromccy.isEmpty || !toccy.isEmpty) (1/fromccy.head)*toccy.head
    //else 1
    if (fromccy.isEmpty) 1 
    else if (toccy.isEmpty) 1
    else (1/fromccy.head)*toccy.head
  }
  
}
  
  
