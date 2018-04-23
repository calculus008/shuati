package leetcode;

import java.util.Collections;
import java.util.PriorityQueue;

/**
 * Created by yuank on 4/22/18.
 */
public class LE_295_Find_Median_From_Data_Stream {
    /**
         Median is the middle value in an ordered integer list. If the size of the list is even,
         there is no middle value. So the median is the mean of the two middle value.

         Examples:
         [2,3,4] , the median is 3

         [2,3], the median is (2 + 3) / 2 = 2.5

         Design a data structure that supports the following two operations:

         void addNum(int num) - Add a integer number from the data stream to the data structure.
         double findMedian() - Return the median of all elements so far.
         For example:

         addNum(1)
         addNum(2)
         findMedian() -> 1.5
         addNum(3)
         findMedian() -> 2

        Hard
     */


    /**
     * Solution 1 : instead of make min heap and max heap, use min heap by default and use the trick that make element negative when adding to small
     * heap to achieve the same result of a max heap.
     *
     * O(logn)
     */
    class MedianFinder1 {
        /**
         -1, 2, 3, 4
         -1:
         big :  -1
         small : 1

         big :
         small : 1,

         big :  -1
         small :

         2:
         big :   -1, 2
         small :

         big :   2
         small : 1

         3:
         big :   2, 3
         small : 1

         big   : 3
         small : 1, -2

         big   : 3, 2
         small : 1

         4:
         big :   3, 2, 4
         small : 1

         big   : 3, 4
         small : 1, -2

         */

        /**
         * PriorityQueue should take Long, not Integer, to prevent overflow. May not need it. Since using Integer also passes Leetcode tests.
         */
        PriorityQueue<Long> small;
        PriorityQueue<Long> big;

        /** initialize your data structure here. */
        public MedianFinder1() {
            small = new PriorityQueue<>();
            big = new PriorityQueue<>();
        }

        public void addNum(int num) {
            big.offer((long)num);
            //!!! 是从big中poll，也就是取出最小的元素（pq是min priorityqueue by default). 取出后，该元素在big中就不存在了。
            small.offer(-big.poll());
            if (small.size() > big.size()) {
                big.offer(-small.poll());
            }

        }

        public double findMedian() {
            /**
                 Many traps in this one line:
                 1.Since we make element in small heat negative, we need to do "- small.peek()", not "+".
                 2.Use "2.0", NOT "2" for devide, otherwise it will return as type long. (1 + 2) / 2 will return 1, which is wrong,
                   while (1 + 2) /2.0 returns 1.5.
                 3.Use "peek()", not "poll()". We just get the value, not remove the top element
             */
            return big.size() > small.size() ? big.peek() : (big.peek() - small.peek()) / 2.0;
        }
    }

    /**
     * Solution 2 : Same logic, just use Max Heap instead of using the negative trick
     */
    class MedianFinder2 {

        PriorityQueue<Long> small;
        PriorityQueue<Long> large;

        /**
         * initialize your data structure here.
         */
        public MedianFinder2() {
            /**
             * init max head
             */
            small = new PriorityQueue<>(1000, Collections.reverseOrder()); //has to specify init capacity in this constructor
            // small = new PriorityQueue<>((a, b) -> b - a); //since we use Long in heap, this line won't work, because the comparator still takes Integer, so the complier is confused and return error. If we use Integer for heap, it works.
            large = new PriorityQueue<>();
        }

        public void addNum(int num) {
            large.offer((long) num);
            small.offer(large.poll());
            if (small.size() > large.size()) {
                large.offer(small.poll());
            }
        }

        public double findMedian() {
            return large.size() != small.size() ? large.peek() : (large.peek() + small.peek()) / 2.0;
        }
    }
}
