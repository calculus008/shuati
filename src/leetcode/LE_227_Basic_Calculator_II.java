package leetcode;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

/**
 * Created by yuank on 4/4/18.
 */
public class LE_227_Basic_Calculator_II {
    /**
        Implement a basic calculator to evaluate a simple expression string.

        The expression string contains only non-negative integers, +, -, *, / operators and empty spaces .
        The integer division should truncate toward zero.

        You may assume that the given expression is always valid.

        Some examples:
        "3+2*2" = 7
        " 3/2 " = 1
        " 3+5 / 2 " = 5
        Note: Do not use the eval built-in library function.
     */

    //Stack solution, Time and Space : O(n)
    /**
     * 因为没有括号，只有加减乘除， 所以：
     * 1.取出数字，根据当前的操作符，对乘除，运算优先，从stack中取出一个数，和当前数计算，将结果压入stack.
     * 2.遇到操作符号，记下当前的操作符。
     *
     * 关键 ：因为乘除优先，计算乘除，结果入栈。对加减，分别入栈正数和负数。最后，所有元素出栈并相加。
     */
    class Solution1 {
        public int calculate(String s) {
            if (s == null || s.length() == 0) return 0;

            int res = 0;
            int n = s.length();
            Stack<Integer> stack = new Stack<>();
            char op = '+';

            for (int i = 0; i < n; i++) {
                char c = s.charAt(i);
                if (Character.isDigit(c)) {
                    int num = 0;
                    while (i < n && Character.isDigit(s.charAt(i))) {
                        num = num * 10 + s.charAt(i) - '0';
                        i++;
                    }

                    if (op == '+') {
                        stack.push(num);
                    } else if (op == '-') {
                        stack.push(-num);
                    } else if (op == '*') {
                        stack.push(stack.pop() * num);
                    } else if (op == '/') {
                        stack.push(stack.pop() / num);
                    }

                    i--;
                } else if (c == '+' || c == '-' || c == '*' || c == '/') {
                    op = c;
                }
            }

            for (int number : stack) {
                res += number;
            }

            return res;
        }
    }

    /**
     * Preferred Solution
     *
     * Use eval(), like LE_772_Basic_Calculator_III
     *
     * No parentheses, so no extra level to "recurse". No need to save operator in a separate stack
     */
    class Solution2 {
        public int calculate(String s) {
            if (s == null || s.length() == 0) return 0;

            int res = 0;
            int n = s.length();
            Stack<Integer> stack = new Stack<>();
            char op = '+';

            for (int i = 0; i < n; i++) {
                char c = s.charAt(i);
                if (Character.isDigit(c)) {
                    int num = 0;
                    while (i < n && Character.isDigit(s.charAt(i))) {
                        num = num * 10 + s.charAt(i) - '0';
                        i++;
                    }

                    stack.push(eval(op, num, stack));

                    i--;
                } else if (c == '+' || c == '-' || c == '*' || c == '/') {
                    op = c;
                }
            }

            for (int number : stack) {
                res += number;
            }

            return res;
        }

        private int eval(char sign, int num, Stack<Integer> stack) {
            int ret = 0;
            if (sign == '+') {
                ret = num;
            } else if (sign == '-') {
                ret = -num;
            } else if (sign == '*') {
                ret = stack.pop() * num;
            } else if (sign == '/') {
                ret = stack.pop() / num;
            }

            return ret;
        }
    }

    class Solution_Practice {
        public int calculate(String s) {
            if (null == s || s.length() == 0) {
                return 0;
            }

            char[] chars = s.toCharArray();
            Deque<Integer> stack = new ArrayDeque<>();
            char op = '+';
            int num = 0;
            int res = 0;
            int len = chars.length;

            for (int i = 0; i < len; i++) {
                if (Character.isDigit(chars[i])) {
                    while (i < len && Character.isDigit(chars[i])) {
                        num = num * 10 + chars[i] - '0';
                        i++;
                    }
                    i--;

                    int val = 0;
                    if (op == '+') {
                        val = num;
                    } else if (op == '-') {
                        val = -num;
                    } else if (op == '*') {
                        val = stack.pop() * num;
                    } else if (op == '/') {
                        val = stack.pop() / num;
                    }
                    stack.push(val);

                    num = 0;
                } else if (chars[i] == '+' || chars[i] == '-' || chars[i] == '*' || chars[i] == '/') {
                    op = chars[i];
                }
            }

            while (!stack.isEmpty()) {
                res += stack.pop();
            }

            return res;
        }
    }

    class Solution_Recursion {
        public int calculate(String s) {
            if (s == null || s.length() == 0) return 0;

            s = s.replaceAll(" ", "");
            s += "#";

            int cur = 0, pre = 0, sum = 0;
            char preOp = '+';

            for (char c : s.toCharArray()) {
                if (c >= '0' && c <= '9') {
                    cur = cur * 10 + c - '0';
                } else {
                    switch (preOp) {
                        case '+':
                            sum += pre;
                            pre = cur;
                            break;
                        case '-':
                            sum += pre;
                            pre = -cur;
                            break;
                        case '*':
                            pre *= cur;
                            break;
                        case '/':
                            pre /= cur;
                            break;
                    }

                    preOp = c;
                    cur = 0;
                }
            }

            return sum + pre;
        }
    }

}
