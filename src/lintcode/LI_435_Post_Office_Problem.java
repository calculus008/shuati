package lintcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yuank on 10/24/18.
 */
public class LI_435_Post_Office_Problem {
    /**
         On one line there are n houses. Give you an array of integer means the the position
         of each house. Now you need to pick k position to build k post office, so that the
         sum distance of each house to the nearest post office is the smallest. Return the
         least possible sum of all distances between each village and its nearest post office.

         Example
         Given array a = [1,2,3,4,5], k = 2.
         return 3.

         Challenge
         Could you solve this problem in O(n^2) time ?

         Hard
     */

    /**
         Solution 1

         Time : O(n ^ 2)

         1.
         首先，这是一道随着k的增多，情况不断变化的题，而且没有一个起点
         如果要用dfs尝试完所有情况，只能是brute force，也能做，但是先让它待定。

         看看dp方法行不行，dp必然伴随着由小及大的关系，有可能是前后关系，也可能是区间关系，
         具体是什么关系下别急着下定论。

         【最关键的地方】先分析如果k = 1的情况，我们可以得出只有index 为mid的时候，整体的sum才最小
         这里涉及到数学推导，大家可以自己去推导一个。
         即如果区间是 [2,3,4,5,6,22,28,123] 在idx 为3或4时总的sum最小

         此时我们得到一个重要的信息，就是，我们可以利用这个信息求出
         往任意区间内加入一个office之后，这个区间的minimum sum。

         有了这个关键信息之后，我们才能去想要怎么写dp（要不然去陷入空想dp的死循环）
         for x in range(i, j)：
         dp[j][k] = min(dp[x][k - 1] + dis[j + 1][i])
         此时的x 和 j + 1就像两个圆心，找到他们的相切点，即得到最小的sum

         这道题个人感觉很难，包含了很多很难想到的思想。。


         2.
         答案中应用了四边形不等式优化，复杂度是O(n^2)， 如果不用四边形不等式优化复杂度是O(kn^2)。
         四边形不等式不要求掌握，证明这个题目满足使用四边形不等式的要求就比较难，不推荐。

         区间类DP
         状态函数：

         dp[i][l]＝dp[j][l-1] + dis[j+1][i] (l-1<=j<i)。

         其中dp[i][l]表示在前i个村庄中建l个post的最短距离，j为分隔点，可以将问题转化为 :

            在前j个村庄建l－1个post的最短距离 ＋ 在第j＋1到第i个村庄建1个post的最短距离。

         其中有个性质，如元素是单调排列的，则在中间位置到各个元素的距离和最小。

         初始化dis矩阵，枚举不同开头和结尾的村庄之间建1个post的最小距离，即求出开头和结尾村庄的中间点，
         然后计算开头到结尾的所有点到中间点的距离。记得要对原矩阵排序，这样才能用中间点距离最小性质。

         初始化dp矩阵，即初始化dp[i][1]，求前i个村庄建1个post的最小距离（可根据dis求出）。+

         post数l从2枚举到k，开始村庄i从l枚举到结尾（因为要建l个post至少需要l个村庄，否则没有意义），
         然后根据状态函数求dp[i][l]，分割点j从l－1枚举到i-1（前j个村庄建l－1个post则至少需要l－1个村庄），
         在这些分隔点的情况下求dp[i][l]的最小值。

         返回dp[n][k]即可。
     */

    public class Solution1 {
        int[][] init(int[] A) {
            int n = A.length;
            int[][] dis = new int[n + 1][n + 1];
            for (int i = 1; i <= n; i++) {
                for (int j = i + 1; j <= n; ++j) {
                    int mid = (i + j) / 2;
                    for (int k = i; k <= j; ++k) {
                        dis[i][j] += Math.abs(A[k - 1] - A[mid - 1]);
                    }
                }
            }
            return dis;
        }

        public int postOffice(int[] A, int k) {
            int n = A.length;
            Arrays.sort(A);

            int[][] dis = init(A);
            int[][] dp = new int[n + 1][k + 1];

            if (n == 0 || k >= A.length) {
                return 0;
            }

            for (int i = 0; i <= n; ++i) {
                dp[i][1] = dis[1][i];

            }

            for (int nk = 2; nk <= k; nk++) {//loop for number of post offices
                for (int i = nk; i <= n; i++) {//loop for villages
                    dp[i][nk] = Integer.MAX_VALUE;
                    for (int j = 0; j < i; j++) {//分割点
                        if (dp[i][nk] == Integer.MAX_VALUE || dp[i][nk] > dp[j][nk - 1] + dis[j + 1][i])
                            dp[i][nk] = dp[j][nk - 1] + dis[j + 1][i];
                    }
                }
            }
            return dp[n][k];
        }
    }

    /**
     * Solution 2
     * DFS
     */
    public class Solution2 {
        private int minDist;

        public int postOffice(int[] A, int K) {
            if (A == null || A.length <= K) {
                return 0;
            }
            // if K == 0, we can return Integer.MAX_VALUE

            minDist = Integer.MAX_VALUE;
            dfs(A, 0, new ArrayList<Integer>(K), K);

            return minDist;
        }

        private void dfs(int[] A, int pos, List<Integer> offices, int K) {
            if (offices.size() == K) {
                minDist = Math.min(minDist, computeDist(A, offices));
                return;
            }

            for (int i = pos; i < A.length; ++i) {
                offices.add(A[i]);
                dfs(A, i + 1, offices, K);
                offices.remove(offices.size() - 1);
            }
        }

        private int computeDist(int[] A, List<Integer> offices) {
            int dist = 0;

            for (int house : A) {
                int currMin = Integer.MAX_VALUE;

                for (int office : offices) {
                    currMin = Math.min(currMin, Math.abs(house - office));
                }

                dist += currMin;
            }

            return dist;
        }
    }

    /**
     * Solution 3
     * 加入首位两个dummy post office, 算出距离矩阵后就是常规的背包问题了
     */
    public class Solution {
        public int postOffice(int[] A, int k) {
            if(A==null||A.length ==0) return 0;

            Arrays.sort(A);
            int n = A.length;
            int[][] dis = new int[n+2][n+2];

            for(int i = 0; i< n+2; i++){
                for(int j = i+1; j< n+2; j++){
                    if(i== 0 && j==n+1){dis[i][j] = 0x7fffffff; continue;}
                    for(int p=i+1;p<j;p++){
                        dis[i][j] += Math.min((i==0?0x7fffffff:(A[p-1]-A[i-1])),(j==n+1?0x7fffffff:(A[j-1]-A[p-1])));
                    }
                }
            }

            int[][] dp  = new int[2][n+2];
            int cur= 0, pre = 1;

            for(int i= 1; i<n+2;i++){
                dp[cur][i] = dis[0][i];
            }

            for(int j= 0 ; j<k;j++){
                cur = 1-cur;    pre = 1-cur;
                for(int i= 1; i<n+2;i++){
                    dp[cur][i] = 0x7fffffff;
                    for(int p=0 ; p<=i ;p++)
                        dp[cur][i] = Math.min(dp[cur][i],dp[pre][i-p]+dis[i-p][i] ) ;
                }
            }

            return dp[cur][n+1];
        }
    }
}
