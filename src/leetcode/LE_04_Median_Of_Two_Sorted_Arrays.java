package leetcode;

/**
 * Created by yuank on 8/29/18.
 */
public class LE_04_Median_Of_Two_Sorted_Arrays {
    /**
         There are two sorted arrays nums1 and nums2 of size m and n respectively.

         Find the median of the two sorted arrays. The overall run time complexity should be O(log (m+n)).

         You may assume nums1 and nums2 cannot be both empty.

         Example 1:

         nums1 = [1, 3]
         nums2 = [2]

         The median is 2.0
         Example 2:

         nums1 = [1, 2]
         nums2 = [3, 4]

         The median is (2 + 3)/2 = 2.5
     */

    class Solution_partition_clean {
        /**
         * https://www.youtube.com/watch?v=LPFhl65R7ww
         * https://github.com/mission-peace/interview/blob/master/src/com/interview/binarysearch/MedianOfTwoSortedArrayOfDifferentLength.java
         */
        public double findMedianSortedArrays(int[] nums1, int[] nums2) {
            int l1 = nums1.length;
            int l2 = nums2.length;
            if (l1 > l2) {
                return findMedianSortedArrays(nums2, nums1);
            }

            int l = 0;
            int r = l1;

            while (l <= r) {
                int mid1 = l + (r - l) / 2;
                int mid2 = (l1 + l2 + 1) / 2 - mid1;

                int maxLeft1 = mid1 == 0 ? Integer.MIN_VALUE : nums1[mid1 - 1];
                int minRight1 = mid1 == l1 ? Integer.MAX_VALUE : nums1[mid1];

                int maxLeft2 = mid2 == 0 ? Integer.MIN_VALUE : nums2[mid2 - 1];
                int minRight2 = mid2 == l2 ? Integer.MAX_VALUE : nums2[mid2];

                if (maxLeft1 <= minRight2 && maxLeft2 <= minRight1) {
                    if ((l1 + l2) % 2 == 0) {
                        return (double)((Math.max(maxLeft1, maxLeft2) + Math.min(minRight1, minRight2)) / 2.0);
                    } else {
                        return (double) Math.max(maxLeft1, maxLeft2);
                    }
                } else if (maxLeft1 > minRight2) {
                    r = mid1 - 1;
                } else {
                    l = mid1 + 1;
                }
            }

            throw new IllegalArgumentException();
        }
    }
    class Solution_clean__huahua {
        public double findMedianSortedArrays(int[] nums1, int[] nums2) {
            if (nums1 == null && nums2 == null) return 0.0;

            int l1 = nums1.length;
            int l2 = nums2.length;

            if (l1 > l2) {
                return findMedianSortedArrays(nums2, nums1);
            }

            int k = (l1 + l2 + 1) / 2;

            int l = 0;
            int r = l1;
            while (l < r) {
                int m = l + (r - l) / 2;
                if (nums1[m] < nums2[(k - m) - 1]) {
                    l = m + 1;
                } else {
                    r = m;
                }
            }

            int m1 = l;
            int m2 = k - m1;

            int c1 = Math.max (m1 <= 0 ? Integer.MIN_VALUE : nums1[m1 - 1],
                    m2 <= 0 ? Integer.MIN_VALUE : nums2[m2 - 1]);

            if (((l1 + l2) % 2) == 1) {
                return c1;
            }

            int c2 = Math.min(m1 >= l1 ? Integer.MAX_VALUE : nums1[m1],
                    m2 >= l2 ? Integer.MAX_VALUE : nums2[m2]);

            return (c1 + c2) / 2.0;
        }
    }

    /**
     * Solution 4
     *
     * My preferred version
     *
     * Huahua version :
     * https://zxi.mytechroad.com/blog/algorithms/binary-search/leetcode-4-median-of-two-sorted-arrays/
     *
     * Binary Search
     * Time  : O(log(min(n1,n2)))
     * Space : O(1)
     *
     */
    class Solution {
        public double findMedianSortedArrays(int[] nums1, int[] nums2) {
            if (nums1 == null && nums2 == null) return 0.0;

            int l1 = nums1.length;
            int l2 = nums2.length;

            if (l1 > l2) {
                return findMedianSortedArrays(nums2, nums1);
            }

            /**
             *  k是个数
             */
            int k = (l1 + l2 + 1) / 2;

            /**
             * !!!
             * 此处，二分搜素的对象是nums1[] 的index，所以，[l, r) 左闭右开，所以r不能是l1 - 1, 是l1.
             * 循坏条件是 l < r，但最后 l 可能等于 r， 表示第一个数组所有的元素都要使用。
             * 如果循环条件是(l <= r)，最后 l 可能大于 r，那就越界了。
             */
            int l = 0;
            int r = l1;
            while (l < r) {
                int m = l + (r - l) / 2;

                /**
                 *
                 * #1.Merged View :
                 *   k = (n1 + n2 + 1) / 2
                 *
                 *   if (n1 + n2) is even, median is (C[k - 1] + C[k]) / 2
                 *   C[0]..........., | C[k - 1], C[k], | .......C[n1 + n2 -1]
                 *
                 *   if (n1 + n2) is odd, median is C[k])
                 *   C[0]...........C[k - 1], | C[k], |   .......C[n1 + n2 -1]
                 *
                 *
                 * #2.Assume, we get m1 elements from nums1 and m2 elements from nums2
                 *    And : m1 + m2 = k
                 *
                 *   nums1 : A[0]......,A[m1 - 1],  | A[m1], ... <=== m1 (元素个数）is the value we do binary search on (m)
                 *   nums2 : B[0].......B[m2 - 1],  | B[m2], ...
                 *
                 *   Then, median must be from : A[m1 - 1], A[m1], B[m2 - 1], B[m2]
                 *
                 * #3.Binary Search for m1 so that :
                 *    A[m1] > B[m2 - 1]
                 *    A[m1 - 1] < B[m2]
                 *
                 * #4.If n1 + n2 is even :
                 *    Left median (C[k - 1]): max(A[m1 - 1], B[m2 - 1])
                 *    Right median (C[k]): min(A[m1], B[m2])
                 *
                 *    Median = (C[k - 1] + C[k]) / 2
                 *
                 * #5.Binary Search on m1, condition is nums1[m1] < nums2[m2 - 1]
                 *    so here m is m1, m2 = k - m1 = k - m, so m2 - 1 = (k - m) - 1
                 *
                 *    Therefore : nums1[m] < nums2[k - m
                 */
                if (nums1[m] < nums2[(k - m) - 1]) {
                    l = m + 1;
                } else {
                    r = m;
                }
            }


            /**
             * !!!
             */
            int m1 = l;
            int m2 = k - m1;

            /**!!!
             * It's "nums1" and "nums2", NOT "nums"
             *
             * 找左中位数 -> max(nums1[m1 - 1], nums2[m2 - 1]
             *
             * m1 <= 0 : meaning no number in nums1 is used
             */
            int c1 = Math.max (m1 <= 0 ? Integer.MIN_VALUE : nums1[m1 - 1],
                    m2 <= 0 ? Integer.MIN_VALUE : nums2[m2 - 1]);

            if (((l1 + l2) % 2) == 1) {
                return c1;
            }

            /**
             * !!!
             * 找右中位数 -> min(nums1[m1], nums2[m2]
             *
             * m1 >= l1 : use all number in nums1
             * m2 >= l2
             */
            int c2 = Math.min(m1 >= l1 ? Integer.MAX_VALUE : nums1[m1],
                    m2 >= l2 ? Integer.MAX_VALUE : nums2[m2]);

            return (c1 + c2) / 2.0;
        }
    }

    // https://discuss.leetcode.com/topic/16797/very-concise-o-log-min-m-n-iterative-solution-with-detailed-explanation?page=1

    // http://windliang.cc/2018/07/18/leetCode-4-Median-of-Two-Sorted-Arrays/

    /**
     *
     * Solution 1
     *
     * Time : O(log(min(n1, n2))
     *
     * Key Insights :
     *
     This problem is notoriously hard to implement due to all the corner cases. Most implementations consider odd-lengthed and
     even-lengthed arrays as two different cases and treat them separately. As a matter of fact, with a little mind twist.
     These two cases can be combined as one, leading to a very simple solution where (almost) no special treatment is needed.

     First, let's see the concept of 'MEDIAN' in a slightly unconventional way. That is:

     "if we cut the sorted array to two halves of EQUAL LENGTHS, then
     median is the AVERAGE OF Max(lower_half) and Min(upper_half), i.e. the
     two numbers immediately next to the cut".

     For example, for [2 3 5 7], we make the cut between 3 and 5:

        [2 3 / 5 7]

     then the median = (3+5)/2. Note that I'll use '/' to represent a cut, and (number / number) to represent a cut made through
     a number in this article.

     For [2 3 4 5 6], we make the cut right through 4 like this:

        [2 3 (4/4) 5 7]

     Since we split 4 into two halves, we say now both the lower and upper subarray contain 4.
     This notion also leads to the correct answer: (4 + 4) / 2 = 4;

     For convenience, let's use L to represent the number immediately left to the cut, and R the right counterpart.
     In [2 3 5 7], for instance, we have L = 3 and R = 5, respectively.

     We observe the index of L and R have the following relationship with the length of the array N:

         N        Index of L / R
         1               0 / 0
         2               0 / 1
         3               1 / 1
         4               1 / 2
         5               2 / 2
         6               2 / 3
         7               3 / 3
         8               3 / 4

     It is not hard to conclude that index of L = (N-1)/2, and R is at N/2. Thus, the median can be represented as

        (L + R)/2 = (A[(N-1)/2] + A[N/2])/2

     To get ready for the two array situation, let's add a few imaginary 'positions' (represented as #'s) in between numbers,
     and treat numbers as 'positions' as well.

         [6 9 13 18]  ->   [# 6 # 9 # 13 # 18 #]    (N = 4)
         position index     0 1 2 3 4 5  6 7  8     (N_Position = 9)

         [6 9 11 13 18]->   [# 6 # 9 # 11 # 13 # 18 #]   (N = 5)
         position index      0 1 2 3 4 5  6 7  8 9 10    (N_Position = 11)

     As you can see, there are always exactly 2*N+1 'positions' regardless of length N. Therefore,
     the middle cut should always be made on the Nth position (0-based). Since index(L) = (N-1)/2 and index(R) = N/2 in this situation,
     we can infer that index(L) = (CutPosition-1)/2, index(R) = (CutPosition)/2.

     Now for the two-array case:

         A1: [# 1 # 2 # 3 # 4 # 5 #]    (N1 = 5, N1_positions = 11)

         A2: [# 1 # 1 # 1 # 1 #]     (N2 = 4, N2_positions = 9)

     Similar to the one-array problem, we need to find a cut that divides the two arrays each into two halves such that

     "any number in the two left halves" <= "any number in the two right
     halves".

     We can also make the following observations：

     There are 2N1 + 2N2 + 2 position altogether. Therefore, there must be exactly N1 + N2 positions on each side of the cut,
     and 2 positions directly on the cut.

     Therefore, when we cut at position C2 = K in A2, then the cut position in A1 must be C1 = N1 + N2 - k.
     For instance, if C2 = 2, then we must have C1 = 4 + 5 - C2 = 7.

         [# 1 # 2 # 3 # (4/4) # 5 #]

         [# 1 / 1 # 1 # 1 #]

     When the cuts are made, we'd have two L's and two R's. They are

         L1 = A1[(C1-1)/2]; R1 = A1[C1/2];
         L2 = A2[(C2-1)/2]; R2 = A2[C2/2];

     In the above example,

         L1 = A1[(7-1)/2] = A1[3] = 4; R1 = A1[7/2] = A1[3] = 4;
         L2 = A2[(2-1)/2] = A2[0] = 1; R2 = A1[2/2] = A1[1] = 1;

     Now how do we decide if this cut is the cut we want? Because L1, L2 are the greatest numbers on the left halves and R1, R2 are
     the smallest numbers on the right, we only need

        L1 <= R1 && L1 <= R2 && L2 <= R1 && L2 <= R2

     to make sure that any number in lower halves <= any number in upper halves. As a matter of fact, since
     L1 <= R1 and L2 <= R2 are naturally guaranteed because A1 and A2 are sorted, we only need to make sure:

        L1 <= R2 and L2 <= R1.

     Now we can use simple binary search to find out the result.

     If we have L1 > R2, it means there are too many large numbers on the left half of A1,
     then we must move C1 to the left (i.e. move C2 to the right);

     If L2 > R1, then there are too many large numbers on the left half of A2, and we must move C2 to the left.
     Otherwise, this cut is the right one.

     After we find the cut, the medium can be computed as (max(L1, L2) + min(R1, R2)) / 2;

     Two side notes:

     A. Since C1 and C2 can be mutually determined from each other, we can just move one of them first,
        then calculate the other accordingly. However, it is much more practical to move C2 (the one on the shorter array) first.
        The reason is that on the shorter array, all positions are possible cut locations for median, but on the longer array,
        the positions that are too far left or right are simply impossible for a legitimate cut. For instance, [1], [2 3 4 5 6 7 8].
        Clearly the cut between 2 and 3 is impossible, because the shorter array does not have that many elements to
        balance out the [3 4 5 6 7 8] part if you make the cut this way. Therefore, for the longer array to be used as
        the basis for the first cut, a range check must be performed. It would be just easier to do it on the shorter array,
        which requires no checks whatsoever. Also, moving only on the shorter array gives a run-time complexity
        of O(log(min(N1, N2))) (edited as suggested by @baselRus)

     B. The only edge case is when a cut falls on the 0th(first) or the 2Nth(last) position.
        For instance, if C2 = 2N2, then R2 = A2[2*N2/2] = A2[N2], which exceeds the boundary of the array.
        To solve this problem, we can imagine that both A1 and A2 actually have two extra elements, INT_MIN at A[-1] and INT_MAX at A[N].
        These additions don't change the result, but make the implementation easier: If any L falls out of the left boundary of the array,
       then L = INT_MIN, and if any R falls out of the right boundary, then R = INT_MAX.
     *
     * **/
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int n1 = nums1.length;
        int n2 = nums2.length;

        //since m1+m2=n1+n2, we can only search in one of the arrays, so we take the shorter one, which makes O(long(min(n1,n2)))
        if(n1 > n2) return findMedianSortedArrays(nums2, nums1);

        //!!!unisveral formula for calculating median, regardless of length(odd or even)
        if(n1 == 0) return (double)(nums2[(n2-1)/2] + nums2[n2/2])/2;

        //do binary search in n1
        int low = 0;
        int high = 2*n1; //n1 is the one with smaller length, so 2 * n1 won't exceed the total length after two arrays combined

        while(low <= high) {
            /**
             * Note : m1, m2 are defined as positions, not the real index in the array.
             *   [6 9 13 18]  ->   [# 6 # 9 # 13 # 18 #]    (N = 4)
               position index       0 1 2 3 4 5  6 7  8     (N_Position = 9)
             */
            int m1 = low + (high-low)/2; //cut point for nums1
            int m2 = n1 + n2 - m1; //cut point for nums2

            //!!!use MIN and MAX to handle the case that left and right col falls out of the array boundary
            double l1 = m1 == 0 ? Integer.MIN_VALUE : nums1[(m1 - 1) / 2];
            double l2 = m2 == 0 ? Integer.MIN_VALUE : nums2[(m2 - 1) / 2];

            double r1 = m1 == n1 * 2 ? Integer.MAX_VALUE : nums1[m1 / 2];
            double r2 = m2 == n2 * 2 ? Integer.MAX_VALUE : nums2[m2 / 2];

            if(l1 > r2) { //means there are too many big elemts on the left side of nums1, we need to move to left in nums1
                high = m1 - 1;
            } else if(l2 > r1) {//means there are too many big elements on the left side of nums2, we need to move right in nums1
                low = m1 + 1;
            } else {
                return (Math.max(l1, l2) + Math.min(r1, r2))/2;
            }
        }

        return -1;
    }

    /**
     * Solution 2
     *
     * JiuZhang
     *'二分答案的方法，时间复杂度 O(log(range) * (log(n) + log(m)))
     * 其中 range 为最小和最大的整数之间的范围。可以拓展到 Median of K Sorted Arrays
     *
     */
    public double findMedianSortedArrays2(int[] A, int[] B) {
        int n = A.length + B.length;

        if (n % 2 == 0) {
            return (findKth(A, B, n / 2) + findKth(A, B, n / 2 + 1)) / 2.0;
        }

        return findKth(A, B, n / 2 + 1);
    }

    // k is not zero-based, it starts from 1
    public int findKth(int[] A, int[] B, int k) {
        if (A.length == 0) {
            return B[k - 1];
        }
        if (B.length == 0) {
            return A[k - 1];
        }

        int start = Math.min(A[0], B[0]);
        int end = Math.max(A[A.length - 1], B[B.length - 1]);

        // find first x that >= k numbers is smaller or equal to x
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            if (countSmallerOrEqual(A, mid) + countSmallerOrEqual(B, mid) < k) {
                start = mid;
            } else {
                end = mid;
            }
        }

        if (countSmallerOrEqual(A, start) + countSmallerOrEqual(B, start) >= k) {
            return start;
        }

        return end;
    }

    private int countSmallerOrEqual(int[] arr, int number) {
        int start = 0, end = arr.length - 1;

        // find first index that arr[index] > number;
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            if (arr[mid] <= number) {
                start = mid;
            } else {
                end = mid;
            }
        }

        if (arr[start] > number) {
            return start;
        }

        if (arr[end] > number) {
            return end;
        }

        return arr.length;
    }

    /**
     * Solution 3
     * JiuZhang
     *
     * 分治法。时间复杂度 log(n + m)
     */
    public double findMedianSortedArrays3(int A[], int B[]) {
        int n = A.length + B.length;

        if (n % 2 == 0) {
            return (findKth(A, 0, B, 0, n / 2) + findKth(A, 0, B, 0, n / 2 + 1)) / 2.0;
        }

        return findKth(A, 0, B, 0, n / 2 + 1);
    }

    // find kth number of two sorted array
    public double findKth(int[] A, int startA, int[] B, int startB, int k) {
        /**
           !!!
           The order of the first 2 boundary condition checks and the check for "k == 1" can NOT be changed.
           If start index is out of boundary, we must deal with it first.
         **/
        if (startA >= A.length) {
            return B[startB + k - 1];
        }

        if (startB >= B.length) {
            return A[startA + k - 1];
        }

        if (k == 1) {
            return Math.min(A[startA], B[startB]);
        }

        int targetA = startA + k / 2 - 1 < A.length ? A[startA + k / 2 - 1] : Integer.MAX_VALUE;
        int targetB = startB + k / 2 - 1 < B.length ? B[startB + k / 2 - 1] : Integer.MAX_VALUE;

        /**
         !!!
         "k - k / 2", it can NOT be changed to "N / 2". This is not integer subtraction.
         for example :  if k = 3, k - k / 2 = 3 - 3/2 = 3 - 1 = 2, it is NOT 3 / 2 = 1.
         Basically, we just throw away elements of the number k / 2 here.
        **/
        if (targetA < targetB) {
            return findKth(A, startA + k / 2, B, startB, k - k / 2);
        } else {
            return findKth(A, startA, B, startB + k / 2, k - k / 2);
        }
    }
}
