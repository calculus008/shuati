package lintcode;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by yuank on 11/11/18.
 */
public class LI_541_Zigzag_Iterator_II {
    /**
     Follow up Zigzag Iterator (LE_281_Zigzag_Iterator): What if you are given k 1d vectors?
     How well can your code be extended to such cases? The "Zigzag" order is not clearly
     defined and is ambiguous for k > 2 cases. If "Zigzag" does not look
     right to you, replace "Zigzag" with "Cyclic".

     Example
     Given k = 3 1d vectors:

     [1,2,3]
     [4,5,6,7]
     [8,9]

     Return [1,4,8,2,5,9,3,6,7].
     */

    public class ZigzagIterator2 {
        /**
         * Since we use "removeFirst()", MUST declare list as LinkedList!!!
         */
        LinkedList<Iterator<Integer>> list;

        public ZigzagIterator2(List<List<Integer>> vecs) {
            list = new LinkedList<>();
            for (List<Integer> vec : vecs) {//!!!"List<Integer>
                if (!vec.isEmpty()) {
                    list.add(vec.iterator());
                }
            }
        }

        public int next() {
            Iterator<Integer> cur = list.removeFirst();//!!! "Iterator<Integer> cur"
            int res = cur.next();

            if (cur.hasNext()) {
                list.add(cur);
            }

            return res;
        }


        public boolean hasNext() {
            return !list.isEmpty(); //!!! "!"
        }
    }
}
