package day03.work

/**
  *
  * @ClassName: SplitWork
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/19 21:13
  *
  */
//需求：当前字段只切割第一个空格的数据，以元组的形式返回
//需求结果展示（a0000038db0c302，com.yunlian.wewe,com.octinn.birthdayplus com.elinkway.infinitemovies）
object SplitWork {
  def main(args: Array[String]): Unit = {
    val strings = Array("a0000038db0c302 com.yunlian.wewe,com.octinn.birthdayplus com.elinkway.infinitemovies")

    val indes: Int = strings.mkString.indexOf(" ")
    val resulted: Array[(String, String)] = strings.map(t => {
      val str1: String = t.substring(0, indes)
      val str2: String = t.substring(indes)
      (str1, str2)
    })
    println(resulted.toBuffer)

  }
}
