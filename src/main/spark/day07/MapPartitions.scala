package day07

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable

/**
  *
  * @ClassName: MapPartitions
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/26 16:16
  *
  */
object MapPartitions {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName("MapPartition")
      .setMaster("local")
    val sc = new SparkContext(conf)
    //准备模拟数据
    val arr = Array("张三", "李四", "王二", "码子")

    val nameRDD = sc.parallelize(arr, 2)//分区数2个

    val hashMap: mutable.HashMap[String, Double] =
      mutable.HashMap("张三" -> 278.5, "李四" -> 290.0, "王二" -> 301.0, "码子" -> 205.0)
    //如果我们调用mapPartitions算子，那么他的里面实现肯定是Iterator集合的方式
    val stuScores: RDD[Double] = nameRDD.mapPartitions(m => {
      var list = mutable.LinkedList[Double]()
      while (m.hasNext) {
        val name = m.next()
        val score: Option[Double] = hashMap.get(name)
        list ++= score
      }
      list.iterator
    })

    stuScores.collect().foreach(println)
    sc.stop()
  }
}
