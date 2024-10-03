package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
     * Constraints:
     * 1 <= arr.length <= 3 * 104
     * 1 <= arr[i] <= 3 * 104
     *
     * Medium
     *
     * https://leetcode.com/problems/sum-of-subarray-minimums
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


    class Solution_editorial_clean {
        public int sumSubarrayMins(int[] arr) {
            int MOD = 1000000007;

            Stack<Integer> stack = new Stack<>();
            long sum = 0;

            for (int i = 0; i <= arr.length; i++) {// building monotonically increasing stack
                while (!stack.empty() && (i == arr.length || arr[stack.peek()] >= arr[i])) {//">="
                    int cur = stack.pop();
                    int l = stack.empty() ? -1 : stack.peek(); // previous smaller item for arr[cur] is arr[stack.peek()]
                    int r = i;
                    long count = (cur - l) * (r - cur) % MOD;

                    sum += (count * arr[cur]) % MOD; //!!! arr[cur]
                    sum %= MOD;
                }
                stack.push(i);
            }

            return (int) (sum);
        }
    }


    class Solution_2_clean {
        public int sumSubarrayMins(int[] arr) {
            int len = arr.length;
            int mod = (int) 1e9 + 7;
            long sum = 0;//!!! long

            Stack<Integer> s1 = new Stack<>();
            Stack<Integer> s2 = new Stack<>();
            int[] A = new int[len];
            int[] B = new int[len];

            for (int i = 0; i < len; i++) {
                B[i] = len - i;
            }

            for (int i = 0; i < len; i++) {
                while (!s1.isEmpty() && arr[s1.peek()] > arr[i]) {
                    s1.pop();
                }
                A[i] = s1.isEmpty() ? i + 1 : i - s1.peek();
                s1.push(i);

                while (!s2.isEmpty() && arr[s2.peek()] > arr[i]) {
                    B[s2.peek()] = i - s2.peek();
                    s2.pop();
                }
                s2.push(i);
            }

            for (int i = 0; i < len; i++) {
                sum = (sum + (long) A[i] * B[i] * arr[i]) % mod;
            }

            return (int) sum;
        }
    }

    class Demo {
        /**
         * Mono Increase Stack is used for:
         * Find the Previous Less Element (PLE) of each element in a list (or array) with O(n) time.
         * For example:
         * [3, 7, 8, 4]
         * The previous less element of 7 is 3.
         * The previous less element of 8 is 7.
         * The previous less element of 4 is 3.
         *
         * -1 in res : no PLE
         *
         * Push index, not element itself, into stack.
         * "num" represents the number pushed into the stack at current stack (after add PLE to res):
         * stack           res
         * "0"             -1
         *  0 "1"          A[0] = 3
         *  0  1 "2"       A[1] = 7
         *  0 "3"          A[0] = 3
         */
        public List<Integer> findPreviousLessElements(int[] A) {
            List<Integer> res = new ArrayList<>();
            Stack<Integer> stack = new Stack<>();
            for (int i = 0; i < A.length; i++) {
                while (!stack.isEmpty() && A[stack.peek()] > A[i]) {
                    stack.pop();
                }
                int PLE = stack.isEmpty() ? -1 : A[stack.peek()];
                res.add(PLE);

                stack.push(i);
            }
            return res;
        }

        /**
         * Find the Next Less Element (NLE) of each element in a list (or array) with O(n) time:
         * What is the next less element of an element?
         * For example:
         * [3, 7, 8, 4]
         * The next less element of 8 is 4.
         * The next less element of 7 is 4.
         * There is no next less element for 3 and 4.
         *
         * stack            res
         * "0"              -
         *  0 "1"           -
         *  0  1  "2"       -
         *  0 "3"           res[2] = A[3] = 4
         *                  res[1] = A[3] = 4
         *
         *                  [-1, 4, 4, -1]
         */
        public int[] findNextLessElements(int[] A) {
            int[] res = new int[A.length];
            Arrays.fill(res, -1);

            Stack<Integer> stack = new Stack<>();
            for (int i = 0; i < A.length; i++) {
                while (!stack.isEmpty() && A[stack.peek()] > A[i]) {
                    int top = stack.pop();
                    res[top] = A[i];
                }
                stack.push(i);
            }
            return res;
        }
    }

    /**
     * https://leetcode.com/problems/sum-of-subarray-minimums/discuss/178876/stack-solution-with-very-detailed-explanation-step-by-step
     * Good summary of mono stack problems.
     *
     *
     * 1.find the previous less element of each element in a vector with O(n) time
     * 2.find the next less element of each element in a vector with O(n) time
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

            /**
             * distance from current index to the index of PLE
             * If there's a PLE for current element, the distance is the length of subarray from the element right next
             * to PLE element to current element. For example:
             * [2, 9, 7, 8, 3, 4, 6, 1]
             *     |________|
             *
             * For "3" (at index 4), its PLE is "2" (at index 0), the distance is 4 - 0 = 4
             *
             * If there's no PLE for current element, the distance is the length or total number of element from the
             * frist element to current element.
             */
            int[] A = new int[len];//
            int[] B = new int[len];//distance from current index to the index of NLE

            for (int i = 0; i < len; i++) {
                /**
                 * From demo, we can see that PLE element is set for every index in for loop. So we don't really need
                 * to init A[] here. For a given index, if stack is empty, it means there's no PLE for the elemnet at
                 * the current index. For this problem, the distance to PLE is length of current index, which is i + 1.
                 */
                //A[i] = i + 1;

                /**
                 * !!!
                 * From demo, we can see that we only set NLE when there's stack pop operations at certain index. If
                 * there's no such action, it means there's no NLE, or current element is the largest from this index
                 * to the end. For this problem it means the distance is:
                 * total length of the array - (i + 1) + 1 => len - i
                 */
                B[i] = len - i;
            }

            for (int i = 0; i < len; i++) {
                //previous less element
                while (!s1.isEmpty() && arr[s1.peek()] > arr[i]) {
                    s1.pop();
                }
                A[i] = s1.isEmpty() ? i + 1 : i - s1.peek();
                /**
                 * !!!
                 * Must do push() after set A[], otherwise, the stack top element will not be the correct one.
                 */
                s1.push(i);

                //next less element
                while (!s2.isEmpty() && arr[s2.peek()] > arr[i]) {
                    B[s2.peek()] = i - s2.peek();
                    s2.pop();
                }
                s2.push(i);
            }

            for (int i = 0; i < len; i++) {
                /**
                 * !!!
                 * Must cast multiplication result to long before addition,
                 * otherwise, value will be wrong for really large number.
                 */
                sum = (sum + (long) A[i] * B[i] * arr[i]) % mod;
            }

            return (int) sum;
        }
    }
}
