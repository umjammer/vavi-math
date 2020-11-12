/*
 * Copyright (c) 2009 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.util.graph;

import java.util.Collection;


/**
 * Condition.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 2009/07/28 nsano initial version <br>
 */
interface Condition {

    /** */
    boolean isSatisfied(Collection<Integer> path);
}

/* */
