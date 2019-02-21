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
     * 4.Coordinate update on all 3 maps, used in both "get()" and "put()". Each call to "get()" and
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
                 * what saved in bucket map:
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
