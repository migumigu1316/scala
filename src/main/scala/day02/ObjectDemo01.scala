package day02

/**
  *
  * @ClassName: ObjectDemo01
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/18 14:33
  *
  * object 相当于class的当个实例,通常在里面放一些静态的变量或者方法
  * 第一次调用Object方法时就会执行Object构造器,也就是Object内部不在方法中的代码执行
  * 但是,Object不能定义接收参数的构造器
  * 注意:Object的构造器只会在第一次调用时执行一次,以后调用在不会执行构造器
  */
object ObjectDemo01 {
  private val Num=2
  println("this ObjectDemo01 object")

  def getNum=Num
}

object ObjectTest{
  def main(args: Array[String]): Unit = {
    //通过方法获取里面private声明的变量

    println(ObjectDemo01.getNum)
  }
}
