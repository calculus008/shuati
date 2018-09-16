package leetcode;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

/**
 * Created by yuank on 9/15/18.
 */
public class LE_460_LFU_Cache {
    /**
         Design and implement a data structure for Least Frequently Used (LFU) cache. It should support the following operations: get and put.

         get(key) - Get the value (will always be positive) of the key if the key exists in the cache, otherwise return -1.
         put(key, value) - Set or insert the value if the key is not already present. When the cache reaches its capacity, it should invalidate the least frequently used item before inserting a new item. For the purpose of this problem, when there is a tie (i.e., two or more keys that have the same frequency), the least recently used key would be evicted.

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
     * Solution 1 : 3 HashMap + LinkedHashSet
     * O(1) for get() and put()
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
             * !!! How to update minFreq
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

}
