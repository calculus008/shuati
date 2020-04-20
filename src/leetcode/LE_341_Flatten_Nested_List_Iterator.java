package leetcode;

import java.util.*;

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
     */

    /**
     * Practice of Solution2
     *
     * Let N be the total number of integers within the nested list,
     * L be the total number of lists within the nested list,
     * D be the maximum nesting depth (maximum number of lists inside each other).
     *
     * Time :
     * Constructor : O(1)
     * next() : O(1)
     * hasNext() : O(L / N) or O(1)
     *             If the top of the stack is an integer, then this function does nothing; taking O(1) time.
     *
     *             Otherwise, it needs to process the stack until an integer is on top. The best way of analyzing
     *             the time complexity is to look at the total cost across all calls and then divide by the number
     *             of calls made. Once the iterator is exhausted we must have seen every integer at least once,
     *             costing O(N) time. Additionally, it has seen every list (except the first) on the stack at least
     *             once also, so this costs O(L)time. Adding these together, we get O(N + L)time.
     *
     *             The amortized time  is the total cost, O(N + L), divided by the number of times it's called.
     *             In order to get all integers, we need to have called it N times. This gives us an amortized
     *             time complexity of O(N + L) / N = O(N / N + L / N) = O(L / N)
     *
     * Space : O(D)
     *
     */
    public class NestedItrator_Iterator_Stack_Solution_Practice {
        Deque<Iterator<NestedInteger>> stack;
        Integer cur;

        public NestedItrator_Iterator_Stack_Solution_Practice(List<NestedInteger> nestedList) {
            stack = new ArrayDeque<>();
            cur = null;
            stack.push(nestedList.iterator());
        }

        public Integer next() {
            if (cur != null) {
                Integer res = cur;
                cur = null;
                return res;
            }

            return null;
        }

        public boolean hasNext() {
            while (cur == null && !stack.isEmpty()) {
                Iterator<NestedInteger> it = stack.peek();
                if (!it.hasNext()) {
                    stack.pop();
                    continue;
                }

                NestedInteger n = it.next();
                if (n.isInteger()) {
                    cur = n.getInteger();
                    return true;
                } else {
                    stack.push(n.getList().iterator());
                }
            }

            return false;
        }
    }


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

        The stack only stores iterator, it does not store NestedInteger,
        therefore we need to have another global variable "current"
        to save the current number. NOTICE, "current" is an Integer
        type object, so we can set it to null to tell the current
        status of the number.

        NestedInteger is nested, when we want to flatten it, we can only
        do output when current NestedInteger is an Integer (not a list).
        Therefore we have to go to the bottom of the nested structure,
        save the iterator of each level in stack, whose FILO sequence
        is what we need here.

        hasNext() actually finds the next Integer, put int in "current"
        (current is not null then) next() returns value in "current"
        and puts it back to null.


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
            // or call next() first to make sure next is available
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

    /**
     * Similar to LE_339_Nested_List_Weight_Sum
     *
     * A question before this is the Nested List Weight Sum, and it requires recursion to solve.
     * As it carries to this problem that we will need recursion to solve it. But since we need
     * to access each NestedInteger at a time, we will use a stack to help.
     *
     * In the constructor, we push all the nestedList into the stack from back to front, so when
     * we pop the stack, it returns the very first element. Second, in the hasNext() function,
     * we peek the first element in stack currently, and if it is an Integer, we will return true
     * and pop the element. If it is a list, we will further flatten it. This is iterative version
     * of flatting the nested list. Again, we need to iterate from the back to front of the list.
     */

    public class NestedIterator3 implements Iterator<Integer> {
        Stack<NestedInteger> stack = new Stack<>();

        public NestedIterator3(List<NestedInteger> nestedList) {
            for(int i = nestedList.size() - 1; i >= 0; i--) {
                stack.push(nestedList.get(i));
            }
        }

        @Override
        public Integer next() {
            return stack.pop().getInteger();
        }

        @Override
        public boolean hasNext() {
            while(!stack.isEmpty()) {
                NestedInteger curr = stack.peek();
                if(curr.isInteger()) {
                    return true;
                }
                stack.pop();
                for(int i = curr.getList().size() - 1; i >= 0; i--) {
                    stack.push(curr.getList().get(i));
                }
            }
            return false;
        }
    }

    /**
     * Follow up for NestedIterator3
     * This is an optimization especially for non-balanced tree.
     * Idea is to save current traverse index of NestedInteger
     * list instead of flattening whole 'getList()'
     */
    class NestedIterator4 {
        class Pair {
            NestedInteger ni;
            int idx;

            public Pair(NestedInteger ni, int idx) {
                this.ni = ni;
                this.idx = idx;
            }
        }

        Stack<Pair> stack;

        public NestedIterator4(List<NestedInteger> nestedList) {
            stack = new Stack<>();
            for (int i = nestedList.size() - 1; i >= 0 ; i--) {
                stack.push(new Pair(nestedList.get(i), 0));
            }
        }

        public Integer next() {
            return stack.pop().ni.getInteger();
        }

        /**
         * In NestedIterator3, in hasNext(), we flatten a list completely,
         * extract every NestedInteger and push it into the stack.
         *
         * 虽然average hasNext()的时间复杂度是O(1), 但是极端情况下，hasNext()的时间复杂度是O(n)。
         *
         * 这里我们加index，免去了flatten的时间
         *
         * Here, we use Pair.getIdx to remember the index in the list, every
         * call to hasNext() will only extract one element from list and
         * push into stack, then increase getIdx.
         *
         * This works better for 'none-balanced' structure -
         * there are many elements in one list element.
         *
         * In this case, hasNext() won't spend time to extract all elements
         * (say 10000), it just get the next one, push into stack so that
         * next() can retrieve it.
         */
        public boolean hasNext() {
            while (!stack.isEmpty() && !stack.peek().ni.isInteger()) {
                /**
                 * From 2nd condition above, here, top element of stack
                 * is not an Integer, must be a list.
                 *
                 * Then, first check if the list is already at the end.
                 */
                if (stack.peek().idx == stack.peek().ni.getList().size()) {
                    stack.pop();
                    continue;
                }

                Pair p = stack.peek();
                stack.push(new Pair(p.ni.getList().get(p.idx++), 0));
            }

            return stack.isEmpty() ? false : true;
        }
    }

    /**
     * Here are a few reasons why Deque is better than Stack:
     *
     * Object oriented design - Inheritance, abstraction, classes and interfaces: Stack is a class, Deque is an interface.
     * Only one class can be extended, whereas any number of interfaces can be implemented by a single class in Java
     * (multiple inheritance of type). Using the Deque interface removes the dependency on the concrete Stack class and
     * its ancestors and gives you more flexibility, e.g. the freedom to extend a different class or swap out different
     * implementations of Deque (like LinkedList, ArrayDeque).
     *
     * Inconsistency: Stack extends the Vector class, which allows you to access element by index. This is inconsistent
     * with what a Stack should actually do, which is why the Deque interface is preferred (it does not allow such operations)
     * --its allowed operations are consistent with what a FIFO or LIFO data structure should allow.
     *
     * Performance: The Vector class that Stack extends is basically the "thread-safe" version of an ArrayList.
     * The synchronizations can potentially cause a significant performance hit to your application. Also, extending other
     * classes with unneeded functionality (as mentioned in #2) bloat your objects, potentially costing a lot of extra memory
     * and performance overhead.
     *
     * There are probably other good reasons.. these are the ones I think are most important.
     *
     * In several of my interviews, I used the Deque data structure instead of a Stack and mentioned why.
     * It only took a few seconds, but I'm sure the interviewer was happy about it. (I ended up with 12 offers,
     * in case you're curious.)
     *
     * https://dzone.com/articles/why-future-generations-will
     */

}
