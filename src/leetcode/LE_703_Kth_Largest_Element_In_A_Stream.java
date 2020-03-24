package leetcode;

import java.util.PriorityQueue;

public class LE_703_Kth_Largest_Element_In_A_Stream {
    /**
     * Design a class to find the kth largest element in a stream.
     * Note that it is the kth largest element in the sorted order,
     * not the kth distinct element.
     *
     * Your KthLargest class will have a constructor which accepts
     * an integer k and an integer array nums, which contains initial
     * elements from the stream. For each call to the method KthLargest.add,
     * return the element representing the kth largest element in the stream.
     *
     * Example:
     *
     * int k = 3;
     * int[] arr = [4,5,8,2];
     * KthLargest kthLargest = new KthLargest(3, arr);
     * kthLargest.add(3);   // returns 4
     * kthLargest.add(5);   // returns 5
     * kthLargest.add(10);  // returns 5
     * kthLargest.add(9);   // returns 8
     * kthLargest.add(4);   // returns 8
     * Note:
     * You may assume that nums' length ≥ k-1 and k ≥ 1.
     *
     * Easy
     */

    class KthLargest {
        PriorityQueue<Integer> pq;
        int size;

        public KthLargest(int k, int[] nums) {
            pq = new PriorityQueue<>();
            size = k;

            for (int num : nums) {
                pq.offer(num);
                if (pq.size() > size) {
                    pq.poll();
                }
            }
        }

        public int add(int val) {
            pq.offer(val);
            if (pq.size() > size) {
                pq.poll();
            }

            return pq.peek();
        }
    }
}
