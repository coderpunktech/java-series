package com.codingbit.graph;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Describes a graph data structure that allows to discover: <br>
 * - The number of all possible paths from one node to another <br>
 * - The shortest path from one node to another
 */
public class Graph {

    /**
     * Represents the node as a Map since the journey could start from any node
     */
    private final Map<String, Node> nodes;

    public Graph() {
        this.nodes = new HashMap<>();
    }

    public Graph add(final Node node) {
        this.nodes.put(node.getName(), node);
        return this;
    }

    /**
     * Find all the paths from a node to another, a node will not be revisited in a path
     *
     * @param fromName the name of the node to start from
     * @param toName the name of the destination node
     * @return a list of all the paths available to get from the starting node to the destination node <br>
     *         or null when the graph is empty
     */
    public List<Path> allPaths(final String fromName, final String toName) {
        return null;
    }

    /**
     * Find the shortest path from a node to another, a node will not be revisited in a path
     *
     * @param fromName the name of the node to start from
     * @param toName the name of the destination node
     * @return the {@link Path} with the shortest distance between the starting and destination node <br>
     *         or null when no paths are found.
     */
    public Path shortestPath(final String fromName, final String toName) {
        return allPaths(fromName, toName)
                .stream()
                .reduce((accumulator, combiner) -> (accumulator.getLength() < combiner.getLength() ? accumulator : combiner))
                .orElse(null);
    }

    public Map<String, Node> getNodes() {
        return nodes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Graph graph = (Graph) o;
        return Objects.equals(nodes, graph.nodes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nodes);
    }

    @Override
    public String toString() {
        return "Graph{" +
                "nodes=" + nodes +
                '}';
    }
}
