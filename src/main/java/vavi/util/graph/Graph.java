/*
 * Copyright (c) 2009 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.util.graph;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Graph.
 * 
 * @author <a href="mailto:vavivavi@yahoo.co.jp">Naohide Sano</a> (nsano)
 * @version 0.00 2009/07/17 nsano initial version <br>
 */
public class Graph<T> {
    /** list of vertices */
    private List<Vertex<T>> vertexList;

    /** adjacency matrix */
    private boolean adjMat[][];
    /** */
    private float weights[][];

    /** */
    public boolean isAdjoining(int start, int end) {
        return adjMat[start][end];
    }

    /** */
    public float getWeight(int start, int end) {
        return weights[start][end];
    }

    /** */ 
    public Graph() {
        vertexList = new ArrayList<Vertex<T>>();
    }

    /** */
    public void addVertex(Vertex<T> vertex) {
        vertexList.add(vertex);
    }

    /** */
    public void addEdge(Vertex<T> startV, Vertex<T> endV) {
        if (adjMat == null) {
            // adjacency matrix
            adjMat = new boolean[vertexList.size()][vertexList.size()];
        }
        int start = vertexList.indexOf(startV);
        int end = vertexList.indexOf(endV);
        adjMat[start][end] = true;
        adjMat[end][start] = true;
    }

    /** */
    public void addEdgeHalfway(Vertex<T> startV, Vertex<T> endV) {
        if (adjMat == null) {
            // adjacency matrix
            adjMat = new boolean[vertexList.size()][vertexList.size()];
        }
        int start = vertexList.indexOf(startV);
        int end = vertexList.indexOf(endV);
        adjMat[start][end] = true;
// System.err.println(start + ", " + end + ": " + startV + ", " + endV);
    }

    /** */
    public void addEdge(Vertex<T> startV, Vertex<T> endV, float weight) {
        if (adjMat == null) {
            // adjacency matrix
            adjMat = new boolean[vertexList.size()][vertexList.size()];
            weights = new float[vertexList.size()][vertexList.size()];
        }
        int start = vertexList.indexOf(startV);
        int end = vertexList.indexOf(endV);
        adjMat[start][end] = true;
        adjMat[end][start] = true;
        weights[start][end] = weight;
        weights[end][start] = weight;
    }

    /** */
    private GraphVisitor<T> visitor;

    /** */
    public void setVisitor(GraphVisitor<T> visitor) {
        this.visitor = visitor;
    }

    /** */
    public void fireAtNode(int index) {
        visitor.atNode(vertexList.get(index));
    }

    /** for bfs */
    public void fireAtEnd(int index1, int index2) {
        List<Vertex<T>> result = new ArrayList<Vertex<T>>();
        result.add(vertexList.get(index1));
        result.add(vertexList.get(index2));
        visitor.atEnd(result);
    }

    /** for dfs */
    public void fireAtEnd(Iterator<Integer> iterator) {
        List<Vertex<T>> result = new ArrayList<Vertex<T>>();
        while (iterator.hasNext()) {
            result.add(vertexList.get(iterator.next()));
        }
        visitor.atEnd(result);
    }

    /** */
    public void mark(int index, boolean mark) {
        vertexList.get(index).wasVisited = mark;
    }

    /** */
    public void unmarkAll() {
        for (int j = 0; j < size(); j++) {
            mark(j, false);
        }
    }

    /** */
    public boolean isMarked(int index) {
        return vertexList.get(index).wasVisited == true;
    }

    /** */
    public Vertex<T> getVertex(int index) {
        return vertexList.get(index);
    }

    /** */
    public int indexOf(Vertex<T> vertex) {
        return vertexList.indexOf(vertex);
    }

    /** */
    public int size() {
        return vertexList.size();
    }

    /**
     * @return .dot format text
     * @see graphvis 
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("digraph \"");
        sb.append(super.toString());
        sb.append("\" {\n");
        sb.append("concentrate=true\n");
        
        for (int y = 0; y < adjMat.length; y++) {
            for (int x = 0; x < adjMat[y].length; x++) {
                if (adjMat[y][x]) {
                    sb.append("\"");
                    sb.append(vertexList.get(y));
                    sb.append("\" -> \"");
                    sb.append(vertexList.get(x));
                    sb.append("\"");
                    sb.append(";\n");
                }
            }
        }
        sb.append("}");
        return sb.toString();
    }
}

/* */
