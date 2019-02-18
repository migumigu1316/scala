package day13

import org.apache.spark.sql.{DataFrame, SparkSession}

/**
  *
  * @ClassName: DataSet02
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/10/9 17:02
  *
  */
object DataSet02 {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession
      .builder()
      .appName("DataSet02")
      .master("local").getOrCreate()

    //导入隐士转换
    import spark.implicits._
    val df: DataFrame = spark.read.json(
      "F:\\IDEA_WorkSpace\\scala\\src\\TestData\\day13\\a.json")

    //将分布式存储在集群上的分布式数据集,全部收集到Driver
//    df.collect().foreach(println)

    //对数据进行统计
//    println(df.count())

    //first获取数据中的第一条数据
//    println(df.first())

    //遍历所有的元素,如果在集群上运行的话,看不见结果
    //分布式运行的时候,只有调用collect才能看到,一般不会这么做,因为怕内存不够
//    df.foreach(println(_))

    //reduce 对数据进行聚合汇总,有一条多一条
//    println(df.map(t => 1).reduce(_ + _))

    //take 取前几个值
//    df.take(2).foreach(println)

    //show 打印显示
//    df.show()

    spark.stop()
  }
}
