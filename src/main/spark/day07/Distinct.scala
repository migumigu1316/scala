package day07

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  *
  * @ClassName: Distinct
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/26 16:36
  *
  */
object Distinct {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("Distinct").setMaster("local")
    val sc = new SparkContext(conf)
    //接下来进行一个UV的统计案例
    //uv: user view 每天每个用户可能对同一个网站会点击多次
    //此时,需要对用户进行去重,然后统计每天有多少用户访问了网站
    //而不是所有用户访问多少次(PV)
    val arr = Array(
      "user1 2018-9-26 16:45:39",
      "user2 2018-9-26 16:23:40",
      "user3 2018-9-26 16:45:39",
      "user1 2018-9-26 16:36:54",
      "user2 2018-9-26 16:45:35",
      "user3 2018-9-26 16:40:39",
      "user4 2018-9-26 16:45:36",
      "user6 2018-9-26 16:46:39",
      "user2 2018-9-26 16:23:40",
      "user4 2018-9-26 16:47:39",
      "user6 2018-9-26 16:45:39",
      "user7 2018-9-26 16:48:36",
      "user5 2018-9-26 16:45:39",
      "user3 2018-9-26 16:46:34",
      "user1 2018-9-26 16:45:32"
    )
    val logs = sc.parallelize(arr,2)
    val userRDD: RDD[String] = logs.map(t => {
      val lines: Array[String] = t.split(" ")
      lines(0)
    })
    //distinct()去重,计数
    val userCount: Long = userRDD.distinct().count()
    println(userCount)
    sc.stop()
  }
}
