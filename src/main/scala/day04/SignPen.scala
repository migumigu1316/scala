package day04

/**
  *
  * @ClassName: SignPen
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/20 16:43
  *
  */
class SignPen {
  def write(context: String) = println(context)

}

class SignPens {
  def getsignpen(name: String)(implicit signPen: SignPen): Unit = {
    signPen.write(name + ".......")

  }
}

object ImplicitTest2 {
  def main(args: Array[String]): Unit = {
    import day04.ImplicitsignPen.signPen
    val pen = new SignPens
    pen.getsignpen("zhangsan")

  }
}