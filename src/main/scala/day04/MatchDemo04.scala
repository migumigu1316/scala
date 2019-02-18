package day04

/**
  *
  * @ClassName: MatchDemo04
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/20 11:42
  *
  * 样例类的模式匹配
  * 如果我们定义样例类,
  */
class MatchDemo04

//类的前面加上case就是样例类
case class Studented(name: String) extends MatchDemo04

case class Teacher(name: String) extends MatchDemo04

object MatchCaseTest {
  def main(args: Array[String]): Unit = {
    val teacher: MatchDemo04 = Teacher("zhangsan")

    val studented: MatchDemo04 = Studented("lisi")

    teacher match {
      case Teacher(name) => println("Teacher,name is " + name)
      case Studented(name) => println("studented,name is " + name)
      case _ => println("Teacher or studented,Nothing.......")
    }
  }
}