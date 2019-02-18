package day04

/**
  *
  * @ClassName: Man
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/20 19:20
  *
  */
class Man(val name:String)

class SuperMan(val name:String){
  def mans = println("变身SuperMan!!!")
}

object manTest{
  def main(args: Array[String]): Unit = {
    import day04.SuperMans.man2superman
    val t = new Man("小萝卜头.汤尼")
    t.mans
  }
}
