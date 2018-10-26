package lintcode;

/**
 * Created by yuank on 10/23/18.
 */
public class LI_394_Coins_In_A_Line {
    /**
         There are n coins in a line. Two players take turns to take one or two coins
         from right side until there are no more coins left. The player who take the last coin wins.

         Could you please decide the first play will win or lose?

         Example
         n = 1, return true.

         n = 2, return true.

         n = 3, return false.

         n = 4, return true.

         n = 5, return true.

         Challenge
         O(n) time and O(1) memory
     */

    /**
     * Solution 1
     *
     * Search + Memoization
     *
     * State: dp[i] 现在还剩i个硬币，现在先手取硬币的人最后输赢状况.
     *
     * 注意 ：此处dp[i]只是针对player1的，就是说dp[i]是和player1绑定的。!!!
     *
     *                  i            player1 move
     *                /  \
     *             i-1    i-2        player2 move
     *             / \    / \
     *          i-2 i-3 i-3 i-4      player1 move
     *     ........................
     *
     * Function: dp[i] = (dp[i-2] && dp[i-3]) || (dp[i-3] && dp[i-4])
     *
     * player1 will win if either left(pick 1)  or right(pick 2) will win
     *
     * Initialize:
     *    dp[0] = false  no coin to pick in the end, player1 lose.
     *    dp[1] = true
     *    dp[2] = true
     *    dp[3] = false
     *
     * Answer: • dp[n]
     */
    public boolean firstWillWin1(int n) {
        int[] dp = new int[n + 1];

        return MemorySearch(n, dp);

    }

    boolean MemorySearch(int n, int[] dp) { // 0 is empty, 1 is false, 2 is true
        /**
         * dp[n] == 0, 也就是说在初始状态，还不能用mem中的记录。
         */
        if (dp[n] != 0) {
            if (dp[n] == 1) {
                return false;
            } else {
                return true;
            }
        }

        if (n <= 0) {
            dp[n] = 1;
        } else if (n == 1) {
            dp[n] = 2;
        } else if (n == 2) {
            dp[n] = 2;
        } else if (n == 3) {
            dp[n] = 1;
        } else {
            if ((MemorySearch(n - 2, dp) && MemorySearch(n - 3, dp)) ||
                    (MemorySearch(n - 3, dp) && MemorySearch(n - 4, dp))) {
                dp[n] = 2;
            } else {
                dp[n] = 1;
            }
        }

        if (dp[n] == 2) return true;

        return false;
    }

    /**
         Solution 2

         dp[i]表示面对i个石子，是否当前（!!!) 先手必胜 (dp[i] = TRUE 勝 / FALSE 敗)

         就是说, dp[i]只针对当前要拿coin的player, 并不和player1或player2绑定。

         dp[i] = dp[i-1] == FALSE OR dp[i-2] == FALSE
         只要可以使得下一個人 有條路必敗 当前先手就贏了, 如果沒有任何的路, 使後手會輸 当前先手一定輸.

         dp[i] =
             TRUE， dp[i-1]==FALSE AND dp[i-2]==FALSE 拿1或2个石子都必胜
             TRUE， dp[i-1]==FALSE AND dp[i-2]==TRUE  拿1个石子必胜
             TRUE， dp[i-1]==TRUE  AND dp[i-2]==FALSE 拿2个石子必胜
             FALSE，dp[i-1]==TRUE  AND dp[i-2]==TRUE  必败

         只要当前後手有一種方法, 是必敗 当前先手必勝
         只要当前後手都是必勝 当前先手必敗.

         当前後手無論怎麼走都必勝 ---> 当前先手必敗
         当前後手只要有一條路必敗 ---> 当前先手必勝
     */
    public boolean firstWillWin2(int n) {
        if (n == 0) {
            return false;
        } else if (n == 1 || n == 2) {
            return true;
        }

        boolean[] dp = new boolean[n + 1];
        dp[1] = true;
        dp[2] = true;

        for (int i = 3; i < dp.length; i++) {
            dp[i] = !dp[i - 1] || !dp[i - 2];
        }

        return dp[n];
    }
}
