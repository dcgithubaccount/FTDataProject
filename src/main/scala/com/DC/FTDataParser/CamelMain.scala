package com.DC.FTDataParser

import akka.actor.ActorSystem
import akka.actor.Props
import akka.camel.Consumer

object QuartzExample {

  def main(args: Array[String]): Unit = {
    val system = ActorSystem("my-quartz-system")
    system.actorOf(Props[MyQuartzActor])
    //system.shutdown
    println("Ram Ram")
  }

  class MyQuartzActor extends Consumer {

    def endpointUri = "quartz://hello?cron=0+0/2+*+?+*+MON-FRI"

    def receive = {

      case msg  => println("==============> received %s" format msg)

    }

  }

}