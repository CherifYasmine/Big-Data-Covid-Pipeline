#!/bin/bash
cd /root/pipeline
echo "Big Data Covid Pipeline"
echo "-------------------"
echo ""
sleep 3

echo ""
echo "0-Initializing"
sleep 2
rm covidpercountry.txt
rm covidtotal.txt
rm covidcases.txt
hadoop fs -rm pipeline/input/covidcases.txt
hadoop fs -rm pipeline/input/covidtotal.txt
hadoop fs -rm -r pipeline/output
hadoop fs -rm -r pipeline/output2

echo ""
echo "1-Putting data in HDFS"
sleep 3
sed '1d' covid.txt > covidcases.txt
hadoop fs -put covidcases.txt pipeline/input

echo ""
echo "2-Applying Map Reduce"
sleep 3
hadoop jar CasesTotal.jar tn.insat.pipeline.CasesTotal pipeline/input pipeline/output

echo ""
echo "3-Getting data from HDFS"
sleep 3
hadoop fs -getmerge pipeline/output/ covidpercountry.txt

echo ""
echo "4-Creating table in HBase"
sleep 3
java HbaseTable

echo ""
echo "5-Creating Kafka topic"
sleep 3
kafka-topics.sh --zookeeper localhost:2181 --delete --topic covid
sleep 1
kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic covid

echo ""
echo "6-Launching Kafka Producer"
sleep 3
java -cp "$KAFKA_HOME/libs/*":. CovidProducer covid covidpercountry.txt

echo ""
echo "7-Launching Kafka Consumer and saving in Hbase"
sleep 3
java -cp "/usr/local/hbase/lib/*:$KAFKA_HOME/libs/*":. CovidConsumer covid

echo ""
echo "8-Launching Kafka Consumer and saving in Hbase"
sleep 3
hadoop fs -put covidpercountry.txt pipeline/input
spark-submit  --class tn.insat.pipeline.CasesTotalTask --master local  --driver-memory 4g --executor-memory 2g --executor-cores 1                spark_total.jar  pipeline/input/covidpercountry.txt  pipeline/output2 >> covidtotal.txt

echo ""
echo "Pipeline Done"
sleep 1
echo "Showing Results:"
sleep 1
cat covidtotal.txt
sleep 2
