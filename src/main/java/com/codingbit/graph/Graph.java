package com.codingbit.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;

/**
 * Describes a graph data structure that allows to discover: <br>
 * - The number of all possible paths from one node to another <br>
 * - The shortest path from one node to another
 */
public class Graph {

    /**
     * Represents the node as an adjacency list
     */
    private final Map<Node, List<Edge>> adjacencyList;
    /**
     * The queue of probable paths
     */
    private final Queue<List<Node>> probablePath;
    /**
     * The list of paths
     */
    private final List<Path> paths;

    public Graph() {
        this.adjacencyList = new HashMap<>();
        this.probablePath = new LinkedList<>();
        this.paths = new ArrayList<>();
    }

    public Graph addNode(final Node node) {
        this.adjacencyList.put(node, new ArrayList<>());
        return this;
    }

    public Graph addEdge(final Node node, final Edge edge) {
        this.adjacencyList.get(node).add(edge);
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
        // create the nodes
        Node from = new Node(fromName);
        Node destination = new Node(toName);

        if (!adjacencyList.containsKey(from)) {
            throw new IllegalArgumentException(String.format("%s node missing from graph", from));
        }

        if (!adjacencyList.containsKey(destination)) {
            throw new IllegalArgumentException(String.format("%s node missing from graph", destination));
        }

        return findPaths(from, destination);
    }

    /**
     * Uses BFS to find all possible paths in this graph
     *
     * @param from the starting node
     * @param destination the destination node
     * @return a list containing all the possible paths
     */
    private List<Path> findPaths(final Node from, final Node destination) {
        // add the starting node to the probable path queue
        probablePath.add(new ArrayList<>(){{add(from);}});

        // while there are probable paths to poll
        while (!probablePath.isEmpty()) {
            // poll the probable path
            List<Node> path = probablePath.poll();
            // get the last node in this path
            Node lastNode = path.get(path.size() - 1);

            // if the last node is equal to the destination we have a full path!
            if (lastNode.equals(destination)) {
                // start building the path by finding all the edges
                List<Edge> edges = new ArrayList<>();
                // loop the path
                for (int i = 0; i < path.size() - 1; i++) {
                    // get the current node in the path
                    Node current = path.get(i);
                    // get the next node in the path
                    Node next = (i < path.size() - 2) ? path.get(i + 1) : destination;
                    // find the edge for this node in the adjacency list
                    Edge wanted = adjacencyList.get(current)
                            .stream()
                            // if the `to` node matches next then this is the edge we want
                            .filter(edge -> edge.getTo().equals(next))
                            // there will always be only one
                            .findFirst()
                            // zero is not possible, throw!
                            .orElseThrow();

                    // add the wanted edge to the list
                    edges.add(wanted);

                }
                // create a new path with the edges and add it to the paths list
                paths.add(new Path(edges));
            }
            // since we are dealing with a cyclic graph, discard the path as soon as the
            // lastNode appears more than once in the path (avoid a StackOverFlow)
            else if (path.stream().filter(node -> node.equals(lastNode)).count() == 1) {
                // find the neighbours for this node in the adjacency list
                List<Edge> neighbours = adjacencyList.get(lastNode);

                // for each neighbour node create a path from the previous path
                for (Edge neighbour : neighbours) {
                    List<Node> nestedPath = new ArrayList<>(path);
                    nestedPath.add(neighbour.getTo());
                    // add the current path to the probablePath queue so it can be polled later
                    probablePath.add(nestedPath);
                }
            }
        }

        return paths;
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

    /**
     * Clear the paths list and the probablePath queue
     */
    public void clearState() {
        this.paths.clear();
        this.probablePath.clear();
    }

    public Map<Node, List<Edge>> getAdjacencyList() {
        return adjacencyList;
    }

    public Queue<List<Node>> getProbablePath() {
        return probablePath;
    }

    public List<Path> getPaths() {
        return paths;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Graph graph = (Graph) o;
        return Objects.equals(adjacencyList, graph.adjacencyList) && Objects.equals(probablePath, graph.probablePath) && Objects.equals(paths, graph.paths);
    }

    @Override
    public int hashCode() {
        return Objects.hash(adjacencyList, probablePath, paths);
    }

    @Override
    public String toString() {
        return "Graph{" +
                "adjacencyList=" + adjacencyList +
                ", probablePath=" + probablePath +
                ", paths=" + paths +
                '}';
    }
}
