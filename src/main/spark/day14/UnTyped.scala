package day14

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.{DataFrame, SparkSession}

/**
  *
  * @ClassName: UnTyped
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/10/10 10:22
  *
  * 弱类型: select, where, groupBy, agg, join,...
  */
object UnTyped {
  def main(args: Array[String]): Unit = {
    //屏蔽不必要的日志显示在终端上
    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
    Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)

    val spark: SparkSession = SparkSession.builder()
      .appName("UnTyped")
      .master("local")
      .getOrCreate()

    //导入隐士转换
    import spark.implicits._
    //导入 spark SQL 的functions
    import org.apache.spark.sql.functions._

    //加载数据
    val df: DataFrame = spark.read.json(
      "F:\\IDEA_WorkSpace\\scala\\src\\TestData\\day13\\a.json")
    val df2: DataFrame = spark.read.json(
      "F:\\IDEA_WorkSpace\\scala\\src\\TestData\\day13\\b.json")

    //join的第一个参数是表名，第二个参数是连接条件
    df.where("age > 20").join(df2,$"depId"===$"id"/*相同字段连接*/)
      //根据我们join后的数据进行分组
        .groupBy(df2("name"),df("gender"))
      //然后进行统计操作
        .agg(avg("salary"))

        .select($"gender",$"name",$"avg(salary)")
      //执行Action操作，将结果显示
        .show()


    spark.stop()
  }
}
