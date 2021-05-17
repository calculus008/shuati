package Interviews.Amazon;

import java.util.Arrays;
import java.util.Scanner;

public class Query_Min_In_Range_Of_Unsorted_Array {
    static Scanner in = new Scanner(System.in);

    private static int getMinOverRange(int[] segmentTree, int qs, int qe, int s, int e, int pos) {
        if (qs <= s && qe >= e) {
            return segmentTree[pos];
        }

        if (qs > e || s > qe) {
            return Integer.MAX_VALUE;
        }

        int mid = s + (e - s) / 2;

        return Math.min(getMinOverRange(segmentTree, qs, qe, s, mid, 2 * pos + 1),
                getMinOverRange(segmentTree, qs, qe, mid + 1, e, 2 * pos + 2));
    }

    private static void buildSegmentTree(int[] a, int[] segmentTree, int s, int e, int pos) {
        if (e == s) {
            segmentTree[pos] = a[s];
            return;
        }

        int mid = s + (e - s) / 2;

        buildSegmentTree(a, segmentTree, s, mid, 2 * pos + 1);
        buildSegmentTree(a, segmentTree, mid + 1, e, 2 * pos + 2);

        segmentTree[pos] = Math.min(segmentTree[2 * pos + 1], segmentTree[2 * pos + 2]);
    }

    public static void main(String[] args) {
//        final int n = in.nextInt();
//        int[] a = new int[n];
//
//        for (int i = 0; i < n; i++) {
//            a[i] = in.nextInt();
//        }

        int a[] = {1, 3, 2, 7, 9, 11};
        int n = a.length;

        int x = (int) Math.pow(2, Math.ceil(Math.log10(n) / Math.log10(2)));
        int sizeOfSegmentTree = 2 * x - 1;

        int[] segmentTree = new int[sizeOfSegmentTree];
        buildSegmentTree(a, segmentTree, 0, n - 1, 0);


//        final int q = in.nextInt();
        int q = 3;
        for (int i = 0; i < n; i++) {
            int minOverRange = getMinOverRange(segmentTree, i, i + q - 1, 0, n - 1, 0);
            System.out.println(minOverRange);
        }
    }
}

