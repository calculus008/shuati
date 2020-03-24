package leetcode;

public class LE_978_Longest_Turbulent_Subarray {
    /**
     * A subarray A[i], A[i+1], ..., A[j] of A is said to be turbulent if and only if:
     *
     * For i <= k < j, A[k] > A[k+1] when k is odd, and A[k] < A[k+1] when k is even;
     * OR, for i <= k < j, A[k] > A[k+1] when k is even, and A[k] < A[k+1] when k is odd.
     * That is, the subarray is turbulent if the comparison sign flips between each adjacent pair of elements in the subarray.
     *
     * Return the length of a maximum size turbulent subarray of A.
     *
     *
     *
     * Example 1:
     * Input: [9,4,2,10,7,8,8,1,9]
     * Output: 5
     * Explanation: (A[1] > A[2] < A[3] > A[4] < A[5])
     *
     * Example 2:
     * Input: [4,8,12,16]
     * Output: 2
     *
     * Example 3:
     * Input: [100]
     * Output: 1
     *
     *
     * Note:
     * 1 <= A.length <= 40000
     * 0 <= A[i] <= 10^9
     *
     * Medium
     */

    /**
     * Evidently, we only care about the comparisons between adjacent elements.
     * If the comparisons are represented by -1, 0, 1 (for <, =, >), then we want
     * the longest sequence of alternating 1, -1, 1, -1, ... (starting with either 1 or -1).
     *
     * These alternating comparisons form contiguous blocks. We know when the next block ends:
     * when it is the last two elements being compared, or when the sequence isn't alternating.
     *
     * For example, take an array like A = [9,4,2,10,7,8,8,1,9]. The comparisons are [1,1,-1,1,-1,0,-1,1].
     * The blocks are [1], [1,-1,1,-1], [0], [-1,1].
     *
     * Scan the array from left to right. If we are at the end of a block (last elements OR it stopped
     * alternating), then we should record the length of that block as our candidate answer, and set
     * the start of the new block as the next element.
     */
    public int maxTurbulenceSize(int[] A) {
        if (A == null || A.length == 0) return 0;

        /**
         * !!!
         * For corner case:
         * If A has only 1 element, we return 1;
         */
        int res = 1;
        int start = 0;
        int n = A.length;

        for (int i = 1; i < n; i++) {
            int c = Integer.compare(A[i - 1], A[i]);

            if (c == 0) {
                start = i;
            } else if (i == n - 1 || Integer.compare(A[i], A[i + 1]) * c != -1 ) {
                res = Math.max(res, i - start + 1);
                start = i;
            }
        }

        return res;
    }
}
