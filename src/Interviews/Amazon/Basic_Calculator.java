package Interviews.Amazon;

import java.util.ArrayDeque;
import java.util.Deque;

public class Basic_Calculator {
    class Solution_LE_224 {
        /**
         * s contains +, -, (, ) and space
         */
        public int calculate(String s) {
            if (null == s || s.length() == 0) {
                return 0;
            }

            Deque<Character> q = new ArrayDeque<>();
            for (char c : s.toCharArray()) {
                if (c != ' ') {
                    q.offer(c);
                }
            }
            q.offer('#');

            return helper(q);
        }

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
                        sum += cur;
                    } else if (preOp == '-') {
                        sum -= cur;
                    }

                    if (c == ')') break;

                    preOp = c;
                    cur = 0;
                }
            }

            return sum;
        }
    }

    class Solution_LE_227 {
        /**
         * s contains +, -, *, / and space, no parenthesis.
         *
         * Follow up:
         * Need to check for divide by zero
         */
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
                            pre /= cur; //if need to check devide by zero, throw exception here
                            break;
                    }

                    preOp = c;
                    cur = 0;
                }
            }

            return sum + pre;
        }
    }

    class Solution_LE_772 {
        /**
         * s contains +, -, *, / ,space and parenthesis.
         */
        public int calculate(String s) {
            if (null == s || s.length() == 0) {
                return 0;
            }

            Deque<Character> q = new ArrayDeque<>();
            for (char c : s.toCharArray()) {
                if (c != ' ') {
                    q.offer(c);
                }
            }
            q.offer('#');

            return helper(q);
        }

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
                    /**
                     * if asked to deal with factorial ('!') and '^':
                     *
                     * if (c == '!') {
                     *    int cur = fact(cur);
                     * }
                     */
                    if (preOp == '+') {
                        sum += pre;
                        pre = cur;
                    } else if (preOp == '-') {
                        sum += pre;
                        pre = -cur;
                    } else if (preOp == '*') {
                        pre *= cur;
                    } else if (preOp == '/') {
                        pre /= cur;
                    }

                    if (c == ')') break;

                    preOp = c;
                    cur = 0;
                }
            }

            return sum + pre;
        }
    }
}
