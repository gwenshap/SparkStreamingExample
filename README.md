How to invoke:

ETL example:

java -cp /Users/gshapira/workspace/SparkStreamingLambda/target/SparkStreamingLambda-1.0-SNAPSHOT.jar:/Users/gshapira/Downloads/spark-1.0.2-bin-hadoop2/lib/spark-assembly-1.0.2-hadoop2.2.0.jar cloudera.fun.sparklambda.etl.BatchErrorCount local example_data/testData.txt resData

Streaming example:

java -cp /Users/gshapira/workspace/SparkStreamingLambda/target/SparkStreamingLambda-1.0-SNAPSHOT.jar:/Users/gspira/Downloads/spark-1.0.2-bin-hadoop2/lib/spark-assembly-1.0.2-hadoop2.2.0.jar cloudera.fun.sparklambda.streaming.StreamingErrorCount local[2] localhost 9999

