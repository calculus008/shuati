package lintcode;

import java.util.Iterator;
import java.util.List;

/**
 * Created by yuank on 9/12/18.
 */
public class LI_601_Flatten_2D_Vector {
    /**
         Implement an iterator to flatten a 2d vector.

         Example
         Given 2d vector =

         [
             [1,2],
             [3],
             [4,5,6]
         ]

         By calling next repeatedly until hasNext returns false,
         the order of elements returned by next should be: [1,2,3,4,5,6].

         Medium
     */

    public class Vector2D implements Iterator<Integer> {
        Iterator<List<Integer>> i;
        Iterator<Integer> j;

        public Vector2D(List<List<Integer>> vec2d) {
            i = vec2d.iterator();
            j = null;
        }

        @Override
        public Integer next() {
            if (!hasNext()) {
                return null;
            }
            return j.next();
        }

        @Override
        public boolean hasNext() {
            //!!! "while"
            while ((j == null || !j.hasNext()) && i.hasNext()) {
                j = i.next().iterator();
            }
            return j != null && j.hasNext();
        }

        @Override
        public void remove() {}
    }
}
