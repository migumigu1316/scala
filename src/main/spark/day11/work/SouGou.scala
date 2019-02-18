package day11.work
import java.sql.{Connection, Date, DriverManager, SQLException}

import org.apache.log4j.{Level, Logger}
import org.apache.spark.storage.StorageLevel
import org.apache.spark.{Partitioner, SparkConf, SparkContext}

import scala.collection.mutable
/**
  *
  * @ClassName: SouGou
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/10/8 17:27
  *
  */
object SouGou {
  def jdbc(iter: Iterator[(String,Int)]) ={
    //创建jdbc的连接
    val conn:Connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mytest?characterEncoding=utf-8", "root", "123456")
    val pstmt =conn.prepareStatement("INSERT INTO Sogou(Srking,Ssum,date) VALUES(?,?,?)")
    try {
      iter.foreach(t => {
        pstmt.setString(1, t._1)
        pstmt.setInt(2, t._2)
        pstmt.setDate(3, new Date(System.currentTimeMillis()))
        val i = pstmt.executeUpdate()
        if (i > 0) println("添加成功！") else println("添加失败")
      })
    } catch {
      case e:SQLException => println(e.getMessage)
    } finally {
      if(pstmt != null){
        pstmt.close()
      }
      if(conn !=null){
        conn.close()
      }
    }

  }

  def binarySearch(lines: Array[String], ip: Long) : Int = {
    var low = 0
    var high = lines.length - 1
    while (low <= high) {
      val middle = (low + high) / 2
      if (ip == lines(middle).split("\\s")(0).toLong)
        return middle
      if (ip < lines(middle).split("\\s")(0).toLong)
        high = middle - 1
      else {
        low = middle + 1
      }
    }
    19
  }
  def main(args: Array[String]): Unit = {
    //屏蔽不必要的日志显示在终端上
    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
    Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)
    System.setProperty("hadoop.home.dir", "")
    val sc = new SparkContext(new SparkConf().setAppName("Sogou").setMaster("local"))
    val rdd=sc.textFile("E:\\HuoHu\\下载\\SogouQ.txt").map(t=>{
      val lines = t.split("\\s")
      lines(4)
    })
    /*//创建hdfs检查点hdfs文件夹
    sc.setCheckpointDir(args(1))
    //将计算结果保存到hdfs上
    rdd.persist(StorageLevel.MEMORY_ONLY_SER).checkpoint()*/

    val y=sc.textFile("E:\\HuoHu\\下载\\123.txt").collect()
    val lv = sc.broadcast(y)

    val rdd2=rdd.map(t=>{
      val i = binarySearch(lv.value,t.toLong)
      val str: String = lv.value(i)
      val s = str.split("\\s")(1)
      (s,1)
    })
    val sougouPartition = new SougouPartition
    val rdd3 = rdd2.reduceByKey(sougouPartition,_+_).sortBy(_._2,false)
    rdd3.foreachPartition(part=>{
      //传入一个分区过去，一个分区有多条数据，
      // 一个分区创建一个jdbc连接，写完这个数据再关jdbc连接
      jdbc(part)
    })
  }
}
class SougouPartition extends Partitioner{
  val map =Map("国际"->0,"社会"->1,"图片"->2,"视频"->3,"军事"->4,"评论"->5,
    "历史"->6,"文化"->7,"公益"->8,"旅游"->9,"新闻"->10,
    "体育"->11,"科技"->12,"汽车"->13,"国内"->14,"游戏"->15,
    "彩票"->16,"商城"->17,"地图"->18,"其他"->19)
  override def numPartitions: Int = map.size+1

  override def getPartition(key: Any): Int = {
    map.getOrElse(key.toString,0)
  }
}
