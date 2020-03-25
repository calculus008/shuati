package leetcode;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class LE_1046_Last_Stone_Weight {
    /**
     * We have a collection of rocks, each rock has a positive integer weight.
     *
     * Each turn, we choose the two heaviest rocks and smash them together.
     * Suppose the stones have weights x and y with x <= y.  The result of this smash is:
     *
     * If x == y, both stones are totally destroyed;
     * If x != y, the stone of weight x is totally destroyed, and the stone of weight y has new weight y-x.
     * At the end, there is at most 1 stone left.  Return the weight of this stone (or 0 if there are no stones left.)
     *
     *
     * Example 1:
     * Input: [2,7,4,1,8,1]
     * Output: 1
     * Explanation:
     * We combine 7 and 8 to get 1 so the array converts to [2,4,1,1,1] then,
     * we combine 2 and 4 to get 2 so the array converts to [2,1,1,1] then,
     * we combine 2 and 1 to get 1 so the array converts to [1,1,1] then,
     * we combine 1 and 1 to get 0 so the array converts to [1] then that's the value of last stone.
     *
     *
     * Note:
     * 1 <= stones.length <= 30
     * 1 <= stones[i] <= 1000
     *
     * Easy
     */
    class Solution1 {
        public int lastStoneWeight(int[] stones) {
            PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> Integer.compare(b, a));

            for (int stone : stones)  {
                pq.offer(stone);
            }

            int res = 0;
            while (!pq.isEmpty()) {
                int cur = pq.poll();

                if (!pq.isEmpty()) {
                    int next = pq.poll();
                    if (next != cur) {
                        int n = Math.abs(cur - next);
                        pq.offer(n);
                    }
                } else {
                    res = cur;
                }
            }

            return res;
        }
    }

    class Solution2 {
        public int lastStoneWeight(int[] stones) {
            Queue<Integer> maxPq = new PriorityQueue<>(stones.length, Comparator.reverseOrder());

            for (int stone : stones) {
                maxPq.add(stone);
            }

            while (maxPq.size() >= 2) {
                int y = maxPq.poll();
                int x = maxPq.poll();
                if (y > x) {
                    maxPq.add(y - x);
                }
            }

            return maxPq.isEmpty() ? 0 : maxPq.peek();
        }
    }
}
