package leetcode;

/**
 * Created by yuank on 4/5/18.
 */
public class LE_233_Number_Of_Digit_One {
    /**
     *         Given an integer n, count the total number of digit 1 appearing in all non-negative integers less
     *         than or equal to n.
     *
     *         For example:
     *         Given n = 13,
     *         Return 6, because digit 1 occurred in the following numbers: 1, 10, 11, 12, 13.
     *
     *         Hard
     *
     *         https://leetcode.com/problems/number-of-digit-one
     */

    /**
     *  At ones:
     *  Upon 10, there's one one
     *  upon 20, there's two ones
     *  ...
     *  Upon 160, there is 16 ones
     *  Upon 161, there is 17 ones
     *  So # of ones at one's position: (n / 10) + (n % 10 != 0)
     *
     *  At tens:
     *  Upon 1000, there are 100 ones
     *  Upon 2000, there are 200 ones
     *  ...
     *  So , at tens:  (n / 100) * 10 + Min( Max(n % 100 - 10 + 1, 0), 10)
     *  ...
     *
     *  Time complexity: O(log10(n
     *  No of iterations equal to the number of digits in n which is log10(n)
     *
     *  Space complexity: O(1) space required.
     */
    public int countDigitOne_editorial(int n) {
        int count = 0;
        for (long i = 1; i <= n; i *= 10) {
            long divider = i * 10;
            count += (n / divider) * i + Math.min(Math.max(n % divider - i + 1, 0), i);
        }
        return count;
    }

    /**
     *         https://blog.csdn.net/xudli/article/details/46798619
     *
     *         intuitive: 每10个数, 有一个个位是1, 每100个数, 有10个十位是1, 每1000个数, 有100个百位是1.
     *         做一个循环, 每次计算单个位上1得总个数(个位,十位, 百位).
     *
     *         例子:
     *
     *         以算百位上1为例子:   假设百位上是0, 1, 和 >=2 三种情况:
     *
     *             case 1: n=3141092, a= 31410, b=92. 计算百位上1的个数应该为 3141 *100 次.
     *                                                 (31410 + 8) / 10 * 100 = 3141 * 100
     *                                                 (31410 % 10 != 1)
     *
     *             case 2: n=3141192, a= 31411, b=92. 计算百位上1的个数应该为 3141 *100 + (92+1) 次.
     *                                                 (31411 + 8) / 10 * 100 = 3141 * 100
     *                                                 (31411 % 10 == 1), (b + 1) = (92 + 1)
     *
     *             case 3: n=3141592, a= 31415, b=92. 计算百位上1的个数应该为 (3141+1) *100 次.
     *                                                 (31415 + 8) / 10 * 100 = 3142 * 100
     *                                                 (31415 % 10 != 1)
     *
     *         以上三种情况可以用 一个公式概括:
     *
     *         (a + 8) / 10 * m + (a % 10 == 1) * (b + 1);
     */

    //Time : < O(n), Space : O(1)
    public int countDigitOne(int n) {
        int res = 0;
        for (long m = 1; m <= n; m *= 10) {
            long a = n / m;
            long b = n % m;
            res += (a + 8) / 10 * m;
            if (a % 10 == 1) {
                res += b + 1;
            }
        }

        return res;
    }
}
