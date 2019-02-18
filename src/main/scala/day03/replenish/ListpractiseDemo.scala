package day03.replenish

/**
  *
  * @ClassName: ListpractiseDemo
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/19 23:05
  *
  */
object ListpractiseDemo {
  def main(args: Array[String]): Unit = {

    //1. List
    // List代表一个不可变的列表
    // List的创建，val list = List(1, 2, 3, 4)
    // List有head和tail，head代表List的第一个元素，tail代表第一个元素之后的所有元素，list.head，list.tail
    // List有特殊的::操作符，可以用于将head和tail合并成一个List，0 :: list
    // ::这种操作符要清楚，在spark源码中都是有体现的，一定要能够看懂！
    // 如果一个List只有一个元素，那么它的head就是这个元素，它的tail是Nil
    val list1 = List(4, 3, 5, 7, 6, 8, 9, 2, 1, 0)

    //写法2更简单
   // val list2: List[Int] = list1.map((x: Int) => x * 2)

    val list2: List[Int] = list1.map((_ * 2))
    println(list2)
    println("--------------1----------------------------")

    //将list1中的偶数取出来生成一个新的集合
    val list3 = list1.filter(_ % 2 == 0)
    println(list3)
    println("--------------2----------------------------")

    //将list1排序后生成一个新的集合
    val list4 = list1.sortWith(_ > _)
    println(list4)
    println("--------------3----------------------------")

    //反转排序顺序
    val list5 = list1.sorted.reverse
    println(list5)
    println("--------------4----------------------------")

    //将list1中的元素4个一组,类型为Iterator[List[Int]]
    val it: Iterator[List[Int]] = list1.grouped(4)
    val list6 = it.toList
    print(list6 + "\t")
    println("--------------６----------------------------")

    //将多个list压扁成一个List
    val list7 = list6.flatten
    print(list7)
    println("--------------７----------------------------")


  }
}
