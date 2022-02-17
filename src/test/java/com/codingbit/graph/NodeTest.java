package com.codingbit.graph;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class NodeTest {

    @Mock
    private Edge edge;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("It should allow to visit and un-visit a node")
    void node() {
        Node node = new Node("A");
        node.add(edge);

        assertEquals("A", node.getName());
        assertEquals(1, node.getEdges().size());
        assertFalse(node.isVisited());

        node.visit();

        assertTrue(node.isVisited());

        node.unvisit();

        assertFalse(node.isVisited());
    }
}