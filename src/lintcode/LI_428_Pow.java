package lintcode;

/**
 * Created by yuank on 6/25/18.
 */
public class LI_428_Pow {
    /**
         Implement pow(x, n).

         Example
         Pow(2.1, 3) = 9.261
         Pow(0, 1) = 0
         Pow(1, 0) = 1
         Challenge
         O(logn) time
     */

    /**
         计算x的n次方， 即计算x^nx
         n
         。

         由公式可知： x^n = x^{n/2} * x^{n/2}x
         n
         =x
         n/2
         ∗x
         n/2
         。

         如果我们求得x^{n/2}x
         n/2
         ， 则可以O(1)求出x^nx
         n
         , 而不需要再去循环剩下的n/2n/2次。

         以此类推，若求得x^{n/4}x
         n/4
         ， 则可以O(1)求出x^{n/2}x
         n/2
         。
         。。。

         因此一个原本O(n)的问题，我们可以用O(logn)复杂度的算法来解决。
     */
    public double myPow_Iterative(double x, int n) {
        boolean isNegative = false;
        if (n < 0) {
            x = 1 / x;
            isNegative = true;
            n = -(n + 1); // Avoid overflow when n == MIN_VALUE
        }

        double ans = 1, tmp = x;

        while (n != 0) {
            if (n % 2 == 1) {
                ans *= tmp;
            }
            tmp *= tmp;
            n /= 2;
        }

        if (isNegative) {
            ans *= x;
        }
        return ans;
    }

    public double myPow_Recurrsion(double x, int n) {
        // write your code here
        if (n == 0) {
            return 1;
        }

        if (n == 1) {
            return x;
        }

        if (n == -1) {
            return 1 / x;
        }

        double ans = myPow_Recurrsion(x, n / 2);

        if (n % 2 == 0) {
            return ans * ans;
        } else {
            return (n > 0) ? ans * ans * x : ans * ans * 1 / x;
        }
    }
}
