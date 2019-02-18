package day02

/**
  *
  * @ClassName: student
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/18 11:39
  *
  */
/**
  * // 定义不带private的var ，此时scala生成的面向JVM的类时，会定义为private的name字段，
  * 并提供public的getter和setter方法
  * // 而如果使用private修饰，则生成的getter和setter也是private的
  * // 如果定义val ，则只会生成getter方法
  * // 如果不希望生成setter和getter方法，则将声明为private[this]
  */
class student {
  //内部会有默认的get和set方法
  var name = "leo"
}

object studentTest{
  def main(args: Array[String]): Unit = {
    val student = new student
    student.name="l"
    println(student.name)
  }
}