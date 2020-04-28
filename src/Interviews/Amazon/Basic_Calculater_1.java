package Interviews.Amazon;

import java.util.ArrayDeque;
import java.util.Deque;

public class Basic_Calculater_1 {
    public static int calculate(String s) {
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

    private static int helper(Deque<Character> q) {
        int sum = 0, pre = 0, cur = 0, pre1 = 0;
        char preOp = '+';
        char preOp1 = '+';

        while (!q.isEmpty()) {
            char c = q.poll();

            if (c >= '0' && c <= '9') {
                cur = cur * 10 + c - '0';
            } else if (c == '(') {
                cur = helper(q);
            } else {
                if (c == '!') {
                    cur = fact(cur);
                    System.out.println(cur);
                }

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

    private static int fact(int n) {
        int res = 1;
        for (int i = 2; i <= n; i++) {
            res *= i;
        }
        return res;
    }

    public static void main(String[] args) {
        String s = "1 + 3 ^ 2 + 4";

        System.out.println(calculate(s));
    }
}
