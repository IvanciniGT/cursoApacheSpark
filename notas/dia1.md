
# Apache Spark

Es un motor de procesamiento MAP REDUCE que corre sobre un cluster Apache Hadoop.
Provee APIs para distintos lenguajes de programación como Java, Scala, Python y R.

Para qué se usa? Transformaciones de datos
En las empresas montamos muchas ETLs.

Una ETL es un tipo de programa (SCRIPT) que se encarga de extraer datos de una fuente, transformarlos y cargarlos en un destino.

En los entornos de producción tenemos  y aplicaciones que van capturando /generando datos que se almacenan en BBDDs.
Pero según pasa el tiempo, hay datos que se quedan muertos... no me son interesantes en los entornos de producción.

En las empresas creamos lo que denominamos un DATA LAKE, que es un repositorio de información en crudo (que no ha sido muy procesada) que extraemos de las BBDD de producción.

En esos datalakes tenemos datos de años atrás...
En un momento dado, podemos querer hacer algo con esos datos: Business Intelligence, Machine Learning, Data Mining, etc.
Para ello, extraemos los datos de los datalakes, los transformamos y los cargamos en un datawarehouse.
En un datawarehouse, los datos están limpios, procesados y estructurados, para un uso concreto (BI, ML, DM, etc.)

        BBDDs de PRODUCCION --ETLs--> DATA LAKE --ETLs--> DATA WAREHOUSE

ETL: Extract, Transform, Load
Aquí hay variantes: TEL, ELT, ETLT, TELT, TETL

Hay un montón de herramientas que nos permiten montar ETLs: Microsoft SSIS, SpringBatch, Apache Spark

En concreto, Apache Spark se centra en la T de la ETL: Transform

Pero... para poder trabajar con Apache Spark, primero necesitamos aprender / dominar la programación funcional.
Todo lo que hagamos en Apache Spark será mediante programación funcional.


---

# Qué vamos a hacer en la formación:

√ Entender el concepto de programación funcional, y cómo se usa en Java
- Entender el concepto de programación MAP REDUCE, y cómo se usa en Java
- Introducción: Big Data, Apache Hadoop, Apache Spark
- Llevarnos el trabajo que tengamos en JAVA (MAP REDUCE) a Apache Spark
- Aprenderemos otras formas de trabajo disponibles en Apache Spark
- Hablaremos de formatos de almacenamiento de datos: Parquet, Avro.

---

# Programación Funcional

La programación funcional es otro paradigma de programación.
Un paradigma es una forma de usar un lenguaje de programación, para expresar/simbolizar algo que quiero transmitir.
De hecho no es un concreto propio de los lenguajes de programación.
Los humanos, en nuestros lenguajes naturales (español, inglés...) también usamos los lenguajes de formas diferentes.

    Felipe, pon una silla debajo de la ventana.         IMPERATIVO
    Felipe, debajo de la ventana ha de haber una silla. DECLARATIVO (Spring)

En los lenguajes de programación usamos distintos paradigmas:
- Imperativo                Cuando damos instrucciones de forma secuencial
                            En ocasiones queremos romper esa secuencialidad, y usamos palabras especiales para controlar el flujo:
                                IF, ELSE, FOR, WHILE, SWITCH, BREAK, CONTINUE, RETURN
- Procedural                Cuando el lenguaje me permite agrupar instrucciones en procedimientos/funciones/métodos/rutinas
                            Y ejecutarlos posteriormente.
                            Este tiene su gracia:
                            - Me permite generar código más estructurado
                            - Me ofrece un alto nivel de reutilización de código
- Funcional                 Cuando el lenguaje me permite que una variable apunte a una función.... para posteriormente ejecutar 
                            esa función desde la variable. 
                            Con la programación funcional, el tema no es lo que es... que es sencillo de entender.
                            La clave es lo que puedo comenzar a hacer cuando el lenguaje me permite eso:
                            - Puedo empezar a crear funciones que acepten funciones como argumentos
                            - Puedo empezar a crear funciones que devuelvan funciones como resultado
                            Y AQUI ES CUANDO EL CEREBRO EXPLOTA !!!!!! 
- Orientado a objetos       Cuando el lenguaje me permite definir mis propios tipos de datos, con sus propiedades y métodos
                            Todo lenguaje manipula datos. Y esos datos tienen una naturaleza (son de un tipo):
                                
                                            Propiedades/Attributos      Métodos
                                TEXTOS      Secuencia de caracteres     .toUpperCase(), .toLowerCase(), .length()
                                FECHAS      Dia / Mes / Año             .caesEnBisiesto(), diaDeLaSemana(), caesEnFinDeSemana()
                                LISTAS      Secuencia de elementos      .get(), .add(), .remove(), .size()
                                ---
                                USUARIO     Nombre, Apellidos, Edad     .eresMayorDeEdad()
- Declarativo
- ...
---

# Tipado estático vs tipado dinámico

En tipado dinamico las variables no tienen tipo de dato.
En tipado estático las variables tienen tipo de dato.... y solo pueden apuntar a datos de su tipo.

---
# Programación MAP-REDUCE

Es una forma de escribir programas que necesitan tratar con colecciones de datos.
Originalmente se plantea para trabajar con GRANDES VOLUMENES DE DATOS sobre los que quiero aplicar programación paralela.
Quién crea esto (esta forma de escribir programas) es Google, para poder indexar la web.
Y crearon esta forma de escribir programas y lo publicaron en un paper: "MapReduce: Simplified Data Processing on Large Clusters"

Desde entonces, en casi todos los lenguajes de programación, se ha implementado una forma de programación MAP REDUCE.

MAP REDUCE se basa en programación funcional: EXCLUSIVAMENTE.
En JAVA, hasta 1.8, no teníamos programación funcional.... no había map reduce.
En cuanto se introduce la programación funcional en JAVA, se introduce la programación MAP REDUCE.
En el caso de JAVA, se consigue mediante una clase llamada Stream.

La clase Stream es una clase que nos permite trabajar con colecciones de datos aplicando operaciones de MAP REDUCE.

Un Stream es como un List o un Set, pero mientras que el List o el Set lo que tienen son funciones para trabajar con datos discretos:
- Dame un dato en una posición concreta
- Elimínalo
- Actualiza el dato en una posición concreta
Los Stream lo que tienen es funciones que aplican sobre TODOS los datos de la colección.

Ventajas de MapReduce:
- Facilidad de programación (una vez le cogemos el punto a la programación funcional)
- Facilidad de mantenimiento
- Facilidad para comentar el código
- Facilidad para paralelizar el código
- Rendimiento brutal comparado con técnicas de programación secuencial

# De qué va?

Al crear un algoritmo Map Reduce, siempre partiré de una colección de datos, que soporte operaciones MAP y REDUCE: Stream.

Cualquier colección de Java (List, Set, Map...) puedo convertirlo en un Stream mediante el método stream().

Un proceso/algoritmo MAP-REDUCE tiene la siguiente pinta:

    COLECCION DE PARTIDA     --> MAP ---> MAP ---> MAP ---> MAP ----> REDUCE
    QUE SOPORTE MAP-REDUCE
        STREAM<T>

Aplicando este tipo de algoritmo podemos resolver de forma muy eficaz y muy sencilla problemas de procesamiento de datos complejos.... muy complejos.

## Funciones tipo MAP

Una función tipo MAP es una función que al aplicarse sobre un conjunto de datos que soporte MAP-REDUCE, me devuelve otro conjunto de datos que soporte MAP-REDUCE.

    STREAM f(STREAM) : ENTONCES f es una función tipo MAP

Las funciones tipo MAP tiene una característica muy importante: 
Se ejecutan en modo PEREZOSO (LAZY)
Lo que implica que realmente no se ejecutan hasta que no se necesita el resultado.

Ejemplos de funciones tipo map:
    - MAP: Aplica una función (que llamamos función de transformación) a cada elemento de la colección... para transformarlo en otro elemento.... Y recopilar todos los resultados en una nueva colección, que es lo que devuelve.

        COLECCION INICIAL       MAP                 COLECCION FINAL
            1               numero-> numero * 2         2
            2                                           4
            3                                           6
            4                                           8
        ** NOTA: La función que espera recibir la función MAP es de tipo: Function<T,R> donde T es el tipo de dato de la colección inicial y R es el tipo de dato de la colección final.

    - FLAT MAP = MAP + FLATTEN
      Aplica sobre cada dato la función de transformación suministrada ... como si fuera un MAP

      Pero... en este caso, FLATMAP requiere una función de transformación que devuelva un STREAM

      Una vez aplicado el MAP, se juntan todos los Streams en uno solo
        Stream<Stream<T>> -> Stream<T>

    - FILTER: Aplica un Predicado(una función que recibe un dato y devuelve true o false)
              sobre cada elemento de la colección inicial, 
              Y genera una colección final que solo contendrá los elementos para los que la 
              función de predicado devuelva TRUE 

        COLECCION INICIAL       FILTER                              COLECCION FINAL
            1               numero-> numero % 2 == 0        false           
            2                                               true            2
            3                                               false
            4                                               true            4
    - SORT: Ordena los elementos de la colección inicial según un criterio que le pasamos como argumento.
    - LIMIT: Limita el número de elementos de la colección final a un número que le pasamos como argumento.

## Funciones tipo REDUCE

Una función tipo REDUCE es una función que al aplicarse sobre un conjunto de datos que soporte MAP-REDUCE, me devuelve algo que no es un conjunto de datos que soporte MAP-REDUCE.

    OBJECT f(STREAM) : ENTONCES f es una función tipo REDUCE
        con OBJECT != STREAM

    T f(STREAM) : ENTONCES f es una función tipo REDUCE

Las funciones tipo REDUCE tienen una característica muy importante:
Se ejecutan en modo ANSIOSO (EAGER)
Lo que implica que realmente se ejecutan en cuanto se invoca la función.

En un algoritmo MAP-REDUCE, siempre cabaré con una función tipo REDUCE al final...
QUE ES LA QUE DESENCADENA LA EJECUCION DE LOS TRABAJOS MAP.
Vamos a tener un efecto DOMINO:

    Colección -> MAP1 -> MAP2 -> MAP3 -> REDUCE
                El reduce se ejecuta y para ello necesita el resultado de MAP3
                    Y entonces se ejecuta MAP3
                        Y para ello necesita el resultado de MAP2
                            Y entonces se ejecuta MAP2
                                Y para ello necesita el resultado de MAP1
                                    Y entonces se ejecuta MAP1

Los motores de procesamiento MAP-REDUCE son capaces de optimizar la ejecución de los trabajos MAP y decidir la mejor estrategia de ejecución posible en cada escenario.

Ejemplos de funciones tipo reduce:
- forEach: Aplica una función a cada elemento de la colección inicial.
- collect: Transforma un Stream en un Colecction... el tipo de collection que yo le diga:
      unStream.collect(Collectors.toList())
      unStream.collect(Collectors.toSet())
      unStream.collect(Collectors.toMap()) 


      Colección -> Stream -> Colección
            .stream()    .collect(Collectors.toList())
                         .collect(Collectors.toSet())

> Ejemplo

Fichero de texto con palabras en Español: 660k palabras
Me va a dar además otra palabra.
Voy a sacar las palabras similares a esa palabra (CORRECTOR ORTOGRÁFICO)

    manana??? -> mañana, manzana, banana

Para eso tendremos que:
- Ir palabra a palabra de las 660k palabras para ver si se parecen o no a la mia...
- Más bien para ver cuánto se parece (función de LEVENSHTEIN)
    manana -> mañana : DISTANCIA DE LEVENSHTEIN = 1
    manana -> manzana: DISTANCIA DE LEVENSHTEIN = 1
    manana -> manzano: DISTANCIA DE LEVENSHTEIN = 2

---

# MAVEN

Es una herramienta de automatización de trabajos sobre proyectos de desarrollo de software (principalmente JAVA).
Me permite automatizar tareas (GOALS):
- compile
- test-compile
- test
- package
- install
- clean
- sonar:sonar
- javadoc:javadoc
- spring-boot:run

Las tareas realmente no las ejecuta maven... sino PLUGINS.
Maven viene con unos cuantos plugins por defecto, pero yo puedo añadir los que quiera.

La estructura típica de proyecto maven es:
    proyecto/
        src/
            main/
                java/
                resources/
            test/
                java/
                resources/
        target/
            classes/
            test-classes/
            proyecto-1.0-SNAPSHOT.jar
        pom.xml             

# POM.XML

Es el archivo de configuración de maven para mi proyecto....
En él defino:
- Las coordenadas de mi proyecto (groupId, artifactId, version)
- Los plugins que voy a usar
- Configuraciones para esos plugins
- Dependencias que voy a usar

GOALS TIPICOS: 
- compile           Compila los .java que tengo en src/main/java y deja los .class en target/classes
                    Copia lo que tengo en src/main/resources en target/classes
    ^
- test-compile      Compila los .java que tengo en src/test/java y deja los .class en target/test-classes
                    Copia lo que tengo en src/test/resources en target/test-classes
    ^
- test              Ejecuta los tests que tengo en target/test-classes
    ^
- package           Empaqueta mi proyecto en un .jar o en un .war.. o en un .ear
                    Si es un jar: Lo que hace es meter en un ZIP la carpeta target/classes, le cambia la extensión a .jar y lo deja en target
    ^
- install           Copia mi empaquetado (el .jar) en mi carpeta .m2, de forma que mi proyecto pueda ser usado como dependencia en otros proyectos

clean               Borra la carpeta target

# Carpeta .m2 de maven

Una carpeta que crea maven en automático dentro de mi carpeta de usuario:
c:\Users\miUsuario\.m2

En esa carpeta es donde maven busca las dependencias que necesita para compilar mi proyecto.
Si una dependencia no la encuentra, la descarga de internet y la guarda en esa carpeta.

---
## Planteo el map Reduce:

COLECCION INICIAL
    Fiesta en la playa #BeachParty#SunFun#CacaSun, bronceado extremo!
    #CaféEnLaMañana con un libro... #Relax pero mi café está frío :(
    Noche de estudio #StudyHard#NoSleep, ¿quién inventó los exámenes?
                        vvvvv
NECESITO PLANTEAR OPERACIONES MAP para pasar de la colección inicial a la colección final
                        vvvvv
COLECCION FINAL
    beachparty
    sunfun
    caféenlamañana
    relax
    studyhard

Y teneos 1 linea de código para hacer el trabajo!

De cada tweet quiero sacar los hashtags?.. COMO???
    Qué es un hashtag? Una palabra que comienza por #
    Tengo palabras? NO... tengo tweets... tengo líneas de texto
    Cada linea la necesito convertir en palabras:
        texto.split( ) -> Array[] con tokens separados por espacios
                    ^ REGEX

    Array<String> -> Stream<String>

(Fiesta, en, la, playa, #BeachParty, #SunFun, #CacaSun, bronceado, extremo) 
   vvvv
    Fiesta
    en
    la
    playa
    #BeachParty
    #SunFun
    #CacaSun
    bronceado
    extremo
    vvvv
    #BeachParty
    #SunFun
    #CacaSun
    vvvv
    beachparty
    sunfun
    cacasun
    vvvv
    beachparty
    sunfun


---

# REGEX

Trabajar con expresiones regulares es importante. Nos permite hacer operaciones muy potentes sobre textos.
El concepto básico en regex es el de patrón.
Defino un patrón... y entonces puedo:
- Dividir un texto por ese patrón
- Buscar todas las ocurrencias de ese patrón en un texto
- Verificar si un texto cumple con un patrón
Los patrones los definimos en un lenguaje específico para ello.... que se definió por primera vez en PERL. De ese lenguaje se han hecho implementaciones en casi todos los lenguajes de programación.

Un patrón es: Una secuencia de SUBPATRONES
un SUBPATRON es: una secuencia de caracteres seguida de un modificador de número de ocurrencia.

> Texto de ejemplo.: Me llamo Iván... Tengo 47 palos... Mi teléfono es 666 666 666

Secuencias de caracteres en perl:       SIGNIFICADO
    - llamo                                 coincidencia exacta de ese texto en el texto original
        Me llamo Iván... Tengo 47 palos... Mi teléfono es 666 666 666
           -----

    - [abc]                                 coincidencia de una de las letras a, b o c
        Me llamo Iván... Tengo 47 palos... Mi teléfono es 666 666 666
             -                     -

    - [a-z]                                 coincidencia de caracteres en ese rango (ASCII) : a-z
        Me llamo Iván... Tengo 47 palos... Mi teléfono es 666 666 666
         - -----  - -     ----    -----     - --- ---- --
    
    - [0-9]                                 coincidencia de caracteres en ese rango (ASCII) : 0-9
        Me llamo Iván... Tengo 47 palos... Mi teléfono es 666 666 666
                               --                         --- --- ---   11 veces encontrado el patrón
    - .                                     cualquier carácter
        Me llamo Iván... Tengo 47 palos... Mi teléfono es 666 666 666 --- 50 veces aparece cualquier caracter. Cada caractes es Cualquier caracter
    - Escapar caracteres especiales: 
    - \. [.]                                   coincidencia de un punto
        Me llamo Iván... Tengo 47 palos... Mi teléfono es 666 666 666
                     ---               ---              6 veces aparece un punto

Modificadores de cantidad
    - No poner nada                         1 vez
    - ?                                     0 o 1 vez
    - +                                     1 o más veces
    - *                                     0 o más veces
    - {4}                                   4 veces
    - {4,7}                                 de 4 a 7 veces
    - {7,}                                  7 o más veces

Caracteres especiales:
    - ^                                     comienza por
    - $                                     termina por
    - ()                                    agrupar subpatrones
    - |                                     OR

Cuando necesite trabajar con regex : https://regex101.com/