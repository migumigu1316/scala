package day12

import org.apache.spark.sql.{DataFrame, SQLContext, SaveMode}
import org.apache.spark.{SparkConf, SparkContext}

/**
  *
  * @ClassName: LoadOrSave
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/10/8 16:42
  *
  * 数据源读取和存储
  */
object LoadOrSave {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("LoadOrSave").setMaster("local")
    val sc = new SparkContext(conf)
    val ssc = new SQLContext(sc)

    //load默认的读取文件的格式是parquet文件,可以直接通过.format() 进行更改读取方式,
    //注意:读取parquet文件,内容是打印不出来的
    val df1: DataFrame = ssc.read.load(
      "F:\\IDEA_WorkSpace\\scala\\src\\TestData\\day12\\u.parquet")
    df1.show()

    //读json格式的数据
    val df2: DataFrame = ssc.read.format("json").load(
      "F:\\IDEA_WorkSpace\\scala\\src\\TestData\\day12\\students.json")
    df2.show()

    val df3: DataFrame = ssc.read.json(
      "F:\\IDEA_WorkSpace\\scala\\src\\TestData\\day12\\userlog.json")

    //保存数据.利用save进行保存
//    df1.write.save("")
//    df1.write.jdbc()
//    df2.write.format("jdbc").save("")
//    df2.write.format("jdbc").mode(SaveMode.Append).save("")
//    df3.write.parquet("")
  }
}
