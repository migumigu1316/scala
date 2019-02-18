package day03.replenish

/**
  *
  * @ClassName: functionDemo02
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/19 22:54
  *
  */
object functionDemo02 {

  def main(args: Array[String]): Unit = {

    //1.闭包:闭包最简洁的解释,函数在变量不处于其有效作用域时,还能对变量进行访问,即为闭包
    /**
      * 两次调用a函数，传入不同的msg的值，并且创建不同的函数返回，然后msg只是一个局部变量，
      * 却在a执行完之后，还可以继续存在创建的函数之中，当调用第二个函数的时候，msg的值被保
      * 留了下来存在函数的内部缓存中，可以反复的使用，这种变量已经超出了作用域，还可以使用的情况，就叫闭包！
      */
    def a(msg: String) = (name: String) => println(msg + " " + name)

    val b = a("hello")

    val c = a("hi")

    a("1")

    b("12") //hello 12

    c("23") //hi 23

    //2.柯里化:指的是,将原来接受两个参数的一个参数,转化为两个函数,
    //    第一个函数接受原先的第一个参数,然后返回接受原先第二个参数的第二个函数


  }
}
