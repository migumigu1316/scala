package day17

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  *
  * @ClassName: SparkStreamingWordCount
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/10/15 14:09
  *
  * Spark Streaming 入门 WordCount
  */
object SparkStreamingWordCount {
  //屏蔽不必要的日志显示在终端上
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
  Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)
  def main(args: Array[String]): Unit = {
    //TODO 当我们程序启动后,此时,必须要给Spark Streaming 这个程序最少分配两个进程
    val conf = new SparkConf()
      .setAppName("SparkStreamingWC")
      //TODO 最少两个线程,一个线程用于拉去数据,一个线程用于计算数据
      .setMaster("local[2]")

    //创建Spark Streaming的执行入口，也就是Streaming对象
    //设置时间的间隔，Duration 毫秒   Seconds 秒
    //设置完时间的间隔，也就是批次提交任务，叫batch
    val ssc = new StreamingContext(conf,Seconds(5))

    //TODO 测试方式用 ssc.socketTextStream(hostname,port)
    //首先，创建输入DStream，代表了一个数据源（比如，kafka，socket）
    //socketTextStream()方法两个参数,第一个是主机的ip,第二个是监听那个端口号
    val lines: ReceiverInputDStream[String] = ssc.socketTextStream(
      "192.168.198.10",9999)

    //到这里可以理解为DStream,每秒进行封装一次RDD,也就是这5秒的RDD
    //接下来开始收集数据,进行RDD操作,使用SparkCore算子,在DStream中完成

    val words: DStream[String] = lines.flatMap(_.split(" "))
    val tuples: DStream[(String, Int)] = words.map((_,1))
    val result: DStream[(String, Int)] = tuples.reduceByKey(_+_)

    //到这里实现了wc程序
    //思路,加深印象
    //每秒汇总发送到指定的socket端口上的饿数据,都会被DStream接收到
    //然后开始处理数据,一行一行处理
    //然后在调用一系列算子操作,直到最后一个WordCount RDD 作为输出
    //此时,就得到了每秒发送过来的单词统计的数据

    //最后打印
    result.print()

    //启动
    ssc.start()

    //等待停止
    ssc.awaitTermination()
  }
}
