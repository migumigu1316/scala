package day08

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  *
  * @ClassName: Coalesces
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/27 10:02
  * 重点
  * Coalesces 合并
  */
object Coalesces {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("Coalesces").setMaster("local")
    val sc = new SparkContext(conf)
/**
    //coalesces算子,功能是将RDD的partition缩减,减少
    //将一定的数据,压缩到更少的partition中去

    //建议使用场景,配合filter算子使用
    //使用filter算子过滤很多数据以后,比如30%数据,出现了很多partition中的数据不均匀的情况
    //此时建议使用coalesce算子,压缩RDD的partition熟练
    //从而让各个partition中放入数据更加紧凑
*/
    /**
      * 需求:
      * 公司原先有6个部门
      * 但是呢，不巧的是碰到公司裁员，裁员以后呢，有的部门中的人员就少了
      * 是不是出现了人员分配不均匀的现象了
      * 此时呢，做一个部门的整合操作，将不同的部门员工进行压缩。
      * val list = List("张三","李四","王五","麻子","赵柳","田七","王大牛","小明","小倩","阿强")
      */

    val list = List("张三","李四","王五","田七","牛二","麻子","赵钱","孙丽","小倩","武松")
    //6个分区表示6个部门
    val nameRDDs: RDD[String] = sc.parallelize(list,6)
    val namePartitions: RDD[(Int, String)] =
      nameRDDs.mapPartitionsWithIndex(mapPartitionIndex,true)
    namePartitions.collect().foreach(println)

    //由原先的6个合并成3个部门,调用coalesce
    val namePartition2: RDD[(Int, String)] = namePartitions.coalesce(3)
    val namePartition3: RDD[(Int, (Int, String))] =
      namePartition2.mapPartitionsWithIndex(mapPartitionIndex2,true)
    namePartition3.collect().foreach(println)
    sc.stop()
  }
//写个配合mapPartitionIndex, i1是分区号,
  def mapPartitionIndex(i1:Int,iter:Iterator[String]):Iterator[(Int,String)]={
    var  res=List[(Int,String)]()
    while(iter.hasNext){
      var name=iter.next()
      res=res.::(i1,name)
    }
    res.iterator
  }

  def mapPartitionIndex2(i1:Int,iter:Iterator[(Int,String)]):Iterator[(Int,(Int,String))]={
    var res=List[(Int,(Int,String))]()
    while (iter.hasNext){
      val name = iter.next()
      res= res.::(i1,name)
    }
    res.iterator
  }

}
