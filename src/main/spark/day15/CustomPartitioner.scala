package day15

import kafka.producer.Partitioner
import kafka.utils.{Utils, VerifiableProperties}

/**
  *
  * @ClassName: CustomPartitioner
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/10/13 10:24
  *
  */
class CustomPartitioner(props: VerifiableProperties = null) extends Partitioner{
  override def partition(key: Any, numPartitions: Int) = {
    Utils.abs(key.hashCode) % numPartitions
  }
}
