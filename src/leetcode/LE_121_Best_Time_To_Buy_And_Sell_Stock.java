package leetcode;

/**
 * Created by yuank on 3/14/18.
 */
public class LE_121_Best_Time_To_Buy_And_Sell_Stock {
    /**
        Say you have an array for which the ith element is the price of a given stock on day i.

        If you were only permitted to complete at most one transaction
        (ie, buy one and sell one share of the stock), design an algorithm to find the maximum profit.

        Example 1:
        Input: [7, 1, 5, 3, 6, 4]
        Output: 5

        max. difference = 6-1 = 5 (not 7-1 = 6, as selling price needs to be larger than buying price)
        Example 2:
        Input: [7, 6, 4, 3, 1]
        Output: 0

        In this case, no transaction is done, i.e. max profit = 0.
     */

    public static int maxProfit(int[] prices) {
        if (prices == null || prices.length < 2) return 0;

        int res = 0;

        //!!! min 不能是等于0，必须是prices[0]
        int min = prices[0];

        for (int price : prices) {
            min = Math.min(min, price);
            res = Math.max(res, price - min);
        }

        return res;
    }

    /**
     * follow up, return value can be negative,
     * meaning we must make a buy and a sell, if
     * we can't profit, try to return the min loss.
     *
     * key insights, for the case there must be a loss,
     * it means the number must keep decreasing, therefore
     * we simply keep track the difference between each day,
     * keep the max (minimize the loss)
     */
    public static int maximumProfit(int[] prices) {
        if (null == prices || prices.length < 2) {
            return 0;
        }

        int min = prices[0];
        int res = Integer.MIN_VALUE;
        int diff = Integer.MIN_VALUE;

        for (int i = 1; i < prices.length; i++) {
            min = Math.min(min, prices[i]);

            diff = Math.max(diff, prices[i] - prices[i - 1]);//!!!

            res = Math.max(res, prices[i] - min);
        }

        if (res == 0 && diff != 0) {
            return diff;
        } else {
            return res;
        }
    }

    /**
     * DoorDash phone interview version
     *
     * 1.Input and ouput is double type
     * 2.Throw exception for invalid input
     * 3.Must buy and sell at least once - need to deal with the corner
     *   case that the price is strictly decreasing, try to lose the min
     *   amount of money
     */
    public static double maxProfit(double[] prices) {
        if (null == prices) {
            throw new RuntimeException("input is null");
        }

        if (prices.length < 2) {
            throw new RuntimeException("not enough data point to make profit");
        }

        double res = 0.0;
        double min = prices[0];

        double diff = Integer.MIN_VALUE;

        for (int i = 1; i < prices.length; i++) {
            if (prices[i] < 0) {
                throw new RuntimeException("invalid price number");
            }

            min = Math.min(min, prices[i]);
            diff = Math.max(diff, prices[i] - prices[i - 1]);
            res = Math.max(res, prices[i] - min);
        }

        if (res == 0 && diff != 0) {
            return diff;
        } else {
            return res;
        }
    }


    public static void main(String[] args) {
        int[] test = {10, 4, 3, 2, 2, 1};
        System.out.println(maximumProfit(test));
    }
}
