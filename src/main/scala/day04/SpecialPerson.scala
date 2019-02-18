package day04

/**
  *
  * @ClassName: SpecialPerson
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/20 16:22
  *
  */
class SpecialPerson(val name:String)

class Studenteds(val name:String)

class Older(val name:String)

class PersonTest{
  var number = 0
  def Specical(p:SpecialPerson): Unit ={
    number +=1
    println("T-"+number)
  }
}
object ImplicitTest{
  def main(args: Array[String]): Unit = {
    import day04.Implicits.object2SpecialPerson
    val zhangsan = new Studenteds("zhangsan")
    val wangwu = new Older("wangwu")
    val pt = new PersonTest
    pt.Specical(wangwu)
  }
}