package leetcode;

/**
 * Created by yuank on 3/14/18.
 */
public class LE_123_Best_Time_To_Buy_And_Sell_Stock_III {
    /*
        Say you have an array for which the ith element is the price of a given stock on day i.

        Design an algorithm to find the maximum profit. You may complete at most TWO transactions.

        Note:
        You may not engage in multiple transactions at the same time (ie, you must sell the stock before you buy again).
     */

    public static int maxProfit(int[] prices) {
        if (prices == null || prices.length < 2) return 0;

        int buy1 = Integer.MIN_VALUE;
        int buy2 = Integer.MIN_VALUE;
        int sell1 = 0;
        int sell2 = 0;
        for (int price : prices) {
            sell2 = Math.max(sell2, price + buy2);//!!! price + buy2
            buy2 = Math.max(buy2, sell1 - price);
            sell1 = Math.max(sell1, price + buy1);//!!! price + buy2
            buy1 = Math.max(buy1, -price);
        }
        return sell2;
    }
}
