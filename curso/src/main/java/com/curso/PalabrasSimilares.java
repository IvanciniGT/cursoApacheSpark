package com.curso;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PalabrasSimilares {

    private static final int MAXIMO_PALABRAS_A_DEVOLVER = 10;
    private static final String NOMBRE_FICHERO_PALABRAS_VALIDAS = "diccionario.ES.txt";

    public static void main(String[] args) throws URISyntaxException, IOException {
        String palabraObjetivo = "manana";
        List<String> palabrasValidas = leerPalabrasValidas();
        List<String> palabrasSimilares = buscarPalabrasSimilares(palabraObjetivo, palabrasValidas);
        imprimirPalabrasSimilares(palabrasSimilares);
    }

    private static void imprimirPalabrasSimilares(List<String> palabrasSimilares) {
    }

    private static List<String> buscarPalabrasSimilares(String palabraObjetivo, List<String> palabrasValidas) {
        return new ArrayList<>();
    }

    private static String normalizarPalabra(String palabra) {
        return palabra.toLowerCase().trim();
    }

    public static List<String> leerPalabrasValidas() throws URISyntaxException, IOException {
        URI uriDelFichero = PalabrasSimilares.class.getClassLoader().getResource(NOMBRE_FICHERO_PALABRAS_VALIDAS).toURI();
        return Files.readAllLines(Paths.get(uriDelFichero))
                .stream()                                    // Para cada linea del fichero
                .map( linea -> linea.split("=")[0] )   // Me quedo con la palabra (sin definición... que aparece detrás del =)
                .map( PalabrasSimilares::normalizarPalabra ) // Normalizo la palabra
                .collect( Collectors.toList() );             // Las meto en una lista
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
