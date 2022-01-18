package leetcode;

import java.util.*;

public class LE_2013_Detect_Squares {
    /**
     * You are given a stream of points on the X-Y plane. Design an algorithm that:
     *
     * Adds new points from the stream into a data structure. Duplicate points are allowed and should be treated as different points.
     * Given a query point, counts the number of ways to choose three points from the data structure such that the three points and
     * the query point form an axis-aligned square with positive area.
     * An axis-aligned square is a square whose edges are all the same length and are either parallel or perpendicular to the x-axis and y-axis.
     *
     * Implement the DetectSquares class:
     *
     * DetectSquares() Initializes the object with an empty data structure.
     * void add(int[] point) Adds a new point point = [x, y] to the data structure.
     * int count(int[] point) Counts the number of ways to form axis-aligned squares with point point = [x, y] as described above.
     *
     *
     * Example 1:
     * Input
     * ["DetectSquares", "add", "add", "add", "count", "count", "add", "count"]
     * [[], [[3, 10]], [[11, 2]], [[3, 2]], [[11, 10]], [[14, 8]], [[11, 2]], [[11, 10]]]
     * Output
     * [null, null, null, null, 1, 0, null, 2]
     *
     * Explanation
     * DetectSquares detectSquares = new DetectSquares();
     * detectSquares.add([3, 10]);
     * detectSquares.add([11, 2]);
     * detectSquares.add([3, 2]);
     * detectSquares.count([11, 10]); // return 1. You can choose:
     *                                //   - The first, second, and third points
     * detectSquares.count([14, 8]);  // return 0. The query point cannot form a square with any points in the data structure.
     * detectSquares.add([11, 2]);    // Adding duplicate points is allowed.
     * detectSquares.count([11, 10]); // return 2. You can choose:
     *                                //   - The first, second, and third points
     *                                //   - The first, third, and fourth points
     *
     *
     * Constraints:
     * point.length == 2
     * 0 <= x, y <= 1000
     * At most 3000 calls in total will be made to add and count.
     *
     * Medium
     *
     * https://leetcode.com/problems/detect-squares/
     */

    /**
     * HashMap + Geometry
     *
     * For problem of checking if a square or rectangle can be formed, remember, iterate all points and check if the point
     * can be the DIAGONAL point with the given point. This way, it is easy to construct the other two points.
     *
     * For a square, if given point is : {x, y}, a diagonal point is {dx, dy}, then the other two points will be :
     * {x, dy} and {dx, y}
     *
     * Similar technic is used in LE_939_Minimum_Area_Rectangle
     *
     * NOTICE:
     * Int array can not be used as key in HashMap or an element in Set,
     * arrays use the default identity-based Object.hashCode() implementation and there's no way you can override that.
     * Don't use Arrays as keys in a HashMap / HashSet! Use a Set or List instead.
     *
     * For this problem, I used List as key but it also caused problem in some test cases. So the preferred way is to
     * construct a key of String type from the array.
     */

    class DetectSquares1 {
        Map<String, Integer> count;
        /**
         * For fast iterating all points, we can also get the keyset from count hashmap, but it requires decodeing x and y
         * coordinates from the key. So speed wise, it is not efficient.
         */
        List<int[]> points;

        public DetectSquares1() {
            count = new HashMap<>();
            points = new ArrayList<>();
        }

        public void add(int[] point) {
            String key = getKey(point[0], point[1]);
            count.put(key, count.getOrDefault(key, 0) + 1);
            points.add(point);
        }

        public int count(int[] point) {
            int x = point[0];
            int y = point[1];

            int res = 0;

            for (int[] p : points) {
                int dx = p[0];
                int dy = p[1];

                if (x - dx == 0 || Math.abs(x - dx) != Math.abs(y - dy)) continue;

                String key1 = getKey(dx, y);
                String key2 = getKey(x, dy);

                int c1 = count.getOrDefault(key1, 0);
                int c2 = count.getOrDefault(key2, 0);

                /**
                 * "Duplicate points are allowed and should be treated as different points"
                 * Therefore, we need to multiply the number of points together to get the correct result.
                 */
                res += c1 * c2;
            }

            return res;
        }

        private String getKey(int x, int y) {
            return x + "#" + y;
        }
    }

    /**
     * Same algorithm, since there a given condition:
     * "0 <= x, y <= 1000"
     *
     * So we use 2D array, instead of a hashmap, to count the number of points. The index of the 2D array is the x and y
     * coordinates of a point.
     */
    class DetectSquares2 {
         int[][] count = new int[1001][1001];
         List<int[]> points = new ArrayList<>();

         public void add(int[] p) {
             count[p[0]][p[1]] += 1;
             points.add(p);
         }

         public int count(int[] p1) {
             int x = p1[0], y = p1[1], res = 0;

             for (int[] p : points) {
                 int dx = p[0], dy = p[1];

                 if (Math.abs(x-dx) == 0 || Math.abs(x-dx) != Math.abs(y-dy)) continue;

                 res += count[x][dy] * count[dx][y];
             }

             return res;
         }
    }
}
