package day09

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  *
  * @ClassName: LogsUserInfo
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/28 14:40
  * 需求:在一定时间范围内,求用户所在所有基站停留的时间最长Top2
  */
object LogsUserInfo {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf()
      .setAppName("LogsUserInfo")
      .setMaster("local")
    val sc = new SparkContext(conf)

    //获取用户访问基站的信息数据
    //手机号phone,时间戳,基站,事件
    val files: RDD[String] = sc.textFile(
      "F:\\IDEA_WorkSpace\\scala\\src\\TestData\\mobilelocation\\log")

    //切分用户数据
    val lacAndPhoneAndTime: RDD[((String, String), Long)] = files.map(line => {
      //切分
      val user: Array[String] = line.split(",")
      //手机号
      val phone: String = user(0)
      //时间戳
      val time: Long = user(1).toLong
      //基站ID
      val lac: String = user(2)
      //事件类型
      val eventType: Int = user(3).toInt

      val time_long =
        if (eventType == 1) - time else time
      //((手机号,基站),时长)
      ((phone, lac), time_long)
    })

    //用户在相同基站停留的时间总结
    val sumAndPhoneAndTime: RDD[((String, String), Long)] =
      lacAndPhoneAndTime.reduceByKey(_ + _)

    //为了方便,需要整合用户在基站停留的时间信息,把基站ID放在前面
    val lacAndPhoneAndlacAndTime: RDD[(String, (String, Long))] =
      sumAndPhoneAndTime.map(t => {
      //获取手机号
      val phone = t._1._1
      //获取基站ID
      val lac = t._1._2
      //获取用户在单个基站停留的总时长
      val time = t._2

      (lac, (phone, time))
    })

    //获取基站信息(经纬度)
    //基站ID,经度x,纬度y,___
    val lacInfo: RDD[String] = sc.textFile(
      "F:\\IDEA_WorkSpace\\scala\\src\\TestData\\mobilelocation\\lac_info.txt")

    val lacAndXY: RDD[(String, (String, String))] = lacInfo.map(t => {
      val filds = t.split(",")
      //获取基站ID
      val lac = filds(0)
      //获取经度
      val x = filds(1)
      //获取纬度
      val y = filds(2)
      (lac, (x, y))
    })

    //把经纬度信息join到用户访问信息中去
    //(lac, (phone, time))    (lac, (x, y))
    val joined: RDD[(String, ((String, Long), (String, String)))] =
      lacAndPhoneAndlacAndTime.join(lacAndXY)

    val phoneAndTimeAndXY: RDD[(String, Long, (String, String))] = joined.map(t => {
      //获取手机号
      val phone = t._2._1._1
      //获取基站ID
      val lac = t._1
      //获取时长
      val time = t._2._1._2
      //获取经纬度
      val xy = t._2._2
      (phone, time, xy)
    })

    //按照手机号进行分组
    val grouped: RDD[(String, Iterable[(String, Long, (String, String))])] =
      phoneAndTimeAndXY.groupBy(_._1)

    //然后按照时长排序
    val sorted: RDD[(String, List[(String, Long, (String, String))])] =
      grouped.mapValues(_.toList.sortBy(_._2).reverse)

    //取Top2
    val result: Array[(String, List[(String, Long, (String, String))])] = sorted.take(2)

    //打印
    println(result.toBuffer)

    //关闭
    sc.stop()
  }
}
