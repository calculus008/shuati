package Interviews.Apple;

//import com.sun.javaws.exceptions.InvalidArgumentException;

public class Kth_Smallest_In_Two_Sorted_Array {
    /**
     * Similar to LE_04_Median_Of_Two_Sorted_Arrays
     *
     * O(log(min(m, n)))
     */
    public class Solution_binary_search {
        public int findKthSmallest(int[] arr1, int[] arr2, int k) {
            int len1 = arr1.length;
            int len2 = arr2.length;

            // Ensure that we are always binary searching on the smaller array
            if (len1 > len2) {
                return findKthSmallest(arr2, arr1, k);
            }

            int low = Math.max(0, k - len2); // Minimum elements we can take from arr1
            int high = Math.min(k, len1);    // Maximum elements we can take from arr1

            while (low <= high) {
                int partition1 = (low + high) / 2;
                int partition2 = k - partition1;

                // Edge cases for partitioning at the start or end of the arrays
                int maxLeft1 = (partition1 == 0) ? Integer.MIN_VALUE : arr1[partition1 - 1];
                int minRight1 = (partition1 == len1) ? Integer.MAX_VALUE : arr1[partition1];

                int maxLeft2 = (partition2 == 0) ? Integer.MIN_VALUE : arr2[partition2 - 1];
                int minRight2 = (partition2 == len2) ? Integer.MAX_VALUE : arr2[partition2];

                // Check if we found the correct partition
                if (maxLeft1 <= minRight2 && maxLeft2 <= minRight1) {
                    return Math.max(maxLeft1, maxLeft2);
                } else if (maxLeft1 > minRight2) {
                    // Too many elements taken from arr1, move partition to the left
                    high = partition1 - 1;
                } else {
                    // Not enough elements taken from arr1, move partition to the right
                    low = partition1 + 1;
                }
            }

            throw new IllegalArgumentException("Input arrays are not valid.");
        }

//        public static void main(String[] args) {
//            KthSmallestInSortedArrays finder = new KthSmallestInSortedArrays();
//
//            int[] arr1 = {2, 3, 6, 7, 9};
//            int[] arr2 = {1, 4, 8, 10};
//            int k = 5;
//
//            System.out.println("The " + k + "-th smallest number is: " + finder.findKthSmallest(arr1, arr2, k));
//        }
    }



    class Solution_merge {
        /**
         * 两个排序数组，返回kth smallest, a variation of LE_21_Merge_Two_Sorted_Lists
         * <p>
         * Time  : O(l1 + l2)
         * Space : O(1)
         */
        public int getKthSmallest(int[] a1, int[] a2, int k) {
            if (k <= 0) throw new IllegalArgumentException();

            int l1 = a1.length;
            int l2 = a2.length;

            if (k > l1 + l2) throw new IllegalArgumentException();

            int p1 = 0, p2 = 0;

            int count = 0;
            int res = 0;

            /**
             * Same iteration logic as in LE_21_Merge_Two_Sorted_Lists, Solution2
             *
             * !!!
             * #1.
             * 只需要处理两种情况：
             * a1当前值 < a2当前值, a1当前值被采用，count++
             * otherwise (a1当前值 >= a2当前值), a2当前值被采用, count++
             */
            while (p1 < l1 && p2 < l2) {
                if (a1[p1] < a2[p2]) {
                    count++;
                    System.out.println(a1[p1] + ", count=" + count);

                    /**
                     * !!!
                     * #2.
                     * Must return here, different from LE_21_Merge_Two_Sorted_Lists,
                     * when we get the answer here, a1 and a2 may not go to the end,
                     * so the two "if" after the "while" loop won't be valid.
                     */
                    if (count == k) {
                        return a1[p1];
                    }

                    p1++;
                } else {
                    count++;
                    System.out.println(a2[p2] + ", count=" + count);

                    if (count == k) {
                        return a2[p2];
                    }

                    p2++;
                }
            }


            System.out.println("p1=" + p1 + ", p2=" + p2);

            if (p1 < l1) {
                /**
                 * !!!
                 * #3
                 * "p1 - 1": p1 already move to the next position, need to backoff one step.
                 */
                int idx = k - count + p1 - 1;
                res = a1[idx];
            }

            if (p2 < l2) {
                int idx = k - count + p2 - 1;
                res = a2[idx];
            }

            return res;
        }
    }

//    public static void main(String[] args) {
//        int[] a2 = {1, 1, 1, 7, 9, 10, 11, 12, 13};
//        int[] a1 = {2, 4, 4, 8};
////        int[] a1 = {};
//
//        System.out.println(getKthSmallest(a1, a2, 7));
//    }
}
