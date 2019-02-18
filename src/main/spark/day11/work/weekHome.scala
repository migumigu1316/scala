package day11.work

/**
  *
  * @ClassName: weekHome
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/10/7 17:13
  *
  */
//import com.mysql.jdbc.Connection
import java.sql.{Connection, Date, DriverManager, PreparedStatement}

import org.apache.spark.broadcast.Broadcast
import org.apache.spark.rdd.RDD
import org.apache.spark.{Partitioner, SparkConf, SparkContext}

//1.需要将两个文件进行读取，将小文件进行广播。
//"C:\\d\\资料\\大数据spark\\week14\\day10\\day11\\ID.txt"
//"C:\\d\\资料\\大数据spark\\week14\\day10\\day11\\sougou.txt"
//"C:\\d\\资料\\大数据spark\\week14\\day10\\day11\\SogouQ.reduced"
//时间	id	搜索内容	标签1	标签2	结果网址
//2.用自定义分区方式，实现分区，设置检查点，倒序排序，统计出一天的点击排行量
//3.将最后的数据结果存入MySQL和HDFS
object weekHome {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local").setAppName("souGou")
    val sc = new SparkContext(conf)

    //    val fileSearch = sc.textFile("C:\\d\\资料\\大数据spark\\week14\\day10\\day11\\sougou.txt")
    val fileSearch = sc.textFile("C:\\d\\资料\\大数据spark\\week14\\day10\\day11\\SogouQ.reduced\\SogouQ.reduced")
    //    val words = fileSearch.flatMap(_.split("\t"))//注意看为什么不是这个
    //两标签是word(3),标签中间不是|t而是“ ”
    //!!!注意!最后一行,什么都没有是个空行,如果不去掉,会有空指针,当然,加个if应该ok??????
    val tags = fileSearch.map(w => {
      val word = w.split("\t")
      word(3)
    })
    //    tags.foreach(println)
    val labels = tags.flatMap(_.split(" "))
    //    labels.take(5).foreach(println)
    //为什么一到这map就出错,为什么!!!!小数据时,,l.toInt>20管使,到大表了,就报错呢
    val tuplesModi = labels.map(l => {
      //      if (l.toInt >20) {
      if (l.length>2|(l.length==2 & l>="20")) {
        //        l == 20
        (20.toString,1)
      }else (l, 1)
    })
    //在这就分区？？？？？？？后面的会有影响吗
    val part = new PartitionID
    val result: RDD[(String, Int)] = tuplesModi.reduceByKey(_+_)
    val results1: RDD[(String, Int)] = tuplesModi.reduceByKey(part,_+_)

    val sorted = results1.sortBy(_._2,false)
    sorted.foreach(println)
    //测试，看计数和是否等于200,是200，但是，大于20的没有被赋值为20
    //    val sum = sc.accumulator(0)
    //    val check = results.foreach(x=>sum+=x._2)
    //    println(sum)

    //和id表关联join
    val idInfofile = sc.textFile("C:\\d\\资料\\大数据spark\\week14\\day10\\day11\\ID.txt")
    //广播的位置,是在读取的时候就做成广播还是,
    val idInfo: Broadcast[RDD[String]] = sc.broadcast(idInfofile)
    //相join的两者的形式，，需要一样？？？？？？？？？？广播后,,东西变了
    //    val id = idInfo.map(x => {
    //      val words = x.split(" ")
    //      (words(0), words(1))
    //    })
    val id = idInfo.value.map(x => {
      val words = x.split(" ")
      (words(0), words(1))
    })

    val joined: RDD[(String, (Int, String))] = sorted.join(id)
    //joined的形式是（一样的，（前者的不同，后者的不同））
    val fin = joined.map(x => {
      (x._2._2, x._2._1)
    })
    //    joined.map((_._2._2,_._2._1)),,不行，因为一个简写中_只能出现一次
    //    join后顺序就又乱了，所以前面的不用排序，在这还得排序
    val fina = fin.sortBy(_._2,false)
    fina.foreach(println)
    //再来个分区，，但是！！！分区器的位置，在第一次shuffle处？？？
    //    fina.saveAsTextFile("C:\\d\\资料\\大数据spark\\week14\\day10\\partitions")
    fina.saveAsTextFile("C:\\d\\资料\\大数据spark\\week14\\day10\\partitionsDIY")
    //    fina.foreachPartition(data2MySql)
    //关门
    sc.stop()
  }
  //连接mysql,可以直接在整个object中建立连接函数,(这,,,好像不是个函数把,,而是个常量,不过这个常量内的内容有点长,还有动作)
  //注意,这里是的preparestatement在foreach中,而那个在外面,有什么区别吗
  //!!!!!!!!!!!!!set中的起始,是从1开始还是从0开始,从0吧???????
  //也可以建连接函数,基本一样
  //  val data2MySql = (it:Iterator[(String,Int)]) => {
  //    var conn : Connection =null;
  //    var ps : PreparedStatement = null;
  //    val sql = "insert into search_info (label,counts,access_date) values(?,?,?)"
  //    val jdbcUrl = "jdbc:mysql://localhost:3306/spark?characterEncoding=utf-8"
  //    val user = "root"
  //    val password = "root"
  //
  //    try{
  //      conn = DriverManager.getConnection(jdbcUrl,user,password)
  //      it.foreach(line=>{
  //        ps = conn.prepareStatement(sql)
  //        ps.setString(1,line._1)
  //        ps.setInt(2,line._2)
  //        ps.setDate(3, new Date(System.currentTimeMillis()))
  //        ps.executeUpdate()
  //      })
  //    } catch {
  //      case e:Exception => println(e.printStackTrace())
  //    }finally {
  //      if(ps !=null)
  //        ps.close()
  //      if(conn != null)
  //        conn.close()
  //    }
  //  }
}
class PartitionID extends Partitioner with Serializable{
  override def numPartitions = 5
  //  val map = Map("1"->0,"2"->0,"3"->0,"4"->0,"5"->1,"6"->1,"7"->1,"8"->1,"9"->2,"10"->2,"11"->2,"12"->2,"13"->3,"14"->3,"15"->3,"16"->3)
  //虽然在前面分区了,但是!之后经过一系列的操作,数据的分区还是发生了变化
  //  override def getPartition(key: Any) :Int= {
  override def getPartition(key: Any) :Int= key match{
    //这个函数的形式？？？？？？？
    //    case "国际" | "社会" | "图片" | "视频"=> 0
    //    case "军事" | "评论" | "历史" | "文化"=> 1
    //    case "公益" | "旅游" | "新闻" | "体育"=> 2
    //    case "科技" | "汽车" | "国内" | "游戏"=> 3
    case "1" | "2" | "3" | "4"=> 0
    case "5" | "6" | "7" | "8"=> 1
    case "9" | "10" | "11" | "12"=> 2
    case "13" | "14" | "15" | "16"=> 3
    case _ => 4
    //    map.getOrElse(key.toString,4)
  }
}
