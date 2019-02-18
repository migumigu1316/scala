package day18

import kafka.common.TopicAndPartition
import kafka.message.MessageAndMetadata
import kafka.serializer.StringDecoder
import kafka.utils.{ZKGroupTopicDirs, ZkUtils}
import org.I0Itec.zkclient.ZkClient
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, InputDStream}
import org.apache.spark.streaming.kafka.{HasOffsetRanges, KafkaUtils, OffsetRange}
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  *
  * @ClassName: KafkaDirectWordCount
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/10/16 15:20
  *
  *        直连方式(常用)
  */

//TODO 直连方式(常用)
object KafkaDirectWordCount {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName("Direct")
      .setMaster("local[2]")
    val ssc = new StreamingContext(conf, Seconds(5))

    //创建消费者组
    val groupID = "g01"

    //指定topic名字
    val topic = "test1"

    //指定kafka的broker地址
    // sparkStreaming的Task直接连到kafka分区,用底层的API消费,效果更高
    val brokerList = "192.168.198.10:9092,192.168.198.20:9092,192.168.198.30:9092"

    //指定zk的地址,后期更新消费的偏移量时使用(以后可以使用Redis,mysql来记录偏移量)
    val zk = "192.168.198.10:2181,192.168.198.20:2181,192.168.198.30:2181"

    //创建topic集合,如果以后多,可以创建多个topic
    val topics = Set(topic)

    //创建一个ZKGroupTopicDirs对象,其实是指定往zk中写入数据的目录,用于保存偏移量
    val topicDirs = new ZKGroupTopicDirs(groupID, topic)

    //获取zookeeper中的路径"g01/offsets/test1"
    val zkTopicPath = s"${topicDirs.consumerOffsetDir}"

    //准备kafka参数
    val kafkas = Map(
      "metadata.broker.list" -> brokerList,
      "group.id" -> groupID,
      //从头开始读数据
      "auto.offset.reset" -> kafka.api.OffsetRequest.SmallestTimeString
    )

    //zookeeper的host和ip创建一个client用于更新偏移量
    //zk的客户端,可以从zk中读取偏移量,并更新[偏移量
    val zkClient = new ZkClient(zk)

    //查询该路径下的分区数
    val clientPartition: Int = zkClient.countChildren(zkTopicPath) //   /g01/offsets/test1

    //创建一个kafka
    var kafkaStream: InputDStream[(String, String)] = null

    //如果zookeeper中保存offset,我们会利用这个offset作为kafkaStream的起始位置
    //TopicAndPartition [/g01/offsets/test1/1/000001]
    var fromOffset: Map[TopicAndPartition, Long] = Map()

    //如果保存过offset
    if (clientPartition > 0) {
      //clientPartition 的数量其实就是/g01/offsets/test1的分区数目
      for (i <- 0 until clientPartition) {
        //  /g01/offsets/test1/0/   123456
        val partitionOffset = zkClient.readData[String](s"$zkTopicPath/${i}")
        val tp = TopicAndPartition(topic, i)

        //将不同的partition对应的offset增加到 fromOffset中
        fromOffset += (tp -> partitionOffset.toLong)

        //Key:kafka的Key   value:"hello hello hello"
        //这个会将kafka的消息进行 transform,最终 kafka的数据都会变成(kafka的Key , message)
        //这样的Tuple
        val messageHandler = (mmd: MessageAndMetadata[String, String]) => (
          mmd.key(), mmd.message()
        )

        //通过KafkaUtils 创建直连的DStream(fromOffset参数：按照前面计算好的偏移量继续消费数据)
        kafkaStream = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder, (String, String)](
          ssc, kafkas, fromOffset, messageHandler)
      }
    } else {
      //如果没保存，根据Kafka的配置使用最新或者最旧的Offset
      kafkaStream = KafkaUtils.createDirectStream[String, String, StringDecoder,
        StringDecoder](ssc, kafkas, topics)
    }

    //偏移量的范围
    var offsetRanges: Array[OffsetRange] = Array[OffsetRange]()

    //从kafka读取的数据,用transform方法可以批次提交
    //该transform方法计算获取到当前批次RDD,然后将RDD的偏移量取出来,然后将RDD返回DStream
    //RDD的类型是 kafkaRDD
    //TODO 注释掉下面这几行,从上次偏移量开始读取数据
//    val transform: DStream[(String, String)] = kafkaStream.transform(rdd => {
//      //得到该RDD对应的kafka的消息的offset
//      //该RDD是一个kafkaRDD,可以获得偏移量的范围
//      offsetRanges = rdd.asInstanceOf[HasOffsetRanges].offsetRanges
//      rdd
//    })
//
//    //取到需要的数据
//    val messages: DStream[String] = transform.map(_._2)
    //TODO

    //依次迭代DStream中的RDD
    kafkaStream.foreachRDD(rdd => {
      offsetRanges = rdd.asInstanceOf[HasOffsetRanges].offsetRanges
      //对RDD操作,最后触发Action
      rdd.map(_._2).foreachPartition(f => {
        f.foreach(x => {
          println(x + "-----------写业务的位置-------------")
        })
      })

      for (o <- offsetRanges) {
        //  /g01/offsets/test1/0
        val zkPath = s"${topicDirs.consumerOffsetDir}/${o.partition}"

        //将该partition的of分桶保存到zookeeper
        ZkUtils.updatePersistentPath(zkClient, zkPath, o.untilOffset.toString)
      }
    })

    ssc.start()
    ssc.awaitTermination()
  }
}