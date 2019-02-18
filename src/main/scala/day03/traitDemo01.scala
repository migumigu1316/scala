package day03

/**
  *
  * @ClassName: traitDemo01
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/19 10:26
  *
  *        将trait作为接口使用
  */
/**
  * // Scala中的Triat是一种特殊的概念
  * // 首先我们可以将Trait作为接口来使用，此时的Triat就与Java中的接口非常类似
  * // 在triat中可以定义抽象方法，就与抽象类中的抽象方法一样，只要不给出方法的具体实现即可
  * // 类可以使用extends关键字继承trait，注意，这里不是implement，而是extends，
  * 在scala中没有implement的概念，无论继承类还是trait，统一都是extends
  * // 类继承trait后，必须实现其中的抽象方法，实现时不需要使用override关键字
  * // scala不支持对类进行多继承，但是支持多重继承trait，使用with关键字即可
  */
trait traitDemo01 {
  //在trait中可以定义抽象方法,就与抽象类中的抽象方法一样
  //只要不给出方法的具体实现即可
  def sayHello(name: String)
}

trait makeTrait {
  def makeTrait(age: Int)
}

class Traits(val name: String) extends traitDemo01 with makeTrait {
  def sayHello(name: String): Unit = {
    println("hello , " + name)
  }

  //重写makeTrait方法
  def makeTrait(age: Int): Unit = {
    println("hello , " + name +"\t" + age)
  }
}

object TraitTest {
  def main(args: Array[String]): Unit = {
    val traits = new Traits("lily")
    println(traits.name)
    traits.makeTrait(20)
    traits.sayHello("joke")

  }
}