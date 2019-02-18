package day03

/**
  *
  * @ClassName: absValDemo
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/19 10:10
  *
  */
abstract class absValDemo {
  val name:String //抽象变量
}

class absVal02 extends absValDemo {
  val name: String = "leo"
}

object absValTest{
  def main(args: Array[String]): Unit = {
    val absval = new absVal02
    println(absval.name)
  }
}