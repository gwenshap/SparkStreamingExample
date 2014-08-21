# Spark Lambda Architecture
## Overview
This project is intended to show an example of how Spark can be used to efficiently implement a Lambda Architecture

Lambda Architectures typically share some of the business logic between the batch layer and speed layer.
When each layer is implemented in a different language or framework, this leads to code duplication, painful
maintenance and errors.

On the other hand, if Spark is used to implement the batch layer and SparkStreaming for the speed layer, they can share
common functions, reducing code duplication and the associated maintenance overhead.

This project is intended as an example of how this can be done.
It contains two packages for counting errors in logs. One for batch use and the other for streaming.

##Build
mvn clean package

##Usage

###ETL example:

java -cp SparkStreamingLambda-1.0-SNAPSHOT.jar:<spark dir>/lib/spark-assembly-1.0.2-hadoop2.2.0.jar cloudera.fun.sparklambda.etl.BatchErrorCount <master> <input> <output>

###Streaming example:

java -cp SparkStreamingLambda-1.0-SNAPSHOT.jar:<spark dir>/lib/spark-assembly-1.0.2-hadoop2.2.0.jar cloudera.fun.sparklambda.streaming.StreamingErrorCount <master> localhost <port>

to send data to the streaming example, use:
nc -lk <port>

