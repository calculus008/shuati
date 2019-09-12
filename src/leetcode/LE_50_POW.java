package leetcode;

/**
 * Created by yuank on 2/28/18.
 */
public class LE_50_POW {
    /**
        Implement pow(x, n).

        Example 1:
        Input: 2.00000, 10
        Output: 1024.00000

        Example 2:
        Input: 2.10000, 3
        Output: 9.26100
     */

    /**
     * https://zxi.mytechroad.com/blog/math/leetcode-50-powx-n/
     *
     * Recursion
     *
     * square x and cut n in half.
     * if n is negative, compute 1.0 / pow(x, |n|)
     *
     * pow(x, n) := pow(x * x, n / 2) * (x if n % 2 else 1)
     * pow(x, 0) := 1
     *
     * Example:
     * pow(x, 5) = pow(x^2, 2) * x
     *           = pow(x^4, 1) * x
     *           = pow(x^8, 0) * x^4 * x
     *           = 1 * x^4 * x = x^5
     *
     * Time complexity: O(logn)
     * Space complexity: O(logn)
     */
    class Solution1 {
        public double myPow(double x, int n) {
            /**
             * use long to prevent overflow
             */
            long m = (long)n;
            return m >= 0 ? pow(x, m) : 1.0 / pow(x, Math.abs(m));
        }

        public double pow(double x, long n) {
            if (n == 0) return 1.0;

            return pow(x * x, n / 2) * (n % 2 == 1 ? x : 1);
        }
    }



    //Time : O(logn), Space : O(1)
    public double myPow(double x, int n) {
        if (n == 0) return 1.0;

        if(n > 0) {
            return pow(x, n);
        } else {
            return 1 / pow(x, n);
        }
    }

    //2 ^ 3 =  2 ^ 1 * 2 ^ 1 * 2 =  (2^ 0 * 2 ^ 0 * 2) *  (2^ 0 * 2 ^ 0 * 2) * 2 = (1 * 1 * 2) * (1 * 1 * 2) * 2 = 8
    //2 ^ 7 = 2 ^ 3 * 2 ^ 3 * 2
    private double pow(double x, int n) {
        if (n == 0) return 1.0;

        double y = pow (x, n / 2);

        if(n % 2 == 0) {
            return y * y;
        } else {
            return y * y * x;
        }
    }

    class Solution2 {
        private double fastPow(double x, long n) {
            if (n == 0) {
                return 1.0;
            }
            double half = fastPow(x, n / 2);
            if (n % 2 == 0) {
                return half * half;
            } else {
                return half * half * x;
            }
        }
        public double myPow(double x, int n) {
            long N = n;
            if (N < 0) {
                x = 1 / x;//!!1
                N = -N;//!!!
            }

            return fastPow(x, N);
        }
    };
}
