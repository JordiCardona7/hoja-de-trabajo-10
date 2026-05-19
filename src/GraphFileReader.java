import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class GraphFileReader {
    public DirectedGraph read(Path path) throws IOException {
        if (!Files.exists(path)) {
            throw new IOException("No existe el archivo: " + path.toAbsolutePath());
        }

        DirectedGraph graph = new DirectedGraph();
        List<String> lines = Files.readAllLines(path);
        for (int index = 0; index < lines.size(); index++) {
            parseLine(graph, lines.get(index), index + 1);
        }
        return graph;
    }

    private void parseLine(DirectedGraph graph, String line, int lineNumber) {
        String trimmedLine = line.trim();
        if (trimmedLine.isEmpty() || trimmedLine.startsWith("#")) {
            return;
        }

        String[] parts = trimmedLine.split("\\s+");
        if (parts.length != 3) {
            throw new IllegalArgumentException(
                    "Linea " + lineNumber + " invalida. Use el formato: Ciudad1 Ciudad2 KM");
        }

        try {
            graph.addEdge(parts[0], parts[1], Integer.parseInt(parts[2]));
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Linea " + lineNumber + " invalida. KM debe ser entero.", exception);
        }
    }
}

