package leetcode;

import java.util.HashMap;

/**
 * Created by yuank on 8/28/18.
 */
public class LE_146_LRU_Cache {
    /**
         Design and implement a data structure for Least Recently Used (LRU) cache.
         It should support the following operations: get and put.

         get(key) - Get the value (will always be positive) of the key if the key exists in the cache, otherwise return -1.
         put(key, value) - Set or insert the value if the key is not already present. When the cache reached its capacity,
         it should invalidate the least recently used item before inserting a new item.

         Follow up:
         Could you do both operations in O(1) time complexity?

         Example:

            LRUCache cache = new LRUCache( 2 );

            cache.put(1, 1);
            cache.put(2, 2);
            cache.get(1);       // returns 1
            cache.put(3, 3);    // evicts key 2
            cache.get(2);       // returns -1 (not found)
            cache.put(4, 4);    // evicts key 1
            cache.get(1);       // returns -1 (not found)
            cache.get(3);       // returns 3
            cache.get(4);       // returns 4

        Hard
     **/

    /**
         The problem can be solved with a hashtable that keeps track of the keys and its values in the double linked list.
         One interesting property about double linked list is that the node can remove itself without other reference.
         In addition, it takes constant time to add and remove nodes from the head or tail.

         One particularity about the double linked list that I implemented is that I create a pseudo head and tail
         to mark the boundary, so that we don't need to check the NULL node during the update.
         This makes the code more concise and clean, and also it is good for the performance as well.

         The problem can be solved with a hashtable that keeps track of the keys and its values in the double linked list.
         One interesting property about double linked list is that the node can remove itself without other reference.
         In addition, it takes constant time to add and remove nodes from the head or tail.

         One particularity about the double linked list that I implemented is that I create a pseudo head and tail
         to mark the boundary, so that we don't need to check the NULL node during the update. This makes the code
         more concise and clean, and also it is good for the performance as well.
     **/

    class LRUCache {
        class Node {
            Node pre, next;
            /**
             Must have key in Node object,
             it is used to track key value when we want to delelete
             the entry from map in put() (when capacity is full)
             **/
            int key, val;
        };

        private void addNode(Node node) {
            node.pre = head;
            node.next = head.next;
            head.next.pre = node;
            head.next = node;
        }

        private void removeNode(Node node) {
            node.pre.next = node.next;
            node.next.pre = node.pre;
        }

        private void moveToHead(Node node) {
            this.removeNode(node);
            this.addNode(node);
        }


        private HashMap<Integer, Node> map;

        /**
         No need to fully implement a Double Linkedlist class,
         we only need a Node class for the DL, then head and tail is private to current
         soution class, all operation can be satisfied.So DL we have is actually like :

         head <=> n1 <=> n2 <=> n3 <=> tail

         The start and end nodes are always head and tail, this is easier to handle all the null cases
         **/
        private Node head, tail;
        private int capacity;
        private int count; //track how many nodes we have in the list

        public LRUCache(int capacity) {
            map = new HashMap<>();
            this.capacity = capacity;
            count = 0;

            head = new Node();
            tail = new Node();
            head.pre = null;
            head.next = tail;
            tail.pre = head;
            tail.next = null;
        }

        public int get(int key) {
            if (!map.containsKey(key)) return -1;

            Node cur = map.get(key);
            int res = cur.val;
            moveToHead(cur);
            return res;
        }

        public void put(int key, int value) {
            if (map.containsKey(key)) {
                Node cur = map.get(key);
                cur.val = value;
                moveToHead(cur);
                return;
            }

            if (count >= capacity) {
                Node m = tail.pre;

                //!!! Remember to update the 2 data structures (map and DL) in each operation, here is DELETE
                map.remove(m.key);
                removeNode(m);//always remove the tail.pre, this is teh least recent used one

                count--;
            }

            Node node = new Node();
            node.val = value;
            node.key = key;//!!!

            //!!! Remember to update the 2 data structures (map and DL) in each operation, here is ADD
            map.put(key, node);//!!!
            addNode(node);//!!!

            count++;
            return;
        }
    }

}
