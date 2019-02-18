package day01

import scala.collection.mutable.ArrayBuffer

/**
  *
  * @ClassName: test
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/17 21:12
  *
  */
object test {
  def main(args: Array[String]): Unit = {
    println("Hello, world!")
        one_word
        two_word
        three_word
        four_word
        five_word
  }

  def one_word: Unit = {
    for (i <- 0 to 10 if i % 2 == 0 && i != 0)
      print(i + "\t")
  }

  def two_word: Unit = {
    val f2 = for (i <- 0 to 10 if i % 2 == 0)
      yield i
    print(f2)
  }

  def three_word: Unit = {
    val f3 = for (i <- 0 until 10 if i % 2 == 1)
      print(i + "\t")
    //yield i
    // print(f3)
  }

  /**
    * 4.创建一个5位的空数组
    * 5.将空数组赋值
    * 6.然后添加一个新的数组，组合成一个新的数组
    * 7.遍历组合后的数组元素，用多种方式实现遍历（最少两种）
    */
  def  four_word: Unit ={
    //定义空数组
    val arr1 = new Array[Int](5)
    //    数组赋值：
    //   如 names(index)= value;
    arr1(0)=1
    //    arr1(1)=2
    //    arr1(2)=3
    //    arr1(3)=4
    //    arr1(4)=5
    for (i<- arr1)
      print(arr1.toBuffer)
    //    val arr3 = Array.apply(1,2,3,4)
    //    for (i<- arr3)println(i)
  }

  def five_word: Unit ={
    var arr2 = ArrayBuffer[Int]()
    arr2 += (7,8,9)
    println(arr2)
  }


}
