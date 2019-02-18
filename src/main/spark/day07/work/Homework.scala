package day07.work

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

import scala.collection.mutable

/**
  *
  * @ClassName: SparkWordCount
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/26 22:32
  *
  */
object Homework {
  //  使用两种方式创建RDD并将数据进行处理，统计单词出现的数量
  def SparkWordCount(): Unit = {
    //创建一个sparkConf
    val conf = new SparkConf()
      .setAppName("SparkWordCount")
      .setMaster("local")
//    val conf = new SparkConf().setAppName("count").setMaster("hadoop01:7077")
    //创建SparkContext的上下文,执行入口
    val sc = new SparkContext(conf)
    //开始统计文件单词
    val lines: RDD[String] = sc.textFile("F://a.txt")
    val words: RDD[(String, Int)] = lines.map((_,1))
    val result: RDD[(String, Int)] = words.reduceByKey(_+_)
    result.foreach(println)
    sc.stop()
  }

  def SparkWordCount1(args: Array[String]): Unit = {
    //创建一个sparkConf
    val conf = new SparkConf()
      .setAppName("SparkWordCount1")
      .setMaster("hdfs://hadoop01:9000")
    //创建SparkContext的上下文,执行入口
    val sc = new SparkContext(conf)
    //开始统计文件单词
    val lines: RDD[String] = sc.textFile(args(0))
    val words: RDD[(String, Int)] = lines.map((_,1))
    val result: RDD[(String, Int)] = words.reduceByKey(_+_)
    result.foreach(println)
    //保存到HDFS上
    result.saveAsTextFile(args(1))
    //关闭资源
    sc.stop()
  }

  //将数据进行取偶
  def ou(): Unit = {
    val conf = new SparkConf().setAppName("ou").setMaster("local")
    val sc = new SparkContext(conf)
    val datas = Array(1, 2, 3, 7, 4, 5, 8)
    val numsRDD: RDD[Int] = sc.parallelize(datas)
    val hashMap = mutable.HashMap(2 -> "zhangsan", 4 -> "lisi", 8 -> "wangwu")
    //如果我们调用mappartitions算子,
    val resulted: RDD[String] = numsRDD.mapPartitions(m => {
      var list = mutable.LinkedList[String]()
      while (m.hasNext) {
        val name = m.next()
        val named: Option[String] = hashMap.get(name)
        list ++= named
      }
      list.iterator
    })
    resulted.collect().foreach(println)
    sc.stop()
  }

//  3. val arr = Array("hello", "world", "hello", "spark", "hello", "hive", "hi", "spark")
//  将上述数据进行去重，然后统计去重后的数据取前2个进行显示
  def distinct(): Unit = {
    val conf = new SparkConf().setAppName("distinct").setMaster("local")
    val sc = new SparkContext(conf)
    val arr = Array("hello", "world", "hello", "spark", "hello", "hive", "hi", "spark")
    val lines: RDD[String] = sc.parallelize(arr)
    val resulted: Array[String] = lines.distinct().take(2)
    resulted.foreach(println)
  sc.stop()
  }

  //  4.val arr = Array("dog cat gnu","salmon rabbit","turkey wolf","bear bee")
  //  用groupByKey和reduceByKey两种方式实现单词计数的统计
  def groupByKey(): Unit = {
    val conf = new SparkConf().setAppName("groupByKey").setMaster("local")
    val sc = new SparkContext(conf)
    val arr = Array("dog cat gnu", "salmon rabbit", "turkey wolf", "bear bee")
    val Animal = sc.parallelize(arr)
    val words: RDD[String] = Animal.flatMap(_.split(" "))
    val tuples: RDD[(String, Int)] = words.map((_, 1))
    val grouped: RDD[(String, Iterable[Int])] = tuples.groupByKey()
    //Iterable[Int]没有聚合,打印需要再次循环打印出来
    grouped.foreach(x => {
      println(x._1)
      x._2.foreach(println)
    })
    sc.stop()
  }

  def reduceByKey(): Unit = {
    val conf = new SparkConf().setAppName("reduceByKey").setMaster("local")
    val sc = new SparkContext(conf)
    val arr = Array("dog cat gnu", "salmon rabbit", "turkey wolf", "bear bee")
    val Animal = sc.parallelize(arr)
    val words: RDD[String] = Animal.flatMap(_.split(" "))
    val tuples: RDD[(String, Int)] = words.map((_, 1))
    val resulted: RDD[(String, Int)] = tuples.reduceByKey(_ + _)
    resulted.foreach(println)
    sc.stop()
  }


  def main(args: Array[String]): Unit = {
        ou()
    //    distinct()
    //    groupByKey()
    //    reduceByKey()
    //SparkWordCount1(args: Array[String])
  }
}
