package day04

/**
  *
  * @ClassName: ScalaWordCounts
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/20 10:13
  *
  * 统计单词的总数
  */
object ScalaWordCounts {
  def main(args: Array[String]): Unit = {

   //使用scala的io包将内容的数据读取出来
    val lines01 = scala.io.Source.fromFile("F://a.txt").mkString
    val lines02 = scala.io.Source.fromFile("F://b.txt").mkString

    //将两个文件的数据放入list集合中
    val lines = List(lines01,lines02)

    //开始进行单词的计数统计
    val result: Int = lines.flatMap(_.split("\n")).flatMap(_.split("\\s"))
      .map((_,1)).map(_._2).reduceLeft(_+_)

    //打印
    println(result)

  }
}
