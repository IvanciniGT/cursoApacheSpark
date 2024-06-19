package conceptos;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Stream;
import java.util.function.Function;
public class Colecciones {
    
    public static void main(String[] args) {
        // Crear un lista de enteros
        List<Integer> numeros = new ArrayList<>();
        numeros.add(1);
        numeros.add(2);
        numeros.add(3);
        numeros.add(4);
        
        // Vamos a iterar por la lista.
        // Pre java 1.5
        for (int i = 0; i < numeros.size(); i++) {
            System.out.println(numeros.get(i));
        }
        // Pre Java 1.8
        for (Integer numero : numeros) {
            System.out.println(numero);
        }
        // Desde java 1.8: // ESTO ES MUCHO MAS EFICIENTE QUE LO DE ARRIBA (va mucho más rápido)
        numeros.forEach( numero -> System.out.println(numero) );
        numeros.forEach( System.out::println );

        System.out.println("-----------");
        // Programación MAP-REDUCE
        Stream<Integer> miStream = numeros.stream();

        Stream<Integer> resultado = miStream.map(  dato -> dato * 2 );
        resultado.forEach( System.out::println );
        // En programa imperativa:
        List<Integer> resultado2 = new ArrayList<>();
        for (Integer numero : numeros) {
            resultado2.add(numero * 2);
        }
        for (Integer numero : resultado2) {
            System.out.println(numero);
        }
        // Forma simplificada
        Function<Integer, Integer> laFuncion = dato -> dato * 2;
        numeros.stream()                            // Para cada numero
                .map(  dato -> dato * 2 )            // Lo multiplico por 2
                .map(  dato -> dato * 3 )            // Lo multiplico por 3
                .map(  dato -> dato / 4 )            // Lo divido entre 4
                .map(  dato -> dato + 3 )            // Le sumo 3
                .map(  dato -> dato - 2 )            // Le resto 2
                .forEach( System.out::println );     // Lo saco por pantalla

        // Spark lo que me permite es ejecutar esos cálculos usando 10 JVM de 10 máquinas físicas distintas... y devolverme el resultado.
        // Y para pasar ese código a Spark solo tendré que cambiar 1 palabra

        // El problema no es aprender Spark... 
        // El problema es aprender a resolver problemas de forma funcional aplicando algoritmos MAP-REDUCE
        System.out.println("-----------");
        numeros.stream()    
               .filter( dato -> dato % 2 == 0 )   // Me quedo con los pares
               .sorted()                          // Los ordeno
               .limit(1)                  // Me quedo con los dos primeros
               .forEach( System.out::println  );  // Los saco por pantalla

        List<String> masNumeros = new ArrayList<>();
        masNumeros.add("2");
        masNumeros.add("1");
        masNumeros.add("6");
        masNumeros.add("4");
        masNumeros.add("3");
        masNumeros.add("5");

        masNumeros.stream()
                 .map( Integer::parseInt ) // Convierto los números en enteros
                 .sorted() // Los ordeno
                 .forEach( System.out::println ); // Los saco por pantalla
    }
}
