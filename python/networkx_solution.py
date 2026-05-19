import argparse
import math

import networkx as nx


def read_graph(path):
    graph = nx.DiGraph()
    with open(path, "r", encoding="utf-8") as file:
        for line_number, line in enumerate(file, start=1):
            stripped = line.strip()
            if not stripped or stripped.startswith("#"):
                continue

            parts = stripped.split()
            if len(parts) != 3:
                raise ValueError(f"Linea {line_number} invalida. Use: Ciudad1 Ciudad2 KM")

            origin, destination, distance = parts
            graph.add_edge(origin, destination, weight=int(distance))
    return graph


def center_from_floyd(graph):
    lengths = dict(nx.floyd_warshall(graph, weight="weight"))
    eccentricities = {}

    for destination in graph.nodes:
        maximum = 0
        for origin in graph.nodes:
            distance = lengths.get(origin, {}).get(destination, math.inf)
            maximum = max(maximum, distance)
        eccentricities[destination] = maximum

    center = min(eccentricities, key=eccentricities.get)
    return center, eccentricities, lengths


def print_matrix(graph, lengths):
    nodes = list(graph.nodes)
    width = max(8, *(len(node) + 2 for node in nodes))
    print("".rjust(width), end="")
    for node in nodes:
        print(node.rjust(width), end="")
    print()

    for origin in nodes:
        print(origin.rjust(width), end="")
        for destination in nodes:
            distance = lengths.get(origin, {}).get(destination, math.inf)
            value = "INF" if distance == math.inf else str(int(distance))
            print(value.rjust(width), end="")
        print()


def main():
    parser = argparse.ArgumentParser(description="Floyd-Warshall con NetworkX para guategrafo.txt")
    parser.add_argument("file", nargs="?", default="guategrafo.txt")
    args = parser.parse_args()

    graph = read_graph(args.file)
    center, eccentricities, lengths = center_from_floyd(graph)

    print("Matriz de caminos mas cortos:")
    print_matrix(graph, lengths)
    print()
    print(f"Centro del grafo: {center}")
    print("Excentricidades:")
    for city, eccentricity in eccentricities.items():
        value = "INF" if eccentricity == math.inf else f"{int(eccentricity)} KM"
        print(f"  {city}: {value}")


if __name__ == "__main__":
    main()

