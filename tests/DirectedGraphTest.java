import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DirectedGraphTest {
    @Test
    public void addCityCreatesDiagonalWithZero() {
        DirectedGraph graph = new DirectedGraph();

        graph.addCity("Guatemala");

        assertEquals(1, graph.size());
        assertEquals(0, graph.getDistance("Guatemala", "Guatemala"));
    }

    @Test
    public void addEdgeCreatesCitiesAndDistance() {
        DirectedGraph graph = new DirectedGraph();

        graph.addEdge("Guatemala", "Mixco", 15);

        assertEquals(2, graph.size());
        assertEquals(15, graph.getDistance("Guatemala", "Mixco"));
        assertEquals(DirectedGraph.INF, graph.getDistance("Mixco", "Guatemala"));
    }

    @Test
    public void removeEdgeMarksConnectionAsInfinite() {
        DirectedGraph graph = new DirectedGraph();
        graph.addEdge("Antigua", "Escuintla", 55);

        graph.removeEdge("Antigua", "Escuintla");

        assertEquals(DirectedGraph.INF, graph.getDistance("Antigua", "Escuintla"));
    }

    @Test
    public void addEdgeRejectsInvalidDistance() {
        DirectedGraph graph = new DirectedGraph();

        assertThrows(IllegalArgumentException.class, () -> graph.addEdge("A", "B", 0));
    }
}

