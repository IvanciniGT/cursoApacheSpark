package com.curso;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

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
        URI ruta = TrendingTopics.class.getClassLoader().getResource(nombreFichero).toURI();

        Files.readAllLines(Paths.get(ruta))
                .stream()                                                      // Para cada tweet
                .map(    tweet -> tweet.replace("#", " #")   ) // Añado un espacio antes de cada #
                .map(    tweet -> tweet.split("[ ,.;:_()@·<>+*¿?!¡-]+") ) // Separo las palabras por los espacios y otros caracteres
                .flatMap( Arrays::stream                                       ) // Convierto CADA array de palabras de cada tweet en un Stream consolidado de palabras
                .filter(  palabra -> palabra.startsWith("#")                   ) // Me quedo solo con las palabras que empiezan por #
                .map(    hashtag -> hashtag.substring(1)            ) // Les quito el #
                .map(    String::toLowerCase                                  ) // Las paso a minúsculas
                .filter(  hashtag -> PALABRAS_PROHIBIDAS.stream().noneMatch( hashtag::contains ) ) // Me quedo con los que no contengan palabras prohibidas
                          // Declaro una función que recibe un hashtag y devuelve si alguna palabra prohibida está contenida en el hashtag.
                .forEach( System.out::println                                 ); // Los imprimo
    }
/*
    private static boolean sinPalabrasProhibidas(String hashtag){
        /*boolean contienePalabraProhibida = false;
        for (String palabraProhibida : PALABRAS_PROHIBIDAS) {                       // Pra cada palabra prohibida
            if(hashtag.contains(palabraProhibida)){                                 // Mira si está contenida en el Hashtag
                contienePalabraProhibida = true;                                    // Si está contenida, marco que contiene una palabra prohibida
                break;
            }
        }
        return !contienePalabraProhibida;                                           // Si no contiene ninguna palabra prohibida, devuelvo true

        return PALABRAS_PROHIBIDAS.stream()                                         // Para cada palabra prohibida
                .filter( palabraProhibida -> hashtag.contains(palabraProhibida) )    // quedate con las que estén contenidas en el hashtag
                .count() == 0;
        return PALABRAS_PROHIBIDAS.stream()     // Para cada palabra prohibida
                .filter( hashtag::contains )     // quedate con las que estén contenidas en el hashtag
                .count() == 0;                  // Si no hay ninguna, es que no contiene ninguna palabra prohibida
        return PALABRAS_PROHIBIDAS.stream().noneMatch( hashtag::contains );         // Aplica la función como un filter.,... y cuenta que ninguna cumpla la condición del predicado
    }
    */
}