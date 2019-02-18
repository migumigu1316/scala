package day03

/**
  *
  * @ClassName: scalaWCbak
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/19 19:55
  * 用scala实现WordCount
  *
  * scalaWC的完整版
  */
object scalaWCbak {
  def main(args: Array[String]): Unit = {
    //数据源
    val lines = List("hello tom hello jerry","hello Jen hello","hello tom")

    //把数据切分并压平
    //通过这一步数据将变成List("hello","tom","hello",....)
    val words: List[String] = lines.flatMap(_.split(" "))
//    words.foreach(println)
    //过滤空格
    val filtered: List[String] = words.filter(_ != " ")

    //生成一个元组:(hello,1)
    //返回的结果(hello,1)(tom,1)(hello,1)(jerry,1)(hello,1)(Jen,1)(hello,1)(hello,1)(tom,1)
    val tuples: List[(String, Int)] = filtered.map((_,1))
//    tuples.foreach(println)

    //以key进行分组
    //返回的结果是(tom,List((tom,1), (tom,1)))
    val grouped: Map[String, List[(String, Int)]] = tuples.groupBy(x=>x._1)//x是生成的每个元组的值,如(hello,1)
//   grouped.foreach(println)

    //开始统计分组后的相同单词的个数,其实只是需要统计单词对应的list长度即可
    //返回的结果 (Jen,1)(tom,2)(jerry,1)(hello,5)
    val result: Map[String, Int] = grouped.mapValues(_.size)
//    result.foreach(println)

    //降序排列
    val resulted: List[(String, Int)] = result.toList.sortBy(_._2).reverse

    //输出
    println(resulted)
  }
}
