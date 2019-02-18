package day02.work

//import scala.collection.mutable.Map
import scala.collection.mutable
import scala.collection.mutable._
/**
  *
  * @ClassName: Map
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/18 21:19
  *
  */
class Map {
  // 创建一个可变的Map,导包
  val ages = Map("Leo" -> 30, "Jen" -> 25, "Jack" -> 23)

  // 创建一个空的HashMap
  val age1 = new mutable.HashMap[String,Int]

  //Map的元素类型----Tuple
  //简单的Tuple
  val t = ("leo",12)

}
object MapTest{
  def main(args: Array[String]): Unit = {
    val m = new Map
    println(m.ages("Leo"))

//    println(m.ages("Leoy"))//不存在会报错

    // 使用contains函数检查key是否存在
    println(m.ages.contains("Leo"))//true   --------有结果返回true,没有false

    // getOrElse函数
    println(m.ages.getOrElse("Leo",0))//30  -------有结果返回结果值,没有返回0

    //遍历Map函数
    //遍历map的key
    for ((k,v) <- m.ages)println(k + " " + v)

    //遍历map的key
    for(key <- m.ages.keySet)println(key)

    // 遍历map的value
    for (value <- m.ages.values) println(value)

    // 生成新map，反转key和value
    val m1 = for ((key, value) <- m.ages) yield (value, key)
    println(m1)

    // 访问Tuple
    println(m.t._1)
    println(m.t._2)

  }
}