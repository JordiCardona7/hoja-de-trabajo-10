import java.util.List;

public class MatrixFormatter {
    public String formatAdjacency(DirectedGraph graph) {
        return formatMatrix(graph.getCities(), graph.copyAdjacencyMatrix());
    }

    public String formatDistances(FloydResult result) {
        return formatMatrix(result.getCities(), result.copyDistances());
    }

    private String formatMatrix(List<String> cities, int[][] matrix) {
        int width = 8;
        for (String city : cities) {
            width = Math.max(width, city.length() + 2);
        }

        StringBuilder builder = new StringBuilder();
        builder.append(String.format("%" + width + "s", ""));
        for (String city : cities) {
            builder.append(String.format("%" + width + "s", city));
        }
        builder.append(System.lineSeparator());

        for (int row = 0; row < matrix.length; row++) {
            builder.append(String.format("%" + width + "s", cities.get(row)));
            for (int column = 0; column < matrix[row].length; column++) {
                builder.append(String.format("%" + width + "s", formatValue(matrix[row][column])));
            }
            builder.append(System.lineSeparator());
        }

        return builder.toString();
    }

    private String formatValue(int value) {
        return value >= DirectedGraph.INF ? "INF" : Integer.toString(value);
    }
}

