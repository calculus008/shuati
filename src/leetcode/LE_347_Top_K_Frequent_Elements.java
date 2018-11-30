package leetcode;

import java.util.*;

/**
 * Created by yuank on 5/16/18.
 */
public class LE_347_Top_K_Frequent_Elements {
    /**
         Given a non-empty array of integers, return the k most frequent elements.

         For example,
         Given [1,1,1,2,2,3] and k = 2, return [1,2].

         Note:
         You may assume k is always valid, 1 ≤ k ≤ number of unique elements.
         Your algorithm's time complexity must be better than O(n log n), where n is the array's size.

         Medium

        Similar problem at LE_692_Top_K_Frequent_Words
     */

    /**
         http://zxi.mytechroad.com/blog/hashtable/leetcode-347-top-k-frequent-elements/

         Bucket
         Time and Spae : O(n)

         23 ms
     **/
    class Solution1 {
            public List<Integer> topKFrequent1 ( int[] nums, int k){
            List<Integer> res = new ArrayList<>();
            Map<Integer, Integer> map = new HashMap<>();

            //!!! "ArrayList" can't follow with "<>"
            List<Integer>[] bucket = new ArrayList[nums.length + 1];//!!!"nums.length + 1", bucket, 1-based!!!

            //create frequency map
            for (int num : nums) {
                map.put(num, map.getOrDefault(num, 0) + 1);
            }

            //create bucket
            for (int key : map.keySet()) {
                int freq = map.get(key);
                if (bucket[freq] == null) {
                    bucket[freq] = new ArrayList<>();
                }
                bucket[freq].add(key);
            }

            //Start from the largest freq, put element into res
            for (int i = bucket.length - 1; i >= 0 && res.size() < k; i--) {//!!!"< k"
                if (bucket[i] != null) {
                    for (int j = 0; j < bucket[i].size() && res.size() < k; j++) {
                        res.add(bucket[i].get(j));
                    }
                }
            }

            return res;
        }
    }

    /**
     * Headp + HashMap
     * Time  : O(nlogk)
     * Space : O(n)
     *
     * 54 ms
     */

    class Solution2 {
        class Pair {
            int num;
            int freq;

            public Pair(int n, int f) {
                this.num = n;
                this.freq = f;
            }
        }

        public List<Integer> topKFrequent(int[] nums, int k) {
            List<Integer> res = new ArrayList<>();
            Map<Integer, Integer> map = new HashMap<>();

            //create frequency map
            for (int num : nums) {
                int cur = map.getOrDefault(num, 0) + 1;
                map.put(num, cur);
            }

            //min heap on freq
            PriorityQueue<Pair> pq = new PriorityQueue<>((a, b) -> a.freq - b.freq);

            for (Map.Entry<Integer, Integer> e : map.entrySet()) {
                pq.offer(new Pair(e.getKey(), e.getValue()));
                if (pq.size() > k) {//!!!
                    pq.poll();
                }
            }

            while (!pq.isEmpty()) {
                res.add(pq.poll().num);
            }

            Collections.sort(res);

            return res;
        }
    }
}
