package day02

/**
  *
  * @ClassName: ApplyDemo
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/18 15:07
  *
  */
/**
  * object中非常重要的一个特殊方法，就是apply方法
  * 通常在伴生对象中实现apply方法，并在其中实现构造伴生类的对象的功能
  * 而创建伴生类的对象时，通常不会使用new Class的方式，而是使用Class()的方式，
  *   隐式地调用伴生对象得apply方法，这样会让对象创建更加简洁
  *
  * @param name
  */
class ApplyDemo(val name:String){

}
object ApplyDemo {
  def apply(name:String): ApplyDemo = new ApplyDemo(name)
}

object ApplyTest /* extends App */{
  def main(args: Array[String]): Unit = {//main方法可以用继承APP代替
    //不加new也可以,因为apply进行了一次隐士的调用,实现初始化操作
    val applys = new ApplyDemo("lily")
    println(applys.name)
  }
}