package day04.work
import scala.collection.mutable._
/**
  *
  * @ClassName: ListWork01
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/20 19:32
  *
  * list 集合的练习
  */
object ListWork01 {
  def main(args: Array[String]): Unit = {

    //  集合的常用方法
    val arr = ArrayBuffer(1, 2, 3, 4)
    //  head（取头部）
    println(arr.head)//1
    //  last（取尾部）
    println(arr.last)//4
    //  tail（取除了头以外的）
    println(arr.tail)//ArrayBuffer(2, 3, 4)
    //  length（长度）
    println(arr.length)//4
    //  isEmpty（是否为空）
    println(arr.isEmpty)//false
    //  sum（求和）
    println(arr.sum)//10
    println(arr.reduceLeft(_ + _) + "-------------------")
    //  max（最大值）
    println(arr.max)//4
    println(arr.reduceLeft(_ max(_)) + "*****************")
    //  min（最小值）
    println(arr.min)//1
    println(arr.reduceLeft(_ min(_)) + "------------------")
    //  count（取偶数，取奇数）
    val count = arr.filter(_%2==0)
    val count1 = arr.filter(_%2==1)
    println(count)//ArrayBuffer(2, 4)
    println(count1)//ArrayBuffer(1, 3)
    //  exists(_ % 2==0)（判断是否有整除为0的）
    println(arr.exists(_ % 2 == 0))//true
    //  filter（过滤）
    println(arr.filter(_ % 2 == 0))//ArrayBuffer(2, 4)
    //  take（取前几个元素的值）
    println(arr.take(2))//ArrayBuffer(1, 2)
    //  contains（是否存在）
    println(arr.contains(2))//true
    //  indexOf（取下标）
    println(arr.indexOf(2))//1
  }
}
