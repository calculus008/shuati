package leetcode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by yuank on 11/29/18.
 */
public class LE_432_All_O_One_Data_Structure {
    /**
         Implement a data structure supporting the following operations:

         Inc(Key) - Inserts a new key with value 1. Or increments an existing key by 1.
                    Key is guaranteed to be a non-empty string.
         Dec(Key) - If Key's value is 1, remove it from the data structure.
                    Otherwise decrements an existing key by 1. If the key does not exist,
                    this function does nothing. Key is guaranteed to be a non-empty string.
         GetMaxKey() - Returns one of the keys with maximal value. If no element exists,
                       return an empty string "".
         GetMinKey() - Returns one of the keys with minimal value.
                       If no element exists, return an empty string "".

         Challenge: Perform all these in O(1) time complexity.

         Hard
     */

    /**
     * Design + HashMap
     *
     * Key implementation tricks:
     * 1.Use two HashMap + DDL (buckets)
     *
     *   countMap  - key to its values (will be increased/decreased in inc() and dec())
     *   nodeMap   - values to node (so that we can find the node (bucket) in DDL with a
     *               given value in O(1)).
     *
     *   DLL - kind of like insertion sort, for a new key, insert at the head.
     *         maintain order in inc() and dec(). So, max key is always at the
     *         end of the DDL and min key is always at the head of the DDL.
     *
     *         The node in DDL basically functions as a bucket, it contains the
     *         list of keys that have the same value.
     *
     *   这题和LE_460_LFU_Cache有类似的地方，都是用bucket来存放同样frequency的keys.
     *
     *   LE_460_LFU_Cache 我们只关心minFreq, 并且要处理"least recent", 所以用HashMap
     *   to dist frequency to LinkedHashSet.
     *
     *   对这道题，关键要求是要能同时getMaxKey() and getMinKey() in O(1), 所以要用
     *   DLL, 要动态的维护其order, 保证最小和最大各在头和尾。
     *
     * 2.Add head and tail to list : head <=> n1 <=> n2 .... <=> tail
     *   This way there's always a node for pre and next pointers,
     *   simplify the logic (no need to check null pointer for pre and next).
     *   Also, you don't need to worry about updating tail and head. It will be
     *   there in the list.
     *
     * 3.Common list operation
     *   removeNodeFromList
     *   addNodeAfter
     *
     * 4.Common function for node
     *   removeKeyFromNode
     *   changeKey
     *
     * Reference
     * https://leetcode.com/problems/all-oone-data-structure/discuss/91416/Java-AC-all-strict-O(1)-not-average-O(1)-easy-to-read
     */
    class AllOne {
        class Node{
            int count;
            Set<String> keySet;
            Node pre;
            Node next;

            public Node(int count) {
                this.count = count;
                keySet = new HashSet<String>();
            }
        }

        Map<Integer, Node> nodeMap;
        Map<String, Integer> countMap;
        Node head;
        Node tail;

        /** Initialize your data structure here. */
        public AllOne() {
            nodeMap = new HashMap<>();
            countMap = new HashMap<>();

            /**
             * !!!
             * head <=> n1 <=> n2 .... <=> tail
             */
            head = new Node(Integer.MIN_VALUE);
            tail = new Node(Integer.MAX_VALUE);
            head.next = tail;
            tail.pre = head;
        }

        /** Inserts a new key <Key> with value 1. Or increments an existing key by 1. */
        public void inc(String key) {
            if (countMap.containsKey(key)) {
                changeKey(key, 1);
            } else {
                countMap.put(key, 1);
                if (head.next.count != 1) {
                    addNodeAfter(new Node(1), head);
                }
                head.next.keySet.add(key);
                nodeMap.put(1, head.next);
            }
        }

        /** Decrements an existing key by 1. If Key's value is 1, remove it from the data structure. */
        public void dec(String key) {
            if (countMap.containsKey(key)) {
                int count = countMap.get(key);
                if (count == 1) {
                    countMap.remove(key);
                    removeKeyFromNode(nodeMap.get(count), key);
                } else {
                    changeKey(key, -1);
                }
            }
        }

        /** Returns one of the keys with maximal value. */
        public String getMaxKey() {
            return tail.pre == head ? "" : (String)tail.pre.keySet.iterator().next();
        }

        /** Returns one of the keys with Minimal value. */
        public String getMinKey() {
            return head.next == tail ? "" : (String)head.next.keySet.iterator().next();
        }

        private void changeKey(String key, int offset) {
            int count = countMap.get(key);
            int newCount = count + offset;
            countMap.put(key, newCount);

            Node curNode = nodeMap.get(count);

            Node newNode;
            if (nodeMap.containsKey(newCount)) {
                newNode = nodeMap.get(newCount);
            } else {
                newNode = new Node(newCount);
                nodeMap.put(newCount, newNode);
                addNodeAfter(newNode, offset == 1 ? curNode : curNode.pre);
            }

            newNode.keySet.add(key);
            removeKeyFromNode(curNode, key);
        }

        private void removeKeyFromNode(Node node, String key) {
            node.keySet.remove(key);
            if (node.keySet.size() == 0) {
                removeNodeFromList(node);
                nodeMap.remove(node.count);
            }
        }

        private void removeNodeFromList(Node node) {
            node.pre.next = node.next;
            node.next.pre = node.pre;
            node.next = null;
            node.pre = null;
        }

        private void addNodeAfter(Node newNode, Node preNode) {
            newNode.pre = preNode;
            newNode.next = preNode.next;
            preNode.next.pre = newNode;
            preNode.next = newNode;
        }
    }
}
