package day10.work

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  *
  * @ClassName: homework
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/29 19:24
  *
  */
object homework {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("homework").setMaster("local")
//    val conf: SparkConf = new SparkConf().setAppName("homework")
    val sc = new SparkContext(conf)
    //接下来进行检查点机制的创建
    sc.setCheckpointDir("hdfs://hadoop01:9000/checkpoint")

    val list = List(("tom", 12, 56), ("jack", 35, 65), ("jen", 35, 23), ("tom", 12, 68), ("jack", 35, 69))
    val l: RDD[(String, Int, Int)] = sc.parallelize(list)

    val namesAndNumsAndnumed: RDD[((String, Int), Int)] =
      l.map(t => {
        val names: String = t._1
        val nums: Int = t._2
        val numed: Int = t._3
        ((names, nums), numed)
      })
    //    namesAndNumsAndnumed.collect().foreach(println)
    val reduced: RDD[((String, Int), Int)] = namesAndNumsAndnumed.reduceByKey(_ + _)
    //    reduced.collect().foreach(println)
    val newlist: RDD[(String, Int, Int)] = reduced.map(t => {
      val name: String = t._1._1
      val num2: Int = t._1._2
      val sumnum: Int = t._2
      (name, num2, sumnum)
    })
//    newlist.collect().foreach(println)

    //将自己定义的排序规则传入,进行排序
    val sorted: RDD[(String, Int, Int)] = newlist.sortBy(t => {
      new n(t._2, t._3)
    })
    //    sorted.collect().foreach(println)
    /**
      * 官网建议我们在做checkpoint操作的时候,先进行持久化操作,也就是为了防止我们中间的结果出现问题
      * 数据丢失,前面的运行还没到checkpoint这一步,我们可以做一个cache缓存,下次这些数据直接在cache缓存中
      * 再次计算时,就不用从头计算,如果我们的checkpoint执行成功的话,那么之前所做的缓存将被销毁
      */
    sorted.cache().checkpoint()

    val resluted: Array[(String, Int, Int)] = sorted.take(2)
    //打印
//    resluted.foreach(println)

    //保存到HDFS上
    reduced.saveAsTextFile("hdfs://hadoop01:9000/ordered")

    sc.stop()
  }
}

class n(val i2: Int, val i3: Int) extends Ordered[n] with Serializable {
  override def compare(that: n): Int = {
    if (this.i2 == that.i2 ) {
      that.i2 - this.i2
    } else {
      that.i3 - this.i3
    }
  }
}