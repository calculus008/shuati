package leetcode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LE_554_Brick_Wall {
    /**
     * There is a brick wall in front of you. The wall is rectangular and has several rows of bricks.
     * The bricks have the same height but different width. You want to draw a vertical line from the
     * top to the bottom and cross the least bricks.
     *
     * The brick wall is represented by a list of rows. Each row is a list of integers representing
     * the width of each brick in this row from left to right.
     *
     * If your line go through the edge of a brick, then the brick is not considered as crossed.
     * You need to find out how to draw the line to cross the least bricks and return the number
     * of crossed bricks.
     *
     * You cannot draw a line just along one of the two vertical edges of the wall, in which case
     * the line will obviously cross no bricks.
     *
     * Example:
     *
     * Input: [[1,2,2,1],
     *         [3,1,2],
     *         [1,3,2],
     *         [2,4],
     *         [3,1,2],
     *         [1,3,1,1]]
     *
     * Output: 2
     *
     * Medium
     */

    /**
     * Use hashmap to record the edge positions and how many rows have edge at the
     * position, keep updating the max number of rows that have edge, result is
     * total number of rows - max
     */
    class Solution {
        public int leastBricks(List<List<Integer>> wall) {
            Map<Integer, Integer> map = new HashMap<>();
            int numberOfRows = wall.size();

            int max = 0;
            for (List<Integer> row : wall) {
                int pre = 0;
                for (int i = 0; i < row.size() - 1; i++) {
                    int n = row.get(i);
                    int cur = pre + n;
                    map.put(cur, map.getOrDefault(cur, 0) + 1);
                    max = Math.max(max, map.get(cur));
                    pre = cur;
                }
            }

            return numberOfRows - max;
        }
    }
}
