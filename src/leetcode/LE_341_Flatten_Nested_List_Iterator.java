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
            for (int i = nestedList.size() - 1; i >= 0; i--) {//!!! going from back to front
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

        Key Insights:
        The stack only stores iterator, it does not store NestedInteger, therefore we need to have another global variable "current"
        to save the current number. NOTICE, "current" is an Integer type object, so we can set it to null to tell the current status
        of the number.

        NestedInteger is nested, when we want to flatten it, we can only do output when current NestedInteger is an Integer (not a list).
        Therefore we have to go to the bottom of the nested structure, save the iterator of each level in stack, whose FILO sequence
        is what we need here.

        hasNext() actually finds the next Integer, put int in "current" (current is not null then)
        next() returns value in "current" and puts it back to null.


        Example :

        input =  [[1,1],2,[3,4]]

         After constructor:
         stack                    current = null
         ------------------
         i : Iterator for input

        1.hasNext():
         top = i (peek())
         i.next() is [1,1]

         stack
         ------------------
         i1 : Iterator for [1,1]
         i : Iterator for input

         Keep looping

         top = i1
         i1.next = 1, we have an Integer, set current = 1, return true;


       2.next(): return current (value is 1), set current back to null.

       3.hasNext():
        top = i1;
        top.next = 1 (2nd "1")
        set current = 1, return true;

       4.next(): return current (value is 1), set current back to null.

       5.hasNext():
       top = i1, i1 is empty now. pop out.
       top = i, top.next = 2, set current = 2, return true;

       6.next(): return current (value is 2), set current back to null.

       7.hasNext():
       top = i1;
       top.next = [3,4], not an Integer, push its i2 (Iterator of [3,4])

         stack
         ------------------
         i1 : Iterator for [3,4]
         i : Iterator for input

       keep looping

       top = i2;
       i2.next = 3, we have an Integer, set current = 3, return true;

       8.next(): return current (value is 3), set current back to null.

       9.hasNext():
       top is i2, i2.next = 4, set current = 4, return true;

       10.next(): return current (value is 4), set current back to null;

       11.hasNext();
       top = i2, i2 is empty (i2.hasNext == false), pop out;
       keep looping;
       top = i, i is empty (i.hasNext == false), pop out;
       stack is empty, end loop;
       return false

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
                Iterator<NestedInteger> top = stack.peek();//!!! peek(), NOT pop()
                if (!top.hasNext()) {//the list is empty
                    stack.pop();//remove iterator of the empty list
                    continue;
                }

                //After last if, we know top.hasNext() == true. Then we can use top.next() here.
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
