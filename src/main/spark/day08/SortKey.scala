package day08

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  *
  * @ClassName: SortKey
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/27 16:04
  *
  * spark的二次排序
  * 需求:
  * 1.按照文件的第一列排序
  * 2.如果第一列相同,则按照第二列排序
  */
object SortKey {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("sortKey").setMaster("local")
    val sc = new SparkContext(conf)
    //读取数据信息
    val file: RDD[String] = sc.textFile("F:\\IDEA_WorkSpace\\scala\\src\\TestData\\sort.txt")
    //map读取每行的数据
    val tuples: RDD[(Sorted, String)] = file.map(f => {
      val lines: Array[String] = f.split(" ")
      //返回元组数据
      (Sorted(lines(0).toInt, lines(1).toInt), f)
    })
    val sorted: RDD[(Sorted, String)] = tuples.sortByKey()
    sorted.foreach(println)

    /**
      * (Sorted(排序后的结果),原数据)
      * (Sorted(2,4),2 4)
      * (Sorted(2,10),2 10)
      * (Sorted(12,3),12 3)
      * (Sorted(23,100),23 100)
      * (Sorted(30,60),30 60)
      */
    sc.stop()
  }
}

case class Sorted(val first:Int,val second:Int) extends Ordered[Sorted]{
  override def compare(that: Sorted): Int = {
    //如果第一个值不相等,直接排序第一个
    if(this.first - that.first != 0){
      this.first - that.first
    }else{
      //如果第一个值相等,就排序第二个
      this.second - that.second
    }
  }
}