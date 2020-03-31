package leetcode;

import java.util.LinkedList;
import java.util.Queue;

public class LE_1298_Maximum_Candies_You_Can_Get_From_Boxes {
    /**
     * Given n boxes, each box is given in the format [status, candies, keys, containedBoxes] where:
     *
     * status[i]: an integer which is 1 if box[i] is open and 0 if box[i] is closed.
     * candies[i]: an integer representing the number of candies in box[i].
     * keys[i]: an array contains the indices of the boxes you can open with the key in box[i].
     * containedBoxes[i]: an array contains the indices of the boxes found in box[i].
     * You will start with some boxes given in initialBoxes array. You can take all the candies
     * in any open box and you can use the keys in it to open new boxes and you also can use the
     * boxes you find in it.
     *
     * Return the maximum number of candies you can get following the rules above.
     *
     * Example 1:
     * Input: status = [1,0,1,0], candies = [7,5,4,100], keys = [[],[],[1],[]], containedBoxes = [[1,2],[3],[],[]], initialBoxes = [0]
     * Output: 16
     * Explanation: You will be initially given box 0. You will find 7 candies in it and boxes 1 and 2. Box 1 is closed and you don't have a key for it so you will open box 2. You will find 4 candies and a key to box 1 in box 2.
     * In box 1, you will find 5 candies and box 3 but you will not find a key to box 3 so box 3 will remain closed.
     * Total number of candies collected = 7 + 4 + 5 = 16 candy.
     *
     * Example 2:
     * Input: status = [1,0,0,0,0,0], candies = [1,1,1,1,1,1], keys = [[1,2,3,4,5],[],[],[],[],[]], containedBoxes = [[1,2,3,4,5],[],[],[],[],[]], initialBoxes = [0]
     * Output: 6
     * Explanation: You have initially box 0. Opening it you can find boxes 1,2,3,4 and 5 and their keys. The total number of candies will be 6.
     *
     * Example 3:
     * Input: status = [1,1,1], candies = [100,1,100], keys = [[],[0,2],[]], containedBoxes = [[],[],[]], initialBoxes = [1]
     * Output: 1
     *
     * Example 4:
     * Input: status = [1], candies = [100], keys = [[]], containedBoxes = [[]], initialBoxes = []
     * Output: 0
     *
     * Example 5:
     * Input: status = [1,1,1], candies = [2,3,2], keys = [[],[],[]], containedBoxes = [[],[],[]], initialBoxes = [2,1,0]
     * Output: 7
     *
     * Constraints:
     * 1 <= status.length <= 1000
     * status.length == candies.length == keys.length == containedBoxes.length == n
     * status[i] is 0 or 1.
     * 1 <= candies[i] <= 1000
     * 0 <= keys[i].length <= status.length
     * 0 <= keys[i][j] < status.length
     * All values in keys[i] are unique.
     * 0 <= containedBoxes[i].length <= status.length
     * 0 <= containedBoxes[i][j] < status.length
     * All values in containedBoxes[i] are unique.
     * Each box is contained in one box at most.
     * 0 <= initialBoxes.length <= status.length
     * 0 <= initialBoxes[i] < status.length
     *
     * Hard
     */

    /**
     * Delayed processing
     * We process using queue, but for boxes that we don't have keys, the process has to be delayed
     * only when we have the key.
     *
     * So we have two states for boxes:
     *   found   : we have the box but no key
     *   visited : we have key, box is opened and candies are counted
     *
     * Two cases we can't process:
     *   Have a box but no key
     *   Have a key but no box
     *
     * When we find a box in side a box, we add it to found, and put int in queue.
     *
     * When we get a key, modify status of the box to open, if it's in found, we put in queue, otherwise not in queue.
     *
     * Box waits for key
     */
    class Solution {
        public int maxCandies(int[] status, int[] candies, int[][] keys, int[][] containedBoxes, int[] initialBoxes) {
            int n = status.length;//number of boxes

            boolean[] visited = new boolean[n];
            boolean[] found = new boolean[n];

            Queue<Integer> q = new LinkedList<>();

            for (int i : initialBoxes) {
                q.offer(i);
                found[i] = true;
            }

            int res = 0;
            while (!q.isEmpty()) {
                int i = q.poll();

                if (status[i] == 1 && !visited[i]) {
                    res += candies[i];
                    visited[i] = true;

                    for (int key : keys[i]) {
                        status[key] = 1;

                        /**
                         * !!!
                         * Must do found[key] check here. We can only open the boxes
                         * that are contained in current box. For example:
                         *
                         * input:
                         * [1,1,1]
                         * [100,1,100]
                         * [[],[0,2],[]]
                         * [[],[],[]]
                         * [1]
                         *
                         * All 3 boxes are in open state. But, we are only given gox 1,
                         * box 1 does not contain any other boxes, therefore, we can only
                         * get the one candy in box 1. Here, without checking "found[key]",
                         * we will add 200 (100 each in box 0 and box 2) to the result,
                         * which is wrong.
                         */
                        if (found[key]) {
                            q.offer(key);
                        }
                    }

                    for (int box : containedBoxes[i]) {
                        found[box] = true;
                        /**
                         * 只有包含在现在的盒子里的盒子才对我们有意义
                         */
                        q.offer(box);
                    }
                }
            }

            return res;
        }
    }

    /**
     * 变形题
     *
     * 有一堆房间， 有的是互相有门的，有的没有， 然后一些房间有特定的KEY TO OTHER ROOMS，
     * can you reach room Y from room X。比如说 从room1开始，ROOM1 有KEY{2,3}，
     * 我就能开2,3 ROOM的门，当然也要看ROOM1 和这些ROOMS 有没有connection。
     */
    class Solution1 {
        public boolean maxCandies(int[] status, int[][] keys, int[][] connections, int start, int destination) {
            int n = status.length;//number of boxes

            boolean[] visited = new boolean[n];
            boolean[] found = new boolean[n];

            Queue<Integer> q = new LinkedList<>();

            q.offer(start);
            found[start] = true;

            while (!q.isEmpty()) {
                int i = q.poll();

                if (status[i] == 1 && !visited[i]) {
                    if (i == destination) return true;

                    visited[i] = true;

                    for (int key : keys[i]) {
                        status[key] = 1;

                        if (found[key]) {
                            q.offer(key);
                        }
                    }

                    for (int room : connections[i]) {
                        found[room] = true;
                        q.offer(room);
                    }
                }
            }

            return false;
        }
    }
}
