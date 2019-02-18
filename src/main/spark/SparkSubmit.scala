
import org.apache.spark.SPARK_VERSION
/**
  *
  * @ClassName: SparkSubmit
  * @Description: TODO
  * @Author: xqg
  * @Date: 2018/9/29 17:23
  *
  */
object SparkSubmit {
  def main(args: Array[String]): Unit = {
      println("""Welcome to
      ____              __
     / __/__  ___ _____/ /__
    _\ \/ _ \/ _ `/ __/  '_/
   /___/ .__/\_,_/_/ /_/\_\   version %s
      /_/
                        """.format(SPARK_VERSION))
      println("Type --help for more information.")

  }
}
