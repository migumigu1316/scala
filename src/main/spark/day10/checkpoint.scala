package day10

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  *
  * @ClassName: checkpoint
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/29 16:06
  * checkpoint 检查点
  */
object checkpoint {

  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("checkpoint").setMaster("local")
//    val conf: SparkConf = new SparkConf().setAppName("checkpoint")
    val sc = new SparkContext(conf)
    //接下来进行检查点机制的创建
    sc.setCheckpointDir("hdfs://hadoop01:9000/checkpoint")

    //从集群上读取数据
    val files: RDD[String] = sc.textFile("hdfs://hadoop01:9000/Spark/a.txt")

    //处理数据
    val words: RDD[String] = files.flatMap(_.split(" "))
    val tuples: RDD[(String, Int)] = words.map((_,1))
    val result: RDD[(String, Int)] = tuples.reduceByKey(_ + _)
    //官网建议我们在做checkpoint操作的时候,先进行持久化操作,也就是为了防止我们中间的结果出现问题
    //数据丢失,前面的运行还没到checkpoint这一步,我们可以做一个cache缓存,下次这些数据直接在cache缓存中
    //再次计算时,就不用从头计算,如果我们的checkpoint执行成功的话,那么之前所做的缓存将被销毁
    result.cache().checkpoint()
    //输出数据
    result.foreach(println)

  }
}
