package day15;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.Properties;

/**
 * @ClassName: Producers
 * @Description: TODO
 * @Author: xqg
 * @Date: 2018/10/11 16:30
 *
 * kafka Producers(生产者) API操作
 */
public class Producers {
    public static void main(String []args){
        //通过properties类进行配置文件的配置
        Properties prop = new Properties();
        //配置执行的端口号和IP
        prop.put("metadata.broker.list",
                "192.168.198.10:9092,192.168.198.20:9092,192.168.198.30:9092");
        //进行序列化
        prop.put("serializer.class","kafka.serializer.StringEncoder");

        //定义一个
        ProducerConfig config = new ProducerConfig(prop);

        //创建producer(生产者)
        Producer<String,String> producer = new Producer<>(config);

        int i=0;
        while (true){
            //发送消息
            producer.send(new KeyedMessage<String,String>("test","msg"+i));
            i++;
        }

    }
}
