package lintcode;

/**
 * Created by yuank on 10/25/18.
 */
public class LI_593_Stone_Game_II {
    /**
         There is a stone game.At the beginning of the game the
         player picks n piles of stones in a CIRCLE.

         The goal is to merge the stones in one pile observing the following rules:

         At each step of the game,the player can merge two adjacent piles to a new pile.
         The score is the number of stones in the new pile.
         You are to determine the minimum of the total score.

         Example
         For [4, 1, 1, 4], in the best solution, the total score is 18:

         1. Merge second and third piles => [4, 2, 4], score +2
         2. Merge the first two piles => [6, 4]，score +6
         3. Merge the last two piles => [10], score +10

         Other two examples:
         [1, 1, 1, 1] return 8
         [4, 4, 5, 9] return 43

         Hard
     */

    /**
     * 复制一份数组，接在原数组后面，我们只需要取以下标0到（n-1）开头的最大长度为n子区间的得分情况，
     * dp[i][j]为i, j之间的最小得分
     * dp[i][j] = min(dp[i][k] + dp[k+1][j] + weight)
     * 最后比较所有的n长度有效区间谁最小即可，返回该值
     */
    public int stoneGame2(int[] A) {
        if (A == null || A.length == 0) {
            return 0;
        }
        int n = A.length;
        int[] B = new int[2 * n];
        for (int i = 0; i < 2 * n; i++) {
            B[i] = A[i % n];
            //System.out.println(B[i]);
        }

        int dp[][] = new int[2 * n][2 * n];

        for (int l = 2; l <= n; l++) {
            for (int i = 0; i + l - 1 < 2 * n; i++) {
                int j = i + l - 1;
                dp[i][j] = Integer.MAX_VALUE;

                int score = getScore(i, j, B);

                for (int k = i; k < j; k++) {
                    dp[i][j] = Math.min(dp[i][j], dp[i][k] + dp[(k + 1)][j] + score);
                }
            }
        }

        int res = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            //System.out.println(dp[i][i+n-1]);
            res = Math.min(dp[i][(i + n - 1)], res);
        }
        return res;
    }


    int getScore(int s, int e, int[] A) {
        int score = 0;
        for (int i = s; i <= e; i++) {
            score += A[i];
        }
        return score;
    }
}
