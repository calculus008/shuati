package Interviews;

public class BinarySearchTest {
    private static int findIndex1(int[] A, int target) {
        int start = 0, end = A.length - 1;
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            if (A[mid] < target) {
                start = mid;
            } else if (A[mid] > target) {
                end = mid;
            } else {
                end = mid;
            }
        }

        if (A[start] >= target) {
            return start;
        }
        if (A[end] >= target) {
            return end;
        }
        return A.length;
    }

    private static int findIndex2(int[] arr, int x) {
        int l = 0;
        int r = arr.length;

        while (l < r) {
            int m = l + (r - l) / 2;
            if (arr[m] >= x) {
                r = m;
            } else {
                l = m + 1;
            }
        }

        return l;
    }
    public static void main(String[] args) {
        int[] input = {0,1,1,1,2,3,6,7,8,9};
        int target = 4;

        int idx = findIndex1(input, target);

        System.out.println("idx="+idx);

        idx = findIndex2(input, target);

        System.out.println("idx="+idx);

    }
}
