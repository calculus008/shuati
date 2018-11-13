package leetcode;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by yuank on 4/18/18.
 */
public class LE_281_Zigzag_Iterator {
    /**
     *   Given two 1d vectors, implement an iterator to return their elements alternately.

         For example, given two 1d vectors:

         v1 = [1, 2]
         v2 = [3, 4, 5, 6]

         By calling next repeatedly until hasNext returns false, the order of elements
         returned by next should be: [1, 3, 2, 4, 5, 6].

         Follow up: What if you are given k 1d vectors? How well can your code be extended to such cases?

         Clarification for the follow up question - Update (2015-09-18):
         The "Zigzag" order is not clearly defined and is ambiguous for k > 2 cases.
         If "Zigzag" does not look right to you, replace "Zigzag" with "Cyclic".

         For example, given the following input:

         [1,2,3]
         [4,5,6,7]
         [8,9]
         It should return [1,4,8,2,5,9,3,6,7].

         Median
     */

    /**
     Time and Space : O(n)

     Also works for the follow up question of having k lists
     */
    public class ZigzagIterator {

        LinkedList<Iterator> list;

        public ZigzagIterator(List<Integer> v1, List<Integer> v2) {
            list = new LinkedList<>();
            if (!v1.isEmpty()) list.add(v1.iterator()); //!!!"v1.iterator()"
            if (!v2.isEmpty()) list.add(v2.iterator());
        }

        /**
         * Key :
         * Use "removeFirst()" to remove the iterator fro head, use it to get the current
         * Integer, then if it still has more elements, add it back to list.
         * "add()" adds the iterator to the tail of the list. So, if we have 4 iterators:
         *
         *                      i1 -> i2 -> i3 -> i4
         * next() : i1.next(),  i2 -> i3 -> i4 -> i1
         * next() : i2.next(),  i3 -> i4 -> i1 -> i2
         * next() : i3.next(),  i4 -> i1 -> i2 -> i3
         * next() : i4.next(),  i1 -> i2 -> i3 -> i4
         *
         * This is the form of "Cyclic" iterators.
         */
        public int next() {
            Iterator cur = list.removeFirst();
            int res = (Integer) cur.next(); //!!!"(Integer)"!!!

            /**
             * this check ensures "hasNext()" logic works
             */
            if (cur.hasNext()) {
                list.add(cur);
            }
            return res;
        }

        /**
         * !!! "!"  "!list.isEmpty()"
         */
        public boolean hasNext() {
            return !list.isEmpty();
        }
    }
}
