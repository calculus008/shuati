package leetcode;

/**
 * Created by yuank on 4/30/18.
 */
public class LE_309_Best_Time_To_Buy_And_Sell_Stock_With_Cooldown {
    /**
         Say you have an array for which the ith element is the price of a given stock on day i.

         Design an algorithm to find the maximum profit. You may complete as many transactions as you like
        (ie, buy one and sell one share of the stock multiple times) with the following restrictions:

         You may not engage in multiple transactions at the same time (ie, you must sell the stock before you buy again).
         After you sell your stock, you cannot buy stock on next day. (ie, cooldown 1 day)
         Example:

         prices = [1, 2, 3, 0, 2]
         maxProfit = 3
         transactions = [buy, sell, cooldown, buy, sell]

         Medium
     */

    /**
         https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-cooldown/discuss/75927/Share-my-thinking-process

         1.buy[i]  = max(rest[i - 1] - price, buy[i - 1])
         2.sell[i] = max(buy[i - 1] + price, sell[i - 1])
         3.rest[i] = max(buy[i - 1], sell[i - 1], rest[i - 1])

         简化：
         buy[i] <= rest[i]     => 带入#3  rest[i] = max(sell[i-1], rest[i-1])
         rest[i] = sell[i - 1] => 带入#1. buy[i] =  max(sell[i - 2] - price, buy[i - 1])
         保留#2                 =>        sell[i] = max(buy[i - 1] + price, sell[i - 1])

         Time : O(n)
         Space : O(1)
     */

    public int maxProfit(int[] prices) {
        int buy = Integer.MIN_VALUE, preBuy = 0;
        int sell = 0, preSell = 0;

        for (int price : prices) {
            preBuy = buy;
            buy = Math.max(preSell - price, preBuy);
            preSell = sell;
            sell = Math.max(preBuy + price, preSell);
        }
        return sell;
    }

    public int maxProfit2(int[] prices) {
        if (prices == null || prices.length == 0) return 0;

        int[] sell = new int[prices.length];
        int[] buy = new int[prices.length];
        sell[0] = 0;
        buy[0] = -prices[0];

        for (int i = 1; i < prices.length; ++i) {
            sell[i] = Math.max(sell[i - 1], buy[i - 1] + prices[i]);
            buy[i] = Math.max(buy[i - 1], (i > 1 ? sell[i - 2] : 0) - prices[i]);
        }
        return sell[prices.length - 1];
    }
}
