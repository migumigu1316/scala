package day02

/**
  *
  * @ClassName: OverrideDemo
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/18 16:15
  *
  */
/**
  * // Scala中，如果子类要覆盖一个父类中的非抽象方法，则必须使用override关键字
  * // override关键字可以帮助我们尽早地发现代码里的错误，比如：override修饰的
  * 父类方法的方法名我们拼写错了；比如要覆盖的父类方法的参数我们写错了；等等
  * // 此外，在子类覆盖父类方法之后，如果我们在子类中就是要调用父类的被覆盖的方法呢？
  * 那就可以使用super关键字，显式地指定要调用父类的方法
  */
class OverrideDemo {
  private var name = "张三"
  def getName = name
}

class OverrideStu extends OverrideDemo{
  private var score= 100
  //利用Override重写父类的方法,通过super来调用父类的getName
  override def getName = score + " " + super.getName
}

object OverrideTest{
  def main(args: Array[String]): Unit = {
    val stu = new OverrideStu
    println(stu.getName)
  }
}