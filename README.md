# Hoja de Trabajo 10

Programa en Java para representar un grafo dirigido de ciudades, aplicar el algoritmo de Floyd-Warshall, consultar rutas mas cortas, modificar arcos y calcular el centro del grafo.

## Requisitos cubiertos

- Grafo dirigido implementado con matriz de adyacencia.
- Lectura de `guategrafo.txt` sin encabezado, con lineas en formato `Ciudad1 Ciudad2 KM`.
- Algoritmo de Floyd-Warshall con matriz de distancias y reconstruccion de ruta.
- Calculo del centro del grafo usando la excentricidad por columna indicada en el documento de apoyo.
- Menu para consultar ruta, ver centro, agregar arcos y eliminar arcos.
- Pruebas unitarias con JUnit.
- Diagrama UML en `docs/diagrama_uml.md` y `docs/diagrama_uml.puml`.
- Programa opcional en Python con NetworkX en `python/networkx_solution.py`.

## Ejecutar

```bash
javac -d out src/*.java
java -cp out Main
```

Tambien se puede pasar otra ruta de archivo al `Main`:

```bash
javac -d out src/*.java
java -cp out Main ruta/al/guategrafo.txt
```

## Pruebas

Las pruebas JUnit estan en la carpeta `tests`. Se pueden ejecutar desde IntelliJ IDEA, NetBeans, VS Code o cualquier IDE que tenga JUnit configurado.

## Menu

1. Consultar la ruta mas corta entre una ciudad origen y una ciudad destino.
2. Mostrar la ciudad que queda en el centro del grafo.
3. Modificar el grafo eliminando una conexion o agregando/actualizando una conexion.
4. Finalizar el programa.

## Python opcional

Instalar NetworkX y ejecutar:

```bash
pip install -r python/requirements.txt
python python/networkx_solution.py guategrafo.txt
```
