package leetcode;

/**
 * Created by yuank on 3/23/18.
 */
public class LE_174_Dungeon_Game {
    /*
        The demons had captured the princess (P) and imprisoned her in the bottom-right corner of a dungeon.
        The dungeon consists of M x N rooms laid out in a 2D grid. Our valiant knight (K) was initially positioned
        in the top-left room and must fight his way through the dungeon to rescue the princess.

        The knight has an initial health point represented by a positive integer. If at any point his health point drops to 0 or below,
        he dies immediately.

        Some of the rooms are guarded by demons, so the knight loses health (negative integers) upon entering these rooms;
        other rooms are either empty (0's) or contain magic orbs that increase the knight's health (positive integers).

        In order to reach the princess as quickly as possible, the knight decides to move only rightward or downward in each step.


        Write a function to determine the knight's minimum initial health so that he is able to rescue the princess.

        For example, given the dungeon below, the initial health of the knight must be at least 7
        if he follows the optimal path RIGHT-> RIGHT -> DOWN -> DOWN.

        -2 (K)	-3	3
        -5	-10	1
        10	30	-5 (P)

        Notes:

        The knight's health has no upper bound.
        Any room can contain threats or power-ups, even the first room the knight enters and the bottom-right room where the princess is imprisoned.
     */

    //DP, Time and Space : O(m * n)
    /*  dugeon[][]:
        -2  -3   3
        -5  -10  1
         10  30 -5

        dp[][]:
         0   0   0
         0   0   0
         1   1   6

         0   0   2
         0   0   5
         1   1   6

         0   0   2
         0   11  5
         1   1   6

         0   5   2
         0   11  5
         1   1   6

         7   5   2
         6   11  5
         1   1   6

    */
    public int calculateMinimumHP(int[][] dungeon) {
        if (dungeon == null || dungeon.length == 0) return 0;

        int m = dungeon.length;
        int n = dungeon[0].length;
        int[][] dp = new int[m][n];

        dp[m - 1][n - 1] = Math.max(1 - dungeon[m - 1][n - 1], 1);
        for (int i = n - 2; i >= 0; i--) {//last row
            dp[m - 1][i] = Math.max(dp[m - 1][i + 1] - dungeon[m - 1][i], 1);
        }

        for (int i = m - 2; i >= 0; i--) {//last column
            dp[i][n - 1] = Math.max(dp[i + 1][n - 1] - dungeon[i][n - 1], 1);
        }

        for (int i = m - 2; i >= 0; i--) {
            for (int j = n - 2; j >= 0; j--) {
                dp[i][j] = Math.min(Math.max(dp[i + 1][j] - dungeon[i][j], 1),
                        Math.max(dp[i][j + 1] - dungeon[i][j], 1));
            }
        }

        return dp[0][0];
    }
}
