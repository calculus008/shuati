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
    public static int subarraySumII_1(int[] A, int start, int end) {
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
     *
     * Example : [1,2,3,4] and interval = [1,3]
     * prefixSum passed in : [0, 1, 3, 6, 10], target = 1 - 1 = 0 :
     * left = 0, right = 1, count = 0 + 5 - 1 = 4, left++
     * left = 1, right = 1, right++
     * left = 1, right = 2, count = 4 + 5 - 2 = 7, left++
     * left = 2, right = 2, right++
     * left = 2, right = 3, count = 7 + 5 - 3 = 9, left++
     * left = 3, right = 3, right++
     * left = 3, right = 4, count = 9 + 5 - 4 = 10, left++
     * left = 4, right = 4,,right++
     * left = 4, right = 5 == prefixSum.length, stop
     * return count = 10
     *
     * prefixSum passed in : [0, 1, 3, 6, 10], target = 3
     * left = 0, right = 1, prefixSum[1] - prefixSum[0] = 1 - 0 = 1 <= 3, right++
     * left = 0, right = 2, prefixSum[2] - prefixSum[0] = 3 - 0 = 3 == 3, right++
     * left = 0, right = 3, prefixSum[3] - prefixSum[0] = 6 - 0 = 6 > 3,  count = 0 + 5 - 3 = 2, left++
     * left = 1, right = 3, prefixSum[3] - prefixSum[1] = 6 - 1 = 5 > 3,  count = 2 + 5 - 3 = 4, left++
     * left = 2, right = 3, prefixSum[3] - prefixSum[2] = 6 - 3 = 3 == 3, right++
     * left = 2, right = 4, prefixSum[4] - prefixSum[2] = 10 - 3 = 7 > 3, count = 4 + 5 - 4 = 5, left++
     * left = 3, right = 4, prefixSum[4] - prefixSum[3] = 10 - 6 = 4 > 3, count = 5 + 5 - 4 = 6, left++
     * left = 4, right = 4, right++, right = 5,exit while loop
     * return count = 6
     *
     * In essence, this function is to find the number of subarrays that the difference between left and right elements
     * are bigger than target in a none-decrease array.
     *
     * Use two pointers.
     */
    private static int getLarger(int[] sources, int target) {
        int left = 0, right = 1;//!!!
        int count = 0;

        while (right < sources.length) {//!!! "right < sources.length"
            if (left == right) {
                /**
                 * make sure left and right at least one step apart
                 */
                right++;
            } else if (sources[right] - sources[left] <= target) {
                /**
                 * Difference of the first and the last equals or is small then target,
                 * need to keep adding more, move right by one.
                 */
                right++;
            } else {
                /**
                 * Difference of the first and the last is bigger than target,
                 * so no need to go further, since array is none-decrease, the subarrays
                 * after current index of "right" will also be bigger than target.
                 *
                 * !!! "sources.length - right"
                 * In another form : the last index of sources - right + 1
                 *                   = (the last index of sources + 1) - right
                 *                   = sources.length - right
                 */
                count += sources.length - right; // !!!"- right"
                left++;
            }
        }

//        System.out.println("return count=" + count);
        return count;
    }

    /**
     * Solution 2
     *
     * Difference from Solution 1 is to use binary search in find().
     *
     * Time is also O(n), since we still need to first calculate prefixSun
     * Even using Binary Search, the time for search is nlogn since it is done in a for loop
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

    public static void main(String [] args) {
        subarraySumII_1(new int[] {1, 2, 3, 4}, 1, 3);
    }
}
