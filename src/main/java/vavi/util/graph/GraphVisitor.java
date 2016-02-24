/*
 * Copyright (c) 2009 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.util.graph;

import java.util.List;


/**
 * GraphVisitor. 
 *
 * @author <a href="mailto:vavivavi@yahoo.co.jp">Naohide Sano</a> (nsano)
 * @version 0.00 2009/07/17 nsano initial version <br>
 */
public interface GraphVisitor<T> {
    /** */
    void atNode(Vertex<T> vertex);
    /** */
    void atEnd(List<Vertex<T>> vertices);
}
/* */
