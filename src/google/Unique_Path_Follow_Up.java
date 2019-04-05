package google;

import java.util.*;

public class Unique_Path_Follow_Up {
    /**
     * LE_62_Unique_Path
     * LE_63_Unique_Path_II
     *
     * 给定一个矩形的宽和长，求所有可能的路径数量
     *
     * Rules：
     * 1. 从左上角走到右上角
     * 2. 要求每一步只能向正右、右上或右下走，即 →↗↘
     *
     * 思路:
     * 按照列dp, dp[i][j] = dp[i - 1][j - 1] + dp[i][j - 1] + dp[i + 1][j - 1]，
     * 注意i-1，i+1需要存在
     *
     * followup1: 优化空间复杂度至 O(n)
     * followup2: 给定矩形里的三个点，判断是否存在遍历这三个点的路经
     * followup3: 给定矩形里的三个点，找到遍历这三个点的所有路径数量
     * followup4: 给定一个下界 (x == H)，找到能经过给定下界的所有路径数量 (x >= H)
     * followup5: 起点和终点改成从左上到左下，每一步只能 ↓↘↙，求所有可能的路径数量
     *
     * Solution is Python
     * https://github.com/jaychsu/algorithm/blob/master/other/unique_paths_with_followups.py
     *
     *
     *     dp[0][0] = 1
     *
     *     for y in range(1, n):
     *         for x in range(m):
     *             dp[x][y] = dp[x][y - 1]
     *
     *             //since y starts from 1, so y - 1 is always valid.
     *             //here x > 0 guarantees x - 1 is valid.
     *             if x > 0:
     *                 dp[x][y] += dp[x - 1][y - 1]
     *
     *             // x + 1 < m : make sure x + 1 is a valid index
     *             if x + 1 < m:
     *                 dp[x][y] += dp[x + 1][y - 1]
     *
     *     return dp[0][n - 1]
     */

    public static int uniquePaths(int m, int n) {
        int[][] dp = new int[m][n];
        dp[0][0] = 1;

        for (int j = 1; j < n; j++) {
            for (int i = 0; i < m; i++) {
                dp[i][j] = dp[i][j - 1];

                if (i > 0) {
                    dp[i][j] += dp[i - 1][j - 1];
                }

                if (i + 1 < m) {
                    dp[i][j] += dp[i + 1][j - 1];
                }

            }
        }

        return dp[m - 1][n - 1];
    }

    /**
     * followup1: 优化空间复杂度至 O(n)
     */
    public static int uniquePaths_followup_1(int m, int n) {
        int[] dp = new int[m];
        dp[0] = 1;
        int pre = 0, cur = 0;

        for (int j = 1; j < n; j++) {
            pre = 0;
            cur = 0;

            for (int i = 0; i < m; i++) {
                pre = cur;
                cur = dp[i];

                if (i > 0) {
                    dp[i] += pre;//dp[i - 1][j - 1]
                }

                if (i + 1 < m) {
                    dp[i] += dp[i + 1];//dp[i + 1][j - 1]
                }
            }
        }

        return dp[0];
    }

   /**
    * 思路：只保留上一列的空间，用两个数组滚动dp
    **/
    public int uniquePaths_followup_1_1(int rows, int cols) {
        int[] dp = new int[rows];
        int[] tmp = new int[rows];
        dp[0] = 1;

        for(int j = 1 ; j  < cols ; j++) {
            for(int i = 0 ; i < rows ; i++) {
                int val1 = i - 1 >= 0 ? dp[i - 1] : 0;
                int val2 = dp[i];
                int val3 = i + 1 < rows ? dp[i + 1] : 0;

                tmp[i] = val1 + val2 + val3;
            }
            System.arraycopy(tmp, 0, dp, 0, tmp.length);
        }

        return dp[0];
    }

    /**
     * followup2: 给定矩形里的三个点，判断是否存在遍历这三个点的路经
     * 思路：
     * 假设三个点坐标为(x1, y1) (x2, y2) (x3, y3)
     * 1：从(0,0)出发，如果后一个点在前一个点展开的扇形区域内，则可以被达到
     * 2：先对三个点按照纵坐标y排序，如果一个y上有一个以上的点，则返回false
     * 3：对于(xi, yi)，得到前一个点在该列的可达范围 :
     *                len = yi - y(i-1)
     *                upper = x(i-1) - len
     *                lower = x(i-1) + len
     * 如果[xi]在这个范围内 (lower <= xi <= upper)，则可达
     */
    public boolean uniquePaths_followup_2(int[][] points) {
        List<int[]> list = new ArrayList<>();
        list.add(new int[]{0, 0});

        for (int[] point : points) {
            list.add(point);
        }

        /**
         * sorted by cols
         */
        Collections.sort(list, (a, b) -> {
            return a[1] - b[1];
        });

        for (int i = 1; i < list.size(); i++) {
            int[] curr = list.get(i);
            int[] prev = list.get(i - 1);

            /**
             * 两个点在一个列上，并且不是同一个点。
             */
            if (curr[1] == prev[1] && curr[0] != prev[0]) {
                return false;
            }

            int colDist = curr[1] - prev[1];//horizontal distance
            int upper = prev[0] - colDist;//
            int lower = prev[0] + colDist;

            if (lower <= curr[0] && curr[0] <= upper) {
                continue;
            } else {
                return false;
            }

//            if (curr[0] <= lower && curr[0] >= upper) {
//                continue;
//            } else {
//                return false;
//            }
        }
        return true;
    }

    /**
     * followup3: 给定矩形里的三个点，找到遍历这三个点的所有路径数量
     *
     * 思路：
     * 1：还是按照follow up 1的思路用滚动数组dp，但是如果当前列有需要到达的点时，
     *    只对该点进行dp，其他格子全部置零，表示我们只care这一列上经过目标点的路径
     * 2：如果一列上有多个需要达到的点，直接返回0；
     */
    public int uniquePaths_followup_3(int rows, int cols, int[][] points) {
        int[] dp = new int[rows];
        int[] tmp = new int[rows];

        /**
         * hashmap : key - given points row value, value - col value
         * point[x][y], x is col, y is row, so:
         * map(point[1], point[0])
         */
        Map<Integer, Integer> map = new HashMap<>();

        for(int[] point : points) {
            if(map.containsKey(point[1])) {//point[
                return 0;
            } else {
                map.put(point[1], point[0]);
            }
        }

        int res = 0;
        dp[0] = 1;

        for(int j = 1 ; j  < cols ; j++) {
            for(int i = 0 ; i < rows ; i++) {
                int val1 = i - 1 >= 0 ? dp[i - 1] : 0;
                int val2 = dp[i];
                int val3 = i + 1 < rows ? dp[i + 1] : 0;
                tmp[i] = val1 + val2 + val3;
            }

            System.arraycopy(tmp, 0, dp, 0, tmp.length);

            /**
             * find one of the given points
             */
            if(map.containsKey(j)) {
                int row = map.get(j);
                for(int i = 0 ; i < rows; i++) {
                    /**
                     * "只对该点进行dp，其他格子全部置零，表示我们只care这一列上经过目标点的路径"
                     * 如果发现的是第一个点，则只有该点统计路径数目，等于我么从该点出发。。。
                     */
                    if(i != row) {
                        dp[i] = 0;
                    } else {
                        res = dp[i];
                    }
                }
            }
        }

        return res;
    }

    /**
     * followup4: 给定一个下界 (x == H)，找到能经过给定下界的所有路径数量 (x >= H)
     *
     * 思路：
     * 1：先dp一遍，得到所有到右上的路径数量
     * 2：然后在 0<=x<=H, 0<=y<=cols 这个小矩形中再DP一遍得到不经过下界的所有路径数量
     * 3：两个结果相减得到最终路径数量
     *
     * 重用follow up 1的代码
     */
    public int uniquePaths_followup_4(int rows, int cols, int H) {
        return uniquePaths_followup_1(rows, cols) - uniquePaths_followup_1(H, cols);
    }

    /**
     * followup5: 起点和终点改成从左上到左下，每一步只能 ↓↘↙，求所有可能的路径数量
     *
     * 参考代码：按照 行 dp，其他地方不变
     */
    public int uniquePaths_followup_5(int rows, int cols) {
        int[] dp = new int[cols];
        int[] tmp = new int[cols];
        dp[0] = 1;

        for(int i = 1 ; i  < rows ; i++) {
            for(int j = 0 ; j < cols ; j++) {
                int val1 = j - 1 >= 0 ? dp[j - 1] : 0;
                int val2 = dp[j];
                int val3 = j + 1 < cols ? dp[j + 1] : 0;

                tmp[i] = val1 + val2 + val3;
            }
            System.arraycopy(tmp, 0, dp, 0, tmp.length);
        }
        return dp[0];
    }
}
