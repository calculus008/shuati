package leetcode;

import java.util.Arrays;

public class LE_1024_Video_Stitching {
    /**
     * You are given a series of video clips from a sporting event that lasted T seconds.
     * These video clips can be overlapping with each other and have varied lengths.
     *
     * Each video clip clips[i] is an interval: it starts at time clips[i][0] and ends
     * at time clips[i][1].  We can cut these clips into segments freely: for example,
     * a clip [0, 7] can be cut into segments [0, 1] + [1, 3] + [3, 7].
     *
     * Return the minimum number of clips needed so that we can cut the clips into segments
     * that cover the entire sporting event ([0, T]).  If the task is impossible, return -1.
     *
     *
     *
     * Example 1:
     * Input: clips = [[0,2],[4,6],[8,10],[1,9],[1,5],[5,9]], T = 10
     * Output: 3
     * Explanation:
     * We take the clips [0,2], [8,10], [1,9]; a total of 3 clips.
     * Then, we can reconstruct the sporting event as follows:
     * We cut [1,9] into segments [1,2] + [2,8] + [8,9].
     * Now we have segments [0,2] + [2,8] + [8,10] which cover the sporting event [0, 10].
     *
     * Example 2:
     * Input: clips = [[0,1],[1,2]], T = 5
     * Output: -1
     * Explanation:
     * We can't cover [0,5] with only [0,1] and [0,2].
     *
     * Example 3:
     * Input: clips = [[0,1],[6,8],[0,2],[5,6],[0,4],[0,3],[6,7],[1,3],[4,7],[1,4],
     *                 [2,5],[2,6],[3,4],[4,5],[5,7],[6,9]], T = 9
     * Output: 3
     * Explanation:
     * We can take clips [0,4], [4,7], and [6,9].
     *
     * Example 4:
     * Input: clips = [[0,4],[2,8]], T = 5
     * Output: 2
     * Explanation:
     * Notice you can have extra video after the event ends.
     *
     *
     * Note:
     * 1 <= clips.length <= 100
     * 0 <= clips[i][0], clips[i][1] <= 100
     * 0 <= T <= 100
     *
     * Medium
     */

    /**
     * https://zxi.mytechroad.com/blog/leetcode/leetcode-weekly-contest-131-1021-1022-1023-1024/
     *
     * Time  : O(n * T ^ 2)
     * Space : O(T ^ 2)
     */
    public int videoStitching(int[][] clips, int T) {
        if (clips == null || clips.length == 0) return -1;

        /**
         * dp[i][j] := min clips to cover range [i, j]
         **/
        int[][] dp = new int[T + 1][T + 1];
        int n = clips.length;
        int max = n + 1;

        for (int i = 0; i < dp.length; i++) {
            /**
             * !!!
             * Can't set init value to Integer.MAX_VALUE,
             * because in the following logic, if plus 1,
             * the value will overflow to be Integer.MIN_VALUE
             */
            Arrays.fill(dp[i], max);
        }

        for (int[] clip : clips) {
            int start = clip[0];
            int end = clip[1];

            /**
             * !!!
             * clip is the current one we are working on,
             * interval between [i, j] is the final movie we are supposed to get after editing.
             */
            for (int l = 1; l <= T; l++) {
                for (int i = 0; i + l <= T; i++) {
                    int j = i + l;

                    if (end < i || j < start) {
                        /**
                         * current clip has no overlap with the final movie
                         *  i       j
                         *  |_______|
                         *             start      end
                         *              |__________|
                         * or
                         *             start      end
                         *              |__________|
                         *                             i           j
                         *                             |___________|
                         */
                        continue;
                    } else if (start <= i && j <= end) {
                        /**
                         *    start        end
                         *     |____________|
                         *       i       j
                         *       |_______|
                         *
                         */
                        dp[i][j] = 1;
                    } else if (end >= j) {
                        /**
                         *    start        end
                         *     |____________|
                         * i            j
                         * |____________|
                         *
                         */
                        dp[i][j] = Math.min(dp[i][j], dp[i][start] + 1);
                    } else if (start <= i) {
                        /**
                         *    start        end
                         *     |____________|
                         *           i            j
                         *           |____________|
                         */
                        dp[i][j] = Math.min(dp[i][j], 1 + dp[end][j]);
                    } else {
                        /**
                         *    start        end
                         *     |____________|
                         * i                       j
                         * |_______________________|
                         *
                         */
                        dp[i][j] = Math.min(dp[i][j], dp[i][start] + 1 + dp[end][j]);
                    }
                }
            }
        }

        return dp[0][T] == max ? -1 : dp[0][T];
    }
}
