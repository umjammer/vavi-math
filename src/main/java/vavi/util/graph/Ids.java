/*
 * http://d.hatena.ne.jp/aidiary/20080726/1217074176
 */

package vavi.util.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Stack;


/**
 * iterative deepening search.
 */
public class Ids<T> implements Searchable {

    private Graph<T> graph;

    public Ids(Graph<T> graph) {
        this.graph = graph;
    }

    private int start;

    public void setStart(int start) {
        this.start = start;
    }

    private int limit;

    // iterative deepening search
    public void setLimit(int limit) {
        this.limit = limit;
    }

    // iterative deepening search
    public void search() {
        ids(limit, myCondition, new Stack<Integer>() {{ add(start); }});
    }

    Condition myCondition = new Condition() {
        @Override
        public boolean isSatisfied(Collection<Integer> path) {
            return path.size() > 0;
        }
    };

    private void ids(int limit, Condition condition, Stack<Integer> path) {
        int depth = path.size();
        int cur_pos = path.peek();
        if (depth == limit) {  // 深さがlimitに達したら探索終了
            if (condition.isSatisfied(path)) {
                graph.fireAtEnd(path.iterator()); // display it
            }
        } else {
            // 深さ優先探索
            for (int p : getAdjUnvisitedVertex(cur_pos)) {
                if (!path.contains(p)) {
                    path.push(p);
                    ids(limit, condition, path);
                    path.pop();
                }
            }
        }
    }

    /** returns an unvisited vertex adj to v */
    private List<Integer> getAdjUnvisitedVertex(int v) {
        List<Integer> result = new ArrayList<>();
        for (int j = graph.size() - 1; j >= 0; j--) {
            if (graph.isAdjoining(v, j)) {
//System.err.print("(" + v + ", " + j + ": " + vertexList.get(j) + ")");
                result.add(j);
            }
        }
        return result;
    }
}

/* */
