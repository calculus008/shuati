package Interviews.Amazon;

public class Make_Sorted_By_One_Swap {
    /**
     * https://www.geeksforgeeks.org/check-if-array-can-be-sorted-with-one-swap/
     *
     * Given an array containing N elements. Find if it is possible to sort it in non-decreasing order using at most one swap.
     *
     * Examples:
     *
     * Input : arr[] = {1, 2, 3, 4}
     * Output : YES
     * The array is already sorted
     *
     * Input : arr[] = {3, 2, 1}
     * Output : YES
     * Swap 3 and 1 to get [1, 2, 3]
     *
     * Input : arr[] = {4, 1, 2, 3}
     * Output :NO
     */
    static boolean checkSorted(int n, int arr[]) {
        /**
         * Find counts and positions of elements that are out of order.
         */
        int first = 0, second = 0;
        int count = 0;

        for (int i = 1; i < n; i++) {
            if (arr[i] < arr[i - 1]) {
                count++;

                if (first == 0) {
                    first = i;
                } else {
                    second = i;
                }
            }
        }

        if (count > 2) return false;

        /**
         * If all elements are sorted already
         **/
        if (count == 0) return true;

        /**
         * Cases like {1, 5, 3, 4, 2}, we swap 5 and 2.
         **/
        if (count == 2) {
            swap(arr, first - 1, second);
        } else if (count == 1) {// Cases like {1, 2, 4, 3, 5}
            swap(arr, first - 1, first);
        }

        /**
         * Now check if array becomes sorted for cases like {4, 1, 2, 3}
         **/
        for (int i = 1; i < n; i++) {
            if (arr[i] < arr[i - 1]) return false;
        }

        return true;
    }

    static int[] swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
        return arr;
    }

    // Driver Code
    public static void main(String[] args) {
        int arr[] = {1, 4, 3, 2};
        int n = arr.length;
        if (checkSorted(n, arr))
            System.out.println("Yes");
        else
            System.out.println("No");
    }
}
