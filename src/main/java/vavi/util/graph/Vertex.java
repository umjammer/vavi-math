/*
 * Copyright (c) 2009 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.util.graph;


/**
 * Vertex.
 *
 * @author <a href="mailto:vavivavi@yahoo.co.jp">Naohide Sano</a> (nsano)
 * @version 0.00 2009/07/17 nsano initial version <br>
 */
public class Vertex<T> {
    /** */
    public T object;
    /** */
    public boolean wasVisited;
    /** */
    public Vertex(T object) {
        this.object = object;
        wasVisited = false;
    }
    /* */
    public String toString() {
        return String.valueOf(object);
    }
    /* */
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (Vertex.class.isInstance(o)) {
            return Vertex.class.cast(o).object.equals(object);
        } else {
            return false;
        }
    }
}

/* */
