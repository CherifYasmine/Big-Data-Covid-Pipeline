import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
public class HbaseTable {

    private Table table;
    private String tableName = "covid_cases";
    private String family = "cases";

    public void createHbaseTable() throws IOException {
        Configuration config = HBaseConfiguration.create();
        Connection connection = ConnectionFactory.createConnection(config);
        Admin admin = connection.getAdmin();

        HTableDescriptor ht = new HTableDescriptor(TableName.valueOf(tableName));
        ht.addFamily(new HColumnDescriptor(family));
        System.out.println("connecting");

        System.out.println("Creating Table");
        createOrOverwrite(admin, ht);
        System.out.println("Done......");
        
        table = connection.getTable(TableName.valueOf(tableName));
        String s1="america";
        String s2="1234";
        try {
            System.out.println("Adding Row");
            byte[] row = Bytes.toBytes("cases_"+s1);
            Put p = new Put(row);

            p.addColumn(family.getBytes(), "country".getBytes(), Bytes.toBytes(s1));
            p.addColumn(family.getBytes(), "casesNumber".getBytes(), Bytes.toBytes(s2));
            table.put(p);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            table.close();
            connection.close();
        }   
    }

    public static void createOrOverwrite(Admin admin, HTableDescriptor table) throws IOException {
        if (admin.tableExists(table.getTableName())) {
            admin.disableTable(table.getTableName());
            admin.deleteTable(table.getTableName());
        }
        admin.createTable(table);
    }

    public static void main(String[] args) throws IOException {
        HbaseTable admin = new HbaseTable();
        admin.createHbaseTable();
    }
}