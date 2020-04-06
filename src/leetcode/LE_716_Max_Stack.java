package leetcode;

import java.util.*;

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
     *
     * Solution #1 can't be linear in the worst case:
     * pushing N/2 numbers in descending order and then popMax N/2 times.
     * Since the solution enumerates the whole stack on every popMax,
     *
     * O(N) for popMax(), O(1) for other functions
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

        /**
         * O(logn)
         */
        public void push(int x) {
            Node node = dll.add(x);

            if(!map.containsKey(x)) {
                map.put(x, new ArrayList<Node>());
            }

            map.get(x).add(node);
        }

        /**
         * O(1) (if do dist.remove(), it will be O(logn)
         */
        public int pop() {
            int val = dll.pop();
            List<Node> L = map.get(val);
            L.remove(L.size() - 1);

            if (L.isEmpty()) {
                map.remove(val);
            }

            return val;
        }

        /**
         * O(1)
         */
        public int top() {
            return dll.peek();
        }

        /**
         * log(n)
         */
        public int peekMax() {
            return map.lastKey();//!!!
        }

        /**
         * log(n)
         */
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

        /**
         * simulate push()
         * always add at the tail
         */
        public Node add(int val) {
            Node x = new Node(val);
            x.next = tail;
            x.prev = tail.prev;
            tail.prev = tail.prev.next = x;
            return x;
        }

        /**
         *
         */
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

    /**
     * Java solution beat 99.37%,
     * O(n) push,  O(1) for rest using double linked list
     *
     * https://leetcode.com/problems/max-stack/discuss/125771/Java-solution-beat-99.37-O(n)-push-O(1)-for-rest-using-double-linked-list
     */
    class MaxStack3 {
        class StackNode {
            int val;
            StackNode stack_next;
            StackNode stack_prev;
            StackNode max_next;
            StackNode max_prev;

            public StackNode(int val) {
                this.val = val;
                max_next = max_prev = stack_next = stack_prev = null;
            }
        }

        StackNode stack;
        StackNode max;

        public MaxStack3() {
            stack = null;
        }

        //O(n)
        public void push(int x) {
            if(stack == null) {
                max = stack = new StackNode(x);
            } else {
                StackNode node = new StackNode(x);

                node.stack_next = stack;
                stack.stack_prev = node;
                stack = node;

                StackNode point = max;

                while(point != null) {
                    if(point.val <= x) {
                        if(point.max_prev != null) {
                            point.max_prev.max_next = node;
                        } else {
                            max = node;
                        }

                        node.max_prev = point.max_prev;
                        node.max_next = point;
                        point.max_prev = node;
                        return;
                    } else {
                        if(point.max_next == null) {
                            point.max_next = node;
                            node.max_prev = point;
                            return;
                        }
                    }
                    point = point.max_next;
                }
            }
        }

        public int pop() {
            StackNode node = stack;

            stack = stack.stack_next;
            if(stack != null) {
                stack.stack_prev = null;
            }

            if(node == max) {
                max = max.max_next;
                if(max != null) {
                    max.max_prev = null;
                }
            } else {
                node.max_prev.max_next = node.max_next;
                if(node.max_next != null) {
                    node.max_next.max_prev = node.max_prev;
                }
            }

            return node.val;
        }

        public int top() {
            return stack.val;
        }

        public int peekMax() {
            return max.val;
        }

        public int popMax() {
            StackNode node = max;


            max = max.max_next;
            if(max != null) {
                max.max_prev = null;
            }

            if(node == stack) {//if the max node is the top one in stack
                stack = stack.stack_next;
                if(stack != null) {
                    stack.stack_prev = null;
                }
            } else {
                node.stack_prev.stack_next = node.stack_next;
                if(node.stack_next != null) {
                    node.stack_next.stack_prev = node.stack_prev;
                }
            }

            return node.val;
        }
    }

    /**
     * My solution for Interviews.Linkedin's follow up which requires O(1) for popMax()
     *
     * O(1) for popMax(), peekMax() and top().
     * O(n) for push() and pop().
     *
     * Double LinkedList + LinkedList
     *
     * DLL : simulate Stack, top of the stack is tracked by tail, push() always append node at the tail.
     * LinkedList : maxList, sorted in descending order by insertion sort,
     *              node with max value is always at index 0.
     *              It saves a reference to each DLL node.
     *
     */
    class MaxStack {
        class Node {
            Node pre;
            Node next;
            int val;

            public Node(int val) {
                this.val = val;
            }
        }

        Node head;
        Node tail;
        LinkedList<Node> maxList;

        public MaxStack() {
            head = new Node(0);
            tail = new Node(0);
            head.next = tail;
            tail.pre = head;
            maxList = new LinkedList<>();
        }

        //O(n)
        public void push(int x) {
            Node cur = new Node(x);
            addNode(cur);

            Node curRef = cur;
            if (maxList.size() == 0) {
                maxList.add(curRef);
            } else {
                /**
                 * Insertion sort logic, keep max at the first (index 0)
                 */
                int idx = 0;
                if (x >= maxList.get(0).val) {
                    maxList.add(0, curRef);
                } else {
                    while (idx < maxList.size() && maxList.get(idx).val > x) {
                        idx++;
                    }

                    maxList.add(idx, curRef);
                }
            }
        }

        //O(n)
        public int pop() {
            int value = tail.pre.val;
            removeNode(tail.pre);

            /**
             * Max is at index 0, search from the start
             */
            for (int i = 0; i < maxList.size() ; i++) {
                if (value == maxList.get(i).val) {
                    maxList.remove(i);//LinkedList remove() is O(1)
                    break;
                }
            }

            return value;
        }

        //O(1)
        public int top() {
            return tail.pre.val;
        }

        //O(1)
        public int peekMax() {
            return maxList.peekFirst().val;
        }

        //O(1)
        public int popMax() {
            Node max = maxList.pollFirst();
            removeNode(max);

            return max.val;
        }

        //O(1)
        private void removeNode(Node node) {
            node.pre.next = node.next;
            node.next.pre = node.pre;
            node.pre = null;
            node.next = null;
        }

        //add node at the end (tail)
        private void addNode(Node node) {
            node.next = tail;
            node.pre = tail.pre;
            tail.pre.next = node;
            tail.pre = node;
        }
    }

}
