package day13

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.expressions.{MutableAggregationBuffer, UserDefinedAggregateFunction}
import org.apache.spark.sql.types._
import org.apache.spark.sql.{DataFrame, Row, SQLContext}
import org.apache.spark.{SparkConf, SparkContext}

/**
  *
  * @ClassName: UDAF
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/10/9 14:27
  *
  * 自定义聚合函数 UDAF
  */
object UDAF {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("UDAF").setMaster("local")
    val sc = new SparkContext(conf)
    val ssc = new SQLContext(sc)

    //构造模拟数据
    val names =Array("leo","kitty","jack","tom","Marry")
    val namesRDD: RDD[String] = sc.parallelize(names,5)
    val namesRowRDD: RDD[Row] = namesRDD.map(t=>Row(t))
    val structType = StructType(Array(StructField("name",StringType,true)))

    //构建DataFrame
    val df: DataFrame = ssc.createDataFrame(namesRowRDD,structType)
    //注册临时表
    df.registerTempTable("names")

    //定义和注册自定义函数
    //定义函数
    //注册函数
    ssc.udf.register("strCount",new UDAF)

    //进行单词的统计
    ssc.sql("select name,strCount(name) from names group by name")
      .collect.foreach(println)

    sc.stop()
  }
}
class  UDAF extends UserDefinedAggregateFunction{

  //指的是,输入数据类型
  override def inputSchema: StructType = {
    StructType(Array(StructField("str",StringType,true)))
  }

  //指的是，中间进行聚合时，所处理数据的类型
  override def bufferSchema: StructType = {
    StructType(Array(StructField("count",IntegerType,true)))
  }

  //指的是函数返回值类型
  override def dataType: DataType = {
    IntegerType
  }

  //数据的统一性，一般设置为true
  override def deterministic: Boolean = true

  //为每个分组的数据执行初始化操作
  override def initialize(buffer: MutableAggregationBuffer): Unit = {
    buffer(0)=0
  }

  //在进行聚合的时候，每当有新的值进来，对分组后的聚合数据进行计算
  //本地的聚合操作，相当于Hadoop中的Combiner
  override def update(buffer: MutableAggregationBuffer, input: Row): Unit = {
    buffer(0)=buffer.getAs[Int](0)+1
  }

  //由于spark是分布式的,一个分组的数据,可能会在不同的节点上进行局部聚合,就是updata
  //但是,最后一组数据,在各个节点上的局合值,要进行merge也就是合并
  override def merge(buffer1: MutableAggregationBuffer, buffer2: Row): Unit = {
    buffer1(0) = buffer1.getAs[Int](0) + buffer2.getAs[Int](0)
  }

  //最后一个返回的是结果
  override def evaluate(buffer: Row): Any = {
    buffer.getAs[Int](0)
  }
}
