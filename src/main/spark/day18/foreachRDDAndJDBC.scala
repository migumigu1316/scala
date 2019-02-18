package day18

import java.sql.{Connection, Statement}

import org.apache.spark.{HashPartitioner, SparkConf}
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  *
  * @ClassName: foreachRDDAndJDBC
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/10/16 14:12
  *
  * 案例:案例：改写UpdateStateByKeyWordCount，
  * 将每次统计出来的全局的单词计数，写入一份，到MySQL数据库中
  */
object foreachRDDAndJDBC {
  /**
    * (Iterator[(K, Seq[V], Option[S])]) => Iterator[(K,S)]
    * 第一个参数:是我们从kafka获取到的元素,Key,String 类型
    * 第二个参数:是我们进行单词计数统计的value值，Int类型
    * 第三个参数，使我们每次批次提交的中间结果集
    */
  val updatafunc = (ite:Iterator[(String ,Seq[Int],Option[Int])])=>{
    ite.map(t=>{
      (t._1,t._2.sum + t._3.getOrElse(0))
    })
  }
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName("foreachRDDAndJDBC")
      .setMaster("local[2]")
    val ssc = new StreamingContext(conf,Seconds(5))

    //检查点
//    ssc.checkpoint("hdfs://hadoop01:9000/checkpoint4")
    ssc.checkpoint("f://checkpoint4")

    //配置kafka需要的参数
    val zk = "192.168.198.10:2181,192.168.198.20:2181,192.168.198.30:2181"

    //组ID
    val groupID = "gp1"

    //topic
    val topics = Map("test1"->1)

    //创建kafka的输入数据流,来获取数据
    val data: ReceiverInputDStream[(String, String)] = KafkaUtils.createStream(ssc,zk,groupID,topics)

    //开始处理数据
    //处理数据,但是数据是(k,v),对value进行处理.flatMap(_._2.split(" ")
    val lines: DStream[String] = data.flatMap(_._2.split(" "))
    val words: DStream[(String, Int)] = lines.map((_,1))
    val value: DStream[(String, Int)] = words.updateStateByKey(
      updatafunc,
      new HashPartitioner(ssc.sparkContext.defaultParallelism),
      true
    )

    //开始调用foreachRDD
    value.foreachRDD(f=>{
      //然后如果我们用到connection连接,那么必须得用foreachPartition
      f.foreachPartition(fp=>{
        //获取jdbc连接
        val conn: Connection = ConnectionPool.getConnection
        fp.foreach(tuple=>{
          val sql = "insert into sparkWC(word,count) values('"+tuple._1+"',"+tuple._2+")"
          val statement: Statement = conn.createStatement()
          statement.executeLargeUpdate(sql)
        })

        //释放连接处
        ConnectionPool.returnConnection(conn)
      })
    })

    //启动
    ssc.start()
    ssc.awaitTermination()
  }
}
