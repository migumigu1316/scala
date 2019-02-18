package day02.work

import scala.collection.mutable.ArrayBuffer

/**
  *
  * @ClassName: RemoveNnegative
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/18 21:09
  *
  */
class RemoveNnegative {
  val a = ArrayBuffer[Int](1,2,3,4,5,-1,-2,-5,-9)
  var flag = false
  var arrayLength = a.length
  var index = 0

  while (index < arrayLength) {
    if (a(index) >= 0) {
      index += 1
    } else {
      if (!flag) {
        flag = true;
        index += 1
      } else {
        a.remove(index)
        arrayLength -= 1
      }
    }
  }

}
object RemoveNnegativeTest{
  def main(args: Array[String]): Unit = {
    val nnegative = new RemoveNnegative
    println()
  }
}