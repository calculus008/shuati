package leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LE_963_Minimum_Area_Rectangle_II {
    /**
     * Given a set of points in the xy-plane, determine the minimum area of any rectangle
     * formed from these points, with sides not necessarily parallel to the x and y axes.
     *
     * If there isn't any rectangle, return 0.
     *
     * Medium
     */

    /**
     * https://leetcode.com/problems/minimum-area-rectangle-ii/solution/
     *
     * Consider opposite points AC and BD of a rectangle ABCD. They both have the same center O,
     * which is the midpoint of AC and the midpoint of AB; and they both have the same radius
     * dist(O, A) == dist(O, B) == dist(O, C) == dist(O, D). Notice that a necessary and
     * sufficient condition to form a rectangle with two opposite pairs of points is that
     * the points must have the same center and radius.
     *
     * Time Complexity: O(N^2 * logN), where N is the length of points.
     * It can be shown that the number of pairs of points with the same classification
     * is bounded by logN
     *
     * Space Complexity: O(N).
     *
     * https://leetcode.com/problems/minimum-area-rectangle-ii/discuss/208361/JAVA-O(n2)-using-Map
     */
    class Solution1 {
        public double minAreaFreeRect(int[][] points) {
            int n = points.length;

            if (n < 4) return 0.0;

            Map<String, List<int[]>> map = new HashMap<>();

            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    long dist = getDist(points[i], points[j]);
                    long x = points[i][0] + points[j][0];
                    long y = points[i][1] + points[j][1];

                    String key = dist + "_" + x + "_" + y;

                    if (!map.containsKey(key)) {
                        map.put(key, new ArrayList<>());
                    }

                    map.get(key).add(new int[]{i, j});
                }
            }

            double res = Double.MAX_VALUE;

            for (List<int[]> list : map.values()) {
                for (int i = 0; i < list.size(); i++) {
                    for (int j = i + 1; j < list.size(); j++) {
                        int[] p1 = points[list.get(i)[0]];
                        int[] p2 = points[list.get(j)[0]];
                        int[] p3 = points[list.get(j)[1]];

                        double area = Math.sqrt(getDist(p1, p2)) * Math.sqrt(getDist(p1, p3));
                        res = Math.min(res, area);
                    }
                }
            }

            return res == Double.MAX_VALUE ? 0.0 : res;
        }

        private long getDist(int[] p1, int[] p2) {
            long dx = p1[0] - p2[0];
            long dy = p1[1] - p2[1];

            return dx * dx + dy * dy;
        }
    }
}
