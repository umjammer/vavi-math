/*
 * http://www.cs.uiowa.edu/~sriram/21/fall05/ExamplePrograms/ReaderFiles/Chap13/bfs/bfs.java
 */

package vavi.util.graph;

import java.util.LinkedList;
import java.util.Queue;


/**
 * breadth-first search.
 */
public class Bfs<T> implements Searchable {

    /** */
    private Queue<Integer> queue = new LinkedList<>();

    /** */
    private Graph<T> graph;

    /** */ 
    public Bfs(Graph<T> graph) {
        this.graph = graph;
    }

    private int start;
    private int end = -1;

    public void setStart(int start) {
        this.start = start;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    private boolean isEnd(int v) {
        return end != -1 && v == end;
    }

    /** breadth-first search */
    public void search() {
        graph.mark(start, true); // mark it
        queue.offer(start); // insert at tail
        graph.fireAtNode(start); // display it
        int v2;

        while (!queue.isEmpty()) {
            int v1 = queue.poll();
            if (isEnd(v1)) {
                graph.fireAtEnd(start, end); // display it
            }

            // until it has no unvisited neighbors
            while ((v2 = getAdjUnvisitedVertex(v1)) != -1) { // get one,
                graph.mark(v2, true); // mark it
                graph.fireAtNode(v2); // display it
                queue.offer(v2); // insert it
            }
        }

        graph.unmarkAll();
    }

    // returns an unvisited vertex adj to v
    public int getAdjUnvisitedVertex(int v) {
        for (int j = 0; j < graph.size(); j++) {
            if (graph.isAdjoining(v, j) && !graph.isMarked(j)) {
                return j;
            }
        }
        return -1;
    }
}

/* */
