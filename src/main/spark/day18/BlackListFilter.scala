package day18

import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

/**
  *
  * @ClassName: BlackListFilter
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/10/16 11:07
  *
  * 案例分析: 过滤广告黑名单
  * 用户对我们的网站上的广告可以进行点击
  * 点击之后,需要进行实时计费,点击一次,算一次钱,进行消费
  * 但是,对于某些无良商家刷广告的人,那么我们要一个黑名单
  * 只要是黑名单中的用户点击的广告,就可以给过滤掉
  *
  * transform操作,实现案例
  */
object BlackListFilter {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
        .setAppName("BlackListFilter")
        .setMaster("local[2]")
    val ssc = new StreamingContext(conf,Seconds(5))

    //设置黑名单   //将来可以换成读取文件
    val blackList = Array(("Leo",true),("jack",true))


    //创建一个RDD
    val blackListRDD: RDD[(String, Boolean)] =
      ssc.sparkContext.parallelize(blackList,5)

    //使用socketTextStream来监听端口
    val socketData: ReceiverInputDStream[String] =
      ssc.socketTextStream("192.168.198.10", 9999)

    //接下来接收到的数据转换成和我们黑名单对应的数据格式
    //返回的格式就是(username,data,username)
    val users: DStream[(String, String)] =
      socketData.map(lines=>(lines.split(" ")(1),lines))

    //TODO 调用Spark Streaming中的transform算子,可以随意操作RDD算子
    //使用Spark Streaming中的transform算子进行操作,实现过滤
    val userRDDs = users.transform(user=>{
      /**
        * 这里为什么不用join?
        * 如果当我们join的时候,那么如果不是黑名单中的用户,就join不到,然后就不要他了
        * 这样做不行,所以得使用左外连接,进行join
        * 就算join不到,数据也会被保存
      */
      val joinRDD: RDD[(String, (String, Option[Boolean]))] =
        user.leftOuterJoin(blackListRDD)

      //接下来进行filter实现join后的数据进行过滤
      val userFilter: RDD[(String, (String, Option[Boolean]))] =
        joinRDD.filter(tuple => {
        /**
          * 这里说一下，我们要取到数据的第二组数据的第二个元素，进行过滤
          * 也就是说当我们的元素为false的时候，那么将不进行过滤
          * 否则反之，证明这个人在黑名单中存在。
          */
        if (tuple._2._2.getOrElse(false)) {
          false
        } else {
          true
        }
      })
      //将黑名单过滤后,将处理真正的白名单信息
      val userRDD: RDD[String] = userFilter.map(t=>t._2._1)
      userRDD
    })

    //将数据进行打印输出
    userRDDs.print()

    //启动
    ssc.start()

    //线程等待
    ssc.awaitTermination()

  }
}
