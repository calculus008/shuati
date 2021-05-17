package Interviews.Facebook;

public class Find_Intersection_Of_All_Intervals {
    /**
     * Given N intervals of the form of [l, r], the task is to find the intersection of all the intervals.
     * An intersection is an interval that lies within all of the given intervals. If no such intersection
     * exists then print -1.
     *
     * Examples:
     *
     * Input: arr[] = {{1, 6}, {2, 8}, {3, 10}, {5, 8}}
     * Output: [5, 6]
     * [5, 6] is the common interval that lies in all the given intervals.
     */

    // Function to print the intersection
    static void findIntersection(int intervals[][], int N) {
        // First interval
        int l = intervals[0][0];
        int r = intervals[0][1];

        // Check rest of the intervals
        // and find the intersection
        for (int i = 1; i < N; i++) {
            // If no intersection exists
            if (intervals[i][0] > r ||
                    intervals[i][1] < l) {
                System.out.println(-1);
                return;
            }
            // Else update the intersection
            else {
                l = Math.max(l, intervals[i][0]);
                r = Math.min(r, intervals[i][1]);
            }
        }
        System.out.println("[" + l + ", " + r + "]");
    }

    // Driver code
    public static void main(String[] args) {

        int intervals[][] = {{1, 6},
                {2, 8},
                {3, 10},
                {5, 8}};
        int N = intervals.length;
        findIntersection(intervals, N);
    }
}

