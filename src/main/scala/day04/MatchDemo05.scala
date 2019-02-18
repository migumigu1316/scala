package day04

/**
  *
  * @ClassName: MatchDemo05
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/20 11:54
  *
  * Option的模式匹配
  * 如果我们数据在获取的时候,判断是否获取到了,如果没有获取到,将返回None类型,获取到了返回Some类型
  */
/**
  * Scala有一种特殊的类型，叫做Option。Option有两种值，一种是Some，表示有值，一种是None，表示没有值。
  * Option通常会用于模式匹配中，用于判断某个变量是有值还是没有值，这比null来的更加简洁明了
  */
object MatchDemo05 {
  def main(args: Array[String]): Unit = {
    val grads = Map("Leo" -> "A","jack" -> "B","jen" -> "C")
    val option: Option[String] = grads.get("Leo")
    option match {
      case Some(grade) => println("your grade is " + grade)
      case None  => println("Sorry")
    }
  }
}
