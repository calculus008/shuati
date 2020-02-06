package Interviews.Servicenow;

public class FindDuplicateNumbers_III {
    /**
     * Given an unsorted array of n integers which can contain integers from 1 to n.
     * Some elements can be repeated multiple times and some other elements can be
     * absent from the array. Count frequency of all elements that are present and
     * print the missing elements.
     *
     * Examples:
     *
     * Input: arr[] = {2, 3, 3, 2, 5}
     * Output: Below are frequencies of all elements
     *         1 -> 0
     *         2 -> 2
     *         3 -> 2
     *         4 -> 0
     *         5 -> 1
     *
     * Input: arr[] = {4, 4, 4, 4}
     * Output: Below are frequencies of all elements
     *         1 -> 0
     *         2 -> 0
     *         3 -> 0
     *         4 -> 4
     *
     * https://www.geeksforgeeks.org/count-frequencies-elements-array-o1-extra-space-time/
     */

    void printfrequency(int arr[], int n) {
        /**
         * Subtract 1 from every element so that the elements become in range from 0 to n-1
         * " integers from 1 to n"
         **/
        for (int j = 0; j < n; j++) {
            arr[j] = arr[j] - 1;
        }

        /**
         * Use every element arr[i] as index and add 'n' to element present at arr[i]%n
         * to keep track of count of occurrences of arr[i]
         **/
        for (int i = 0; i < n; i++)  {
            arr[arr[i] % n] += n;
        }

        /**
         * To print counts, simply print the number of times n was added at index
         * corresponding to every element
         **/
        for (int i = 0; i < n; i++) {
            System.out.println(i + 1 + "->" + arr[i] / n);
        }
    }

    public static void main(String[] args) {
        FindDuplicateNumbers_III count = new FindDuplicateNumbers_III();
        int arr[] = {2, 3, 3, 2, 5};
        int n = arr.length;
        count.printfrequency(arr, n);
    }
}
