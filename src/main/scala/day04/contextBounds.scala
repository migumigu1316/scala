package day04

/**
  *
  * @ClassName: contextBounds
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/20 15:24
  *
  * T:Ordering
  */
/**
  * // Context Bounds是一种特殊的Bounds，其实个人认为，Context Bounds之所以叫Context，
  * 是因为它基于的是一种全局的上下文，需要使用到上下文中的隐式值以及注入
  * @param number1
  * @param number2
  * @param ev$1
  * @tparam T
  */
class contextBounds[T: Ordering](val number1: T, val number2: T) {
  def max(implicit ordering: Ordering[T]) =
    if (ordering.compare(number1, number2) > 0) number1 else number2

}

object contextBoundsTest {
  def main(args: Array[String]): Unit = {
    val context = new contextBounds(5, 8)
    println(context.max)
  }
}