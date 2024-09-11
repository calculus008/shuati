package leetcode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import common.Point;

public class LE_149_Max_Points_On_A_Line {
    /**
     * Given n points on a 2D plane, find the maximum number of points that lie on the same straight line.
     *
     * Hard
     *
     * https://leetcode.com/problems/max-points-on-a-line
     */

    /**
     * https://github.com/anmingyu11/AlgorithmsUnion/blob/master/LeetCode/src/_java/_0149MaxPointsOnALine.java
     * <p>
     * http://zxi.mytechroad.com/blog/geometry/leetcode-149-max-points-on-a-line/
     * <p>
     * Time  : O(n ^ 2)
     * Space : O(n)
     */

    public class Solution1 {
        public int maxPoints(Point[] points) {
            /**
             遍历每个点，看它和后面的每个点构成的直线上有多少个点
             对每个点建立map，斜率是key
             斜率要用分数的形式，不要用double的形式存
             计算分数时先求分子分母的最大公约数gcd，再都除以gcd
             重合的点特殊处理

             Since we use String to represent slop and use as key in map,
             we avoid all the issues of calculating slop.
             */
            int l = points.length;
            if (l == 0) return 0;
            if (l <= 2) return l;//!!!

            int res = 0;
            for (int i = 0; i < l - 1; i++) {
                Map<String, Integer> map = new HashMap<>();
                int overlap = 0;
                int lineMax = 0;

                for (int j = i + 1; j < l; j++) {
                    int x = points[i].x - points[j].x;
                    int y = points[i].y - points[j].y;
                    if (x == 0 && y == 0) {
                        overlap++;
                        continue;
                    }

                    int gcd = generateGcd(x, y);
                    x /= gcd;
                    y /= gcd;

                    // 用string来存储斜率
                    String slope = String.valueOf(x) + String.valueOf(y);
                    int count = map.getOrDefault(slope, 0) + 1;
                    map.put(slope, count);
                    lineMax = Math.max(lineMax, count);
                }
                res = Math.max(res, lineMax + overlap + 1);
            }
            return res;
        }

        public int generateGcd(int x, int y) {
            if (y == 0) return x;
            return generateGcd(y, x % y);
        }
    }

    class Solution2 {
        public int maxPoints(int[][] points) {
            int n = points.length;
            if (n < 2) return n;

            Set<String> set = new HashSet<>();
            int max = 1;

            for (int i = 0; i < n && !set.contains(points[i][0] + "-" + points[i][1]); i++) {
                int[] a = points[i];
                int same = 0;
                Map<Double, Integer> map = new HashMap<>();
                int localMax = 1;

                for (int j = i + 1; j < n; j++) {
                    if (isSame(a, points[j])) {
                        same++;
                        continue;
                    }

                    double slope = getSlope(a, points[j]);
                    map.put(slope, map.getOrDefault(slope, 1) + 1);
                    localMax = Math.max(localMax, map.get(slope));
                }

                set.add(a[0] + "-" + a[1]);
                max = Math.max(max, localMax + same);
            }

            return max;
        }

        private boolean isSame(int[] a, int[] b) {
            return a[0] == b[0] && a[1] == b[1];
        }

        private double getSlope(int[] a, int[] b) {
            if (a[0] == b[0]) return Double.MAX_VALUE;
            if (a[1] == b[1]) return 0;
            return ((double) a[0] - b[0]) / ((double) a[1] - b[1]);
        }
    }
}
