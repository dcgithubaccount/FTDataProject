package com.DC.FTDataParser

object XMLPersister //(fromccy: String, toccy: String)  
{
  import scala.xml._
  import java.net._
  import scala.io.Source
  import scala.util.Try
  import org.slf4j.Logger
  import org.slf4j.LoggerFactory
  
  val XMLPersisterlogger = LoggerFactory.getLogger(this.getClass)
  
  XMLPersisterlogger.info("Entering XML Persister")
  
  
  
  def getXML(url: String, retry : Int): Try[scala.xml.Node] =  
  {
    
     Try(XML.loadString(Source.fromURL(new URL(url)).mkString)).recoverWith {
       case _ if(retry > 0 ) => {
         Thread.sleep(30000)
         getXML(url,retry -1)
       }
     }
   
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
  
  
