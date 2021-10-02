package leetcode;

import java.util.*;

public class LE_683_K_Empty_Slots {
    /**
     * You have n bulbs in a row numbered from 1 to n. Initially, all the bulbs are turned off. We turn on exactly one
     * bulb every day until all bulbs are on after n days.
     *
     * You are given an array bulbs of length n where bulbs[i] = x means that on the (i+1)th day, we will turn on the
     * bulb at position x where i is 0-indexed and x is 1-indexed.
     *
     * Given an integer k, return the minimum day number such that there exists two turned on bulbs that have exactly k
     * bulbs between them that are all turned off. If there isn't such day, return -1.
     *
     * Example 1:
     * Input: bulbs = [1,3,2], k = 1
     * Output: 2
     * Explanation:
     * On the first day: bulbs[0] = 1, first bulb is turned on: [1,0,0]
     * On the second day: bulbs[1] = 3, third bulb is turned on: [1,0,1]
     * On the third day: bulbs[2] = 2, second bulb is turned on: [1,1,1]
     * We return 2 because on the second day, there were two on bulbs with one off bulb between them.
     *
     * Example 2:
     * Input: bulbs = [1,2,3], k = 1
     * Output: -1
     *
     * Constraints:
     * n == bulbs.length
     * 1 <= n <= 2 * 104
     * 1 <= bulbs[i] <= n
     * bulbs is a permutation of numbers from 1 to n.
     * 0 <= k <= 2 * 104
     *
     * Hard
     *
     * https://leetcode.com/problems/k-empty-slots/
     */

    /**
     * https://leetcode.com/problems/k-empty-slots/discuss/107948/Iterate-over-time-vs.-iterate-over-position
     *
     * Iterate over position
     * For this perspective, we need to first transform our input array flowers into another array days so that days[i]
     * represents the day on which the flower at position i + 1 will bloom (note again index i starts from 0). Now given
     * an integer k, we are required to output the first day such that there exists two flowers in the status of blooming,
     * and the number of flowers between them is k and these flowers are not blooming.
     *
     * What is the difference between this perspective and the previous one? The answer is: all the candidate ranges of
     * position of the flowers are readily known and are arranged in ascending order. Let [j, i] represent flowers from
     * position j + 1 to position i + 1 (both inclusive). Then [j, i] is a candidate range if j = i - (k + 1). This is
     * because if we can determine that all flowers from positions j + 2 to i will bloom after both flowers at position
     * j + 1 and position i + 1, let d = max(days[i], days[j]), then d will be a day that satisfies the aforementioned
     * condition (though may not be the first such day) and thus qualifies as a candidate day for the final answer.
     * We just need to choose the smallest one from all these candidate days.
     *
     * So how do we check if all flowers from positions j + 2 to i will bloom after both flowers at position j + 1 and
     * i + 1? The solution is simple: of all the flowers from positions j + 2 to i, find the one that will bloom first
     * and denote the day on which it blooms as d_min, then compare d_min with d. If d_min > d, then d is a valid candidate
     * day as specified above; otherwise it is not. Again, we will have multiple ways to implement this idea.
     *
     * Final remarks: this type of problems typically exhibit "symmetric" features in the input data set. For this problem,
     * we have equivalent perspectives of the input data as either flowers or days. Similar situations can be found for ：
     *
     * 354. Russian Doll Envelopes
     * 630. Course Schedule III
     *
     * However, in the presence of extra restrictions, this symmetry may be broken and solutions may favor one perspective
     * over the other. In such cases, it would be advisable to make attempts from both perspectives and choose the one
     * that suits you best.
     */

    /**
     * No-queue solution
     *
     * Time and Space : O(n)
     *
     * "bulbs[i] = x means that on the (i+1)th day, we will turn on the bulb at position x
     *  where i is 0-indexed and x is 1-indexed."
     *
     * 根据题意，我们实际上是要对days操作，所以要先生成days数组：
     * days[] to record each position's flower's blooming day. That means days[i] is the blooming day of the flower in
     * position i+1。也就是说， days[]的数值是day, days[i] = y, 在第y天，i+1位置的灯泡亮了。
     *
     * 在days[]里面，我们是要找这样的一个长度为 k + 2 的窗口：
     * index：
     * left, left + 1, left + 2, ...., left + k - 1, left + k, right
     *
     * 想象我们从最左边开始，向右移动这个长度固定的窗口。对于days[i] (left < i < right), 合法的窗口满足：
     * days[i] > days[left] and days[i] > days[right]
     * 窗口左边界从0开始， 所以窗口中的day值一定要大于窗口的左右边界的day值，这样才能保证，时间上，在day[left]和day[right]之间，没有
     * 灯在空间位置left和right之间能点亮。
     *
     * bulbs[i] = x   时间为下标， 空间为值, for (i + 1)th day, bulb at position x is turned on.
     * days[i] = y    空间为下标， 时间为值, for bulb position (i + 1), it is turned on on the yth day.
     *
     */
    class Solution1 {
        public int kEmptySlots(int[] bulbs, int k) {
            int n = bulbs.length;
            int[] days = new int[n];

            for (int i = 0; i < n; i++) {
                /**
                 * position is the index in array days[], should be zero based.
                 * "i + 1" : value for days, one based.
                 */
                int position = bulbs[i] - 1;
                days[position] = i + 1;
            }

            int left = 0;
            int right = k + 1;

            int res = Integer.MAX_VALUE;

            for (int i = 1; right < n; i++) {
                if (days[i] > days[left] && days[i] > days[right]) continue;

                if (i == right) {
                    res = Math.min(res, Math.max(days[left], days[right]));
                }

                left = i;
                right = left + k + 1;
            }

            return res == Integer.MAX_VALUE ? -1 : res;
        }
    }

    /**
     * Deque Solution
     *
     * The key here is to get rid of positions with blooming days larger than that of the current position before adding
     * it to the deque from the left (this is because as long as the current position is in the deque, the position with
     * minimum blooming day cannot be these removed positions). Each position will be pushed into and popped out from the
     * deque once, so the overall time complexity will be O(n).
     *
     * ?? don't quite understand it yet.
     */
    class Solution {
        public int kEmptySlots(int[] bulbs, int k) {
            int n = bulbs.length;
            int[] days = new int[n];

            for (int i = 0; i < n; i++) {
                int position = bulbs[i] - 1;
                days[position] = i + 1;

                /**
                 * deal with k = 0 case separately
                 */
                if (k == 0 && (position - 1 >= 0 && days[position - 1] != 0	|| position + 1 < days.length && days[position + 1] != 0))
                    return i + 1;
            }

            Deque<Integer> dq = new ArrayDeque<>();
            int res = Integer.MAX_VALUE;

            for (int i = 0; i < days.length; i++) {
                while (!dq.isEmpty() && days[dq.peekLast()] > days[i]) {
                    dq.pollLast();

                    if (k > 0 && !dq.isEmpty() && i - dq.peekLast() - 1 == k) {
                        res = Math.min(res, Math.max(days[dq.peekLast()], days[i]));
                    }
                }
                dq.offerLast(i);
            }

            return res == Integer.MAX_VALUE ? -1 : res;
        }
    }
}
