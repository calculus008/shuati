package Interviews.Paypal;

/**
 * A simplified version of LE_1060_Missing_Element_In_Sorted_Array
 */
public class FirstMissingNumberInSortedArray {
    public static int findMissing(int arr[], int n) {
        int l = 0, h = n - 1;

        if (arr[h] - arr[0] == n - 1) return arr[n - 1] + 1;

        while (h > l) {
            int m = l + (h - l) / 2;
            int missing = arr[m] - arr[0] - m;

            if (missing >= 1) {
                h = m;
            } else {
                l = m + 1;
            }
        }

        return arr[l - 1] + 1;
    }

    public static void main(String args[]) {
//        int arr[] = {-9, -8, -7, -5, -4, -3, -2, -1, 0};
        int arr[] = {1, 2, 4, 9};
        int n = arr.length;

        System.out.print(findMissing(arr, n));
    }
}
