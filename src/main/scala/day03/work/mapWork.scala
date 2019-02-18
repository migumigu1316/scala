package day03.work

/**
  *
  * @ClassName: mapWork
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/19 20:35
  */
// map案例实战 ： 为List中每个元素都添加一个前缀 “ name is ”
// List ("Leo", "Jen", "Peter", "Jack")

class mapWork {
  def Lists(list: List[String], x: String):Unit = {
    if (list != Nil){
      println(x+list.head)//x就是需要拼接的内容
      Lists(list.tail,x)
    }
  }
}
object listTest{
  def main(args: Array[String]): Unit = {
    val m = new mapWork
    //调用mapWork中的Lists方法,传入List集合
    m.Lists(List ("Leo", "Jen", "Peter", "Jack"),"name is ")
  }
}