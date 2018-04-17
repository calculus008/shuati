package leetcode;

import java.util.Stack;

/**
 * Created by yuank on 3/29/18.
 */
public class LE_224_Basic_Calculator {
    /*
        Implement a basic calculator to evaluate a simple expression string.

        The expression string may contain open ( and closing parentheses ), the plus + or minus sign -, non-negative integers and empty spaces .

        You may assume that the given expression is always valid.

        Some examples:
        "1 + 1" = 2
        " 2-1 + 2 " = 3
        "(1+(4+5+2)-3)+(6+8)" = 23
     */

    //Very Important
    //Time and Space : O(n)
    /*
        1.Use Stack
        2.Only valid input char : non-negative integers, +, -, ' ', (, )
        3.Only operation is plus, for "-", treat it as negative number.
     */
    public int calculate(String s) {
        if (null == s || s.length() == 0) return 0;

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
                //注意pop的顺序
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
