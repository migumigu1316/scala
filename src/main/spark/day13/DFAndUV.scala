package day13

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, Row, SQLContext}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @ClassName: DFAndUV
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/10/9 9:38
  *
  * Spark SQL 内置函数操作
  */
object DFAndUV {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("DFAndUV").setMaster("local")
    val sc = new SparkContext(conf)
    val ssc = new SQLContext(sc)

    //读json格式数据
    val Dframe: DataFrame = ssc.read.json(
      "F:\\IDEA_WorkSpace\\scala\\src\\TestData\\day12\\userlog.json")

    //构建RDD[Row]
    val rowRDD: RDD[Row] = Dframe.rdd.map(t => {
      Row(t.get(0), t.get(1).toString.toInt)//类型的转换
    })

    //接下来,创建structType
    val structType = StructType(Array(
      StructField("data", StringType, true),
      StructField("userid", IntegerType, true)))

    //构建DataFrame
    val df: DataFrame = ssc.createDataFrame(rowRDD,structType)
    //接下来使用Spark SQL中的内置函数进行数据处理
    //注意:使用内置函数,需要导入隐士转换
    import ssc.implicits._

    //首先进行数据的分组,然后进行聚合去重
    //这里注意!!!!-----------导入一个隐士函数不够,还需要导入Spark SQL中的内置函数包
    import org.apache.spark.sql.functions._

    df.groupBy("data").agg('data,countDistinct("userid"))
      //这个地方会有三列数据,那么我们只需要两列就可以了,所以直接用map取值
      .rdd.map(row => Row(row(1),row(2))).collect.foreach(println)

    sc.stop()

  }
}
