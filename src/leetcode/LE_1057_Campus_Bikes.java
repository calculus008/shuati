package leetcode;

import java.util.*;

public class LE_1057_Campus_Bikes {
    /**
     * On a campus represented as a 2D grid, there are N workers and M bikes, with N <= M. Each worker and bike is a
     * 2D coordinate on this grid.
     *
     * Our goal is to assign a bike to each worker. Among the available bikes and workers, we choose the (worker, bike)
     * pair with the shortest Manhattan distance between each other, and assign the bike to that worker. (If there are
     * multiple (worker, bike) pairs with the same shortest Manhattan distance, we choose the pair with the smallest
     * worker index; if there are multiple ways to do that, we choose the pair with the smallest bike index). We repeat
     * this process until there are no available workers.
     *
     * The Manhattan distance between two points p1 and p2 is Manhattan(p1, p2) = |p1.x - p2.x| + |p1.y - p2.y|.
     *
     * Return a vector ans of length N, where ans[i] is the index (0-indexed) of the bike that the i-th worker is assigned to.
     *
     * Example 1:
     * Input: workers = [[0,0],[2,1]], bikes = [[1,2],[3,3]]
     * Output: [1,0]
     * Explanation:
     * Worker 1 grabs Bike 0 as they are closest (without ties), and Worker 0 is assigned Bike 1. So the output is [1, 0].
     *
     * Example 2:
     * Input: workers = [[0,0],[1,1],[2,0]], bikes = [[1,0],[2,2],[2,1]]
     * Output: [0,2,1]
     * Explanation:
     * Worker 0 grabs Bike 0 at first. Worker 1 and Worker 2 share the same distance to Bike 2, thus Worker 1 is assigned
     * to Bike 2, and Worker 2 will take Bike 1. So the output is [0,2,1].
     *
     * Note:
     * 0 <= workers[i][j], bikes[i][j] < 1000
     * All worker and bike locations are distinct.
     * 1 <= workers.length <= bikes.length <= 1000
     *
     * Medium
     */

    /**
     * Solution1
     * Bucket Sort
     *
     * Very important implicit conditions:
     * 1."1 <= workers.length <= bikes.length <= 1000" -> worker id and bike id are in range [1, 1000]
     * 2."0 <= workers[i][j], bikes[i][j] < 1000" -> x, y co-ordinations are both in range (0, 1000]
     *
     * Therefore, the range of distance is [0, 2000],  which is much lower than the # of pairs, which is 1e6 (1000 * 1000).
     * It's a good time to use bucket sort. Basically, it's to put each pair into the bucket representing its distance.
     * Eventually, we can loop thru each bucket from lower distance.
     *
     * Therefore, it's O(M * N) time and O(M * N) space.
     *
     * Solution2
     * PriorityQueue
     *
     * A straight forward solution is to use a PriorityQueue of bike and worker pairs. (The heap order should be Distance ASC,
     * WorkerIndex ASC, Bike ASC). But each insertion and poll on pq will require log(size of pq), therefore time wise, it
     * is more expensive than the bucket sort solution : O(MN log(MN))
     */
    class Solution1 {
        public int[] assignBikes(int[][] workers, int[][] bikes) {
            /**
             * Buckets, defined as array of list whose element is int array
             * key: distance value => list of {worker_id, bike_id} pair whose distance is the value of the key
             *
             * !!!
             * Notice how it should be declared.
             * "List<int[]>[] buckets = new List<int[]>[2000];" is WRONG:
             * 1.After new, need to use implementation of list - ArrayList, not "List"
             * 2.Do not include "<int[]>" or "<>" after ArrayList, use generic form.
             */
            List<int[]>[] buckets = new ArrayList[2000];
            int[] res = new int[workers.length];
            Arrays.fill(res, -1);

            /**
             * A great advantage of iterating workers/bikes and add to list is that we start from id 0 and go incrementally
             * , therefore the pair we add to list already satisfy that smaller ids for workers and bikes are in front,
             * no extra sorting or compare logic required.
             */
            for (int i = 0; i < workers.length; i++) {
                for (int j = 0; j < bikes.length; j++) {
                    int dist = getDistance(workers[i], bikes[j]);
                    if (buckets[dist] == null) {
                        buckets[dist] = new ArrayList<>();
                    }
                    buckets[dist].add(new int[]{i, j});
                }
            }

            boolean[] bikeVisited = new boolean[bikes.length];

            for (List<int[]> list : buckets) {
                if (list == null) continue;

                for (int[] pair : list) {
                    int w = pair[0];
                    int b = pair[1];

                    /**
                     * if bike is already assigned or the worker already has a bike, continue
                     */
                    if (bikeVisited[b] || res[w] != -1) continue;

                    res[w] = b;
                    bikeVisited[b] = true;
                }
            }

            return res;
        }

        private int getDistance(int[] p1, int[] p2) {
            return Math.abs(p1[0] - p2[0]) + Math.abs(p1[1] - p2[1]);
        }
    }

    class Solution2 {
        public int[] assignBikes(int[][] workers, int[][] bikes) {
            int l1 = workers.length, l2 = bikes.length;
            int[] wo = new int[l1], bi = new int[l2];
            Arrays.fill(wo, -1);
            Arrays.fill(bi, -1);

            PriorityQueue<int[]> pq = new PriorityQueue<int[]>((a, b) -> {
                int comp = Integer.compare(a[0], b[0]);
                if (comp == 0) {
                    if (a[1] == b[1]) {
                        return Integer.compare(a[2], b[2]);
                    }

                    return Integer.compare(a[1], b[1]);
                }

                return comp;
            });

            for (int i = 0; i < l1; i++) {
                for (int j = 0; j < l2; j++) {
                    int[] worker = workers[i];
                    int[] bike = bikes[j];
                    int dist = Math.abs(worker[0] - bike[0]) + Math.abs(worker[1] - bike[1]);
                    pq.offer(new int[]{dist, i, j});
                }
            }

            int assigned = 0;
            while (!pq.isEmpty() && assigned < l1) {
                int[] s = pq.poll();
                if (wo[s[1]] == -1 && bi[s[2]] == -1) {
                    wo[s[1]] = s[2];
                    bi[s[2]] = s[1];
                    assigned++;
                }
            }
            return wo;
        }
    }
}
