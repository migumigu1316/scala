package day12

import org.apache.spark.sql.{DataFrame, SQLContext}
import org.apache.spark.{SparkConf, SparkContext}

/**
  *
  * @ClassName: DataFrameCreate
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/10/8 10:55
  *
  *        spark sql 入门
  */
object DataFrameCreate {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("DF").setMaster("local")
    val sc = new SparkContext(conf)
    val ssc = new SQLContext(sc)
    val df: DataFrame = ssc.read.text("F:\\IDEA_WorkSpace\\scala\\src\\TestData\\day12\\a.txt")
    df.show()
  }
}
