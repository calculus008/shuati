package leetcode;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by yuank on 5/13/18.
 */
public class LE_324_Wiggle_Sort_II {
    /**
     * Given an unsorted array nums, reorder it such that nums[0] < nums[1] > nums[2] < nums[3]....

         Example:
         (1) Given nums = [1, 5, 1, 1, 6, 4], one possible answer is [1, 4, 1, 5, 1, 6].
         (2) Given nums = [1, 3, 2, 2, 3, 1], one possible answer is [2, 3, 1, 3, 1, 2].

         Note:
         You may assume all input has valid answer.

         Follow Up:
         Can you do it in O(n) time and/or in-place with O(1) extra space?

         Medium
     */

    /**
         Solution 1 : sorting
         Time :  O(nlogn)
         Space : O(n)

         Given  [1, 5, 1, 1, 6, 4]

         After sort : [1, 1, 1, 4, 5, 6]
                   col 0  1  2  3  4  5

         mid = (6 - 1) / 2 = 2

         i = 0
             temp[0] = nums[2 - 0] = 1
             temp[1] = nums[5 - 0] = 6
             temp [1, 6, 0, 0, 0, 0]
             col   0  1  2  3  4  5

         i = 1
             temp[2] = nums[2 - 1] = 1
             temp[3] = nums[5 - 1] = 5
             temp [1, 6, 1, 5, 0, 0]
             col   0  1  2  3  4  5

         i = 2
             temp[4] = nums[2 - 2] = 1
             temp[5] = nums[5 - 2] = 4
             temp [1, 6, 1, 5, 1, 4]
             col   0  1  2  3  4  5
     **/

    public void wiggleSort1(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        int mid = (n-1)/2;
        int idx = 0;
        int[] temp = new int[n];

        //!!! "<= mid"
        for (int i = 0; i <= mid ; i++) {
            temp[idx] = nums[mid - i];
            if (idx + 1 < n) {//!!! if the length of nums is odd, col+1 could go beyond boundary, need to check here
                /**
                    (n-1)-i : start from end of the sorted array (the largest), move left

                    col starts with 0 (even), each time increase 2, so it always points to even index. col + 1 points to odd index.

                    place 2 elements adjacent to each other in each iteration, garauntee first one is smaller than the 2nd one.

                    In next iteration, move left from mid value, guarantee the small one in this round is smaller than the large one from the last round (adjacent to its left).
                **/
                temp[idx + 1] = nums[(n - 1) - i ];
            }

            /**
             * !!!一次处理两个元素，步长为2
             */
            idx += 2;
        }

        System.arraycopy(temp, 0, nums, 0, n);
    }


    /**
         Solution 2
         Time  : O(n)
         Space : O(n)

         https://blog.csdn.net/yc461515457/article/details/52424459

         1.这个题目看似简单，实际上是比较难的。
         一开始的想法是找到数组的中位数，然后根据中位数对数组划分为两部分。大的那一部分放在索引为奇数的位置(1，3，5，7，9…)，
         小的那一部分放在索引为偶数的位置(0，2，4，6，8…)。（这里是可以随意放的）

         这个想法是对的，但还不够。比如当数组为[4,5,5,6]时，得到的结果就是[4,5,5,6]，这不符合题目要求，正确的结果应该是[5,6,4,5]。
         这里问题出在两部分数中相同大小的数放在一起了。

         为了避免这个问题，直观上来说，需要把较小的那一部分中比较大的数和较大的那一部分中比较小的数放的越远越好。
         (要做到这样有点难，因为这样需要两部分的数都有序，实际上一步划分之后两部分的数是无序的)

         当然还一个简单的方法，那就是确保和中位数相等的数间隔放置的。
         这里用到了three-way partitioning算法。

         2.We can use a one-pass three-way partition to rearrange all elements.
         The idea is to re-dist the indices into its destined indices, odd indices first and even indices follow.

         Example:

         Original Indices:    0  1  2  3  4  5  6  7  8  9 10 11
         Mapped Indices:      1  3  5  7  9 11  0  2  4  6  8 10
         (its reverse mapping is)

         Mapped Indices:      0  1  2  3  4  5  6  7  8  9 10 11
         Original Indices:    6  0  7  1  8  2  9  3 10  4 11  5   (wiggled)
         In order to achieve this, we can use a function alike

         int map_index(int col, int n) {
            return (2 * col + 1) % (n | 1);
         }
         where (n | 1) calculates the nearest odd that is not less than n.!!!


         3.The whole "virtual indexing" is the effort to achive O(1) for space.
           So, if space is O(n), we first run 3 way partition

          https://leetcode.com/problems/wiggle-sort-ii/discuss/77682/Step-by-step-explanation-of-index-mapping-in-Java

         (1) elements smaller than the 'median' are put into the last even slots

         (2) elements larger than the 'median' are put into the first odd slots

         (3) the medians are put into the remaining slots.

         Mapped :      1   3   5   0   2   4
         Index :       0   1   2   3   4   5
         Small half:   M       S       S
         Large half:       L       L       M

     **/

    public void wiggleSort2(int[] nums) {
        int median = findKthLargest(nums, (nums.length + 1) / 2);
        int n = nums.length;

        int left = 0, i = 0, right = n - 1;

        while (i <= right) {
            if (nums[newIndex(i,n)] > median) {
                swap(nums, newIndex(left++,n), newIndex(i++,n));
            }
            else if (nums[newIndex(i,n)] < median) {
                swap(nums, newIndex(right--,n), newIndex(i,n));
            }
            else {
                i++;
            }
        }
    }

//    public void wiggleSort(int[] nums) {
//        if (nums == null || nums.length == 0)   return;
//        int len = nums.length;
//        int median = findMedian(0, len-1, len/2, nums);
//        int left = 0, right = len-1, i = 0;
//        // dist current index firstly
//        while (i <= right) {
//            int mappedCurIndex = newIndex(i, len);
//            if (nums[mappedCurIndex] > median) {
//                int mappedLeftIndex = newIndex(left, len);
//                swap(mappedLeftIndex, mappedCurIndex, nums);
//                left++; i++;
//            } else if (nums[mappedCurIndex] < median) {
//                int mappedRightIndex = newIndex(right, len);
//                swap(mappedCurIndex, mappedRightIndex, nums);
//                right--;
//            } else {
//                i++;
//            }
//        }
//    }

    //!!! calculates the nearest odd that is not less than n
    private int newIndex(int index, int n) {
        return (1 + 2*index) % (n | 1);
    }

    private void swap(int[] nums, int idx1, int idx2) {
        int temp = nums[idx1];
        nums[idx1] = nums[idx2];
        nums[idx2] = temp;
    }

    /**
        Copy from LE_215 : https://leetcode.com/problems/kth-largest-element-in-an-array/discuss/60294/Solution-explained

        After findKthLargest(), numbers bigger than median are on the right, smaller numbers on the left.
     **/
    private int findKthLargest(int[] nums, int k) {
        shuffle(nums);
        k = nums.length - k;
        int lo = 0;
        int hi = nums.length - 1;
        while (lo < hi) {
            final int j = partition(nums, lo, hi);
            if(j < k) {
                lo = j + 1;
            } else if (j > k) {
                hi = j - 1;
            } else {
                break;
            }
        }
        return nums[k];
    }

    private int partition(int[] a, int lo, int hi) {

        int i = lo;
        int j = hi + 1;
        while(true) {
            while(i < hi && less(a[++i], a[lo]));
            while(j > lo && less(a[lo], a[--j]));
            if(i >= j) {
                break;
            }
            swap(a, i, j);
        }
        swap(a, lo, j);
        return j;
    }

    private boolean less(int v, int w) {
        return v < w;
    }

    private void shuffle(int a[]) {
        final Random random = new Random();
        for(int ind = 1; ind < a.length; ind++) {
            final int r = random.nextInt(ind + 1);
            swap(a, ind, r);
        }
    }


}
