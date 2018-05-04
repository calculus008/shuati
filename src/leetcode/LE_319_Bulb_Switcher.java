package leetcode;

/**
 * Created by yuank on 5/3/18.
 */
public class LE_319_Bulb_Switcher {
    /**
         There are n bulbs that are initially off. You first turn on all the bulbs.
         Then, you turn off every second bulb. On the third round, you toggle every third bulb
         (turning on if it's off or turning off if it's on). For the ith round, you toggle every i bulb.
         For the nth round, you only toggle the last bulb. Find how many bulbs are on after n rounds.

         Example:

         Given n = 3.

         At first, the three bulbs are [off, off, off].
         After first round, the three bulbs are [on, on, on].
         After second round, the three bulbs are [on, off, on].
         After third round, the three bulbs are [on, off, off].

         So you should return 1, because there is only one bulb is on.

         Medium
     */

    /**
     * Ligth is OFF : after even number of operations
     * Light is ON : after odd number of operations
     * "every ith round" -> i is factor of number n
     *
     * 求完全平方数的个数
     */

    public int bulbSwitch(int n) {
        return (int)Math.sqrt(n);
    }
}
