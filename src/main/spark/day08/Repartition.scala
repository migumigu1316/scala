package day08

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  *
  * @ClassName: Repartition
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/27 11:15
  * 公司要增加新的部门
  * 重点
  * Repartition 分配
  */
object Repartition {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("repartition").setMaster("local")
    val sc = new SparkContext(conf)
    /**
      * repartition算子,用于任意将RDD的partition增多,或者减少
      * 与coalesce不同之处在于,coalesce默认是将分区减少
      * 但是调用Repartition算子是将分区增加
      */
    /**
      * 案例需求:
      * 公司增加新部门
      * 因为人员太多,部门太少
      */
    val list = List("张三","李四","王五","田七","牛二","麻子","赵钱","孙丽","小倩","武松")
    //现公司有3个部门
    val nameRDDs = sc.parallelize(list,3)
    //这时候调用Repartition这个算子，实现增加
    val nameRDDs2: RDD[(Int, String)] =
      nameRDDs.mapPartitionsWithIndex(mapPartitionIndex,true)
    nameRDDs2.collect.foreach(println)

    //调用Repartition
    val repartitionName: RDD[(Int, String)] = nameRDDs2.repartition(6)
    val namePartitions: RDD[(Int, (Int, String))] =
      repartitionName.mapPartitionsWithIndex(mapPartitionIndex2,true)
    //查看重新分区后的数据
    namePartitions.collect.foreach(println)
    sc.stop()
  }
  def mapPartitionIndex(i1:Int,iter:Iterator[String]):Iterator[(Int,String)]={
    var res = List[(Int,String)]()
    while (iter.hasNext){
      var name = iter.next()
      res = res.::(i1,name)
    }
    res.iterator
  }
  def mapPartitionIndex2(i1:Int,iter:Iterator[(Int,String)]):Iterator[(Int,(Int,String))]={
    var res = List[(Int,(Int,String))]()
    while(iter.hasNext){
      val tuple = iter.next()
      res=res.::(i1,tuple)
    }
    res.iterator
  }
}