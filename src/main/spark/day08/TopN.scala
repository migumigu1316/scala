package day08

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  *
  * @ClassName: TopN
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/27 16:25
  *
  *        需求:对文件内的数据,取最大的前三个
  */
object TopN {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("topN").setMaster("local")
    val sc = new SparkContext(conf)
    //读取文件数据信息
    val lines: RDD[String] = sc.textFile("F:\\IDEA_WorkSpace\\scala\\src\\TestData\\top.txt")

    //调用map,进行数据提取操作
    val tuples: RDD[(Integer, String)] = lines.map(m => (Integer.valueOf(m), m))//类型转换

    //sortByKey(false)默认是true按照升序,设置false降序
    val result: Array[(Integer, String)] = tuples.sortByKey(false).take(3)

    result.foreach(t=>println(t._2))//9   7   6
    sc.stop()
  }
}
