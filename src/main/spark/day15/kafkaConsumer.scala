package day15

import java.util.Properties
import java.util.concurrent.Executors

import kafka.consumer.{Consumer, ConsumerConfig, ConsumerConnector, KafkaStream}

import scala.collection.mutable
/**
  *
  * @ClassName: kafkaConsumer
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/10/13 10:23
  *
  */

class kafkaConsumer(val consumer: String, val stream: KafkaStream[Array[Byte],
  Array[Byte]])
  extends Runnable{
  override def run() = {
    val it = stream.iterator()
    while (it.hasNext()) {
      val data = it.next()
      val topic = data.topic
      val offset = data.offset
      val partition = data.partition
      val msg = new String(data.message())
      println(s"Consumer: $consumer, topic: $topic, " +
        s"offset: $offset, partition: $partition, msg: $msg")
    }
  }
}
object kafkaConsumer {
  def main(args: Array[String]): Unit = {
    // 读取topic的名称
    val topic = "test"

    // 定义一个map，用于存储多个topic
    val topics = new mutable.HashMap[String, Int]()
    topics.put(topic, 2)

    // 创建配置类并封装配置信息
    val props = new Properties()
    // consumer group
    props.put("group.id", "group01")
    // 指定zookeeper列表，用于获取offset
    props.put("zookeeper.connect", "192.168.198.10:2181,192.168.198.20:2181,192.168.198.30:2181")
    // 当zookeeper没有offset的时候，指定offset
    props.put("auto.offset.reset", "smallest")

    // 封装配置信息
    val config: ConsumerConfig = new ConsumerConfig(props)

    // 创建consumer实例
    val consumer: ConsumerConnector = Consumer.create(config)

    // 获取数据, map里的key是topic的名称
    val streams: collection.Map[String, List[KafkaStream[Array[Byte], Array[Byte]]]] =
      consumer.createMessageStreams(topics)

    // 获取指定topic的数据
    val stream: Option[List[KafkaStream[Array[Byte], Array[Byte]]]] = streams.get(topic)

    // 创建固定数量的线程池
    val pool = Executors.newFixedThreadPool(3)

    for (i <- 0 until stream.size) {
      pool.execute(new kafkaConsumer(s"Consumer: $i", stream.get(i)))
    }

  }
}
