object test2 {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(58); 
  println("Welcome to the Scala worksheet");$skip(93); 
  
  val PBConfFile = "/Volumes/MyData/SharesProject/FTCompanyData/Configuration/PBDivi.csv"
 case class PBDivi(
 val country : String,
 val PB : Double,
 val Diviyield : Double);System.out.println("""PBConfFile  : String = """ + $show(PBConfFile ));$skip(284); 
 
 val PB = io.Source.fromFile(PBConfFile,"UTF-8").getLines.map(_.split(",")).map {p =>
 val country = p(0)
 val PB = p(1).toDouble
 val Diviyield = p(2).toDouble
 
 PBDivi(country,PB,Diviyield)
 };System.out.println("""PB  : Iterator[test2.PBDivi] = """ + $show(PB ));$skip(22); 
val country = "India";System.out.println("""country  : String = """ + $show(country ));$skip(74); 

val a = for (i <- PB; if i.country == country ) yield (i.PB,i.Diviyield);System.out.println("""a  : Iterator[(Double, Double)] = """ + $show(a ));$skip(36); 
val (pb, diviyield) = a.toList.head;System.out.println("""pb  : Double = """ + $show(pb ));System.out.println("""diviyield  : Double = """ + $show(diviyield ));$skip(48); 
val l = List((2015,6,1),(2015,6,8),(2015,7,21));System.out.println("""l  : List[(Int, Int, Int)] = """ + $show(l ));$skip(74); 

def ls (xs :List[Any]) :List[Any] = xs match {
	case xs => xs drop (1)
};System.out.println("""ls: (xs: List[Any])List[Any]""");$skip(31); val res$0 = 

l map { x => List(x._2,x._3)};System.out.println("""res0: List[List[Int]] = """ + $show(res$0));$skip(84); 

val qq = l groupBy {x => x._1} map {case(k,v) => (k,v map {x => List(x._2,x._3)})};System.out.println("""qq  : scala.collection.immutable.Map[Int,List[List[Int]]] = """ + $show(qq ));$skip(32); val res$1 = 
qq map {case(k,v) => (k,ls(v))};System.out.println("""res1: scala.collection.immutable.Map[Int,List[Any]] = """ + $show(res$1))}

/*
										  sdf.parse(d("RunDate").toString), //2
										  d("Country"), //3
										  d("Exchange"), //4
										  d("RIC"), //5
										  d("IssueName"), //6
										  d("Industry"), //7
										  d("EarningPerShare"), //8
										  d("BookValuePerShare"),
										  d("AnnualDividend"),
										  d("DividendExDate"), //9
										  d("DividendPayDate"), //10
										  fmt((d.getAs[DBObject]("PricesandVolume").get)("Close")).toDouble, //9
										  fmt((d.getAs[DBObject]("PricesandVolume").get)("Open")).toDouble,
										  fmt((d.getAs[DBObject]("PricesandVolume").get)("DayHigh")).toDouble ,
										  fmt((d.getAs[DBObject]("PricesandVolume").get)("DayLow")).toDouble,
										  fmt((d.getAs[DBObject]("PricesandVolume").get)("PreviousClose")).toDouble, //10
										  fmt((d.getAs[DBObject]("PricesandVolume").get)("YearLowPrice")).toDouble, //11
										  fmt((d.getAs[DBObject]("PricesandVolume").get)("YearHighPrice")).toDouble, //12
										  (d.getAs[DBObject]("Currency").get)("ExchangeCCY"), //13
										  (d.getAs[DBObject]("Currency").get)("AnnualReportCCY"), //14
										  fmt((d.getAs[DBObject]("Ratios").get)("PriceToBook")).toDouble, //15
										  fmt((d.getAs[DBObject]("Ratios").get)("AnnualDivYield")).toDouble, //16
										  fmt((d.getAs[DBObject]("Ratios").get)("DebtToEquity")).toDouble, //17
										  fmt((d.getAs[DBObject]("Ratios").get)("ReturnOnEquity")).toDouble, //18
										  fmt((d.getAs[DBObject]("Ratios").get)("OperatingProfitMargin")).toDouble, //19
										  fmt((d.getAs[DBObject]("Ratios").get)("PriceToEarning")).toDouble, //20
										  d("FinanceStatements").toString.takeRight(4) //21 Annual Report Year
										  
										  )).toList//.sortBy( r =>  r._16)(Desc).sortBy(w => w._15) */

/*m map{ x => Array(x._1.toString,
								x._2.toString,
								x._3.toString,
								x._4.toString,
								x._5.toString,
								x._6.toString,
								x._7.toString,
								x._8.toString,
								x._9.toString,
								x._10.toString,
								x._11.toString,
								x._12.toString,
								x._13.toString,
								x._14.toString,
								x._15.toString,
								x._16.toString,
								x._17.toString,
								x._18.toString,
								x._19.toString,
								x._20.toString,
								x._21.toString)
								

} */
/*m filter {x => x._9 > 0} map {x => List(x._1,x._2,x._3,x._4,x._5,x._6,x._7,
x._8,x._9,x._10,x._11,x._12,x._13,x._14,x._15,x._16,x._17,x._18,x._19,x._20,x._21,fmt(((x._9-x._11)/(x._12 - x._11))*100)
) } filter {x => x._22 > 20} */

//m filter {x => x._9 > 0} map {x => fmt(((x._9-x._10)/(x._9))*100).toDouble}

/*(m filter {x => x._9 > 0} map {x =>
List(x._1,x._5,x._6,x._9,x._10,
x._11,x._12,x._13,x._14,x._15,x._16,
x._17,x._18,x._19,x._20,fmt(((x._9-x._10)/(x._9))*100))})*/
//val a = m filter {x => x._9 > 0}
//val n = m filter {x => x._9 > 0} map {x => fmt(((x._9-x._11)/(x._12 - x._11))*100)}


//val s = 5
//val n  =  m.sortBy(w => w._2)(Desc) map (x =>  fmt(x._9).toDouble) filter {x => x > 0 }

//(n.take(5).dropRight(1) zip n.take(5).drop(1)) map {x => log(x._1/x._2)}
//n.take(10)
//val z = (n.take(s).dropRight(1) zip n.take(s).drop(1)) map {x => log(x._1/x._2)}
//val l = z.length
//Average
//val avg = z.map(x => x).sum  / l


// Standard Deviation


//val sd = sqrt(z.map {  x => pow((x - avg),2)}.sum / (l - 1))
// Skewness
//val skew = ((z.map {x => pow((x - avg),3)}.sum)*l)/(pow(sd,3) * (l - 1)*(l -2))
// Excess Kutosis
//val exkurt = (((z.map {x => pow((x - avg),4)}.sum) * l * (l+1))/((l-1)*(l-2)*(l-3)*pow(sd,4))) - ((3*pow(l-1,2))/((l-2)*(l-3)))
// observation greater than 2 SD
//z map {x => if(x > 2*sd) x else 0 } filter {x => x != 0}

// Observations less than 2 SD
//z map {x => if(x < 2* -sd) x else 0 } filter {x => x != 0}
//val(pos,neg) = z partition ( _ > 0)
//pos.length
//pos.sum
//neg.length
//neg.sum

//pos.sum + neg.sum

  
 //m map { x => println(x)}


}
