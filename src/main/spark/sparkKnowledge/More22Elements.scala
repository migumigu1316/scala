package sparkKnowledge

/**
  *
  * @Description: TODO Scala构建超过22个元素的class
  * @ClassName: More22Elements
  * @Author: xqg
  * @Date: 2019/1/24 9:19
  *
  */
object More22Elements {
  def main(args: Array[String]): Unit = {
    val student = new Student(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27)
    /** 获取其中一个元素 */
    println(student.productElement(1).toString)
    /** 是否包含元素：23 */
    println(student.canEqual(23))
    /** 遍历元素 */
    student.productIterator.foreach(println)
    /** toString方法 */
    println(student.productPrefix)
    println(student.equals(student))

  }
}

/**
  * student 类中大于22个元素的实现
  */
class Student(one: Int, two: Int, three: Int, four: Int, five: Int,
              six: Int, seven: Int, eight: Int, nine: Int, ten: Int,
              eleven: Int, twelve: Int, thirteen: Int, fourteen: Int, fifteen: Int,
              sixteen: Int, seventeen: Int, eighteen: Int, nineteen: Int, twenty: Int,
              first: Int, second: Int, third: Int, fourth: Int, fifth: Int,
              sixth: Int, seventh: Int
             ) extends Product {
  val arrays = Array(one, two, three, four, five,
    six, seven, eight, nine, ten,
    eleven, twelve, thirteen, fourteen, fifteen,
    sixteen, seventeen, eighteen, nineteen, twenty,
    first, second, third, fourth, fifth,
    sixth, seventh)

  /**
    * 根据下标获取元素
    *
    * @param n 下标 index
    * @return 元素
    */
  override def productElement(n: Int): Int = arrays(n)

  /**
    * 元素个数
    *
    * @return 元素个数
    */
  override def productArity: Int = arrays.length

  /**
    * 自定义元素比较：是否包含这个元素
    *
    * @param that 输入的元素
    * @return 是否包含，true表示包含
    */
  override def canEqual(that: Any): Boolean = arrays.contains(that)

  /**
    * 类似toString方法
    *
    * @return 元素链接字符串
    */
  override def productPrefix = arrays.mkString("\t")

  /**
    * 获取迭代器
    *
    * @return 迭代
    */
  override def productIterator: Iterator[Int] = new scala.collection.Iterator[Int] {
    private var c: Int = 0

    /** productArity 是元素的个数 */
    private val cMax = productArity

    def hasNext: Boolean = c < cMax

    def next() = {
      /** 这个productElement方法是： 根据下标获取元素 */
      val result = productElement(c)
      c += 1//下标
      result
    }
  }
}