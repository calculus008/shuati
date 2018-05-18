package leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
         Bucket
         Time and Spae : O(n)
     **/
    public List<Integer> topKFrequent(int[] nums, int k) {
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
        for(int i = bucket.length - 1; i >=0 && res.size() < k; i--) {//!!!"< k"
            if (bucket[i] != null) {
                for (int j = 0; j < bucket[i].size() && res.size() < k; j++) {
                    res.add(bucket[i].get(j));
                }
            }
        }

        return res;
    }
}
