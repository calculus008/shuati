package Interviews.Amazon;

import java.util.Arrays;

public class Min_In_Sliding_Window_For_Unsorted_Array_1 {
    static int segmentTree[]; //array to store segment tree

    private static int getMid(int s, int e) {
        return s + (e - s) / 2;
    }

    private static int queryHelper(int start, int end, int qs, int qe, int index) {
        if (qs <= start && qe >= end) {
            return segmentTree[index];
        }

        if (end < qs || start > qe) {
            return Integer.MAX_VALUE;
        }

        int mid = getMid(start, end);

        return Math.min(queryHelper(start, mid, qs, qe, 2 * index + 1),
                        queryHelper(mid + 1, end, qs, qe, 2 * index + 2));
    }

    public static int getMinInWindow(int n, int qs, int qe) {
        // Check for erroneous input values
        if (qs < 0 || qe > n - 1 || qs > qe) {
            System.out.println("Invalid Input");
            return -1;
        }

        return queryHelper(0, n - 1, qs, qe, 0);
    }

    private static int buildHelper(int arr[], int start, int end, int idx) {
        if (start == end) {
            segmentTree[idx] = arr[start];
            return arr[start];
        }

        int mid = getMid(start, end);
        segmentTree[idx] = Math.min(buildHelper(arr, start, mid, idx * 2 + 1),
                                    buildHelper(arr, mid + 1, end, idx * 2 + 2));

        return segmentTree[idx];
    }

    public static void buildSegmentTree(int arr[], int n) {
        int x = (int) (Math.ceil(Math.log(n) / Math.log(2)));

        System.out.println("x="+x);
        int max_size = 2 * (int) Math.pow(2, x) - 1;
        segmentTree = new int[max_size];

        buildHelper(arr, 0, n - 1, 0);
    }

    // Driver program to test above functions
    public static void main(String args[]) {
        int arr[] = {1, 3, 2, 7, 9, 11};
        int n = arr.length;

        /** Build segment tree from given array **/
        buildSegmentTree(arr, n);

        System.out.println("Segment Tree: " + Arrays.toString(segmentTree));

        /**
         * Test single query query
         */
        int qs = 1;  // Starting index of query range
        int qe = 5;  // Ending index of query range

        // Print minimum value in arr[qs..qe]
        System.out.println("Minimum of values in range [" + qs + ", "
                + qe + "] is = " + getMinInWindow(n, qs, qe));

        /**
         * Test sliding window min
         */
        int windowSize = 3;
        int[] res = new int[arr.length - windowSize + 1];
        for (int i = 0; i < arr.length - windowSize + 1; i++) {
            int end = i + windowSize - 1;
            res[i] = getMinInWindow(arr.length, i, end);
            System.out.println("Range : " + i + ", " + end + ", res : " + res[i]);
        }

        System.out.println(Arrays.toString(res));
    }
}
