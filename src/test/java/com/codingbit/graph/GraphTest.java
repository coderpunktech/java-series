package com.codingbit.graph;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GraphTest {

    private Graph graph;

    @BeforeEach
    void setUp() {
        Node A = new Node("A");
        Node B = new Node("B");
        Node C = new Node("C");
        Node D = new Node("D");
        Node E = new Node("E");
        Node F = new Node("F");

        graph = new Graph();

        graph.addNode(A)
                .addNode(B)
                .addNode(C)
                .addNode(D)
                .addNode(E)
                .addNode(F)
                .addEdge(A, new Edge(2, A, B))
                .addEdge(A, new Edge(3, A, D))
                .addEdge(B, new Edge(6, B, C))
                .addEdge(B, new Edge(10, B, E))
                .addEdge(C, new Edge(4, C, F))
                .addEdge(D, new Edge(4, D, C))
                .addEdge(D, new Edge(3, D, E))
                .addEdge(E, new Edge(7, E, F))
                .addEdge(E, new Edge(3, E, D));
    }

    @Test
    void from_A_to_C() {
        List<Path> paths = graph.allPaths("A", "C");
        assertEquals(3, paths.size());

        List<Path> sorted = paths.stream()
                .sorted(Comparator.comparing(Path::getLength))
                .collect(Collectors.toList());

        Path one = sorted.get(0);
        assertNotNull(one);
        assertEquals(7, one.getLength());
        assertEquals("A -> D -> C", one.getReadableString());

        Path two = sorted.get(1);
        assertNotNull(two);
        assertEquals(8, two.getLength());
        assertEquals("A -> B -> C", two.getReadableString());

        Path three = sorted.get(2);
        assertNotNull(three);
        assertEquals(19, three.getLength());
        assertEquals("A -> B -> E -> D -> C", three.getReadableString());
    }

    @Test
    void from_A_to_F() {

        List<Path> paths = graph.allPaths("A", "F");
        assertEquals(5, paths.size());

        List<Path> sorted = paths.stream()
                .sorted(Comparator.comparing(Path::getLength))
                .collect(Collectors.toList());

        Path one = sorted.get(0);
        assertNotNull(one);
        assertEquals(11, one.getLength());
        assertEquals("A -> D -> C -> F", one.getReadableString());

        Path two = sorted.get(1);
        assertNotNull(two);
        assertEquals(12, two.getLength());
        assertEquals("A -> B -> C -> F", two.getReadableString());

        Path three = sorted.get(2);
        assertNotNull(three);
        assertEquals(13, three.getLength());
        assertEquals("A -> D -> E -> F", three.getReadableString());

        Path four = sorted.get(3);
        assertNotNull(four);
        assertEquals(19, four.getLength());
        assertEquals("A -> B -> E -> F", four.getReadableString());

        Path five = sorted.get(4);
        assertNotNull(five);
        assertEquals(23, five.getLength());
        assertEquals("A -> B -> E -> D -> C -> F", five.getReadableString());

    }
}