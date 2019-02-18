package day13

import org.apache.spark.sql.SparkSession

/**
  *
  * @ClassName: DataSet01
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/10/9 15:11
  *
  * spark 2.0 新特性
  * DataFrame = Dataset[Row]
  * 案例分析: 计算部门的平均薪资和年龄
  * 需求:
  * 1. 只统计年龄>20的员工
  * 2. 根据部门名称和员工性别要求进行统计
  * 3. 统计每个部门性别的平均薪资和平均年龄
  */
object DataSet01 {
  def main(args: Array[String]): Unit = {
    //首先第一步变了,在spark2.0版本后,直接可以使用sparkSession,不需要conf和context
    val spark =  SparkSession
      .builder()
      .appName("DataSet01")
      .master("local")
      .getOrCreate()

    //导入隐式转换
    import spark.implicits._
    //导入Spark SQL中的内置函数functions
    import org.apache.spark.sql.functions._

    //首先将两个文件加载进来，形成两个DataFrame
    val df1 = spark.read.json("F:\\IDEA_WorkSpace\\scala\\src\\TestData\\day13\\a.json")
    val df2 = spark.read.json("F:\\IDEA_WorkSpace\\scala\\src\\TestData\\day13\\b.json")

    //根据需求进行处理，第一个需求过滤20以下的员工
    //第二个需求，根据部门名称和员工进行统计，那么就需要将两个表join
    //join的第一个参数是表名，第二个参数是连接条件
    df1.filter("age > 20").join(df2,$"depId"===$"id")
      //根据我们join后的数据进行分组
      .groupBy(df2("name"),df1("gender"))
      //然后进行统计操作
      .agg(avg(df1("salary")),avg(df1("age")))
      //执行Action操作，将结果显示
      .show()

    spark.stop()
  }
}
