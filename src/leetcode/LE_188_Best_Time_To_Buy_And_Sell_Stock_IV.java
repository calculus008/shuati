package leetcode;

/**
 * Created by yuank on 3/24/18.
 */
public class LE_188_Best_Time_To_Buy_And_Sell_Stock_IV {
    /*
        Say you have an array for which the ith element is the price of a given stock on day i.

        Design an algorithm to find the maximum profit. You may complete at most k transactions.

        Note:
        You may not engage in multiple transactions at the same time (ie, you must sell the stock before you buy again).
     */

    //Because buy and sell prices may not be the same day, when k>len/2, that means we can do as many transactions as we want.
    // So, in case k>len/2, this problem is same to Best Time to Buy and Sell Stock II (#122).
    public int maxProfit(int k, int[] prices) {
        int len = prices.length;
        if (k > len / 2) return helper(prices);

        //dp[i][j] : max gain for j days and at most i actions
        int[][] dp = new int[k + 1][len];
        for (int i = 1; i <=k; i++) {
            int tempMax = -prices[0]; //at index 0,must be a buy action, so the gain is negative
            for (int j = 1; j < len; j++) {
                dp[i][j] = Math.max(dp[i][j - 1], prices[j] + tempMax);
                tempMax = Math.max(tempMax, dp[i-1][j-1] - prices[j]);
            }
        }

        return dp[k][len - 1];
    }

    private int helper(int[] prices) {
        int res = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1]) {
                res += prices[i] - prices[i - 1];
            }
        }
        return res;
    }
}
