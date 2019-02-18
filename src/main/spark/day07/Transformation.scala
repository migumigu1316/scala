package day07

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @ClassName: Transformation
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/26 14:27
  *
  * parallelize并行化
  */
object Transformation {
  def map(): Unit = {
    //创建一个sparkConf
    val conf = new SparkConf()
      .setAppName("map")
      .setMaster("local")

    //创建SparkContext的上下文,执行入口
    val sc = new SparkContext(conf)

    val arr = Array(1, 2, 3, 4, 5)
    //并行化方式创建RDD
    val numRDD = sc.parallelize(arr, 1)

    val maps: RDD[Int] = numRDD.map(x => x * 2)

    maps.foreach(num => println(num))
    sc.stop()
  }

  //过滤
  def filter(): Unit = {
    //创建一个sparkConf
    val conf = new SparkConf()
      .setAppName("filter")
      .setMaster("local")

    //创建SparkContext的上下文,执行入口
    val sc = new SparkContext(conf)
    //创建数据
    val arr = Array(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    val numRDD = sc.parallelize(arr)
    val filterRDD: RDD[Int] = numRDD.filter(_ % 2 == 0)
    filterRDD.foreach(println)
    sc.stop()
  }

  //切分压平
  def flatMap(): Unit = {
    //创建一个sparkConf
    val conf = new SparkConf()
      .setAppName("flatMap")
      .setMaster("local")

    //创建SparkContext的上下文,执行入口
    val sc = new SparkContext(conf)
    //创建数据
    val arr = Array("hello you", "hello me", "hello eorld")
    val lines: RDD[String] = sc.parallelize(arr)
    val words: RDD[String] = lines.flatMap(_.split(" "))
    words.foreach(println)
    sc.stop()
  }

  def groupByKey(): Unit = {
    val conf = new SparkConf().setAppName("groupByKey").setMaster("local")
    val sc = new SparkContext(conf)
    val arr = Array(("class2", 80), ("class1", 90), ("class2", 95), ("class1", 82))
    val classScores = sc.parallelize(arr)
    val grouped: RDD[(String, Iterable[Int])] = classScores.groupByKey()
    //Iterable[Int]没有聚合,打印需要再次循环打印出来
    grouped.foreach(x => {
      println(x._1)
      x._2.foreach(println)
    })
    sc.stop()
  }

  //按照key进行聚合
  def reduceByKey(): Unit = {
    val conf = new SparkConf().setAppName("reduceByKey").setMaster("local")
    val sc = new SparkContext(conf)
    val arr = Array(("class2", 80), ("class1", 90), ("class2", 95), ("class1", 82))
    val classScores = sc.parallelize(arr)
    val Scores: RDD[(String, Int)] = classScores.reduceByKey(_ + _)
    Scores.foreach(println)
    sc.stop()
  }

  //按照Key进行排序
  def sortByKey(): Unit = {
    val conf = new SparkConf().setAppName("sortByKey").setMaster("local")
    val sc = new SparkContext(conf)
    val tuple = Array((60, "leo"), (70, "tom"), (80, "jack"))
    val Scores = sc.parallelize(tuple)
    val sorted: RDD[(Int, String)] = Scores.sortByKey(false)
    sorted.foreach(x => println(x._1 + "-----" + x._2))
    sc.stop()
  }

  //join
  def join(): Unit ={
    val conf=new SparkConf().setAppName("join").setMaster("local")
    val sc = new SparkContext(conf)
    val studentList = Array((1,"leo"),(2,"jack"),(3,"tom"))
    val scoreList = Array((1,99),(2,95),(3,98))
    val students = sc.parallelize(studentList)
    val scores = sc.parallelize(scoreList)
    val studentScores: RDD[(Int, (String, Int))] = students.join(scores)
    //                    id      name    score
    studentScores.foreach(x=>{
      println("student ID: " + x._1 )
      println("student Name: " + x._2._1)
      println("student scores: " + x._2._2)
    })
    sc.stop()
  }

  //cogroup()算子不长用
  def cogroup(): Unit ={
    val conf=new SparkConf().setAppName("join").setMaster("local")
    val sc = new SparkContext(conf)
    val studentList = Array((1,"leo"),(2,"jack"),(3,"tom"))
    val scoreList = Array((1,99),(2,95),(3,98))
    val students = sc.parallelize(studentList)
    val scores = sc.parallelize(scoreList)
    /**
      * cogroup的函数实现:这个实现根据两个要进行合并的两个RDD操作,
      * 生成一个CoGroupedRDD的实例,这个RDD的返回结果是把相同的key中
      * 两个RDD分别进行合并操作,最后返回的RDD的value是一个Pair的实例,
      * 这个实例包含两个Iterable的值,第一个值表示的是RDD1中相同KEY的值,
      * 第二个值表示的是RDD2中相同key的值.由于做cogroup的操作,
      * 需要通过partitioner进行重新分区的操作,因此,执行这个流程时,
      * 需要执行一次shuffle的操作(如果要进行合并的两个RDD的都已经是shuffle后的rdd,
      * 同时他们对应的partitioner相同时,就不需要执行shuffle
      */
    val studentScores: RDD[(Int, (Iterable[String], Iterable[Int]))] = students.cogroup(scores)
    studentScores.foreach(x=>{
      println("student ID: " + x._1 )
      println("student Name: " + x._2._1)
      println("student scores: " + x._2._2)
    })

    /**
      * student ID: 1
      * student Name: CompactBuffer(leo)
      * student scores: CompactBuffer(99)
      * student ID: 3
      * student Name: CompactBuffer(tom)
      * student scores: CompactBuffer(98)
      * student ID: 2
      * student Name: CompactBuffer(jack)
      * student scores: CompactBuffer(95)
      */
    sc.stop()
  }

  def main(args: Array[String]): Unit = {
    //    map()
    //    filter()
    //    flatMap()
    //    groupByKey()
    //    reduceByKey()
    //    sortByKey()
    //    join()
    cogroup()
  }
}
