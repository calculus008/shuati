package leetcode;

import java.util.Arrays;

/**
 * Created by yuank on 3/16/18.
 */
public class LE_135_Candy {
    /**
        There are N children standing in a line. Each child is assigned a rating value.

        You are giving candies to these children subjected to the following requirements:

        Each child must have at least one candy.
        Children with a higher rating get more candies than their neighbors.
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

            Arrays.fill(left2right, 1);
            Arrays.fill(right2left, 1);

            for (int i = 1; i < ratings.length; i++) {
                if (ratings[i] > ratings[i - 1]) {
                    left2right[i] = left2right[i - 1] + 1;
                }
            }

            for (int i = ratings.length - 2; i >= 0; i--) {
                if (ratings[i] > ratings[i + 1]) {
                    right2left[i] = right2left[i + 1] + 1;
                }
            }

            for (int i = 0; i < ratings.length; i++) {
                sum += Math.max(left2right[i], right2left[i]);
            }
            return sum;
        }
    }

    /**
     * Use 1 array, 2 passes
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
}
