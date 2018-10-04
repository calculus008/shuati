package lintcode;

/**
 * Created by yuank on 10/2/18.
 */
public class LI_404_Subarray_Sum_II {
    /**
         Given an integer array, find a subarray where the sum of numbers is in a given interval.
         Your code should return the number of possible answers.
         (The element in the array should be positive)!!!

         Example
         Given [1,2,3,4] and interval = [1,3], return 4. The possible answers are:

         [0, 0]
         [0, 1]
         [1, 1]
         [2, 2]

         Medium
     */

    /**
     * Solution 1
     * O(n)的时间复杂度 (Prefix & 两个指针), 利用prefixSum数组形成递增序列
     * ("The element in the array should be positive"),
     * 用两个指针的方法计算任意两个数之差在[start, end]范围。
     */
    public int subarraySumII_1(int[] A, int start, int end) {
        if (A == null || A.length == 0 || start > end) {
            return 0;
        } else if (A.length == 1) {
            if (A[0] >= start && A[0] <= end) {
                return 1;
            }
            return 0;
        }

        int[] prefixSum = new int[A.length + 1];
        prefixSum[0] = 0;

        /**
         * 由于“The element in the array should be positive”，
         * 处理后的prefixSum[]是递增的，也就是sorted.
         */
        for (int i = 1; i < prefixSum.length; i++) {
            prefixSum[i] = prefixSum[i - 1] + A[i - 1];
        }

        /**
         * Get the number of subarrays s1 whose sum is bigger than start - 1,
         * get the number of subarrays s2 whose sum is bigger than end,
         * then s1 - s2 is the number of subarrays whose sum is in [start, end]
         */
        return getLarger(prefixSum, start - 1) - getLarger(prefixSum, end);
    }

    /**
     * In sources[], for index i and j (i < j), find number of pairs of i and j
     * that sources[j] - sources[i] > target.
     *
     * Or in other words, find number of subarrays whose sum is bigger than target.
     *
     * We can use two pointers or binary search on sources[] because it is sorted
     * by definition.
     */
    private int getLarger(int[] sources, int target) {
        int left = 0, right = 1;
        int count = 0;

        while (right < sources.length) {
            if (left == right) {
                right++;
            } else if (sources[right] - sources[left] <= target) {
                right++;
            } else {
                count += sources.length - right;
                left++;
            }
        }

        return count;
    }

    /**
     * Solution 2
     *
     * Difference from Solution 1 is to use binary search in find().
     *
     */
    public int subarraySumII_2(int[] A, int start, int end) {
        int len = A.length;
        for (int i = 1; i < len; ++i)
            A[i] += A[i - 1];

        int cnt = 0;
        for (int i = 0; i < len; ++i) {
            //case for a valid subarray starts at index 0
            if (A[i] >= start && A[i] <= end) {
                cnt++;
            }

            int l = A[i] - end;
            int r = A[i] - start;
            cnt += find(A, i, r + 1) - find(A, i, l);
        }
        return cnt;
    }

    int find(int[] A, int len, int value) {
        if (len - 1 >= 0 && A[len - 1] < value)
            return len;

        int l = 0, r = len - 1, ans = 0;
        while (l <= r) {
            int mid = (l + r) / 2;
            if (value <= A[mid]) {
                ans = mid;
                r = mid - 1;
            } else
                l = mid + 1;
        }
        return ans;
    }
}
