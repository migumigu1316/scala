package day19;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @ClassName: RedisAPI02
 * @Description: TODO
 * @Author: xqg
 * @Date: 2018/10/17 11:19
 *
 * 连接池连接
 */
public class RedisAPI02 {
    public static void main(String []args){
        //创建JedisPool的连接池
        JedisPool jedisPool = new JedisPool("192.168.198.10", 6379);

        //通过Pool获取到jedis 实例
        Jedis jedis = jedisPool.getResource();

        //密码验证
        jedis.auth("123456");
        System.out.println(jedis.ping());

        jedis.set("a2","jedis");//存值
        System.out.println(jedis.get("a2"));//取值

        //关闭
        jedis.close();
    }
}
