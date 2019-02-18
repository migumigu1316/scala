package day04

/**
  *
  * @ClassName: Father
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/20 14:48
  *
  * 泛型: 下边界 Bounds >:
  */
//案例：领取身份证（父子领取）
class Father(val name:String) {

}
class Child(name:String)extends  Father(name)

class testChild

class FatherAndChild{
  def getFC[R >: Child](r:R): Unit ={
    if (r.getClass == classOf[Child]){
      println("领取成功!")
    }else if (r.getClass == classOf[Father]){
      println("先签字,在领取")
    }else{
      println("Sorry,你不能领取")
    }
  }
}
object FatherAndChildTest{
  def main(args: Array[String]): Unit = {
    val father = new Father("father")
    val child = new Child("child")
    val fc = new FatherAndChild
    val test = new testChild
    fc.getFC(father)
  }
}