object test3 {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(58); 
  println("Welcome to the Scala worksheet")
  import scala.math._;$skip(130); 
  def mean (xs: Seq[Double]): Option[Double] = {
  	if (xs.isEmpty) None
  	else Some(xs.sum/xs.length)
  };System.out.println("""mean: (xs: Seq[Double])Option[Double]""");$skip(39); 
  
  val l = List(3.0,4,5,6,8,9,10,11);System.out.println("""l  : List[Double] = """ + $show(l ));$skip(41); 
  
  def g(v:Double) = List(v-1, v, v+1);System.out.println("""g: (v: Double)List[Double]""");$skip(20); val res$0 = 
  l map {x => g(x)};System.out.println("""res0: List[List[Double]] = """ + $show(res$0));$skip(24); val res$1 = 
  l flatMap {x => g(x)};System.out.println("""res1: List[Double] = """ + $show(res$1));$skip(62); val res$2 = 
  mean(l);System.out.println("""res2: Option[Double] = """ + $show(res$2));$skip(157);  //flatMap {m => mean(l map {x => math.pow(x-m,2)})}
  //l flatMap { x => x - mean(x)}
  
  
  
  def variance(xs: Seq[Double]): Option[Double] =
  mean(xs) flatMap (m => mean(xs.map(x => math.pow(x - m, 2))));System.out.println("""variance: (xs: Seq[Double])Option[Double]""");$skip(13); val res$3 = 
 variance(l);System.out.println("""res3: Option[Double] = """ + $show(res$3));$skip(181); 
  
  
  //import scala.util.{Try, Success, Failure}
  
  def map2[A,B,C](a :Option[A], b :Option[B]) (f :(A,B) => C) :Option[C] = {
  	a flatMap {aa => b map {bb => f(aa,bb)} }
  };System.out.println("""map2: [A, B, C](a: Option[A], b: Option[B])(f: (A, B) => C)Option[C]""");$skip(97); 
  
  def Try[A] (a: => A) :Option[A] = {
  	try Some(a)
  	catch {case e :Exception => None}
  };System.out.println("""Try: [A](a: => A)Option[A]""");$skip(117); 
  def insuranceRateQuote(age :Int, numberOfSpeedingTickets :Int) :Double = {
  	5 *age + numberOfSpeedingTickets
  };System.out.println("""insuranceRateQuote: (age: Int, numberOfSpeedingTickets: Int)Double""");$skip(264); 
  
  def parseInsranceRateQuote(age :String, numberOfSpeedingTickets : String) :Option[Double] = {
  	val optAge : Option[Int] = Try{age.toInt}
  	val optTickets :Option[Int] = Try{numberOfSpeedingTickets.toInt}
  	map2(optAge,optTickets) (insuranceRateQuote)
  };System.out.println("""parseInsranceRateQuote: (age: String, numberOfSpeedingTickets: String)Option[Double]""");$skip(37); val res$4 = 
    parseInsranceRateQuote("21","7");System.out.println("""res4: Option[Double] = """ + $show(res$4));$skip(166); 
    
  def sequence[A](a :List[Option[A]]) :Option[List[A]] = a match {
    	case Nil => Some(Nil)
    	case h::t => h flatMap { hh => sequence(t) map {hh::_}}
    }
    
import scala.io.Source
import java.net.URL;System.out.println("""sequence: [A](a: List[Option[A]])Option[List[A]]""");$skip(241); 

def getContent(url: URL): Either[String, Source] =
  if (url.getHost.contains("google"))
    Left("Requested URL is blocked for the good of the people!")
  else
    Right(Source.fromURL(url));System.out.println("""getContent: (url: java.net.URL)Either[String,scala.io.Source]""");$skip(191); 

getContent(new URL("http://markets.ft.com/research/Markets/Tearsheets/Summary?s=GG:NYQ")) match {
  case Left(msg) => println(msg)
  case Right(source) => source.getLines.foreach(println)
};$skip(163); 


val content: Either[String, Iterator[String]] =
	getContent(new URL("http://markets.ft.com/research/Markets/Tearsheets/Summary?s=GG:NYQ")).right.map(_.getLines);System.out.println("""content  : Either[String,Iterator[String]] = """ + $show(content ));$skip(117); 
val moreContent : Either[String, Iterator[String]] =
	getContent(new URL("http://google.com")).right.map(_.getLines);System.out.println("""moreContent  : Either[String,Iterator[String]] = """ + $show(moreContent ));$skip(50); val res$5 = 
	
	(content match  {
	case Right(x) => x}).toList;System.out.println("""res5: List[String] = """ + $show(res$5))}
    
}
