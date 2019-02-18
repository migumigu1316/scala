package day16.work

import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, Row, SQLContext}
import org.apache.spark.{SparkConf, SparkContext}

/**
  *
  * @ClassName: exam01
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/10/12 17:25
  * 7. 数据如下：（10分）
  *        http://java/com.la&123/com.*
  *        http://java/com.la*123/com.）
  *        http://ui/com.la()123/com. ……
  *        http://bigdata/com.la!123/com.[.
  *        .....
  *        需求：
  * 1.	使用spark SQL实现统计URL中学科的出现的次数
  * 2.	并且进行降序排序，要求使用窗口函数实现
  */
object exam01 {
  def main(args: Array[String]): Unit = {
    //屏蔽不必要的日志显示在终端上
    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
    Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)

    val conf: SparkConf = new SparkConf().setAppName("countCourse").setMaster("local")
    val sc = new SparkContext(conf)
    val ssc = new SQLContext(sc)

    //导入隐士转换
    import ssc.implicits._
    //导入Spark SQL中的内置函数functions
    import org.apache.spark.sql.functions._

    //读取数据
    //20161123101523	http://java.learn.com/java/javaee.shtml
    val line: RDD[String] = sc.textFile("F:\\IDEA_WorkSpace\\scala\\src\\TestData\\access.txt")
    val rowRDD: RDD[Row] = line.map(t => {
      val lines: Array[String] = t.split("/")
      //      (lines(2), 1)//(android.learn.com,1)
      //自己给字段定义一个类型
      //Row类型(第一个数据(类型),第二个数据(类型),)
      Row(lines(2), 1)
    })

    val structType = StructType(Array(
      StructField("course", StringType, true),
      StructField("num", IntegerType, true)))

    //创建DataFrame
    val df: DataFrame = ssc.createDataFrame(rowRDD,structType)
    //注册临时表
    df.registerTempTable("courses")

//    1.	使用spark SQL实现统计URL中学科的出现的次数
//    2.	并且进行降序排序，要求使用窗口函数实现
      val frame: DataFrame = ssc.sql(
  "select course,count(1) as sum_course from courses group by course")
//      frame.show()//查看
    /**
      * +-----------------+----------+
      * |           course|sum_course|
      * +-----------------+----------+
      * |   java.learn.com|        61|
      * |bigdata.learn.com|       118|
      * |android.learn.com|         5|
      * |     ui.learn.com|        86|
      * |     h5.learn.com|        75|
      * +-----------------+----------+
      */
    //接下来开始使用开窗函数，进行数据排序统计，开窗函数相当于一个伪列，
    // 我们不可以直接使用，但是可以给他当成一个结果集，然后调用
    //如我我们想用之前获取的结果集，那么需要将之前的结果集注册为临时表
    frame.registerTempTable("courses_tmp")


    val frame1: DataFrame = ssc.sql(
      "select * from(" +
      "select *,row_number() " +
      "over(order by sum_course desc) ranked " +
        "from courses_tmp) t " )

    frame1.show()//查看
    /**
      * +-----------------+----------+------+
      * |           course|sum_course|ranked|
      * +-----------------+----------+------+
      * |bigdata.learn.com|       118|     1|
      * |     ui.learn.com|        86|     2|
      * |     h5.learn.com|        75|     3|
      * |   java.learn.com|        61|     4|
      * |android.learn.com|         5|     5|
      * +-----------------+----------+------+
      */
    sc.stop()
  }
}
