package sparkKnowledge

import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}

/**
  * @Description: TODO 
  * @ClassName: SparkOnHive2
  * @Author: xqg
  * @Date: 2019/1/10 20:45
  *
  */
object SparkOnHive2 {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession.builder()
      .appName("SparkOnHive2")
      .master("local[2]")
      .config("hadoop.home.dir","/user/hive/warehouse")
      .enableHiveSupport() //hive
      .getOrCreate()

    //TODO 使用哪个库 如：use databaseName
    spark.sql("use sqlstudy")

    //TODO spark.sql("SQL 语句")
    val df: DataFrame = spark.sql("select * from sqlstudy1")
    df.show()

    //TODO 从Hive表中查询出数据后，保存到其他表中
    //TODO 保存的模式，如果存在就覆盖 Overwrite
    df.write.mode(SaveMode.Overwrite).saveAsTable("save_tableName")

    spark.stop()
  }
}
