package day05

import scala.actors.{Actor, Future}

/**
  *
  * @ClassName: ActorDemo03
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/21 10:08
  *        同步发送和异步发送
  */
//
case class SyncMsg(id: Int, msg: String)

//
case class AsyncMsg(id: Int, msg: String)

//答复
case class ReplyMsg(id: Int, msg: String)

class ActorDemo03 extends Actor {
  //继承Actor   重写act方法
  override def act(): Unit = {
    while (true) {
      receive {//发送
        case "start" => println(" start..........")
        case SyncMsg(id, msg) => println("id : " + s"${id}" + "  msg :" + msg)
         //返回值 sender主要是用来返回数据
          sender ! ReplyMsg(3, "ok")
        case AsyncMsg(id, msg) => println("id : " + s"${id}" + "  msg :" + msg)
         Thread.sleep(3000)
//          sender主要是用来返回数据
          //响应
          sender ! ReplyMsg(3, "ok")
      }
    }
  }
}

object ActorDemo03Test {
  def main(args: Array[String]): Unit = {
    val actor = new ActorDemo03
    actor.start()

    //异步消息 actor !
    actor ! AsyncMsg(1, "hello actor!!")
    println("异步发送消息成功")

    //同步消息 actor !?
//    !?第二种表示同步发送消息，有返回值（死锁问题）
    actor !? SyncMsg(2, "hello actor!!")
    println("同步发送消息成功")

    //异步消息 actor !!
    val reply: Future[Any] = actor !! AsyncMsg(4, "汤尼 actor!!!")
    //获取值,apply进行取值操作,然后通过isSet进行判断是否有值
    val r = reply.apply()
    println(reply.isSet)
    println(r)
  }
}