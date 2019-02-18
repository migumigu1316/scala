package day13

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.types.{StringType, StructField, StructType}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{Row, SQLContext}

/**
  *
  * @ClassName: UDF
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/10/9 11:55
  *
  * spark SQL 自定义 UDF
  */
object UDF {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("UDF").setMaster("local")
    val sc = new SparkContext(conf)
    val ssc = new SQLContext(sc)

    //构造模拟数据
    val names =Array("leo","kitty","jack","tom")

    val namesRDD: RDD[String] = sc.parallelize(names,5)
    val rowRDD: RDD[Row] = namesRDD.map(t=>Row(t))
    val structType = StructType(Array(StructField("name",StringType,true)))
    val df = ssc.createDataFrame(rowRDD,structType)
    //注册临时表
    df.registerTempTable("names")
    //定义和注册自定义函数
    //1.定义函数：自己写匿名函数
    //2.注册自定义函数
    ssc.udf.register("strLen",(str:String)=>str.length)
    //执行SQL了
    ssc.sql("select name,strLen(name) from names").collect.foreach(println)

    sc.stop()

  }
}
