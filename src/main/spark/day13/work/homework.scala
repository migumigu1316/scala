package day13.work

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.types._
import org.apache.spark.sql.{DataFrame, Row, SQLContext}
import org.apache.spark.{SparkConf, SparkContext}

/**
  *
  * @ClassName: homework
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/10/9 20:28
  *
  * 作业：过滤掉小于三个字段的数据，然后进行总价的聚合，用内置函数实现
  */
object homework {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setMaster("local")
      .setAppName("DailySale")
    val sc = new SparkContext(conf)
    val ssc = new SQLContext(sc)

    val userSaleLog = Array(
      "2018-1-01,55.05,110",
      "2018-1-01,23.15,113",
      "2018-1-01,15.20,",
      "2018-1-02,56.05,114",
      "2018-1-02,78.87,115",
      "2018-1-02,113.02,112")
    val data: RDD[String] = sc.parallelize(userSaleLog,5)

    //过滤掉小于三个字段的数据
    val lines: RDD[String] = data.filter(f => {
      f.split(",").length > 2
    })

    //构建RDD[Row]
    val rowRDD: RDD[Row] = lines.map(t => {
      val files: Array[String] = t.split(",")
      Row(files(0), files(1).toDouble, files(2).toInt)
    })

    //接下来,创建structType
    val structType = StructType(Array(
      StructField("date", StringType, true),
      StructField("price", DoubleType, true),
      StructField("other", IntegerType, true)))

    //接下来构建DataFrame
    val df: DataFrame = ssc.createDataFrame(rowRDD,structType)

//    //注册临时表
//    df.registerTempTable("sell")

    df.show()

    //导入隐士转换
    import ssc.implicits._
    import org.apache.spark.sql.functions._

    //然后进行总价的聚合，用内置函数实现
    df.groupBy("date").agg('date,sum("price")).show()

    sc.stop()
  }
}
