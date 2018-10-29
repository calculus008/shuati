package lintcode;

/**
 * Created by yuank on 10/28/18.
 */
public class LI_92_Backpack {
    /**
         Given n items with size Ai, an integer m denotes the size of a backpack.
         How full you can fill this backpack?

         Example
         If we have 4 items with size [2, 3, 5, 7], the backpack size is 11,
         we can select [2, 3, 5], so that the max size we can fill this backpack is 10.
         If the backpack size is 12. we can select [2, 3, 7] so that we can fulfill the backpack.

         You function should return the max size we can fill in the given backpack.

         Challenge
         O(n x m) time and O(m) memory.

         O(n x m) memory is also acceptable if you do not know how to optimize memory.

         Notice
         You can not divide any item into small pieces.

         Medium
     */

    /**
     * https://blog.csdn.net/luoshengkim/article/details/76514558
     *
     * Solution 1
     * Time and Space : O(m * n)
     *
     *  bp[i][j] 前i个物品，取出一些能否组成和为j
     */
    public int backPack1(int m, int[] A) {
        if (m == 0 || A == null || A.length == 0) return 0;

        int n = A.length;
        boolean[][] dp = new boolean[n + 1][m + 1];
        dp[0][0] = true;

        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                dp[i][j] = dp[i - 1][j];
                if (j >= A[i - 1] && dp[i - 1][j - A[i - 1]]) {
                    dp[i][j] = true;
                }
            }
        }

        for (int j = m; j >= 0; j--) {
            if (dp[n][j]) {
                return j;
            }
        }

        return 0;
    }

    /**
     * Solution 2
     * Time  : O(m * n)
     * Space : O(m)
     * 滚动数组 ： apply “% 2” everywhere index i is referenced.
     */
    public int backPack2(int m, int[] A) {
        if (m == 0 || A == null || A.length == 0) return 0;

        int n = A.length;
        boolean[][] dp = new boolean[2][m + 1];
        dp[0][0] = true;

        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                dp[i % 2][j] = dp[(i - 1) % 2][j];
                if (j >= A[i - 1] && dp[(i - 1) % 2][j - A[i - 1]]) {
                    dp[i % 2][j] = true;
                }
            }
        }

        for (int j = m; j >= 0; j--) {
            if (dp[n % 2][j]) {
                return j;
            }
        }

        return 0;
    }

    /**
     * Solution 3
     * Time  : O(m * n)
     * Space : O(m)
     *
     * dp[i] : size为i时，背包里所能装下的最多item的个数。
     */
    public static int backPack3(int m, int[] A) {
        if (m == 0 || A == null || A.length == 0) return 0;

        int[] dp = new int[m + 1];//!!! int, not boolean
        for (int i = 0; i < A.length; i++) {
            for (int j = m; j >= A[i]; j--) {
                System.out.println("i=" + i + ", dp[" + j +" - A[" + i + "]]=" + dp[j - A[i]] + ", dp[" + j + "]=" + dp[j]);

                dp[j] = Math.max(dp[j], dp[j - A[i]] + A[i]);

                System.out.println("i=" + i + ", dp[" + j +"]=" + dp[j]);
            }
        }

        return dp[m];
    }

    /**
     i=0, dp[11 - A[0]]=0, dp[11]=0
     i=0, dp[11]=2
     i=0, dp[10 - A[0]]=0, dp[10]=0
     i=0, dp[10]=2
     i=0, dp[9 - A[0]]=0, dp[9]=0
     i=0, dp[9]=2
     i=0, dp[8 - A[0]]=0, dp[8]=0
     i=0, dp[8]=2
     i=0, dp[7 - A[0]]=0, dp[7]=0
     i=0, dp[7]=2
     i=0, dp[6 - A[0]]=0, dp[6]=0
     i=0, dp[6]=2
     i=0, dp[5 - A[0]]=0, dp[5]=0
     i=0, dp[5]=2
     i=0, dp[4 - A[0]]=0, dp[4]=0
     i=0, dp[4]=2
     i=0, dp[3 - A[0]]=0, dp[3]=0
     i=0, dp[3]=2
     i=0, dp[2 - A[0]]=0, dp[2]=0
     i=0, dp[2]=2

     i=1, dp[11 - A[1]]=2, dp[11]=2
     i=1, dp[11]=5
     i=1, dp[10 - A[1]]=2, dp[10]=2
     i=1, dp[10]=5
     i=1, dp[9 - A[1]]=2, dp[9]=2
     i=1, dp[9]=5
     i=1, dp[8 - A[1]]=2, dp[8]=2
     i=1, dp[8]=5
     i=1, dp[7 - A[1]]=2, dp[7]=2
     i=1, dp[7]=5
     i=1, dp[6 - A[1]]=2, dp[6]=2
     i=1, dp[6]=5
     i=1, dp[5 - A[1]]=2, dp[5]=2
     i=1, dp[5]=5
     i=1, dp[4 - A[1]]=0, dp[4]=2
     i=1, dp[4]=3
     i=1, dp[3 - A[1]]=0, dp[3]=2
     i=1, dp[3]=3

     i=2, dp[11 - A[2]]=5, dp[11]=5
     i=2, dp[11]=10
     i=2, dp[10 - A[2]]=5, dp[10]=5
     i=2, dp[10]=10
     i=2, dp[9 - A[2]]=3, dp[9]=5
     i=2, dp[9]=8
     i=2, dp[8 - A[2]]=3, dp[8]=5
     i=2, dp[8]=8
     i=2, dp[7 - A[2]]=2, dp[7]=5
     i=2, dp[7]=7
     i=2, dp[6 - A[2]]=0, dp[6]=5
     i=2, dp[6]=5
     i=2, dp[5 - A[2]]=0, dp[5]=5
     i=2, dp[5]=5

     i=3, dp[11 - A[3]]=3, dp[11]=10
     i=3, dp[11]=10
     i=3, dp[10 - A[3]]=3, dp[10]=10
     i=3, dp[10]=10
     i=3, dp[9 - A[3]]=2, dp[9]=8
     i=3, dp[9]=9
     i=3, dp[8 - A[3]]=0, dp[8]=8
     i=3, dp[8]=8
     i=3, dp[7 - A[3]]=0, dp[7]=7
     i=3, dp[7]=7
     */
    public static void main(String [] args) {
        backPack3(11, new int[] {2, 3, 5, 7});
    }
}
