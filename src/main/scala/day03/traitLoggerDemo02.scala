package day03

/**
  *
  * @ClassName: traitLoggerDemo02
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/19 11:06
  * 2. 在Trait中定义具体方法
  *   // Scala中的Triat可以不是只定义抽象方法，还可以定义具体方法，
  *   此时trait更像是包含了通用工具方法的东西
  *
  *      在spark中使用了trait来定义了通用的日志打印方法
  */
trait traitLoggerDemo02 {//trait相当于接口
  def log(message:String) = message
}

class Logger(val name:String) extends traitLoggerDemo02{
  def makeTraits(age:Int):Unit={
    println(log(name) + "进行打印log......" + age)
  }
}

object LoggerTest{
  def main(args: Array[String]): Unit = {
    val logs = new Logger("lily")
    logs.makeTraits(20)
  }
}