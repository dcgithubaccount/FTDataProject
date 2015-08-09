import AssemblyKeys._

//assemblyJarName in assembly := "FTDataProject.jar"

name := "FTDataProject"

version := "1.0"

scalaVersion := "2.11.1"

libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.1.7" % "test"

libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "1.0.2"

libraryDependencies += "org.slf4j" % "slf4j-api" % "1.7.7"

libraryDependencies += "ch.qos.logback" % "logback-core" % "1.1.2"                           

libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.1.2"                            

libraryDependencies += "org.mongodb" %% "casbah" % "2.7.3"  

libraryDependencies += "com.typesafe.akka" % "akka-actor_2.10" % "2.3.6"

libraryDependencies += "net.sf.opencsv" % "opencsv" % "2.3"

libraryDependencies += "com.typesafe.akka" % "akka-camel_2.10" % "2.3.6"

libraryDependencies += "org.apache.camel" % "camel-scala" % "2.14.0"               

libraryDependencies += "org.scalanlp" % "breeze_2.11" % "0.11.2" 

libraryDependencies += "org.scalanlp" % "breeze-natives_2.11" % "0.11.2"

libraryDependencies += "org.scalanlp" % "breeze-viz_2.11" % "0.11.2"




resolvers ++= Seq(
  //"Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/",
  "Sonatype Releases" at "https://oss.sonatype.org/content/repositories/releases/"
)

mainClass in assembly := Some("com.DC.FTDataParser.MainApp")
                            






