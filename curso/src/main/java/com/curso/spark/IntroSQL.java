package com.curso.spark;

import org.apache.spark.sql.SparkSession;

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

        // Paso 3: TRABAJAR CON LOS DATOS (SQL)
        // Paso 4: Dejo el resultado en algún sitio

        // Paso 5: Cerrar la conexión
        conexion.close();
    }

}
