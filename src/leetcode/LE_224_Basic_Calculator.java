package leetcode;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

/**
 * Created by yuank on 3/29/18.
 */
public class LE_224_Basic_Calculator {
    /**
        Implement a basic calculator to evaluate a simple expression string.

        The expression string may contain open ( and closing parentheses ),
        the plus + or minus sign -, non-negative integers and empty spaces .

        You may assume that the given expression is always valid.

        Some examples:
        "1 + 1" = 2
        " 2-1 + 2 " = 3
        "(1+(4+5+2)-3)+(6+8)" = 23
     */


    /**
     *  Very Important
        Time and Space : O(n)

        1.Use Stack
        2.Only valid input char : non-negative integers, +, -, ' ', (, )
        3.Only operation is plus, for "-", treat it as negative number.
     */
    class Solution1 {
        public int calculate1(String s) {
            if (null == s || s.length() == 0) return 0;

            /**
             * In fact, we need to save both operator and number.
             * Normally we need 2 stack, one for number, one for operator.
             * But since we only have '+' and '-', so we only save sign of
             * int type (1 or -1), therefore we only use one stack.
             */
            Stack<Integer> stack = new Stack<>();
            int res = 0;
            int len = s.length();
            int sign = 1;

            //每一对"()"是一个计算单元
            for (int i = 0; i < len; i++) {
                if (Character.isDigit(s.charAt(i))) {
                    int num = 0;
                    while (i < len && Character.isDigit(s.charAt(i))) {
                        num = num * 10 + s.charAt(i) - '0';
                        i++;
                    }
                    //!!! 因为外循环是for loop, i自动加1，当while循环结束后，应该把i减去1。否则下一步会跳过运算符。比如： 1-1， 取出1后，下一步会跳过减号，跑到下一个1，结果为2，而不是正确的0
                    i--;

                    res += num * sign; //!!! 同一单元的计算在parse数字后立刻进行
                } else if (s.charAt(i) == '(') { //遇到"(",说明要开始新的计算单元，用stack保留到目前为止的计算结果，重新初始化res和sign
                    //注意push的顺序
                    stack.push(res);
                    stack.push(sign);
                    res = 0;
                    sign = 1;
                } else if (s.charAt(i) == ')') {//遇到")", 说明当前计算单元结束，计算当前和上一个单元的和。
                    //注意pop的顺序, 第一个pop出来的是sign, 第二各才是数值
                    res = stack.pop() * res + stack.pop();
                } else if (s.charAt(i) == '+') {
                    sign = 1;
                } else if (s.charAt(i) == '-') {
                    sign = -1;
                }
            }

            return res;
        }
    }

    /**
     *  Use 2 stacks, one for number, one for operator
     */
    class Solution2 {
        public int calculate2(String s) {
            Stack<Integer> s1 = new Stack<>();//number
            Stack<Character> s2 = new Stack<>();//operator

            int res = 0;
            char op = '+';//只有‘+’和‘-’，同级运算，可以不考虑优先级的问题。

            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (Character.isDigit(c)) {
                    int num = c - '0';
                    while (i + 1 < s.length() && Character.isDigit(s.charAt(i + 1))) {
                        num = num * 10 + s.charAt(i + 1) - '0';
                        i++;
                    }
                    // res += sign * num;
                    res += op == '+' ? num : -num;
                } else if (c == '-' || c == '+') {
                    op = c;
                } else if (c == '(') {
                    /**
                     实际上是模拟递归，把要保留的元素压入各自的栈，然后把这些元素清空或清零。
                     这样下一层的处理就有了clean slate.
                     **/
                    s1.push(res);
                    s2.push(op);
                    res = 0;
                    op = '+';//!!!
                } else if (c == ')') {
                    res = (s2.pop() == '+' ? res : -res) + s1.pop();
                }
            }
            return res;
        }
    }

    class Solution_Recursion_1 {
        /**
         * Not an efficient one, but best for Karat test, it only needs
         * to add the 2nd "else if" from its first question answer.
         */
        public int calculate(String s) {
            if (s == null || s.length() == 0) return 0;

            int sign = 1;

            char[] chs = s.toCharArray();
            int res = 0;
            int num = 0;
            for (int i = 0; i < chs.length; i++) {
                if (Character.isDigit(chs[i])) {
                    while (i < chs.length && Character.isDigit(chs[i])) {
                        num = num * 10 + chs[i] - '0';
                        i++;
                    }
                    i--;
                } else if (chs[i] == '+' || chs[i] == '-') {
                    System.out.println(sign + "," + num);
                    res += sign * num;
                    num = 0;
                    sign = chs[i] == '+' ? 1 : -1;
                } else if (chs[i] == '(') {
                    int start = i;
                    int count = 1;

                    /**
                     * #1
                     */
                    i++;
                    while (i < chs.length && count > 0) {
                        if (chs[i] == '(') {
                            count++;
                        } else if (chs[i] == ')') {
                            count--;
                        }
                        i++;
                    }

                    /**
                     * #2
                     * when recurse to the next level, remove the outside parenthese,
                     * hence, substring(start + 1, i - 1)
                     */
                    res += sign * calculate(s.substring(start + 1, i - 1));

                    /**
                     * #3
                     */
                    i--;
                }
            }

            System.out.println(sign + "," + num);
            res += sign * num;

            return res;
        }
    }

    class Solution_Recursion {
        public int calculate(String s) {
            if (null == s || s.length() == 0) {
                return 0;
            }

            Deque<Character> q = new ArrayDeque<>();
            for (char c : s.toCharArray()) {
                /**
                 * !!! filter out spaces
                 */
                if (c != ' ') {
                    q.offer(c);
                }
            }

            /**
             * !!!
             * signal the end of calculation, for example: 1 + 2 + 3,
             * without the last '#', the logic will miss adding the last number.
             */
            q.offer('#');
            return helper(q);
        }

        /**
         * !!!
         * Since we only have '+','-','(',')', we don't need 'pre' here.
         * 'pre' is for case we have '*' and '/', which we need to back track to previous operand.
         *
         * Here, instead of considering calculating two operands with one operator, we have a fixed
         * operand - 'sum'. We just add a positive or negative number to sum.
         *
         * If current char is not number or '(' (marking the start of a recursion), it signals that
         * current number extraction is done, then we just need to add it to sum.
         */
        private int helper(Deque<Character> q) {
            int sum = 0, pre = 0, cur = 0;
            char preOp = '+';

            while (!q.isEmpty()) {
                char c = q.poll();

                if (c >= '0' && c <= '9') {
                    cur = cur * 10 + c - '0';
                } else if (c == '(') {
                    cur = helper(q);
                } else {
                    if (preOp == '+') {
//                        sum += pre;
//                        pre = cur;
                        sum += cur;
                    } else if (preOp == '-') {
//                        sum += pre;
//                        pre = -cur;
                        sum -= cur;
                    }

                    if (c == ')') break;

                    preOp = c;
                    cur = 0;
                }
            }

//            return sum + pre;
            return sum;
        }
    }
}
