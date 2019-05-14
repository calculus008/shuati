package Interviews.Linkedin;

import java.util.Arrays;

public class Simple_Queries_With_Two_Arrays {
    /**
     * https://www.geeksforgeeks.org/element-1st-array-count-elements-less-equal-2nd-array/
     *
     * Given two unsorted arrays arr1[] and arr2[]. They may contain duplicates.
     * For each element in arr1[] count elements less than or equal to it in array arr2[].
     *
     * Examples:
     * Input : arr1[] = [1, 2, 3, 4, 7, 9]
     *         arr2[] = [0, 1, 2, 1, 1, 4]
     * Output : [4, 5, 5, 6, 6, 6]
     *
     * Input : arr1[] = [5, 10, 2, 6, 1, 8, 6, 12]
     *         arr2[] = [6, 5, 11, 4, 2, 3, 7]
     * Output : [4, 6, 1, 5, 0, 6, 5, 7]
     */

    /**
     * Efficient Approach
     * Sort the elements of 2nd array, i.e., array arr2[]. Then perform a modified binary
     * search on array arr2[]. For each element x of array arr1[], find the last index of
     * the largest element smaller than or equal to x in sorted array arr2[].
     *
     * Time Complexity: O(mlogn + nlogn)
     */

    /**
     * Refer to Huahua's binary search :
     * https://www.youtube.com/watch?v=v57lNF2mb_s&t=802s
     *
     * We actually want to find upper/lower bound using binary search
     * Very important!!!
     *
     * Upper Bound/Lower Bound
     * Find the smallest index to satisfy g(index).
     *
     * lower bound : first index of i, such that A[i] >= x
     * upper bound : first index of i, such that A[i] > x
     *
     * Example :
     * A = [1, 2, 2, 2, 4, 4, 5]
     * idx  0  1  2  3  4  5  6
     *
     * lower_bound(A, 2) = 1
     * lower_bound(A, 3) = 4 (3 does not exist in A)
     *
     * upper_bound(A, 2) = 4
     * upper_bound(A, 5) = 7 (5 does not exist in A)
     *
     * Key is that x may not exist in A!!!
     *
     * That is why we can't use binary search used from LE_34_Search_For_A_Range,
     * since it only finds elements that exist in array, here elements in arr1
     * may not exit in arr2.
     *
     */
    public static int binary_search(int arr[], int l, int h, int x) {
        while (l <= h) {
            int mid = (l + h) / 2;

            // if 'x' is greater than or equal to arr[mid],
            // then search in arr[mid+1...h]
            if (arr[mid] <= x) {
                l = mid + 1;
            } else {
                h = mid - 1;
            }
        }

        // required index
        return h;
    }


    public static int upper_bound(int A[], int target) {
        int l = 0;
        int r = A.length;

        while (l < r) {
            int mid = l + (r - l) / 2;

            if (A[mid] > target) { //g(mid)
                r = mid;
            } else {
                l = mid + 1;
            }
        }

        return l;
    }

    public static int lower_bound(int A[], int target) {
        int l = 0;
        int r = A.length;

        while (l < r) {
            int mid = l + (r - l) / 2;

            if (A[mid] >= target) { //g(mid)
                r = mid;
            } else {
                l = mid + 1;
            }
        }

        return l;
    }


    // method to count for each element in 1st array,
    // elements less than or equal to it in 2nd array
    public static void countEleLessThanOrEqual(int arr1[], int arr2[]) {
        Arrays.sort(arr2);

        // for each element of 1st array
        for (int i = 0; i < arr1.length; i++) {
            int index = upper_bound(arr2, arr1[i]);
            System.out.print(index + " ");
        }
    }

    public static void countEleBiggerThan(int arr1[], int arr2[]) {
        Arrays.sort(arr2);

        // for each element of 1st array
        for (int i = 0; i < arr1.length; i++) {
            int index = upper_bound(arr2, arr1[i]);
            System.out.print((arr1.length - 1 - index + 1) + " ");
        }
    }

    public static void countEleBiggerThanOrEqual(int arr1[], int arr2[]) {
        Arrays.sort(arr2);

        // for each element of 1st array
        for (int i = 0; i < arr1.length; i++) {
            int index = lower_bound(arr2, arr1[i]);
            System.out.print((arr1.length - 1 - index + 1) + " ");
        }
    }

    // Driver method
    public static void main(String[] args) {
        int arr1[] = {1, 2, 3, 4, 7, 9};
        int arr2[] = {0, 1, 2, 1, 1, 4};// {0, 1, 1, 1, 2, 4}

        countEleLessThanOrEqual(arr1, arr2);

        System.out.println("");

        countEleBiggerThan(arr1, arr2);

        System.out.println("");

        countEleBiggerThanOrEqual(arr1, arr2);
    }
}
