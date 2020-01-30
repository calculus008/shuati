package Interviews.Servicenow;

public class RearrangeNumbers {
    /**
     * An array contains both positive and negative numbers in random order.
     * Rearrange the array elements so that positive and negative numbers
     * are placed alternatively. Number of positive and negative numbers
     * need not be equal. If there are more positive numbers they appear
     * at the end of the array. If there are more negative numbers, they
     * too appear in the end of the array.
     *
     * For example, if the input array is [-1, 2, -3, 4, 5, 6, -7, 8, 9],
     * then the output should be [9, -7, 8, -3, 5, -1, 2, 4, 6]
     *
     * Require Time O(n), Space O(1)
     */

    /**
     * [-1, 2, -3, 4, 5, 6, -7, 8, 9] => [-1, -7, -3, 2, 4, 5, 6, 8, 9]
     *
     * [2, -7, -3, -1, 4, 5, 6, 8, 9]
     * [2, -7,  4, -1, -3, 5, 6, 8, 9]
     * [2, -7,  4, -1,  5, -3, 6, 8, 9]
     */
    static void rearrange(int arr[], int n) {
        // The following few lines are similar to partition
        // process of QuickSort.  The idea is to consider 0
        // as pivot and divide the array around it.
        int i = -1, temp = 0;
        for (int j = 0; j < n; j++) {
            if (arr[j] < 0) {
                i++;
                temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        // Now all positive numbers are at end and negative numbers at
        // the beginning of array. Initialize indexes for starting point
        // of positive and negative numbers to be swapped
        int pos = i + 1, neg = 0;

        // Increment the negative index by 2 and positive index by 1, i.e.,
        // swap every alternate negative number with next positive number
        while (pos < n && neg < pos && arr[neg] < 0) {
            temp = arr[neg];
            arr[neg] = arr[pos];
            arr[pos] = temp;
            pos++;
            neg += 2;
        }
    }

    // A utility function to print an array
    static void printArray(int arr[], int n) {
        for (int i = 0; i < n; i++)
            System.out.print(arr[i] + "   ");
    }

    /*Driver function to check for above functions*/
    public static void main(String[] args) {
        int arr[] = {-1, 2, -3, 4, 5, 6, -7, 8, 9};
        int n = arr.length;
        rearrange(arr, n);
        System.out.println("Array after rearranging: ");
        printArray(arr, n);
    }
}
