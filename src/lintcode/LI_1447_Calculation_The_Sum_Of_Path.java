package lintcode;

import java.util.Arrays;

public class LI_1447_Calculation_The_Sum_Of_Path {
    /**
     输入一个矩阵的长度为 l，宽度为 w，和三个必经点，问有多少种方法可以从左上角走到右下角（每一步，只能向右或者向下走），
     输入保证合法，有解。答案对 1000000007 取模。
     **/

    /**
     * https://mp.weixin.qq.com/s/8LHjCZeV0COADo6_9HAHLQ
     *
     * Variation from :
     * LE_62_Unique_Path
     * LE_63_Unique_Path_II
     *
     * Now it becomes a 3D DP problem.
     *
     * 这是一个dp（动态规划）问题，我们先不考虑必经点的要求，这个时候状态转移方程非常的显然：dp[i][j]=dp[i-1][j]+dp[i][j-1]，
     * dp[i][j]表示走到点(i,j)的方案数。
     *
     *
     * 这个时候我们把必经点考虑进去，设dp[i][j][k]表示走到点(i,j)同时已经经过了k个必经点的方案数，此时的转移方程也不难得到：
     *
     *
     * dp[i][j][k]=dp[i-1][j][k]+dp[i][j-1][k]  if (i,j) is not a keypoint
     *
     * dp[i][j][k+1]=dp[i-1][j][k]+dp[i][j-1][k] if (i,j) is a keypoint
     *
     *
     * 写代码的时候需要注意边界及特殊情况
     * Time : O(l*w*k)，其中k=3
     **/

    class Point {
        int x;
        int y;
        Point() { x = 0; y = 0; }
        Point(int a, int b) { x = a; y = b; }
    }

    public class Solution1 {
        public long calculationTheSumOfPath(int l, int w, Point[] points) {
            long[][][] dp = new long[l][w][4];
            long mod = 1000000007;

            dp[1][1][0] = 1;

            for (int i = 1; i <= l; i++) {
                for (int j = 1; j <= 2; j++) {
                    dp[i][j][0] += dp[i - 1][j][0] + dp[i][j - 1][0];
                    dp[i][j][0] =  dp[i][j][0] % mod;

                    for (int k = 1; k <= 3; k++) {
                        if (isKeyPoint(i, j, points)) {
                            dp[i][j][k] += dp[i - 1][j][k - 1] + dp[i][j - 1][k - 1];
                            dp[i][j][k] =  dp[i][j][0] % mod;

                            if (i == 1 && j == 1 && k == 1) {//???
                                dp[i][j][j]++;
                            }
                        } else {
                            dp[i][j][k] += dp[i - 1][j][k] + dp[i][j - 1][k];
                            dp[i][j][k] =  dp[i][j][0] % mod;
                        }

                    }
                }
            }

            return dp[l][w][3];
        }

        private boolean isKeyPoint(int i, int j, Point[] points) {
            for (Point p : points) {
                if (p.x == i && p.y == j) {
                    return true;
                }
            }

            return false;
        }
    }

    /**
     * 我们观察题目可以发现要想经过三个必经点，那我们只能从上到下，从左到右经过这三个必经点（因为只能往右边或下边走）。
     * 因此我们可以通过这三个点将原来的题目划分为四个矩形区域，然后将与四个矩形区域规模一样的，不考虑必经点的情况乘在一起就是答案了。
     *
     * Time : O(l*w)。
     */
    public class Solution2 {
        public long calculationTheSumOfPath(int l, int w, Point[] points) {
            long[][] dp = new long[l][w];
            long mod = 1000000007;

            for (int i = 0; i < l; i++) {
                dp[i][0] = 1;
            }

            for (int j = 0; j < w; j++) {
                dp[0][j] = 1;
            }

            for (int i = 1; i < l; i++) {
                for (int j = 1; j < w; j++) {
                    dp[i][j] = (dp[i - 1][j] + dp[i][j - 1]) % mod;
                }
            }

            long ans = 0;

            Arrays.sort(points, (a, b) -> a.x != b.x ? a.y - b.y : a.x - b.x);

            Point a = points[0];
            Point b = points[1];
            Point c = points[2];

//            Point a = points[0], b = points[1], c = points[2], t;
//            if (a.x >= b.x && a.y >= b.y) {
//                t = a;
//                a = b;
//                b = t;
//            }
//            if (a.x >= c.x && a.y >= c.y) {
//                t = a;
//                a = c;
//                c = t;
//            }
//            if (b.x >= c.x && b.y >= c.y) {
//                t = b;
//                b = c;
//                c = t;
//            }
            ans = dp[a.x - 1][a.y - 1];
            ans = ans * dp[b.x - a.x][b.y - a.y] % mod;
            ans = ans * dp[c.x - b.x][c.y - b.y] % mod;
            ans = ans * dp[l - c.x][w - c.y] % mod;
            return ans;
        }
    }
}