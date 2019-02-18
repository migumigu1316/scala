package day04

/**
  *
  * @ClassName: dogss
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/20 15:13
  *
  * implicit 隐式转换关键字
  */
object dogss {
  implicit def dog2person(dog:Object):Any=
    if (dog.isInstanceOf[Dogs]){
      val dogs = dog.asInstanceOf[Dogs]
      new Personeds(dogs.name)
    }else{
      Nil
    }
}
