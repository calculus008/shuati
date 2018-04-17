package leetcode;

import java.util.Arrays;

/**
 * Created by yuank on 3/16/18.
 */
public class LE_135_Candy {
    /*
        There are N children standing in a line. Each child is assigned a rating value.

        You are giving candies to these children subjected to the following requirements:

        Each child must have at least one candy.
        Children with a higher rating get more candies than their neighbors.
        What is the minimum candies you must give?
     */

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

        for (int  i = n - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1]) {
                candies[i] = Math.max(candies[i], candies[i+1] + 1);//"Children with a higher rating get more candies than their neighbors"
            }
        }

        int sum = 0;
        for (int candy: candies) {
            sum += candy;
        }

        return sum;
    }
}
