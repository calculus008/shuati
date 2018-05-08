package leetcode;

/**
 * Created by yuank on 5/7/18.
 */
public class LE_321_Create_Maximum_Number {
    /**
         Given two arrays of length m and n with digits 0-9 representing two numbers.
         Create the maximum number of length k <= m + n from digits of the two.
         The relative order of the digits from the same array must be preserved. Return an array of the k digits.
         You should try to optimize your time and space complexity.

         Example 1:
         nums1 = [3, 4, 6, 5]
         nums2 = [9, 1, 2, 5, 8, 3]
         k = 5
         return [9, 8, 6, 5, 3]

         Example 2:
         nums1 = [6, 7]
         nums2 = [6, 0, 4]
         k = 5
         return [6, 7, 6, 0, 4]

         Example 3:
         nums1 = [3, 9]
         nums2 = [8, 9]
         k = 3
         return [9, 8, 9]

         Hard
     */

    /**
         Greedy
         http://zxi.mytechroad.com/blog/dynamic-programming/leetcode-321-create-maximum-number/
         https://www.youtube.com/watch?v=Vr1KZyzb_LQ&index=19&list=PLvyIyKZVcfAn07kMfBND1i7OSXj50QYTY
         https://segmentfault.com/a/1190000007655603

         我们可以分三步得到正确结果：
         1：从nums1里取i个元素组成最大数组，从nums2里取k-i个元素组成最大数组。
         2：合并之前结果，得到一个长度为k的最大数组。
         3：对于不同长度分配的情况，比较每次得到的长度为k的最大数组，返回最大的一个

         Time  : O((m + n) ^ 3) (worst case) (?)
         Space : O(k)
     **/
    public int[] maxNumber(int[] nums1, int[] nums2, int k) {
        int m = nums1.length;
        int n = nums2.length;

        /**
         "i = Math.max(0, k - n); i <= k && i <= m"

         (k - n) : based on the length of nums2, at least how many number we should get from nums1. Example :
         nums1 [2,4,6,5]
         nums2 [4,5,6,3,2,7]
         k = 5

         m = 4, n = 6
         k - n = 5 - 6 = -1, therefore i starts from 0

         nums1 [4,5,6,3,2,7]
         nums2 [2,4,6,5]
         k = 5

         m = 6, n = 4
         k - n = 5 - 4 = 1, therefore i starts from 1, which means we need to get at least 1 number from nums1
         **/

        int[] res = new int[k];
        for (int i = Math.max(0, k - n); i <= k && i <= m; i++) {
            int[] temp = merge(maxValue(nums1, i), maxValue(nums2, k - i), k);
            if (greater(temp, 0, res, 0)) {
                res = temp;
            }
        }

        return res;
    }

    // O(n)
    private int[] maxValue(int[] nums, int k) {
        int n = nums.length;
        int[] res = new int[k];
        for (int i = 0, j = 0; i < n; i++) {
            /**  !!!
                 "n - i > k - j" :  如果有大的数字在结尾出现，不能在往回更新， 比如：

                 4 3 6 2 5 7

                 * * *          n i k j
                 4              6 0 3 0
                 4 3            6 1 3 1
                 6 3            6 2 3 0 这里4和3应该已经都pop out了
                 6 2            6 3 3 1
                 6 5            6 4 3 1
                 6 5 7          6 5 3 2  !!! 这里7比6和5都大，但是， 此时n-i==k-j, 所以跳过while循环(也就是不往回更新), 直接把7放在最后。
             **/
            while (n - i > k - j && j > 0 && res[j - 1] < nums[i]) {
                j--;
            }
            if (j < k) { //!!! "j < k"
                res[j++] = nums[i];
            }
        }

        return res;
    }

    //O((m + n) ^ 2)
    private int[] merge(int[] nums1, int[] nums2, int k) {
        int[] res = new int[k];
        for (int i = 0, j = 0, r = 0; r < k; r++) {
            res[r] = greater(nums1, i, nums2, j) ? nums1[i++] : nums2[j++];
        }

        return res;
    }

    private boolean greater(int[] nums1, int i, int[] nums2, int j) {
        int m = nums1.length;
        int n = nums2.length;

        while (i < m && j < n && nums1[i] == nums2[j]) {
            i++;
            j++;
        }

        return j == n || (i < m && nums1[i] > nums2[j]);
    }
}
