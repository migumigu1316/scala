package sparkKnowledge

/**
  *
  * @Description: TODO 
  * @ClassName: compareVersion
  * @Author: xqg
  * @Date: 2019/2/18 15:11
  *
  */
object compareVersion {
  def hashRes(a: String, b: String): Int = {
    val tmp_a = strSplit(a)
    val tmp_b = strSplit(b)
    val bool = string2Hash(tmp_a, tmp_b)
    bool
  }

  def string2Hash(tmp_a: String, tmp_b: String): Int = {

    val code_a = tmp_a.hashCode
    val code_b = tmp_b.hashCode
    var b: Int = -1
    if (code_a - code_b > 0) {
      b = 1
    }
    else if (code_a - code_b == 0) {
      b = 0
    }
    else {
      b = 2
    }
    b
  }

  def strSplit(tmp: String): String = {
    val strings: Array[String] = tmp.split("\\.")
    val stringBuffer = new StringBuffer()
    for (i: Int <- 0 to strings.length - 1) {
      stringBuffer.append(strings(i))
    }
    stringBuffer.toString
  }

  def main(args: Array[String]): Unit = {
    var str1 = "1.2.3.a"
    var str2 = "1.2.4.b"

    val bool = hashRes(str1, str2)
    bool match {
      case 0 => println("版本相等！")
      case 1 => println("第一个版本大！")
      case 2 => println("第二个版本大！")
      case _ => println("判断失败！")
    }
  }
}