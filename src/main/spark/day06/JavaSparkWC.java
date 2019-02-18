//package day06;
//
//import org.apache.spark.SparkConf;
//import org.apache.spark.api.java.JavaPairRDD;
//import org.apache.spark.api.java.JavaRDD;
//import org.apache.spark.api.java.JavaSparkContext;
//import org.apache.spark.api.java.function.FlatMapFunction;
//import org.apache.spark.api.java.function.Function2;
//import org.apache.spark.api.java.function.PairFunction;
//import scala.Tuple2;
//
//import java.util.Arrays;
//
///**
// * @ClassName: JavaSparkWC
// * @Description: TODO
// * @Author: xqg
// * @Date: 2018/9/25 15:03
// * java实现SparkWC
// */
//public class JavaSparkWC {
//
//    public static void main(String[] args) {
//        SparkConf conf = new SparkConf()
//                .setAppName("JavaSparkWC")
//                .setMaster("local");
//
//        //创建上下文对象
//        JavaSparkContext sc = new JavaSparkContext(conf);
//
//        //读取数据文件,获取数据
//        JavaRDD<String> lines = sc.textFile("F:\\a.txt");
//
//        JavaRDD<String> words = lines.flatMap(new FlatMapFunction<String, String>() {
//            @Override
//            public Iterable<String> call(String s) throws Exception {
//                return Arrays.asList(s.split(" "));
//            }
//        });
//
//        //调用map算子
//        JavaPairRDD<String, Integer> tuples = words.mapToPair(
//                new PairFunction<String, String, Integer>() {
//            @Override
//            public Tuple2<String, Integer> call(String s) throws Exception {
//                return new Tuple2<String, Integer>(s, 1);
//            }
//        });
//
//        //聚合
//        JavaPairRDD<String, Integer> reduces = tuples.reduceByKey(
//                new Function2<Integer, Integer, Integer>() {
//            @Override
//            public Integer call(Integer v1, Integer v2) throws Exception {
//                return v1 + v2;
//            }
//        });
//
//        //Java接口汇总没有提供sortBy算子，如果需要以value进行排序时
//        //需要把数据进行反转一下，排序完成后，再反转过来
//        JavaPairRDD<Integer, String> swaped = reduces.mapToPair(
//                new PairFunction<Tuple2<String, Integer>, Integer, String>() {
//            @Override
//            public Tuple2<Integer, String> call(Tuple2<String, Integer> t) throws Exception {
//
//                //return new Tuple2<>(t._2,t._1);
//                return t.swap();
//            }
//        });
//
//        //降序排列
//        JavaPairRDD<Integer, String> sorted = swaped.sortByKey(false);
//
//        //数据反转回来
//        JavaPairRDD<String, Integer> result = sorted.mapToPair(
//                new PairFunction<Tuple2<Integer, String>, String, Integer>() {
//            @Override
//            public Tuple2<String, Integer> call(Tuple2<Integer, String> t) throws Exception {
//                return t.swap();
//            }
//        });
//
//        //数据打印
//         System.out.println(result.collect());
//
//         //关闭资源
//        sc.stop();
//    }
//}
