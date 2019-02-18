package day03

/**
  *
  * @ClassName: LinkedListDemo
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/19 17:09
  *
  * 使用while循环将LinkedList中的每个元素都 *2
  */
object LinkedListDemo {
  def main(args: Array[String]): Unit = {
    val list = scala.collection.mutable.LinkedList(1, 2, 3, 4, 5)
    var lists = list
    while (lists != Nil) {
      // LinkedList代表一个可变的列表，使用elem可以引用其头部，使用next可以引用其尾部

      //在可变list中用elem,  elem取的是第一个元素
      lists.elem = lists.elem * 2
      //next取的是剩余的所有元素
      lists = lists.next
    }
    list.foreach(println)
  }
}
