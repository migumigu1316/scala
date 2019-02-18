package day03

/**
  *
  * @ClassName: absDemo
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/19 10:01
  *
  * 如果在父类中,有某些方法无法立即实现,而需要依赖不同的子类来覆盖,重实现写自己的方法
  *
  *
  */
abstract class absDemo {
  def sayhello: Unit

}

class abstractDemo(name:String) extends absDemo {
  override def sayhello: Unit = {
    println("hello ,  "+ name)
  }
}

object absTest{
  def main(args: Array[String]): Unit = {
    //抽象类不能实例化
    val arr = new abstractDemo("zhangsan")

    arr.sayhello
  }
}