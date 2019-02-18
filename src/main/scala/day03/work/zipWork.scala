package day03.work

/**
  *
  * @ClassName: zipWork
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/19 21:10
  *
  */
//List("Leo", "Jen", "Peter", "Jack")
//List(100, 90, 75, 83)
object zipWork {
  def main(args: Array[String]): Unit = {
    val a = List("Leo", "Jen", "Peter", "Jack")
    val b = List(100, 90, 75, 83)
    val c= a.zip(b)

    c.foreach(println)
  }
}
