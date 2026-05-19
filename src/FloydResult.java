import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FloydResult {
    private final List<String> cities;
    private final Map<String, Integer> indexes;
    private final int[][] distances;
    private final int[][] next;

    FloydResult(List<String> cities, int[][] distances, int[][] next) {
        this.cities = List.copyOf(cities);
        this.indexes = new LinkedHashMap<>();
        for (int index = 0; index < cities.size(); index++) {
            indexes.put(cities.get(index), index);
        }
        this.distances = DirectedGraph.copyMatrix(distances);
        this.next = DirectedGraph.copyMatrix(next);
    }

    public List<String> getCities() {
        return Collections.unmodifiableList(cities);
    }

    public int[][] copyDistances() {
        return DirectedGraph.copyMatrix(distances);
    }

    public int distanceBetween(String origin, String destination) {
        return distances[indexOf(origin)][indexOf(destination)];
    }

    public boolean hasPath(String origin, String destination) {
        return distanceBetween(origin, destination) < DirectedGraph.INF;
    }

    public List<String> pathBetween(String origin, String destination) {
        int originIndex = indexOf(origin);
        int destinationIndex = indexOf(destination);

        if (next[originIndex][destinationIndex] == -1) {
            return List.of();
        }

        List<String> path = new ArrayList<>();
        int current = originIndex;
        path.add(cities.get(current));
        while (current != destinationIndex) {
            current = next[current][destinationIndex];
            if (current == -1) {
                return List.of();
            }
            path.add(cities.get(current));
        }
        return path;
    }

    public Map<String, Integer> eccentricities() {
        Map<String, Integer> eccentricities = new LinkedHashMap<>();

        for (int column = 0; column < cities.size(); column++) {
            int maximumDistanceToCity = 0;
            for (int row = 0; row < cities.size(); row++) {
                maximumDistanceToCity = Math.max(maximumDistanceToCity, distances[row][column]);
            }
            eccentricities.put(cities.get(column), maximumDistanceToCity);
        }

        return eccentricities;
    }

    public String center() {
        if (cities.isEmpty()) {
            throw new IllegalStateException("No se puede calcular el centro de un grafo vacio.");
        }

        String center = cities.get(0);
        int bestEccentricity = DirectedGraph.INF;
        for (Map.Entry<String, Integer> entry : eccentricities().entrySet()) {
            if (entry.getValue() < bestEccentricity) {
                center = entry.getKey();
                bestEccentricity = entry.getValue();
            }
        }
        return center;
    }

    public int eccentricityOf(String city) {
        Integer eccentricity = eccentricities().get(normalizeCity(city));
        if (eccentricity == null) {
            throw new IllegalArgumentException("La ciudad no existe en el grafo: " + city);
        }
        return eccentricity;
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
}

