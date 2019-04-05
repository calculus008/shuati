package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuank on 5/3/18.
 */
public class LE_319_Bulb_Switcher {
    /**
         There are n bulbs that are initially off. You first turn on all the bulbs.
         Then, you turn off every second bulb. On the third round, you toggle every third bulb
         (turning on if it's off or turning off if it's on). For the ith round, you toggle every i bulb.
         For the nth round, you only toggle the last bulb.

         Find how many bulbs are on after n rounds.

         Example:

         Given n = 6.

         At first,          [off, off, off, off, off, off].
         1st Round          [on, on, on, on, on, on].
         2nd Round          [on, off, on, off, on, off].
         3rd                [on, off, off, off, on, on].
         4th                [on, off, off, on, on, on]
         5th                [on, off, off, on, off, on]
         6th                [on, off, off, on, off, off]
                             1              4
         factors            {1}           {1,2,4}

     ??

     So you should return 2, because there are two bulbs are on.

         Medium
     */

    /**
     * A bulb ends up on iff it is switched an odd number of times.
     *
     * Call them bulb 1 to bulb n. Bulb i is switched in round d if and only if d divides i.
     * （i % d == 0)
     * So bulb i ends up on if and only if it has an odd number of divisors.
     *
     * Divisors (factors) come in pairs, like i=12 has divisors 1 and 12, 2 and 6, and 3 and 4.
     * Except when i is a square, like 36 has divisors 1 and 36, 2 and 18, 3 and 12, 4 and 9,
     * and double divisor 6. So bulb i ends up on if and only if i is a square.
     *
     * So just count the square numbers.
     *
     * Let R = int(sqrt(n)). That's the root of the largest square in the range [1,n].
     * And 1 is the smallest root. So you have the roots from 1 to R, that's R roots.
     * Which correspond to the R squares. So int(sqrt(n)) is the answer.
     * (C++ does the conversion to int automatically, because of the specified return type)
     *
     * Ligth is OFF : after even number of operations
     * Light is ON : after odd number of operations
     * "every ith round" -> i is factor of number n
     *
     * 求完全平方数的个数
     */

    public int bulbSwitch(int n) {
        return (int)Math.sqrt(n);
    }

    /**
     * 有n个学生（编号为sid = [1..n]）依次走进有n个锁柜的房间（锁的编号为lockid = [1..n])，
     * 该学生将会打开或锁上lockid可以被sid整除的锁，写一个打印所有在第n个学生操作后处于打开的
     * 状态的lockid。
     *
     * Initial State
     *   [X  X  X  X  X  X]
     *
     *   [1, 2, 3, 4, 5, 6]
     * 1 [O  O  O  O  O  O]
     * 2 [O  X  O  X  O  X]
     * 3 [O  X  X  X  O  O]
     * 4 [O  X  X  O  O  O]
     * 5 [O  X  X  O  X  O]
     * 6 [O  X  X  O  X  X]
     *
     * 打开 -> O
     * 锁上 -> X
     */
    public class BulbSwitcher_1 {
        List<Integer> switcher(int N) {
            List<Integer> res = new ArrayList<Integer>();

            for (int i = 1; i <= N; i++) {
                int sqrt = (int)Math.sqrt(i);

                if (sqrt * sqrt == i) { // 是否是整除, if yes, 会落单
                    res.add(i); // This guy will be on, as there is odd number of turn on/off
                }
            }

            return res;
        }
    }
}
