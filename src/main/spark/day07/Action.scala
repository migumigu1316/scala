package day07

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @ClassName: Action
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/26 15:20
  *
  *        Action算子练习
  */
object Action {

  def reduce(): Unit = {
    val conf = new SparkConf().setAppName("reduce").setMaster("local")
    val sc = new SparkContext(conf)
    val arr=Array(1,2,3,4,5)
    val lines = sc.parallelize(arr)
    val sum = lines.reduce(_+_)
    println(sum)//15
    sc.stop()
  }

  def collect(): Unit = {
    val conf = new SparkConf().setAppName("collect").setMaster("local")
    val sc = new SparkContext(conf)
    val arr = Array(1, 2, 3, 4, 5)
    val lines = sc.parallelize(arr)
    val words: RDD[Int] = lines.map(_ * 2)
    //注意:当调用Action算子后,RDD的程序就结束了,
    //之后再操作这个结果集,就是scala操作
    val result: Array[Int] = words.collect()
    for (num <- result) {
      println(num)//2,4,6,8,10
      sc.stop()
    }
  }
    def count(): Unit = {
      val conf: SparkConf = new SparkConf().setAppName("count").setMaster("local")
      val sc = new SparkContext(conf)
      val arr = Array(1, 2, 3, 4, 5)
      val num: RDD[Int] = sc.parallelize(arr)
      val counts: Long = num.count()//Array中有几个数
      println(counts)//5
      sc.stop()
    }

    def take(): Unit = {
      val conf = new SparkConf().setAppName("take").setMaster("local")
      val sc = new SparkContext(conf)
      val arr = Array(53, 22, 3, 14, 5)
      val num: RDD[Int] = sc.parallelize(arr)
//    take不对数据进行排序，返回前N个值(返回rdd 中从0到N-1的下标表示的值);
//    takeOrdered是按照key的正序排序,返回前N个元素;
//    top默认降序排序,是按照key倒叙排序,返回前N个元素,底层是调用的takeOrdered的翻转
      val takes: Array[Int] = num.take(3)//53   22   3
//      val takes: Array[Int] = num.takeOrdered(3)//3  5  14
//      val takes: Array[Int] = num.top(3)//53  22  14
      takes.foreach(println)
      sc.stop()
    }


  def saveAsTextFile(): Unit = {
    val conf = new SparkConf().setAppName("saveAsTextFile").setMaster("local")
    val sc = new SparkContext(conf)
    val arr = Array(1, 2, 3, 4, 5)
    val num: RDD[Int] = sc.parallelize(arr)
    //使用saveAsTextFile算子时,这个目录必须是不存在的,保存时自动生成
    num.saveAsTextFile("F://TestData")
    sc.stop()
  }

  def countByKey(): Unit = {
    val conf = new SparkConf().setAppName("countByKey").setMaster("local")
    val sc = new SparkContext(conf)
    val arr = Array(("class1","leo"),("class2","jack"),("class1","tom"),
      ("class1","jen"),("class3","lily"))
    val students: RDD[(String, String)] = sc.parallelize(arr)
    //统计相同key的数量
    val studentCounts: collection.Map[String, Long] = students.countByKey()
    println(studentCounts)//Map(class3 -> 1, class1 -> 3, class2 -> 1)
    sc.stop()
  }

  def main(args: Array[String]): Unit = {
//    reduce()
//    collect()
//    count()
//    take()
      saveAsTextFile()
//    countByKey()
  }
}
