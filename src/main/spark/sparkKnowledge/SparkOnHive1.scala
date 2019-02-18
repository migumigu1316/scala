package sparkKnowledge

import org.apache.spark.sql.{DataFrame, SaveMode}
import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.{SparkConf, SparkContext}

/**
  *
  * @Description: TODO Spark On Hive
  * @ClassName: Spark
  * @Author: xqg
  * @Date: 2019/1/10 20:18
  *
  */
object SparkOnHive1 {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("SparkOnHive1").setMaster("local[2]")
    val sc = new SparkContext(conf)
    val sqlContext = new HiveContext(sc)

    //读取hive表
    //TODO 形式：库名.表名
//    sqlContext.table("dataBase")
//      .createOrReplaceTempView("tableName")
    //TODO 使用哪个库 如：use databaseName
    sqlContext.sql("use sqlstudy")

    //TODO SQL语句
    val df: DataFrame = sqlContext.sql(
      """
        |select *
        |from sqlstudy1
      """.stripMargin)

    //TODO 或者sql语句写成下面这中形式
//    val df: DataFrame = sqlContext.sql("select * from tableName")
    /**
      * 运行时会出现类似这个错误 ：
      * 出现错误(null) entry in command string: null chmod 0700
      * 解决办法:
      *
      * 在https://github.com/SweetInk/hadoop-common-2.7.1-bin中下载hadoop.dll，并拷贝到c:\windows\system32目录中。
      */

    df.show()

    //TODO 从Hive表中查询出数据后，保存到其他表中
    //TODO 保存的模式，如果存在就覆盖 Overwrite
    df.write.mode(SaveMode.Overwrite).saveAsTable("save_tableName")
    sc.stop()
  }
}
