package com.curso;
import java.io.IOException;
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
        final String palabraObjetivo = "Federico";
        final List<String> palabrasValidas = leerPalabrasValidas();

        // Calentar el JIT
        System.out.println("Calentando el JIT");
        List<String> palabrasParaCalentar = new ArrayList<>();
        palabrasParaCalentar.add("Federico");
        palabrasParaCalentar.add("Membrillo");
        palabrasParaCalentar.add("Casimiro");
        palabrasParaCalentar.add("Morcilla");
        for (int i = 0; i < 5; i++) {
            for (String palabraParaCalentar : palabrasParaCalentar) {
                buscarPalabrasSimilares(palabraParaCalentar, palabrasValidas);
            }
        }
        System.out.println("Calentamiento terminado");
        // Y ahora mido el tiempo
        long tin= System.nanoTime();
        final List<String> palabrasSimilares = buscarPalabrasSimilares(palabraObjetivo, palabrasValidas);
        long tout= System.nanoTime();
        System.out.println("Tiempo invertido en la búsqueda: "+(tout-tin)/(1000*1000) +" microsegundos");
        imprimirPalabrasSimilares(palabrasSimilares);
    }

    private static void imprimirPalabrasSimilares(List<String> palabrasSimilares) {
        palabrasSimilares.forEach(System.out::println);
    }

    private static List<String> buscarPalabrasSimilares(String palabraObjetivo, List<String> palabrasValidas) {
        final String palabraObjetivoNormalizada = normalizarPalabra(palabraObjetivo);
        return palabrasValidas.stream()            // Para cada palabra
                .parallel() // .parallelStream()
                            // Abre tantos hilos como Cores tengas disponibles en la máquina
                            // Reparte los datos entre esos hilos
                            // Mandales las tareas que hay que ejecutar
                            // Espera a que todos los hilos terminen
                            // Recoge los resultados
               .filter( palabra -> Math.abs( palabra.length() - palabraObjetivoNormalizada.length() ) <= DISTANCIA_MAXIMA_ADMISIBLE  ) // Quito las muy distintas en tamaño
                        /*
                        Le llega un como List<String> llamado "palabras"
                        List<String> palabrasDeDistanciaSimilar=new ArrayList<String>();
                        for (palabra:palabras){
                            boolean salida = Mirar_SI_las_palabras_son_de_tamaño_Similar(palabra);
                            if(salida)
                                palabrasDeDistanciaSimilar.add(palabra);
                        }
                        return palabrasDeDistanciaSimilar;
                        */
               .map(   palabraDeLongitudSimilar ->
                                new PalabraPuntuada(palabraDeLongitudSimilar, distanciaLevenshtein(palabraDeLongitudSimilar, palabraObjetivoNormalizada)  )) // Añado las distancias
               .filter( palabraPuntuada -> palabraPuntuada.distancia <= DISTANCIA_MAXIMA_ADMISIBLE ) // Quito las muy diferentes
               .sorted(Comparator.comparing( palabraPuntuada -> palabraPuntuada.distancia)) // Ordeno por distancia
               .limit(MAXIMO_PALABRAS_A_DEVOLVER) // Me quedo con las DISTANCIA_MAXIMA_ADMISIBLE mejores!
               .map( palabraPuntuada -> palabraPuntuada.palabra ) // Descarto las puntuaciones
               .collect(Collectors.toList());
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
    private static class PalabraPuntuada {
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
