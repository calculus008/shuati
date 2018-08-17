package leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

        public boolean remove(int val) {
            boolean contain = locs.containsKey(val);
            if(!contain) return false;

            int idx = locs.get(val);
            locs.remove(val);

            if(idx < nums.size() - 1) {
                int lastVal = nums.get(nums.size()-1);
                nums.set(idx, lastVal);
                locs.put(lastVal, idx);
            }

            nums.remove(nums.size() - 1);

            return true;
        }



        //   This solution should be for the one with duplicated items.

        //1.To start, we know HashMap satiesifes add/remove in O(1), but not random retrieval. So we need to another data structure.
        //2.ArrayList "nums" is the data structure satisfies random retrieval requirements.
        //3.HashMap uses the input val as key, the value is ArrayList, which stores the indexes of the key value in ArrayList "nums"
        //4.So that remove can trace the it back to "nums" and remove it in O(1) by using copy and remove trick
//     ArrayList<Integer> nums;
// 	HashMap<Integer, List<Integer>> locs;
// 	java.util.Random rand = new java.util.Random();
//     /** Initialize your data structure here. */
//     public RandomizedSet() {
//         nums = new ArrayList<Integer>();
// 	    locs = new HashMap<Integer, List<Integer>>();
//     }

//     /** Inserts a value to the collection. Returns true if the collection did not already contain the specified element. */
//     public boolean insert(int val) {
//         boolean contain = locs.containsKey(val);
// 	    if ( ! contain ) locs.put( val, new ArrayList<Integer>() );
// 	    locs.get(val).add(nums.size());
// 	    nums.add(val);
// 	    return !contain ;
//     }

//     /** Removes a value from the collection. Returns true if the collection contained the specified element. */
//     public boolean remove(int val) {
//         boolean contain = locs.containsKey(val);
// 	    if ( ! contain ) return false;

// 	    //ArrayList remove returns the element that is removed!!!
//         //!!!"loc" returned here is the index of "val" in current "nums"
// 	    int loc = locs.get(val).remove( locs.get(val).size() - 1 );

//         //the removed value is not the last one in "nums"
//         //Then we copy the last one to location "loc"(and changes index in HashMap accordingly),remove last element, which is O(1).
//         //We can do it since "nums" only stores elements, it is not location or sequence sensitive.
// 	    if (loc < nums.size() - 1 ) {
// 	       int lastone = nums.get(nums.size() - 1);
// 	       nums.set( loc , lastone );
// 	       locs.get(lastone).remove( locs.get(lastone).size() - 1);
// 	       locs.get(lastone).add(loc);
// 	    }

//         nums.remove( nums.size() - 1 );

// 	    if (locs.get(val).isEmpty()) locs.remove(val);
// 	    return true;
//     }

//     /** Get a random element from the collection. */
//     public int getRandom() {
//         //!!!!
//         return nums.get( rand.nextInt(nums.size()) );
//     }
    }
}
