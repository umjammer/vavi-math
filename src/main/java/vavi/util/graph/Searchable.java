/*
 * Copyright (c) 2009 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.util.graph;


/**
 * Searchable.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2009/07/17 nsano initial version <br>
 */
public interface Searchable {

    void setStart(int start);

    void search();

    enum Type {
        DFS(Dfs.class),
        BFS(Bfs.class);
        Class<? extends Searchable> searchableClass;
        Type(Class<? extends Searchable> searchableClass) {
            this.searchableClass = searchableClass;
        }
    }

    class Factory {
        public static Searchable getSearchable(Type type) {
            try {
                return type.searchableClass.newInstance();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
    }
}

/* */
