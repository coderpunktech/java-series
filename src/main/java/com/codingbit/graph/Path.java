package com.codingbit.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Describes a Path object which is represented as a list of {@link Edge}
 */
public class Path {

    /**
     * represents the path from the starting edge to the final edge
     */
    private final List<Edge> edges;

    public Path() {
        this.edges = new ArrayList<>();
    }

    /**
     * Add the next Edge to the path
     *
     * @param edge the next edge to add to this path
     * @return this
     */
    public Path add(final Edge edge) {
        this.edges.add(edge);
        return this;
    }

    /**
     * @return the sum of all the edges distance or null when there are no edges
     */
    public Integer getLength() {
        return this.edges.stream()
                .map(Edge::getDistance)
                .reduce(Integer::sum)
                .orElse(null);
    }

    public List<Edge> getEdges() {
        return edges;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Path path = (Path) o;
        return Objects.equals(edges, path.edges);
    }

    @Override
    public int hashCode() {
        return Objects.hash(edges);
    }

    @Override
    public String toString() {
        return "Path{" +
                "edges=" + edges +
                '}';
    }
}
