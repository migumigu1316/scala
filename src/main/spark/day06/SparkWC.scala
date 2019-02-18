package day06

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  *
  * @ClassName: SparkWC
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/25 14:31
  *
  */
object SparkWC {
  def main(args: Array[String]): Unit = {
    //首先创建SparkConf配置文件对象
    val conf = new SparkConf()
      //给spark程序一个名字
      .setAppName("SparkWC")
      //用setMaster,这个程序就是本地模式
      //local:用一个线程模拟集群运行程序
      //loc上下文al[2]:用两个个线程模拟集群运行程序
      //local[*]:用所有空闲的线程模拟集群运行程序
//      .setMaster("local[*]")

    //创建sparkContext上下文,启动入口
    val sc = new SparkContext(conf)

    //进行读取数据,处理数据
//    val lines: RDD[String] = sc.textFile("F:\\a.txt")
    val lines: RDD[String] = sc.textFile(args(0))

    //进行单词统计,调用flatMap算子,切分压平
    val words: RDD[String] = lines.flatMap(_.split(" "))

    //调用map,进行单词计数
    val tuples: RDD[(String, Int)] = words.map((_,1))

    //调用reduceByKey,单词聚合
    val reduces: RDD[(String, Int)] = tuples.reduceByKey(_+_)

    //排序
    val sortWC: RDD[(String, Int)] = reduces.sortBy(_._2)

    //打印
//    println(sortWC.collect.toBuffer)

    //保存到HDFS上
    sortWC.saveAsTextFile(args(1))

    //关闭资源
    sc.stop()


  }
}
