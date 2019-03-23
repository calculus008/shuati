package leetcode;

import java.util.List;
import java.util.Stack;

/**
 * Created by yuank on 3/18/18.
 */
public class LE_150_Evaluate_Reverse_Polish_Notation {
    /**
        Evaluate the value of an arithmetic expression in Reverse Polish Notation.

        Valid operators are +, -, *, /. Each operand may be an integer or another expression.

        Some examples:
          ["2", "1", "+", "3", "*"] -> ((2 + 1) * 3) -> 9
          ["4", "13", "5", "/", "+"] -> (4 + (13 / 5)) -> 6

        For Polish Notation, just need to scan from right to left, same logic.
    */

    //Time and Space : O(n)
    public static int evalRPN(String[] tokens) {
        Stack<Integer> stack = new Stack<>();
        int res = 0;

        for(String token : tokens) {
            if(token.equals("+")) {
                stack.push(stack.pop() + stack.pop());
            } else if(token.equals("-")) {
                int a = stack.pop();
                int b = stack.pop();
                stack.push(b - a);
            } else if(token.equals("*")) {
                stack.push(stack.pop() * stack.pop());
            } else if(token.equals("/")) {
                int a = stack.pop();//must do this way, because we do "b/a", can't do it in one line
                int b = stack.pop();

                if (a == 0) throw new IllegalArgumentException("divisor can't be 0.");

                stack.push(b / a);
//            } else if (token.equals("!")) {
//                int a = stack.pop();
//                stack.push(factorial(a));
            } else {
                stack.push(Integer.parseInt(token));
            }
        }

        return stack.pop();
    }

//    public double rpn (List<String> ops) throws IllegalArgumentException, ArithmeticException {
//
//    }

}
