package day13

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.types.{DoubleType, StringType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, Row, SQLContext}
import org.apache.spark.{SparkConf, SparkContext}

/**
  *
  * @ClassName: DFAndMoney
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/10/9 10:06
  *
  * spark SQL 内置函数统计每天的总销售额
  */
object DFAndMoney {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("DFAndMoney").setMaster("local")
    val sc = new SparkContext(conf)
    val ssc = new SQLContext(sc)

    //自己选择读取数据方式用.format()
    val Dframe: DataFrame = ssc.read.format("json").load(
      "F:\\IDEA_WorkSpace\\scala\\src\\TestData\\day12\\usermoney.json")

    //构建RDD[Row],用getAS[类型](index)取数据比get(index).toInt好用
    val rowRDD: RDD[Row] = Dframe.rdd.map(t => {
      Row(t.getAs[String](0), t.getAs[Double](1))
    })

    //创建structType
    val structType = StructType(Array(StructField("data", StringType, true),
      StructField("money", DoubleType, true)))

    //接下来构建DataFrame
    val df: DataFrame = ssc.createDataFrame(rowRDD,structType)

    //导入隐士转换
    import ssc.implicits._
    import org.apache.spark.sql.functions._
    //根据时间进行分组，然后在进行聚合money
    df.groupBy("data").agg('data,sum("money"/*聚合函数*/))
      .rdd.map(row=>Row(row(1),row(2))).collect.foreach(println)

    sc.stop()
  }
}
