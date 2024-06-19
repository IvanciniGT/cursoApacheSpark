package com.curso;

import java.io.IOException;
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

    public static void main(String[] args) throws IOException {
        System.out.println("Trending topics");
        // Partiendo de un fichero lleno de TWEETS (cada linea es un tweet)
        //  Acaba generando:
        //  HASHTAG   | OCURRENCIAS
        //  goodVibes | 100
        //  badVibes  | 50
        //  mierdaVibes | 10 ( NO ... prohibido... Contiene una palabra fea
        // Lista de palabras prohibidas: caca, culo, pedo, pis, mierda
        // De momento vamos a montar MEDIO PROGRAMA:
        // Extraer un listado limpio de los hashtags que hay en los tweets del fichero
        String nombreFichero = "tweets.txt";
        String ruta = TrendingTopics.class.getClassLoader().getResource(nombreFichero).getPath();
        Files.readAllLines(Paths.get(ruta))
                .stream()                                                       // Para cada tweet
                .map(    tweet -> tweet.replace("#", " #")   ) // Añado un espacio antes de cada #
                .map(    tweet -> tweet.split("[ ,.;:_()@·<>+*¿?!¡-]+") ) // Separo las palabras por los espacios y otros caracteres
                .flatMap( Arrays::stream                                       ) // Convierto CADA array de palabras de cada tweet en un Stream consolidado de palabras
                .filter(  palabra -> palabra.startsWith("#")                   ) // Me quedo solo con las palabras que empiezan por #
                .map(    hashtag -> hashtag.substring(1)            ) // Les quito el #
                .map(    String::toLowerCase                                  ) // Las paso a minúsculas
                // Quitar los que contengan palabras prohibidas
                .forEach( System.out::println                                 ); // Los imprimo
    }
}
