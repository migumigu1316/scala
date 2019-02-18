package day03.work

/**
  *
  * @ClassName: flatMapWork
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/19 20:48
  *
  */
// faltMap案例实战：将List中的多行句子拆分成单词
//List("Hello World", "You Me")

object flatMapWork {
  def main(args: Array[String]): Unit = {

    val lines = List("Hello World", "You Me")
    //把数据切分并压平
    val words = lines.flatMap(_.split(" "))
    words.foreach(println)
  }
}
