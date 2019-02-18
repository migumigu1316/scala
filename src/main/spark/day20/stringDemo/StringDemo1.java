package day20.stringDemo;

import redis.clients.jedis.Jedis;

public class StringDemo1 {
    public static void main(String[] args) {
        // 创建一个Jedis连接
        Jedis jedis = new Jedis("node02", 6379);

//        String pingAck = jedis.ping();
//        System.out.println(pingAck);

        String setAck = jedis.set("s1", "111");
        System.out.println(setAck);

        String getAck = jedis.get("s1");
        System.out.println(getAck);


        jedis.close();
    }
}
