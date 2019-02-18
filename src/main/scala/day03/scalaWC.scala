package day03

/**
  *
  * @ClassName: scalaWC
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/19 15:16
  *
  * 用scala实现wordCount
  */
object scalaWC {
  def main(args: Array[String]): Unit = {
    //数据源
    val lines = List("hello tom hello lerry","hello jen hello","hello lily jen")

    //把数据切分并压平
    val words = lines.flatMap(_.split(" "))
//    lines.flatMap(_.split(" ")).filter(_ != "").map((_,1)).groupBy(x => x._1).mapValues
//    (_.size)
    //过滤空格
    val filtered = words.filter(_ != "")

    //生成一个元组:(hello,1)
    val tuples = filtered.map((_,1))

    //以key进行分组
    val grouped: Map[String, List[(String, Int)]] = tuples.groupBy(x=>x._1)

    //开始统计分组后的相同单词的个数,其实只是需要统计单词对应的list长度即可
//    val result = grouped.map(x=>(x._1,x._2.size))
    val result = grouped.mapValues(_.size)

    //降序排序
    val reverse = result.toList.sortBy(_._2).reverse//取出每个单词出现的次数,按照次数进行排序

    //输出
    println(reverse)

  }
}
