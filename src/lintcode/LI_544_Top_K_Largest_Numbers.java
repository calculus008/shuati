package lintcode;

import java.util.PriorityQueue;
import java.util.Random;

/**
 * Created by yuank on 9/2/18.
 */
public class LI_544_Top_K_Largest_Numbers {
    /**
         Given an integer array, find the top k largest numbers in it.

         Example
         Given [3,10,1000,-99,4,100] and k = 3.
         Return [1000, 100, 10].
     */

    /**
        Solution 1 : Heap, time O(nlogk)
     */
    public int[] topk(int[] nums, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int num : nums) {
            pq.offer(num);
            if (pq.size() > k) {
                pq.poll();
            }
        }

        int[] res = new int[k];
        int i = k - 1;
        while (!pq.isEmpty()) {
            res[i--] = pq.poll();
        }

        return res;
    }

    /**
       Solution 2 : Quick Select
     */

    public int[] topk2(int[] nums, int k) {
        // Write your code here
        quickSort(nums, 0, nums.length - 1, k);

        int[] topk = new int[k];
        for (int i = 0; i < k; ++i)
            topk[i] = nums[i];

        return topk;
    }

    private void quickSort(int[] A, int start, int end, int k) {
        if (start >= k)
            return;

        if (start >= end) {
            return;
        }

        int left = start, right = end;
        // key point 1: pivot is the value, not the index
        Random rand =new Random(end - start + 1);
        int index = rand.nextInt(end - start + 1) + start;
        int pivot = A[index];

        // key point 2: every time you compare left & right, it should be
        // left <= right not left < right
        while (left <= right) {
            // key point 3: A[left] < pivot not A[left] <= pivot
            while (left <= right && A[left] > pivot) {
                left++;
            }
            // key point 3: A[right] > pivot not A[right] >= pivot
            while (left <= right && A[right] < pivot) {
                right--;
            }

            if (left <= right) {
                int temp = A[left];
                A[left] = A[right];
                A[right] = temp;

                left++;
                right--;
            }
        }

        quickSort(A, start, right, k);
        quickSort(A, left, end, k);
    }
}
