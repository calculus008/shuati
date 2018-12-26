package leetcode;

/**
 * Created by yuank on 3/4/18.
 */
public class LE_62_Unique_Path {
    /**
        A robot is located at the top-left corner of a m x n grid (marked 'Start' in the diagram below).

        The robot can only move either down or right at any point in time. The robot is trying to reach the bottom-right corner of the grid.

        How many possible unique paths are there?
     */
    /**
        Solution 1: straight forward with 2D array
        Time : O(m*n),Space : O(m*n)
     **/
    public static int uniquePaths1(int m, int n) {
        int[][] a = new int[m][n];
        a[0][0] = 1;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 || j == 0) {
                    a[i][j] = 1;
                } else {
                    a[i][j] = a[i][j - 1] + a[i - 1][j];
                }
            }
        }

        return a[m - 1][n - 1];
    }

    /**
        Solution 2:
        Evole from Solution 1, each time when we update path[i][j], we only need path[i - 1][j] (at the same column) and path[i][j - 1] (at the left column).
        So it is enough to maintain two columns (the current column and the left column) instead of maintaining the full m*n. Space O(min(m, n))

        Further more,  we find that keeping two columns is used to recover pre[i], which is just cur[i] before its update.
        So there is even no need to use two vectors and one is just enough.

        Well, till now, I guess you may even want to optimize it to O(1) space complexity since the above code seems
        to rely on only cur[i] and cur[i - 1]. You may think that 2 variables is enough? Well, it is not.
        Since the whole cur needs to be updated for n - 1 times, it means that all of its values need to be saved
        for next update and so two variables is not enough.
    **/
    public static int uniquePaths2(int m, int n) {
         if (m > n) return uniquePaths2 (n, m);

         int[] a = new int[m];
         a[0] = 1;

         for (int i = 0; i < n; i++) {
             for (int j = 0; j < m; j++) {
                 if (i == 0 || j == 0) {
                     a[j] = 1;
                 } else {
                     a[j] += a[j-1];
                 }
             }
         }

         return a[m-1];
     }

    //Solution 3 : Math
    //Combination(count, k) = count!/(count-k)!*k! = (count-k+1)*(count-k+2)...(count-k+k)/k!
    //                      = (count-k+1)/1 * (count-k+2)/2 * (count-k+3)/3 .......(count-k+k)/k
    public static int uniquePaths3(int m, int n) {
        int count = m + n -2;
        int k = m -1;
        double res = 1;
        for (int i = 1; i <= k; i++) {
            res = res * (count - k + i) / i;
        }

        return (int)res;
    }


    //Solution 4: Space : O(n), scan the matrix row by row
    public static int uniquePaths4(int m, int n) {
        int[] res = new int[n];
        res[0] = 1;

        for (int i = 0; i < m ; i++) {
            for (int j = 1; j < n; j++) {
                res[j] += res[j - 1];
            }
        }

        return res[n - 1];
    }
}