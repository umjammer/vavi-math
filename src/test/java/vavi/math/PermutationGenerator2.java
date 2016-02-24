/*
 * http://www.merriampark.com/perm.htm.
 */

package vavi.math;

import vavi.util.Generator;


/**
 * Systematically generate permutations. 
 */
public class PermutationGenerator2 extends Generator<int[]> {

    private PermutationGenerator x;

    public PermutationGenerator2(int n) {
        x = new PermutationGenerator(n);
    }

    @Override
    public void run() {
        while (x.hasNext()) {
            yield(x.next().clone());
        }
    }
}

/* */
