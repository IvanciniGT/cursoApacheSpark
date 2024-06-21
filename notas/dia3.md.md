# Aprenderemos otras formas de trabajo disponibles en Apache Spark

En Spark se crea una nueva librería por la dificultad que presenta la librería básica (core) y en general el uso de patrones de programación MAP REDUCE.
La librería spark-sql. La usamos muchísimo. De hecho es la forma preferida hoy en día de trabajar con Spark...
Aunque no siempre es posible usarla, dependendiendo de los trabajos que vaya a realizar.
Lo que ofrece es una capa por encima de la librería core, que nos permite usar sintaxis SQL para trabajar con datos.
Si las transformaciones que quiero hacer encajan con sintaxis SQL, GUAY! Si no, no puedo usarla.

Al trabajar con SparkSQL, NOSOTROS no vamos a gestionar ya objetos RDD, sino que vamos a trabajar con objetos DataFrame.
Un DataFrame básicamente es un RDD<Row>, siendo Row un nuevo tipo de dato a nuestra disposición.
Lo que tenemos es una tabla... al más puro estilo EXCEL o BBDD relacional.
Pero eso subyace en la librería... para nosotros queda muy oculto.
Sobre esos Dataframes trabajaremos con SQL. Y la librería SparkSQL se encargará de transformar nuestras sentencias SQL en operaciones MAP REDUCE sobre RDDs.
Cuando usamos esta librería, todo cambia, incluso la forma de conectarnos a Spark... tendremos objetos nuevos para establecer la conexión.

---

Spark se basa en HADOOP, y por tanto, en el sistema de ficheros HDFS.
Hadoop está pensado para trabajar sobre máquinas POSIX.
Esto no es ninguna limitación. Siempre montamos los clusters de Hadoop sobre máquinas con SO GNU/Linux (RedHat, CentOS, Ubuntu, etc).

---


Nuestro programa se va a ejecutar en un cluster de Spark.
Nuestros archivos (que serán más o menos gordos), no los vamos a tener en nuestra máquina local. 
Los archivos los sacaré SIEMPRE de ubicaciones en RED... habitualmente usando el protocolo HDFS: hdfs://mi-cluster:9000/mi-archivo.txt
Cuando hayamos procesado ese archivo, el resultado lo volcaremos en otro archivo en HDFS.

Pero el procesamiento de los datos no lo hará un NODO (máquina) del cluster, sino que lo harán TODOS los nodos del cluster.
Cada nodo generará su archivo de salida. De forma que lo que guardaré será NO UN ARCHIVO... sino toda una colección de archivos.
Que guardaré en un directorio en HDFS.

---

# Qué era UNIX?

Un SO que desarrollaba la gente de los "lab Bell" de la americana AT&T.
Antiguamente los SO se licenciaban de forma distinta. AT&T licenciaba UNIX a grandes empresas/universidades, y estas a su vez lo modificaban, adaptándolo a su hardware, y lo vendían a sus clientes.
En un momento dado llego a haber más de 400 distribuciones de UNIX.
Muchas presentaban incompatibilidades entre ellas. De forma que un programa creado para una, podía no funcionar en otra.
Hubo que poner orden... y aparecieron 2 estandares: POSIS y SUS.

# Qué es UNIX?

Hoy en día, cuando nos referimos a un SO UNIX, nos referimos a un SO que cumple con esos estándares.
Hay muchos SO que cumplen con esos estándares:
- HP-UX (Unix®)
- Solaris (Unix®)
- AIX (Unix®)
- MacOS (Unix®)
Hay sistemas operativos que creemos que cumplen con los estándares... aunque no estamos seguros, ya que nunca se certificaron... NI LO HARÁN.. no hay ningún interés en ello:
- GNU/Linux
- BSD: FreeBSD, OpenBSD, NetBSD, etc.

# POSIX

Define entre otras cosa los comandos básicos con los que interacturar con el SO:
- ls
- cp
- mkdir
- rm

--- 
# Validar un DNI

Lo primero, compruebo que el DNI tenga un formato adecuado:
1-8 Números y una letra. ---> REGEX --->  ^[0-9]{1,8}[a-zA-Z]$
Lo segundo, validamos la letra:
- Calculamos el resto de dividir el número entre 23. (Operador módulo: %)

    2.300.001 | 23
              +-----------
            1  100.000
            ^
            El resto estará entre 0 y 22.
            El ministerio de interior da una tabla con las letras que corresponden a cada resto.
             1 -> R

             letras control = "TRWAGMYFPDXBNJZSQVHLCKE"

---

# Qué hace Spark?

Distribuir datos de una colección a distintos nodos... para que ellos ejecuten un trabajo. Y recopilar el resultado de esos trabajos.
Quiero validar 1M de DNIs de personas... Y tengo 10 máquinas... qué hace spark?
Manda a cada máquina unos 100.000 DNIs... y le pide a cada máquina que valide esos DNIs.

Realmente no parte los datos en 10 particiones... y a cada máquina le manda 1 de esas particiones.
Esto no sería óptimo?
- PROBLEMA 1: Cuando una máquina va por el 99.999 peta!
  Qué hace Spark? Le manda los 100.000 (que el maestro los sigue teniendo) a otro trabajador.
  Y los 99.999 que ya había procesado? ese tiempo invertido? A la basura!
  Me podría interesar en lugar de hacer 10 paquetes de 100.000, hacer 1000 paquetes de 1.000.
  Y si una máquina se jode como mucho pierdo el tiempo de procesamiento de 999 DNIs.
  Eso si... a más paquetes, más tráfico de red.. que ralentizará el proceso.
- PROBLEMA 2: Y si una de las máquinas del cluster (que no lo uso solo yo) está ocupada haciendo otra cosa?
  Tengo 9 máquinas que han acabado.. están echándose la siesta.. y yo esperando? Tiene sentido? NINGUNO
    Mejor parto el trabajo en mogollón de paquetes: 1000 paquetes de 1.000 DNIs.
    Y los trabajadores, que vayan sacando paquetes de la cola de trabajo.
    Cuando un trabajador acabe con un paquete, que coja otro de la cola.
    Y si una máquina está ocupada, pues que coja otro trabajador el paquete que le tocaría a esa máquina.
    Esto lo gestiona Spark en AUTOMATICO... siempre y cuando haya definido un número de particiones adecuado.

# Consideraciones a tener en cuenta:

- Otro cosita....
  En cada envío de trabajo (cada paquete), Spark manda al trabajado toda la info que necesita para hacer el trabajo.

- Spark no es una BBDD... y por ende, tengo que tener mucho cuidado con ciertas operaciones a la hora de trabajar especialmente con sintaxis SQL.

Imaginad que tengo un fichero con 1M de registros... y Otro fichero con otros 1M de registros.... y les quiero hacer un JOIN.

Que hace Spark?
Particionar los datos en paquetes.. qué datos? Los 2 ? NI DE COÑA ! Solo el primero. El segundo hay que mandarlo a todos los nodos. Y encima se va a mandar en cada paquete de trabajo

| personas               |
| id  | nombre | cp      |
|-----|--------|---------|
| 1   | Pepe   | 28001   |
| 2   | Juan   | 28002   |  NODO 1
| 3   | Maria  | 28003   |
|-----|--------|---------|
| 4   | Pedro  | 28001   |
| 5   | Ana    | 28002   |  NODO 2
| 6   | Luis   | 28003   |


| cp     | poblacion |
|--------|-----------|
| 28001  | Madrid    |
| 28002  | Barcelona |
| 28003  | Valencia  |

Para paliar un poco esto, podríamos hacer un BROADCAST de la tabla de poblaciones.
Esto lo que hace es que la tabla de poblaciones se envía a todos los nodos antes de empezar a trabajar.
Y los nodos mantienen esa tabla en memoria hasta que acabe el trabajo completo... evitando que se mande en cada paquete de trabajo.

- Las BBDD hacen JOINS MUY eficientes porque tiene INDICES de los campos de unión de las tablas.
  Qué es un índice? Una copia preordenada 
  en Spark no existe el concepto de ÍNDICE. Los datos nos se pueden tener preordenados. Y eso hace que el JOIN Sea mucho más ineficiente.

  No deberíamos intentar hacer joins de ese tipo en Spark. Siempre que podamos, haremos JOINs de tabla grande con tabla pequeña.
  Por eso el otro día os hablaba de ETLs, ETLTs, TETL

  A veces extraigo datos, los transformo con spark, los cargo en una BBDD y allí vuelvo a trabajar con ellos.

--

Cuando una aplicación se manda a un cluster, lo que enviamos en realidad es un JAR.
Cuando descargo Spark de la Web de Spark