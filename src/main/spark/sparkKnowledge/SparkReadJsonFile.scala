package sparkKnowledge

import org.apache.spark.sql.{DataFrame, DataFrameReader, SQLContext}
import org.apache.spark.{SparkConf, SparkContext}

/**
  *
  * @Description: TODO 
  * @ClassName: SparkReadJsonFile
  * @Author: xqg
  * @Date: 2019/1/15 17:01
  *
  */
object SparkReadJsonFile {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[2]").setAppName("SparkReadJsonFile")
    val sc = new SparkContext(conf)
    val ssc = new SQLContext(sc)

    val reader: DataFrameReader = ssc.read.format("json")
    val jf: DataFrame = reader.load("F:\\IDEA_WorkSpace\\scala\\src\\TestData\\day13\\a.json")

    jf.show()

    /**
      * +---+-----+------+------+------+
      * |age|depId|gender|  name|salary|
      * +---+-----+------+------+------+
      * | 25|    1|  male|   Leo| 20000|
      * | 30|    2|female| Marry| 25000|
      * | 35|    1|  male|  Jack| 15000|
      * | 42|    3|  male|   Tom| 18000|
      * | 21|    3|female|Kattie| 21000|
      * | 30|    2|female|   Jen| 28000|
      * | 19|    2|female|   Jen|  8000|
      * | 19|    2|female|   Jen|  8000|
      * | 19|    2|female|   Jen|  8000|
      * +---+-----+------+------+------+
      */
    jf.printSchema()

    /**
      * root
      * |-- age: long (nullable = true)
      * |-- depId: long (nullable = true)
      * |-- gender: string (nullable = true)
      * |-- name: string (nullable = true)
      * |-- salary: long (nullable = true)
      */
    sc.stop()
  }
}
