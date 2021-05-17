package Interviews.Amazon;

import java.util.Arrays;

public class Minimum_Radius_Enclosing_K_Points {
    /**
     * https://www.geeksforgeeks.org/find-minimum-radius-atleast-k-point-lie-inside-circle/
     *
     * Given a positive integer K, a circle center at (0, 0) and coordinates of some points.
     * The task is to find minimum radius of the circle so that at-least k points lie inside
     * the circle. Output the square of the minimum radius.
     *
     * Examples:
     *
     * Input : (1, 1), (-1, -1), (1, -1),
     *          k = 3
     * Output : 2
     * We need a circle of radius at least 2
     * to include 3 points.
     *
     *
     * Input : (1, 1), (0, 1), (1, -1),
     *          k = 2
     * Output : 1
     * We need a circle of radius at least 1
     * to include 2 points. The circle around
     * (0, 0) of radius 1 would include (1, 1)
     * and (0, 1).
     */

    /**
     * The idea is to find square of Euclidean Distance of each point from origin (0, 0).
     * Now, sort these distance in increasing order. Now the kth element of distance is
     * the required minimum radius.
     */

    /**
     * Return minumum distance required so that aleast k point lie inside the circle.
     **/
    static int minRadius(int k, int[] x, int[] y, int n) {
        int[] dis = new int[n];

        /**
         * Finding distance between of each point from origin
         **/
        for (int i = 0; i < n; i++) {
            dis[i] = x[i] * x[i] + y[i] * y[i];
        }

        Arrays.sort(dis);

        return dis[k - 1];
    }

    public static void main(String[] args) {
        int k = 3;
        int[] x = {1, -1, 1};
        int[] y = {1, -1, -1};
        int n = x.length;

        System.out.println(minRadius(k, x, y, n));

    }
}
