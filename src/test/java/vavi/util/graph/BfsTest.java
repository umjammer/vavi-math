/*
 * Copyright (c) 2013 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.util.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.fail;


/**
 * BfsTest. 
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (umjammer)
 * @version 0.00 2013/02/12 umjammer initial version <br>
 */
public class BfsTest {

    @Test
    public void test() {
        fail("Not yet implemented");
    }

    public static void main(String[] args) {
        Vertex<Integer> V1 = new Vertex<>(1);
        Vertex<Integer> V2 = new Vertex<>(2);
        Vertex<Integer> V3 = new Vertex<>(3);
        Vertex<Integer> V4 = new Vertex<>(4);
        Vertex<Integer> V5 = new Vertex<>(5);
        Vertex<Integer> V6 = new Vertex<>(6);
        Vertex<Integer> V7 = new Vertex<>(7);
        Vertex<Integer> V8 = new Vertex<>(8);
        Vertex<Integer> V9 = new Vertex<>(9);
        Vertex<Integer> V10 = new Vertex<>(10);
        Vertex<Integer> V11 = new Vertex<>(11);
        Vertex<Integer> V12 = new Vertex<>(12);

        Graph<Integer> graph = new Graph<>();
        graph.setVisitor(new GraphVisitor<Integer>() {
            List<Vertex<Integer>> parent = new ArrayList<>(); 
            @Override
            public void atNode(Vertex<Integer> vertex) {
                parent.add(vertex);
            }
            @Override
            public void atEnd(List<Vertex<Integer>> vertexes) {
                List<Vertex<Integer>> path = new ArrayList<>();
                Vertex<Integer> start = vertexes.get(0);
                Vertex<Integer> end = vertexes.get(1);
System.err.println("A: " + start + ", " + end);
                boolean b = false;
                for (int i = parent.size() -1; i >= 0; i--) {
System.err.println("B: " + parent.get(i) + ", " + b);
                    if (parent.get(i).equals(start)) {
                        path.add(start);
                        b = true;
                    } else if (parent.get(i).equals(end)) {
                        path.add(end);
                        break;
                    } else if (b) {
                        path.add(parent.get(i));
                    }
                }
                Collections.reverse(path);

                for (Vertex<Integer> v : path) {
                    System.out.print(v);
                    System.out.print(" ");
                }
                System.out.println();
            }
        });

        graph.addVertex(V1); // 0 (start for bfs)
        graph.addVertex(V2);
        graph.addVertex(V3);
        graph.addVertex(V4);
        graph.addVertex(V5);
        graph.addVertex(V6);
        graph.addVertex(V7);
        graph.addVertex(V8);
        graph.addVertex(V9);
        graph.addVertex(V10);
        graph.addVertex(V11);
        graph.addVertex(V12);

        graph.addEdge(V1, V2);
        graph.addEdge(V1, V3);
        graph.addEdge(V1, V4);
        graph.addEdge(V2, V5);
        graph.addEdge(V2, V6);
        graph.addEdge(V4, V7);
        graph.addEdge(V4, V8);
        graph.addEdge(V5, V9);
        graph.addEdge(V5, V10);
        graph.addEdge(V7, V11);
        graph.addEdge(V7, V12);
        
//        System.err.println(graph);

        Bfs<Integer> bfs = new Bfs<>(graph);
        bfs.setEnd(10);
        bfs.search(); // breadth-first search
    }
}

/* */
