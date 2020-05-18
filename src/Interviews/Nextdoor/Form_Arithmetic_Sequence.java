package Interviews.Nextdoor;

public class Form_Arithmetic_Sequence {
    /**
     * Given a sorted array arr[], the task is to find minimum elements needed to be inserted
     * in the array such that array forms an Arithmetic Progression.
     *
     * Examples:
     *
     * Input: arr[] = {1, 6, 8, 10, 14, 16}
     * Output: 10
     * Explanation:
     * Minimum elements required to form A.P. is 10.
     * Transformed array after insertion of the elements.
     * arr[] = {1, 2, 3, 4, 5, 6, 7, 8,
     * 9, 10, 11, 12, 13, 14, 15, 16}
     *
     * Input: arr[] = {1, 3, 5, 7, 11}
     * Output: 1
     * Explanation:
     * Minimum elements required to form A.P. is 1.
     * Transformed array after insertion of the elements.
     * arr[] = {1, 3, 5, 7, 9, 11}
     *
     * https://www.geeksforgeeks.org/minimum-elements-inserted-in-a-sorted-array-to-form-an-arithmetic-progression/?ref=rp
     */

    // Function to find the greatest
    // common divisor of two numbers
    static int gcdFunc(int a, int b) {
        if (b == 0) return a;
        return gcdFunc(b, a % b);
    }

    // Function to find the minimum
    // the minimum number of elements
    // required to be inserted into array
    static int findMinimumElements(int[] a, int n) {
        int[] b = new int[n - 1];

        // Difference array of consecutive
        // elements of the array
        for (int i = 1; i < n; i++) {
            b[i - 1] = a[i] - a[i - 1];
        }
        int gcd = b[0];

        // GCD of the difference array
        for (int i = 0; i < n - 1; i++) {
            gcd = gcdFunc(gcd, b[i]);
        }

        int ans = 0;

        // Loop to calculate the minimum
        // number of elements required
        for (int i = 0; i < n - 1; i++) {
            ans += (b[i] / gcd) - 1;
        }
        return ans;
    }

    // Driver Code
    public static void main(String[] args) {
        int arr1[] = {1, 6, 8, 10, 14, 16};
        int n1 = arr1.length;
        // Function calling
        System.out.print(findMinimumElements(arr1, n1)
                + "\n");
    }
}
