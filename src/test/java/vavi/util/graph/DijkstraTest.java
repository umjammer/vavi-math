/*
 * Copyright (c) 2013 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.util.graph;

import java.util.List;

import org.junit.Test;

import static org.junit.Assert.fail;


/**
 * DijkstraTest.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (umjammer)
 * @version 0.00 2013/02/12 umjammer initial version <br>
 */
public class DijkstraTest {

    @Test
    public void test() {
        fail("Not yet implemented");
    }

    /** */
    public static void main(String[] args) throws Exception {
        Vertex<Character> A = new Vertex<>('A');
        Vertex<Character> B = new Vertex<>('B');
        Vertex<Character> C = new Vertex<>('C');
        Vertex<Character> D = new Vertex<>('D');
        Vertex<Character> E = new Vertex<>('E');
        Vertex<Character> F = new Vertex<>('F');
        Vertex<Character> G = new Vertex<>('G');
        Vertex<Character> H = new Vertex<>('H');

        Graph<Character> graph = new Graph<>();
        graph.setVisitor(new GraphVisitor<Character>() {
            @Override
            public void atNode(Vertex<Character> vertex) {
            }
            @Override
            public void atEnd(List<Vertex<Character>> vertexes) {
            }
        });

        graph.addVertex(A);
        graph.addVertex(B);
        graph.addVertex(C);
        graph.addVertex(D);
        graph.addVertex(E);
        graph.addVertex(F);
        graph.addVertex(G);
        graph.addVertex(H);

        graph.addEdge(A, B, 10);
        graph.addEdge(A, D, 20);
        graph.addEdge(B, C, 10);
        graph.addEdge(C, E, 20);
        graph.addEdge(E, H, 20);
        graph.addEdge(H, G, 10);
        graph.addEdge(H, A, 30);
        graph.addEdge(G, F, 10);
        graph.addEdge(F, D, 20);

        new Dijkstra<>(graph).search();
    }
}

/* */
