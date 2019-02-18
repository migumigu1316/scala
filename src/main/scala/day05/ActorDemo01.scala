package day05

import scala.actors.Actor

/**
  *
  * @ClassName: ActorDemo01
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/21 9:25
  *
  */
/**
  * Actor发送消息的三种方式
  * ! 第一种表示异步发送消息，无返回值(一般都用这种)
  * !?第二种表示同步发送消息，有返回值（死锁问题）
  * !!第三种表示异步发送消息，无返回值 Futrue
  *
  * Actor启动步骤：
  * 	1.创建一个类继承Actor
  * 	2.实现Actor中的act方法
  * 	3.实例化类，调用start()启动
  * 	4.实现消息的接收发送
  */
class ActorDemo01 extends Actor {
  override def act(): Unit = {
    while (true){
      receive{
        case name => println("Hello, "+ name)
      }
    }
  }
}

object ActorDemo01Test{
  def main(args: Array[String]): Unit = {
    val actor = new ActorDemo01
    //启动
    actor.start()
    //异步无返回值
    //! 第一种表示异步发送消息，无返回值(一般都用这种)
    actor ! "汤尼"

   }
}