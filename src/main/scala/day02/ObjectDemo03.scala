package day02

/**
  *
  * @ClassName: ObjectDemo03
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/18 14:53
  * Object继承抽象类
  */
// object的功能其实和class类似，除了不能定义接受参数的构造器之外
// object也可以继承抽象类，并覆盖抽象类中的方法

abstract class ObjectDemo03(var message:String) {
  def sayhelo(name: String):Unit
}

object ObjectDemo03Test extends ObjectDemo03("hello") {
  //重写抽象方法
  override def sayhelo(name: String): Unit = {
    println(message+" , "+name)
  }

  def main(args: Array[String]): Unit = {
    ObjectDemo03Test.sayhelo("Leo")
  }
}