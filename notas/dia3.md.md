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