package day03

/**
  *
  * @ClassName: myClass
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/19 11:36
  *
  * 5. 混合使用具体方法和抽象方法
  * // 在trait中，可以混合使用具体方法和抽象方法
  * // 可以让具体方法依赖于抽象方法，而抽象方法则放到继承trait的类中去实现
  */
class myClass {
  def printMsg(msg:String)=println(msg)
}
trait Mytrait extends myClass{
  def log(msg:String)=println("log" + msg)
}
class MyTrait02(val name:String)extends Mytrait{
  def sayHello: Unit ={
    log(name+"000")
    println(name + "111")
  }
}
object MytraitTest{
  def main(args: Array[String]): Unit = {
    val myTrait = new MyTrait02("A")
    myTrait.sayHello
  }
}