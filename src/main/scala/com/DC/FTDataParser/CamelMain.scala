package com.DC.FTDataParser

import akka.actor.ActorSystem
import akka.actor.Props
import akka.camel.Consumer
import au.com.bytecode.opencsv.CSVWriter
import scala.collection.JavaConversions._
import java.io.FileWriter
import java.io.BufferedWriter
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import scala.xml._
import scala.io.Source
import java.net.URL

object currencyPullerCron {

  def main(args: Array[String]): Unit = {
    val system = ActorSystem("CurrencyExtractor-System")
    system.actorOf(Props[CurrencyExtractorActor])
    //system.shutdown
    //println("Ram Ram")
  }

  class CurrencyExtractorActor extends Consumer {
  
    def currencyWriter(baseccy :String) = {

     case class Currency (
	    val Name : String,
	    val Rate : String,
	    val Date : String,
	    val Time : String,
	    val Bid : String,
	    val Ask : String
	  )

	val d1 = new DateTime()
	val filename = d1.getYear().toString + d1.getMonthOfYear().toString + d1.getDayOfMonth().toString + d1.getHourOfDay().toString + d1.getMinuteOfHour().toString + ".csv"
	
	val init = "http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.xchange%20where%20pair%20in%20("
	val last = ")&env=store://datatables.org/alltableswithkeys"
	val ccy = Array("RUB","MXN","INR","KRW","TRY","GBP","PLN","NZD","COP","AUD","CAD","THB","CLP","USD","EUR","SEK","SGD","NOK","JPY","ZAR","MYR","TWD","CHF","PHP","ILS","IDR","CNY","BRL","PEN","HUF","DKK")
	val base = baseccy
	val ccystring = (ccy map {x => "%22"+base + x + "%22"}).mkString(",")
	
	val finalurlString = init+ccystring+last
	val xchange =  XML.loadString(Source.fromURL(new URL(finalurlString)).mkString)
	
	val listCcy = (xchange \ "results" \ "rate").map {
					n =>
					val Name = (n \ "Name").text
					val Rate = (n \ "Rate").text
					val Date = (n \ "Date").text
					val Time = (n \ "Time").text
					val Bid  = (n \ "Bid").text
					val Ask  = (n \ "Ask").text

					Currency(Name,Rate,Date,Time,Bid,Ask)

				} filter {x => x.Name != "N/A"} map {x => Array(x.Name.toString,
																												x.Rate.toString,
																												x.Date.toString,
																												x.Time.toString)}
					
	
	val out = new BufferedWriter(new FileWriter("/home/nidhideepak/workspace/FTDataProject/" + filename))
  val writer = new CSVWriter(out)
  		writer.writeAll(listCcy)
      out.close()


}                                                 

    def endpointUri = "quartz://XMLExtractor?cron=0+0/15+*+?+*+MON-FRI"

    def receive = {

      //case msg  => println("==============> received %s" format msg)
      case msg => currencyWriter("USD")
    }

  }

}