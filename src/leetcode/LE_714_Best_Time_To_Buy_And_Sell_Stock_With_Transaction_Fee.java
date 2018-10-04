package leetcode;

/**
 * Created by yuank on 10/3/18.
 */
public class LE_714_Best_Time_To_Buy_And_Sell_Stock_With_Transaction_Fee {
    /**
         Your are given an array of integers prices, for which the i-th element is the price of
         a given stock on day i; and a non-negative integer fee representing a transaction fee.

         You may complete as many transactions as you like, but you need to pay the transaction
         fee for each transaction. You may not buy more than 1 share of a stock at a time
         (ie. you must sell the stock share before you buy again.)

         Return the maximum profit you can make.

         Example 1:
         Input: prices = [1, 3, 2, 8, 4, 9], fee = 2
         Output: 8
         Explanation: The maximum profit can be achieved by:
         Buying at prices[0] = 1
         Selling at prices[3] = 8
         Buying at prices[4] = 4
         Selling at prices[5] = 9
         The total profit is ((8 - 1) - 2) + ((9 - 4) - 2) = 8.
         Note:

         0 < prices.length <= 50000.
         0 < prices[i] < 50000.
         0 <= fee < 50000.

         Medium
     */

    //https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-transaction-fee/discuss/108870/Most-consistent-ways-of-dealing-with-the-series-of-stock-problems

    /**
         我们考虑最朴素的方法，对于每一天，如果当前有股票，考虑出售或者保留，如果没股票，考虑购买或者跳过，进行dfs搜索。
         每天都有两种操作，时间复杂度为O(2^n)

         如何优化呢？我们用动态规划的思想来解决这个问题，考虑每一天同时维护两种状态：
         拥有股票(own)状态和已经售出股票(sell)状态。用own和sell分别保留这两种状态到目前为止所拥有的最大利润。
         对于sell，用前一天own状态转移，比较卖出持有股是否能得到更多的利润，即sell = max(sell , own + price - fee)，
         而对于own , 我们考虑是否买新的股票更能赚钱(换言之，更优惠），own=max( own, sell-price)

         初始化我们要把sell设为0表示最初是sell状态且没有profit，把own设为负无穷因为最初不存在该状态，我们不希望从这个状态进行转移
         因为我们保存的都是最优状态，所以在买卖股票时候取max能保证最优性不变
         最后直接返回sell即可
     */
    public int maxProfit(int[] prices, int fee) {
        int sell=0;
        int own=-prices[0];
        for (int i = 1; i < prices.length; i++){
            sell = Math.max(sell, own + prices[i] - fee);
            own = Math.max(own, sell - prices[i]);
        }
        return sell;
    }
}
