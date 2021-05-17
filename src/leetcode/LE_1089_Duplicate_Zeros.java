package leetcode;

public class LE_1089_Duplicate_Zeros {
    /**
     * Given a fixed length array arr of integers, duplicate each occurrence of zero, shifting the remaining elements
     * to the right.
     *
     * Note that elements beyond the length of the original array are not written.
     *
     * Do the above modifications to the input array in place, do not return anything from your function.
     *
     * Example 1:
     * Input: [1,0,2,3,0,4,5,0]
     * Output: null
     * Explanation: After calling your function, the input array is modified to: [1,0,0,2,3,0,0,4]
     *
     * Example 2:
     * Input: [1,2,3]
     * Output: null
     * Explanation: After calling your function, the input array is modified to: [1,2,3]
     *
     *
     * Note:
     * 1 <= arr.length <= 10000
     * 0 <= arr[i] <= 9
     *
     * Easy
     */

    /**
     * In-place array question suggests:
     * 1.Write from the end, which avoid overwriting needed values.
     * 2.First count total number of zeros. Then we imagine that there's anther array with new length (after duplicating
     *   zeros)
     * 3.i points to original array, j points to the imagined new array.
     * 4.Only write to original array when j is smaller than original length
     * 5.When encountering zero, move one extra position for j (since in the imagined new array, each 0 is duplicated)
     *
     * [1,0,2,3,0,4,5,0]
     *                i
     * [1,0,0,2,3,0,0,4,5,0,0]
     *                      j
     */
    class Solution {
        public void duplicateZeros(int[] arr) {
            int len = arr.length;
            int count = 0;

            for (int i = 0; i < len; i++) {
                if (arr[i] == 0) count++;
            }

            int newLen = len + count;

            for (int i = len - 1, j = newLen - 1; i >= 0 && j >= 0; i--, j--) {
                if (arr[i] != 0) {
                    if (j < len) arr[j] = arr[i];
                } else {
                    if (j < len) arr[j] = arr[i];
                    j--;
                    if (j < len) arr[j] = arr[i];
                }
            }
        }
    }
}
