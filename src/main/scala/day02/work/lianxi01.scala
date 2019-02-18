package day02.work

/**
  *
  * @ClassName: lianxi01
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/18 19:15
  *
  */
class lianxi01 {
  //创建一个5位的空数组
  val arr1 = new Array[Int](5)
  //将空数组赋值
  arr1(0)=1
  arr1(1)=2
  arr1(2)=3
  arr1(3)=4
  arr1(4)=5
  //然后添加一个新的数组，组合成一个新的数组
  val arr2 = Array[Int](6,7,8,9,10)

  val a = arr1.zip(arr2)

}

object lianxiTest {
  def main(args: Array[String]): Unit = {
    val l = new lianxi01
    for (e<- l.arr2) print(e + "\t")
    println()//为了换行

    //遍历组合后的数组元素，用多种方式实现遍历（最少两种）
    //第一种遍历
    for (a<- l.a)print(a + "\t")
    println()//为了换行

    //第二种遍历
    for (i <- 0 until l.a.length)print(l.a(i) +"\t")
    println()//为了换行

   // l.a.foreach(x=>print(x))

    //倒序遍历
    for (i <- (0 until l.a.length).reverse)print(l.a(i)+"\t")
  }
}