//package day12;
//
//import org.apache.spark.SparkConf;
//import org.apache.spark.api.java.JavaRDD;
//import org.apache.spark.api.java.JavaSparkContext;
//import org.apache.spark.api.java.function.Function;
//import org.apache.spark.sql.DataFrame;
//import org.apache.spark.sql.Row;
//import org.apache.spark.sql.SQLContext;
//
//import java.util.List;
//
//
///**
// * @ClassName: JavaRDD2DataFrame
// * @Description: TODO
// * @Author: xqg
// * @Date: 2018/10/8 14:28
// *
// * 第一种: 通过反射方式实现
// */
//public class JavaRDD2DataFrame {
//    public static void main(String[] args) {
//        //创建一个普通的RDD
//        SparkConf conf = new SparkConf().setAppName(
//                "JavaRDD2DataFrame").setMaster("local");
//        JavaSparkContext sc = new JavaSparkContext(conf);
//        SQLContext ssc = new SQLContext(sc);
//        JavaRDD<String> files = sc.textFile(
//                "F:\\IDEA_WorkSpace\\scala\\src\\TestData\\day12\\students.txt");
//
//        //对这个RDD进行操作
//        JavaRDD<student> studented = files.map(new Function<String, student>() {
//            @Override
//            public student call(String s) throws Exception {
//                //切分
//                String[] splits = s.split(",");
//                //将需要的类new出来,然后进行赋值
//                student stu = new student();
//                stu.setId(Integer.valueOf(splits[0]));
//                stu.setName(splits[1]);
//                stu.setAge(Integer.valueOf(splits[2]));
//                return stu;
//            }
//        });
//
//        //接下来开始将使用反射方式,将RDD转换为DataFrame
//        //将student传入进去,其实就是用反射的方法来创建DataFrame
//        //因为student本身就是一个反射应用
//        DataFrame df = ssc.createDataFrame(studented, student.class);
//        //拿到一个DF后,就可以注册一个临时表,然后=对其中的数据执行SQL语句操作
//        df.registerTempTable("student");
//        //针对临时表执行SQL语句,查询年龄小于18的
//        DataFrame sql = ssc.sql("select * from student where age <= 18 ");
//
////        sql.show();
//
//        //其实这种展示没什么意义,要将这些数据转换成RDD进行操作
//        JavaRDD<Row> rowJavaRDD = sql.javaRDD();
//
//        //调用map算子,进行处理
//        JavaRDD<student> studentJavaRDD = rowJavaRDD.map(new Function<Row, student>() {
//            @Override
//            public student call(Row row) throws Exception {
//                student stu = new student();
//                stu.setId(row.getInt(1));
//                stu.setName(row.getString(2));
//                stu.setAge(row.getInt(0));
//                return stu;
//            }
//        });
//
//        //将数据收回来,用collect
//        List<student> collect = studentJavaRDD.collect();
//        //最后进行数据的自定义展示
//        for (student stu : collect) {
//            System.out.println(stu.getId() + " " + stu.getName() + " " + stu.getAge());
////            System.out.println(stu.toString());
//        }
//    }
//}
