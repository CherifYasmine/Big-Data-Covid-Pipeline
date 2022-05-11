package tn.insat.pipeline;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class CasesMapper extends Mapper<Object, Text, Text, IntWritable> {

    private IntWritable writeCases = new IntWritable();
    private Text word = new Text();

    public void map(Object key, Text value, Mapper.Context context) throws IOException, InterruptedException {
            String row = value.toString();
            String[] cols = row.split(",");
            String country = cols[6];

            int cases = Integer.parseInt(cols[4]);
            word.set(country);
            writeCases.set(cases);
            context.write(word, writeCases);
    }
}
