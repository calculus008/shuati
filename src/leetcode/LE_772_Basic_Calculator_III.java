package leetcode;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

/**
 * Created by yuank on 10/18/18.
 */
public class LE_772_Basic_Calculator_III {
    /**
         Implement a basic calculator to evaluate a simple expression string.

         The expression string may contain open ( and closing parentheses ),
         the plus + or minus sign -, non-negative integers and empty spaces .

         The expression string contains only non-negative integers, +, -, *, / operators ,
         open ( and closing parentheses ) and empty spaces .
         The integer division should truncate toward zero.

         You may assume that the given expression is always valid.
         All intermediate results will be in the range of [-2147483648, 2147483647].

         Some examples:

         "1 + 1" = 2
         " 6-4 / 2 " = 4
         "2*(5+5*2)/3+(6/2+8)" = 21
         "(2+6* 3+5- (3*14/7+2)*5)+3"=-12

         Hard
     */

    /**
     * Queue + Recursion
     */
    class Solution_Recursion {
        public int calculate(String s) {
            if (s == null || s.length() == 0) return 0;

            Deque<Character> q = new ArrayDeque<>();
            for (char c : s.toCharArray()) {
                if (c != ' ') {
                    q.offer(c);
                }
            }

            /**
             * !!!
             */
            q.offer('#');

            return helper(q);
        }

        private int helper(Deque<Character> q) {
            int pre = 0, cur = 0, sum = 0;
            char preop = '+';

            while (!q.isEmpty()) {
                char c = q.poll();

                if (c >= '0' && c <= '9') {
                    cur = cur * 10 + c - '0';
                } else if (c == '(') {
                    cur = helper(q);
                }else {//not number and not '(', could be operator or ')'
                    switch (preop) {
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

                    if (c == ')') break;
                    preop = c;
                    cur = 0;
                }
            }

            return sum + pre;
        }
    }
    /**
         We just go through expression once, and for each character, and there are totally 5 cases
         case1: digit:
         case1.1: if operator now is '+', push digit
         case1.2: if operator now is '-', push -digit
         case1.3: if operator now is '*', push (poll*digit)
         case1.4: if operator nwo is '/', push (poll/digit)

         case2: space: do nothing

         case3: operators: update operator

         case4: (: push the operator into s2, push '(' into s1, use +inf as '('.
         case5: ): continues poll and sum polled value until poll '(',
                   then poll the operator from stack2, do calculate, then push back to stack1

         Use s1 to store digit and '(', why use long? because we want to use Long.MAX_VALUE to represent special char '('
         Use s2 to store operator before '('

         核心的思想是摊平运算，这里有两个不同的优先级，乘除和括号。类似于LE_227_Basic_Calculator_II， eval()在同一个stack中
         压入同级的元素（都是加的关系）。对每一对括号内的运算，应该可以用一个新的stack来计算，相对比较复杂，如果有n对嵌套的括号，
         那就同时需要n个stack，空间复杂度太高。

         这里用了一个技巧，用Long.MAX_VALUE来代表'(',这样用一个stack, 我们可以知道括号运算的起点。
     */

    class Solution1 {
        public int calculate(String s) {
            if (s == null || s.length() == 0) return 0;

            int n = s.length();
            char sign = '+';
            int res = 0;

            Stack<Long> s1 = new Stack<>();
            Stack<Character> s2 = new Stack<>();

            for (int i = 0; i < n; i++) {
                char c = s.charAt(i);
                if (Character.isDigit(c)) {
                    long num = 0;
                    while (i < n && Character.isDigit(s.charAt(i)) ) {
                        num = num * 10 + s.charAt(i) - '0';
                        /**
                         * !!!
                         */
                        i++;
                    }

                    s1.push(eval(sign, num, s1));
                    i--;
                } else if (c == '(') {
                    s1.push(Long.MAX_VALUE);
                    s2.push(sign);
                    /**
                     * !!!
                     */
                    sign = '+';
                } else if (c == ')') {
                    long num = 0;
                    /**
                     * need to know when to stop current level sum
                     * Use Long.MAX_VALUE to represent '('
                     */
                    while (s1.peek() != Long.MAX_VALUE) {
                        num += s1.pop();
                    }
                    s1.pop();//remove '('
                    char operator = s2.pop();
                    s1.push(eval(operator, num, s1));
                } else if (c == '+' || c == '-' || c == '*' || c == '/') {
                    sign = c;
                }
            }

            while (!s1.isEmpty()) {
                res += s1.pop();
            }
            return res;
        }

        private long eval(char sign, long num, Stack<Long> stack) {
            long ret = 0;
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

    /**
     * Solution 2
     * Come from lintcode 368, input is String Array, instead of String.
     * Save us some work on extracting number.
     * Same algorithm as Solution 1.
     */
    public class Solution2 {
        public int evaluateExpression(String[] expression) {
            if (expression == null || expression.length == 0) return 0;

            Stack<Long> stack = new Stack<>();
            Stack<String> operator = new Stack<>();
            String op = "+";
            int sum = 0;

            for (String s : expression) {
                if (isNumber(s)) {
                    stack.push(eval(Long.valueOf(s), op, stack));
                } else if (s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/")) {
                    op = s;
                } else if (s.equals("(")) {
                    stack.push(Long.MAX_VALUE);
                    operator.push(op);
                    op = "+";
                } else if (s.equals(")") ) {
                    long res = 0;
                    while (stack.peek() != Long.MAX_VALUE){
                        res += stack.pop();
                    }
                    stack.pop();
                    stack.push(eval(res, operator.pop(), stack));
                }
            }

            while (!stack.isEmpty()) {
                sum += stack.pop();
            }

            return sum;
        }

        private long eval(Long num, String op, Stack<Long> stack) {
            long ret = 0;
            if (op.equals("+")) {
                ret = num;
            } else if (op.equals("-")) {
                ret = -num;
            } else if (op.equals("*")) {
                ret = stack.pop() * num;
            } else if (op.equals("/") ) {
                ret = stack.pop() / num;
            }

            return ret;
        }

        /**
         * !!!
         */
        private boolean isNumber(String s) {
            if (s == null || s.length() == 0) return false;

            String regex = "\\d+";
            return s.matches(regex);
        }
    }

    class Solution_Practice {
        public int calculate(String s) {
            if (null == s || s.length() == 0) {
                return 0;
            }

            Deque<Long> nums = new ArrayDeque<>();
            Deque<Character> ops = new ArrayDeque<>();

            int num = 0;
            char op = '+';
            int len = s.length();
            int res = 0;

            char[] chars = s.toCharArray();
            for (int i = 0; i < len; i++) {
                if (Character.isDigit(chars[i])) {
                    while (i < len && Character.isDigit(chars[i])) {
                        num = num * 10 + chars[i] - '0';
                        i++;//!!!
                    }
                    i--;

                    process(nums, num, op);
                    num = 0;
                } else if (chars[i] == '+' || chars[i] == '-' || chars[i] == '*' || chars[i] == '/') {
                    op = chars[i];
                } else if (chars[i] == '(') {
                    nums.push(Long.MAX_VALUE);
                    ops.push(op);

                    /**
                     * !!!
                     */
                    op = '+';
                } else if (chars[i] == ')') {
                    int temp = 0;
                    while (nums.peek() != Long.MAX_VALUE) {
                        temp += nums.pop();
                    }
                    nums.pop();
                    process(nums, temp, ops.pop());
                }
            }

            while (!nums.isEmpty()) {
                res += nums.pop();
            }

            return res;
        }

        private void process(Deque<Long> nums, int num, char op) {
            long val = 0;
            if (op == '+') {
                val = num;
            } else if (op == '-') {
                val = -num;
            } else if (op == '*') {
                val = nums.pop() * num;
            } else if (op == '/') {
                val = nums.pop() / num;
            }

            nums.push(val);
        }
    }
}
