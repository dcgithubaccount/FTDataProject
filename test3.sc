object test3 {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  import scala.math._
  def mean (xs: Seq[Double]): Option[Double] = {
  	if (xs.isEmpty) None
  	else Some(xs.sum/xs.length)
  }                                               //> mean: (xs: Seq[Double])Option[Double]
  
  val l = List(3.0,4,5,6,8,9,10,11)               //> l  : List[Double] = List(3.0, 4.0, 5.0, 6.0, 8.0, 9.0, 10.0, 11.0)
  
  def g(v:Double) = List(v-1, v, v+1)             //> g: (v: Double)List[Double]
  l map {x => g(x)}                               //> res0: List[List[Double]] = List(List(2.0, 3.0, 4.0), List(3.0, 4.0, 5.0), Li
                                                  //| st(4.0, 5.0, 6.0), List(5.0, 6.0, 7.0), List(7.0, 8.0, 9.0), List(8.0, 9.0, 
                                                  //| 10.0), List(9.0, 10.0, 11.0), List(10.0, 11.0, 12.0))
  l flatMap {x => g(x)}                           //> res1: List[Double] = List(2.0, 3.0, 4.0, 3.0, 4.0, 5.0, 4.0, 5.0, 6.0, 5.0, 
                                                  //| 6.0, 7.0, 7.0, 8.0, 9.0, 8.0, 9.0, 10.0, 9.0, 10.0, 11.0, 10.0, 11.0, 12.0)
  mean(l) //flatMap {m => mean(l map {x => math.pow(x-m,2)})}
                                                  //> res2: Option[Double] = Some(7.0)
  //l flatMap { x => x - mean(x)}
  
  
  
  def variance(xs: Seq[Double]): Option[Double] =
  mean(xs) flatMap (m => mean(xs.map(x => math.pow(x - m, 2))))
                                                  //> variance: (xs: Seq[Double])Option[Double]
 variance(l)                                      //> res3: Option[Double] = Some(7.5)
  
  
  //import scala.util.{Try, Success, Failure}
  
  def map2[A,B,C](a :Option[A], b :Option[B]) (f :(A,B) => C) :Option[C] = {
  	a flatMap {aa => b map {bb => f(aa,bb)} }
  }                                               //> map2: [A, B, C](a: Option[A], b: Option[B])(f: (A, B) => C)Option[C]
  
  def Try[A] (a: => A) :Option[A] = {
  	try Some(a)
  	catch {case e :Exception => None}
  }                                               //> Try: [A](a: => A)Option[A]
  def insuranceRateQuote(age :Int, numberOfSpeedingTickets :Int) :Double = {
  	5 *age + numberOfSpeedingTickets
  }                                               //> insuranceRateQuote: (age: Int, numberOfSpeedingTickets: Int)Double
  
  def parseInsranceRateQuote(age :String, numberOfSpeedingTickets : String) :Option[Double] = {
  	val optAge : Option[Int] = Try{age.toInt}
  	val optTickets :Option[Int] = Try{numberOfSpeedingTickets.toInt}
  	map2(optAge,optTickets) (insuranceRateQuote)
  }                                               //> parseInsranceRateQuote: (age: String, numberOfSpeedingTickets: String)Optio
                                                  //| n[Double]
    parseInsranceRateQuote("21","7")              //> res4: Option[Double] = Some(112.0)
    
  def sequence[A](a :List[Option[A]]) :Option[List[A]] = a match {
    	case Nil => Some(Nil)
    	case h::t => h flatMap { hh => sequence(t) map {hh::_}}
    }                                             //> sequence: [A](a: List[Option[A]])Option[List[A]]
    
import scala.io.Source
import java.net.URL

def getContent(url: URL): Either[String, Source] =
  if (url.getHost.contains("google"))
    Left("Requested URL is blocked for the good of the people!")
  else
    Right(Source.fromURL(url))                    //> getContent: (url: java.net.URL)Either[String,scala.io.Source]

getContent(new URL("http://markets.ft.com/research/Markets/Tearsheets/Summary?s=GG:NYQ")) match {
  case Left(msg) => println(msg)
  case Right(source) => source.getLines.foreach(println)
}                                                 //> <!DOCTYPE html>
                                                  //| <!--[if lt IE 7 ]><html lang="en" class="msie msie6 core"><![endif]-->
                                                  //| <!--[if IE 7 ]><html lang="en" class="msie msie7 core"><![endif]-->
                                                  //| <!--[if IE 8 ]><html lang="en" class="msie msie8 core"><![endif]-->
                                                  //| <!--[if IE 9 ]><html lang="en" class="msie msie9 core"><![endif]-->
                                                  //| <!--[if (gt IE 9)|!(IE)]><!--><html class="core" lang="en" xmlns="http://ww
                                                  //| w.w3.org/1999/xhtml"><!--<![endif]-->
                                                  //| <head>
                                                  //| <style id="antiClickjack">body{display:none !important;}</style>
                                                  //| <script type="text/javascript">
                                                  //| if (self === top) {
                                                  //| var antiClickjack = document.getElementById("antiClickjack");
                                                  //| antiClickjack.parentNode.removeChild(antiClickjack);
                                                  //| } else {
                                                  //| top.location = self.location;
                                                  //| }
                                                  //| </script>
                                                  //| <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
                                                  //| <meta charset="UTF-8" />
                                                  //| <meta property="fb:admins" content="653531613" />
                                                  //| <title>Goldcorp Inc, GG:NYQ summary - FT.com</title>
                                                  //| <m
                                                  //| Output exceeds cutoff limit.


val content: Either[String, Iterator[String]] =
	getContent(new URL("http://markets.ft.com/research/Markets/Tearsheets/Summary?s=GG:NYQ")).right.map(_.getLines)
                                                  //> content  : Either[String,Iterator[String]] = Right(non-empty iterator)
val moreContent : Either[String, Iterator[String]] =
	getContent(new URL("http://google.com")).right.map(_.getLines)
                                                  //> moreContent  : Either[String,Iterator[String]] = Left(Requested URL is bloc
                                                  //| ked for the good of the people!)
	
	(content match  {
	case Right(x) => x}).toList               //> res5: List[String] = List(<!DOCTYPE html>, <!--[if lt IE 7 ]><html lang="en
                                                  //| " class="msie msie6 core"><![endif]-->, <!--[if IE 7 ]><html lang="en" clas
                                                  //| s="msie msie7 core"><![endif]-->, <!--[if IE 8 ]><html lang="en" class="msi
                                                  //| e msie8 core"><![endif]-->, <!--[if IE 9 ]><html lang="en" class="msie msie
                                                  //| 9 core"><![endif]-->, <!--[if (gt IE 9)|!(IE)]><!--><html class="core" lang
                                                  //| ="en" xmlns="http://www.w3.org/1999/xhtml"><!--<![endif]-->, <head>, <style
                                                  //|  id="antiClickjack">body{display:none !important;}</style>, <script type="t
                                                  //| ext/javascript">, if (self === top) {, var antiClickjack = document.getElem
                                                  //| entById("antiClickjack");, antiClickjack.parentNode.removeChild(antiClickja
                                                  //| ck);, } else {, top.location = self.location;, }, </script>, <meta http-equ
                                                  //| iv="X-UA-Compatible" content="IE=edge,chrome=1" />, <meta charset="UTF-8" /
                                                  //| >, <meta property="fb:admins" content="653531613" />, <title>Goldcorp Inc, 
                                                  //| GG:NYQ summary - FT.com
                                                  //| Output exceeds cutoff limit.
    
}