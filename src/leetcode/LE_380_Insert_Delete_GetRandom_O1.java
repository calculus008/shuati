package leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by yuank on 8/17/18.
 */
public class LE_380_Insert_Delete_GetRandom_O1 {
    /**
         Design a data structure that supports all following operations in average O(1) time.

         insert(val): Inserts an item val to the set if not already present.
         remove(val): Removes an item val from the set if present.
         getRandom: Returns a random element from current set of elements. Each element must have the same probability of being returned.
         Example:

         // Init an empty set.
         RandomizedSet randomSet = new RandomizedSet();

         // Inserts 1 to the set. Returns true as 1 was inserted successfully.
         randomSet.insert(1);

         // Returns false as 2 does not exist in the set.
         randomSet.remove(2);

         // Inserts 2 to the set, returns true. Set now contains [1,2].
         randomSet.insert(2);

         // getRandom should return either 1 or 2 randomly.
         randomSet.getRandom();

         // Removes 1 from the set, returns true. Set now contains [2].
         randomSet.remove(1);

         // 2 was already in the set, so return false.
         randomSet.insert(2);

         // Since 2 is the only number in the set, getRandom always return 2.
         randomSet.getRandom();

         Medium
     */

    public class RandomizedSet {
        private HashMap<Integer, Integer> locs;
        private List<Integer> nums;

        /** Initialize your data structure here. */
        public RandomizedSet() {
            nums = new ArrayList<Integer>();
            locs = new HashMap<Integer, Integer>();
        }

        public boolean insert(int val) {
            boolean contain = locs.containsKey(val);
            if(contain) return false;

            locs.put(val, nums.size());
            nums.add(val);
            return true ;
        }

        public int getRandom() {
            java.util.Random rand = new Random();
            return nums.get( rand.nextInt(nums.size()) );
        }

        /**
         * 通过HashMap找到要删除元素在list中的index, 然后和list的最后一个元素交换，
         * 实际被删除的是交换后list的最后一个元素。
         *
         * 如果不交换，在list中删除元素是O(n),达不到O(1)的要求。
         */
        public boolean remove(int val) {
            boolean contain = locs.containsKey(val);
            if(!contain) return false;

            int idx = locs.get(val);
            locs.remove(val);

            if(idx < nums.size() - 1) {//!!!
                int lastVal = nums.get(nums.size()-1);
                nums.set(idx, lastVal);
                locs.put(lastVal, idx);
            }

            nums.remove(nums.size() - 1);

            return true;
        }
    }
}
