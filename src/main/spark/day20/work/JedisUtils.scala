package day20.work

import redis.clients.jedis.Jedis

/**
  *
  * @ClassName: JedisUtils
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/10/18 9:43
  *
  */
object JedisUtils {
  //问题1.计算出总的成交量总额（结果保存到redis中）
  def jedisCount(lines:Array[String],jedis:Jedis): Unit ={
    val sumPrice = lines(4).toDouble
    jedis.incrByFloat(Contants.TOTAL_CONUT,sumPrice)
    println(jedis.get(Contants.TOTAL_CONUT))//24169

  }

  //问题2.计算每个商品分类的成交量的top3（结果保存到redis中）
  def jedisCountTopN(lines:Array[String],jedis:Jedis): Unit ={
    val commodity = lines(2)
    jedis.zrevrange(Contants.TOTAL_commodity,0,2)
    println(jedis.get(Contants.TOTAL_commodity))
  }


}
