package com.DC.FTDataParser

trait DBConnection {
import com.mongodb.casbah.Imports._
val mongoClient = MongoClient("localhost", 27017)
val db = mongoClient("CompanyDatabase")  
val price = db("PriceAndRatio")
val statement = db("FinancialStatements")
val Industry = db("Industry")
}