package leetcode;

import java.util.Arrays;

/**
 * Created by yuank on 3/16/18.
 */
public class LE_135_Candy {
    /**
        There are N children standing in a line. Each child is assigned a rating value.

        You are giving candies to these children subjected to the following requirements:

        #1.Each child must have at least one candy.
        #2.Children with a higher rating get more candies than their neighbors.

        What is the minimum candies you must give?

        Hard
     */

    /**
     * https://leetcode.com/problems/candy/solution/
     *
     * Use 2 Arrays, 3 passes
     *
     * Time : O(3n)
     * Space : O(n)
     */
    public class Solution1 {
        public int candy(int[] ratings) {
            int sum = 0;
            int[] left2right = new int[ratings.length];
            int[] right2left = new int[ratings.length];

            /**
             * Make sure everyone has at least one candy (#1)
             */
            Arrays.fill(left2right, 1);
            Arrays.fill(right2left, 1);

            /**
             * Make sure #2 for the ith child to (i - 1)th child
             */
            for (int i = 1; i < ratings.length; i++) {
                if (ratings[i] > ratings[i - 1]) {
                    left2right[i] = left2right[i - 1] + 1;
                }
            }

            /**
             * Make sure #2 for the ith child to (i + 1)th child
             */
            for (int i = ratings.length - 2; i >= 0; i--) {
                if (ratings[i] > ratings[i + 1]) {
                    right2left[i] = right2left[i + 1] + 1;
                }
            }

            /**
             * pick the max for each item and sum
             */
            for (int i = 0; i < ratings.length; i++) {
                sum += Math.max(left2right[i], right2left[i]);
            }
            return sum;
        }
    }

    /**
     * Use 1 array, 2 passes
     *
     * Same algorithm as Solution1, but use only one array.
     * The trick is combining step 2 and step3 (in solution1) in one pass.
     *
     * Time : O(2n)
     * Space : O(n)
     */
    class Solution2 {
        public int candy(int[] ratings) {
            if (ratings == null || ratings.length == 0) return 0;

            int n = ratings.length;
            int[] candies = new int[n];
            Arrays.fill(candies, 1);

            for (int i = 1; i < n; i++) {
                if (ratings[i] > ratings[i - 1]) {
                    candies[i] = candies[i - 1] + 1;//"Children with a higher rating get more candies than their neighbors"
                }
            }

            int sum = candies[ratings.length - 1];

            for (int i = n - 2; i >= 0; i--) {
                if (ratings[i] > ratings[i + 1]) {
                    candies[i] = Math.max(candies[i], candies[i + 1] + 1);//"Children with a higher rating get more candies than their neighbors"
                }

                sum += candies[i];
            }

            return sum;
        }
    }


    /**
     * Approach 4: Single Pass Approach with Constant Space in https://leetcode.com/problems/candy/solution/
     *
     * 1.The candies are always distributed in terms of increments of 1. Further, while distributing the
     *   candies, the local minimum number of candies given to a student is 1. Thus, the sub-distributions
     *   always take the form: 1, 2, 3, ..., n or n,..., 2, 1, whose sum is simply given by n(n+1)/2
     *
     * 2.Where to count the peak and valley point?
     *   The peak point's count needs to be the max of the counts determined by the rising and the falling
     *   slopes.
     *   the local valley point will always be assigned a candy count of 1.
     *
     * Time : O(n)
     * Space : O(1)
     */
    public class Solution3 {
        public int count(int n) {
            return (n * (n + 1)) / 2;
        }

        public int candy(int[] ratings) {
            if (ratings.length <= 1) {
                return ratings.length;
            }

            int candies = 0;
            int up = 0;
            int down = 0;
            int old_slope = 0;

            for (int i = 1; i < ratings.length; i++) {
                int new_slope = (ratings[i] > ratings[i - 1]) ? 1 : (ratings[i] < ratings[i - 1] ? -1 : 0);

                if ((old_slope > 0 && new_slope == 0) || (old_slope < 0 && new_slope >= 0)) {
                    candies += count(up) + count(down) + Math.max(up, down);
                    up = 0;
                    down = 0;
                }

                if (new_slope > 0) up++;
                if (new_slope < 0) down++;
                if (new_slope == 0) candies++;

                old_slope = new_slope;
            }

            candies += count(up) + count(down) + Math.max(up, down) + 1;

            return candies;
        }
    }

    /**
     * https://leetcode.com/problems/candy/discuss/135698/Simple-solution-with-one-pass-using-O(1)-space
     *
     * 'up' and 'down' to count the steps of continuous up and down respectively, and a 'peak' representing
     * the peak before going down.
     *
     * "peak >= down ? -1:0":
     * when peak >= down, the candy for the peak still don't need to change.
     * For example, [0, 1, 20, 9, 8, 7], for the first 5 number, we need to assign [1,2,3,2,1] candies.
     * But when 7 comes up, we need to raise the value of the peak, which is 3 above, it need to be
     * 4, [1,2,4,3,2,1]
     *
     * This solution here, make it to be [1,2,3,1,2,4], the sum are same.
     *
     * For the fourth value 9 in [0, 1, 20, 9, 8, 7], down = 1 <= peak = 2,
     * then the current result is computed as 1 + down + (peak >= down ? -1 : 0) = 1 + 1 + (-1) = 1.
     * That is the fourth 1 in [1,2,3,1,2,4].
     * And so on...
     *
     * Space O(1)
     */
    class Solution4 {
        public int Candy(int[] ratings) {
            if (ratings.length == 0) return 0;
            int ret = 1;
            int up = 0, down = 0, peak = 0;

            for (int i = 1; i < ratings.length; i++) {
                if (ratings[i - 1] < ratings[i]) {
                    up++;
                    peak = up;
                    down = 0;
                    ret += 1 + up;
                } else if (ratings[i - 1] == ratings[i])  {
                    peak = up = down = 0;
                    ret += 1;
                } else {
                    up = 0;
                    down++;
                    ret += 1 + down + (peak >= down ? -1 : 0);
                }
            }

            return ret;
        }
    }
}
