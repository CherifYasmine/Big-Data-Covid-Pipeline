package tn.insat.pipeline;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static jersey.repackaged.com.google.common.base.Preconditions.checkArgument;
public class CasesTotalTask {
      private static final Logger LOGGER = LoggerFactory.getLogger(CasesTotalTask.class);

      public static void main(String[] args) {
          checkArgument(args.length > 1, "Please provide the path of input file and output dir as parameters.");
          new CasesTotalTask().run(args[0], args[1]);
      }
      
      public void run(String inputFilePath, String outputDir) {

          SparkConf conf = new SparkConf()
                  .setAppName(CasesTotalTask.class.getName());
          
          JavaSparkContext sc = new JavaSparkContext(conf);

          JavaRDD<String> textFile = sc.textFile(inputFilePath);
          JavaPairRDD<String, Integer> counts = textFile
        		  .flatMap(s -> Arrays.asList(s.split("\t")).iterator())
                  .filter(word -> word.matches("-?\\d+(\\.\\d+)?")&&!word.isEmpty())
                  .mapToPair(word -> new Tuple2<>("Total of covid cases: ", Integer.parseInt(word)))
                  .reduceByKey((a, b) -> a + b);
          			counts.saveAsTextFile(outputDir);
                   int x=counts.first()._2;
        		   System.out.println("Total of covid cases: "+x);
      }
  }
