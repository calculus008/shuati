package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LE_1313_Decompress_Run_Length_Encoded_List {
    /**
     * We are given a list nums of integers representing a list compressed with run-length encoding.
     *
     * Consider each adjacent pair of elements [freq, val] = [nums[2*i], nums[2*i+1]] (with i >= 0).
     * For each such pair, there are freq elements with value val concatenated in a sublist.
     * Concatenate all the sublists from left to right to generate the decompressed list.
     *
     * Return the decompressed list.
     *
     * Example 1:
     * Input: nums = [1,2,3,4]
     * Output: [2,4,4,4]
     * Explanation: The first pair [1,2] means we have freq = 1 and val = 2 so we generate the array [2].
     * The second pair [3,4] means we have freq = 3 and val = 4 so we generate [4,4,4].
     * At the end the concatenation [2] + [4,4,4] is [2,4,4,4].
     *
     * Example 2:
     * Input: nums = [1,1,2,3]
     * Output: [1,3,3]
     *
     *
     * Constraints:
     * 2 <= nums.length <= 100
     * nums.length % 2 == 0
     * 1 <= nums[i] <= 100
     *
     * Easy
     */

    class Solution1 {
        public int[] decompressRLElist(int[] nums) {
            List<Integer> list = new ArrayList<>();

            for (int i = 0; i < nums.length; i += 2) {
                int freq = nums[i];
                int num = nums[i + 1];
                for (int j = 0 ; j < freq; j++) {
                    list.add(num);
                }
            }

            int[] res = new int[list.size()];
            int i = 0;
            for (int num : list) {
                res[i++] = num;
            }

            return res;
        }
    }

    class Solution2 {
        public int[] decompressRLElist(int[] nums) {
            /**
             * First pass get total size of result array
             * It saves time and space of using list like
             * Solution1.
             */
            int arrSize = 0;
            for (int i = 0; i < nums.length; i += 2) {
                arrSize += nums[i];
            }

            int[] result = new int[arrSize];

            int startIdx = 0;
            for (int i = 0; i < nums.length; i += 2) {
                Arrays.fill(result, startIdx, startIdx + nums[i], nums[i + 1]);
                startIdx += nums[i];
            }
            return result;
        }
    }
}
