package leetcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by yuank on 5/16/18.
 */
public class LE_327_Count_Of_Range_Sum {
    /**
         Given an integer array nums, return the number of range sums that lie in [lower, upper] inclusive.
         Range sum S(i, j) is defined as the sum of the elements in nums between indices i and j (i ≤ j), inclusive.

         Note:
         A naive algorithm of O(n2) is trivial. You MUST do better than that.

         Example:
         Given nums = [-2, 5, -1], lower = -2, upper = 2,
         Return 3.
         The three ranges are : [0, 0], [2, 2], [0, 2] and their respective sums are: -2, -1, 2.

         Hard
     */

    /**
     * Solution 1 : Use Binary Indexed Tree
     *
     * Time  : O(nlogn)
     * Space : O(n)
     *
     * https://leetcode.com/problems/count-of-range-sum/discuss/78026/An-O(n-log-n)-solution-via-Fenwick-Tree
     *
     *   At a high level, we would like a black-box data structure that supports the following operations:

         A[i] = 0 for any i initially
         void plus(int i, int delta): A[i] += delta
         int query(int i): return sum(A[i] + A[i - 1] + A[i - 2] + ...)
         To use this black-box to solve the problem, we can use the following pseudocode.

         for (int i = 0; i < sum.length; i++) plus(sum[i], 1)
         int ans = 0;
         for (int i = 1; i < sum.length; i++) {
             plus(sum[i - 1]), -1);
             ans += query(upper + sum[i - 1]);
             ans -= query(lower + sum[i - 1] - 1);
         }

         Fenwick Tree is easy to implement and provides O(log N)-time guarantee for both operations, where N is the largest index in the tree. However,
         it can only handle positive indices, and total storage occupied is proportional to N as well.

         Consider all possible indices we need for this problem: sum[i], lower + sum[i - 1] - 1, and upper + sum[i - 1].
         They can be either negative or very large and thus cannot be directly used for a Fenwick Tree.
         One can solve this problem by doing the so-called discretization, i.e., map these 3n indices to positive numbers in [1, 3n]
         while still preserving their relative order. Therefore, the size of the Fenwick Tree is bounded by 3n, and each operation takes only O(log n).

         For instance, assume the indices before discretization are [5, -3, 8, 300, 10]. After the mapping, they become [2, 1, 3, 5, 4].
         Now, they are good to go for a linear size Fenwick Tree.

         An easy way to do the discretization can be like this.

         Collect all the involved indices in an ArrayList.
         Sort the ArrayList.
         Map each number x to the index of x in the sorted ArrayList. This step can be done efficiently, in O(log n) time, per number by binary search.
     */


    public int countRangeSum1(int[] nums, int lower, int upper) {
        int n = nums.length;
        long[] sums = new long[n + 1];
        List<Long> list = new ArrayList<>();

        //!!!make sure index 0 is not used by any number
        list.add(Long.MIN_VALUE);
        list.add(0L);

        for (int i = 1; i < sums.length; i++) {
            sums[i] = sums[i - 1] + nums[i - 1];
            list.add(sums[i]);
            /**
             * lower ≤ sum[j] - sum[i - 1] ≤ upper, i.e., lower + sum[i - 1] ≤ sum[j] ≤ upper + sum[i - 1], for all i ≤ j ≤ n.
             */
            list.add(sums[i - 1] + lower - 1);
            list.add(sums[i - 1] + upper);
        }
        Collections.sort(list);//Done discretization

        // BinaryIndexedTree bit = new BinaryIndexedTree(list.size() - 1);
        int[] bit = new int[list.size()];
        for (int i = 0; i < sums.length; i++) {//!!! "int i = 0", starts from index 0!!!
            //!!! "Collections.binarySearch" returns the index of the target value
            // bit.update(Collections.binarySearch(list, sums[i]), 1);

            /**
             * binarySearch return :
             * the index of the search key, if it is contained in the list; otherwise, (-(insertion point) - 1).
             * The insertion point is defined as the point at which the key would be inserted into the list: the index of the
             * first element greater than the key, or list.size() if all elements in the list are less than the specified key.
             * Note that this guarantees that the return value will be >= 0 if and only if the key is found.
             */

            update(bit, Collections.binarySearch(list, sums[i]), 1);
        }

        int res = 0;
        for (int i = 1; i < sums.length; i++) {
            // bit.update(Collections.binarySearch(list, sums[i - 1]), -1);
            // res += bit.query(Collections.binarySearch(list, sums[i - 1] + upper));
            // res -= bit.query(Collections.binarySearch(list, sums[i - 1] + lower - 1));

            update(bit, Collections.binarySearch(list, sums[i - 1]), -1);//???

            res += query(bit, Collections.binarySearch(list, upper + sums[i - 1]));
            res -= query(bit, Collections.binarySearch(list, lower + sums[i - 1] - 1));
        }

        return res;
    }

    private void update(int[] bit, int i, int delta) {
        for (; i < bit.length; i += i & -i) {
            bit[i] += delta;
        }
    }

    private int query(int[] bit, int i) {
        int sum = 0;
        for (; i > 0; i -= i & -i) {
            sum += bit[i];
        }
        return sum;
    }


    /**
     * Solution 2 : Merge Sort
         https://leetcode.com/problems/count-of-range-sum/discuss/77990/Share-my-solution

         The merge sort based solution counts the answer while doing the merge. During the merge stage, we have already sorted the left half [start, mid)
         and right half [mid, end). We then iterate through the left half with index i. For each i, we need to find two indices k and j in the right half
         where :

             j is the first index satisfy sums[j] - sums[i] > upper and
             k is the first index satisfy sums[k] - sums[i] >= lower.

         Then the number of sums in [lower, upper] is j-k.

         We also use another index t to copy the elements satisfy sums[t] < sums[i] to a cache in order
         to complete the merge sort.

         Despite the nested loops, the time complexity of the "merge & count" stage is still linear. Because the indices k, j, t will only increase
         but not decrease, each of them will only traversal the right half once at most. The total time complexity of this divide and conquer
         solution is then O(n log n).

         One other concern is that the sums may overflow integer. So we use long instead.
     *
     */
    public int countRangeSum2(int[] nums, int lower, int upper) {
        int n = nums.length;
        long[] sums = new long[n + 1];
        for (int i = 0; i < n; ++i)
            sums[i + 1] = sums[i] + nums[i];
        return countWhileMergeSort(sums, 0, n + 1, lower, upper);
    }

    private int countWhileMergeSort(long[] sums, int start, int end, int lower, int upper) {
        if (end - start <= 1) return 0;
        int mid = (start + end) / 2;

        int count = countWhileMergeSort(sums, start, mid, lower, upper)
                + countWhileMergeSort(sums, mid, end, lower, upper);

        int j = mid, k = mid, t = mid;
        long[] cache = new long[end - start];

        for (int i = start, r = 0; i < mid; ++i, ++r) {
            // k is the first index satisfy sums[k] - sums[i] >= lower.
            while (k < end && sums[k] - sums[i] < lower) {
                k++;
            }

            //j is the first index satisfy sums[j] - sums[i] > upper and
            while (j < end && sums[j] - sums[i] <= upper) {
                j++;
            }

            while (t < end && sums[t] < sums[i]) {
                cache[r++] = sums[t++];
            }
            cache[r] = sums[i];

            count += j - k;
        }
        System.arraycopy(cache, 0, sums, start, t - start);
        return count;
    }

}
