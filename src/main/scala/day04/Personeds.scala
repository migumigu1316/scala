package day04

/**
  *
  * @ClassName: Personeds
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/20 15:05
  *
  * 泛型:View Bounds 上下边界  <%
  */
/**
  * // 上下边界Bounds，虽然可以让一种泛型类型，支持有父子关系的多种类型。但是，
  * 在某个类与上下边界Bounds指定的父子类型范围内的类都没有任何关系，则默认是肯定不能接受的。
  * // 然而，View Bounds作为一种上下边界Bounds的加强版，支持可以对类型进行隐式转换，
  * 将指定的类型进行隐式转换后，再判断是否在边界指定的类型范围内
  * @param name
  */
// 案例：跟小狗交朋友
class Personeds (val name:String){
def sayHello =println("Hi I'm "+name)
  def mkPerson(p:Personeds): Unit ={
    p.sayHello
  }
}

class Personeds02(name:String) extends Personeds(name)

class Dogs(val name:String)

//View Bounds 上下边界  <%
class Partys[T <% Personeds](p1:T){
  def play = p1.mkPerson(p1)
}

object ViewBoundsTest{
  def main(args: Array[String]): Unit = {
    import day04.dogss.dog2person
    val lisi = new Personeds("lisi")
    val zhaoliu = new Personeds02("zhaoliu")
    val xiaobai = new Dogs("xiaobai")
    val py = new Partys(xiaobai)
    py.play
  }
}