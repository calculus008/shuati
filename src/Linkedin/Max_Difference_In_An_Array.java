package Linkedin;

public class Max_Difference_In_An_Array {
    /**
     * Given an array arr[] of integers, find out the maximum difference between any
     * two elements such that larger element appears after the smaller number.
     *
     * Examples :
     *
     * Input : arr = {2, 3, 10, 6, 4, 8, 1}
     * Output : 8
     * Explanation : The maximum difference is between 10 and 2.
     *
     * Input : arr = {7, 9, 5, 6, 3, 2}
     * Output : 2
     * Explanation : The maximum difference is between 9 and 7.
     */

   /**
    *  The function assumes that there are at least two
    *  elements in array.The function returns a negative
    *  value if the array is sorted in decreasing order.
    *  Returns 0 if elements are equal
    *
    *  Time  : O(n)
    *  Space : O(1)
    * */
    public static int maxDiff(int arr[], int arr_size) {
        int max_diff = arr[1] - arr[0];
        int min_element = arr[0];

        for (int i = 1; i < arr_size; i++) {
            if (arr[i] - min_element > max_diff) {
                max_diff = arr[i] - min_element;
            }

            if (arr[i] < min_element) {
                min_element = arr[i];
            }
        }
        return max_diff;
    }

    /* Driver program to test above functions */
    public static void main(String[] args) {
        int arr[] = {1, 2, 90, 10, 110};
        int size = arr.length;
        System.out.println("MaximumDifference is " +
                maxDiff(arr, size));
    }
}
