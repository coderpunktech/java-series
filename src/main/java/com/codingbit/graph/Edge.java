package com.codingbit.graph;

import java.util.Objects;

/**
 * Describe the distance between 2 nodes
 */
public class Edge {

    /**
     * The distance value expressed as an Integer
     */
    private final Integer distance;
    /**
     * The starting node
     */
    private final Node from;
    /**
     * The destination node
     */
    private final Node to;


    public Edge(final Integer distance, final Node from, final Node to) {
        this.distance = distance;
        this.from = from;
        this.to = to;
    }

    public Integer getDistance() {
        return distance;
    }

    public Node getFrom() {
        return from;
    }

    public Node getTo() {
        return to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return Objects.equals(distance, edge.distance) && Objects.equals(from, edge.from) && Objects.equals(to, edge.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(distance, from, to);
    }

    @Override
    public String toString() {
        return "Edge{" +
                "distance=" + distance +
                ", from=" + from +
                ", to=" + to +
                '}';
    }
}
