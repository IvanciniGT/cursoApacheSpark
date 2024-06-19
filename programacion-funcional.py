
# Una variable en PY, JAVA, JS, es una referencia a un dato que tengo en RAM

numero = 7

texto = "Hola" 
    # ->
# Qué hace esta línea de código?
#   "Hola"       - Crea un dato de tipo string con el valor "Hola" y lo guarda en memoria RAM
#   texto        - Crea una variable, con el nombre "texto"
#   =            - Asigna la variable "texto" al valor "Hola"

texto = "Adios"
# Qué hace esta línea de código?
#   "Adios"      - Crea un dato de tipo string con el valor "Adios" y lo guarda en memoria RAM
#                  Donde se guarda? En el mismo sitio donde estaba "Hola" o en otro?
#                   EN OTRO SITIO. En este momento tengo 2 objetos de tipo string en RAM
#                   "Hola" y "Adios", en sitios distintos
#   texto        - Arranca el postit de donde estaba pegao (al lado de "Hola")
#   =            - Asigna la variable "texto" al valor "Adios" ( Y pégalo al lado del nuevo valor)
#                  En este momento, el dato "Hola" queda en RAM sin nadie que lo referencia...
#                  y por ende, ese datos en JAVA/PY/JS es irrecuperable. Se convierte en BASURA: GARBAJE
#                  Y en un momento dado, es posible (o no) que entre el recolector de basura y lo borre
# EN C es distinto. El nuevo dato "Adios" se guarda en el mismo sitio donde estaba "Hola"
# C es un lenguaje que hace un uso mucho más optimizado de la memoria RAM...
# A costa de una mayor complejidad en la programación

def saluda(nombre):
    print( "Hola " + nombre)

saluda("Pepe")

mi_variable = saluda    # Mi variable apunta a una función
mi_variable("Juan")     # Ejecuta la función a la que apunta mi variable

def generar_saludo_informal(nombre):
    return "Hola " + nombre

def generar_saludo_formal(nombre):
    return "Buenos días " + nombre

def imprimir_saludo(funcion_generadora_de_saludo, nombre):
    print( funcion_generadora_de_saludo(nombre) )

def imprimir(saludo):
    print( saludo )

imprimir_saludo(generar_saludo_informal, "Pepe")
imprimir_saludo(generar_saludo_formal, "Pepe")

imprimir( generar_saludo_informal("Pepe") )

# Esta es la clave de la programacion funcional