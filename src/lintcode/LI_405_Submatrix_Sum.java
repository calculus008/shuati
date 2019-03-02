package lintcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yuank on 10/1/18.
 */
public class LI_405_Submatrix_Sum {
    /**
         Given an integer matrix, find a submatrix where the sum of numbers is zero.
         Your code should return the coordinate of the left-up and right-down number.

         Have you met this question in a real interview?
         Example
         Given matrix

         [
             [1 ,5 ,7],
             [3 ,7 ,-8],
             [4 ,-8 ,9],
         ]
         return [(1,1), (2,2)]

         Medium
     */

    /**
     * 用前缀和优化, 令 sum[i][j] = sum[0][j] + sum[1][j] + ... + sum[i][j]
     *
     * 然后枚举上下边界, 这样就相当于在一行内, 求一个数组连续子串和为0的问题了.
     */
    class Solution1 {
        public int[][] submatrixSum(int[][] matrix) {
            int[][] result = new int[2][2];
            int M = matrix.length;
            if (M == 0) return result;
            int N = matrix[0].length;
            if (N == 0) return result;

            //!!! pre-compute: sum[i][j] = sum of submatrix [(0, 0), (i, j)]
            int[][] sum = new int[M + 1][N + 1];

            for (int j = 0; j <= N; ++j) sum[0][j] = 0;
            for (int i = 1; i <= M; ++i) sum[i][0] = 0;
            for (int i = 0; i < M; ++i) {
                for (int j = 0; j < N; ++j)
                    sum[i + 1][j + 1] = matrix[i][j] + sum[i + 1][j] + sum[i][j + 1] - sum[i][j];
            }

            for (int l = 0; l < M; ++l) {
                for (int h = l + 1; h <= M; ++h) {
                    Map<Integer, Integer> map = new HashMap<>();
                    for (int j = 0; j <= N; ++j) {
                        int diff = sum[h][j] - sum[l][j];
                        if (map.containsKey(diff)) {
                            int k = map.get(diff);
                            result[0][0] = l;
                            result[0][1] = k;
                            /**
                             * operate on sum, which is padded, so based on
                             * sum[j + 1] - sum[i] is the subarray sum between [i, j],
                             * higher end should minus 1.
                             */
                            result[1][0] = h - 1;
                            result[1][1] = j - 1;
                            return result;
                        } else {
                            map.put(diff, j);
                        }
                    }
                }
            }
            return result;
        }
    }

    class Solution2 {
        public int[][] submatrixSum(int[][] M) {
            int n = M.length, m = M[0].length;

            int[][] sum = new int[n + 1][m];
            int[][] ans = new int[2][2];

            for (int i = 1; i <= n; i++) {

                for (int j = 0; j < m; j++) {
                    sum[i][j] = sum[i - 1][j] + M[i - 1][j];
                }

                for (int k = 0; k < i; k++) {
                    int t = 0;
                    HashMap<Integer, Integer> map = new HashMap<>();
                    map.put(0, -1);
                    for (int j = 0; j < m; j++) {
                        t += (sum[i][j] - sum[k][j]);
                        if (!map.containsKey(t)) {
                            map.put(t, j);
                            continue;
                        }
                        ans[0][0] = k;
                        ans[0][1] = map.get(t) + 1;
                        ans[1][0] = i - 1;
                        ans[0][1] = j;
                        return ans;
                    }
                }
            }
            return ans;
        }
    }

    class ResultType {
        boolean flag = false;
        List<Integer> indices;
        public ResultType (boolean F, List<Integer> idx){
            this.flag = F;
            this.indices = idx;
        }
    }
    public class Solution3 {
        public int[][] submatrixSum(int[][] matrix) {
            // write your code here
            //预处理列向量和
            int m = matrix.length;
            int n = matrix[0].length;
            int[][] col_sum = new int[m + 1][n];

            for (int i = 1; i <= m; i++){
                for (int j = 0; j < n; j++){
                    col_sum[i][j] = col_sum[i - 1][j] + matrix[i - 1][j];
                }
            }

            int[] prefix = new int[n];
            //枚举起始行和终止行
            for (int start = 0; start < m; start++){
                for (int end = start; end < m; end++){
                    //求start -> end这一段 所有列向量和
                    for (int k = 0; k < n; k++){
                        prefix[k] = col_sum[end + 1][k] - col_sum[start][k];
                    }
                    //丢给subarray sum处理求index
                    ResultType res = subarraySum(prefix);
                    if (res.flag){
                        int[][] soln = {{start, res.indices.get(0)},{end, res.indices.get(1)}};
                        return soln;
                    }
                }
            }
            int[][] soln = new int[2][2];
            return soln;
        }

        //subarray sum那道题抄过来的
        public ResultType subarraySum(int[] nums) {
            List<Integer> ans = new ArrayList<>();
            int sum = 0;
            HashMap<Integer, Integer> map = new HashMap <> ();
            map.put(0, 0);

            for (int i = 0; i < nums.length; i++){
                sum += nums[i];
                if (map.containsKey(sum)){
                    ans.add(map.get(sum));
                    ans.add(i);
                    return new ResultType(true, ans);
                }
                map.put(sum, i + 1);
            }

            return new ResultType(false, ans);
        }
    }

}
