//package day05
//
//import akka.actor.{Actor, ActorRef, ActorSystem, Props}
//import com.typesafe.config.{Config, ConfigFactory}
//
///**
//  *
//  * @ClassName: AkkaWorker
//  * @Description: TODO
//  * @Author: xqg
//  * @Date: 2018/9/21 14:18
//  *
//  *  编写Akka的Worker端
//  *   ActorSystem对象：单例对象，可以通过ActorSystem来创建Actor
//  */
///**
//  * PreStart方法：该方法在Actor对象构造器之后，receive方法之前执行，
//  * 然后它只执行一次，该方法的作用就是初始化操作
//  * Receive 方法：该方法在Prestart方法之后执行，不停的执行，用做发送和接收消息
//  */
//class AkkaWorker extends Actor{
//  override def preStart(): Unit = {
//    //通过master的URL获取Master的Actor
//    val master = context.actorSelection(
//      "akka.tcp://MasterSystem@127.0.0.1:6666/user/Master")
//    master ! "context"
//  }
//
//  override def receive: Receive = {
//    case "worker" => println("接收自己发送的消息")
//    case "reply" => println("接收到Master端发送过来的消息")
//  }
//}
//object WorkerTest{
//  def main(args: Array[String]): Unit = {
//    val host = "127.0.0.1"
//    val port = "8888"
//    val config =
//      s"""
//         |akka.actor.provider = "akka.remote.RemoteActorRefProvider"
//         |akka.remote.netty.tcp.hostname = "$host"
//         |akka.remote.netty.tcp.port = "$port"
//       """.stripMargin
//    val cf: Config = ConfigFactory.parseString(config)
//    val workerSystem: ActorSystem = ActorSystem("WorkerSystem",cf)
//    val worker: ActorRef = workerSystem.actorOf(Props[AkkaWorker])
//    worker ! "context"
//  }
//}
