package day04

/**
  *
  * @ClassName: StudentsDemo06
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/20 14:19
  *
  * 泛型
  */
class StudentsDemo06[T] (val locald:T) {
  def getLocald(name:T)=println("name is "+ name + " local " + locald)

}
object StuLocalTest{
  def main(args: Array[String]): Unit = {
    val stu=new StudentsDemo06[Int](2017111)
    stu.getLocald(56)
  }
}