package day02

/**
  *
  * @ClassName: ObjectDemo02
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/18 14:45
  *
  *        伴生类
  */
class ObjectDemo02(x: Int, y: Int) {
  def getObject: Unit = {
    println(x + "   " + y + "  " + ObjectDemo02.name + " " + ObjectDemo02.age)
  }
}

/**
  * 伴生对象
  */
object ObjectDemo02 {
  private val name = "张三"
  private val age = 20
}

object objectDemo02Test {
  def main(args: Array[String]): Unit = {
    val demo = new ObjectDemo02(99, 98)
    demo.getObject
    //在普通的对象中无法访问private修饰的变量
    //ObjectDemo02.name
  }
}