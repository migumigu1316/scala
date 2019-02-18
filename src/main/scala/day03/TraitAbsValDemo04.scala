package day03

/**
  *
  * @ClassName: TraitAbsValDemo04
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/19 11:22
  *
  * 4. 在trait中定义抽象字段
  * 定义抽象字段的时候，那么如果我们继承了这个接口，那么必须要重写实现这个字段，并且给予赋值；
  */
trait TraitAbsValDemo04 {
  //定义抽象字段
  val name:String
  //定义具体的抽象方法
  def sayHello(age:Int)=println(name + " " + age)

}
class pig(val names:String)extends TraitAbsValDemo04{
  val name:String = names
  def getHello (num:Int): Unit ={
    sayHello(num)
  }
}
object pigTest{
  def main(args: Array[String]): Unit = {
    val pig = new pig("猪")
    pig.sayHello(5)
  }
}