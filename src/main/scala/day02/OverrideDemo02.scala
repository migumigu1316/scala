package day02

/**
  *
  * @ClassName: OverrideDemo02
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/18 16:22
  *
  * 覆盖变量
  */
class OverrideDemo02 {
val name = "lily"
  def age=0
}

class Overridestu02 extends OverrideDemo02{
  override val name:String="leo"

  override def age: Int = 30
}
object OverrideTest02{
  def main(args: Array[String]): Unit = {
    val od02 = new OverrideDemo02
    println(od02.name)
    println(od02.age)
    val od = new Overridestu02
    println(od.name)
    println(od.age)
  }
}