package day07

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @ClassName: RDDCount
  * @Description: TODO
  * @Author: xqg 
  * @Date: 2018/9/26 14:18
  *
  * 统计文件每次出现的单词的次数
  */
object RDDCount {
  def main(args: Array[String]): Unit = {
    //创建一个sparkConf
    val conf = new SparkConf()
      .setAppName("count")
      .setMaster("local")
    //创建SparkContext的上下文,执行入口
    val sc = new SparkContext(conf)

    //开始统计文件单词
    val lines: RDD[String] = sc.textFile("F://a.txt")

    val words: RDD[(String, Int)] = lines.map((_,1))

    val result: RDD[(String, Int)] = words.reduceByKey(_+_)

    result.foreach(println)
    sc.stop()
  }
}
