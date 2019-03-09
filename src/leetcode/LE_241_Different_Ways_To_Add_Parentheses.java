package leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yuank on 4/7/18.
 */
public class LE_241_Different_Ways_To_Add_Parentheses {
    /**
        Given a string of numbers and operators, return all possible results
        from computing all the different possible ways
        to group numbers and operators. The valid operators are +, - and *.


        Example 1
        Input: "2-1-1".

        ((2-1)-1) = 0
        (2-(1-1)) = 2
        Output: [0, 2]


        Example 2
        Input: "2*3-4*5"

        (2*(3-(4*5))) = -34
        ((2*3)-(4*5)) = -14
        ((2*(3-4))*5) = -10
        (2*((3-4)*5)) = -10
        (((2*3)-4)*5) = 10
        Output: [-34, -14, -10, -10, 10]
     */

    /**
     *  http://zxi.mytechroad.com/blog/leetcode/leetcode-241-different-ways-to-add-parentheses/

        Backtracking, same type as Word Break questions.

        https://www.youtube.com/watch?v=gxYV8eZY0eQ

        Time : O(n ^ 3) not sure, Space : O(n)
    */
    //Solution 1 : recursion  9 ms
    class Solution1 {
        public List<Integer> diffWaysToCompute(String input) {
            List<Integer> res = new ArrayList<>();
            if (input == null || input.length() == 0) return res;

            for (int i = 0; i < input.length(); i++) {
                char c = input.charAt(i);
                if (c == '+' || c == '-' || c == '*') {
                    String a = input.substring(0, i);
                    String b = input.substring(i + 1);
                    List<Integer> al = diffWaysToCompute(a);
                    List<Integer> bl = diffWaysToCompute(b);

                    for (int j : al) {
                        for (int k : bl) {
                            if (c == '+') {
                                res.add(j + k);
                            } else if (c == '-') {
                                res.add(j - k);
                            } else if (c == '*') {
                                res.add(j * k);
                            }
                        }
                    }
                }
            }

            if (res.size() == 0) {
                res.add(Integer.valueOf(input));
            }

            return res;
        }
    }

    //Solution 2 : Recursion with memorization, 3 ms
    class Solution2 {
        public List<Integer> diffWaysToCompute2(String input) {
            Map<String, List<Integer>> map = new HashMap<>();

            return getAns(input, map);
        }

        List<Integer> getAns(String input, Map<String, List<Integer>> map) {
            if (map.containsKey(input)) return map.get(input);

            List<Integer> res = new ArrayList<>();

            for (int i = 1; i < input.length(); i++) {
                char ch = input.charAt(i);
                if (ch == '+' || ch == '-' || ch == '*') {
                    String l = input.substring(0, i);
                    String r = input.substring(i + 1);

                    List<Integer> res1 = getAns(l, map);
                    List<Integer> res2 = getAns(r, map);

                    List<Integer> res3 = compute(res1, res2, ch);

                    res.addAll(res3);
                }
            }

            /**
             * !!!
             * This means current input is a number, no operand in it, memorize it.
             **/
            if (res.isEmpty()) {
                res.add(Integer.parseInt(input));
            }

            map.put(input, res);
            return res;
        }

        //compute Descartes product (笛卡尔积)
        List<Integer> compute(List<Integer> l1, List<Integer> l2, char operand) {
            List<Integer> res = new ArrayList<Integer>();
            for (Integer i : l1) {
                for (Integer j : l2) {
                    int k = 0;
                    if (operand == '+') {
                        k = i + j;
                    } else if (operand == '-') {
                        k = i - j;
                    } else if (operand == '*') {
                        k = i * j;
                    }
                    res.add(k);
                }
            }
            return res;
        }
    }
}
