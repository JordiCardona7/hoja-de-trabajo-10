import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DirectedGraph {
    public static final int INF = 1_000_000_000;

    private final List<String> cities;
    private final Map<String, Integer> indexes;
    private int[][] adjacency;

    public DirectedGraph() {
        this.cities = new ArrayList<>();
        this.indexes = new LinkedHashMap<>();
        this.adjacency = new int[0][0];
    }

    public void addCity(String city) {
        String normalizedCity = normalizeCity(city);
        if (indexes.containsKey(normalizedCity)) {
            return;
        }

        int newSize = cities.size() + 1;
        int[][] resized = new int[newSize][newSize];
        for (int row = 0; row < newSize; row++) {
            for (int column = 0; column < newSize; column++) {
                resized[row][column] = row == column ? 0 : INF;
            }
        }

        for (int row = 0; row < adjacency.length; row++) {
            System.arraycopy(adjacency[row], 0, resized[row], 0, adjacency[row].length);
        }

        cities.add(normalizedCity);
        indexes.put(normalizedCity, newSize - 1);
        adjacency = resized;
    }

    public void addEdge(String origin, String destination, int distance) {
        if (distance <= 0) {
            throw new IllegalArgumentException("La distancia debe ser mayor que cero.");
        }

        addCity(origin);
        addCity(destination);
        adjacency[indexOf(origin)][indexOf(destination)] = distance;
    }

    public void removeEdge(String origin, String destination) {
        int originIndex = indexOf(origin);
        int destinationIndex = indexOf(destination);
        if (originIndex == destinationIndex) {
            throw new IllegalArgumentException("No se puede eliminar la distancia de una ciudad hacia si misma.");
        }
        adjacency[originIndex][destinationIndex] = INF;
    }

    public List<String> getCities() {
        return Collections.unmodifiableList(cities);
    }

    public int size() {
        return cities.size();
    }

    public int getDistance(String origin, String destination) {
        return adjacency[indexOf(origin)][indexOf(destination)];
    }

    public int[][] copyAdjacencyMatrix() {
        return copyMatrix(adjacency);
    }

    public boolean containsCity(String city) {
        return indexes.containsKey(normalizeCity(city));
    }

    private int indexOf(String city) {
        String normalizedCity = normalizeCity(city);
        Integer index = indexes.get(normalizedCity);
        if (index == null) {
            throw new IllegalArgumentException("La ciudad no existe en el grafo: " + normalizedCity);
        }
        return index;
    }

    private static String normalizeCity(String city) {
        if (city == null || city.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la ciudad no puede estar vacio.");
        }
        return city.trim();
    }

    static int[][] copyMatrix(int[][] matrix) {
        int[][] copy = new int[matrix.length][];
        for (int row = 0; row < matrix.length; row++) {
            copy[row] = matrix[row].clone();
        }
        return copy;
    }
}

