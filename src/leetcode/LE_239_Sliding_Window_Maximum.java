package leetcode;

import java.util.*;

/**
 * Created by yuank on 4/6/18.
 */
public class LE_239_Sliding_Window_Maximum {
    /**
        Given an array nums, there is a sliding window of size k which is moving from the very left of the array to the very right.
        You can only see the k numbers in the window. Each time the sliding window moves right by one position.

        For example,
        Given nums = [1,3,-1,-3,5,3,6,7], and k = 3.

        Window position                Max
        ---------------               -----
        [1  3  -1] -3  5  3  6  7       3
         1 [3  -1  -3] 5  3  6  7       3
         1  3 [-1  -3  5] 3  6  7       5
         1  3  -1 [-3  5  3] 6  7       5
         1  3  -1  -3 [5  3  6] 7       6
         1  3  -1  -3  5 [3  6  7]      7
        Therefore, return the max sliding window as [3,3,5,5,6,7].

        Note:
        You may assume k is always valid, ie: 1 ≤ k ≤ input array's size for non-empty array.

        Follow up:
        Could you solve it in linear time?
     */

    /**
      https://www.youtube.com/watch?v=2SXqBsTR6a8&t=6s

      Brutal Force : Time : O((n - k + 1) * k), worst case O(n ^ 2), space : O(1)

      Heap solution : Time : O((n - k + 1) * logk), space : O(k)

      Best solution : Time and Space : O(n)
      Monotonic Queue Solution, using Deque to implement.
      Monotonic Queue - maintain elements in queue in sorted order (from large to small). Max number is at the head of the deque.
      Example :

             input            Mono Queue        max
      [[1],3,-1,-3,5,3,6,7]    [1]
      [[1,3],-1,-3,5,3,6,7]    [3]
      [[1,3,-1],-3,5,3,6,7]    [3,-1]            3
      [1,[3,-1,-3],5,3,6,7]    [3,-1,-3]         3
      [1,3,[-1,-3,5],3,6,7]    [5]               5
      [1,3,-1,[-3,5,3],6,7]    [5,3]             5
      [1,3,-1,-3,[5,3,6],7]    [6]               6
      [1,3,-1,-3,5,[3,6,7]]    [7]               7


    */

    class MonoQueue {
        private Deque<Integer> deque;

        public MonoQueue() {
            deque = new LinkedList<>();
        }

        public void push(int num) {
            //remove all elements that are smaller than num before add num
            while (!deque.isEmpty() && num > deque.peekLast()) {
                deque.pollLast();
            }
            deque.addLast(num);
        }

        public void pop() {
            deque.poll();
        }

        public int getMax() {
            return deque.peek();
        }
    };

    public int[] maxSlidingWindow(int[] nums, int k) {
        if(nums == null || nums.length == 0) {
            return new int[0];
        }

        MonoQueue mq = new MonoQueue();
        int[] res = new int[nums.length - k + 1];

        for (int i = 0; i < nums.length; i++) {
            mq.push(nums[i]);
            if (i - k + 1 >= 0) {
                res[i - k + 1] = mq.getMax();
                if (nums[i - k + 1] == mq.getMax()) {
                    mq.pop();
                }
            }
        }

        return res;
    }


    //Use Deque directly, save index in deque instead of number itself
    public int[] maxSlidingWindow2(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return nums;
        }

        Deque<Integer> deque = new LinkedList<>();
        int[] res = new int[nums.length - k + 1];

        for (int i = 0; i < nums.length; i++) {
            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
                deque.pollLast();
            }
            deque.addLast(i);

            if (i - k + 1 >= 0) {
                res[i - k + 1] = nums[deque.peek()]; //!!! "peek", not "poll"
                if (nums[i - k + 1] == res[i - k + 1]) {
                    deque.poll();
                }
            }
        }

        return res;
    }


    //Solution 3 : Heap. Time : O(n ^ 2), Space : O(k)
    public int[] maxSlidingWindow3(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return nums;
        }

        //!!! By default, Java PriorityQueue is a min heap, to define max heap, need to provide Comparator
        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> b - a);
        int[] res = new int[nums.length - k + 1];

        for (int i = 0; i < nums.length; i++) {
            pq.offer(nums[i]);
            if (pq.size() >= k) {
                int idx = i - k + 1;
                res[i - k + 1] = pq.peek();
                //!!! Use remove to delete an object from pq.
                /**
                 * PriorityQueue.remove() is O(n). If we want to achieve logn for remove(),
                 * need to implement Hash Heap, Java lib does not supply one.
                 **/
                pq.remove(nums[i - k + 1]);
            }
        }

        return res;
    }

    /**
     * Solution using Deque and return type is list (instead of array)
     * Example:
     *
     *  idx   0 1 2 3 4
     * Input [1,2,7,7,2]
     *
     * i = 0
     * dq  : 0
     * res : []
     *
     * i = 1
     * dq  : 1
     * res : []
     *
     * i = 2
     * dq  : 2
     * res : [nums[2]]
     * (i - k + 1 = 0)<> (dq.peekFirst() == 2)
     *
     * i = 3
     * dq = 2, 3
     * res : [nums[2], nums[3]]
     * (i - k + 1 = 1)<> (dq.peekFirst() == 2)
     *
     * i = 4
     * dq = 2, 3, 4
     * res : [nums[2], nums[2], nums[2]]
     * (i - k + 1 = 2) == (dq.peekFirst() == 2), remove first element
     * dq = 3, 4
     */
    public List<Integer> maxSlidingWindow4(int[] nums, int k) {
        List<Integer> res = new ArrayList<>();
        if (nums == null || nums.length == 0 || k == 0) return res;

        Deque<Integer> dq = new LinkedList<>();

        //i tracks the index of the element that will come into sliding window
        for (int i = 0; i < nums.length; i++) {
            while (!dq.isEmpty() && nums[dq.peekLast()] < nums[i]) {
                dq.pollLast();
            }
            dq.offerLast(i);

            /**
                meaning we already fill up the window

                !!! ">= 0"

                Moving sliding window is 2 actions
                1.add a new element at the right side
                2.remove the element at the left side

                Here we actually remove the left side first, then
                in the NEXT loop, add the new one on the right.
             **/
            if (i - k + 1 >= 0) {
                res.add(nums[dq.peekFirst()]);

                if ((i - k + 1) == dq.peekFirst()) {
                    dq.pollFirst();
                }
            }
        }

        return res;
    }
}
