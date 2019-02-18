package day20.hashDemo;
import redis.clients.jedis.Jedis;

import java.util.Map;
/**
 * @ClassName: BuyCart
 * @Description: TODO
 * @Author: xqg
 * @Date: 2018/10/19 19:33
 */

public class BuyCart {
    private static Jedis jedis = new Jedis("127.0.0.1", 6379);

    // 添加商品
    public static void addProductCart() {
        jedis.hset("cart:user001", "T恤", "2");
        jedis.hset("cart:user002", "手机", "5");
        jedis.hset("cart:user002", "电脑", "1");
        jedis.close();
    }

    // 查询购物车信息
    public static void getProductInfo() {
        String pForUser001 = jedis.hget("cart:user001", "T恤");
        System.out.println(pForUser001);

        Map<String, String> pForUser002 = jedis.hgetAll("cart:user002");
        for (Map.Entry<String, String> entry : pForUser002.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }

        jedis.close();
    }

    // 修改购物车信息
    public static void editProductInfo() {
        jedis.hset("cart:user001", "T恤", "1");
        jedis.hincrBy("cart:user002", "电脑", 2);

        jedis.close();
    }


    public static void main(String[] args) {
//        addProductCart();
//        getProductInfo();
//        editProductInfo();
    }


}