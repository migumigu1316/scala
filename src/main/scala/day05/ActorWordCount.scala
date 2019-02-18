package day05

import scala.collection.mutable.ArrayBuffer
import scala.actors.{Actor, Future}
import scala.io.Source

/**
  *
  * @ClassName: ActorWordCount
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/21 10:29
  *
  */
object ActorWordCount {
  def main(args: Array[String]): Unit = {
    //1.创建读取的file文件的数组
    val files = Array("F://a.txt", "F://b.txt", "F://c.txt")
    //创建一个ArrayBuffer进行局部的结果累加操作
    val values = new ArrayBuffer[Future[Any]]()

    //创建一个map用来储存过滤后的数据
    val ArrMap = new ArrayBuffer[Map[String, Int]]()

    for (file <- files) {
      val task = new Task
      //启动Actor
      task.start()
      //获取actor响应的局部结果
      val value: Future[Any] = task !! Tasks(file)
      values += value
    }
    while (values.size > 0) {
      //过滤,有可能values中有空值
      val arr: ArrayBuffer[Future[Any]] = values.filter(_.isSet)
      for (value <- arr) {
        //进行Futrue[Any]类型的转换，转换成Map[String,Int]类型
        val maps: Map[String, Int] = value.apply().asInstanceOf[Map[String, Int]]
        ArrMap += maps
        //将数组清空，不能一直让它循环
        values -= value
      }
    }
    //foldLeft[B](z: B)(op: (B, A) => B): B =
    val res = ArrMap.flatten.groupBy(_._1).mapValues(_.foldLeft(0)(_ + _._2))
    println(res)
  }
}

case class Tasks(t: String)

class Task extends Actor {
  override def act(): Unit = {
    while (true) {
      receive {
        case Tasks(file) => {
          //读取数组的路径,然后根据路径找到文件
          val linesIt: Iterator[String] = Source.fromFile(file).getLines()

          //将获取到的数据转换成list集合
          val list: List[String] = linesIt.toList

          //接下来,正常编写scala的wordCount操作
          //切分压平
          val words: List[String] = list.flatMap(_.split(" "))
          //转成元组
          val tuples: List[(String, Int)] = words.map((_, 1))
          //分组
          val grouped: Map[String, List[(String, Int)]] = tuples.groupBy(_._1)
          //结果
          val result: Map[String, Int] = grouped.mapValues(_.size)
          //发送
          sender ! result
        }
      }
    }
  }
}