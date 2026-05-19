import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class FloydWarshallTest {
    @Test
    public void calculatesShortestDistanceAndPath() {
        DirectedGraph graph = new DirectedGraph();
        graph.addEdge("A", "B", 2);
        graph.addEdge("B", "C", 3);
        graph.addEdge("A", "C", 10);

        FloydResult result = new FloydWarshall().calculate(graph);

        assertEquals(5, result.distanceBetween("A", "C"));
        assertEquals(List.of("A", "B", "C"), result.pathBetween("A", "C"));
    }

    @Test
    public void reportsMissingRoute() {
        DirectedGraph graph = new DirectedGraph();
        graph.addEdge("A", "B", 2);
        graph.addCity("C");

        FloydResult result = new FloydWarshall().calculate(graph);

        assertFalse(result.hasPath("C", "A"));
        assertEquals(List.of(), result.pathBetween("C", "A"));
    }

    @Test
    public void calculatesCenterUsingColumnEccentricity() {
        DirectedGraph graph = new DirectedGraph();
        graph.addEdge("a", "b", 1);
        graph.addEdge("b", "c", 2);
        graph.addEdge("c", "d", 2);
        graph.addEdge("d", "c", 3);
        graph.addEdge("d", "b", 1);
        graph.addEdge("c", "e", 4);
        graph.addEdge("e", "d", 5);

        FloydResult result = new FloydWarshall().calculate(graph);

        assertEquals("d", result.center());
        assertEquals(5, result.eccentricityOf("d"));
    }
}

