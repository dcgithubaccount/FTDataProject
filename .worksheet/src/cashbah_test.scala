object cashbah_test  {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(69); 
  
  println("Welcome to the Scala worksheet")
  import com.mongodb.casbah.Imports._;$skip(90); 
  val mongoClient = MongoClient("localhost", 27017);System.out.println("""mongoClient  : com.mongodb.casbah.MongoClient = """ + $show(mongoClient ));$skip(41); 
	val db = mongoClient("CompanyDatabase");System.out.println("""db  : com.mongodb.casbah.MongoDB = """ + $show(db ));$skip(33); 
	val price = db("PriceAndRatio");System.out.println("""price  : com.mongodb.casbah.MongoCollection = """ + $show(price ));$skip(43); 
	val statement = db("FinancialStatements");System.out.println("""statement  : com.mongodb.casbah.MongoCollection = """ + $show(statement ));$skip(23); 
	val country = "LATAM";System.out.println("""country  : String = """ + $show(country ));$skip(156); 
	val query = MongoDBObject("RunDate" -> "20-02-2015") ++ ("Country" -> country) ++ ("Ratios.AnnualDivYield" $gte 0.025) ++ ("Ratios.PriceToBook" $lte 2.00);System.out.println("""query  : com.mongodb.casbah.commons.Imports.DBObject = """ + $show(query ));$skip(343); 
  //val query =  MongoDBObject("Symbol" -> "VOD:LSE")
  val fields = MongoDBObject("Symbol" -> 1,
  													 "RunDate" -> 2,
  													 "Exchange" -> 3,
  													 "Currency" -> 4,
  													 "Ratios" -> 5,
  													 "FinanceStatements" -> 6,
  													 "IssueName" -> 7,
  													 "PricesandVolume" -> 8);System.out.println("""fields  : com.mongodb.casbah.commons.Imports.DBObject = """ + $show(fields ));$skip(32); 
  	
val ct = price.count(query);System.out.println("""ct  : Int = """ + $show(ct ));$skip(51); 
val stquery = MongoDBObject("_id" -> "CM:TOR2014");System.out.println("""stquery  : com.mongodb.casbah.commons.Imports.DBObject = """ + $show(stquery ));$skip(46); 
for (s <- statement.find(stquery)) println(s);$skip(632); 
val m = for (d <- price.find(query,fields)) yield  (d("Symbol"),
																						 d("RunDate"),
																						 d("IssueName"),
																						 (d.getAs[DBObject]("Ratios").get)("PriceToBook"),
																						 (d.getAs[DBObject]("Ratios").get)("AnnualDivYield"),
																						 (d.getAs[DBObject]("Ratios").get)("DebtToEquity"),
																						(d.getAs[DBObject]("PricesandVolume").get)("Close"),
																						(d.getAs[DBObject]("PricesandVolume").get)("PreviousClose")
																						//statement.find(MongoDBObject("_id" -> d("FinanceStatements")))
																						);System.out.println("""m  : Iterator[(AnyRef, AnyRef, AnyRef, AnyRef, AnyRef, AnyRef, AnyRef, AnyRef)] = """ + $show(m ));$skip(34); 
  
 m.foreach  { x => println(x)}}
                                                  
                                                  
 
  
}
