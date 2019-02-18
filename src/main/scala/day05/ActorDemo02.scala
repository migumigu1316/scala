package day05

import scala.actors.Actor

/**
  *
  * @ClassName: ActorDemo02
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/21 9:59
  *
  */
/**
  * 模拟实现用户登陆后台接口
  *
  * @param username
  * @param password
  */
/**
  * 2. 收发case class类型的消息
  * // Scala的Actor模型与Java的多线程模型之间，很大的一个区别就是，Scala Actor
  * 天然支持线程之间的精准通信；即一个actor可以给其他actor直接发送消息。这个功能是非常强大和方便的。
  * // 要给一个actor发送消息，需要使用“actor ! 消息”的语法。在scala中，通常建议使用样例类，
  * 即case class来作为消息进行发送。然后在actor接收消息之后，可以使用scala强大的模式匹配功能来进行不同消息的处理。
  *
  * @param username
  * @param password
  */
//注册
case class Login(username: String, password: String)

//登陆
case class Register(username: String, password: String)

class ActorDemo02 extends Actor {
  override def act(): Unit = {
    while (true) {
      receive {
        case Login(username, password) => println("login,username is "
          + username + " password is " + password)
        case Register(username, password) => println("login,username is "
          + username + " ,password is " + password)
      }
    }
  }
}
object userManger {
  def main(args: Array[String]): Unit = {
    val actor = new ActorDemo02
    //启动actor
    actor.start()
    //异步无返回值
    actor ! Login("Leo","123456")
    actor ! Register("Leo","123456")
  }
}