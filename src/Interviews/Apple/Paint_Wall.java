package Interviews.Apple;

import java.util.*;

public class Paint_Wall {
    /**
     * We would like to paint a wall with length N. Each time we call a function paint(float i) to
     * paint a portion of the wall from i to i +1, and i could be negative. After calling a couple
     * of times for paint(), determine if the wall is completely painted (meaning 0-N is covered).
     */

    /**
     * In general, Solution 1 is more efficient if you check the wall frequently,
     * while Solution 2 may be preferable if paints occur more frequently than checks.
     */

    class PaintWallSorted_1 {
        /**
         * Solution 1: Insert and Merge Intervals (Sorted)
         * In this approach, each paint operation inserts the interval into a sorted list, merging any
         * overlapping intervals. Checking if the wall is fully painted is done by verifying if there are
         * gaps between 0 and N.
         *
         * Time Complexity (paint): O(n) due to merging.
         * Time Complexity (check): O(n) to check if fully painted.
         * Space Complexity: O(n).
         */
        private List<float[]> intervals = new ArrayList<>();

        // Paint the wall from i to i + 1
        public void paint(float i) {// insert and merge, keep the list in sorted order
            float[] newInterval = new float[]{i, i + 1};
            List<float[]> paints = new ArrayList<>();
            boolean inserted = false;

            for (float[] interval : intervals) {
                if (interval[1] < newInterval[0]) {
                    paints.add(interval);  // No overlap, before the new interval
                } else if (interval[0] > newInterval[1]) {
                    if (!inserted) {
                        paints.add(newInterval);  // Insert the new interval if not done
                        inserted = true;
                    }
                    paints.add(interval);  // Add the remaining intervals
                } else {
                    newInterval[0] = Math.min(newInterval[0], interval[0]);  // Merge overlapping intervals
                    newInterval[1] = Math.max(newInterval[1], interval[1]);
                }
            }

            if (!inserted) {
                paints.add(newInterval);  // Insert the new interval if not yet inserted
            }

            intervals = paints;
        }

        // Check if the wall from 0 to N is fully painted
        public boolean isWallFullyPainted(float N) {
            float currentEnd = 0;

            for (float[] interval : intervals) {
                if (interval[1] < 0) { // !!!Ignore intervals that are fully before 0
                    continue;
                }

                if (interval[0] > currentEnd) {// Start checking from 0 onwards
                    return false;  // There's a gap between 0 and N
                }

                currentEnd = Math.max(currentEnd, interval[1]);

                if (currentEnd >= N) {
                    return true;  // Fully painted up to N
                }
            }

            return currentEnd >= N;
        }


//        public static void main(String[] args) {
//            PaintWallSorted wall = new PaintWallSorted();
//            wall.paint(-1);
//            wall.paint(0);
//            wall.paint(1.5f);
//            wall.paint(1);
//            System.out.println(wall.isWallFullyPainted(3));  // Output: false
//            wall.paint(2.5f);
//            System.out.println(wall.isWallFullyPainted(3));  // Output: true
//        }
    }

    class PaintWallUnsorted_2 {
        /**
         * In this approach, each paint operation simply adds the interval to an unsorted list.
         * During the check, the intervals are sorted and merged to verify if the wall is fully
         * painted.
         *
         * Solution 2 (Unsorted Insert and Sort at Check):
         *
         * Time Complexity (paint): O(1) for insertion.
         * Time Complexity (check): O(n log n) for sorting + O(n) for merging and checking.
         * Space Complexity: O(n).
         */
        private List<float[]> intervals = new ArrayList<>();

        // Paint the wall from i to i + 1
        public void paint(float i) {
            intervals.add(new float[]{i, i + 1});
        }

        // Check if the wall from 0 to N is fully painted
        public boolean isWallFullyPainted(float N) {
            intervals.sort(Comparator.comparingDouble(a -> a[0])); // !!!Sort the intervals based on the start point

            float currentEnd = 0;

            for (float[] interval : intervals) {
                if (interval[1] < 0) { // !!!Ignore intervals that are fully before 0
                    continue;
                }

                if (interval[0] > currentEnd) {// Start checking from 0 onwards
                    return false;  // There's a gap between 0 and N
                }

                currentEnd = Math.max(currentEnd, interval[1]);

                if (currentEnd >= N) {
                    return true;  // Fully painted up to N
                }
            }

            return currentEnd >= N;
        }

//        public static void main(String[] args) {
//            PaintWallUnsorted wall = new PaintWallUnsorted();
//            wall.paint(-1);
//            wall.paint(0);
//            wall.paint(1.5f);
//            wall.paint(1);
//            System.out.println(wall.isWallFullyPainted(3));  // Output: false
//            wall.paint(2.5f);
//            System.out.println(wall.isWallFullyPainted(3));  // Output: true
//        }
    }
}