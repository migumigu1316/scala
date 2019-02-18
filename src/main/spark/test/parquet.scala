package test

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.{DataFrame, SparkSession}

/**
  *
  * @ClassName: parquet
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/11/10 9:44
  *
  */
object parquet {
  //屏蔽不必要的日志显示在终端上
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
  Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)

  def main(args: Array[String]): Unit = {
    //    val conf: SparkCo
    val spark = SparkSession
      .builder()
      .appName("parquet")
      .master("local[2]")
      .getOrCreate()
    //导入隐式转换
    import spark.implicits._
    //导入Spark SQL中的内置函数functions
    import org.apache.spark.sql.functions._
//    val df: DataFrame = spark.read.parquet("F:\\IDEA_WorkSpace\\scala\\src\\TestData\\parquetData\\20180715.parquet")
    val df: DataFrame = spark.read.parquet("hdfs:hadoop01:9000/data/parquet/20180715.parquet")

    df.show(100,false)


    /**
      * +--------------------+--------------------+------+--------+------+-------------+--------------------+
      * |               reqid|           sessionid|device|  source|status|           ct|                exts|
      * +--------------------+--------------------+------+--------+------+-------------+--------------------+
      * |20180725122704wqguuf|20180725122704cwospi|   dwx|ximalaya|    00|1532492824247|       Map(bid -> 1)|
      * |20180721122704mgnhbg|20180721122704enkbpr|   jmg| toutiao|    00|1532147224251|       Map(bid -> 5)|
      * |20180707122704fdoqns|20180707122704drveqi|   duq|ximalaya|    01|1530937624252|Map(code -> 1, pr...|
      * +--------------------+--------------------+------+--------+------+-------------+--------------------+
      */

    df.printSchema()

    /**
      * root
      * |-- reqid: string (nullable = true)
      * |-- sessionid: string (nullable = true)
      * |-- device: string (nullable = true)
      * |-- source: string (nullable = true)
      * |-- status: string (nullable = true)
      * |-- ct: long (nullable = true)
      * |-- exts: map (nullable = true)
      * |   |-- key: string
      * |   |-- value: string (valueContainsNull = true)
      */
    spark.stop()
  }
}
