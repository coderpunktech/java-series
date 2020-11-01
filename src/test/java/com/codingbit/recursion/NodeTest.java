package com.codingbit.recursion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

class NodeTest {

    private Node rootNode;
    Node node1;
    Node node2;
    Node node3;
    Node node4;
    Node node5;

    @BeforeEach
    void setUp() {
        //           RootNode
        //              |
        //             / \
        //            /   \
        //         Node1 Node2
        //           |     |
        //          /\      \
        //         /  \      \
        //     Node3 Node4   Node5

        rootNode = new Node("rootNode");

        node1 = new Node("Node1");
        node2 = new Node("Node2", true);
        node3 = new Node("Node3");
        node4 = new Node("Node4", true);
        node5 = new Node("Node5");

        node1.addChild(node3, node4);
        node2.addChild(node5);

        rootNode.addChild(node1, node2);

    }

    @Test
    @DisplayName("Reactive breadth first recursion")
    void bfsRecursion() {
        StepVerifier.create(Flux.fromIterable(rootNode.getChildren())
                .expand(child -> Flux.fromIterable(child.getChildren())))
                .expectNext(node1, node2, node3, node4, node5)
                .verifyComplete();
    }

    @Test
    @DisplayName("Reactive depth first recursion")
    void dfsRecursion() {
        StepVerifier.create(Flux.fromIterable(rootNode.getChildren())
                .expandDeep(child -> Flux.fromIterable(child.getChildren())))
                .expectNext(node1, node3, node4, node2, node5)
                .verifyComplete();
    }

    @Test
    @DisplayName("Reactive recursion of a graph")
    void rootNodeRecursion() {
        List<Node> graph = new ArrayList<>();
        graph.add(rootNode);
        StepVerifier.create(Flux.fromIterable(graph)
                .expandDeep(child -> Flux.fromIterable(child.getChildren())))
                .expectNext(rootNode, node1, node3, node4, node2, node5)
                .verifyComplete();
    }

    @Test
    @DisplayName("Reactive breadth first search")
    void bfsFindFirstMarked() {
        Flux<Node> findMarked = Flux.fromIterable(rootNode.getChildren())
                .expand(child -> Flux.fromIterable(child.getChildren()))
                .takeUntil(Node::isMarked);

        StepVerifier.create(findMarked)
                .expectNext(node1, node2)
                .verifyComplete();

        // check that the found node is equal to the expected node2
        Node found = findMarked.last().block();

        assertNotNull(found);
        assertEquals(node2, found);
    }

    @Test
    @DisplayName("Reactive depth first search")
    void dfsFindFirstMarked() {

        Flux<Node> findMarked = Flux.fromIterable(rootNode.getChildren())
                .expandDeep(child -> Flux.fromIterable(child.getChildren()))
                .takeUntil(Node::isMarked);

        StepVerifier.create(findMarked)
                .expectNext(node1, node3, node4)
                .verifyComplete();

        // check that the found node is equal to the expected node4
        Node found = findMarked.last().block();

        assertNotNull(found);
        assertEquals(node4, found);
    }

}