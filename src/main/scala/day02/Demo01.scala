package day02

/**
  *
  * @ClassName: Demo01
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/18 11:55
  *
  * 辅助构造器
  *
  */
class Demo01() {
  var name = ""
  var age = 0

  def this(name: String) {
    this()
    this.name = name
    println("123")
  }

  def this(name: String, age: Int) {
    this(name)
    this.age = age
    println("123")
  }
}

object Demo01Test {
  def main(args: Array[String]): Unit = {
    val test = new Demo01("lily", 123)
  }
}