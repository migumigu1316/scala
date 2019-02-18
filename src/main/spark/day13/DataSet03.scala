package day13

import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}

/**
  *
  * @ClassName: DataSet03
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/10/9 17:25
  *
  * 基本操作
  */
object DataSet03 {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession.builder().appName("DataSet03")
      .master("local").getOrCreate()

    //导入隐士转换
    import spark.implicits._

    val df: DataFrame = spark.read.json(
      "F:\\IDEA_WorkSpace\\scala\\src\\TestData\\day13\\a.json")

    //创建临时视图,主要是为了使用SQL
    df.createTempView("tmp")

    //创建视图后,可以执行SQL
//    spark.sql("select * from tmp where age > 30").show()

    //获取元数据信息
    df.printSchema()

    //存储结果集
//    df.write.json("hdfs://hadoop01:9000")

    //转换成DataSet
    val pro: Dataset[Pro] = df.as[Pro]
    pro.show()


    spark.stop()
  }
}
case class Pro(name:String,age:Long,gender:String,salary:Long)