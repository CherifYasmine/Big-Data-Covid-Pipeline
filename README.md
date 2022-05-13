# Big Data Pipeline 

- This is a pipeline that uses many Big Data technologies to process worldwide covid cases and per country.
- It was made by me and [Yasmine Cherif](https://github.com/CherifYasmine) to evaluate our academic Big Data Lab.

### Data
- We took the date  from [Kaggle](https://www.kaggle.com/datasets/ghassen1302/coronavirus-tunisia?select=world_daily_number_of_cases.csv.csv)
- The table consists of 56575 lines.
- Each line represent daily covid cases and deaths statistics in country.
- We're interested in grouping the sum of covid cases per country and the worldwide covid cases count.

### Architecture
![Imgur](https://i.imgur.com/Zgyx7AI.png)
- We ran the pipeline and these technologies on 3 docker containers: 1 master and 2 slaves.
- Everything is in Java, and the pipeline script is written in shell script.
- We applied Hadoop's MapReduce on the data, after storing it in HDFS to generate covid cases per country.
- We sent them to a table in HBase with Kafka.
- We also applied Spark's Batch Processing to calculate the total number of cases.

