package day03

/**
  *
  * @ClassName: TraitValDemo03
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/19 11:16
  *
  * // Scala中的Triat可以定义具体变量，此时继承trait的类就自动获得了trait中定义的变量
  * // 但是这种获取变量的方式与继承class是不同的：如果是继承class获取的变量，
  *    实际是定义在父类中的，相当于我们得子类获得到的是父类的引用地址；
  *    而继承trait获取的变量，就直接被添加到了类中
  */
trait TraitValDemo03 {
  val num: Int = 2
}

class dog (val name: String)extends TraitValDemo03{
  def sayhello = println("Hi I'm " + name + ", I have " + num + "eyes.")
}

object dogTest{
  def main(args: Array[String]): Unit = {
    val dog = new dog("大黄")
    dog.sayhello
  }
}