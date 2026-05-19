import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final FloydWarshall FLOYD = new FloydWarshall();
    private static final MatrixFormatter FORMATTER = new MatrixFormatter();

    public static void main(String[] args) {
        Path graphFile = args.length > 0 ? Path.of(args[0]) : Path.of("guategrafo.txt");

        try {
            DirectedGraph graph = new GraphFileReader().read(graphFile);
            FloydResult result = FLOYD.calculate(graph);

            System.out.println("Grafo cargado desde: " + graphFile.toAbsolutePath());
            System.out.println("Matriz de adyacencia:");
            System.out.println(FORMATTER.formatAdjacency(graph));

            runMenu(graph, result);
        } catch (IOException | IllegalArgumentException exception) {
            System.out.println("Error al iniciar el programa: " + exception.getMessage());
        }
    }

    private static void runMenu(DirectedGraph graph, FloydResult result) {
        boolean running = true;
        FloydResult currentResult = result;

        while (running) {
            printMenu();
            int option = readInt("Seleccione una opcion: ");

            switch (option) {
                case 1 -> showShortestPath(currentResult);
                case 2 -> showCenter(currentResult);
                case 3 -> currentResult = modifyGraph(graph);
                case 4 -> {
                    running = false;
                    System.out.println("Programa finalizado.");
                }
                default -> System.out.println("Opcion invalida.");
            }
        }
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("1. Consultar ruta mas corta");
        System.out.println("2. Mostrar centro del grafo");
        System.out.println("3. Modificar el grafo");
        System.out.println("4. Finalizar");
    }

    private static void showShortestPath(FloydResult result) {
        String origin = readText("Ciudad origen: ");
        String destination = readText("Ciudad destino: ");

        try {
            if (!result.hasPath(origin, destination)) {
                System.out.println("No existe ruta entre " + origin + " y " + destination + ".");
                return;
            }

            int distance = result.distanceBetween(origin, destination);
            List<String> path = result.pathBetween(origin, destination);
            System.out.println("Distancia mas corta: " + distance + " KM");
            System.out.println("Ruta: " + String.join(" -> ", path));
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private static void showCenter(FloydResult result) {
        try {
            String center = result.center();
            System.out.println("Centro del grafo: " + center);
            System.out.println("Excentricidades:");
            for (Map.Entry<String, Integer> entry : result.eccentricities().entrySet()) {
                System.out.println("  " + entry.getKey() + ": " + formatDistance(entry.getValue()));
            }
        } catch (IllegalStateException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private static FloydResult modifyGraph(DirectedGraph graph) {
        System.out.println();
        System.out.println("1. Interrupcion de trafico entre dos ciudades");
        System.out.println("2. Agregar o actualizar conexion");
        int option = readInt("Seleccione una opcion: ");

        try {
            if (option == 1) {
                String origin = readText("Ciudad origen: ");
                String destination = readText("Ciudad destino: ");
                graph.removeEdge(origin, destination);
                System.out.println("Conexion eliminada.");
            } else if (option == 2) {
                String origin = readText("Ciudad origen: ");
                String destination = readText("Ciudad destino: ");
                int distance = readInt("Distancia en KM: ");
                graph.addEdge(origin, destination, distance);
                System.out.println("Conexion agregada o actualizada.");
            } else {
                System.out.println("Opcion invalida. No se modifico el grafo.");
            }
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }

        FloydResult recalculated = FLOYD.calculate(graph);
        System.out.println("Matriz de adyacencia actualizada:");
        System.out.println(FORMATTER.formatAdjacency(graph));
        System.out.println("Nuevo centro del grafo: " + recalculated.center());
        return recalculated;
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = SCANNER.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException exception) {
                System.out.println("Ingrese un numero entero.");
            }
        }
    }

    private static String readText(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = SCANNER.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Ingrese un texto valido.");
        }
    }

    private static String formatDistance(int distance) {
        return distance >= DirectedGraph.INF ? "INF" : distance + " KM";
    }
}

