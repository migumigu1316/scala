package day04

/**
  *
  * @ClassName: MatchDemo03
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/20 11:26
  *
  * 匹配数组,集合
  * 3. 对Array和List进行模式匹配
  * // 对Array进行模式匹配，分别可以匹配带有指定元素的数组、
  * 带有指定个数元素的数组、以某元素打头的数组
  */
object MatchDemo03 {
  def main(args: Array[String]): Unit = {
    //匹配数组
    val arr = Array(1,2,3,4)
    arr match{
      case Array(1,x,y,n) => println(s"${x},${y},${n}")
      case Array(1,2,y,n) => println(s"${y},${n}")
      case Array(1,x,y) => println(s"${x},${y}")
      case _ => println("Nothing......")
    }

    //匹配集合
    val list= List("leo","jack","jen")
    list match {
      case "leo" :: Nil => println("Hello")
        // :: 代表相加
      case list01 :: list02 :: list03 :: Nil => println("Hi , "
      + list01 + " Hi  , " + list02 + " Hi , " + list03)
      case _ => println("Nothing......")
    }
  }
}
