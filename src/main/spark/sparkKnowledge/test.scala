package sparkKnowledge

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  *
  * @Description: TODO 
  * @ClassName: test
  * @Author: xqg
  * @Date: 2019/1/18 19:16
  *
  */
object test {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("test").setMaster("local[2]")
    val sc = new SparkContext(conf)
    val value: RDD[String] = sc.parallelize(List("dog","cat","pig","wolf","bee"),3)
    val r: RDD[(Int, String)] = value.keyBy(_.length)

    val list: List[(Int, String)] = r.collect().toList
    println(list)

    println(r.keys.collect().toList + "---------keys--------")
    println(r.values.collect().toList + "---------values--------")

  }
}
