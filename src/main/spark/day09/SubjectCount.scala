package day09

import java.net.URL

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
/**
  *
  * @ClassName: SubjectCount
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/28 16:05
  *
  * 需求: 统计所有用户对每个学科的各个模块的访问量,取Top2
  */
object SubjectCount {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("SubjectCount").setMaster("local")
    val sc = new SparkContext(conf)
    //读取数据信息
    val file: RDD[String] = sc.textFile("F:\\IDEA_WorkSpace\\scala\\src\\TestData\\access.txt")

    //将用户访问的数据进行切分
    val tupleUrls: RDD[(String, Int)] = file.map(t => {
      //切分
      val filds: Array[String] = t.split("\t")
      //获取url
      val url: String = filds(1)
      (url, 1)
    })

    //将相同的URL进行聚合.得到每个学科各个模块的访问量
    val sumed: RDD[(String, Int)] = tupleUrls.reduceByKey(_+_)

    //获取学科信息
    val subjectAndUrlAndCount: RDD[(String, String, Int)] = sumed.map(t => {
      //获取URL
      val url: String = t._1
      //获取count值,访问量
      val count: Int = t._2

      val urls = new URL(url)
      //获取学科
      val subject: String = urls.getHost
      //返回(学科,url,访问量)
      (subject, url, count)
    })

    //按照学科信息进行分组
    val grouped: RDD[(String, Iterable[(String, String, Int)])] =
      subjectAndUrlAndCount.groupBy(_._1)

    //在学科内部进行降序排序
    val sorted: RDD[(String, List[(String, String, Int)])] =
      grouped.mapValues(_.toList.sortBy(_._3).reverse)

    //取TopN
    val result: Array[(String, List[(String, String, Int)])] = sorted.take(2)

    //打印
    println(result.toBuffer)
    /**
      * ArrayBuffer((android.learn.com,List((android.learn.com,http://android.learn.com/android/video.shtml,5))),
      * (ui.learn.com,List((ui.learn.com,http://ui.learn.com/ui/video.shtml,37),
      * (ui.learn.com,http://ui.learn.com/ui/course.shtml,26),
      * (ui.learn.com,http://ui.learn.com/ui/teacher.shtml,23))))
      */
    //关闭
    sc.stop()
  }
}
