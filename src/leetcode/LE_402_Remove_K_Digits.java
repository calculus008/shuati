package leetcode;

import java.util.Stack;

public class LE_402_Remove_K_Digits {
    /**
     * Given a non-negative integer num represented as a string, remove k digits from the number
     * so that the new number is the smallest possible.
     *
     * Note:
     * The length of num is less than 10002 and will be ≥ k.
     * The given num does not contain any leading zero.
     *
     * Example 1:
     * Input: num = "1432219", k = 3
     * Output: "1219"
     * Explanation: Remove the three digits 4, 3, and 2 to form the new number 1219 which is the smallest.
     *
     * Example 2:
     * Input: num = "10200", k = 1
     * Output: "200"
     * Explanation: Remove the leading 1 and the number is 200. Note that the output must not contain leading zeroes.
     *
     * Example 3:
     * Input: num = "10", k = 2
     * Output: "0"
     * Explanation: Remove all the digits from the number and it is left with nothing which is 0.
     */

    /**
     * https://mp.weixin.qq.com/s/4pK5MLMkcuX_1RK_2Pth9g
     *
     * 给定整数 541270936，要求删去一个数，让剩下的整数尽可能小。
     * 此时，无论删除哪一个数字，最后的结果都是从9位整数变成8位整数。既然同样是8位整数，我们显然应该优先把高位的数字降低，
     * 这样对新整数的值影响最大。
     *
     * 如何把高位的数字降低呢？很简单，我们把原整数的所有数字从左到右进行比较，如果发现某一位的数字大于它右面(后面)的数字，
     * 那么在删除该数字后，必然会使得该数位的值降低，因为右面比它小的数字顶替了它的位置。
     *
     * 541270936
     * 41270936
     *
     * 在咱们这个例子中，数字5右侧的数字4小于5，所以删除数字5，最高位数字降低成了4。
     *
     * So keep doing it for k times. It's greedy algorithm.
     *
     * Naive solution
     * 代码使用了两层循环，外层循环基于删除次数（k），内层循环从左到右遍历所有数字。
     * 当遍历到需要删除的数字时，利用字符串的自身方法subString() 把对应数字删除，并重新拼接字符串。
     * 显然，这段代码的时间复杂度是O(kn)
     *
     * A better solution with stack
     * 代码中非常巧妙地运用了栈的特性，在遍历原整数的数字时，让所有数字一个个入栈，当某个数字需要删除时，
     * 让该数字出栈。最后，程序把栈中的元素转化为字符串结果。
     *
     * 我们仍然以整数 541270936，k=3 为例：
     *
     * 1.遍历到数字5，数字5入栈：
     *
     *        541270936
     * stack  5
     *
     * 2.遍历到数字4，发现栈顶5>4，栈顶5出栈，数字4入栈：
     *        541270936
     * stack  4
     *
     * 3.遍历到数字1，发现栈顶4>1，栈顶4出栈，数字1入栈：
     *        541270936
     * stack  1
     *
     * 4.继续遍历数字2，数字7，依次入栈。
     *        541270936
     * stack  127
     *
     * 5.遍历数字0，发现栈顶7>0，栈顶7出栈，数字0入栈
     *        541270936
     * stack  120
     *
     * 6.此时k的次数已经用完，无需再比较，剩下的数字一口气入栈：
     *        541270936
     * stack  120936
     *
     * 此时栈中的元素就是最终的结果。
     *
     * 代码只对所有数字遍历了一趟，遍历的时间复杂度是O（n），而后把栈转化为字符串的时间复杂度也是O（n），
     * 所以最终的时间复杂度是O（n）。同时，程序中利用栈来回溯遍历过的数字以及删除数字，所以程序的空间复杂度是O（n）。
     *
     */
    class Solution {
        public String removeKdigits(String num, int k) {
            if (null == num || num.length() == 0) return "";

            int n = num.length();
            Stack<Character> stack = new Stack<>();
            char[] s = num.toCharArray();

            int j = 0;
            for (int i = 0; i < n; i++) {
                /**
                 * !!!
                 * Use "while", NOT "if"
                 *
                 * 1234567890
                 * k = 9
                 *
                 * while loop will pop 1 ~ 9.
                 */
                while (!stack.isEmpty() && k > 0 && s[i] < stack.peek()) {
                    stack.pop();
                    k--;
                }

                stack.push(s[i]);
            }

            /**
             * Corner case 1:
             * 9
             * k = 1
             *
             * stack has 9, k is still 1, so we need to remove it and bring k to 0.
             */
            while (k > 0) {
                stack.pop();
                k--;
            }

            StringBuilder sb = new StringBuilder();
            /**
             * !!!
             * for loop on stack, will get elements in sequence from bottom to top (FIFO), NOT the stack FILO.
             * so no need to reverse.
             */
            for (Character c : stack) {
                sb.append(c);
            }

            /**!!!
             * Corner case 2:
             * Must check is sb is empty when removing leading zero
             **/
            while (sb.length() > 0 && sb.charAt(0) == '0') {
                sb.deleteCharAt(0);
            }

            /**
             * !!!
             * Corner case 3:
             * need to deal with the case that nothing left in sb.
             */
            return sb.length() == 0 ? "0" : sb.toString();
        }
    }
}
