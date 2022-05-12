import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
public class HbaseCountry {

    private Table table;
    private String tableName = "covid_cases";
    private String family = "cases";

    public void getCountry(String[] args) throws IOException {
    	
    	String country=args[0];
    	
        Configuration config = HBaseConfiguration.create();
        Connection connection = ConnectionFactory.createConnection(config);

        
        table = connection.getTable(TableName.valueOf(tableName));
        try {
            byte[] row = Bytes.toBytes("cases_"+country);

            Get g = new Get(row);

            Result r = table.get(g);
            System.out.println("Number of cases in "+country+" is : "+Bytes.toString(r.getValue(family.getBytes(), "casesNumber".getBytes())));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            table.close();
            connection.close();
        }   
    }


    public static void main(String[] args) throws IOException {
        HbaseCountry admin = new HbaseCountry();
        admin.getCountry(args);
    }
}