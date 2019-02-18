package day02.work

/**
  *
  * @ClassName: otherWork01
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/18 20:28
  *
  */
class otherWork01 {

  val a = Array[Int](1,2,3,4,5)

  //1.接收组成的新数组
  val a2 = for (ele <- a) yield ele * ele

  // 2.结合if守卫，仅转换需要的元素 偶数
  val a3 = for (ele <- a2 if ele % 2 == 0) yield ele * ele

//3.使用函数式编程转换数组（通常使用第一种方式）
  val a4 = a.filter(_ % 2 == 0).map(2 * _)
//  a.filter { _ % 2 == 0 } map { 2 * _ }

}
object otherWork01Test{
  def main(args: Array[String]): Unit = {
    val w = new otherWork01
    //1.
    for (e<- w.a2)print(e + "\t")
    println()

    //2.
    for (e<- w.a3)print(e + "\t")
    println()

    //3.
    for (e<- w.a4)print(e + "\t")//4	8
    println()

  }
}
