package com.curso.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TrendingTopics {

    private static final List<String> PALABRAS_PROHIBIDAS = new ArrayList<>();
    static{
        PALABRAS_PROHIBIDAS.add("caca");
        PALABRAS_PROHIBIDAS.add("culo");
        PALABRAS_PROHIBIDAS.add("pedo");
        PALABRAS_PROHIBIDAS.add("pis");
        PALABRAS_PROHIBIDAS.add("mierda");
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        // PASO 1: ABRIR UNA CONEXION CON UN CLUSTER DE SPARK
        // Configurar nuestra app para ejecutarse en un cluster: Nombre app, Direccion del cluster
        SparkConf conf = new SparkConf();
        conf.setAppName("TrendingTopics"); // Nombre para mi app en el cluster (ID)
        conf.setMaster("local[2]"); // La dirección será del tipo:     spark://mi-cluster:7077
                                    // En desarrollo, spark me permite usar la palabra local.
                                    // eso crea un cluster de spark en mi máquina para jugar
                                    // Dentro de los corchetes, le digo cuantos cores quiero usar
                                    // Un * significa que use todos los cores disponibles en mi máquina
        // ME conecto con el cluster
        JavaSparkContext sc = new JavaSparkContext(conf);
        // PASO 2: PREPARO LOS DATOS EN FORMATO SPARK
        String nombreFichero = "tweets.txt";
        URI ruta = TrendingTopics.class.getClassLoader().getResource(nombreFichero).toURI();
        List<String> tweets = Files.readAllLines(Paths.get(ruta));
        // PASO 3: Configuro el MAP-REDUCE
        List<String> hashtags= sc.parallelize(tweets)
                .map(    tweet -> tweet.replace("#", " #")   ) // Añado un espacio antes de cada #
                .map(    tweet -> tweet.split("[ ,.;:_()@·<>+*¿?!¡-]+") ) // Separo las palabras por los espacios y otros caracteres
                .flatMap( array -> Arrays.asList(array).iterator()             ) // Convierto CADA array de palabras de cada tweet en un Stream consolidado de palabras
                .filter(  palabra -> palabra.startsWith("#")                   ) // Me quedo solo con las palabras que empiezan por #
                .map(    hashtag -> hashtag.substring(1)            ) // Les quito el #
                .map(    String::toLowerCase                                  ) // Las paso a minúsculas
                .filter(  hashtag -> PALABRAS_PROHIBIDAS.stream().noneMatch( hashtag::contains ) ) // Me quedo con los que no contengan palabras prohibidas
                // Declaro una función que recibe un hashtag y devuelve si alguna palabra prohibida está contenida en el hashtag.
                .collect();
        // PASO 4: Obtengo la información deseada... y la guardo como quiera
        hashtags.forEach(System.out::println);
        // PASO 5: Cierro la conexión con el cluster de Spark
        sc.close();
    }
}