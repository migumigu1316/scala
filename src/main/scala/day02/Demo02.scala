package day02

/**
  *
  * @ClassName: Demo02
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/18 14:14
  *
  */
/**
  * 主构造器
  * @param name
  * @param age
  */
class Demo02(val name:String,val age:Int) {
  println(name + " "+ age)

}

class Demo02_1(val name:String,val age:Int = 20 ){
  println(name + " "+ age)
}

object Demo02Test{
  def main(args: Array[String]): Unit = {
    val test = new Demo02("张三",20)
    println(test)
  }
}