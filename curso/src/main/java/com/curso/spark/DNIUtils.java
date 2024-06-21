package com.curso.spark;

public interface DNIUtils {

    static String letras = "TRWAGMYFPDXBNJZSQVHLCKE";

    static boolean dniValido(String dni){
        // Aplicar una expresión regular.
        boolean valido = true;
        if(dni.matches("^[0-9]{1,8}[A-Za-z]$")){
            // Comprobar que la letra es correcta
            int numero = Integer.parseInt(dni.substring(0, dni.length()-1));
            int resto = numero % 23;
            char letra = dni.charAt(dni.toUpperCase().length()-1);
            if (letra != letras.charAt(resto)) {
                valido = false;
            }
        }
        return valido;
    }

}
