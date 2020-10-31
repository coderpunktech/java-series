package com.codingbit.recursion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Node {

    private final String name;
    private final boolean marked;
    private final List<Node> children;

    public Node(String name, boolean marked) {
        this.name = name;
        this.marked = marked;
        this.children = new ArrayList<>();
    }

    public Node(String name) {
        this.name = name;
        this.marked = false;
        this.children = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public boolean isMarked() {
        return marked;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void addChild(final Node...nodes) {
        children.addAll(Arrays.asList(nodes));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return marked == node.marked &&
                Objects.equals(name, node.name) &&
                Objects.equals(children, node.children);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, marked, children);
    }

    @Override
    public String toString() {
        return "Node{" +
                "name='" + name + '\'' +
                ", marked=" + marked +
                ", children=" + children +
                '}';
    }
}
