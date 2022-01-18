package leetcode;

import java.util.*;

public class LE_1042_Flower_Planting_With_No_Adjacent {
    /**
     * You have n gardens, labeled from 1 to n, and an array paths where paths[i] = [xi, yi] describes a bidirectional
     * path between garden xi to garden yi. In each garden, you want to plant one of 4 types of flowers.
     *
     * All gardens have at most 3 paths coming into or leaving it.
     *
     * Your task is to choose a flower type for each garden such that, for any two gardens connected by a path, they have
     * different types of flowers.
     *
     * Return any such a choice as an array answer, where answer[i] is the type of flower planted in the (i+1)th garden.
     * The flower types are denoted 1, 2, 3, or 4. It is guaranteed an answer exists.
     *
     * Example 1:
     * Input: n = 3, paths = [[1,2],[2,3],[3,1]]
     * Output: [1,2,3]
     * Explanation:
     * Gardens 1 and 2 have different types.
     * Gardens 2 and 3 have different types.
     * Gardens 3 and 1 have different types.
     * Hence, [1,2,3] is a valid answer. Other valid answers include [1,2,4], [1,4,2], and [3,2,1].
     *
     * Example 2:
     * Input: n = 4, paths = [[1,2],[3,4]]
     * Output: [1,2,1,2]
     *
     * Example 3:
     * Input: n = 4, paths = [[1,2],[2,3],[3,4],[4,1],[1,3],[2,4]]
     * Output: [1,2,3,4]
     *
     * Constraints:
     * 1 <= n <= 104
     * 0 <= paths.length <= 2 * 104
     * paths[i].length == 2
     * 1 <= xi, yi <= n
     * xi != yi
     * Every garden has at most 3 paths coming into or leaving it.
     *
     * Medium
     *
     * https://leetcode.com/problems/flower-planting-with-no-adjacent/
     */

    /**
     * Graph + Greedy
     *
     * Key:
     * "All gardens have at most 3 paths coming into or leaving it" : Because there is no node that has more than 3 neighbors,
     * always one possible color to choose.
     *
     * So we greedily paint nodes one by one. How to color? For each garden, check its neighbours first, eliminate all used
     * colors, then pick the first unused color for current garden.
     *
     * Time and Space : O(n)
     */
    class Solution {
        public int[] gardenNoAdj(int n, int[][] paths) {
            Map<Integer, List<Integer>> graph = new HashMap<>();
            int[] res = new int[n];

            /**
             * create graph
             */
            for (int[] p : paths) {
                graph.computeIfAbsent(p[0], l -> new ArrayList<>()).add(p[1]);
                graph.computeIfAbsent(p[1], l -> new ArrayList<>()).add(p[0]);
            }

            for (int i = 1; i <= n; i++) {
                List<Integer> next = graph.getOrDefault(i, new ArrayList<>());
                boolean[] colors = new boolean[4];

                /**
                 * Iterate all neighbours, set the colors that have been used as true
                 */
                for (int x : next) {
                    if (res[x - 1] == 0) continue;
                    colors[res[x - 1] - 1] = true;
                }

                /**
                 * Then iterate all colors, pick the first one that has not been used in ith garden's neighbours
                 */
                for (int j = 0; j < 4; j++) {
                    if (!colors[j]) {
                        res[i - 1] = j + 1;
                        break;//!!!
                    }
                }
            }

            return res;
        }
    }

    /**
     * same algorithm, populate map first with list. A little faster, 77.09%
     */
    class Solution1 {
        public int[] gardenNoAdj(int n, int[][] paths) {
            Map<Integer, List<Integer>> graph = new HashMap<>();
            int[] res = new int[n];

            for (int i = 1; i <= n; i++) {
                graph.put(i, new ArrayList<>());

            }
            for (int[] p : paths) {
                graph.get(p[0]).add(p[1]);
                graph.get(p[1]).add(p[0]);
            }

            for (int i = 1; i <= n; i++) {
                List<Integer> next = graph.get(i);
                boolean[] colors = new boolean[4];

                for (int x : next) {
                    if (res[x - 1] == 0) continue;
                    colors[res[x - 1] - 1] = true;
                }

                for (int j = 0; j < 4; j++) {
                    if (!colors[j]) {
                        res[i - 1] = j + 1;
                        break;
                    }
                }
            }

            return res;
        }
    }

}
