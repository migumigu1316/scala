package day17

import org.apache.log4j.{Level, Logger}
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf}

/**
  *
  * @ClassName: KafkaWordCount
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/10/15 15:04
  *
  * KafkaWordCount 实现, Receiver
  */
object KafkaWordCount {
  //屏蔽不必要的日志显示在终端上
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
  Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName("KafkaWordCount")
      .setMaster("local[2]")
    //创建Spark Streaming的执行入口
    val ssc = new StreamingContext(conf,Seconds(5))

    //接下来编写kafka的配置信息
    //首先是kafka的依赖zk关系
    val zk = "192.168.198.10:2181,192.168.198.20:2181,192.168.198.30:2181"

    //给一个kafka消费者组
    val groupID = "gp1"

    //kafka的topic名字,第一个参数是topic名字,第二个参数是线程数
    val topics = Map[String,Int]("test1"->1)

    //创建kafka的输入输出流,来获取kafka中的数据
    //KafkaUtils.createStream()
    val data: ReceiverInputDStream[(String, String)] =
        KafkaUtils.createStream(ssc,zk,groupID,topics)

    //获取到的数据是键值对数据格式,(key,value)
    //接下来就开始处理kafka中的数据
    val lines: DStream[String] = data.flatMap(_._2.split(" "))
    val words: DStream[(String, Int)] = lines.map((_,1))
    val result: DStream[(String, Int)] = words.reduceByKey(_+_)

    //最后打印
    result.print()

    //启动
    ssc.start()

    //等待停止
    ssc.awaitTermination()
  }
}
