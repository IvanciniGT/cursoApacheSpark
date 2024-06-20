
# Medición de tiempos en JAVA / Rendimiento de código

Nosotros escribimos código en lenguaje JAVA: .java
Y ese código lo compilamos, para dar lugar a fichero .class, que contienen
código escrito en otro lenguaje, llamado byte-code.
Posteriormente, ejecutamos el código en lenguaje byte-code...
Y sabe nuestro SO el lenguaje byte-code? NO
Y por ello en tiempo de ejecución ese código hay que ~compilarlo~ al lenguaje del SO que esté usando (len. máquina)... Ese proceso, al hacer en tiempo de ejecución no lo denominamos compilación sino
INTERPRETACION.
Esa interpretación es llevada a cabo por EL JIT de la JVM.
El JIT (Just In Time Compile) es un compilador que se encarga de traducir el código byte-code a lenguaje máquina en tiempo de ejecución.
Y eso tiene pinta que va a hacer a nuestra aplicación más LENTA que una aplicación que ya esté pre compilada para el SO que esté usando (por ejemplo que si la escribo en C o C++).
Y por ello, en Java 1.1, las aplicaciones iban como el puto culo de lentas en JAVA...
Pero todo cambio en Java 1.2, cuando en el JIT de la JVM se incluyo una funcionalidad llamada "HotSpot", que básicamente es una caché de código compilado.
Según vamos ejecutando código byte-code, el JIT va compilando ese código a lenguaje máquina y lo va guardando en la caché de código compilado.
Cuando ya tiene todo el código cacheado, la aplicación va a ir mucho más rápida que al principio, cuando ese código no estaba cacheado.
Por eso, cuando quiero medir el rendimiento de una app JAVA, necesito hacerlo a JIT CALIENTE, es decir, cuando ya tengo todo el código cacheado.
Necesito calentar el JIT (que se llene la cache de código compilado) antes de medir el rendimiento de la aplicación.

Quién lleva el código a la CPU?
    Quién ejecuta el código de mi programa?
    Quién lo recorre?
    UN HILO !!!! Un Thread de SO.

    Mi programa al ejecutarse, el SO abre un proceso para él, y dentro de ese proceso, el SO crea un hilo que se encarga de recorrer el código de mi programa.

    Cuántos HILOS tenemos abiertos en nuestro programa? 1: MAIN

---

Para que uso la memoria RAM en un programa?
- Datos de trabajo
- Cache de datos
- Código del programa
- Para mantener el stack de ejecución de hilos

Cuando tenéis una exception en JAVA... y veis por pantalla la exception: VOLCADO de la pila (stack) de ejecución del hijo que lanza la exception.

Un problema muy habitual, cuando montamos funciones recursivas es que puedo tener un desborde de la pila de ejecución de hilos... se me llena el espacio en RAM que tenía reservado...
   StackOverflow

---
# Introducción: Big Data, Apache Hadoop, Apache Spark

## Bigdata

Bigdata es un enfoque que uso para trabajar con datos... cuando las técnicas tradicionales me fallan.
Para trabajar?
- Analizar datos
- Almacenar datos
- Transmitir datos
- Transformar datos


Esto le pasó a Google, que fué quien inició el mundo del bigdata: 
    Producto
        - MapReduce: Forma de enfrentarse a la creación de programas (algoritmos) 
        - GFS (Google File System): Sistema de archivos distribuido

> Ejemplo 1: Quiero guardar los productos que voy a comprar en el Mercadona: Producto x cantidad

- Bloc de notas: Ahora... eso está bien si voy a comprar 200 productos
- Excel... me sirve hasta 100k productos
- MySQL... me sirve hasta 4M de productos
- Y si tengo 25 millones de productos? El MySQL se va a hacer caquita! No pasa nada: Ms SQL Server
- Y si tengo 250 millones de productos? El Ms SQL Server se va a hacer caquita! No pasa nada: Oracle
    Oracle Database Server x Exadata
- Y si tengo 1billon de datos? El Oracle... y su exadata se va a hacer caquita!
- Y entonces... ahora que?

> Ejemplo 2: Tengo un picho USB de 16Gbs... recien sacao de la caja.
Tengo un amigo, que tiene un primo que tiene un tio que descargó una peli de internet . Ocupa 5Gbs
Puedo guardarlo en ese pincho? Depende sl sistema de archivos: 
    - FAT 16: No (tamaño máximo de archivo 2Gbs)
    - No pasa nada.. NTFS, ext4, zfs... me sirven
Y si tengo un archivo de 1 eB (exaByte)? Ahora qué?
- Y entonces... ahora que?

> Ejemplo 3: ClashRoyale

En el clash teníamos un modo 2v2.
Cada movimiento que yo hago... al final tiene que llegar a los 3 teléfonos de mi compañero/contrincantes... es un mensaje que hay quien transmitir.
Claro... en un segundo yo hago 2 movimientos: 6 mensajes en un segundo
Pero somos 4 jugando: 24 mensajes en un segundo
Si en un momento hay 50.000 partidas = 1.200.000 mensajes en un segundo
Qué servidor aguanta eso? No hay servidor que aguante eso... y si lo hay... es carísimo!
- Y entonces... ahora que?

Ahora me voy a un enfoque bigdata:
- HARDWARE: 
    - Compro un montón de servidores de mierda (commodity hardware)
    - Los conecto en red
- SOFTWARE:
    - Los configuro para que trabajen juntos... como si fueran 1 solo
    Necesito un software que cuando llegue un trabajo, lo reparta entre todas esas máquinas de mierda
        MAP-REDUCE:
      - Y use la potencia de cálculo de todas ellas para hacer el trabajo
      - Y la RAM de todas ellas para almacenar los datos de trabajo
        SISTEMA DE ARCHIVOS DISTRIBUIDO: 
      - Y los HDD de todas ellas para almacenar los datos persistentemente
          - Para esto último, necesito un sistema de archivos que me permita almacenar los datos de forma distribuida
Y google montón una implementación de todo esto: 
    - GFS       \
    - MapReduce / BigTable (propietario de Google)... que sigue... de pago

Pero Google tuvo a bien publicar unos papers explicando:
- Cómo funciona GFS
- Cómo funciona MapReduce

Y llegó un hombrecillo y monto una implementación opensource de todo esto... y le puso de nombre el nombre del elefantito de peluche de su hija: HADOOP... hoy en día gestionado por la fundación APACHE:
- Apache Hadoop:
  - HDFS: Hadoop Distributed File System (basado en el GFS de Google)
  - Un motor de procesamiento MAP-REDUCE (basado en el MapReduce de Google)
Apache Hadoop es el equivalente a un SO para bigdata:
    - HDFS: Sistema de archivos
    - Controla la ejecución de programas(procesos) en un cluster de servidores, haciendo uso de la potencia de cálculo de todos ellos (CPU, RAM)
Ahora bien... para que sirve per se Hadoop... pa' na'... igual que Windows.
Lo que aporta valor son los programas que monto sobre ese SO. Ahora bien... el SO me da una serie de herramientas para montar esos programas.
Y hay un huevo de programas pensados para funcionar sobre un cluster de Apache Hadoop:
- BBDD: Apache HBase, Apache Hive, Apache Cassandra
- ML: Apache Mahout
- Sistemas de mensajería: Apache Kafka
- Transformación de datos: Apache Spark, Apache Storm

Pero había un problemilla con la implementación de MapReduce de Hadoop... era lenta... de cojones...
ya que toda operación... y los datos... al distribuirse entre las máquinas del cluster se guarda continuamente en HDD (antes de enviarse a una máquina y cuando la máquina lo recibía)

SERVIDOR 100.000 datos ---> HDD ---->10.000 datos ---> red ---> Servidor1:  10.000 datos --> HDD
                                ---->10.000 datos ---> red ---> Servidor2:  10.000 datos --> HDD
                                ---->10.000 datos ---> red ---> Servidor3:  10.000 datos --> HDD
                                ---->10.000 datos ---> red ---> Servidor10: 10.000 datos --> HDD

Eso se hacía ( y se hace en Apache Hadoop) para evitar pérdidas de datos.
Si un servidor se cae, que no pierda los datos.
Pero a su vez hacía (y hace) que sea lento de cojones.

Y en un momento dado, surge una reimplementación de MapReduce, sin ese planteamiento.. con otro planteamiento distinto... que consigue asegurar la supervivencia en caso de desastre de los conjuntos de datos distribuidos... usando solo MEMORIA RAM!
El rendimiento BRUTAL: Apache Spark

En Spark el concepto principal es el concepto de RDD... que es una colección de datos (distribuida y tolerante a fallos) preparada para soportar trabajos en paralelo, usando programación funcional MAP-REDUCE... Me suena esto????
> colección de datos preparada para soportar trabajos en paralelo, usando programación funcional MAP-REDUCE

Un RDD de Apache Spark es el equivalente a los Stream de JAVA.
Solo que a los RDD cuando se les aplica una función de reducción, la ejecución no se hace en la máquina local... usando lo recursos de CPU/RAM de la máquina local...
Sino que se ejecuta en un cluster de máquinas de mierda... haciendo uso de la CPU/RAM de todas esas máquinas de mierda.

En los programas que hemos hecho, bastan con cambiar la palabra Stream, por RDD... y ya tenemos un programa que se ejecuta en un cluster de Apache Spark (montado sobre un cluster de Apache Hadoop)

Apache Spark se encarga de todo!
Solo tengo que configurar el algoritmo MAP REDUCE, y él se encarga de distribuirlo entre las máquinas, recolectar los resultados parciales y consolidarlos en un resultado final.

---

JAVA lo creó una compañía llamada Sun Microsystems... que era famosa (y ganaba la pasta?) por hacer hardware. Tenía sus propios servidores, con su propia arquitectura de microprocesadores, llamada SPARC. Para esas máquinas también desarrolló un SO, llamado Solaris.
De eso ganaba pasta... por un tubo!
En paralelo Sun fabricaba software... y era una empresa amada en el mundo del desarrollo.
Fue el precursor de las formas de trabajo que hoy en día continua Red Hat: 
- Productos Opensource, en algunos casos con versiones de Pago
  - OpenOffice -> StarOffice
  - Java

En un momento dado Oracle compra sun microsystems (Está fué la muerte de JAVA).
Oracle no es la querida empresa que era Sun... Oracle es una empresa caracterizada por destrozar todo producto de software que cae en sus manos. Porque solo le importa una cosa: SU BBDD... y hace bien... es su estrategia de negocio.
  - OpenOffice  -> LibreOffice
  - MySQL       -> MariaDB
  - Hudson      -> Jenkins
  - Java

---

Cluster: En ingles: SINONIMO DE GRUPO: GROUP
- Cluster de servidores: Grupo de servidores
- Cluster de proceso: Grupo de procesos
- Algoritmos de clusterización: Algoritmos de agrupación

---

# Entornos de producción:

- HA: High Availability: Alta disponibilidad
  Tratar de garantizar que el sistema estará en funcionamiento un tiempo que se haya acordado previamente de forma contractual. Lo solemos medir en 9s.
    90% = MIERDA GIGANTE = Un sistema puede estar offline 1 de cada 10 días: 36,5 días al año!                  | €
    99% = MIERDA = Un sistema puede estar offline 1 de cada 100 días: 3,65 días al año!                         | €€
        Peluquería de barrio... y tengo una web de reservas.                                                    | €€€€€
    99,9% = EMPEZAMOS a entendernos: Un sistema puede estar offline 1 de cada 1000 días: 8,76 horas al año!     | €€€€€€€€€€€
        Un banco, mercadona                                                                                     | €€€€€€€€€€€€€€€€€€€€€€
    99,99% = MUY BIEN: Un sistema puede estar offline 1 de cada 10.000 días: 52,56 minutos al año!              |
        Un hospital                                                                                             v

    No lo tomamos como un valor absoluto... sino como una indicación de la criticidad del servicio
- Escalabilidad
    Capacidad de ajustar la infraestructura en base a la carga de trabajo de cada momento

        App 1 : App del hospital o cualquier app departamental
            dia 1:    100 usuarios
            dia 100:  98 usuarios           NO HACE FALTA ESCALABILIDAD
            dia 1000: 102 usuarios
        
        App2: Wallapop.. vamos teniendo éxito
            dia 1:       100 usuarios
            dia 100:    1000 usuarios       Qué infra? qué servidor? ESCALADO VERTICAL: MAS MAQUINA (MAS RAM, MAS CPU)
            dia 1000: 10.000 usuarios

        App3: ESTO ES EL DIA DE HOY: INTERNET
            dia n:        100 usuarios
            dia n+1:  1000000 usuarios
            dia n+2:     1000 usuarios
            dia n+3: 10000000 usuarios

            Web telepi:
                00:00: usuarios         0
                01:00: usuarios         0
                09:00: usuarios         0       Qué infra monto? ESCALADO HORIZONTAL: MAS MAQUINAS
                11:00: usuarios         5           Vete metiendo máquinas en el cluster... o quita máquinas del cluster
                13:00: usuarios       100
                15:00: usuarios       500
                16:30: usuarios        10
                20:30: Madrid/barça    1M

            Quien me ofrece esa flexibilidad? a la hora contratar HARDWARE o DESCONTRATARLO?
                CLOUDS: AWS, AZURE, GCP, ORACLE CLOUD
            
            Y aquí y por esto es por lo que VOSOTROS usareis Apache SPARK. Lo ejecuto a las 01:00 am

        En la mayor parte de los casos, hoy en día, usamos enfoques BIGDATA sin tener problemas TECNICOS para usar enfoques más tradicionales... Lo que pasa es que mediante enfoques BIGDATA me ahorro pasta en infra!

---

Para desarrollo con Spark, necesitamos un cluster, donde poder hacer las pruebas
Spark, cuando lo uso para desarrollo, me autoconfigura un cluster de spark en mi máquina, con 1 solo nodo (mierdaservidor)

---

# JAVA 

Como lenguaje de programación ES UNA PUTA MIERDA !
Tiene una cantidad de cagadas en sintaxis... aberrante!
Eso si... La arquitectura de trabajo en java ... y la arquitectura de la JVM es brutal!!!

Ese hecho se juntó con la compra de Sun Microsystems por parte de Oracle... y la muerte de JAVA.
JAVA solo hay una cosa que lo mantiene vivo... y para un uso muy concreto: BACKEND (SPRING/SPRINGBOOT)

DESKTOP JAVA? NI DE COÑA! Antes si: Swing, AWT
SOFTWARE EMBARCADO? NI DE COÑA! Antes si: J2Me
Apps Android? NI DE COÑA: KOTLIN
FRONTEND WEB: Antes si: JSPs... HOY NI DE COÑA: JS


KOTLIN es un lenguaje donde escribo ficheros .kt -> .class -> .jar
Un lenguaje por cierto, pensado por la gente que más sabe en el mundo de grama de lenguajes de programación: JetBrains (IntelliJ IDEA)
Y sale un lenguaje INCREIBLE... 3 pantallas de JAVA las puedo dejar en 3 lineas de código en KOTLIN (sin exagerar)

Pero no fué el UNICO. En el mundo del bigdata crearon su propio lenguaje SCALA
    .sc -> .class -> .jar

Tanto SCALA como KOTLIN son lenguajes alternativos a JAVA... para compilar a byte-code... y que se ejecutan en la JVM.
---

Java 1.1: 1997
Java 1.2: 1998
Java 1.3: 2000
Java 1.4: 2002
Java 1.5: 2004
Java 1.6: 2006
    *** 5 años?? qué ha pasao?
    Oracle compra Sun Microsystems... Y a Oracle JAVA le importa una mierda... solo le importa su BBDD
    La gente empezó a pirarse de JAVA -> C#
Java 1.7: 2011
    *** 3 años?? qué ha pasao?
Java 1.8: 2014
    Aquí vino la puntilla:
    Oracle Anuncia que empezará a cobrar por JVM:
    - Usuarios particulares: 25$ por JVM/año
    - Empresas: 50$ por core/mes
    - Google (había elegido JAVA como base para todo el desarrollo Android)
      - JVM -> Especificación -> OpenJDK, Eclipse Temurin, Amazon Corretto
      - J2EE -> Colección de estándares(JDBC, JMS, JPA) -> Donado a una fundación y se hiciera OS: Jakarta EE
      - Forzar a que se saquen 2 versiones al año de JAVA
      - PERO AUN ASI GOOGLE NO PERDONÓ... ni OLVIDÓ
        - Encargó KOTLIN
        - En ese momento una mierda gigante de lenguaje que existía desde hacía muchos años: Mocha -> JS
          cae en manos de la ECMA y comienzan a generar un estandar del lenguaje y evolucionarlo: ECMA Script ...
          que hoy en día seguimos llamado por cariño: JS... pero sun nombre real es ECMA Script 
        - Y google arranco el motoro de procesamiento de JS de su navegador CHROMIUM (En el que está basado Chrome, Edge, Opera, Brave, Vivaldi, etc) y lo convirtió en un programa independiente... De forma que JS dejase de estar atado a un navegador... y se pudiese ejecutar en cualquier sitio: NODE.JS (el equivalente a la JVM de JS)
    *** 3 años?? qué ha pasao?
    11 años para 3 versiones de JAVA... No cualquieras 11 años... Los 11 años donde explota el mundo INTERNET, APPS, BIGDATA, CLOUDS
Java   9: 2017
    6 meses... qué ha pasao?
Java  10: 2018
Java  11: 2018
Java  12: 2019
Java  13: 2019
Java  14: 2020
Java  15: 2020
Java  16: 2021
Java  17: 2021
Java  18: 2022
Java  19: 2022
Java  20: 2023
Java  21: 2023
Java  22: 2024
Java  23: 2024