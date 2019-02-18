 package day12

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, SQLContext, SaveMode}
import org.apache.spark.{SparkConf, SparkContext}
/**
  *
  * @ClassName: RDD2DataFrame
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/10/8 15:03
  * 第一种方式 反射的方式创建DF
  */
object RDD2DataFrame {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("DF").setMaster("local")
    val sc = new SparkContext(conf)
    val ssc = new SQLContext(sc)
    //创建一个普通的RDD
    val files: RDD[String] = sc.textFile(
      "F:\\IDEA_WorkSpace\\scala\\src\\TestData\\day12\\students.txt")
    val student: RDD[students] = files.map(t => {
//    数据  {"id":1, "name":"leo", "age":18}
      val lines: Array[String] = t.split(",")
      //类测名称 第一个值(类型) 第二个值(类型) 第三值(类型)
      students(lines(0).toInt, lines(1), lines(2).toInt)
    })

    //需要导入隐士转换
    import ssc.implicits._

    //创建DataFrame
    //已经知道的类型的可以直接转成DataFrame
    val df: DataFrame = student.toDF//转成DF

    //注册临时表
    df.registerTempTable("student")

    //针对这个临时表执行SQL语句
    val dfs: DataFrame = ssc.sql("select * from student where age >= 18")

//    dfs.write.mode(SaveMode.Append).text("hdfs://hadoop01:9000")
    dfs.show()
    sc.stop()
  }
}

//样例类,已经知道类型
case class students(id:Int,name:String,age:Int)