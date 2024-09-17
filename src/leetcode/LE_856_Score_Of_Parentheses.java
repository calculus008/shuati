package leetcode;

import java.util.Stack;

public class LE_856_Score_Of_Parentheses {
    /**
     * Given a balanced parentheses string S, compute the score of the string based on the following rule:
     *
     * () has score 1
     * AB has score A + B, where A and B are balanced parentheses strings.
     * (A) has score 2 * A, where A is a balanced parentheses string.
     *
     *
     * Example 1:
     * Input: "()"
     * Output: 1
     *
     * Example 2:
     * Input: "(())"
     * Output: 2
     *
     * Example 3:
     * Input: "()()"
     * Output: 2
     *
     * Example 4:
     * Input: "(()(()))"
     * Output: 6
     *
     * Note:
     * S is a balanced parentheses string, containing only ( and ).
     * 2 <= S.length <= 50
     *
     * Medium
     */

    /**
     * stack, space O(n)
     *
     * Each pair of '()' represent a level.
     *
     * We don't need to use a stack to save parentheses, instead, we just need a stack to save the value of the
     * the previous result in the same level or parallel result(in the same level).
     *
     * Example :
     * ((()))()()
     * ^^^   ^ ^
     * For each '(', we push to stack with value [0, 0, 0, 4, 5],
     *
     * (            (             (             )          )            )           (              )                (               )
     * push cur=0   push cur=0    push cur=0   n=1,cur=1   n=2,cur=2    n=4,cur=4   push cur=4     n=1,             push cur=5      n=1
     *                                         pop():0     pop():0      pop()=0     set cur=0      pop():4          set cur=0       pop():5
     *                                         cur=0+1=1   cur=0+2=2    cur=0+4=4                  cur=4+1=5                        cur=5+1=6
     *
     *
     */

    class Solution1_clean {
        public int scoreOfParentheses(String S) {
            int cur = 0;
            Stack<Integer> stack = new Stack<>();

            for (char c : S.toCharArray()) {
                if (c == '(') {
                    stack.push(cur);
                    cur = 0;
                } else if (c == ')') {
                    int n = cur == 0 ? 1 : cur * 2;
                    cur = stack.pop() + n;
                }
            }

            return cur;
        }
    }

    class Solution1 {
        public int scoreOfParentheses(String S) {
            int cur = 0;
            Stack<Integer> stack = new Stack<>();

            for (char c : S.toCharArray()) {
                if (c == '(') {
                    /**
                     * '(' represents the start of a parentheses entity, push cur to stack,
                     * reset cur to 0.
                     */
                    stack.push(cur);
                    cur = 0;
                } else if (c == ')') {
                    /**
                     * each ')' ends an parenthese entity, calculate "contained" values in n,
                     * then sum with previous same level entities, put in cur.
                     */
                    int n = cur == 0 ? 1 : cur * 2;
                    cur = stack.pop() + n;
                }
            }

            return cur;
        }
    }

    /**
     * Space : O(1)
     *
     * It just occurs to me the input can be treated as a tree. Every () is a tree node. ()() can be treated as two
     * sibling nodes while (()) can be treated as a parent and a child node. Each leaf node has value 1 while non-leaf
     * node has double the value of all of its direct children's value. All we have to do is to calculate the root node
     * value via post-order traverse. And this is exactly what the approach 1 did as it always get the value of deeper
     * layers before gets its' own value! For example, the S = '(()(()()))' can be treated as the tree below.
     *
     *                             (10)            layer 0
     *                             ／ \
     *                           (1)  (4)          layer 1
     *                                /  \
     *                              (1)  (1)       layer 2
     *
     * Basically, the approach 2 is using the same idea. As you can see, the value of the root node is the sum of each
     * leaf node value to the power of it's depth. There are three leaf nodes in the tree. One leaf in layer 1 and two
     * leafs in layer 2. So the final answer is 2^1 + 2^2 + 2^2 = 10.
     */
    class Solution2 {
        public int scoreOfParentheses(String S) {
            int score = 0;
            int depth = 0;

            for (int i = 0; i < S.length(); i++) {
                if (S.charAt(i) == '(') {
                    depth++;
                } else {
                    depth--;
                }

                if (S.charAt(i) == ')' && S.charAt(i - 1) == '(') {
                    // Whenever you meet a () pair, you multiply 1 by all the 2 outside of it, and accumulate the result
                    score += Math.pow(2, depth);
                }
            }

            return score;
        }
    }


}
