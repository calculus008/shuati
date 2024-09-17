package leetcode;

public class LE_769_Max_Chunks_To_Make_Sorted {
    /**
     * Given an array arr that is a permutation of [0, 1, ..., arr.length - 1],
     * we split the array into some number of "chunks" (partitions), and individually
     * sort each chunk.  After concatenating them, the result equals the sorted array.
     *
     * What is the most number of chunks we could have made?
     *
     * Example 1:
     * Input: arr = [4,3,2,1,0]
     * Output: 1
     * Explanation:
     * Splitting into two or more chunks will not return the required result.
     * For example, splitting into [4, 3], [2, 1, 0] will result in [3, 4, 0, 1, 2], which isn't sorted.
     *
     * Example 2:
     * Input: arr = [1,0,2,3,4]
     * Output: 4
     * Explanation:
     * We can split into two chunks, such as [1, 0], [2, 3, 4].
     * However, splitting into [1, 0], [2], [3], [4] is the highest number of chunks possible.
     *
     * Note:
     * arr will have length in range [1, 10].
     * arr[i] will be a permutation of [0, 1, ..., arr.length - 1].
     *
     * Medium
     *
     * https://leetcode.com/problems/max-chunks-to-make-sorted
     */

    /**
     * Greedy
     * Time  : O(n)
     * Space : O(1)
     *
     * https://zxi.mytechroad.com/blog/difficulty/medium/leetcode-769-max-chunks-to-make-sorted/
     *
     * 因为数组arr 的元素在区间[0,n−1] 之间且互不相同，所以数组排序后有arr[i]=i。如果数组arr 的某个长为i+1 的前缀块[a0,ai] 的最大值等于i，
     * 那么说明它排序后与原数组排序后的结果一致(!!!)。统计这些前缀块的数目，就可以得到最大分割块数目。
     */
    public int maxChunksToSorted(int[] arr) {
        int max = 0;
        int res = 0;

        for (int i = 0; i < arr.length; i++) {
            max = Math.max(max, arr[i]);
            if (max == i) {
                res++;
            }
        }

        return res;
    }
}
