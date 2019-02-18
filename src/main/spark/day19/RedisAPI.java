package day19;

import redis.clients.jedis.Jedis;

/**
 * @ClassName: RedisAPI
 * @Description: TODO
 * @Author: xqg
 * @Date: 2018/10/17 11:09
 *
 * 单机连接Redis
 */
public class RedisAPI {
    public static void main(String []args){
        //创建Jedis
        //host: Redis 数据库的IP连接
        //port: Redis 数据库的端口号
        //注意: 需要更改redis里面.conf的配置文件信息,将bind 的 IP 地址写成自己的主机IP
        Jedis jedis = new Jedis("192.168.198.10", 6379);
        //密码验证,Redis中设置了密码就需要密码验证
        jedis.auth("123456");

        //清空数据库中的数据
        jedis.flushDB();
         System.out.println(jedis.ping());

         jedis.set("a1","jedis test");
         System.out.println(jedis.get("a1"));

         //关闭Redis连接
        jedis.close();
    }
}
