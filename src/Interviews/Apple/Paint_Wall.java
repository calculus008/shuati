package Interviews.Apple;

import java.util.*;

public class Paint_Wall {
    /**
     * We would like to paint a wall with length N. Each time we call a function paint(float i) to
     * paint a portion of the wall from i to i +1, and i could be negative. After calling a couple
     * of times for paint(), determine if the wall is completely painted (meaning 0-N is covered).
     */


    /**
     * 1.Track Coverage: Use a data structure to track all painted intervals. For simplicity, a TreeSet or
     * a list of intervals can be used to merge overlapping or adjacent intervals.
     * 2.Check Full Coverage: After processing all paint calls, check if the interval [0, N] is fully covered.
     */

    public class WallPainter {
        private List<float[]> intervals = new ArrayList<>();

        // Method to simulate the painting of an interval
        public void paint(float i) {
            float[] newInterval = new float[]{i, i + 1};
            intervals = insertNewInterval(intervals.toArray(new float[intervals.size()][2]), newInterval);
        }

        // Logic to insert a new interval and merge it with overlapping intervals
        public List<float[]> insertNewInterval(float[][] intervals, float[] newInterval) {
            List<float[]> res = new ArrayList<>();
            int n = intervals.length;
            int i = 0;

            // Add all intervals before the new interval
            while (i < n && newInterval[0] > intervals[i][1]) {
                res.add(intervals[i]);
                i++;
            }

            // Merge overlapping intervals
            while (i < n && newInterval[1] >= intervals[i][0]) {
                newInterval[0] = Math.min(newInterval[0], intervals[i][0]);
                newInterval[1] = Math.max(newInterval[1], intervals[i][1]);
                i++;
            }

            // Add the new merged interval
            res.add(newInterval);

            // Add remaining intervals
            while (i < n) {
                res.add(intervals[i]);
                i++;
            }

            return res;
        }

        // Check if the wall from 0 to N is fully painted
        public boolean isWallFullyPainted(int N) {
            float currentEnd = 0;
            for (float[] interval : intervals) {
                if (interval[0] > currentEnd) {
                    return false;
                }
                currentEnd = Math.max(currentEnd, interval[1]);
                if (currentEnd >= N) {
                    return true;
                }
            }
            return currentEnd >= N;
        }

//        public static void main(String[] args) {
//            WallPainter painter = new WallPainter();
//
//            painter.paint(-1);  // Paints from -1 to 0
//            painter.paint(0);   // Paints from 0 to 1
//            painter.paint(1);   // Paints from 1 to 2
//            painter.paint(1.5f);   // Paints from 1.5 to 2.5
//
//            // Check if the wall from 0 to 3 is fully painted
//            System.out.println(painter.isWallFullyPainted(3));  // Output: fa
//        }
    }
}