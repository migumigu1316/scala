//package day05
//
//import akka.actor
//import akka.actor.{Actor, ActorSystem, Props}
//import com.typesafe.config.{Config, ConfigFactory}
//
///**
//  *
//  * @ClassName: MasterDemo
//  * @Description: TODO
//  * @Author: xqg
//  * @Date: 2018/9/21 11:53
//  *
//  */
//class MasterDemo extends Actor{
//  /**
//    * 进行初始化操作
//    */
//  override def preStart(): Unit = {
//    println("Perstart方法被执行")
//  }
//
//  /**
//    * 进行循环接受和发送消息
//    */
//  override def receive = {
//    case "start" => println("接收到自己发来的消息")
//    case "stop" => println("stop....")
//    case "context" => println("context....")
//      sender ! "reply"
//  }
//}
//
//
//object AkkaMster {
//  def main(args: Array[String]): Unit = {
//    val host = "127.0.0.1"
//    val port = "6666"
//    //加载配置信息  ---s+按三次""
//    val config =
//      s"""
//         |akka.actor.provider = "akka.remote.RemoteActorRefProvider"
//         |akka.remote.netty.tcp.hostname = "$host"
//         |akka.remote.netty.tcp.port = "$port"
//       """.stripMargin
//    //配置信息要加载到Actor中
//    val cf: Config = ConfigFactory.parseString(config)
//    //创建ActorSystem,只有这个对象才能启动actor程序
//    val masterSystem: ActorSystem = ActorSystem("MasterSystem",cf)
//    //用masterSystem创建Actor,并加载需要启动的类,起个别名
//    val master: actor.ActorRef = masterSystem.actorOf(Props[MasterDemo],"Master")
////    master ! "context"
//
//  }
//}