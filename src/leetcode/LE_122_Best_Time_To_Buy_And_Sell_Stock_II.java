package leetcode;

/**
 * Created by yuank on 3/14/18.
 */
public class LE_122_Best_Time_To_Buy_And_Sell_Stock_II {
    /**
        Say you have an array for which the ith element is the price of a given stock on day i.

        Design an algorithm to find the maximum profit. You may complete as many transactions as you like
        (ie, buy one and sell one share of the stock multiple times).

        However, you may not engage in multiple transactions at the same time
        (ie, you must sell the stock before you buy again).

        Medium

        https://leetcode.com/problems/best-time-to-buy-and-sell-stock-ii
     */

    /**
     * Different from LE_121_Best_Time_To_Buy_And_Sell_Stock: multiple buy and sell
     *
     * Greedy
     */
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length < 2) return 0;

        int profit = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1]) {
                //Notice, the operation here has nothing to do with real buy and sell action.
                //It's just a simple way of calculating the accumulated profit value over the days.
                profit += prices[i] - prices[i - 1];
            }
        }

        return profit;
    }

    public int maxProfit1(int[] prices) {
        int i = 0;
        int valley = prices[0];
        int peak = prices[0];
        int maxprofit = 0;

        while (i < prices.length - 1) {
            while (i < prices.length - 1 && prices[i] >= prices[i + 1]) i++;
            valley = prices[i];

            while (i < prices.length - 1 && prices[i] <= prices[i + 1]) i++;
            peak = prices[i];

            maxprofit += peak - valley;
        }
        return maxprofit;
    }
}
