package leetcode;

/**
 * Created by yuank on 3/24/18.
 */
public class LE_188_Best_Time_To_Buy_And_Sell_Stock_IV {
    /**
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

    /**
         这道题可以联想到二维背包问题， 设状态f(i, j)为前i天在transaction limit为j的限制下能获取的最大利益。
         在第i天时我们有两种决策: 1) 卖掉股票，完成transaction, 2) 不卖股票，没有transaction.
         然而难点在于，如果卖出的话，如何得知是第几天买入的呢？此时我们枚举所有m < i, 从而获得第m天买入，第i天卖出的收益；
         如果我们再列举transaction limit的话，就可以得到在限制下前i天的最大收益。

         根据这个思路，可以获得以下状态转移方程:
         f(i, j) = max{f(i - 1, j), f(m, j - 1) + prices[i] - prices[m]} for all m < i.
         但是由于枚举所有m，我们的runtime 是O(n^2 * k), 然而challenge要做到O(nk).

         回到状态转移方程，如果可以做到O(nk),那么我们就得在O(1)时间内找出一个m，使得f(m, j - 1) + prices[i] - prices[m]最大。
         通过观察这个式子，容易得知prices[i]是一个常数项，当f(m, j - 1) - prices[m]最大时该式取最大值。
         令maxDiff = f(m, j - 1) - prices[m] 当i从1 -> N的列举过程中我们必然会遍历所有m， (m < i)，所以在i = i0时遍历过的m，
         i = i1时依然有效，(假设i0 < i1) 如果我们能随时更新maxDiff, 则无须再回退回去再遍历m。

         所以将maxDiff初始为-prices[0]
         maxDiff的更新方程为 maxDiff = max{maxDiff, maxDiff + prices[i]}, 这样就可以做到O(nk).

         note: 代码中为了节约空间，将dp矩阵转置了，f(i, j)变为了f(j, i)。转置之后发现当前行只跟前一行有关，所以只需要两行，N列。
     */

    public int maxProfit2(int K, int[] prices) {
        // write your code here
        if (K == 0 || prices.length == 0){
            return 0;
        }
        // this is necessary to pass test case35%.
        // the maximum number of transactions would be # of days / 2
        // if k >= upper limit, this is the same situation as unlimited transaction.
        // so apply Greedy Choice Property and you don't need to go through the dp.
        int profit = 0;
        if(K >= prices.length / 2) {
            for (int i = 1; i < prices.length; i++) {
                if (prices[i] > prices[i - 1]) {
                    profit += prices[i] - prices[i - 1];
                }
            }
            return profit;
        }
        /*
        mod by 2 for space optimization
        */
        int[][] dp = new int[2][prices.length + 1];
        for (int j = 1; j <= K; j++){
            int maxDiff = -prices[0];
            for (int i = 1; i <= prices.length; i++){
                dp[j % 2][i] = (i == 1) ? 0 : dp[j % 2][i - 1];
                dp[j % 2][i] = Math.max(dp[j % 2][i - 1], maxDiff + prices[i - 1]);
                maxDiff = Math.max(dp[(j - 1) % 2][i] - prices[i - 1], maxDiff);
            }
        }
        return dp[K % 2][prices.length];
    }
}
