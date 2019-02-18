package day04

/**
  *
  * @ClassName: MatchDemo01
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/20 11:07
  *
  * 模式匹配
  */
//匹配字符串
/**
  * 1. 模式匹配基本语法
  * // Scala是没有Java中的switch case语法的，相对应的，Scala提供了更加强大的match case语法，
  * 即模式匹配，类替代switch case，match case也被称为模式匹配
  * // Scala的match case与Java的switch case最大的不同点在于，Java的switch case仅能匹配变量的值，
  * 比1、2、3等；而Scala的match case可以匹配各种情况，比如变量的   类型、集合的元素、有值或无值
  * // match case的语法如下：变量 match { case 值 => 代码 }。如果值为下划线，则代表了不满足
  * 以上所有情况下的默认情况如何处理。此外，match case中，只要一个case分支满足并处理了，
  * 就不会继续判断下一个case分支了。（与Java不同，java的switch case需要用break阻止）
  * // match case语法最基本的应用，就是对变量的值进行模式匹配
  */
object MatchDemo01 {
  def main(args: Array[String]): Unit = {
    val strings = "A"
    strings match {
      case "A" => println("优秀")
      case "B" => println("良好")
      case "C" => println("合格")
      case _ => println("不及格")
    }
  }

}
