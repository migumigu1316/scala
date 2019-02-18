package day08

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  *
  * @ClassName: Cache
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/27 14:33
  *
  */
object Cache {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("Cache").setMaster("local")
    val sc = new SparkContext(conf)
    /**
      * 接下来做持久化RDD操作
      * 要用到cache或者persist算子,如果我们调用textFile算子之后创建了一个RDD
      * 去调用cache或者persist才会生效,如果我们创建了一个RDD,然后单独另起一行
      * 执行cache()或者persist()算子,是没用的,比如:lines.cache()
      * 而且有弊端,会报错大量的文件会丢失
      */
    //首先读取数据
    val lines: RDD[String] = sc.textFile("hdfs://hadoop01:9000")
    //lines.cache()//错误写法
    val words: RDD[(String, Int)] = lines.map((_, 1)).reduceByKey(_ + _).cache()//cache()/persist() 缓存操作
    words.count()
    words.foreach(println)
    sc.stop()
  }
}
