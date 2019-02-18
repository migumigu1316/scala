package day04

/**
  *
  * @ClassName: Implicits
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/20 16:28
  *
  * implicit 隐式转换关键字
  */
object Implicits {
  implicit def object2SpecialPerson(obj: Object): SpecialPerson = {
    if (obj.getClass == classOf[Studenteds]){
      val stu = obj.asInstanceOf[Studenteds]
      new SpecialPerson(stu.name)
    }else if (obj.getClass == classOf[Older]){
      val older = obj.asInstanceOf[Older]
      new SpecialPerson(older.name)
    }else null
  }
}
