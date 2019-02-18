package day11

import java.sql.{Connection, Date, DriverManager, PreparedStatement, SQLException}

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  *
  * @ClassName: JDBCTest
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/30 9:22
  *
  * JDBC
  */
object JDBCTest {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("JDBC").setMaster("local")
    val sc = new SparkContext(conf)
    val files: RDD[String] = sc.textFile("F://a.txt")
    val words: RDD[String] = files.flatMap(_.split(" "))
    val tuples: RDD[(String, Int)] = words.map((_,1))
    val result: RDD[(String, Int)] = tuples.reduceByKey(_ + _)

//    result.saveAsTextFile("")
    //调用foreachPartition这个算子,可以提高性能,因为我们可以直接将一个分区中的所有数据写入到JDBC中
    //如果不调用foreachPartition这个算子,涌foreach,调用JDBC和数据的次数差不多
    //性能效率低
    result.foreachPartition(f=>getConnection(f))

    //创建JDBC连接
    def getConnection(ite:Iterator[(String,Int)]): Unit ={
      val conn: Connection = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/mytest?characterEncoding=utf-8", "root", "123456")
      val pstmt: PreparedStatement = conn.prepareStatement(
        "INSERT INTO wordcount(update_time,words,count) VALUES(?,?,?)")

      //写入数据
      try{
        ite.foreach(rdd =>{
          //获取当前的时间
          pstmt.setDate(1,new Date(System.currentTimeMillis()))
          pstmt.setString(2,rdd._1)
          pstmt.setInt(3,rdd._2)
          //查看数据库是否更新
          val count: Int = pstmt.executeUpdate()
          if(count > 0){
            println("添加成功")
          }else{
            println("添加失败")
          }
        })
      }catch{
        case e:SQLException => println(e.getMessage)
      }finally {
        if(pstmt != null){
          pstmt.close()
        }
        if(pstmt != null){
          conn.close()
        }
      }
    }
  }
}
