package leetcode;

import java.util.Stack;

/**
 * Created by yuank on 3/19/18.
 */
public class LE_155_Min_Stack {
    /**
        Design a stack that supports push, pop, top, and retrieving the minimum element in constant time.

        push(x) -- Push element x onto stack.
        pop() -- Removes the element on top of the stack.
        top() -- Get the top element.
        getMin() -- Retrieve the minimum element in the stack.
        Example:
        MinStack minStack = new MinStack();
        minStack.push(-2);
        minStack.push(0);
        minStack.push(-3);
        minStack.getMin();   --> Returns -3.
        minStack.pop();
        minStack.top();      --> Returns 0.
        minStack.getMin();   --> Returns -2.
     */

    //Solution 2 : one stack
    class MinStack_one_stack_push_twice {

        /**
         用min保存当前最小值，push时只要x小于当前min值，先压入min, 再压入x, 然后改变min值。就是说，保留上一个min值，以便在pop时恢复那个min值。

         3 5 0 -1 -1 4 -1
         keep push (bottom of the stack is MAX_VALUE, inital value of min)
         min    3   0  -1    -1      -1
         stack  3 5 3 0 0 -1 -1 -1 4 -1 -1

         keep pop
         min   3   0  -1    -1
         stack 3 5 3 0 0 -1 -1 -1 4

         min   3   0  -1    -1
         stack 3 5 3 0 0 -1 -1 -1

         min   3   0  -1
         stack 3 5 3 0 0 -1

         min   3   0
         stack 3 5 3 0

         min   3   0
         stack 3 5 3 0

         min   3
         stack 3 5

         min   3
         stack 3
         */

        private int min;
        private Stack<Integer> stack;

        public MinStack_one_stack_push_twice() {
            stack = new Stack<>();
            min = Integer.MAX_VALUE;
        }

        public void push(int x) {
            if (x <= min) { //!!!"<="
                stack.push(min);
                min = x;
            }
            stack.push(x);
        }

        public void pop() {
            /**
             int cur = stack.pop(); //first pop
             if (cur == min) {      //if current min value is popped
             min = stack.pop();  //2nd pop -> previous min value, set it to min
             }
             */
            if (stack.pop() == min) { //!!! "=="
                min = stack.pop();
            }
        }

        public int top() {
            return stack.peek();
        }

        public int getMin() {
            return min;
        }
    }

    //Solution 1 : Two Stacks
    class MinStack1 {

        private Stack<Integer> stack;
        private Stack<Integer> minStack;

        /**
         * initialize your data structure here.
         */
        public MinStack1() {
            stack = new Stack<>();
            minStack = new Stack<>();
        }

        public void push(int x) {
            stack.push(x);
            if (minStack.isEmpty() || (!minStack.isEmpty() && x <= minStack.peek())) {
                minStack.push(x);
            }
        }

        public void pop() {
            int x = stack.pop();
            if (!minStack.isEmpty() && x <= minStack.peek()) {
                minStack.pop();
            }
        }

        public int top() {
            return stack.peek();
        }

        public int getMin() {
            return minStack.peek();
        }
    }

    /**
     * Using linked list as stack, one stack, save value/min pair.
     *
     * If interviewer asks not using stack in java lib, we can use this solution.
     *
     * followup是： pop和push‍‌‍‍‍‍‌‌‌‍‌‌‍‌‍‌‌‌‌的时间复杂度优化
     */
    class MinStack3 {
        class Node {
            int value;
            int min;
            Node next;

            Node(int x, int min) {
                this.value = x;
                this.min = min;
                next = null;
            }
        }

        Node head;

        public void push(int x) {
            if (null == head) {
                head = new Node(x, x);
            } else {
                Node n = new Node(x, Math.min(x, head.min));
                n.next = head;
                head = n;
            }
        }

        public void pop() {
            if (head != null)
                head = head.next;
        }

        public int top() {
            if (head != null)
                return head.value;
            return -1;
        }

        public int getMin() {
            if (null != head)
                return head.min;
            return -1;
        }
    }

    /**
     * !!!
     * https://leetcode.com/problems/min-stack/discuss/49031/Share-my-Java-solution-with-ONLY-ONE-stack
     *
     * Memory optimal solution, use one stack and it saves n numbers (not 2 * n)
     *
     * push 5
     * stack : 0
     * min : 5
     *
     * push 2
     * stack :  0, 3
     * min : 2
     *
     * top() : stack.peek() = 3 > 0, return 2
     *
     * push 4
     * stack: 0, 3, -2
     * min : 2
     *
     * top() : stack.peek() = -2 < 0, return 2 - (-2) = 4
     *
     * pop()
     * stack: 0, 3
     * min: 2
     *
     * pop()
     * stack : 0
     * min: 2 + 3 = 5
     *
     */
    class MinStack4 {
        /**
         *!!!
         * Both stack and min should use long,
         * since we need to do calculation and try to avoid integer overflow
         */
        private long min;
        private Stack<Long> stack;

        public MinStack4() {
            stack = new Stack<>();
            min = 0;
        }

        public void push(int x) {
            if (stack.isEmpty()) {
                stack.push(0L);
                min = x;
            } else {
                long diff = min - x;
                stack.push(diff);
                min = Math.min(min, x);
            }
        }

        /**
         * we save min - x in stack :
         * if it's > 0, meaning, incoming x is smaller than min, x will become new min
         * if it's <= 0, min value will not change.
         *
         * So when we pop, if top value x in stack is positive, meaning, we push the value,
         * we have a new min value, there's a change in min value.So we need to recover
         * the previous min value, which is current min plus x
         */
        public void pop() {
            long x = stack.pop();
            if (x > 0) {
                min += x;
            }
        }

        public int top() {
            if (stack.peek() > 0) {
                return (int)min;
            }

            return (int)(min - stack.peek());
        }

        public int getMin() {
            return (int)min;
        }
    }
}
