package lintcode;

/**
 * Created by yuank on 10/25/18.
 */
public class LI_476_Stone_Game {
    /**
         There is a stone game.At the beginning of the game the player picks n piles of stones in a line.

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

         Related : LE_312_Burst_Balloons
     */

     /**
      * 区间类DP
      *
      * Time  : O(n ^ 3)
      * Space : O(n ^ 2)
      *
      * State:    dp[i][j] 表示把前i个元素到前j个石子合并到一起的最小花费 (!!!)
      * Function: 预处理sum[i,j] 表示i到j所有石子价值和
      *
      *           dp[i][j] = min(dp[i][k]+dp[k+1][j]+sum[i,j]) 对于所有k属于{i,j}
      *
      *           k是i到j之间的分割点，这里计算总和 ：
      *           从i到k区间的最小花费 + 从k+1到j区间的最小花费 + i到j最后合并到一起的花费(也就是i到j的所有元素的和).
      *
      *
      * Intialize:for each i， dp[i][i] = 0
      * Answer:   dp[1][n]
      *
      * For input {4, 1, 1, 4}, output:
      * dp[1][2]=5
      * dp[2][3]=2
      * dp[3][4]=5
      * dp[1][3]=8
      * dp[2][4]=8
      * dp[1][4]=18
      *
      l=2, i=1, j=2, k=1
      l=2, i=2, j=3, k=2
      l=2, i=3, j=4, k=3
      l=3, i=1, j=3, k=1
      l=3, i=1, j=3, k=2
      l=3, i=2, j=4, k=2
      l=3, i=2, j=4, k=3
      l=4, i=1, j=4, k=1
      l=4, i=1, j=4, k=2
      l=4, i=1, j=4, k=3
      */
    public static int stoneGame(int[] A) {
         if (A == null || A.length == 0) return 0;

         int n = A.length;

         /**
          * Add padding for both sum and dp array.
          * For this problem, we can also not using padding
          */
         int[] sum = new int[n + 1];
         int[][] dp = new int[n + 1][n + 1];

         for (int i = 1; i <= n; i++) {
              sum[i] = sum[i - 1] + A[i - 1];
         }

         /**
          * !!!因为是求两部分之和，l要从2开始。如果从1开始，
          *    则下面k的循环不会执行。
          */
         for (int l = 2; l <= n; l++) {
              for (int i = 1; i + l - 1 <= n; i++) {
                   int j = i + l - 1;

                   /**
                    * !!!为求min作准备
                    */
                   dp[i][j] = Integer.MAX_VALUE;

                   /**
                    * "k < j" : 这里j <= n, 如果 k <= j, 则下面k+1会数组越界，
                    * (dp[][]的最大下标是n)
                    */
                   for (int k = i; k < j; k++) {
                        System.out.println("l="+ l+ ", i="+i+ ", j="+j+ ", k="+k);

                        dp[i][j] = Math.min(dp[i][j],
                                dp[i][k] + dp[k + 1][j] + sum[j] - sum[i - 1]);
                   }

//                   System.out.println("dp["+i+"][" + j +"]=" + dp[i][j]);
              }
         }

         return dp[1][n];
    }

     public static void main(String [] args)
     {
          stoneGame(new int[] {4,1,1,4});
     }
}
