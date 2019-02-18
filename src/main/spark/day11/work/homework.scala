package day11.work

import java.sql.{Connection, DriverManager, PreparedStatement, SQLException}

import org.apache.log4j.{Level, Logger}
import org.apache.spark.broadcast.Broadcast
import org.apache.spark.rdd.RDD
import org.apache.spark.{Partitioner, SparkConf, SparkContext}

/**
  *
  * @ClassName: homework
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/30 15:00
  * 1.需要将两个文件进行读取，将小文件进行广播。
  * 2.用自定义分区方式，实现分区，设置检查点，倒序排序，统计出一天的点击排行量
  * 3.将最后的数据结果存入MySQL和HDFS
  */
object homework {
  def main(args: Array[String]): Unit = {
    //屏蔽不必要的日志显示在终端上
    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
    Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)

    val conf: SparkConf = new SparkConf().setAppName("homework").setMaster("local")
    val sc = new SparkContext(conf)

    //创建检查点机制
    //    sc.setCheckpointDir("hdfs://hadoop01:9000/checkpoint_SougoQ")

    //数据格式
    //00:00:00	2982199073774412	[360安全卫士] 8 3	download.it.com.cn/softweb/software/firewall/antivirus/20067/17938.html
    val file: RDD[String] = sc.textFile(
      "F:\\IDEA_WorkSpace\\scala\\src\\TestData\\SogouQ.txt")

    //测试数据
//    val file: RDD[String] = sc.textFile(
//      "G:\\大数据课程(代码+资料)\\spark_scala\\day11\\work\\new 1.txt")
    if (file != null) {
      //用自定义分区方式，实现分区，设置检查点，倒序排序，统计出一天的点击排行量
      val tuples: RDD[(String, Int)] = file.map(t => {
        val files: Array[String] = t.split("\t")
        //两标签的中间不是\t而是" "
        val label: String = files(3)
        val ls: Array[String] = label.split(" ")
        val str1: String = ls(0)
        (str1, 1)
      })
//      tuples.foreach(println)

      //开始使用分区器
      val pID = new partitionID

//      val result: RDD[(String, Int)] = tuples.reduceByKey(_ + _)
      val resulted: RDD[(String, Int)] = tuples.reduceByKey(pID, _ + _)

      //排序
      val sorted: RDD[(String, Int)] = resulted.sortBy(_._2, false)
      sorted.foreach(println)

      //获取id的数据
      val id: RDD[String] = sc.textFile(
        "F:\\IDEA_WorkSpace\\scala\\src\\TestData\\ID.txt")
      //广播文件
      val broad: Broadcast[RDD[String]] = sc.broadcast(id)
      val IDTuples: RDD[(String, String)] = broad.value.map(t => {
        val words: Array[String] = t.split(" ")
        (words(0), words(1))
      })

      val joined: RDD[(String, (Int, String))] = sorted.join(IDTuples)
      val jin: RDD[(String, Int)] = joined.map(t => {
        (t._2._2, t._2._1)
      })
      val res: RDD[(String, Int)] = jin.sortBy(_._2, false)

//      res.cache().checkpoint()
      res.foreach(println)
      //模拟hdfs存储到本地
      //      res.saveAsTextFile("F:\\IDEA_WorkSpace\\scala\\src\\TestData\\work")
      //存储到hdfs上
//      res.saveAsTextFile("hdfs://hadoop01:9000/partitions")

//     val connect = new Connected
//      res.foreachPartition(f => connect.getConnection(f))
      res.foreachPartition(f => getConnection(f))//连接数据库,数据传入库中

      sc.stop()

    } else {
      println("is null")
    }
  }
  //创建JDBC连接
  def getConnection(ite: Iterator[(String, Int)]): Unit = {
    val conn: Connection = DriverManager.getConnection(
      "jdbc:mysql://localhost:3306/mytest?characterEncoding=utf-8", "root", "123456")
    val pstmt: PreparedStatement = conn.prepareStatement(
      "INSERT INTO day11work(type,sum) VALUES(?,?)")

    //写入数据
    try {
      ite.foreach(rdd => {
        pstmt.setString(1, rdd._1)
        pstmt.setInt(2, rdd._2)
        val i = pstmt.executeUpdate()//更新
        if (i > 0) println("添加成功！") else println("添加失败")
      })
    } catch {
      case e: SQLException => println(e.getMessage)
    } finally {
      if (pstmt != null) {
        pstmt.close()
      }
      if (pstmt != null) {
        conn.close()
      }
    }
  }
}

class partitionID extends Partitioner with Serializable/*序列化*/ {

  val map = Map("国际" -> 1, "社会" -> 2, "图片" -> 3, "视频" -> 4, "军事" -> 5,
    "评论" -> 6, "历史" -> 7, "文化" -> 8, "公益" -> 9, "旅游" -> 10,
    "新闻" -> 11, "体育" -> 12, "科技" -> 13, "汽车" -> 14, "国内" -> 15,
    "游戏" -> 16, "彩票" -> 17, "商城" -> 18, "地图" -> 19, "其他" -> 20)

  override def numPartitions: Int = {
    map.size + 1
  }

  override def getPartition(key: Any): Int = {
    map.getOrElse(key.toString, 20) //如果没有指定的分区,把数据放到20分区中
  }
}
