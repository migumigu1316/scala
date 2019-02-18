package day02

/**
  *
  * @ClassName: EnumerateDemo
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/18 15:18
  *
  */
/**
  * // Scala没有直接提供类似于Java中的Enum这样的枚举特性，如果要实现枚举，
  * 则需要用object继承Enumeration类，并且调用Value方法来初始化枚举值
  * // 还可以通过Value传入枚举值的id和name，通过id和toString可以获取;
  * 还可以通过id和name来查找枚举值
  */
object EnumerateDemo extends Enumeration {
  val Spring = Value(0, "spring")
  val Summer = Value(1, "summer")
  val Autumn = Value(2, "autumn")
  val Winter = Value(3, "winter")
}

object EnumerateDemoTest {
  def main(args: Array[String]): Unit = {
    //    println(EnumerateDemo(0))
    //    println(EnumerateDemo.withName("spring"))
    //循环遍历枚举
    for (i <- EnumerateDemo.values) println(i)
  }
}
