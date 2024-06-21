
bin/spark-class org.apache.spark.deploy.master.Master  --help
bin/spark-class org.apache.spark.deploy.master.Master
bin/spark-class org.apache.spark.deploy.worker.Worker --help
bin/spark-class org.apache.spark.deploy.worker.Worker spark://192.168.0.32:7077
bin/spark-submit --master spark://192.168.0.32:7077 --class com.curso.spark.EstimarPi /Users/ivan/Desktop/formacionSparkJava/curso/target/curso-1.0.0.jar