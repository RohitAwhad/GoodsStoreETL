package com.test

import java.io.File

import com.demo.unifiedLoader.getWS
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.{DoubleType, LongType, StringType, StructType}
import org.scalatest.{BeforeAndAfterAllConfigMap, ConfigMap}
import org.scalatest.funsuite.AnyFunSuite

class unified_loader_suite extends AnyFunSuite with BeforeAndAfterAllConfigMap {
  private val master = "local[*]"
  private val appName = "ReadFileTest"
  var sourceDataPath: String = _
  var sparks : SparkSession = _
  var url:String = _
  var username:String = _
  var password:String = _
  var enumvalues:String = _

  val schema =  new StructType()
    .add("created",StringType,true)
    .add("customer",StringType,true)
    .add("item_id",DoubleType,true)

  override def beforeAll(configMap: ConfigMap): Unit = {
    sparks = SparkSession.builder().appName(appName).master(master).getOrCreate()
    url = configMap.getRequired[String]("url")
    username = configMap.getRequired[String]("username")
    password = configMap.getRequired[String]("password")
    enumvalues = configMap.getRequired[String]("enumvalues")

  }

  def validateDepartment(): Boolean = {

    val data_df = sparks.read.format("jdbc")
      .option("url", url)
      .option("dbtable","(select distinct department from item) items")
      .option("user", username)
      .option("password", password)
      .option("driver", "org.postgresql.Driver").load

    val dataDepartment = data_df.collect().map(_.getString(0)).toSet
    return dataDepartment.subsetOf(enumvalues.split(",").toSet)
  }

  test("validateDepartment"){
    assert(validateDepartment())
  }
}
