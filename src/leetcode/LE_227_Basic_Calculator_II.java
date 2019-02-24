package leetcode;

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
        3*2 + 4 - 5/2

      3  curVal = 0, sign = '+', stack :
      *  curVal = 3, sign = '+', stack : 3,      sign = '*'
      2  curVal = 2, sign = '*', stack : 3
      +  curVal = 2, sign = '*', stack : 6,      sign = '+'
      4  curVal = 4, sign = '+', stack : 6,
      -  curVal = 2, sign = '+', stack : 6,4,    sign = '-'
      5  curVal = 5, sign = '-', stack : 6,4,
      /  curVal = 5, sign = '-', stack : 6,4,-5, sign = '/'
      2  curVal = 2, sign = '/', stack : 6,4,-2,

      res = 6 + 4 - 2 = 8

    */
    public static int calculate1(String s) {
        if (s == null || s.length() == 0) return 0;

        Stack<Integer> stack = new Stack<>();
        int res = 0;
        int curVal = 0;
        char sign = '+';
        for (int i = 0; i < s.length(); i++) {
            //Routine to extract number from String
            // if (Character.isDigit(s.charAt(i))) {
            //     curVal = s.charAt(i) - '0';
            //     while (i + 1 < s.length() && Character.isDigit(s.charAt(i + 1))) {
            //         curVal = curVal * 10 + s.charAt(i + 1) - '0';
            //         i++;
            //     }
            //  }

            //Assume no space in the middle of a valid number
            if (Character.isDigit(s.charAt(i))) {
                curVal = curVal * 10 + s.charAt(i) - '0';
            }

            //关键 ： 保存上一个运算符，当遇到新的运算符，对'+'和'-',分别入栈正数和负数，对'*'和'/',pop出堆栈顶的数，计算，结果入栈，更新运算符。
            if (s.charAt(i) != ' ' && !Character.isDigit(s.charAt(i)) || i == s.length() - 1) {
                if (sign == '+') stack.push(curVal);
                if (sign == '-') stack.push(-curVal);
                if (sign == '*') stack.push(stack.pop() * curVal);
                if (sign == '/') stack.push(stack.pop() / curVal);

                if (i < s.length()) {
                    sign = s.charAt(i);
                }

                curVal = 0;
            }
        }

        for (int n : stack) {
            res += n;
        }

        return res;
    }

    //Time : O(n), Space : O(1)
    /*
        3*2+4-5/2

        Current : *
        Start : sign=+, preval=0, curVal=3, res=0
        End :   sign=*, preval=3, curVal=3, res=0

        Current : +
        Start : sign=*, preval=3, curVal=2, res=0
        End :   sign=+, preval=6, curVal=2, res=0

        Current : -
        Start : sign=+, preval=6, curVal=4, res=0
        End :   sign=-, preval=4, curVal=4, res=6

        Current : /
        Start : sign=-, preval=4, curVal=5, res=6
        End :   sign=/, preval=-5, curVal=5, res=10

        Current : 2
        Start : sign=/, preval=-5, curVal=2, res=10
        End :   sign=2, preval=-2, curVal=2, res=10

        res=8
     */
    public static int calculate2(String s) {
        if (s == null || s.length() == 0) return 0;

        //remove all spaces, !!! "s=" !!!
        s = s.trim().replaceAll(" +", "");

        System.out.println(s);

        int res = 0;
        int curVal = 0, preVal = 0;
        char sign = '+';
        for (int i = 0; i < s.length(); i++) {
            if (Character.isDigit(s.charAt(i))) {
                curVal = curVal * 10 + s.charAt(i) - '0';
            }

            if (!Character.isDigit(s.charAt(i)) || i == s.length() - 1) {
                System.out.println("Current : " + s.charAt(i));
                System.out.println("Start : sign=" + sign + ", preval=" + preVal + ", curVal=" + curVal + ", res=" + res);
                if (sign == '+') {
                    res += preVal;
                    preVal = curVal;
                }
                if (sign == '-') {
                    res += preVal;
                    preVal = -curVal;
                }
                if (sign == '*') {
                    preVal = preVal * curVal;
                }
                if (sign == '/') {
                    preVal = preVal / curVal;
                }

                if (i < s.length()) {
                    sign = s.charAt(i);
                }
                System.out.println("End :   sign=" + sign + ", preval=" + preVal + ", curVal=" + curVal + ", res=" + res);
                curVal = 0;
            }
        }

        //!!! don't forget to add the last one !!!
        res += preVal;

        System.out.println("res=" + res);

        return res;
    }

    /**
     * 因为没有括号，只有加减乘除， 所以：
     * 1.取出数字，根据当前的操作符，对乘除，运算优先，从stack中取出一个数，和当前数计算，将结果压入stack.
     * 2.遇到操作符号，记下当前的操作符。
     *
     * 关键 ：因为乘除优先，计算乘除，结果入栈。对加减，分别入栈正数和负数。最后，所有元素出栈并相加。
     */
    public int calculate3(String s) {
        if (s == null || s.length() == 0) return 0;

        int res = 0;
        int n = s.length();
        Stack<Integer> stack = new Stack<>();
        char op = '+';

        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                int num = 0;
                while (i < n && Character.isDigit(s.charAt(i)) ) {
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

    /**
     * Preferred Solution
     *
     * Use eval(), like LE_772_Basic_Calculator_III
     *
     * No parentheses, so no extra level to "recurse". No need to save operator in a separate stack
     */
    public int calculate4(String s) {
        if (s == null || s.length() == 0) return 0;

        int res = 0;
        int n = s.length();
        Stack<Integer> stack = new Stack<>();
        char op = '+';

        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                int num = 0;
                while (i < n && Character.isDigit(s.charAt(i)) ) {
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

    public static void main(String[] args) {
        calculate2("3*2 + 4 - 5/2");
    }
}
