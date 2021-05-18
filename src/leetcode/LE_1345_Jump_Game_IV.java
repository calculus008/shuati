package leetcode;

import java.util.*;

public class LE_1345_Jump_Game_IV {
    /**
     * Given an array of integers arr, you are initially positioned at the first index of the array.
     *
     * In one step you can jump from index i to index:
     *
     * i + 1 where: i + 1 < arr.length.
     * i - 1 where: i - 1 >= 0.
     * j where: arr[i] == arr[j] and i != j.
     * Return the minimum number of steps to reach the last index of the array.
     *
     * Notice that you can not jump outside of the array at any time.
     *
     * Example 1:
     * Input: arr = [100,-23,-23,404,100,23,23,23,3,404]
     * Output: 3
     * Explanation: You need three jumps from index 0 --> 4 --> 3 --> 9. Note that index 9 is the last index of the array.
     *
     * Example 2:
     * Input: arr = [7]
     * Output: 0
     * Explanation: Start index is the last index. You don't need to jump.
     *
     * Example 3:
     * Input: arr = [7,6,9,6,9,6,9,7]
     * Output: 1
     * Explanation: You can jump directly from index 0 to index 7 which is last index of the array.
     *
     * Example 4:
     * Input: arr = [6,1,9]
     * Output: 2
     *
     * Example 5:
     * Input: arr = [11,22,7,7,7,7,7,7,7,22,13]
     * Output: 3
     *
     * Constraints:
     * 1 <= arr.length <= 5 * 104
     * -108 <= arr[i] <= 108
     *
     * Hard
     */

    /**
     * 很tricky的BFS的变形题，要转好几个弯。
     *
     * 1.From a given index i, the valid next stop is the indices that have the same value as arr[i], AND i + 1, i - 1
     *   if i + 1 and i -1 are within index range. So those valid indices is the NEXT LEVEL, therefore it is converted to
     *   a shorted path problem => BFS
     * 2.However, if we jump back to an index that is previously visited, it forms a loop, we can't reach the end and will
     *   stuck in the loop, therefore, we need to remember which indices that have been visited.
     * 3."next.clear()"
     *   Theoretically, without this line, the solution should solve the problem, but on leetcode, I always got TLE
     *   without removing looked up indices by including this line. This lines mainly solves the case that there are
     *   duplicate values in arr.
     *
     *   For example : [7,7,7,7,1]:
     *   map:
     *   7 => 0- > 1 -> 2 -> 3
     *   1 => 4
     *   visited : {0}
     *
     *   After poll 0 from q, visited : {0, 1, 2, 3}, next level {1, 2, 3}. When we go to next level, value is "7" again.
     *   We are still iterating to check again and again....: since we already set indices with value "7" in visited[] when
     *   we first run into "7". So next time we get value "7", we will just repeatedly check visited[] and will not add
     *   anything to q (since those indices are already set "True"). Imagine we have, say,  50000000000 repeated "7" instead
     *   of just 4, then those checking are totally wasted time.
     *
     *   If we have arr of length n with the same value for each index, the for loop go through the "next" list actually
     *   costs O(n ^ n) (for each of the n elements, need to go through the list of length n), and it is totally unnecessary
     *   since when we do that with the first element of arr, we already set visited info for all the next of elements
     *   when we we go through the "next" list.
     *
     *   That's why. next.clear() saves us from that repeated iterating on same values. The result is that we only go to
     *   the neighbor (the same value) once then we break all the edge by using "next.clear()". So the algorithm will
     *   traverse up to N edges for j and 2N edges for (i+1, i-1).That's why time complexity is O(N) (!!!)
     *
     *   If we don't have "next.clear()", time complexity won't be O(n)
     */
    class Solution {
        public int minJumps(int[] arr) {
            int len = arr.length;
            Map<Integer, List<Integer>> map = new HashMap<>();

            for (int i = 0; i < len; i++) {
                /**
                 * learn to use this method
                 */
                map.computeIfAbsent(arr[i], x -> new ArrayList<>()).add(i);
            }

            Queue<Integer> q = new LinkedList<>();
            boolean[] visited = new boolean[len];
            q.offer(0);
            visited[0] = true;

            int res = 0;

            while (!q.isEmpty()) {
                int size = q.size();

                for (int i = 0; i < size; i++) {
                    int cur = q.poll();
                    if (cur == len - 1) return res;

                    List<Integer> next = map.get(arr[cur]);
                    /**
                     * nice trick, add cur + 1 and cur - 1 blindly here, check if it is with index range when we
                     * process it in the for loop.
                     */
                    next.add(cur + 1);
                    next.add(cur - 1);

                    for (int n : next) {
                        if (n >= 0 && n < len && !visited[n]) {
                            q.offer(n);
                            visited[n] = true;
                        }
                    }

                    /**
                     * !!!
                     */
                    next.clear();
                }

                res++;
            }

            return -1;
        }
    }
}
