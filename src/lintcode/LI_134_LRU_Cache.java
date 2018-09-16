package lintcode;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuank on 8/17/18.
 */
public class LI_134_LRU_Cache {
    /**
         Design and implement a data structure for Least Recently Used (LRU) cache.
         It should support the following operations: get and set.

         get(key) - Get the value (will always be positive) of the key if the key exists in the cache,
         otherwise return -1.

         set(key, value) - Set or insert the value if the key is not already present. When the cache
         reached its capacity, it should invalidate the least recently used item before inserting a new item.

         Hard
     */

    /**
     * Solution 1: Singly Linked List
     */
    public class LRUCache_1 {
        class ListNode {
            public int key, val;
            public ListNode next;

            public ListNode(int key, int val) {
                this.key = key;
                this.val = val;
                this.next = null;
            }
        }

        private int capacity, size;
        private ListNode dummy, tail;
        private Map<Integer, ListNode> keyToPrev;

        /*
        * @param capacity: An integer
        */
        public LRUCache_1(int capacity) {
            this.capacity = capacity;
            this.keyToPrev = new HashMap<Integer, ListNode>();
            this.dummy = new ListNode(0, 0);
            this.tail = this.dummy;
        }

        private void moveToTail(int key) {
            ListNode prev = keyToPrev.get(key);
            ListNode curt = prev.next;

            if (tail == curt) {
                return;
            }

            prev.next = prev.next.next;
            tail.next = curt;

            if (prev.next != null) {
                keyToPrev.put(prev.next.key, prev);
            }
            keyToPrev.put(curt.key, tail);

            tail = curt;
        }

        /*
         * @param key: An integer
         * @return: An integer
         */
        public int get(int key) {
            if (!keyToPrev.containsKey(key)) {
                return -1;
            }

            moveToTail(key);

            // the key has been moved to the end
            return tail.val;
        }

        /*
         * @param key: An integer
         * @param value: An integer
         * @return: nothing
         */
        public void set(int key, int value) {
            // get method will move the key to the end of the linked list
            if (get(key) != -1) {
                ListNode prev = keyToPrev.get(key);
                prev.next.val = value;
                return;
            }

            if (size < capacity) {
                size++;
                ListNode curt = new ListNode(key, value);
                tail.next = curt;
                keyToPrev.put(key, tail);

                tail = curt;
                return;
            }

            // replace the first node with new key, value
            ListNode first = dummy.next;
            keyToPrev.remove(first.key);

            first.key = key;
            first.val = value;
            keyToPrev.put(key, dummy);

            moveToTail(key);
        }
    }


    /**
     * Solution 2, Map + Double Linked List
     */
    public class LRUCache_2 {
        private class Node{
            Node prev;
            Node next;
            int key;
            int value;

            public Node(int key, int value) {
                this.key = key;
                this.value = value;
                this.prev = null;
                this.next = null;
            }
        }

        private int capacity;
        private HashMap<Integer, Node> hs = new HashMap<Integer, Node>();
        private Node head = new Node(-1, -1);
        private Node tail = new Node(-1, -1);

        public LRUCache_2(int capacity) {
            this.capacity = capacity;
            tail.prev = head;
            head.next = tail;
        }

        public int get(int key) {
            if( !hs.containsKey(key)) {
                return -1;
            }

            // remove current
            Node current = hs.get(key);
            current.prev.next = current.next;
            current.next.prev = current.prev;

            // move current to tail
            move_to_tail(current);

            return hs.get(key).value;
        }

        public void set(int key, int value) {
            // get 这个方法会把key挪到最末端，因此，不需要再调用 move_to_tail
            if (get(key) != -1) {
                hs.get(key).value = value;
                return;
            }

            if (hs.size() == capacity) {
                hs.remove(head.next.key);
                head.next = head.next.next;
                head.next.prev = head;
            }

            Node insert = new Node(key, value);
            hs.put(key, insert);
            move_to_tail(insert);
        }

        private void move_to_tail(Node current) {
            current.prev = tail.prev;
            tail.prev = current;
            current.prev.next = current;
            current.next = tail;
        }
    }

    /**
     * Solution 3, same as Solution 2
     * Changes:
     *
     * In get(), use moveToTail(), important, move to tail has 2 actions :
     * 1.remove node from its current loctaion.
     * 2.add node to tail
     *
     * In set(),
     */
    public class LRUCache3 {
        class ListNode{
            int val, key;
            ListNode pre, next;

            public ListNode(int key, int val) {
                this.key = key;
                this.val = val;
            }
        }

        Map<Integer, ListNode> map;
        // int count; //!!!no need to use count, check cache size can use "map.size()"
        int capacity;
        ListNode head = new ListNode(-1, -1);
        ListNode tail = new ListNode(-1, -1);

        public LRUCache3(int capacity) {
            // count = 0;
            this.capacity = capacity;
            map = new HashMap<>();
            head.next = tail;
            tail.pre = head;
        }

        public int get(int key) {
            if (!map.containsKey(key)) {
                return -1;
            }

            ListNode cur = map.get(key);
            moveToTail(cur);
            return cur.val;
        }

        public void set(int key, int value) {
            if (map.containsKey(key)) {
                ListNode cur = map.get(key);
                cur.val = value;
                moveToTail(cur);
                return;
            }

            if (map.size() >= capacity) {
                removeHead();
            }

            ListNode node = new ListNode(key, value);

            map.put(key, node);
            addNodeToTail(node);
        }

        private void removeHead() {
            map.remove(head.next.key);//!!! This is where key in ListNode must be used
            removeNode(head.next);
        }

        private void removeNode(ListNode node) {
            node.pre.next = node.next;
            node.next.pre = node.pre;
        }

        private void addNodeToTail(ListNode node) {
            node.next = tail;
            node.pre = tail.pre;
            tail.pre.next = node;
            tail.pre = node;
        }

        private void moveToTail(ListNode node) {
            removeNode(node);
            addNodeToTail(node);
        }
    }
}
