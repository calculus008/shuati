package leetcode;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * Created by yuank on 5/14/18.
 */
public class LE_341_Flatten_Nested_List_Iterator {
    /**
     *   Given a nested list of integers, implement an iterator to flatten it.

         Each element is either an integer, or a list -- whose elements may also be integers or other lists.

         Example 1:
         Given the list [[1,1],2,[1,1]],

         By calling next repeatedly until hasNext returns false, the order of elements returned by next should be: [1,1,2,1,1].

         Example 2:
         Given the list [1,[4,[6]]],

         By calling next repeatedly until hasNext returns false, the order of elements returned by next should be: [1,4,6].

         Medium
     */

    /**
     * Important
     *
     *
     */

    /**
         Stack
         Time and Space : O(n)

         Solution 1
         Make an assumption that nestedList is implemented as ArrayList and can be accessed through get(i).
         This may not be the cause, need to talk to the interviewer
     **/
    public class NestedIterator implements Iterator<Integer> {
        Stack<NestedInteger> stack;

        public NestedIterator(List<NestedInteger> nestedList) {
            stack = new Stack<>();
            for (int i = nestedList.size() - 1; i >= 0; i--) {
                stack.push(nestedList.get(i));
            }
        }

        @Override
        public Integer next() {
            return stack.pop().getInteger();
        }

        @Override
        public boolean hasNext() {
            while (!stack.isEmpty()) {
                NestedInteger cur = stack.peek();
                if (cur.isInteger()) {
                    return true;
                }

                stack.pop();

                for (int i = cur.getList().size() - 1; i >= 0; i--) {
                    stack.push(cur.getList().get(i));
                }
            }

            return false;
        }
    }

    /**
         Solution 2

         Use iterator, a better solution
     **/
    public class NestedIterator1 implements Iterator<Integer> {
        Stack<Iterator<NestedInteger>> stack = new Stack<>();
        Integer current;

        public NestedIterator1(List<NestedInteger> nestedList) {
            if (nestedList == null)
                return;

            stack.push(nestedList.iterator());
        }

        @Override
        public Integer next() {
            Integer result = current;
            current = null;
            return result;
        }

        @Override
        public boolean hasNext() {
            while (!stack.isEmpty() && current == null) {
                Iterator<NestedInteger> top = stack.peek();
                if (!top.hasNext()) {
                    stack.pop();
                    continue;
                }

                NestedInteger n = top.next();
                if (n.isInteger()) {
                    current = n.getInteger();
                    return true;
                } else {
                    stack.push(n.getList().iterator());
                }
            }

            return false;
        }
    }

}
