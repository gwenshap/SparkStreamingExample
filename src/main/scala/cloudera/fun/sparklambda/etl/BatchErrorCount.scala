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

package cloudera.fun.sparklambda.etl

import cloudera.fun.sparklambda.common.ErrorCount
import org.apache.spark.{SparkConf, SparkContext}


object BatchErrorCount {
  def main(args: Array[String]): Unit = {
    if (args.length<3) {
      System.err.println("Usage: BatchErrorCount <master> <inputfile> <outputfile>")
      System.exit(1)
    }
    val conf = new SparkConf()
      .setMaster(args(0))
      .setAppName(this.getClass.getCanonicalName)
      .setJars(Seq(SparkContext.jarOfClass(this.getClass).get))

    val sc = new SparkContext(conf)

    val lines = sc.textFile(args(1))

    val errCount = ErrorCount.countErrors(lines)

    errCount.saveAsTextFile(args(2))
  }
}
