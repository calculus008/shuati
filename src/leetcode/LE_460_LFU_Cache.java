package leetcode;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

/**
 * Created by yuank on 9/15/18.
 */
public class LE_460_LFU_Cache {
    /**
         Design and implement a data structure for Least Frequently Used (LFU) cache.
         It should support the following operations: get and put.

         get(key) - Get the value (will always be positive) of the key if the key exists in the cache, otherwise return -1.
         put(key, value) - Set or insert the value if the key is not already present. When the cache reaches its capacity,
         it should invalidate the least frequently used item before inserting a new item. For the purpose of this problem,
         when there is a tie (i.e., two or more keys that have the same frequency), the least recently used key would be
         evicted.

         Follow up:
         Could you do both operations in O(1) time complexity?

         Example:

         LFUCache cache = new LFUCache(2)

            cache.put(1, 1);
            cache.put(2, 2);
            cache.get(1);       // returns 1
            cache.put(3, 3);    // evicts key 2
            cache.get(2);       // returns -1 (not found)
            cache.get(3);       // returns 3.
            cache.put(4, 4);    // evicts key 1.
            cache.get(1);       // returns -1 (not found)
            cache.get(3);       // returns 3
            cache.get(4);       // returns 4

         Hard
     */

    /**
     * https://www.jiuzhang.com/solution/lfu-cache/#tag-highlight
     */

    /**
     * Use Two HashMap + DLL
     *
     * Map<Integer, Node> nodeMap    : Key is the KEY(!!!), value is the Node that contains the VALUE.
     *
     * Map<Integer, DLList> countMap : This is actually the buckets, key is frequency, value is DLL
     *                                 which are all the values with the same frequency.
     *
     * key:
     * 1.When update(), use cnt in current node to retrieve dll from buckets.
     *   Then remove current node from dll
     * 2.After finish #1, check if cnt equals min and dll is already empty.
     *   If both are true, we can update min, increasing it by one.
     * 3.Update cnt in current node, increasing by one.
     * 4.Add current node into bucket with its updated cnt.
     */

    public class LFUCache_HashMap_DLL {
        class Node {
            int key, val, cnt;
            Node prev, next;
            Node(int key, int val) {
                this.key = key;
                this.val = val;
                /**
                 * !!!
                 */
                cnt = 1;
            }
        }

        class DLList {
            Node head, tail;
            int size;

            DLList() {
                head = new Node(0, 0);
                tail = new Node(0, 0);
                head.next = tail;
                tail.prev = head;
            }

            /**
             * Always add at the head
             */
            void add(Node node) {
                head.next.prev = node;
                node.next = head.next;
                node.prev = head;
                head.next = node;
                size++;
            }

            void remove(Node node) {
                node.prev.next = node.next;
                node.next.prev = node.prev;
                size--;
            }

            Node removeLast() {
                if (size > 0) {
                    Node node = tail.prev;
                    remove(node);
                    return node;
                } else return null;
            }
        }


        int capacity;
        /**
         * !!!
         * use size to track current cache size
         */
        int size;
        int min;

        Map<Integer, Node> nodeMap;
        Map<Integer, DLList> countMap;

        public LFUCache_HashMap_DLL(int capacity) {
            this.capacity = capacity;
            nodeMap = new HashMap<>();
            countMap = new HashMap<>();
        }

        public int get(int key) {
            Node node = nodeMap.get(key);
            if (node == null) {
                return -1;
            }
            update(node);
            return node.val;
        }

        public void put(int key, int value) {
            if (capacity <= 0) {
                return;
            }

            Node node;
            if (nodeMap.containsKey(key)) {
                node = nodeMap.get(key);
                node.val = value;
                update(node);
            } else {
                node = new Node(key, value);
                nodeMap.put(key, node);

                if (size == capacity) {
                    DLList lastList = countMap.get(min);
                    /**
                     * Since we always add new node at the head,
                     * last node in dll is the oldest node in the list.
                     */
                    nodeMap.remove(lastList.removeLast().key);
                    size--;
                }

                size++;
                min = 1;//!!!
                DLList newList = countMap.getOrDefault(node.cnt, new DLList());
                newList.add(node);
                /**
                 * must have this line, since if get is null, getOrDefault()
                 * return a new list that actually does not exist in dist.
                 */
                countMap.put(node.cnt, newList);
            }
        }

        private void update(Node node) {
            DLList oldList = countMap.get(node.cnt);
            oldList.remove(node);

            /**
             * how to update global min frequency
             */
            if (node.cnt == min && oldList.size == 0) {
                min++;
            }

            node.cnt++;

//            DLList newList = countMap.getOrDefault(node.cnt, new DLList());
//            newList.add(node);
//            countMap.put(node.cnt, newList);

            if (!countMap.containsKey(node.cnt)) {
                countMap.put(node.cnt, new DLList());
            }

            countMap.get(node.cnt).add(node);
        }
    }

    public class LFUCache_Practice {
        class Node {
            int key, val, cnt;
            Node prev, next;
            Node(int key, int val) {
                this.key = key;
                this.val = val;
                cnt = 1;
            }
        }

        class DLList {
            Node head, tail;
            int size;

            public DLList() {
                head = new Node(0, 0);
                tail = new Node(0, 0);
                head.next = tail;
                tail.prev = head;
            }

            public void add(Node node) {
                head.next.prev = node;
                node.next = head.next;
                node.prev = head;
                head.next = node;
                size++;
            }

            public void remove(Node node) {
                node.prev.next = node.next;
                node.next.prev = node.prev;
                size--;
            }

            public Node removeLast() {
                if (size > 0) {
                    Node node = tail.prev;
                    remove(node);
                    return node;
                } else {
                    return null;
                }
            }
        }


        int capacity;
        int size;
        int min;

        Map<Integer, Node> nodeMap;
        Map<Integer, DLList> countMap;//bucket

        public LFUCache_Practice(int capacity) {
            this.capacity = capacity;
            nodeMap = new HashMap<>();
            countMap = new HashMap<>();
        }

        public int get(int key) {
            Node node = nodeMap.get(key);
            if (node == null) {
                return -1;
            }
            update(node);
            return node.val;
        }

        public void put(int key, int value) {
            if (capacity <= 0) {
                return;
            }

            Node node;
            if (nodeMap.containsKey(key)) {
                node = nodeMap.get(key);
                node.val = value;
                update(node);
            } else {
                node = new Node(key, value);
                nodeMap.put(key, node);

                if (size == capacity) {//eviction
                    DLList lastList = countMap.get(min);
                    nodeMap.remove(lastList.removeLast().key);
                    size--;
                }

                size++;
                min = 1;
                DLList newList = countMap.getOrDefault(1, new DLList());
                newList.add(node);
                countMap.put(1, newList);
            }
        }

        private void update(Node node) {
            DLList oldList = countMap.get(node.cnt);
            oldList.remove(node);

            if (node.cnt == min && oldList.size == 0) {
                min++;
            }

            node.cnt++;

            if (!countMap.containsKey(node.cnt)) {
                countMap.put(node.cnt, new DLList());
            }

            countMap.get(node.cnt).add(node);
        }
    }


    /**
     * Solution 1 : 3 HashMap + LinkedHashSet
     * O(1) for get() and put()
     *
     * 3 HashMap solution
     * 1."valueMap" : save KV pair, used for getting value for a given key,
     *                also for checking if a key exists int current cache.
     * 2."freMap"   : record frequency of each key
     * 3."bucket"   : Key for this HashMap is frequency, value is a LinkedHashSet, acts as a bucket,
     *                each bucket saves the elements which have the same frequency.
     *
     *                As the question states:
     *
     *                "when there is a tie (i.e., two or more keys that have the same frequency),
     *                the least recently used key would be evicted"
     *
     *                "there is a tie" means multiple elements have the same frequency, so they are in
     *                the same bucket.
     *
     *                "the least RECENTLY used key would be evicted", so we need to tell the appearing
     *                order of the element in the same bucket. LinkedHashSet serves this purpose.
     *                Always evict the first element in bucket referenced by "minFreq", it is O(1)
     *
     * An alternative is to create an element class which value and frequency, then we just need one
     * HashMap to save value and frequency (combine "valueMap" and "freMap"). You need to write more
     * code for it, so the 3 HashMap is simpler.
     *
     * 4.Point update on all 3 maps, used in both "get()" and "put()". Each call to "get()" and
     *   "put()" means the frequency should be updated.
     *
     *   For a given key
     *   a.Get frequency from "freqMap", plus one, update its value in "freqMap"
     *   b.Check in "bucket" if the new frequency exists, if not, create a new bucket for it in "bucket"
     *   c.Remove key from old bucket, add to new bucket.
     *   d.Update minFreq
     *
     * 5.Since we have capacity limit, when capacity is full, need to evict element in "put()".
     *   Always evict the first element in bucket referenced by "minFreq".
     *
     */
    class LFUCache {
        int capacity;
        int minFreq;
        Map<Integer, Integer> valueMap;
        Map<Integer, Integer> freqMap;
        Map<Integer, LinkedHashSet<Integer>> bucket;

        public LFUCache(int capacity) {
            valueMap = new HashMap<>();
            freqMap = new HashMap<>();
            bucket = new HashMap<>();
            bucket.put(1, new LinkedHashSet<>());

            this.capacity = capacity;
            minFreq = -1;
        }

        private void update(int key) {
            int freq = freqMap.get(key);
            int newFreq = freq + 1;
            freqMap.put(key, newFreq);

            if (!bucket.containsKey(newFreq)) {
                bucket.put(freq + 1, new LinkedHashSet<>());
            }

            bucket.get(freq).remove(key);
            /**
             * !!! How to update minFreq :
             * if current key's frequency before plus 1 is minFreq
             * AND (after last line to remove current key from
             * freq bucket) freq bucket is empty, minFreq should
             * increase by one
             */
            if(freq == minFreq && bucket.get(freq).size()==0) {
                minFreq++;
            }

            bucket.get(newFreq).add(key);
        }

        public int get(int key) {
            if (!valueMap.containsKey(key)) {
                return -1;
            }

            int res = valueMap.get(key);
            update(key);

            return res;
        }

        public void put(int key, int value) {
            if (capacity <= 0) return;//!!!

            if (valueMap.containsKey(key)) {
                valueMap.put(key, value);
                update(key);
                return;
            }

            if (valueMap.size() >= capacity) {
                /**
                 * To get the element which is the first added to LinkedHashSet,
                 * need to use Iterator!!!
                 */
                int evictKey = bucket.get(minFreq).iterator().next();

                /**
                 1.Here we are in put() and try to add new KV,
                   minFreq will be set 1 later when new KV is added,
                   Therefore we don't need to update minFreq even
                   if bucket for current minFreq will be empty after remove() action.

                 2."freMap.get(minFreq).remove(evictKey);"
                   Don't need to remove from freqMap, since it's gone from valueMap(),
                   set() will return -1, put() will overwrite its value in freqMap.
                 **/
                bucket.get(minFreq).remove(evictKey);
                valueMap.remove(evictKey);
            }

            valueMap.put(key, value);
            freqMap.put(key, 1);
            bucket.get(1).add(key);
            minFreq = 1;
        }
    }

    class LFUCache_Exersize_1 {
        Map<Integer, Integer> valueMap;
        Map<Integer, Integer> freqMap;
        Map<Integer, LinkedHashSet<Integer>> bucket;
        int capacity;
        int minFreq;

        public LFUCache_Exersize_1(int capacity) {
            valueMap = new HashMap<>();
            freqMap = new HashMap<>();
            bucket = new HashMap<>();
            bucket.put(1, new LinkedHashSet<>());

            this.capacity = capacity;
            minFreq = 0;
        }

        public int get(int key) {
            if (!valueMap.containsKey(key)) {
                return -1;
            }

            int res = valueMap.get(key);
            update(key);//update freqMap and bucket to increase freq by 1.
            return res;
        }

        public void put(int key, int value) {
            /**
             * !!!
             * Corner case
             */
            if (capacity <= 0) {
                return;
            }

            if (valueMap.containsKey(key)) {
                valueMap.put(key, value);
                update(key);
                return;
            }

            /**
             * eviction act
             **/
            if (valueMap.size() == capacity) {
                /**
                 * what saved in bucket dist:
                 * key - frequency
                 * value - all keys (!!!) which have the same frequency as the key.
                 *
                 * so the values in LinkedHashSet are keys (!!!)
                 */
                int evictKey = bucket.get(minFreq).iterator().next();

                freqMap.remove(evictKey);
                valueMap.remove(evictKey);
                bucket.get(minFreq).remove(evictKey);
            }

            valueMap.put(key, value);
            freqMap.put(key, 1);
            bucket.get(1).add(key);
            minFreq = 1;//!!!
        }

        /**
         * update() :
         * Update on freqMap, bucket and minFreq when a key is used in either get() or put().
         * valueMap will be updated separately.
         */
        private void update(int key) {
            /**
             * #1.update freqMap
             */
            int freq = freqMap.getOrDefault(key, 0);
            int newFreq = freq + 1;
            freqMap.put(key, newFreq);

            /**
             * #2.update bucket
             */
            if (!bucket.containsKey(newFreq)) {
                bucket.put(newFreq, new LinkedHashSet<>());
            }
            /**
             * When a new key is added, we execute "freqMap.put(key, 1)" and "valueMap.put(key, value)"
             * in put().
             *
             * So when update() is called (in two places), the if logic guarantees the key already exists
             * in valueMap, so it also must exist in freqMap, so bucket.get(freq) will never be null.
             */
            bucket.get(freq).remove(key);
            bucket.get(newFreq).add(key);

            /**
             * #3.update minFreq
             */
            if (minFreq == freq && bucket.get(freq).size() == 0) {
                minFreq++;
            }
        }
    }

}
