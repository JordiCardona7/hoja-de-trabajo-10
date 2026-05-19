public class FloydWarshall {
    public FloydResult calculate(DirectedGraph graph) {
        int[][] distances = graph.copyAdjacencyMatrix();
        int[][] next = initializeNext(distances);
        int size = graph.size();

        for (int intermediate = 0; intermediate < size; intermediate++) {
            for (int origin = 0; origin < size; origin++) {
                for (int destination = 0; destination < size; destination++) {
                    if (distances[origin][intermediate] == DirectedGraph.INF
                            || distances[intermediate][destination] == DirectedGraph.INF) {
                        continue;
                    }

                    int candidateDistance = distances[origin][intermediate] + distances[intermediate][destination];
                    if (candidateDistance < distances[origin][destination]) {
                        distances[origin][destination] = candidateDistance;
                        next[origin][destination] = next[origin][intermediate];
                    }
                }
            }
        }

        return new FloydResult(graph.getCities(), distances, next);
    }

    private int[][] initializeNext(int[][] distances) {
        int[][] next = new int[distances.length][distances.length];

        for (int origin = 0; origin < distances.length; origin++) {
            for (int destination = 0; destination < distances.length; destination++) {
                if (distances[origin][destination] < DirectedGraph.INF) {
                    next[origin][destination] = destination;
                } else {
                    next[origin][destination] = -1;
                }
            }
        }

        return next;
    }
}

