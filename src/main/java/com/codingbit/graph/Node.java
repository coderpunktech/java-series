package com.codingbit.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represent a node in the graph structure
 */
public class Node {

    /**
     * Describes the way to identify a particular Node
     */
    private final String name;
    /**
     * All the edges for this node
     */
    private final List<Edge> edges;
    /**
     * Describes whether the node has been visited or not
     */
    private boolean visited;

    public Node(final String name) {
        this.name = name;
        this.visited = false;
        this.edges = new ArrayList<>();
    }

    public Node add(final Edge edge) {
        this.edges.add(edge);
        return this;
    }

    public String getName() {
        return name;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public boolean isVisited() {
        return visited;
    }

    /**
     * Mark a node as visited
     *
     * @return this
     */
    public Node visit() {
        this.visited = true;
        return this;
    }

    /**
     * Mark a node as unvisited
     *
     * @return this
     */
    public Node unvisit() {
        this.visited = false;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return visited == node.visited && Objects.equals(name, node.name) && Objects.equals(edges, node.edges);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, edges, visited);
    }

    @Override
    public String toString() {
        return "Node{" +
                "name='" + name + '\'' +
                ", edges=" + edges +
                ", visited=" + visited +
                '}';
    }
}
