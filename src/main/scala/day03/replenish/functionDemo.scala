package day03.replenish

/**
  *
  * @ClassName: functionDemo
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/19 22:17
  *
  */
object functionDemo {
  def main(args: Array[String]): Unit = {
    val a = Array(1, 2, 3, 4, 5).map((num: Int) => num * num)
    a.foreach(print)
    println("-------------1-------------------------------")

    // map: 对传入的每个元素都进行映射，返回一个处理后的元素
    val a2 = Array(1, 2, 3, 4, 5).map(2 * _)
    a2.foreach(print)
    println("--------------2------------------------------")

    // foreach: 对传入的每个元素都进行处理，但是没有返回值
    (1 to 9).map("*" * _).foreach(println _)
    println("--------------3------------------------------")

    // filter: 对传入的每个元素都进行条件判断，如果对元素返回true，则保留该元素，否则过滤掉该元素
    print((1 to 20).filter(_ % 2 == 0))
    println("---------------4-----------------------------")

    // reduceLeft: 从左侧元素开始，进行reduce操作，即先对元素1和元素2进行处理，
    // 然后将结果与元素3处理，再将结果与元素4处理，依次类推，即为reduce；reduce操作必须掌握！spark编程的重点！！！
    // 下面这个操作就相当于1 * 2 * 3 * 4 * 5 * 6 * 7 * 8 * 9
    print((1 to 9).reduceLeft(_ * _))
    println("----------------5----------------------------")

    // sortWith: 对元素进行两两相比，进行排序
    val a3 = Array(3, 2, 5, 4, 10, 1).sortWith(_ < _)
    a3.foreach(print)
    println("----------------6----------------------------")

    //flatMap：将元素进行切分压平
   val lists = List("Hello A","Hi B").flatMap(_.split(" "))
    lists.foreach(println)
    println("----------------6----------------------------")
  }

}
