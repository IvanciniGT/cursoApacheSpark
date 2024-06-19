package com.curso;

public class PalabrasSimilares {

    public static void main(String[] args) {
        String palabraObjetivo = "manana";
        String ficheroConPalabras = "diccionario.ES.txt";
        // Ejemplo de uso de la distancia de Levenshtein
        //System.out.println("Distancia entre " + palabraObjetivo + " manzano: "+ distanciaLevenshtein(palabraObjetivo, "manzano"));

        // Leer el archivo de palabras
        // Y queremos las 10 más similares a la palabra objetivo
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
