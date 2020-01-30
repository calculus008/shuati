package Interviews.Servicenow;

public class FindDuplicateNumbers_II {
    /**
     * Given an array of n elements which contains elements from 0 to n-1,
     * with any of these numbers appearing any number of times. Find these
     * repeating numbers in O(n) and using only constant memory space.
     *
     * It is required that the order in which elements repeat should be maintained.
     * If there is no repeating element present then print -1.
     *
     * Examples:
     *
     * Input : arr[] = {1, 2, 3, 1, 3, 6, 6}
     * Output : 1, 3, 6
     * Elements 1, 3 and 6 are repeating.
     * Second occurrence of 1 is found
     * first followed by repeated occurrence
     * of 3 and 6.
     *
     * Input : arr[] = {0, 3, 1, 3, 0}
     * Output : 3, 0
     * Second occurrence of 3 is found
     * first followed by second occurrence
     * of 0.
     *
     * https://www.geeksforgeeks.org/duplicates-in-an-array-in-on-time-and-by-using-o1-extra-space-set-3/
     */

    /**
     * Modified from FindDuplicateNumbers_I
     *
     * To mark the presence of an element size of the array, n is added to the index
     * position arr[i] corresponding to array element arr[i]. Before adding n, check
     * if value at index arr[i] is greater than or equal to n or not. If it is greater
     * than or equal to, then this means that element arr[i] is repeating. To avoid
     * printing repeating element multiple times, check if it is the first repetition
     * of arr[i] or not. It is first repetition if value at index position arr[i] is
     * less than 2*n. This is because, if element arr[i] has occurred only once before
     * then n is added to index arr[i] only once and thus value at index arr[i] is less
     * than 2*n. Add n to index arr[i] so that value becomes greater than or equal to
     * 2*n and it will prevent further printing of current repeating element. Also if
     * value at index arr[i] is less than n then it is first occurrence (not repetition)
     * of element arr[i]. So to mark this add n to element at index arr[i].
     */
    static void printDuplicates(int arr[], int n) {
        int i;

        /**
         * Flag variable used to represent whether repeating element is found or not.
         **/
        int flag = 0;

        for (i = 0; i < n; i++) {

            /**
             * Check if current element is repeating or not. If it is repeating
             * then value will be greater than or equal to n.
             **/
            if (arr[arr[i] % n] >= n) {
                /**
                 * Check if it is first repetition or not. If it is first repetition
                 * then value at index arr[i] is less than 2*n. Print arr[i] if it is
                 * first repetition.
                 **/
                if (arr[arr[i] % n] < 2 * n) {
                    System.out.print(arr[i] % n + " ");
                    flag = 1;
                }
            }

            /**
             * Add n to index arr[i] to mark presence of arr[i] or to mark repetition of arr[i].
             **/
            arr[arr[i] % n] += n;
        }

        /**
         * If flag variable is not set then no repeating element is found. So print -1.
         **/
        if (!(flag > 0)) {
            System.out.println("-1");
        }
    }

    public static void main(String[] args) {
        int arr[] = {1, 6, 3, 1, 3, 6, 6};
        int arr_size = arr.length;
        printDuplicates(arr, arr_size);
    }
}
