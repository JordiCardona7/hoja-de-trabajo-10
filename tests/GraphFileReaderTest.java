import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GraphFileReaderTest {
    @Test
    public void readsGuateGrafoFileWithoutHeader() throws IOException {
        DirectedGraph graph = new GraphFileReader().read(Path.of("guategrafo.txt"));

        assertEquals(10, graph.size());
        assertEquals(30, graph.getDistance("Mixco", "Antigua"));
        assertEquals(55, graph.getDistance("Antigua", "Escuintla"));
    }
}
