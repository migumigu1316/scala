package day04

/**
  *
  * @ClassName: SuperMans
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/20 19:20
  *
  */
object SuperMans {
  implicit def man2superman(man:Man):SuperMan={
    new SuperMan(man.name)
  }
}
