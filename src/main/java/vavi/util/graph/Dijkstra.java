/*
 * http://www.geocities.jp/m_hiroi/light/pyalgo22.html
 */

package vavi.util.graph;



/**
 * Dijkstra.
 */
public class Dijkstra<T> implements Searchable {

    private Graph<T> graph;

    public Dijkstra(Graph<T> graph) {
        this.graph = graph;
    }

    private int start;

    public void setStart(int start) {
        this.start = start;
    }

    private float[] cost;
    private int[] prev;

    /* */
    public void search() {
        graph.unmarkAll();
        cost = new float[graph.size()];
        for (int i = 0; i < cost.length; i++) {
            cost[i] = Float.MAX_VALUE;
        }
        prev = new int[graph.size()];

        cost[start] = 0;

        while (true) {
            float min = Float.MAX_VALUE;
            int next = -1;
            graph.mark(start, true);
            // 頂点の選択
            for (int i = 0; i < graph.size(); i++) {
                if (graph.isMarked(i)) {
                    continue;
                }
                if (graph.isAdjoining(start, i)) {
                    float d = cost[start] + graph.getWeight(start, i);
                    if (d < cost[i]) {
                        cost[i] = d;
                        prev[i] = start;
                    }
                }
                if (min > cost[i]) {
                    min = cost[i];
                    next = i;
                }
            }
            start = next;
            if (next == -1) {
                break;
            }
        }
        // 経路の表示
        print_path();
    }

    void print_path() {
        for (int i = 0; i < graph.size(); i++) {
            System.err.printf("%d, prev = %d, cost = %.2f\n", i, prev[i], cost[i]);
        }
    }
}
