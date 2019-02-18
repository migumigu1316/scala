package day02

/**
  *
  * @ClassName: Helloworld
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/18 11:31
  *
  */
class HelloWorld {

  private var name = "leo"

  def sayHello(): Unit = {
    println("hello," + name)
  }

  def getName = name


  object helloWorldTest {
    def main(args: Array[String]): Unit = {

      val world = new HelloWorld

      world.sayHello()
      println(world.getName)
    }
  }

}
