package day20.stringDemo;

import com.google.gson.Gson;
import redis.clients.jedis.Jedis;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class StringDemo2 {
    private static Jedis jedis = new Jedis("node02", 6379);

    public static void main(String[] args) throws Exception {
//        StringTest();
//        objectTest();

        objectToJsonTest();

    }

    /**
     * 将字符串缓存到String数据结构中
     */
    public static void StringTest(){
        jedis.set("user:001:name", "xiaofang");
        jedis.mset("user:002:name", "xiaofen", "user:003:name", "yaoyao");

        String uname001 = jedis.get("user:001:name");
        String uname002 = jedis.get("user:002:name");
        String uname003 = jedis.get("user:003:name");

        System.out.println(uname001);
        System.out.println(uname002);
        System.out.println(uname003);
    }

    /**
     * 将对象缓存到String数据结构中
     */
    public static void objectTest() throws Exception {
        ProductInfo p = new ProductInfo();
        p.setName("Iphone8plus");
        p.setPrice(7999.0);
        p.setProcuctDesc("看视频");

        // 将对象序列化为byte数组
        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(ba);
        // 用对象序列化的方式将ProduceInfo序列化写入流中
        oos.writeObject(p);
        // 将ba流转换为字节数组
        byte[] pBytes = ba.toByteArray();

        // 将序列化好的数据缓存到redis中
        jedis.set("product:001".getBytes(), pBytes);


        // 读取刚刚缓存的数据
        byte[] pBytesRes = jedis.get("product:001".getBytes());
        // 反序列化
        ByteArrayInputStream bi = new ByteArrayInputStream(pBytesRes);
        ObjectInputStream oi = new ObjectInputStream(bi);
        ProductInfo pRes = (ProductInfo) oi.readObject();

        System.out.println(pRes);
    }

    /**
     * 将对象转换为json字符串缓存到redis
     */
    public static void objectToJsonTest() {
        ProductInfo p = new ProductInfo();
        p.setName("Iphone4");
        p.setPrice(4888.0);
        p.setProcuctDesc("用来起啤酒盖儿");

        // 将对象转换为json格式
        Gson gson = new Gson();
        String jsonProductInfo = gson.toJson(p);

        // 缓存到redis
        jedis.set("product:002", jsonProductInfo);


        // 获取数据
        String jsonRes = jedis.get("product:002");

        // 将json字符串转换为对象
        ProductInfo produceInfo = gson.fromJson(jsonRes, ProductInfo.class);

        System.out.println(jsonRes);
        System.out.println(produceInfo);
    }







}
