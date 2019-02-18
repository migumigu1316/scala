package day14

import org.apache.spark.broadcast.Broadcast
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.types.{LongType, StringType, StructField, StructType}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{DataFrame, Row, SQLContext}

import scala.collection.mutable.ListBuffer

/**
  *
  * @ClassName: DailyTop3
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/10/10 11:13
  *
  */
/**
  * 需求:
  * 每天每个搜索词用户访问量top3
  *
  * 需求分析:
  * 1>针对原始数据,获得输入的RDD
  * 2>使用filter算子,去针对RDD输入的数据,进行数据过滤
  * 3>将数据转换"日期_搜索词,用户格式"对其进行分组,对每天每个搜索词进行去重操作
  * 4>并统计去重后的数据,即每天每个搜索词的UV
  * 5>最后获取到的格式"日期_搜索词,UV"
  */
object DailyTop3 {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("DailyTop3").setMaster("local")
    val sc = new SparkContext(conf)
    val ssc = new SQLContext(sc)
    //导入隐士转换
    import ssc.implicits._

    //TODO 前两步 下面的内容已经实现

    //需要过滤到的数据,也就是查询条件,一般在实际情况中通过mysql关系数据库查询
    val maps = Map(
      "city" -> List("beijing"),//地址
      "platform" -> List("android"),//平台
      "version" -> List("1.0", "1.1", "1.2", "1.3")//版本号
    )

    //将查询条件分装为一个broadcast变量
    //根据实现的思路分析,广播出去是最好的方式,这样可以进行优化,每个worker节点就有一份数据.
    val broadcastMap: Broadcast[Map[String, List[String]]] = sc.broadcast(maps)

    //开始读取数据
    val LogRDD: RDD[String] = sc.textFile(
      "F:\\IDEA_WorkSpace\\scala\\src\\TestData\\day13\\searchLog.txt")

    //通过广播变量筛选符号条件的数据
    val filterLog: RDD[String] = LogRDD.filter(log => {
      //与广播里的查询条件进行对比,只要改条件设置了,且日志中的数据没有满足条件
      //那么直接false,过滤该日志
      //否则返回true,保留该日志信息
      val broadcastValue: Map[String, List[String]] = broadcastMap.value

      //第一个get获取Map中的值,第二个get获取相应值的List中的值
      val city: List[String] = broadcastValue.get("city").get
      val platform: List[String] = broadcastValue.get("platform").get
      val version: List[String] = broadcastValue.get("version").get

      //切分原始日志,获取城市,平台,版本
      val lines: Array[String] = log.split(",")

      val citys: String = lines(3)
      val platforms: String = lines(4)
      val versions: String = lines(5)

      //定义flag为可变的
      var flag = true

      //如果数据进来之后,进行对比,当相同的时候就会走false,就保留下来
      //当不相同的时候,走false
      if (!city.contains(citys)) {
        flag = false //或者 flag = !flag
      }

      if (!platform.contains(platforms)) {
        flag = false //或者 flag = !flag
      }

      if (!version.contains(versions)) {
        flag = false //或者 flag = !flag
      }
      flag//将flag值返回出去
    })

    //TODO 接下来实现第三步需求
//    3>将数据转换"日期_搜索词,用户格式"对其进行分组,对每天每个搜索词进行去重

    //将过滤出来的日志映射成"日期_搜索词,用户"
    val dateWordRDD: RDD[(String, String)] = filterLog.map(t => {
      (t.split(",")(0) + "_" + t.split(",")(2), t.split(",")(1))
    })
//    dateWordRDD.foreach(println)
    /**
        (2017-03-13_barbecue,leo)
        (2017-03-13_barbecue,leo)
        (2017-03-13_barbecue,leo)
        (2017-03-13_cloth,leo)
        (2017-03-13_cloth,leo2)
        (2017-03-13_barbecue,tom)
        (2017-03-13_cup,leo)
        (2017-03-13_barbecue,mary)
        (2017-03-13_cup,leo)
        (2017-03-13_cup,leo1)
        (2017-03-13_cup,leo2)
        (2017-03-13_cup,leo3)
        (2017-03-13_cup,leo4)
      */


    //进行分组,获取每天每个搜索词,有哪些用户搜索了
    val dateWordGroupRDD: RDD[(String, Iterable[String])] = dateWordRDD.groupByKey()
//    dateWordGroupRDD.foreach(println)
    /**
        (2017-03-13_cloth,CompactBuffer(leo, leo2))
        (2017-03-13_cup,CompactBuffer(leo, leo, leo1, leo2, leo3, leo4))
        (2017-03-13_barbecue,CompactBuffer(leo, leo, leo, tom, mary))
    */

      //TODO 第四,五步实现
//    4>并统计去重后的数据,即每天每个搜索词的UV
//    5>最后获取到的格式"日期_搜索词,UV"
      //接下来对每天每个搜索词的搜索用户进行去重，获得到UV，返回的格式 “日期_搜索词，UV”
      //如果我们用distinct进行去重的话，那么将会发生shuffle操作，那么为了 提高性能
      //使用map操作
    val dateWordUVRDD: RDD[(String, Int)] = dateWordGroupRDD.map(f = t => {
        //获取到时间
        val dateWord: String = t._1

        //获取分组后的所有用户
        val users: Iterator[String] = t._2.iterator

        //创建一个集合,进行去重
        val list = new ListBuffer[String]
        while (users.hasNext) {
          val user = users.next()
          //第一个用户进来，进行判断，如果存在不添加，不存在添加
          if (!list.contains(user)) {
            list.append(user)
          }
        }
        //获取uv的访问量
        val uv = list.size
        //返回 "日期_搜索词,UV"
        (dateWord, uv)
      })

    //TODO 第六步 需求实现
    //将每天每个搜索词的UC数据,转换成DF
    val dateWordUVRowRDD: RDD[Row] = dateWordUVRDD.map(row => {
      Row(row._1.split("_")(0), row._1.split("_")(1), row._2.toLong)
    })

    //创建structType
    val structType = StructType(Array(
      StructField("date", StringType, true),
      StructField("keyword", StringType, true),
      StructField("uv", LongType, true)
    ))

    //创建DF
    val df = ssc createDataFrame(dateWordUVRowRDD, structType)

    //注册临时表
    df.registerTempTable("date_uv")

    //利用spark sql 窗口函数,统计每天搜索UV排名前三的搜索词
    val df1: DataFrame = ssc.sql("select * from (select date,keyword,uv,row_number() " +
      "over(partition by date order by uv desc) r from date_uv) t where r < 3")
    df1.show()
    /**
      * +----------+--------+---+----+
      * |      date| keyword| uv|   r|
      * +----------+--------+---+----+
      * |2017-03-13|     cup|  5|   1|
      * |2017-03-13|barbecue|  3|   2|
      * +----------+--------+---+----+
      */

    sc.stop()
  }
}
