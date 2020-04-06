package Interviews.Amazon;

import java.util.Arrays;

public class Minimum_Steps_To_Minimize_N {
    /**
     * Given a number n, count minimum steps to minimize it to 1 according to the following criteria:
     *
     * If n is divisible by 2 then we may reduce n to n/2.
     * If n is divisible by 3 then you may reduce n to n/3.
     * Decrement n by 1.
     * Examples:
     *
     * Input : n = 10
     * Output : 3
     *
     * Input : 6
     * Output : 2
     */

    static int getMinSteps_Buttom_Up(int n) {
        int table[] = new int[n + 1];

        for (int i = 0; i <= n; i++) {
            table[i] = n - i;
        }

        for (int i = n; i >= 1; i--) {
            if (!(i % 2 > 0)) {
                table[i / 2] = Math.min(table[i] + 1,
                        table[i / 2]);
            }
            if (!(i % 3 > 0)) {
                table[i / 3] = Math.min(table[i] + 1,
                        table[i / 3]);
            }
        }
        return table[1];
    }

    static int getMinSteps(int n, int memo[]) {
        // base case
        if (n == 1) return 0;

        if (memo[n] != -1) return memo[n];

        /**
         * store temp value for n as min(f(n-1), f(n/2), f(n/3)) + 1
         **/
        int res = getMinSteps(n - 1, memo);

        if (n % 2 == 0) {
            res = Math.min(res,
                    getMinSteps(n / 2, memo));
        }

        if (n % 3 == 0) {
            res = Math.min(res,
                    getMinSteps(n / 3, memo));
        }

        memo[n] = 1 + res;

        return memo[n];
    }

    static int getMinSteps_Top_Down(int n) {
        int memo[] = new int[n + 1];

        Arrays.fill(memo, -1);

        return getMinSteps(n, memo);
    }

    // Driver Code
    public static void main(String[] args) {
        int n = 10;
        System.out.println(getMinSteps_Buttom_Up(n));
        System.out.println(getMinSteps_Top_Down(n));

    }
}
