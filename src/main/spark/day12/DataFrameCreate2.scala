package day12

import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

/**
  *
  * @ClassName: DataFrameCreate2
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/10/8 11:05
  *
  * spark sql 基本操作
  */
object DataFrameCreate2 {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("df").setMaster("local")
    val sc = new SparkContext(conf)
    val ssc = new SQLContext(sc)
    //读取json数据格式
    //创建出来的这个图形,可以理解为一张表
    val df = ssc.read.json("F:\\IDEA_WorkSpace\\scala\\src\\TestData\\day12\\students.json")

    //查看文件的内容,只显示前20条记录,相当于SQL中的select * from 表名
    df.show()

    //显示n条数据
    df.show(2)//显示2条数据

    //内容是否最多显示20个字符,默认是true
    df.show(false)//false显示全部字符

    //综合前面的设置显示的记录条数,以及过长的字符串显示格式
    df.show(1,false)

    //打印DataFrame的元数据
    df.printSchema()

    //和show方法不同,这个方法表示将所有的数据获取,然后返回一个Array对象
    df.collect()

    //传入字段类型的名字,统计数值类型字段的值,比如count,平均值,差值,最大最小值
    df.describe("age","name").show()

    //获取第一行数据
    println(df.first())

    //where条件语句,可以传入条件,然后根据条件进行显示,可以多个条件
    df.where("id = 1 or name = 'leo'").show()

    //过滤,根据字段进行筛选,和where类似
    df.filter("id = 1 or name = 'leo'").show()

    //查询指定的字段
    df.select("id","name").show()

    //获取指定的字段,注意:结合select使用
    println(df.col("id"))

    //去除指定的字段,一次只能去除一个
    df.drop("id").show()

    //排序,orderBy 和 sort 默认升序
    df.orderBy("age").show()//升序
    df.orderBy(-df.col("age")).show()//降序
    df.sort(-df.col("age")).show()//降序

    //分组
    df.groupBy("id")

    //去重
    df.distinct().show()
    //按照指定的字段去重,里面可以传入Array和Seq类型,很少用
    df.dropDuplicates(Seq("id")).show()

    //查询某几列所有的数据,并对列进行计算
    df.select(df.col("id"),df.col("age").plus(1)).show()

    //根据某一列的值进行过滤
    df.filter(df.col("age").gt(18)/*gt:大于*/).show()

    //根据某一列进行分组,然后再进行聚合
    df.groupBy(df.col("age")).count().show()
    sc.stop()
  }
}
