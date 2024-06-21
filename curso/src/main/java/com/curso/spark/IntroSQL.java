package com.curso.spark;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;

import static org.apache.spark.sql.functions.col;

import java.net.URI;
import java.net.URISyntaxException;

public class IntroSQL {

    public static void main(String[] args) throws URISyntaxException {
        // Paso 1: ABRIR CONEXION CON EL CLUSTER DE SPARK
        SparkSession conexion = SparkSession.builder()
                .appName("IntroSQL")
                .master("local[2]")
                .getOrCreate();

        // Paso 2: CARGAR LOS DATOS
        // Lo normal es estar leyendo datos de ficheros. Podría ser también de BBDD o de Kafka (menos frecuente)
        URI uriDelFichero = PalabrasSimilares.class.getClassLoader().getResource("personas.json").toURI();
        //Y el fichero lo va a leer directamente Spark
        Dataset<Row> misDatos = conexion.read().json(uriDelFichero.toString());
        // Tenemos un objeto Dataset, que representa lo que sería equivalente a una tabla de una BBDD.
        // Y como cualquier tabla de BBDD, tendrá un SCHEMA asociado, que indique la estructura de los datos
        misDatos.printSchema();
        // El esquema, Spark, lo infiere de los datos. En algún caso (raro) puede ser que necesite de ajustes manuales
        // Podremos acceder al Schema como un objeto JAVA para editarlo
        // misDatos.schema().fields()[0].

        // Paso 3: TRABAJAR CON LOS DATOS (SQL)
        misDatos.show();
        // En los Datasets tenemos funciones similares a las funciones SQL
        Dataset<Row> nombresYEdades = misDatos.select("nombre", "edad");
        nombresYEdades.show();
        Dataset<Row> nombresYEdades2 = misDatos.select(col("nombre"), col("edad").plus(1));
        nombresYEdades2.show();
        // Los col() me ofrecen funciones adicionales para trabajar con las columnas
        // Las sintaxis no se pueden mezclar. O trabajo con nombre, o trabajo con col(), pero no ambos a la vez
        misDatos.filter( col("edad").geq(18) ).select("nombre","apellidos").show();

        misDatos.groupBy("nombre")
                .sum("edad")
                .orderBy(col("sum(edad)").desc())
                .show();

        // Realmente la potencia de esta librería es que puedo hacer SQL de verdad
        // Para ello, necesito asociar un nombre a mi Dataset, que usar como nombre de tabla en SQL
        misDatos.createOrReplaceTempView("personas");
        // Y ahora puedo hacer SQL de verdad
        Dataset<Row> resultadoSQL = conexion.sql("SELECT nombre, sum(edad) as sumaEdades FROM personas GROUP BY nombre ORDER BY sumaEdades DESC");
        // Paso 4: Dejo el resultado en algún sitio
        resultadoSQL.show();
        resultadoSQL.repartition(2).write().mode("overwrite").json("resultadoSQL.json");
        // Quiero saber si los DNIS son válidos: Filtrar y quedarme con las personas que tengan un DNI Valido
        // Tengo en SQL una función para validar DNIS? NO
        // 2 estrategias entonces:
        // 1. Pasar los datos de un Dataset a un RDD y trabajar con funciones map
        JavaRDD<Row> misDatosValidadosComoRDD = misDatos.toJavaRDD() // Esto me da un RDD<Row>
                .filter( row -> {
                    int indiceDeColumnaQueTieneElDNI = row.fieldIndex("dni");
                    String dni = row.getString(indiceDeColumnaQueTieneElDNI);
                    boolean esValido = DNIUtils.dniValido(dni);
                    return esValido;
                } );// Esto me da un RDD<String> con los DNIS
        // Convierto de vuelta a Dataset<Row>
        Dataset<Row> misDatosValidados = conexion.createDataFrame(misDatosValidadosComoRDD, misDatos.schema());
        misDatosValidados.show();
        // 2. Crear una función SQL que valide los DNIS... Hacer que nuestra función en DNIUtils esté disponible en SQL
        conexion.udf().register("esValido", (String dni) -> DNIUtils.dniValido(dni) , DataTypes.BooleanType);
        conexion.sql("SELECT * FROM personas WHERE esValido(dni)").show();
        // Paso 5: Cerrar la conexión
        conexion.close();
    }

}
