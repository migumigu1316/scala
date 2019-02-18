package day08

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  *
  * @ClassName: GroupTopN
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/27 16:37
  *
  * 分组取TopN
  */
object GroupTopN {
  def main(args: Array[String]): Unit = {
//    val conf = new SparkConf().setAppName("GroupTopN").setMaster("local")
//    .set("spark.testing.memory","521000000")//解决内存不足问题
    val conf: SparkConf = new SparkConf()
      .setAppName("GroupTopN").setMaster("local")
    val sc = new SparkContext(conf)
    val lines: RDD[String] = sc.textFile(
      "F:\\IDEA_WorkSpace\\scala\\src\\TestData\\score.txt")
    val tuples: RDD[(String, Int)] = lines.map(t => {
      val str: Array[String] = t.split(" ")
      //返回tuple元组----(class班级,score成绩)
      (str(0), str(1).toInt)//类型如果不匹配,需要转换
    })
    //分组,按照班级进行分组
    val grouped: RDD[(String, Iterable[Int])] = tuples.groupByKey()
    val classScores: RDD[(String, Array[Int])] = grouped.map(t => {
      //第一步:先取班级的名字
      val classed: String = t._1
      //第二步:取成绩
      //Iterable[Int]转成Array类型
      val score: Array[Int] = t._2.toArray.sortWith(_ > _).take(3)
      //返回元组
      (classed, score)
    })
    classScores.foreach(t=>{
      println(t._1)//班级
      t._2.foreach(println)//成绩
      println("-----------------")
    })

    /**
      * class1
      * 95
      * 90
      * 87
      * -----------------
      * class2
      * 88
      * 87
      * 77
      * -----------------
      */
    sc.stop()
  }
}
