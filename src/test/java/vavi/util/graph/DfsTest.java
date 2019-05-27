/*
 * Copyright (c) 2013 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.util.graph;

import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;


/**
 * DfsTest.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (umjammer)
 * @version 0.00 2013/02/12 umjammer initial version <br>
 */
public class DfsTest {

    @Test
    public void test() {
        fail("Not yet implemented");
    }

    public static void main(String[] args) {
        Vertex<Character> A = new Vertex<>('A');
        Vertex<Character> B = new Vertex<>('B');
        Vertex<Character> C = new Vertex<>('C');
        Vertex<Character> D = new Vertex<>('D');
        Vertex<Character> E = new Vertex<>('E');

        Graph<Character> graph = new Graph<>();
        graph.setVisitor(new GraphVisitor<Character>() {
            @Override
            public void atNode(Vertex<Character> vertex) {
                System.out.print(vertex);
            }
            @Override
            public void atEnd(List<Vertex<Character>> vertexes) {
            }
        });

        graph.addVertex(A); // 0 (start for dfs)
        graph.addVertex(B); // 1
        graph.addVertex(C); // 2
        graph.addVertex(D); // 3
        graph.addVertex(E); // 4

        graph.addEdge(A, B); // AB
        graph.addEdge(B, C); // BC
        graph.addEdge(A, D); // AD
        graph.addEdge(D, E); // DE

        System.out.print("Visits: ");
        new Dfs<>(graph).search(); // depth-first search
        System.out.println();
        System.out.println(graph);
    }
}

/* */
