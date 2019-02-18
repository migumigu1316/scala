package day01

/**
  *
  * @ClassName: lianxi2
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/17 23:05
  *
  */
object lianxi2 {

  def main(args: Array[String]): Unit = {
    val r1 = m1(f1)
    println(r1)

    val r2 = m1(f2)
    println(r2)

    //方法转函数

  //  val f3 = m2 _

  }

  def m1(f: (Int, Int) => Int): Int = {
    f(2, 6)
  }

  //定义一个函数f1，参数是两个Int类型，返回值是一个Int类型
  val f1 = (x: Int, y: Int) => x + y
  //定义一个f2
  val f2 = (m: Int, n: Int) => m * n

 // def m2 (f1:(Int,Int,Int) => Int ):Int = x-y*z
}
