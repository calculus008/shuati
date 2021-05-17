package leetcode;

import java.util.Stack;

public class LE_907_Sum_Of_Subarray_Minimums {
    /**
     * Given an array of integers A, find the sum of min(B), where B ranges over every (contiguous) subarray of A.
     *
     * Since the answer may be large, return the answer modulo 10^9 + 7.
     *
     * Example 1:
     * Input: [3,1,2,4]
     * Output: 17
     * Explanation: Subarrays are [3], [1], [2], [4], [3,1], [1,2], [2,4], [3,1,2], [1,2,4], [3,1,2,4].
     * Minimums are 3, 1, 2, 4, 1, 1, 2, 1, 1, 1.  Sum is 17.
     *
     *
     * Note:
     * 1 <= A.length <= 30000
     * 1 <= A[i] <= 30000
     *
     * Example 1:
     * Input: arr = [3,1,2,4]
     * Output: 17
     * Explanation:
     * Subarrays are [3], [1], [2], [4], [3,1], [1,2], [2,4], [3,1,2], [1,2,4], [3,1,2,4].
     * Minimums are 3, 1, 2, 4, 1, 1, 2, 1, 1, 1.
     * Sum is 17.
     *
     * Example 2:
     * Input: arr = [11,81,94,43,3]
     * Output: 444
     *
     *
     * Constraints:
     * 1 <= arr.length <= 3 * 104
     * 1 <= arr[i] <= 3 * 104
     *
     * Medium
     *
     * Similar problem (monotone (increase/decrease) stack):
     * LE_503_Next_Greater_Element_II
     * LE_84_Largest_Rectangle_In_Histogram
     * LE_42_Trapping_Rain_Water
     * LE_85_Maximal_Rectangle
     * LE_316_Remove_Duplicate_Letters
     * LE_402_Remove_K_Digits
     * LE_321_Create_Maximum_Number
     * LE_456_132_Pattern
     * LE_239_Sliding_Window_Maximum
     * LE_769_Max_Chunks_To_Make_Sorted
     */

    /**
     * https://leetcode.com/problems/sum-of-subarray-minimums/discuss/178876/stack-solution-with-very-detailed-explanation-step-by-step
     * Good summary of mono stack problems.
     *
     * Key:
     * For each arr[i], find previous less elements and next less elements, its contribution to the sum is pre * next * arr[i].
     *
     * Example: given [2, 9, 7, 8, 3, 4, 6, 1]
     *
     * For "3":
     *                [2, 9, 7, 8, 3, 4, 6, 1]
     *                 ^           ^        ^
     *              pre less              next less
     *
     * we can determine the distance between 3 and 2(previous less) , and the distance between 3 and 1(next less).
     * In this example, the distance is 4 and 3 respectively.
     *
     * How many sub arrays with 3 being its minimum value?
     * The answer is 4*3.
     *
     * 9 7 8 3
     * 9 7 8 3 4
     * 9 7 8 3 4 6
     * 7 8 3
     * 7 8 3 4
     * 7 8 3 4 6
     * 8 3
     * 8 3 4
     * 8 3 4 6
     * 3
     * 3 4
     * 3 4 6
     *
     * Or think it this way:
     * the substrings within [9, 7, 8, 3] that ends with 3, total 4:
     * 9 7 8 3
     *   7 8 3
     *     8 3
     *       3
     *
     * Then for [3 4 6], substring starts with 3:
     * 3 4 6
     *   4 6
     *     6
     *
     * All those 3 substring can append to the 4 substring in the first group to form a substring from [9 7 8 3 4 6]
     * that contains element 3, therefore 4 * 3.
     *
     * How much the element 3 contributes to the final answer?
     * It is 3*(4*3).
     */

    class Solution {
        public int sumSubarrayMins(int[] arr) {
            int len = arr.length;
            int mod = (int) 1e9 + 7;
            long sum = 0;//!!! long

            Stack<Integer> s1 = new Stack<>();
            Stack<Integer> s2 = new Stack<>();

            int[] A = new int[len];
            int[] B = new int[len];

            for (int i = 0; i < len; i++) {
                A[i] = i + 1;
                /**
                 * !!!
                 * Must init B[], otherwise, for example : [1, 2, 3, 4, 5], it keeps increasing, so we never go into
                 * while loop for s2, then we lose the chance to set B[], then it will has value of 0. Therefore, we
                 * need to init with len - (i + 1) + 1, assuming current element is the largest from current position
                 * to the end of the arr[].
                 */
                B[i] = len - i;
            }

            for (int i = 0; i < len; i++) {
                //previous less
                while (!s1.isEmpty() && arr[s1.peek()] > arr[i]) {
                    s1.pop();
                }
                A[i] = s1.isEmpty() ? i + 1 : i - s1.peek();
                /**
                 * !!!
                 * Must do push() after set A[], otherwise, the stack top element will not be the correct one.
                 */
                s1.push(i);

                //next less
                while (!s2.isEmpty() && arr[s2.peek()] > arr[i]) {
                    B[s2.peek()] = i - s2.peek();
                    s2.pop();
                }
                s2.push(i);
            }

            for (int i = 0; i < len; i++) {
                /**
                 * !!!
                 * Must cast multiplication resutl to long before addition,
                 * otherwise, value will be wrong for really large number.
                 */
                sum = (sum + (long) A[i] * B[i] * arr[i]) % mod;
            }

            return (int) sum;
        }
    }
}
