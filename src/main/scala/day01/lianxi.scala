package day01

/**
  *
  * @ClassName: lianxi
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/17 20:55
  *
  */
object lianxi {
  def main(args: Array[String]): Unit = {
    val x = 1
    val y = if (x > 0) 1 else -1
    println(y)

    //支持混合类型表达式
    val z = if (x > 1) 1 else "error"
    //打印z的值
    println(z)

    //如果缺失else，相当于if (x > 2) 1 else ()
    val m = if (x > 2) 1
    println(m)

    //if 和 else if
    val k = if (x < 0) 0
    else if (x >= 1) 1 else -1
    println(k)


    circulation
  }

  def circulation: Unit = {
    //for(i <- 表达式),表达式1 to 10返回一个Range（区间）
    //每次循环将区间中的一个值赋给i
    val one =for (i<- 1 to 10 )
      println(i)
    //for(i<- arr)
    val arr1 = Array("a","b","c")
    for (i<- arr1)
      println(i)

    for (i<- 0 until arr1.length)
      println(arr1(i))
  }

  //高级for循环
  //每个生成器 都可以带一个条件，注意：if前面没有分号
  for (i<- 1 to 3; j<- 1 to 3 if i != j)
    print((10 * i + j) + " ")
    println()


  //for推导式：如果for循环的循环体以yield开始，
  //则该循环会构建出一个集合或数组,每次迭代生成集合中的一个值
  val v = for (i <- 1 to 10) yield i * 10
  println(v)



}
