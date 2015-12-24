package com.DC.FTDataParser

import akka.actor.Actor
import akka.actor.Props
import akka.actor.ActorSystem
import scala.concurrent.duration._
import java.util.Calendar
import com.DC.FTDataParser.XMLPersister._
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat


class tasks extends Actor {

 import context.dispatcher
 val fmt = DateTimeFormat.forPattern("yyyy-mm-dd HH:mm:ss:SSS")


 val dt = new DateTime()
 
 def runactor = {
  val dat = new DateTime()
    if (dat.getDayOfWeek()==3 && dat.getHourOfDay() == 22 && (dat.getMinuteOfHour() >= 35 && dat.getMinuteOfHour() <= 40)) context.stop(self)  
    else println("Please do something"  ,new DateTime())  
  
}
  def getsetTime(date : org.joda.time.DateTime) : org.joda.time.DateTime = {
      val d1 = date.getMinuteOfHour()

      d1%5 match {
        case 0 => date.plusMinutes(5)
        case 1 => date.plusMinutes(4)
        case 2 => date.plusMinutes(3)
        case 3 => date.plusMinutes(2)
        case 4 => date.plusMinutes(1)
      }

  }

  val newDate =            fmt.parseDateTime(getsetTime(dt).toString(fmt)).withSecondOfMinute(0).withMillisOfSecond(0)

  val delay = newDate.getSecondOfDay() - dt.getSecondOfDay() 

  val tick = context.system.scheduler.schedule(delay seconds,5     minutes,self,"tick")

 override def postStop() = tick.cancel()

 def receive = {
   case "tick" =>runactor 
                 
     }

 }



object Main extends App {

  val system = ActorSystem("MyActor")
  val actor = system.actorOf(Props[tasks],"Task")
  
  
  
  actor ! "tick"

  system.shutdown()
  
  
  
}



