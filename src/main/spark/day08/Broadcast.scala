package day08

import org.apache.spark.broadcast.Broadcast
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  *
  * @ClassName: Broadcast
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/27 15:06
  *
  * 广播变量 broadcast
  */
object Broadcast {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("broadcast").setMaster("local")
    val sc = new SparkContext(conf)
    val factor = 3//广播值
    //将factor做成广播变量,让每一个节点只有一份
    val broad: Broadcast[Int] = sc.broadcast(factor)
    val arrRDD: RDD[Int] = sc.parallelize(Array(1,2,3,4,5))
//    arrRDD.map(_*factor)
    val lines: RDD[Int] = arrRDD.map(_ * broad.value)//获取广播变量的值--3
    lines.foreach(println)
    sc.stop()
  }
}
