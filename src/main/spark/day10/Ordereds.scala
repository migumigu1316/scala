package day10

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  *
  * @ClassName: Ordereds
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/29 16:28
  *
  *        自定义排序
  */
object Ordereds {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("Ordered").setMaster("local")
    val sc = new SparkContext(conf)

    val rdd: RDD[(String, Int, Int, Int)] = sc.parallelize(List(("冰冰", 40, 168, 1), ("杨幂", 32, 165, 2),
      ("赵丽颖", 32, 166, 3), ("柳岩", 36, 170, 4)))

    //将自己定义的排序规则传入,进行排序
    val sorted: RDD[(String, Int, Int, Int)] = rdd.sortBy(t => {
      new Names(t._2, t._3, t._4)
    })

    //打印
    println(sorted.collect().toBuffer)
    sc.stop()
  }
}

//继承Serializable序列化
class Names(val i1: Int, val i2: Int, val i3: Int) extends Ordered[Names] with Serializable {
  override def compare(that: Names): Int = {
    if (this.i1 == that.i1) {
      that.i2 - this.i2
    } else if (this.i2 == that.i2) {
      that.i3 - this.i3
    } else {
      that.i1 - this.i1
    }
  }
}