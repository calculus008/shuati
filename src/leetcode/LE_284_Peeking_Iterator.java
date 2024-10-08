package leetcode;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by yuank on 4/18/18.
 */
public class LE_284_Peeking_Iterator {
    /**
     * Given an Iterator class interface with methods: next() and hasNext(), design and implement a PeekingIterator
     * that support the peek() operation -- it essentially peek() at the element that will be returned by the next call to next().

         Here is an example. Assume that the iterator is initialized to the beginning of the list: [1, 2, 3].

         Call next() gets you 1, the first element in the list.

         Now you call peek() and it returns 2, the next element. Calling next() after that still return 2.

         You call next() the final time and it returns 3, the last element. Calling hasNext() after that should return false.

         ??Follow up: How would you extend your design to be generic and work with all types, not just integer?

         Medium

         https://leetcode.com/problems/peeking-iterator
     */

    class PeekingIterator implements Iterator<Integer> {

        Iterator<Integer> iter;
        Integer next = null; //!!! Integer

        public PeekingIterator(Iterator<Integer> iterator) {
            // initialize any member here.
            iter = iterator;
            if (iter.hasNext()) {
                next = iter.next();
            }
        }

        // Returns the next element in the iteration without advancing the iterator.
        public Integer peek() {
            return next;
        }

        // hasNext() and next() should behave the same as in the Iterator interface.
        // Override them if needed.
        @Override
        public Integer next() {
            int res = next;
            next = iter.hasNext() ? iter.next() : null;//!!!
            return res;
        }

        @Override
        public boolean hasNext() {
            return next != null; //!!! "!="
        }
    }


    /**
     * The first solution does not consider the case that NULL could be a valid value.
     *
     * using null to check if we have reached the end of iterator is not 100% right.
     * In theory, null could be a valid element.An slightly alternative approach to
     * use boolean indicating end of iterator:
     *
     */
    class PeekingIterator1 implements Iterator<Integer> {
        Integer next;
        Iterator<Integer> iter;
        boolean noSuchElement;

        public PeekingIterator1(Iterator<Integer> iterator) {
            // initialize any member here.
            iter = iterator;
            advanceIter();
        }

        // Returns the next element in the iteration without advancing the iterator.
        public Integer peek() {
            // you should confirm with interviewer what to return/throw
            // if there are no more values
            return next;
        }

        // hasNext() and next() should behave the same as in the Iterator interface.
        // Override them if needed.
        @Override
        public Integer next() {
            if (noSuchElement)
                throw new NoSuchElementException();
            Integer res = next;
            advanceIter();
            return res;
        }

        @Override
        public boolean hasNext() {
            return !noSuchElement;
        }

        private void advanceIter() {
            if (iter.hasNext()) {
                next = iter.next();
            } else {
                noSuchElement = true;
            }
        }
    }
}
