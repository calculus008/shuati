package Interviews.Facebook;

import java.util.*;

public class Count_Sort {
    /**
     * https://www.geeksforgeeks.org/counting-sort/
     *
     * https://medium.com/analytics-vidhya/sorting-strings-using-counting-sort-modified-1a6ca02b9a9f
     *
     * https://gabrielghe.github.io/university/2016/03/09/counting-sort
     *
     * Example with input set that only has digits (0 ~ 9)
     * Input data: 1, 4, 1, 2, 7, 5, 2
     *
     *   Count:
     *   Index:     0  1  2  3  4  5  6  7  8  9
     *   Count:     0  2  2  0  1  1  0  1  0  0
     *
     *   Accumulative Count:
     *   Index:     0  1  2  3  4  5  6  7  8  9
     *   Count:     0  2  4  4  5  6  6  7  7  7
     *
     *   Output:
     *   count[2] - 1 = 3, output[3] = 2, count[2] = 3
     *   count[5] - 1 = 5, output[5] = 5, count[5] = 5
     *   count[7] - 1 = 6, output[6] = 7, count[7] = 6
     *   count[2] - 1 = 2, output[2] = 2, count[2] = 2
     *   count[1] - 1 = 1, output[1] = 1, count[1] = 1
     *   count[4] - 1 = 4, output[4] = 4, count[4] = 4
     *   count[1] - 1 = 0, output[0] = 1, count[1] = 0
     *
     *   Time  : O(n + k) where n is the number of elements in input array and k is the range of input.
     *   Space : O(n + k)
     */

    class CountingSortForChar {
        static void sort(char arr[]) {
            int n = arr.length;

            // The output character array that will have sorted arr
            char output[] = new char[n];

            // Create a count array to store count of individual
            // characters and initialize count array as 0
            int count[] = new int[256];

            // store count of each character
            for (int i = 0; i < n; ++i) {
                ++count[arr[i]];
            }

            // Change count[i] so that count[i] now contains actual
            // position of this character in output array
            for (int i = 1; i <= 255; ++i) {
                count[i] += count[i - 1];
            }

            // Build the output character array
            // To make it stable we are operating in reverse order.
            for (int i = n - 1; i >= 0; i--) {
                output[count[arr[i]] - 1] = arr[i];
                --count[arr[i]];
            }

            // Copy the output array to arr, so that arr now
            // contains sorted characters
            for (int i = 0; i < n; ++i)
                arr[i] = output[i];
        }

        // Driver method
        public static void main(String args[]) {
//            CountingSort ob = new CountingSort();
            char arr[] = {'g', 'e', 'e', 'k', 's', 'f', 'o',
                    'r', 'g', 'e', 'e', 'k', 's'};

            sort(arr);

            System.out.print("Sorted character array is ");
            for (int i = 0; i < arr.length; ++i)
                System.out.print(arr[i]);
        }
    }

    /**
     * The problem with the previous counting sort was that we could not sort the elements if we have negative numbers
     * in it. Because there are no negative array indices. So what we do is, we find the minimum element and we will
     * store count of that minimum element at zero index.
     */
    class CountingSortTakesNegative {

        static void countSort(int[] arr) {
            int max = Arrays.stream(arr).max().getAsInt();
            int min = Arrays.stream(arr).min().getAsInt();

            int range = max - min + 1;
            int count[] = new int[range];
            int output[] = new int[arr.length];

            for (int i = 0; i < arr.length; i++) {
                count[arr[i] - min]++;
            }

            for (int i = 1; i < count.length; i++) {
                count[i] += count[i - 1];
            }

            for (int i = arr.length - 1; i >= 0; i--) {
                output[count[arr[i] - min] - 1] = arr[i];
                count[arr[i] - min]--;
            }

            for (int i = 0; i < arr.length; i++) {
                arr[i] = output[i];
            }
        }

        static void printArray(int[] arr) {
            for (int i = 0; i < arr.length; i++) {
                System.out.print(arr[i] + " ");
            }
            System.out.println("");
        }

        // Driver code
        public static void main(String[] args) {
            int[] arr = {-5, -10, 0, -3, 8, 5, -1, 10};
            countSort(arr);
            printArray(arr);
        }
    }
}
