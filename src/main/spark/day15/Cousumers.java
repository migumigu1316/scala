package day15;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @ClassName: Cousumers
 * @Description: TODO
 * @Author: xqg
 * @Date: 2018/10/11 16:39
 *
 * kafka Consumer(消费者) API操作
 */
public class Cousumers {
    private static final  String topic = "test";
    private static final Integer threads = 2;

    public static void main(String []args){
        //配置相应的属性
        Properties prop = new Properties();

        //配置zookeeper信息
        prop.put("zookeeper.connect",
                "192.168.198.10:2181,192.168.198.20:2181,192.168.198.30:2181");

        //这里从头开始读取数据
        prop.put("auto.offset.reset","smallest");

        //配置消费者组
        prop.put("group.id","vvv");

        //调用我们配置好的API信息,创建消费者对象
        ConsumerConfig config = new ConsumerConfig(prop);

        //创建消费者
        ConsumerConnector consumer = Consumer.createJavaConsumerConnector(config);

        //创建Map,主要用来存储多个topic信息
        HashMap<String,Integer> topicMap = new HashMap<>();
        topicMap.put(topic,threads);

        //创建获取信息流
        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap =
                consumer.createMessageStreams(topicMap);
        List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(topic);

        //循环呢接收map内的topic数据
        for(KafkaStream<byte[], byte[]> kafkaStream:streams ){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (MessageAndMetadata<byte[],byte[]> m:kafkaStream){
                        String msg = new String(m.message());
                         System.out.println(msg);
                    }
                }
            }).start();
        }


    }
}
