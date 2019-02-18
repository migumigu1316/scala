package day19;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * @ClassName: JedisAPI
 * @Description: TODO
 * @Author: xqg
 * @Date: 2018/10/17 11:37
 *
 * Jedis 客户端的基本练习操作
 */
public class JedisAPI {
    public static void main(String[] args) {
        //创建Jedis连接
        Jedis jedis = new Jedis("192.168.198.10", 6379);

        //密码验证
        jedis.auth("123456");
         System.out.println(jedis.ping());

         //清空数据库
        jedis.flushDB();

        System.out.println("-------------下面进入String字符串操作-----------------");

        /**
         * String类型(字符串)
         */
        jedis.set("bigdata","db");
        System.out.println(jedis.get("bigdata"));//db

        //在原来的key对应的value值进行追加字符串
        //如果key不存在,则创建一个新的key
        jedis.append("bigdata","test");
        System.out.println(jedis.get("bigdata"));//dbtest

        //TODO 自增自减性:

        //自增进行累加操作,等价于count++ 默认值是0
        jedis.incr("count");
        System.out.println(jedis.get("count"));//1

        //在原有的值基础上,增加指定的值,等价于count += 10
        jedis.incrBy("count",10);
        System.out.println(jedis.get("count"));//11

        //进行浮点类型自增  注意:Redis没有Double类型
        jedis.incrByFloat("double",1.5);
        System.out.println(jedis.get("double"));//1.5

        //自减    等价于 count--
        jedis.decr("count");
        System.out.println(jedis.get("count"));//10

        //指定减少的值, count -= 1
        jedis.decrBy("count",1);
        System.out.println(jedis.get("count"));//9

        //根据key更改key的值
        jedis.getSet("count","100");
        System.out.println(jedis.get("count"));//100

        //获取指定范围内的字符串   取出的值是[包头包尾]
        System.out.println(jedis.getrange("bigdata", 0, 3));//dbte

        //替换
        jedis.setrange("bigdata", 0, "php");
        System.out.println(jedis.get("bigdata"));//phpest

        //只有key不存在的时候在可以保存key,不存在返回1,存在返回0
        Long r = jedis.setnx("count", "200");
        System.out.println(r);//0----存在返回0

        jedis.mset("bigdata","java","count","2000");
        List<String> mget = jedis.mget("bigdata", "count");
        System.out.println(mget);

        System.out.println("-------------下面进入set集合操作-------------");

        /**
         * 集合操作
         * 元素排序 , 不可重复
         */
        //添加元素
        Long s = jedis.sadd("hobbys", "吃", "喝", "玩", "乐", "看书", "游泳");
        System.out.println(s);//6

        //查询集合元素的数量
        Long size = jedis.scard("hobbys");
        System.out.println(size);//6

        //取多个集合的差集(用元素多的集合减去元素少的集合)
        jedis.sadd("hobbys2","吃","游泳");
        Set<String> sdiff = jedis.sdiff("hobbys", "hobbys2");
        System.out.println(sdiff);//[乐, 玩, 看书, 喝]

        //取hobbys 和 hobbys2 的差集,将结果保存在hobbys3集合中
        jedis.sdiffstore("hobbys3","hobbys","hobbys2");
        Long hobbys3 = jedis.scard("hobbys3");
        System.out.println(hobbys3);//4

        //取多个集合的交集
        Set<String> sinter = jedis.sinter("hobbys", "hobbys2");
        System.out.println(sinter);//[游泳, 吃]

        //将结果集保存到新的集合中
        jedis.sinterstore("hobbys4","hobbys","hobbys2");
        Long hobbys4 = jedis.scard("hobbys4");
        System.out.println(hobbys4);//2

        //判断某个元素是否在集合中
        Boolean sismember = jedis.sismember("hobbys", "吃");
        System.out.println(sismember);//true

        //取集合中的元素
        Set<String> hobbys = jedis.smembers("hobbys");
        System.out.println(hobbys);//[吃, 玩, 乐, 游泳, 看书, 喝]

        //从集合中随机取元素
        List<String> list = jedis.srandmember("hobbys", 2);
        System.out.println(list);//[乐, 游泳]

        //从集合中移除元素或多个元素,返回删除的个数
        Long srem = jedis.srem("hobbys", "吃", "喝", "乐");
        System.out.println(srem);//3

        //根据查询关键字搜索集合中的元素   0:搜索开始位置    搜索规则
        jedis.sadd("hobbys","java","javascript","c","c++","c#","python","scala");
        ScanParams scanParams = new ScanParams();
        scanParams.match("java*");
        ScanResult<String> sscan = jedis.sscan("hobbys", 0, scanParams);
        List<String> result = sscan.getResult();
        System.out.println(result);//[java, javascript]

        System.out.println("-------------下面进入SortedSet操作-------------");
        /**
         * SortedSet 类型
         * 元素不可重复
         * 元素有序的(根据 score 排序)
         * score 值越大,排名越靠后
         */
        //添加元素
        jedis.zadd("price",1,"1");
        jedis.zadd("price",5,"10");
        jedis.zadd("price",3,"15");
        jedis.zadd("price",7,"4.5");
        jedis.zadd("price",8,"6.5");
        jedis.zadd("price",9,"3");
        jedis.zadd("price",10,"五百");

        //获取元素列表
        Set<String> price = jedis.zrange("price", 0, -1);
        System.out.println(price);//[1, 15, 10, 4.5, 6.5, 3, 五百]

        //获取指定范围的元素数据
        Long zcount = jedis.zcount("price", 1, 8);
        System.out.println(zcount);//5

        //指定key在集合的分值排名,下标从0开始
        Long zrank = jedis.zrank("price", "3");
        System.out.println(zrank);//

        //删除成功会返回删除的元素个数,,失败返回0
        Long zrem = jedis.zrem("price", "五百", "4.5");
        System.out.println(zrem);//2

        //获取元素的排名分值
        Double zscore = jedis.zscore("price", "10");
        System.out.println(zscore);//5.0

        //获取指定范围的后几个元素
        Set<String> zrevrange = jedis.zrevrange("price", 0, 2);
        System.out.println(zrevrange);//[3, 6.5, 10]

        System.out.println("-------------下面进入 list 队列 操作-------------");
        /**
         * list 队列
         */
        Long lpush = jedis.lpush("users", "wangbaoqiang", "dengchao", "guodegang", "wujing");
        System.out.println(lpush);//4

        //向尾部添加元素,返回链表的长度
        Long rpush = jedis.rpush("users", "mayun", "mahuateng", "likaifu", "leijun");
        System.out.println(rpush);//8

        //直接获取链表长度
        Long llen = jedis.llen("users");
        System.out.println(llen);//8

        //获取链表元素
        List<String> user = jedis.lrange("users", 0, -1);
        System.out.println(user);//[wujing, guodegang, dengchao, wangbaoqiang, mayun, mahuateng, likaifu, leijun]

        //删除指定的相同元素数量,返回删除元素的个数
        jedis.rpush("users","mayun","mayun");
        Long lrem = jedis.lrem("users", 2, "mayun");
        System.out.println(lrem);//2

        //往队列头部插入元素,列表必须存在
        Long lpushx = jedis.lpushx("users", "huangbo");
        System.out.println(lpushx);//9

        //修改队列中指定索引的元素
        String lset = jedis.lset("users",0, "yangyang");
        System.out.println(lset);//ok

        //返回指定索引的元素
        String lindex = jedis.lindex("users", 0);
        System.out.println(lindex);//yangyang

        System.out.println("-------------下面进入 hash 散列 操作-------------");
        /**
         * hash 散列 操作
         */
        //添加元素,如果key存在则修改,并且返回0,否则反之
        Long username = jedis.hset("user", "name", "zhangsan");
        Long userage = jedis.hset("user", "age", "18");
        System.out.println(username);
        System.out.println(userage);

        //获取某一个key的值
        String hget = jedis.hget("user", "name");
        System.out.println(hget);

        //获取所有元素
        Map<String, String> user1 = jedis.hgetAll("user");
        for (Entry<String, String> map : user1.entrySet()) {
            System.out.println(map.getKey() + "=" + map.getValue());
        }

        //判断某元素是否存在
        Boolean hexists = jedis.hexists("user", "sex");
        System.out.println(hexists);//false

        //获取hash的字段数量
        Long hlen = jedis.hlen("user");
        System.out.println(hlen);//2

        //获取多个字段
        List<String> hmget = jedis.hmget("user", "name", "age");
        System.out.println(hmget);//[zhangsan, 18]

        List<String> hvals = jedis.hvals("user");
        System.out.println(hvals);//[zhangsan, 18]

        //删除字段
        Long hdel = jedis.hdel("user", "name", "age");
        System.out.println(hdel);//2

        //查找字段
        jedis.hset("user","js1","valuel");
        jedis.hset("user","xxxjs1","valuel2");
        jedis.hset("user","abcjs1","valuel3");
        ScanParams scanParams1 = new ScanParams();
        scanParams1.match("js*");
        ScanResult<Entry<String, String>> hscan = jedis.hscan("user", 0, scanParams1);
        for (Entry<String, String> map : hscan.getResult()) {
            System.out.println(map.getKey() + "=" + map.getValue());
        }

        //关闭
        jedis.close();
    }
}
