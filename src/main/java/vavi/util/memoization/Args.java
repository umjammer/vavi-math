/*
 * http://d.hatena.ne.jp/bellbind/20050903/p2
 */

package vavi.util.memoization;

import java.util.Arrays;


/**
 * Args.
 *
 * @author <a href="http://d.hatena.ne.jp/bellbind/20050903/p2"></a>
 * @version 0.00 2009/07/06 nsano initial version <br>
 */
public class Args {

    private Object[] args;

    public Args(Object[] args) {
        this.args = args;
    }

    public int hashCode() {
        return Arrays.deepHashCode(this.args);
    }

    public boolean equals(Object o) {
        if (o instanceof Args) {
            return Arrays.deepEquals(this.args, ((Args) o).args);
        }
        return false;
    }
}

/* */
