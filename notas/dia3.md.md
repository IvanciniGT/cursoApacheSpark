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