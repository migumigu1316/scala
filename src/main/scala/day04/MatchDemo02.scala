package day04

import scala.util.Random

/**
  *
  * @ClassName: MatchDemo02
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/20 11:15
  *
  * 用模式匹配进行匹配类型
  */
/**
  * // Scala的模式匹配一个强大之处就在于，可以直接匹配类型，而不是值！！！
  * 这点是java的switch case绝对做不到的。
  * // 理论知识：对类型如何进行匹配？其他语法与匹配值其实是一样的，但是匹配类型的话，
  * 就是要用“case 变量: 类型 => 代码”这种语法，而不是匹配值的“case 值 => 代码”这种语法。
  */
object MatchDemo02 {
  def main(args: Array[String]): Unit = {
    val cc = new CaseClass
    val arr = Array("Strings",12,true,cc)//匹配----字符串,数字,布尔值,类
    val arr2 = arr(Random.nextInt(arr.length))//随机取数组中的元素
    arr2 match {
      case str:String => println("match " + s"${str}")
      case int:Int => println("match " + s"${int}")
      case boolean: Boolean =>println("match boolean")
      case caseClass: CaseClass => println("match CaseClass")
      case _ => println("Nothing......")
    }
  }
}

class CaseClass