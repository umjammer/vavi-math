/*
 * http://www.cs.uiowa.edu/~sriram/21/fall05/ExamplePrograms/ReaderFiles/Chap13/dfs/dfs.java
 */

package vavi.util.graph;

import java.util.ArrayDeque;
import java.util.Deque;


/**
 * depth-first search.
 */
public class Dfs<T> implements Searchable {

    private Deque<Integer> stack = new ArrayDeque<Integer>();

    private Graph<T> graph;

    public Dfs(Graph<T> graph) {
        this.graph = graph;
    }

    private int start;

    public void setStart(int start) {
        this.start = start;
    }

    // depth-first search
    public void search() {
        graph.mark(start, true); // mark it
        graph.fireAtNode(start); // display it
        stack.push(start); // push it

        while (!stack.isEmpty()) {
            // get an unvisited vertex adjacent to stack top
            int v = getAdjUnvisitedVertex(stack.peek());
            if (v == -1) { // if no such vertex,
                graph.fireAtEnd(stack.iterator());
                stack.pop();
            } else { // if it exists,
                graph.mark(v, true); // mark it
                graph.fireAtNode(v); // display it
                stack.push(v); // push it
            }
        }

        graph.unmarkAll();
    }

    /** returns an unvisited vertex adj to v */
    private int getAdjUnvisitedVertex(int v) {
        for (int j = graph.size() - 1; j >= 0; j--) {
            if (graph.isAdjoining(v, j) && !graph.isMarked(j)) {
//System.err.print("(" + v + ", " + j + ": " + vertexList.get(j) + ")");
                return j;
            }
        }
        return -1;
    }
}

/* */
