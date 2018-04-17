package leetcode;

/**
 * Created by yuank on 1/28/18.
 */
public class BestTimeBuySellStock4 {
    public static int maxProfit(int k, int[] prices) {
        int len = prices.length;

        if (k > len / 2) return helper(prices);

        //dp[i][j] : max gain for j days and at most i transactions
        int[][] dp = new int[k + 1][len];
        for (int i = 1; i <= k; i++) {//outser loop for each transaction - one buy and one sell
            int tempMax = -prices[0];

            System.out.println("===start transaction " +  i + " =====");
            for (int j = 1; j < len; j++) {
                System.out.println("   ===price " +  j + " start =====");
                int newval = prices[j] + tempMax;
                System.out.println("    dp[i][j - 1]=" + dp[i][j - 1] + ", prices[j]=" + prices[j] + ", tempMax=" + tempMax + ", newval=" + newval);
                dp[i][j] = Math.max(dp[i][j - 1], prices[j] + tempMax);

                int alert = dp[i-1][j-1] - prices[j];
                System.out.println("    tempMax=" + tempMax + "， dp[i-1][j-1]=" + dp[i-1][j-1] + ", prices[j]=" + prices[j] + "， alternative=" + alert);
                tempMax = Math.max(tempMax, dp[i-1][j-1] - prices[j]);
                System.out.println("   ===price " +  j + " end =====");
            }
            System.out.println("===end transaction " +  i + " =====");
        }

        return dp[k][len - 1];
    }

    private static int helper(int[] prices) {
        int res = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1]) {
                res += prices[i] - prices[i - 1];
            }
        }
        return res;
    }

    public static void main(String [] args) {
        int[] prices = {7, 1, 5, 3, 6, 4};
        int k = 2;

        int res = maxProfit(k, prices);
        System.out.println("res=" + res);
    }

}
