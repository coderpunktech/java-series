package com.codingbit.graph;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class PathTest {

    @Mock
    private Edge edgeOne;
    @Mock
    private Edge edgeTwo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        when(edgeOne.getDistance()).thenReturn(3);
        when(edgeOne.getFrom()).thenReturn(new Node("A"));
        when(edgeOne.getTo()).thenReturn(new Node("B"));
        when(edgeTwo.getDistance()).thenReturn(2);
        when(edgeTwo.getTo()).thenReturn(new Node("C"));
    }

    @Test
    @DisplayName("It should return the length of the path")
    void path() {
        Path path = new Path();
        path.add(edgeOne);

        assertEquals(1, path.getEdges().size());
        assertEquals(3, path.getLength());
        assertEquals("A -> B", path.getReadableString());

        path.add(edgeTwo);

        assertEquals(2, path.getEdges().size());
        assertEquals(5, path.getLength());
        assertEquals("A -> B -> C", path.getReadableString());

    }

}