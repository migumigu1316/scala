package day17

import org.apache.log4j.{Level, Logger}
import org.apache.spark.{HashPartitioner, SparkConf}
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  *
  * @ClassName: UpdataStateByKey
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/10/15 16:22
  *
  * 单词计数累加,累加批次的结果
  */
object UpdataStateByKey {
  //屏蔽不必要的日志显示在终端上
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
  Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)

  /**
    * (Iterator[(K, Seq[V], Option[S])]) => Iterator[(K,S)]
    * 第一个参数:是我们从kafka获取到的元素,Key,String 类型
    * 第二个参数:是我们进行单词计数统计的value值，Int类型
    * 第三个参数，使我们每次批次提交的中间结果集
    */
  val updateFunc =(ite:Iterator[(String, Seq[Int], Option[Int])]) =>{
    ite.map(t=>{
      (t._1,t._2.sum + t._3.getOrElse(0))
    })
  }

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName("KafkaWordCount")
      .setMaster("local[2]")
    //创建Spark Streaming的执行入口
    val ssc = new StreamingContext(conf, Seconds(5))

    //设置检查点
    ssc.checkpoint("hdfs://hadoop01:9000//checkpoint3")
//    ssc.checkpoint("hdfs://hadoop01:9000/checkpoint2")

    //配置kafka的zk信息
    val zk ="192.168.198.10:2181,192.168.198.20:2181,192.168.198.30:2181"

    //kafka消费者组
    val groupID = "gp2"

    //kafka的 topics 的名字
    val topics: Map[String, Int] = Map("test1"->1)

    //创建kafka的输入数据流,来获取kafka中的数据
    val data: ReceiverInputDStream[(String, String)] = KafkaUtils.createStream(ssc,zk,groupID,topics)

    //处理数据,但是数据是(k,v)
    val lines: DStream[String] = data.flatMap(_._2.split(" "))
    val tuples: DStream[(String, Int)] = lines.map((_,1))

    //开始使用updateStateByKey,聚合操作,历史批次累加操作
    val value: DStream[(String, Int)] = tuples.updateStateByKey(
      updateFunc,
      new HashPartitioner(ssc.sparkContext.defaultParallelism),//指定分区数
      true)

    value.print()

    //启动
    ssc.start()
    //等待停止
    ssc.awaitTermination()
  }
}
