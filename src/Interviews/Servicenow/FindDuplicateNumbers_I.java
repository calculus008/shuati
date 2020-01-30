package Interviews.Servicenow;

public class FindDuplicateNumbers_I {
    /**
     * Given an array of n elements which contains elements from 0 to n-1, with any of these
     * numbers appearing any number of times. Find these repeating numbers in O(n) and using
     * only constant memory space.
     *
     * For example, let n be 7 and array be {1, 2, 3, 1, 3, 6, 6}, the answer should be 1, 3 and 6.
     *
     * https://www.geeksforgeeks.org/duplicates-array-using-o1-extra-space-set-2/
     */

    /**
     *   idx 0  1  2  3  4  5  6   size = 7
     *       1, 2, 3, 1, 3, 6, 6
     *
     *   idx 0  1   2   3   4  5  6
     *       1, 16, 10, 15, 3, 6, 20
     *
     * num/7 0, 2,   1, 2,  0, 0, 2
     *
     */
    public static void printRepeating(int arr[], int n) {
        // First check all the values that are
        // present in an array then go to that
        // values as indexes and increment by
        // the size of array
        for (int i = 0; i < n; i++) {
//            int index = arr[i] % n;
//            arr[index] += n;
            arr[arr[i] % n] += n;
        }

        for (int i = 0; i < n; i++) {
            if ((arr[i] / n) > 1)
                System.out.println(i + " ");
        }
    }

    // Driver's code
    public static void main(String args[]) {
        int arr[] = {1, 6, 3, 1, 3, 6, 6};
        int arr_size = arr.length;

        System.out.println("The repeating elements are: ");
        printRepeating(arr, arr_size);
    }
}


