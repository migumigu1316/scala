package day03

/**
  *
  * @ClassName: ListNameDemo
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/19 16:57
  *
  * 需求:
  * 用递归函数给list中的每个元素添加上指定的前缀,并打印加上前缀的元素
  */
class ListNameDemo {
  def Lists(list: List[Int],x:String): Unit ={
    if (list != Nil){
      // List:不可变的，使用head可以引用其头部，使用tail可以引用其尾部
      println(x+list.head)
      Lists(list.tail,x)
    }
  }
}
object ListNameDemoTest{
  def main(args: Array[String]): Unit = {
    val l = new ListNameDemo
    l.Lists(List(1,2,3,4),"+")
  }
}