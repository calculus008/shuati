package leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LE_528_Random_Pick_With_Weight {
    /**
     * Given an array w of positive integers, where w[i] describes the weight of index i,
     * write a function pickIndex which randomly picks an index in proportion to its weight.
     *
     * Note:
     *
     * 1 <= w.length <= 10000
     * 1 <= w[i] <= 10^5
     * pickIndex will be called at most 10000 times.
     * Example 1:
     *
     * Input:
     * ["Solution","pickIndex"]
     * [[[1]],[]]
     * Output: [null,0]
     * Example 2:
     *
     * Input:
     * ["Solution","pickIndex","pickIndex","pickIndex","pickIndex","pickIndex"]
     * [[[1,3]],[],[],[],[],[]]
     * Output: [null,0,1,1,1,0]
     * Explanation of Input Syntax:
     *
     * The input is two lists: the subroutines called and their arguments. Solution's
     * constructor has one argument, the array w. pickIndex has no arguments.
     * Arguments are always wrapped with a list, even if there aren't any.
     *
     * Variation:
     * 大致是给你一些城市和城市的人口，请写出一个函数，该函数可以根据人口比重随机地输出城市名。
     *
     * Medium
     *
     * https://leetcode.com/problems/random-pick-with-weight
     */

    /**
     * Pre sum + Binary Search
     *
     * Use accumulated freq array to get idx.
     * w[] = {2,5,3,4} => wsum[] = {2,7,10,14}
     * then get random val random.nextInt(14)+1, idx is in range [1,14]
     *
     * idx in [1,2] return 0
     * idx in [3,7] return 1
     * idx in [8,10] return 2
     * idx in [11,14] return 3
     *
     * then become LE_35_Search_For_Insertion_Point . Search Insert Position
     * Time: O(n) to init, O(logn) for one pick
     * Space: O(n)
     */

    class Solution {
        int[] sums;
        Random rand;

        public Solution(int[] w) {
            rand = new Random();
            sums = new int[w.length];

            //prefix sum
            sums[0] = w[0];
            for (int i = 1; i < w.length; i++) {
                sums[i] = sums[i - 1] + w[i];
            }
        }

        public int pickIndex() {
            /**
             * nextInt(int bound)
             * Returns a pseudo random, uniformly distributed int value between 0 (inclusive) and the specified value (exclusive)
             */
            int target = rand.nextInt(sums[sums.length - 1]) + 1;
            int l = 0;
            int r = sums.length;

            while (l < r) {
                int m = l + (r - l) / 2;

                if (sums[m] == target) {
                    return m;
                } else if (sums[m] < target) {
                    l = m + 1;
                } else {
                    r = m;
                }
            }

            return l;
        }
    }
}
