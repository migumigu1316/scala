package day04

/**
  *
  * @ClassName: Personed
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/20 14:30
  *
  * 泛型: 上边界 Bounds <:
  */
/**
  * // 在指定泛型类型的时候，有时，我们需要对泛型类型的范围进行界定，而不是可以是任意的类型。
  * 比如，我们可能要求某个泛型类型，它就必须是某个类的子类，这样在程序中就可以放心地调用泛型类型
  * 继承的父类的方法，程序才能正常的使用和运行。此时就可以使用上下边界Bounds的特性。
  * // Scala的上下边界特性允许泛型类型必须是某个类的子类，或者必须是某个类的父类
  *
  * @param name
  */
//案例：在派对上交朋友
class Personed (val name:String) {
def sayHello = println("Hello, I'm "+ name)
  def makePerson(p:Personed): Unit ={
    sayHello
    p.sayHello
  }
}
class persond02(name: String) extends  Personed(name)

class personed03

//泛型: 上边界 Bounds <:
class Party[T <:Personed](p1:T,p2:T){
  def play = p1.makePerson(p2)
}

object persondedTest{
  def main(args: Array[String]): Unit = {
    val zs = new persond02("zhangsan")
    val ls = new Personed("lisi")
    val p = new personed03//结果会报错
    val py = new Party(zs,ls)
    py.play

  }
}