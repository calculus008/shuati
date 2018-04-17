package leetcode;

import java.util.TreeSet;

/**
 * Created by yuank on 3/28/18.
 */
public class LE_220_Contains_Duplicates_III {
    /*
        Given an array of integers, find out whether there are two distinct indices i and j in the array such
        that the absolute difference between nums[i] and nums[j] is at most t and the absolute difference between i and j is at most k.
     */

    //Solution 1 : Time : nlog(k), space(k)
    //https://www.youtube.com/watch?v=pcNkFM-Dkqg&list=PLvyIyKZVcfAkKE4fx9dz12HnEn-uzxrIK&index=20
    //https://leetcode.com/problems/contains-duplicate-iii/discuss/61655/Java-O(N-lg-K)-solution
    public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
        if (nums == null || nums.length < 2) return false;

        //用Long防止overflow
        TreeSet<Long> set = new TreeSet<>();
        for (int i = 0; i < nums.length; i++) {
            //注意此处cast nums[i] to long
            Long floor = set.floor((long)nums[i] + t);
            Long ceil = set.ceiling((long)nums[i] - t);

            /*
                i = nums[i] + t, j = nums[j] - t

                j                           i
                |___________________________|
               ceil(保证值>=j)            floor(保证值<=i)

               满足问题条件"the absolute difference between nums[i] and nums[j] is at most t "
            */
            if (floor != null && floor >= nums[i]
                    || ceil != null && ceil <= nums[i]) {
                return true;
            }

            //先比较，再加入
            set.add((long)nums[i]);

            //此处的逻辑保证set里最多保持以i结尾的下标连续的k个元素。
            //这是保证满足问题条件"the absolute difference between i and j is at most k"
            if (i >= k) {
                set.remove((long)nums[i - k]);
            }
        }

        return false;
    }
}
