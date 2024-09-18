package leetcode;

import java.util.*;

/**
 * Created by yuank on 4/8/18.
 */
public class LE_215_Kth_Largest_Element_In_Array {
    /**
        Find the kth largest element in an unsorted array.
        Note that it is the kth largest element in the sorted order, not the kth distinct element.
        For example,
        Given [3,2,1,5,6,4] and k = 2, return 5.

        Note:
        You may assume k is always valid, 1 ≤ k ≤ array's length.

        Medium

        https://leetcode.com/problems/kth-largest-element-in-an-array
     **/

    class Solution_min_heap_of_size_k {
        /**
         Solution 1 : use min heap.

         Time  : O(nlogk),
         Space : O(k)

         !!!
         Under normal condition, Solution 2 (quick select) is better (O(n)).
         If "N is much larger than k", heap solution is better :
         1.quick select只是期望是O(N), 并不是真正意义上的O(N)
         2.N 远大于k，说明logk的复杂度很小，我们可以采用Nlogk复杂度的算法。
         https://www.jiuzhang.com/qa/4260/

         Use default PriorityQueue, which is a min-heap, the top one is the min value.
         We keep the size of the heap as k (keep adding elements to heap, once heap size is bigger than k, remove top one
         after adding new element). Hence, in the end, in a heap that has size k, the top one is the min of the k elements,
         it is the kth largest value in nums.
         **/
        public int findKthLargest1(int[] nums, int k) {
            PriorityQueue<Integer> pq = new PriorityQueue<>();
            for (int i = 0; i < nums.length; i++) {
                pq.offer(nums[i]);
                if (pq.size() > k) {
                    pq.poll();
                }
            }

            return pq.peek();
        }
    }

    class Solution_counting_sort {
        /**
         * Given n as the length of nums and m as maxValue - minValue,
         *
         * Time complexity: O(n + m)
         *
         * We first find maxValue and minValue, which costs O(n).
         * Next, we initialize count, which costs O(m).
         * Next, we populate count, which costs O(n).
         * Finally, we iterate over the indices of count, which costs up to O(m).
         *
         * Space complexity: O(m)
         */
        public int findKthLargest(int[] nums, int k) {
            int min = Integer.MAX_VALUE;  // MAX_VALUE for min!!!
            int max = Integer.MIN_VALUE;

            for (int n : nums) {
                min = Math.min(min, n);
                max = Math.max(max, n);
            }

            int size = max - min + 1;
            int[] count = new int[size];

            for (int n : nums) {
                count[n - min]++; //!!! offset: n - min
            }

            int target = k;
            for (int i = count.length - 1; i >= 0; i--) {
                k -= count[i];
                if (k <= 0) {
                    return i + min; //!!! add offset to get the correct value !!!
                }
            }

            return -1;
        }
    }

    class Solution_quick_sort {
        /**
         * Time : O(n), worst case O(n ^ 2)
         * Space : O(n) - we use list for left, right, mid (instead passing index like the quick sort shown below.
         *
         * This version is from Leetcode official solution, it is concise and easy to remember
         */
        class Solution {
            public int findKthLargest(int[] nums, int k) {
                List<Integer> list = new ArrayList<>();
                for (int num: nums) {
                    list.add(num);
                }

                return quickSelect(list, k);
            }

            public int quickSelect(List<Integer> nums, int k) {
                int pivotIndex = new Random().nextInt(nums.size());
                int pivot = nums.get(pivotIndex);

                List<Integer> left = new ArrayList<>();
                List<Integer> mid = new ArrayList<>();
                List<Integer> right = new ArrayList<>();

                for (int num: nums) {
                    if (num > pivot) {
                        left.add(num);
                    } else if (num < pivot) {
                        right.add(num);
                    } else {
                        mid.add(num);
                    }
                }

                if (left.size() >= k) {
                    return quickSelect(left, k);
                }

                if (left.size() + mid.size() < k) {
                    return quickSelect(right, k - left.size() - mid.size());
                }

                return pivot;
            }
        }
    }

    /**
     * **********************************
     */

    /**
     * Quick Select:
     *
     * Time  : O(n), worst case O(n ^ 2)
     * Space : O(1)
     */
    class Solution_Quick_Select {
        public int findKthLargest(int[] nums, int k) {
            /**
             * !!!
             * All param passed here is index, so to find the kth largest number,
             * the target index is k - 1
             */
            return quickSelect(nums, 0, nums.length - 1, k - 1);//!!! index
        }

        private int quickSelect(int[] nums, int start, int end, int k) {
            if (start >= end) return nums[start];

            int l = start;
            int r = end;

            int pivot = nums[start + (end - start) / 2];

            while (l <= r) {
                while (l <= r && nums[l] > pivot) {
                    l++;
                }

                while (l <= r && nums[r] < pivot) {
                    r--;
                }

                if (l <= r) {
                    swap(nums, l, r);
                    l++;
                    r--;
                }
            }

            if (k <= r) {
                return quickSelect(nums, start, r, k);
            }

            if (k >= l) {
                return quickSelect(nums, l, end, k );
            }

            return nums[k];
        }

        private void swap(int[] nums, int left, int right) {
            int temp = nums[left];
            nums[left] = nums[right];
            nums[right] = temp;
        }
    }



    /**
     * Quick Select:
     *
     * Time  : O(n), worst case O(n ^ 2)
     * Space : O(1)
     *
     * For another implementation for  Quick Select solution, refer to LI_005_Kth_Largest_Number
     */
    /**
        [3, 2, 1, 5, 6, 4]   k = 3
    col  0  1  2  3  4  5

    Partition 1, left=0, right=5:
         p  l           r
        [3, 2, 1, 5, 6, 4]
    col  0  1  2  3  4  5

         p     l     r
        [3, 4, 1, 5, 6, 2]
    col  0  1  2  3  4  5

         p        lr
        [3, 4, 6, 5, 1, 2]
    col  0  1  2  3  4  5

         p        r  l
        [3, 4, 6, 5, 1, 2]
    col  0  1  2  3  4  5

         p        r  l
        [5, 4, 6, 3, 1, 2]
    col  0  1  2  3  4  5

        return r (3)

        pos = 3, k - 1 = 2, pos > k - 1, right = pos - 1 = 3 - 1 = 2
        Partition 2, left=0, right=2:
         l     r
        [5, 4, 6, 3, 1, 2]
    col  0  1  2  3  4  5

         p  l  r
        [5, 4, 6, 3, 1, 2]
    col  0  1  2  3  4  5

         p  r  l
        [5, 6, 4, 3, 1, 2]
    col  0  1  2  3  4  5

         p  r  l
        [6, 5, 4, 3, 1, 2]
    col  0  1  2  3  4  5

         return r (1)

         pos = 1, k - 1 = 2, pos < k - 1, left = pos + 1 = 2
        Partition 3, left=2, right=2:

               lr
        [6, 5, 4, 3, 1, 2]
    col  0  1  2  3  4  5

        return r (2)

        pos = 2, k - 1 = 2, pos==k-1, return nums[2] = 4, 4 is the 3rd largest number in nums.

    */

    public int findKthLargest(int[] nums, int k) {
        /**
         * first set left and right value
         */
        int left = 0;
        int right = nums.length - 1;

        while (true) {
            int pos = partition(nums, left, right);

            /**
             * "pos == k - 1"
             */
            if (pos == k - 1) {
                return nums[pos];
            } else if (pos > k - 1) {//pos - 1
                right = pos - 1;
            } else if (pos < k - 1) {//pos + 1
                left = pos + 1;
            }
        }
    }

    public int partition(int[] nums, int left, int right) {
        int pivot = nums[left];
        int l = left + 1;
        int r = right;
        while (l <= r) {
            /**
             * we are trying to find kth largest number,
             * therefore left part should be >= pivot,
             * right part should be <= pivot.
             */
            if (nums[l] < pivot && nums[r] > pivot) {
                swap(nums, l++, r--);
            }

            /**
             * !!!
             * it's ">=" and "<="
             */
            if (nums[l] >= pivot) l++;
            if (nums[r] <= pivot) r--;
        }

        /**
         * put pivot back, since while loop condition is "l <= r",
         * by this step, we know l and r has crossed each other.
         */
        swap(nums, left, r);
        return r;
    }

    public void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
