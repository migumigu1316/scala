package day02

/**
  *
  * @ClassName: privateDemo
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/18 11:45
  *
  */
//伴生类
class privateDemo {
  private[this] val hello = "张三"
  private val name1 = ""
  val name2 = ""
}

//对象中无法访问private修饰的变量
object privateDemoTest {
  def main(args: Array[String]): Unit = {
    val pri = new privateDemo
    pri.name2
  }
}

//伴生对象也无法访问private[this]修饰的
object privateDemo {
  def main(args: Array[String]): Unit = {
    val pri = new privateDemo
    pri.name1
  }
}