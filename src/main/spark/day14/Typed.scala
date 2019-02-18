package day14

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}

/**
  *
  * @ClassName: Typed
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/10/10 9:48
  *
  * Typed: 强类型
  */
object Typed {
  def main(args: Array[String]): Unit = {
    //屏蔽不必要的日志显示在终端上
    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
    Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)

    val spark: SparkSession = SparkSession
      .builder()
      .appName("Typed")
      .master("local")
      .getOrCreate()
    //导入隐士转换
    import spark.implicits._

    val df: DataFrame = spark.read.json(
      "F:\\IDEA_WorkSpace\\scala\\src\\TestData\\day13\\a.json")

    //将df转换成ds,调用as[]
    val ds: Dataset[Pros] = df.as[Pros]

    //查看分区数
    println(ds.rdd.partitions.size)

    //改变分区,可以用repartition和coalesce
    val repartitions: Dataset[Pros] = ds.repartition(5)

    //DataSet类型,需要转换成RDD
//    println(repartitions.rdd.getNumPartitions)//获取分区数

    //改变分区,用coalesce
    val coalesces: Dataset[Pros] = repartitions.coalesce(2)
//    println(coalesces.rdd.getNumPartitions)//获取分区数

    //去重操作,distinct drop
    /**
      * 区别:在于distinct是根据每一条数据进行完整内容的对比去重
      * 而 dropDuplicates可以根据指定的字段去重
      */
    val dis: Dataset[Pros] = coalesces.distinct()//根据每一条数据进行完整内容的对比去重
    val drop: Dataset[Pros] = coalesces.dropDuplicates("name")//根据指定的字段去重
//    dis.show()
//    drop.show()

    /** -----------------------------------------------------------------------*/
    val df1: DataFrame = spark.read.json(
      "F:\\IDEA_WorkSpace\\scala\\src\\TestData\\day13\\c.json")
    val ds1: Dataset[Pros] = df1.as[Pros]

    //获取当前DataSet中有的,但是在另一个DataSet中没有的元素
//    ds.except(ds1).show()

    //过滤
    ds.filter("age > 30").show()

//    //求交集
//    dis.intersect(ds1).show()
//    ds.map(t=>(t.name,t.age)).show()
    ds.sort($"age".desc,$"salary".desc).show()

    spark.stop()
  }
}
case class Pros(name:String,age:Long,depId:Long,gender:String,salary:Long)