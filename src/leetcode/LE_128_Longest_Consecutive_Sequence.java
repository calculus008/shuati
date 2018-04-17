package leetcode;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by yuank on 3/15/18.
 */
public class LE_128_Longest_Consecutive_Sequence {
    /*
        Given an unsorted array of integers, find the length of the longest consecutive elements sequence.

        For example,
        Given [100, 4, 200, 1, 3, 2],
        The longest consecutive elements sequence is [1, 2, 3, 4]. Return its length: 4.

        Your algorithm should run in O(n) complexity.
     */
    public static int longestConsecutive(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        int res = 0;

        //!!! use set, no need to worry about duplicates
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }

        for (int i = 0; i < nums.length; i++) {
            int down = nums[i] - 1;
            while (set.contains(down)) {
                set.remove(down);
                down--; //so the min value in the sequence is down + 1 because it oversteps down by 1
            }

            int up = nums[i] + 1;
            while (set.contains(up)) {
                set.remove(up);
                up++;
            } //same here, max value in the sequence is up - 1 because it oversteps up by 1

            //length of sequence : max - min + 1 = (up - 1) - (down + 1) + 1 = up - 1 - down - 1 + 1 = up - down - 1
            res = Math.max(res, up - down - 1);
        }
        return res;
    }
}
