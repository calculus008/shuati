package Interviews.Servicenow;

public class Find_A_Fixed_Point_With_Duplicates {
    /**
     * Given an array of n integers sorted in ascending order, write a function that
     * returns a Fixed Point in the array, if there is any Fixed Point present in array,
     * else returns -1. Fixed Point in an array is an index i such that arr[i] is equal
     * to i. Note that integers in array can be negative.
     * <p>
     * Examples:
     * <p>
     * Input: arr[] = {-10, -5, 0, 3, 7}
     * Output: 3  // arr[3] == 3
     * <p>
     * Input: arr[] = {-10, -5, 2, 2, 2, 3, 4, 7, 9, 12, 13}
     * Output: 2  // arr[2] == 2
     * <p>
     * Input: arr[] = {-10, -5, 3, 4, 7, 9}
     * Output: -1  // No Fixed Point
     * <p>
     * https://www.geeksforgeeks.org/find-fixed-point-value-equal-index-given-array-duplicates-allowed/
     */

    /**
     * If elements are not distinct, then we see arr[mid] < mid, we cannot conclude which side
     * the fixed is on. It could be on left side or on the right side.
     *
     * We know for sure that since arr[5] = 3, arr[4] couldn't be magic index because arr[4]
     * must be less than or equal to arr[5] (the array is Sorted).
     *
     * So, the general pattern of our search would be:
     *
     *  Left Side: start = start, end = min(arr[m], m - 1)
     *
     *  Right Side: start = max(arr[m], m + 1), end = end
     */
    static int magicIndex(int arr[], int l, int r) {
        // If No Magic Index return -1;
        if (l > r)
            return -1;

        int m = l + (r - l) / 2;
        int midValue = arr[m];

        // Magic Index Found, return it.
        if (m == midValue)
            return m;

        // Search on Left side
        int left = magicIndex(arr, l, Math.min(midValue, m - 1));

        // If Found on left side, return.
        if (left >= 0)
            return left;

        // Return ans from right side.
        return magicIndex(arr, Math.max(midValue, m + 1), r);
    }

    // Driver code
    public static void main(String[] args) {
        int arr[] = {-10, -5, 2, 2, 2, 3, 4, 7,
                9, 12, 13};
        int n = arr.length;
        int index = magicIndex(arr, 0, n - 1);
        if (index == -1)
            System.out.print("No Magic Index");
        else
            System.out.print("Magic Index is : " + index);
    }
}
