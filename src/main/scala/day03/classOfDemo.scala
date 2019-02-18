package day03

/**
  *
  * @ClassName: classOfDemo
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/19 9:55
  *
  */
class classOfDemo

class classdemo02 extends classOfDemo//子类

object InstanceTest{
    def main(args: Array[String]): Unit = {
      //子类返回父类的类型
      val fu: classOfDemo = new classdemo02

      println(fu.isInstanceOf[classdemo02])//true

      println(fu.getClass == classOf[classdemo02])//true
    }
}
