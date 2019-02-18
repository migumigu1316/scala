package day03.work

/**
  *
  * @ClassName: foreachWor
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/19 21:03
  *
  */
// foreach案例实战：打印List中的每个单词
//List("I", "have", "a", "beautiful", "house")
object foreachWor {
  def main(args: Array[String]): Unit = {
    val lines = List("I", "have", "a", "beautiful", "house")
    val words = lines.flatMap(_.split("\""))
    words.foreach(println)
  }

}
