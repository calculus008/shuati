package leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.TreeMap;

public class LE_716_Max_Stack {
    /**
     * Design a max stack that supports push, pop, top, peekMax and popMax.
     *
     * push(x) -- Push element x onto stack.
     * pop() -- Remove the element on top of the stack and return it.
     * top() -- Get the element on the top.
     * peekMax() -- Retrieve the maximum element in the stack.
     * popMax() -- Retrieve the maximum element in the stack, and remove it.
     *             If you find more than one maximum elements, only remove
     *             the top-most one.
     *
     * Example 1:
     * MaxStack stack = new MaxStack();
     * stack.push(5);
     * stack.push(1);
     * stack.push(5);
     * stack.top(); -> 5
     * stack.popMax(); -> 5
     * stack.top(); -> 1
     * stack.peekMax(); -> 5
     * stack.pop(); -> 1
     * stack.top(); -> 5
     *
     * Note:
     * -1e7 <= x <= 1e7
     * Number of operations won't exceed 10000.
     * The last four operations won't be called when stack is empty.
     *
     * Easy
     */

    /**
     * Two Stacks
     *
     * Time and Space : O(n)
     */
    class MaxStack1 {
        Stack<Integer> s1;
        Stack<Integer> s2;

        /** initialize your data structure here. */
        public MaxStack1() {
            s1 = new Stack<>();
            s2 = new Stack<>();
        }

        public void push(int x) {
            if (s2.isEmpty() || x >= s2.peek()) {
                s2.push(x);
            }
            s1.push(x);
        }

        public int pop() {
            int ret = s1.pop();
            if (ret == s2.peek()) {
                s2.pop();
            }
            return ret;
        }

        public int top() {
            return s1.peek();
        }

        public int peekMax() {
            return s2.peek();
        }

        public int popMax() {
            int ret = s2.pop();
            Stack<Integer> s3 = new Stack<>();

            while (s1.peek() != ret) {
                s3.push(s1.pop());
            }
            s1.pop();
            while (!s3.isEmpty()) {
                /**
                 * !!!
                 * This is the only tricky part, if we do:
                 * "s1.push(s3.push)"
                 * it does not use the logic in this.push() to
                 * push max value into s2, so it's not consistent.
                 *
                 * So we have to call this.push() instead.
                 */
                push(s3.pop());//!!!
            }

            return ret;
        }
    }

    /**
     * !!!
     * Solution 2
     *
     * Using structures like Array or Stack will never let us popMax quickly.
     * We turn our attention to tree and linked-list structures that have a
     * lower time complexity for removal, with the aim of making popMax faster
     * than O(N) time complexity.
     *
     * Say we have a double linked list as our "stack". This reduces the problem
     * to finding which node to remove, since we can remove nodes in O(1) time.
     *
     * We can use a TreeMap mapping values to a list of nodes to answer this question.
     * TreeMap can find the largest value, insert values, and delete values, all in
     * O(logN) time.
     */
    class MaxStack2 {
        TreeMap<Integer, List<Node>> map;
        DoubleLinkedList dll;

        public MaxStack2() {
            map = new TreeMap();
            dll = new DoubleLinkedList();
        }

        public void push(int x) {
            Node node = dll.add(x);

            if(!map.containsKey(x)) {
                map.put(x, new ArrayList<Node>());
            }

            map.get(x).add(node);
        }

        public int pop() {
            int val = dll.pop();
            List<Node> L = map.get(val);
            L.remove(L.size() - 1);

            if (L.isEmpty()) {
                map.remove(val);
            }

            return val;
        }

        public int top() {
            return dll.peek();
        }

        public int peekMax() {
            return map.lastKey();//!!!
        }

        public int popMax() {
            int max = peekMax();
            List<Node> L = map.get(max);

            Node node = L.remove(L.size() - 1);
            dll.unlink(node);

            if (L.isEmpty()) {
                map.remove(max);
            }

            return max;
        }
    }

    class DoubleLinkedList {
        Node head, tail;

        public DoubleLinkedList() {
            head = new Node(0);
            tail = new Node(0);
            head.next = tail;
            tail.prev = head;
        }

        public Node add(int val) {
            Node x = new Node(val);
            x.next = tail;
            x.prev = tail.prev;
            tail.prev = tail.prev.next = x;
            return x;
        }

        public int pop() {
            return unlink(tail.prev).val;
        }

        public int peek() {
            return tail.prev.val;
        }

        public Node unlink(Node node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            return node;
        }
    }

    class Node {
        int val;
        Node prev, next;
        public Node(int v) {val = v;}
    }
}
