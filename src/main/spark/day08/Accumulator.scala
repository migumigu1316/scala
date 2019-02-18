package day08

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @ClassName: Accumulator
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/27 15:18
  *
  *  Accumulate 累加器
  */
object Accumulator{
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("accumulator").setMaster("local")
    val sc = new SparkContext(conf)
    //创建一个累加器
    val sum= sc.accumulator(0)
    val RDD: RDD[Int] = sc.parallelize(Array(1,2,3,4,5))
    RDD.foreach(f=>{
      sum += f
    })
    println(sum)
    sc.stop()
  }
}
