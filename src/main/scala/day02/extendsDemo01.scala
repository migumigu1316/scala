package day02

/**
  *
  * @ClassName: extendsDemo01
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/18 16:07
  *
  */
/**
  * // Scala中，让子类继承父类，与Java一样，也是使用extends关键字
  * // 继承就代表，子类可以从父类继承父类的属性和方法；然后子类可以在自己内部放入父类所没有，
  * 子类特有的属性和方法；使用继承可以有效复用代码
  * // 子类可以覆盖父类的属性和方法；但是如果父类用final修饰，属性和方法用final修饰，
  * 则该类是无法被继承的，属性和方法是无法被覆盖的
  */
class extendsDemo01 {
  private var name = "leo"
  def getName = name
}

class extendsDemo01_1 extends extendsDemo01{
  private var score = "100"
  def getScore =score
}

object extendsDemoTest{
  def main(args: Array[String]): Unit = {
    val demo=new extendsDemo01_1
    println(demo.getName)

  }
}
