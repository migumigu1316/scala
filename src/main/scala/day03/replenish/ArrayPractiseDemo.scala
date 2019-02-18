package day03.replenish

/**
  *
  * @ClassName: ArrayPractiseDemo
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/19 23:32
  *
  */
object ArrayPractiseDemo {
  def main(args: Array[String]): Unit = {
    val arr = Array(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

    // 调用par方法，此时会有多个线程同时进行聚合计算
    // 每个线程会计算一部分，最后再全局聚合
    // ((1+2+3+4)+(5+6+7+8)+(9+10))

    val sum: Int = arr.par.sum
    //    val sum1 = arr.par.reduce((x,y) => x + y)

    val sum1 = arr.reduce(_ + _) //单线程
    println(sum1 + "------1--------")
    val sum2 = arr.par.reduce(_ + _) //多线程
    println(sum2 + "------2--------")

    //折叠：有初始值（无特定顺序）
    val sumed1: Int = arr.fold(0)(_ + _) // 单线程
    println(sumed1 + "------3--------")
    val sumed2: Int = arr.par.fold(10)(_ + _) // 多线程线程
    println(sumed2 + "------4--------")


    /**
      * 求交集并集差集
      */
    val l1 = List(5, 6, 4, 7)
    val l2 = List(1, 2, 3, 4)

    //并集的3种写法
    val res1: List[Int] = l1 union l2
    println(res1)

    val res2 = l1 union (l2)
    println(res2)

    val res3 = l1 ++ l2
    println(res3)

    //求交集,2中写法

    val ints1 = l1 intersect(l2)
//    val ints1 = l1 intersect l2
    println(ints1)

    //求差集

  }

}
