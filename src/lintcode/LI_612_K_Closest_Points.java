package lintcode;

import common.Point;

import java.util.PriorityQueue;

/**
 * Created by yuank on 9/1/18.
 */
public class LI_612_K_Closest_Points {
    /**
         Given some points and a point origin in two dimensional space,
         find k points out of the some points which are nearest to origin.
         Return these points sorted by distance, if they are same with distance,
         sorted by x-axis, otherwise sorted by y-axis.

         Example
         Given points = [[4,6],[4,7],[4,4],[2,5],[1,1]], origin = [0, 0], k = 3
         return [[1,1],[2,5],[4,4]]

         Medium
     */

    //Time : O(nlogk), Space : O(n)
    public Point[] kClosest(Point[] points, Point origin, int k) {
        /**
         * !!!
         * define a max heap of size k
         */
        PriorityQueue<Point> pq = new PriorityQueue<>(k, (a, b) -> {
            int dist = Long.compare(distance(b, origin), distance(a, origin));
            if (dist == 0) {
                dist = b.x - a.x;
            }
            if (dist == 0) {
                dist = b.y - a.y;
            }
            return dist;
        });

        for (Point p : points) {
            pq.offer(p);
            if (pq.size() > k) {
                pq.poll();
            }
        }

        /**
         * !!!
         * sorted by distance, res[0] is the closest
         */
        Point[] res = new Point[k];
        int i = k - 1;
        while (!pq.isEmpty()) {
            res[i--] = pq.poll();
        }

        return res;
    }

    private long distance(Point a, Point b) {
        return (a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y);
    }
}
