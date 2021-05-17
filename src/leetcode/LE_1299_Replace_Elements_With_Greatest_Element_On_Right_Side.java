package leetcode;

import java.util.ArrayDeque;
import java.util.Deque;

public class LE_1299_Replace_Elements_With_Greatest_Element_On_Right_Side {
    /**
     * Given an array arr, replace every element in that array with the greatest
     * element among the elements to its right, and replace the last element with -1.
     *
     * After doing so, return the array.
     *
     * Example 1:
     *
     * Input: arr = [17,18,5,4,6,1]
     * Output: [18,6,6,6,1,-1]
     *
     *
     * Constraints:
     *
     * 1 <= arr.length <= 10^4
     * 1 <= arr[i] <= 10^5
     *
     * Easy
     */

    /**
     * Time  : O(n)
     * Space : O(1)
     */
    class Solution {
        public int[] replaceElements(int[] arr) {
            int[] res = new int[arr.length];
            int max = -1;

            for (int i = arr.length - 1; i > -1; i--) {
                res[i] = max;
                max = Math.max(max, arr[i]);
            }

            return res;
        }
    }

    /**
     * Time  : O(n)
     * Space : O(n)
     */
    class Solution_My_Mono_Stack {
        public int[] replaceElements(int[] arr) {
            int[] res = new int[arr.length];
            Deque<Integer> stack = new ArrayDeque<>();

            for (int i = 0; i < arr.length; i++) {
                while (!stack.isEmpty() && arr[stack.peekLast()] < arr[i]) {
                    stack.removeLast();
                }
                stack.addLast(i);
            }

            for (int i = 0; i < arr.length - 1; i++) {
                if (stack.peekFirst() == i) {
                    stack.removeFirst();
                }
                res[i] = arr[stack.peek()];
            }

            res[arr.length - 1] = -1;

            return res;
        }
    }
}
