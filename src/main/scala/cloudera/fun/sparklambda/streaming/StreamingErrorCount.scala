/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cloudera.fun.sparklambda.streaming

import cloudera.fun.sparklambda.common.ErrorCount
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, Logging}
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.StreamingContext._
import org.apache.log4j.{Level, Logger}


/**
 * Created by gshapira on 8/20/14.
 */
object StreamingErrorCount extends Logging{

  def main(args: Array[String]): Unit = {


   if (args.length < 3) {
      System.err.println("Usage: StreamingErrorCount <master> <hostname> <port>")
      System.exit(1)
    }

    val sparkConf = new SparkConf()
      .setMaster(args(0))
      .setAppName(this.getClass.getCanonicalName)

    setStreamingLogLevels()


    val ssc = new StreamingContext(sparkConf, Seconds(10))
    val dStream = ssc.socketTextStream(args(1), args(2).toInt, StorageLevel.MEMORY_AND_DISK_SER)
      dStream.print()

    val errCountStream = dStream.transform(rdd => ErrorCount.countErrors(rdd))

    var daily = 0;
    errCountStream.foreachRDD((rdd: RDD[(String,Int)]) => {
      if (rdd.count() != 0) {
        val partial = rdd.first()._2
        daily = daily + partial
        System.out.println("Errors this minute:%d".format(partial))
        System.out.println("Errors today: %d".format(daily))
      }
    })
    ssc.start()
    ssc.awaitTermination()
  }

  def setStreamingLogLevels() {
    val log4jInitialized = Logger.getRootLogger.getAllAppenders.hasMoreElements
    if (!log4jInitialized) {
      // We first log something to initialize Spark's default logging, then we override the
      // logging level.
      logInfo("Setting log level to [WARN] for streaming example." +
        " To override add a custom log4j.properties to the classpath.")
      Logger.getRootLogger.setLevel(Level.WARN)
    }
  }

}
