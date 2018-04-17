package leetcode;

import java.util.Stack;

/**
 * Created by yuank on 4/4/18.
 */
public class LE_227_Basic_Calculator_II {
    /*
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
    /*
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

    public static void main(String[] args) {
        calculate2("3*2 + 4 - 5/2");
    }
}
