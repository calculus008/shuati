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
     *   if i + 1 and i -1 are with index range. So those valid indices is the NEXT LEVEL, therefore it is converted to
     *   a shorted distance problem => BFS
     * 2.However, if we jump back to an index that is previously visited, it forms a loop, we can't reach the end and will
     *   stuck in the loop, therefore, we need to remember which indices that have been visited.
     * 3."next.clear()" is a magic! I used the same BFS and always got TLE without removing looked up indices.
     *   This method mainly solves the problem when there are duplicate values in arr, for example [7,7,7,7,1]:
     *   map:
     *   7 => 0- > 1 -> 2 -> 3
     *   1 => 4
     *   visited : {0}
     *
     *   After poll 0 from q, visited : {0, 1, 2, 3}, next level {1, 2, 3}. When we go to next level, value is "7" again.
     *   We are still iterating to check again and again that's why. next.clear() saves us from that repeated iterating on same values when n is large
     *
     *   In the case where each index has the same value, I only go to the neighbor (the same value) once then I break all the edge by using: next.clear();.
     *   So the algorithm will traverse up to N edges for j and 2N edges for (i+1, i-1).
     *   That's why time complexity is O(N)
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
