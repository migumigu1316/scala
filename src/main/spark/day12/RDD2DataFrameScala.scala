package day12

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, Row, SQLContext}
import org.apache.spark.{SparkConf, SparkContext}

/**
  *
  * @ClassName: RDD2DataFrameScala
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/10/8 16:23
  *
  * 第二种方式: 使用编程的方式构造DF
  */
object RDD2DataFrameScala {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("DF2").setMaster("local")
    val sc = new SparkContext(conf)
    val ssc = new SQLContext(sc)

    //创建一个普通的RDD
    val files: RDD[String] = sc.textFile(
      "F:\\IDEA_WorkSpace\\scala\\src\\TestData\\day12\\students.txt")
    val rowRDD: RDD[Row] = files.map(t => {
      //切割数据
      val splits: Array[String] = t.split(",")
      //自己给字段定义一个类型
      //Row类型(第一个数据(类型),第二个数据(类型),第三个数据(类型))
      Row(splits(0).toInt, splits(1), splits(2).toInt)
    })

    //构建 structType
    val structType: StructType = StructType(Array(
      StructField("id", IntegerType, true),
      StructField("name", StringType, true),
      StructField("age", IntegerType, true)//字段,数据类型,是否为空值,默认是true
    ))
    //构建DataFrame
    val df: DataFrame = ssc.createDataFrame(rowRDD/*数据*/,structType/*可以理解为:表的每个字段的类型*/)

    //注册临时表
    df.registerTempTable("stu")

    //sql操作
    val sql: DataFrame = ssc.sql("select * from stu where age >= 17")

    //进行转换成RDD
    val rdd: RDD[Row] = sql.rdd

    //RDD打印
    rdd.foreach(println)
  }
}
