package day03

/**
  *
  * @ClassName: TraitDemo05
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/19 11:29
  * 混合使用具体的方法和抽象方法
  */
trait TraitDemo05 {
  def getName:String
  def valid : Boolean={
    getName=="leo"
  }
}

class Names(val name:String)extends TraitDemo05{
  def getName: String = name
  println(valid)
}

object NamesTest{
  def main(args: Array[String]): Unit = {
    val names = new Names("joke")
    println(names.getName)
  }
}