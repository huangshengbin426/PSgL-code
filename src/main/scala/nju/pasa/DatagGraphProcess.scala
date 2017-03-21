package nju.pasa

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Huangsb on 2017/3/20.
  */
object DatagGraphProcess {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("PSgLGraphProcess")
    conf.set("spark.rdd.compress", "true")
//    conf.set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
//    conf.set("spark.kryo.registrationRequired", "true")

    val sc = new SparkContext(conf)
    val originInput: String = "sample/datagraph_origin"
    val originInputPath: String = args(0)

    val lines = sc.textFile(originInputPath)
    val edge = lines.mapPartitions{str =>
      val edgeIter = str.map{v =>
        val edge = v.split("\t")
        val src = edge(0).toInt
        val dst = edge(1).toInt
        (src, dst)
      }
      edgeIter
    }

    /* data graph */
    val dataGraphInputFormat = edge.groupByKey().map{v =>
      val record: StringBuilder = new StringBuilder
      record.append(v._1)
      for (i <- v._2){
        record.append("\t" + i)
      }
      record.toString()
    }
//    dataGraphInputFormat.collect().foreach(println(_))

    /* vertexlavelmap */
    val vertex2labelmap = edge.groupByKey().map{v =>
      val vertexId = v._1
      val label = 1
      val degree = v._2.size
      val smalldegree = v._2.size
      val bigdegree = v._2.size
      val neighborlabel: Array[Int] = new Array[Int](degree)

      val vertex2labelmapStr: StringBuilder = new StringBuilder
      val neighborStr: StringBuilder = new StringBuilder;
      for (i <- neighborlabel){
        neighborlabel(i) = 1
        neighborStr.append("\t" + neighborlabel(i))
      }
      vertex2labelmapStr.append(vertexId + "\t")
      vertex2labelmapStr.append(label + "\t")
      vertex2labelmapStr.append(degree + "\t")
      vertex2labelmapStr.append(bigdegree + "\t")
      vertex2labelmapStr.append(smalldegree)
      vertex2labelmapStr.append(neighborStr)
      vertex2labelmapStr.toString()
    }
//    vertex2labelmap.collect().foreach(println(_))

    val graphName = "sample"
    val fileOutputPath = args(1)

    dataGraphInputFormat.saveAsTextFile(fileOutputPath + graphName + "/graph/datagraph")
    vertex2labelmap.saveAsTextFile(fileOutputPath + graphName + "/vertex2labelmap/vertex2labelmap")

  }
}
