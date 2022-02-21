package com.demo
import com.typesafe.config.ConfigFactory
import org.apache.log4j.Logger
import org.apache.spark.SparkConf
import org.apache.spark.sql.functions.{col, length, to_timestamp}
import org.apache.spark.sql.{SparkSession, functions}
import scalaj.http.Http
import scalaj.http.HttpOptions
import scalaj.http.HttpResponse

object unifiedLoader {

  def getWS(url: String, charSet: String, timeout: Int): scalaj.http.HttpResponse[String] = {
    var response: HttpResponse[String] = null
    try {
      response = Http(url)
        .header("Charset", charSet)
        .option(HttpOptions.readTimeout(timeout)).option(HttpOptions.connTimeout(9000)).asString
      response
    } catch { case ex: Exception => {  println(ex) }}
    response
  }

  def main(args: Array[String]): Unit = {

    val logger = Logger.getLogger(getClass.getName)
    val app_config = ConfigFactory.load("application.conf")
    val apiPath =  app_config.getValue("project.dataPath.path").unwrapped().toString
    val islocal = app_config.getValue("project.spark.islocal").unwrapped().toString
    val url = app_config.getValue("project.DB.url").unwrapped().toString
    val username = app_config.getValue("project.DB.username").unwrapped().toString
    val password = app_config.getValue("project.DB.password").unwrapped().toString
    val table = app_config.getValue("project.DB.table").unwrapped().toString
    val sparkConf = new SparkConf().setAppName("ConfluentConsumer")

    if("true".equalsIgnoreCase(islocal))
      sparkConf.setMaster("local[*]")
    logger.info(url)
    logger.info(username)
    logger.info(password)
    val spark =SparkSession.builder().config(sparkConf).getOrCreate()

    val postgresql_df = spark.read.format("jdbc")
      .option("url", url)
      .option("dbtable",table)
      .option("user", username)
      .option("password", password)
      .option("driver", "org.postgresql.Driver").load

    val ordersRes = getWS(apiPath,"UTF-8",8000)
    import spark.implicits._
    val ordersDf = spark.sparkContext.parallelize(Seq(ordersRes.body)).toDF
    val jsonDF = spark.read.json(ordersDf.select("value").rdd.map(x=>x(0).toString))
     .withColumn("created",to_timestamp(col("created")))

    val finalDF = jsonDF.join(postgresql_df,Seq("item_id"),"LEFT_OUTER")

    finalDF.write.format("jdbc")
      .option("driver", "org.postgresql.Driver")
      .option("url", url)
      .option("dbtable","staging.orders")
      .option("user", username)
      .option("password", password)
      .mode("append").save()

  }
}
