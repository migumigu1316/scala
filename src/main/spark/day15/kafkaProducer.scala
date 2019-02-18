package day15

import java.util.Properties
import kafka.producer.{KeyedMessage, Producer, ProducerConfig}

/**
  *
  * @ClassName: kafkaProducer
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/10/13 10:23
  * 实现一个生产者
  *  能够产生数据并把数据实时的发送到kafka的某个topic中
  *  实现自定义分区器
  */
object kafkaProducer {
  def main(args: Array[String]): Unit = {
    // 定义一个topic
    val topic = "test"

    // 创建一个配置类
    val props = new Properties();
    // 指定序列化器
    props.put("serializer.class", "kafka.serializer.StringEncoder")
    // 指定kafka集群列表
    props.put("metadata.broker.list", "192.168.198.10:9092,192.168.198.20:9092,192.168.198.30:9092")
    // 设置发送数据后的响应方式 0，1，-1
    props.put("request.required.acks", "1")
    // 调用分区器
    //    props.put("partitioner.class", "kafka.producer.DefaultPartitioner");
    props.put("partitioner.class", "day15.CustomPartitioner")

    // 把配置信息封装到ProducerConfig中
    val config = new ProducerConfig(props)

    // 创建Producer实例
    val producer: Producer[String, String] = new Producer(config)

    // 模拟生产数据
    for(i <- 1 to 10000) {
      val msg = s"$i: Producer send data"
      producer.send(new KeyedMessage[String, String](topic, msg))
      Thread.sleep(500)
    }
  }
}
