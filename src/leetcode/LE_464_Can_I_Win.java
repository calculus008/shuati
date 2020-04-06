package leetcode;

import java.util.Arrays;
import java.util.HashMap;

public class LE_464_Can_I_Win {
    /**
     * In the "100 game," two players take turns adding, to a running total,
     * any integer from 1..10. The player who first causes the running total
     * to reach or exceed 100 wins.
     *
     * What if we change the game so that players cannot re-use integers?
     *
     * For example, two players might take turns drawing from a common pool
     * of numbers of 1..15 without replacement until they reach a total >= 100.
     *
     * Given an integer maxChoosableInteger and another integer desiredTotal,
     * determine if the first player to move can force a win, assuming both
     * players play optimally.
     *
     * You can always assume that maxChoosableInteger will not be larger than
     * 20 and desiredTotal will not be larger than 300.
     *
     * Example
     *
     * Input:
     * maxChoosableInteger = 10
     * desiredTotal = 11
     *
     * Output:
     * false
     *
     * Explanation:
     * No matter which integer the first player choose, the first player will lose.
     * The first player can choose an integer from 1 up to 10.
     * If the first player choose 1, the second player can only choose integers from 2 up to 10.
     * The second player will win by choosing 10 and get a total = 11, which is >= desiredTotal.
     * Same with other integers chosen by the first player, the second player will always win.
     */

    class Solution1 {
        /**
         * https://leetcode.com/problems/can-i-win/discuss/95277/Java-solution-using-HashMap-with-detailed-explanation
         * Time : O(2 ^ n), m subprobelms, each takes O(2 ^ n)
         * Space : O(2 ^ n)
         **/

        public boolean canIWin(int maxChoosableInteger, int desiredTotal) {
            if (desiredTotal <= 0) {
                return true;
            }

            if (maxChoosableInteger * (maxChoosableInteger + 1) /2 < desiredTotal) {
                return false;
            }

            // return helper(maxChoosableInteger, desiredTotal, new boolean[maxChoosableInteger], new HashMap<String, Boolean>());
            return helper2(maxChoosableInteger, desiredTotal, 0, new HashMap<Integer, Boolean>());
        }

        private boolean helper(int m, int t, boolean[] state, HashMap<String, Boolean> map) {
            if (t < 0) {
                return false;
            }

            //!!! remember current state
            String cur = Arrays.toString(state);

            if (map.containsKey(cur)) {
                return map.get(cur);
            }

            for (int i = 0; i < m; i++) {
                if (!state[i]) {
                    state[i] = true;
                    //!!! Since we try to see if the first player can win, the next player should fail, therefore - !helper()
                    if (t <= i + 1 || !helper(m, t - (i + 1), state, map)) {
                        //!!! backtrack
                        state[i] = false;
                        //!!! recursion with memo, always remember to put result into the memo
                        map.put(cur, true);
                        return true;
                    }
                    state[i] = false;
                }
            }

            map.put(cur, false);
            return false;
        }

        /**
         * Since maxChoosableInteger is smaller than 29, therefore,
         * use integer (32 bit) to store state and used as key in dist
         **/
        private boolean helper2(int m, int t, int state, HashMap<Integer, Boolean> map) {
            // if (t < 0) {
            //     return false;
            // }

            if (map.containsKey(state)) {
                return map.get(state);
            }

            for (int i = 0; i < m; i++) {
                if ((state & (1 << i)) != 0) {//used
                    continue;
                }

                /**
                 * 在调用下一层时，传"state | (1 << i)" 最为参数，这样, 当前层
                 * 的state值不变，省去了backtracking。
                 */
                if(t <= i + 1 || !helper2(m, t - (i + 1), state | (1 << i), map)) {
                    map.put(state, true);
                    return true;
                }
            }

            map.put(state, false);
            return false;
        }
    }

    /**
     * Huahua's version
     * http://zxi.mytechroad.com/blog/searching/leetcode-464-can-i-win/
     *
     * In essence it is permutation problem.
     */
    class Solution2 {
        private byte[] mem;
        public boolean canIWin(int M, int T) {
            int sum = M * (M + 1) / 2;
            if (sum < T) {
                return false;
            }
            if (T <= 0) {
                return true;
            }

            mem = new byte[1 << M];
            return canIWin(M, T, 0);
        }

        private boolean canIWin(int M, int T, int state) {
            if (T <= 0) {
                return false;
            }
            if (mem[state] != 0) {
                return mem[state] == 1;
            }

            for (int i = 0; i < M; ++i) {
                if ((state & (1 << i)) > 0) continue;
                if (!canIWin(M, T - (i + 1), state | (1 << i))) {
                    mem[state] = 1;
                    return true;
                }
            }
            mem[state] = -1;
            return false;
        }
    }
}
