package lintcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yuank on 11/2/18.
 */
public class LI_403_Continuous_Subarray_Sum_II {
    /**
         Given an CIRCULAR integer array (the next element of the last element is the first element),
         find a continuous subarray in it, where the sum of numbers is the biggest.
         Your code should return the index of the first number and the index of the last number.

         If duplicate answers exist, return any of them.

         Example
         Give [3, 1, -100, -3, 4], return [4,1].

         Challenge
         Do it in O(n) time

         Medium
     */

    public class Solution1 {
        public List<Integer> continuousSubarraySumII(int[] A) {
            List<Integer> result = new ArrayList<Integer>();
            result.add(0);
            result.add(0);
            int total = 0;
            int len = A.length;
            int start = 0, end = 0;
            int local = 0;
            int global = -0x7fffffff;
            for (int i = 0; i < len; ++i) {
                total += A[i];
                if (local < 0) {
                    local = A[i];
                    start = end = i;
                } else {
                    local += A[i];
                    end = i;
                }
                if (local >= global) {
                    global = local;
                    result.set(0, start);
                    result.set(1, end);
                }
            }
            local = 0;
            start = 0;
            end = -1;
            for (int i = 0; i < len; ++i) {
                if (local > 0) {
                    local = A[i];
                    start = end = i;
                } else {
                    local += A[i];
                    end = i;
                }
                if (start == 0 && end == len - 1) continue;
                if (total - local >= global) {
                    global = total - local;
                    result.set(0, (end + 1) % len);
                    result.set(1, (start - 1 + len) % len);
                }
            }
            return result;
        }
    }

    public class Solution2 {
        public List<Integer> continuousSubarraySumII(int[] A) {
            int n = A.length;
            int sum = 0;
            for (int i = 0; i < n; i++){
                sum += A[i];
            }

            int[] ans1 = findMax(A, 1);
            int[] ans2 = findMax(A, -1);

            if (ans1[2] > sum + ans2[2] || ans2[1] - ans2[0] == n - 1){
                return Arrays.asList(ans1[0], ans1[1]);
            } else {
                return Arrays.asList((ans2[1] + 1) % n, (ans2[0] + n - 1) % n);
            }
        }

        int[] findMax(int[] A, int flag){
            int[] ans = new int[3];
            int sum = 0;
            int max = Integer.MIN_VALUE;
            int lo = 0;
            for (int i = 0; i < A.length; i++){
                sum += A[i] * flag;
                if (max < sum){
                    max = sum;
                    ans[0] = lo;
                    ans[1] = i;
                    ans[2] = sum;
                }
                if (sum < 0){
                    sum = 0;
                    lo = i + 1;
                }
            }
            return ans;
        }
    }
}
