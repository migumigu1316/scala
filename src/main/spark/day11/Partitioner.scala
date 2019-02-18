package day11

import java.net.URL

import org.apache.spark.rdd.RDD
import org.apache.spark.{Partitioner, SparkConf, SparkContext}

/**
  *
  * @ClassName: Partitioner
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/30 10:05
  *
  * 自定义分区器
  */
object Partitioner {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("Partitioner").setMaster("local")
    val sc = new SparkContext(conf)
    val files: RDD[String] = sc.textFile(
      "F:\\IDEA_WorkSpace\\scala\\src\\TestData\\partitions.txt",5)
    val tuples: RDD[(String, Int)] = files.map(t => {
      val url = new URL(t)
      //获取学科名字
      val host: String = url.getHost
      //      val s: Array[String] = host.split(".")
      //      s(0)
      val str: String = host.substring(0, host.indexOf("."))
      (str, 1)
    })

    //开始使用分区器
    val partitioned = new Partitioned

    val result: RDD[(String, Int)] = tuples.reduceByKey(_+_)

    //没有使用自定义分区器,会产生Hash碰撞,会导致某一分区数据很多,有的分区数据少的可怜
    result.saveAsTextFile("hdfs://hadoop01:9000/partitions")

  }
}

class Partitioned extends Partitioner with Serializable{
  //Map写成循环形式
  val map = Map("java"->1,"ui"->2,"bigdata"->3,"android"->4,"h5"->5)
  override def numPartitions: Int = map.size + 1

  override def getPartition(key: Any): Int = {
    map.getOrElse(key.toString,0)//如果没有指定的分区,把数据放到0分区中
  }
}