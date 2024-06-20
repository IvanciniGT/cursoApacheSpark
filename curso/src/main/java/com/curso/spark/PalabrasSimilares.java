package com.curso.spark;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PalabrasSimilares {

    private static final int MAXIMO_PALABRAS_A_DEVOLVER = 10;
    private static final int DISTANCIA_MAXIMA_ADMISIBLE = 2;
    private static final String NOMBRE_FICHERO_PALABRAS_VALIDAS = "diccionario.ES.txt";

    public static void main(String[] args) throws URISyntaxException, IOException {
        SparkConf conf = new SparkConf();
        conf.setAppName("PalabrasSimilares");
        conf.setMaster("local[2]");
        JavaSparkContext sc = new JavaSparkContext(conf);

        final String palabraObjetivo = "Federico";
        final List<String> palabrasValidas = leerPalabrasValidas();

        final List<String> palabrasSimilares = buscarPalabrasSimilares(sc, palabraObjetivo, palabrasValidas);
        imprimirPalabrasSimilares(palabrasSimilares);
        sc.close();
    }

    private static List<String> buscarPalabrasSimilares(JavaSparkContext sc, String palabraObjetivo, List<String> palabrasValidas) {
        final String palabraObjetivoNormalizada = normalizarPalabra(palabraObjetivo);
        return sc.parallelize(palabrasValidas)            // Para cada palabra
               .filter( palabra -> Math.abs( palabra.length() - palabraObjetivoNormalizada.length() ) <= DISTANCIA_MAXIMA_ADMISIBLE  ) // Quito las muy distintas en tamaño
               .map(   palabraDeLongitudSimilar ->
                                new PalabraPuntuada(palabraDeLongitudSimilar, distanciaLevenshtein(palabraDeLongitudSimilar, palabraObjetivoNormalizada)  )) // Añado las distancias
               .filter( palabraPuntuada -> palabraPuntuada.distancia <= DISTANCIA_MAXIMA_ADMISIBLE ) // Quito las muy diferentes
               .sortBy( palabraPuntuada -> palabraPuntuada.distancia, true, 1) // Ordeno por distancia
               .map( palabraPuntuada -> palabraPuntuada.palabra ) // Descarto las puntuaciones
               .take(MAXIMO_PALABRAS_A_DEVOLVER);
    }

    private static void imprimirPalabrasSimilares(List<String> palabrasSimilares) {
        palabrasSimilares.forEach(System.out::println);
    }
    private static String normalizarPalabra(String palabra) {
        return palabra.toLowerCase().trim();
    }

    public static List<String> leerPalabrasValidas() throws URISyntaxException, IOException {
        URI uriDelFichero = PalabrasSimilares.class.getClassLoader().getResource(NOMBRE_FICHERO_PALABRAS_VALIDAS).toURI();
        return Files.readAllLines(Paths.get(uriDelFichero))
                .stream()                                    // Para cada línea del fichero
                    // manzana=Fruto del manzano
                    // melon=Fruto del melonero
                .map(  linea -> linea.split("=")[0] /*(1)*/)   // Me quedo con la palabra (sin definición... que aparece detrás del =)
                    // manzana
                    // melon
                .map( PalabrasSimilares::normalizarPalabra ) // Normalizo la palabra
                /*
                Le llega un como List<String> llamado "palabras"
                List<String> palabrasNormalizadas=new ArrayList<String>();
                for (palabra:palabras){
                    String salida = PalabrasSimilares.normalizarPalabra(palabra)
                    palabrasNormalizadas.add(salida);
                }
                return palabrasNormalizadas;
                */


                .collect( Collectors.toList() );             // Las meto en una lista
    }

    /* (1)
    private static String procesarLinea(String linea) {
        return linea.split("=")[0];
    }
    */
    private static class PalabraPuntuada implements Serializable {
        public final int distancia;
        public final String palabra;
        public PalabraPuntuada(String palabra, int distancia) {
            this.distancia = distancia;
            this.palabra = palabra;
        }
    }

    private static int distanciaLevenshtein(String a, String b) {
        int [] costs = new int [b.length() + 1];
        for (int j = 0; j < costs.length; j++)
            costs[j] = j;
        for (int i = 1; i <= a.length(); i++) {
            costs[0] = i;
            int nw = i - 1;
            for (int j = 1; j <= b.length(); j++) {
                int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]), a.charAt(i - 1) == b.charAt(j - 1) ? nw : nw + 1);
                nw = costs[j];
                costs[j] = cj;
            }
        }
        return costs[b.length()];
    }

}
