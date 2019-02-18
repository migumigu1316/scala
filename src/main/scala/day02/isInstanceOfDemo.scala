package day02

/**
  *
  * @ClassName: isInstanceOfDemo
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/18 16:35
  *
  */
/**
  * // 如果我们创建了子类的对象，但是又将其赋予了父类类型的变量。则在后续的程序中，应该如何做？
  * // 首先，需要使用isInstanceOf判断对象是否是指定类的对象，如果是的话，
  * 则可以使用asInstanceOf将对象转换为指定类型
  * // 注意，如果对象是null，则isInstanceOf一定返回false，asInstanceOf一定返回null
  * // 注意，如果没有用isInstanceOf先判断对象是否为指定类的实例，就直接用asInstanceOf转换，
  * 则可能会抛出异常
  */
class isInstanceOfDemo//父类

class Instance extends isInstanceOfDemo//子类

object InstanceTest{
  def main(args: Array[String]): Unit = {
    //子类返回父类的类型
    val fu: isInstanceOfDemo = new Instance

    //先创建一个子类Instance的空类
    val zi : Instance=null

    //接下来进行类型转换，首先转换前要进行类型的判断
    if (fu.isInstanceOf[Instance]){
      val zi = fu.asInstanceOf[Instance]
    }
  }
}