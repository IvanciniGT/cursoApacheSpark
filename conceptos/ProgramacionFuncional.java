package conceptos;
// En java 1.8, que es cuando se añade al lenguaje JAVA el soporte para programación funcional,
// se añade un nuevo paquete al API de java: java.util.function, que está lleno de interfaces, 
// que me permiten apuntar a funciones... los llamamos interfaces funcionales.

import java.util.function.*;
// Function<T,R>    Me permite apuntar a una función que recibe un dato de tipo T y devuelve un dato de tipo R
    // BiFunction<T,U,R>  Me permite apuntar a una función que recibe dos datos de tipo T y U y devuelve un dato de tipo R
// Consumer<T>      Me permite apuntar a una función que recibe un dato de tipo T y no devuelve nada (void)
// Supplier<R>      Me permite apuntar a una función que no recibe nada y devuelve un dato de tipo R
// Predicate<T>     Me permite apuntar a una función que recibe un dato de tipo T y devuelve un booleano

public class ProgramacionFuncional {

    public static void saluda(String nombre){
        System.out.println("Hola " + nombre);
    }
    public static void generarSaludoFormal(String nombre){
        System.out.println("Buenos días " + nombre);
    }
    public static void generarSaludoInformal(String nombre){
        System.out.println("Qué pasa " + nombre);
    }
    public static void imprimirSaludo(Consumer<String> funcionGeneradoraDeSaludos, String nombre){
        funcionGeneradoraDeSaludos.accept(nombre);
    }
    public static double sumar(double a, double b){
        return a + b;
    }
    public static void main(String[] args){
        saluda("Pepe");
        Consumer<String> mi_variable = ProgramacionFuncional::saluda; // En JAVA 1.8 se añade además un nuevo operador al lenguaje ::, que me permite apuntar a una función
        mi_variable.accept("Juan"); // Ejecuta la función consumidora de String a la que apunta la variable para el argumento "Juan"
        imprimirSaludo(ProgramacionFuncional::generarSaludoFormal, "Juan");
        imprimirSaludo(ProgramacionFuncional::generarSaludoInformal, "Juan");
        BiFunction<Double, Double, Double> mi_operacion = ProgramacionFuncional::sumar;
        double resultado = mi_operacion.apply(2.7, 3.4);
        System.out.println("El resultado de la operación es: " + resultado);

        // En java 1.8 aparece un segundo operador: Operador FLECHA, que me permite definir expresiones lambda
        // Que es una expresión LAMBDA... que ante todo es una EXPRESION

        String saludo = "hola"; // STATEMENT: Declaración, Sentencia (Frase, Oración
        int numero = 5+6;       // Otro STATEMENT
                     /// EXPRESION: Un fragmento de código que devuelve un valor
        // Una expresión lambda es un trozo de código que devuelve un valor.... pero un valor muy especial:
        // La referencia a una función declarada dentro de la expresión, que son ANONIMAS.
        // Es una alternativa a la sintaxis tradicional de declarar funciones, que devuelve una referencia a la función declarada.
        Function<Double, Double> mi_funcion  = ProgramacionFuncional::doblar;
        System.out.println(mi_funcion.apply(5.0));
        // Alternativa
        Function<Double, Double> mi_funcion2 = (Double unNumero) -> { // Cada vez que veais una flecha: empezad diciendo: DECLARO UNA FUNCION....
            return unNumero * 2;
        }; 
        // Además de comerme el nombre, me como también el "public", 
        // dado que cualquiera que tenga una referencia a esta función, puede ejecutarla.
        // El "static" que me permite refenciar la función mediante el nombre de la clase, también me lo como.
        // Y hay otra cosa que me como? El tipo devuelto... y entonces cómo sabe JAVA lo que devuelve esa función? Se infiere de la operación.
        System.out.println(mi_funcion2.apply(5.0));
        // JAVA puede inferir muchas cosas de una expresión lambda.
        Function<Double, Double> mi_funcion3 = (unNumero) -> { 
                                                                return unNumero * 2;
                                                            }; 
        System.out.println(mi_funcion3.apply(5.0));
        Function<Double, Double> mi_funcion4 = (unNumero) -> unNumero * 2; 
        System.out.println(mi_funcion4.apply(5.0));

        // Tradicionalmente (en paradigma procedural) hemos declarado funciones para:
        // 1. Reutilizar código
        // 2. Organizar código
        // Pero al usar programación funcional, en ocasiones declaramos funciones para:
        // 3. Pasarlas como argumentos a otras funciones, porque esas otras funciones esperan una función como argumento.
        // Y hay veces que no voy a reutilizar la función, y que de hecho, definirlas en otro sitio me complica la lectura del código.
        // Y entonces uso una expresión lambda, que me permite definir una función anónima, en el mismo sitio donde la necesito.
    }

    public static double doblar(double numero){
        return numero * 2;
    }

}
