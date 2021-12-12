package leetcode;

import java.util.PriorityQueue;

public class LE_1642_Furthest_Building_You_Can_Reach {
    /**
     * You are given an integer array heights representing the heights of buildings, some bricks, and some ladders.
     *
     * You start your journey from building 0 and move to the next building by possibly using bricks or ladders.
     *
     * While moving from building i to building i+1 (0-indexed),
     *
     * If the current building's height is greater than or equal to the next building's height, you do not need a ladder or bricks.
     * If the current building's height is less than the next building's height, you can either use one ladder or (h[i+1] - h[i]) bricks.
     * Return the furthest building index (0-indexed) you can reach if you use the given ladders and bricks optimally.
     *
     * Example 1:
     * Input: heights = [4,2,7,6,9,14,12], bricks = 5, ladders = 1
     * Output: 4
     * Explanation: Starting at building 0, you can follow these steps:
     * - Go to building 1 without using ladders nor bricks since 4 >= 2.
     * - Go to building 2 using 5 bricks. You must use either bricks or ladders because 2 < 7.
     * - Go to building 3 without using ladders nor bricks since 7 >= 6.
     * - Go to building 4 using your only ladder. You must use either bricks or ladders because 6 < 9.
     * It is impossible to go beyond building 4 because you do not have any more bricks or ladders.
     *
     * Example 2:
     * Input: heights = [4,12,2,7,3,18,20,3,19], bricks = 10, ladders = 2
     * Output: 7
     *
     * Example 3:
     * Input: heights = [14,3,19,3], bricks = 17, ladders = 0
     * Output: 3
     *
     * Constraints:
     * 1 <= heights.length <= 105
     * 1 <= heights[i] <= 106
     * 0 <= bricks <= 109
     * 0 <= ladders <= heights.length
     *
     * Medium
     */

    /**
     * https://leetcode.com/problems/furthest-building-you-can-reach/solution/
     *
     * Key Observations:
     * 1.The best strategy is to use the ladders for the longest climbs and the bricks for the shortest climbs
     * 2.The solution is to move along the buildings sequentially, one climb at a time. At all times, we should ensure
     *   ladders have been allocated to the longest climbs seen so far and bricks to the shortest
     * 3.This might sometimes involve going back and changing an earlier allocation.
     */

    /**
     * Min-Heap
     *
     * Whenever we still have ladders, use it (put the height of the clim into pq), in other words, use ladders as
     * priority and use pq to track.
     * When there's no ladder available, compare the climb height with the min-height of the ladder used so far,
     * if that ladder height is smaller than the current climb, swap - use that ladder for current climb and apply
     * bricks to the previous ladder climb.
     *
     * Time : O(NlogN) or O(NlogL), N : length of heights, L : number of ladders
     * Inserting or removing an item from a heap incurs a cost of O(logx), where x is the number of items currently in
     * the heap. In the worst case, we know that there will be N - 1 climbs in the heap, thus giving a time complexity
     * of O(logN) for each insertion and removal, and we're doing up to N of each of these two operations. This gives a
     * O(NlogN). In practice, though, the heap will never contain more than L + 1 climbs at a time—when it gets to this size,
     * we immediately remove a climb from it. So, the heap operations are actually O(logL). We are still performing up to N
     * of each of them, though, so this gives a total time complexity of O(NlogL).
     *
     * Space : O(N) or O(L)
     * As we determined above, the heap can contain up to O(L) numbers at a time. In the worst case, L = N, so we get O(N).
     */
    class Solution1 {
        public int furthestBuilding(int[] heights, int bricks, int ladders) {
            int n = heights.length;
            /**
             * Min Heap
             * Save climb heights by ladders
             */
            PriorityQueue<Integer> pq = new PriorityQueue<>();

            for (int i = 0; i < n - 1; i++) {
                int climb = heights[i + 1] - heights[i]; //!!! make sure we deal with positive number when it is a climb

                if (climb <= 0) continue; //not a climb

                if (ladders > 0) {// Whenever we still have ladders, use it (put the height of the climb into pq)
                    pq.offer(climb);
                    ladders--;
                } else {//no more ladders
                    Integer cur = pq.peek();
                    if (cur == null || climb < cur)  {
                        bricks -= climb;
                    } else {//have a higher climb than the current min ladder height
                        int ladder = pq.poll(); //remove the min ladder from its original location
                        bricks -= ladder; //use bricks instead
                        pq.offer(climb); //use ladder for current climb
                    }

                    if (bricks < 0) return i; //no ladder and not enough bricks, can't go further
                }
            }

            return n - 1;
        }
    }

    /**
     * Simplified logic from Solution1
     */
    class Solution1_1 {
        public int furthestBuilding(int[] heights, int bricks, int ladders) {
            int n = heights.length;
            PriorityQueue<Integer> pq = new PriorityQueue<>();

            for (int i = 0; i < n - 1; i++) {
                int diff = heights[i + 1] - heights[i];

                if (diff <= 0) continue;

                pq.offer(diff);

                if (pq.size() <= ladders) continue;

                bricks -= pq.poll();

                if (bricks < 0) return i;
            }

            return n - 1;
        }
    }

    /**
     * Max-Heap
     *
     * Use bricks as priority, so use pq to track bricks usage.
     *
     * Time  : O(NlogN)
     * Space : O(N)
     */
    class Solution2 {
        public int furthestBuilding(int[] heights, int bricks, int ladders) {
            int n = heights.length;
            /**
             * Max Heap
             * Save bricks used for climbs
             */
            PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> b - a);

            for (int i = 0; i < n - 1; i++) {
                int climb = heights[i + 1] - heights[i];

                if (climb <= 0) continue;

                /**
                 * Use bricks as priority and track it in pq
                 */
                pq.offer(climb);
                bricks -= climb;
                if (bricks >= 0) continue; //as long as we still have bricks, go on

                /**
                 * no ladder and no brick, can't go further
                 */
                if (ladders == 0) return i;

                /**
                 * No bricks by now, but we still have ladders, swap ladder for the max bricks used
                 */
                Integer cur = pq.poll();// get the max bricks climb height
                bricks += cur; // return the bricks (add to unused bricks)
                ladders--; //use a ladder instead
            }

            return n - 1;
        }
    }


}
