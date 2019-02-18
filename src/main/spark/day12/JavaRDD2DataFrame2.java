//package day12;
//
//
//import org.apache.spark.SparkConf;
//import org.apache.spark.api.java.JavaRDD;
//import org.apache.spark.api.java.JavaSparkContext;
//import org.apache.spark.api.java.function.Function;
//import org.apache.spark.sql.DataFrame;
//import org.apache.spark.sql.Row;
//import org.apache.spark.sql.RowFactory;
//import org.apache.spark.sql.SQLContext;
//import org.apache.spark.sql.types.DataTypes;
//import org.apache.spark.sql.types.StructField;
//import org.apache.spark.sql.types.StructType;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @ClassName: JavaRDD2DataFrame2
// * @Description: TODO
// * @Author: xqg
// * @Date: 2018/10/8 15:22
// * <p>
// * 第二种方式: 使用编程方法指定元数据
// */
//public class JavaRDD2DataFrame2 {
//    public static void main(String[] args) {
//        SparkConf conf = new SparkConf().setAppName("DF2").setMaster("local");
//        JavaSparkContext sc = new JavaSparkContext(conf);
//        SQLContext ssc = new SQLContext(sc);
//
//        //创建一个普通的RDD,注意:类型一定是ROW类型
//        JavaRDD<String> files = sc.textFile(
//                "F:\\IDEA_WorkSpace\\scala\\src\\TestData\\day12\\students.txt");
//
//        JavaRDD<Row> row = files.map(new Function<String, Row>() {
//            @Override
//            public Row call(String s) throws Exception {
//                String[] splits = s.split(",");
//
//                return RowFactory.create(
//                        Integer.valueOf(splits[0]),
//                        splits[1],
//                        Integer.valueOf(splits[2]));
//            }
//        });
//
//        //接下来动态构建元数据
//        List<StructField> fields = new ArrayList<>();
//        fields.add(DataTypes.createStructField(
//                "id", DataTypes.IntegerType, true));
//        fields.add(DataTypes.createStructField(
//                "name", DataTypes.StringType, true));
//        fields.add(DataTypes.createStructField(
//                "age", DataTypes.IntegerType, true));
//
//        //构建StructType
//        StructType structType = DataTypes.createStructType(fields);
//
//        //然后开始使用动态构建的元数据,将rows和structType传入进去,构建DF
//        DataFrame df = ssc.createDataFrame(row, structType);
//
//        //注册临时表
//        df.registerTempTable("student");
//
//        //开始执行sql语句,年龄>17的
//        DataFrame sql = ssc.sql("select * from student where age>17");
//
//        //转换成RDD
//        List<Row> collect = sql.javaRDD().collect();
//        for (Row row1 : collect) {
//            System.out.println(row1.toString());
//        }
//
//    }
//}
