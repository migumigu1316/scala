package day20.work

import kafka.common.TopicAndPartition
import kafka.message.MessageAndMetadata
import kafka.serializer.StringDecoder
import kafka.utils.{ZKGroupTopicDirs, ZkUtils}
import org.I0Itec.zkclient.ZkClient
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.dstream.InputDStream
import org.apache.spark.streaming.kafka.{HasOffsetRanges, KafkaUtils, OffsetRange}
import redis.clients.jedis.Jedis

/**
  *
  * @ClassName: homework
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/10/17 17:18
  *
  */
object DirectKafka2Jedis {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName("Direct")
      .setMaster("local[2]")
    val ssc = new StreamingContext(conf, Seconds(5))

    //创建消费者组
    val groupID = "g02"

    //指定topic名字
    val topic = "work"

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
        //TODO 调用JedisConnectionPool连接池中的getConnection()
       val jedis: Jedis = JedisConnectionPool.getConnection()
        f.foreach(x => {
          println("-----------写业务的位置-------------")
          /**
            * 该文件是一个电商网站某一天用户购买商品的订单成交数据，每一行有多个字段，用空格分割，字段的含义如下
            * 用户ID   ip地址          商品分类   购买明细     商品金额
            * A        202.106.196.115 手机       iPhone8      8000
            *
            * 问题1.计算出总的成交量总额（结果保存到redis中）
            * 问题2.计算每个商品分类的成交量的top3（结果保存到redis中）
            * 问题3.计算每个商品分类的成交总额，并按照从高到低排序（结果保存到redis中）
            */
          //处理传输过来的数据
          val lines: Array[String] = x.split(" ")
          //问题1.计算出总的成交量总额（结果保存到redis中）
          JedisUtils.jedisCount(lines,jedis)
//         val price: Double = lines(4).toDouble
//          jedis.incrByFloat("count",price)
//          println(jedis.get("count"))

          //问题2.计算每个商品分类的成交量的top3（结果保存到redis中)
          JedisUtils.jedisCountTopN(lines,jedis)

          //问题3.计算每个商品分类的成交总额，并按照从高到低排序（结果保存到redis中）

        })
        //释放连接
       // jedis.close()
      })

      for (o <- offsetRanges) {
        //  /g01/offsets/test1/0
        val zkPath = s"${topicDirs.consumerOffsetDir}/${o.partition}"

        //将该partition的of分桶保存到zookeeper
        ZkUtils.updatePersistentPath(zkClient, zkPath, o.untilOffset.toString)
      }
    })

    //启动
    ssc.start()
    //等待
    ssc.awaitTermination()
  }
}
