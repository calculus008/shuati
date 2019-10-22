package leetcode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LE_939_Minimum_Area_Rectangle {
    /**
     * Given a set of points in the xy-plane, determine the minimum area of a rectangle
     * formed from these points, with sides parallel to the x and y axes.
     *
     * If there isn't any rectangle, return 0.
     *
     *
     * Example 1:
     * Input: [[1,1],[1,3],[3,1],[3,3],[2,2]]
     * Output: 4
     *
     * Example 2:
     * Input: [[1,1],[1,3],[3,1],[3,3],[4,1],[4,3]]
     * Output: 2
     *
     *
     * Note:
     * 1 <= points.length <= 500
     * 0 <= points[i][0] <= 40000
     * 0 <= points[i][1] <= 40000
     * All points are distinct.
     *
     * Medium
     */

    /**
     * Basically, in your approach, we are first of all finding two points which are possible diagonals of a rectangle.
     * Then, we check if we are able to find the other two diagonal points. If they exist, we found a rectangle.
     *
     * Time : O(n ^ 2)
     *
     */
    class Solution1 {
        public int minAreaRect1(int[][] points) {
            Map<Integer, Set<Integer>> map = new HashMap<>();

            for (int[] p : points) {
                if (!map.containsKey(p[0])) {
                    map.put(p[0], new HashSet<>());
                }
                map.get(p[0]).add(p[1]);
            }

            int min = Integer.MAX_VALUE;

            for (int[] p1 : points) {
                for (int[] p2 : points) {
                    if (p1[0] == p2[0] || p1[1] == p2[1]) { // if have the same x or y
                        continue;
                    }

                    if (map.get(p1[0]).contains(p2[1]) && map.get(p2[0]).contains(p1[1])) { // find other two points
                        min = Math.min(min, Math.abs(p1[0] - p2[0]) * Math.abs(p1[1] - p2[1]));
                    }
                }
            }

            return min == Integer.MAX_VALUE ? 0 : min;
        }

        public int minAreaRect2(int[][] points) {
            Set<String> hs = new HashSet<>();
            for (int[] p : points) {
                hs.add(p[0] + "#" + p[1]);
            }

            int min = Integer.MAX_VALUE;
            for (int i = 0; i < points.length; i++) {
                for (int j = i + 1; j < points.length; j++) {
                    if (points[i][0] == points[j][0] || points[i][1] == points[j][1]) {
                        continue;
                    }

                    String p1 = points[i][0] + "#" + points[j][1];
                    String p2 = points[j][0] + "#" + points[i][1];

                    if (hs.contains(p1) && hs.contains(p2)) {
                        int area = Math.abs((points[i][0] - points[j][0]) * (points[i][1] - points[j][1]));
                        min = Math.min(min, area);
                    }
                }
            }
            return min == Integer.MAX_VALUE ? 0 : min;
        }
    }

    class Solution2 {
        public int minAreaRect(int[][] points) {
            Set<Integer> pointSet = new HashSet();

            for (int[] point : points) {
                pointSet.add(40001 * point[0] + point[1]);
            }

            int ans = Integer.MAX_VALUE;

            for (int i = 0; i < points.length; ++i)
                for (int j = i + 1; j < points.length; ++j) {
                    if (points[i][0] != points[j][0] && points[i][1] != points[j][1]) {
                        if (pointSet.contains(40001 * points[i][0] + points[j][1]) &&
                                pointSet.contains(40001 * points[j][0] + points[i][1])) {
                            ans = Math.min(ans, Math.abs(points[j][0] - points[i][0]) *
                                    Math.abs(points[j][1] - points[i][1]));
                        }
                    }
                }

            return ans < Integer.MAX_VALUE ? ans : 0;
        }
    }
}
