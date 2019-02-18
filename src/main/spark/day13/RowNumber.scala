package day13

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, SQLContext}
import org.apache.spark.{SparkConf, SparkContext}


/**
  *
  * @ClassName: RowNumber
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/10/9 11:21
  *
  * >>>>>重点:<<<<<
  * spark sql 的窗口函数  row_number() over()
  */
object RowNumber {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("RowNumber").setMaster("local")
    val sc = new SparkContext(conf)
    val ssc = new SQLContext(sc)
    val lines: RDD[String] = sc.textFile(
      "F:\\IDEA_WorkSpace\\scala\\src\\TestData\\day13\\teacher.txt")

    //创建一个普通的RDD
    val teacher: RDD[teachers] = lines.map(t => {
      val s: Array[String] = t.split(" ")
      teachers(s(0), s(1))
    })

    //导入隐士转换
    import ssc.implicits._
    //第一种方式创建DataFrame,已知字段的类型
    //这里可以将字段的名字重新赋值,构建DF
    val df: DataFrame = teacher.toDF("subject","teacher")
    df.registerTempTable("teachers")

    //现在将里面的里面学生最喜欢的老师统计出来,也就是分组聚合
    val df1: DataFrame = ssc.sql("select subject,teacher,count(*) as counts " +
      "from teachers group by subject,teacher")

//        df1.show()
   /**+-------+--------+------+
      |subject| teacher|counts|
      +-------+--------+------+
      |    php| liuneng|     7|
      |bigdata|   laosi|     5|
      |bigdata| laozhao|     3|
      | javaee| laowang|     4|
      |    php|guangkun|     8|
      | javaee|   laoqi|     6|
      | javaee|zhangsan|    10|
      +-------+--------+------+
    */
    //接下来开始使用开窗函数，进行数据排序统计，开窗函数相当于一个伪列，
    // 我们不可以直接使用，但是可以给他当成一个结果集，然后调用
    //如我我们想用之前获取的结果集，那么需要将之前的结果集注册为临时表
    df1.registerTempTable("teacher_tmp")

    //注意------调用row_number() over() 进行排序 倒序显示 ,然后在取第一名
    val df2: DataFrame = ssc.sql("select * from (" +
      "select *,row_number() " +
      "over(partition by subject order by counts desc) ranked " +
      "from teacher_tmp) t " +
      "where ranked < 2")
    df2.show()

    /**
      * +-------+--------+------+------+
      * |subject| teacher|counts|ranked|
      * +-------+--------+------+------+
      * | javaee|zhangsan|    10|     1|
      * |bigdata|   laosi|     5|     1|
      * |    php|guangkun|     8|     1|
      * +-------+--------+------+------+
      */

    sc.stop()
  }
}

case class teachers(x:String,y:String)