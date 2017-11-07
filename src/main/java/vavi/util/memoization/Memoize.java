/*
 * http://d.hatena.ne.jp/bellbind/20050903/p2
 */

package vavi.util.memoization;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Memoize. 
 *
 * @author <a href="http://d.hatena.ne.jp/bellbind/20050903/p2"></>
 * @version 0.00 2009/07/06 nsano initial version <br>
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Memoize {
    
    /** */
    boolean debug() default false;
}

/* */
