package leetcode;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;

/**
 * Created by yuank on 9/13/18.
 */
public class LE_394_Decode_String {
    /**
         Given an encoded string, return it's decoded string.

         The encoding rule is: k[encoded_string], where the encoded_string inside the square brackets
         is being repeated exactly k times. Note that k is guaranteed to be a positive integer.

         You may assume that the input string is always valid; No extra white spaces, square brackets are well-formed, etc.

         Furthermore, you may assume that the original data does not contain any digits
         and that digits are only for those repeat numbers, k. For example, there won't be input like 3a or 2[4].

         Examples:

         s = "3[a]2[bc]", return "aaabcbc".
         s = "3[a2[c]]", return "accaccacc".
         s = "2[abc]3[cd]ef", return "abcabccdcdcdef".
         s = 3[2[ad]3[pf]]xyz, return adadpfpfpfadadpfpfpfadadpfpfpfxyz

         Medium

         https://leetcode.com/problems/decode-string

         Related:
         LE_772_Basic_Calculator_III
     */

    /**
     * Preferred solution
     *
     * Queue + Recursion
     *
     * Same algorithm as LE_772_Basic_Calculator_III
     *
     * It needs to deal with nested coded string
     */
    class Solution_best {
        public String decodeString(String s) {
            Deque<Character> queue = new LinkedList<>();
            for (char c : s.toCharArray()) queue.offer(c);
            return helper(queue);
        }

        public String helper(Deque<Character> queue) {
            StringBuilder sb = new StringBuilder();
            int num = 0;
            while (!queue.isEmpty()) {
                char c= queue.poll();

                if (Character.isDigit(c)) {
                    num = num * 10 + c - '0';
                } else if (c == '[') {
                    String sub = helper(queue);
                    for (int i = 0; i < num; i++) {
                        sb.append(sub);
                    }
                    num = 0;
                } else if (c == ']') {
                    break;
                } else {
                    sb.append(c);
                }
            }
            return sb.toString();
        }
    }

    /**
     * ******************************************
     */

    /**
     * Solution 1 : Stack
     *
     *  Key : Use Stack!!!
     *
     *  To get number of token in "[]", we need to keep it until to the deepest level, Stack should be used.
     *
     *  Each '[]' indicates a new level of extraction. However, the repetition number is the one that right
     *  proceeds the '[]'. That's why we need to use Stack to save the number. It is saved for later usage.
     *
     *  Example : 3[2[ab]3[pf]]xyz
     *
     *  c == '3'
     *  s1  s2   res = ""
     *  3
     *
     *  c == '['
     *  s1  s2   res = ""
     *  3   ""
     *
     *  c =='2'
     *  s1  s2   res = ""
     *  2   ""
     *  3
     *
     *  c =='['
     *  s1  s2   res = ""
     *  2   ""
     *  3   ""
     *
     *  c == 'a', c == 'b'
     *  s1  s2   res = "ab"
     *  2   ""
     *  3   ""
     *
     *  c == ']'
     *  num = 2, res = "ab" -> temp = "" + "abab" -> res = "abab"
     *  s1  s2
     *  3   ""
     *
     *
     *  c == '3'  res = "abab"
     *  s1  s2
     *  3   ""
     *  3
     *
     *  c == '['   res = ""
     *  s1  s2
     *  3   "abab"
     *  3   ""
     *
     *  c == 'p' - 'f'  res = "pf"
     *  s1  s2
     *  3   "abab"
     *  3   ""
     *
     *
     *  c == ']'  temp = "abab" + "pfpfpf" -> res = "ababpfpfpf"
     *  s1  s2
     *  3   ""
     *
     *  c == ']'  temp = "" + "ababpfpfpfababpfpfpfababpfpfpf" -> res = "ababpfpfpfababpfpfpfababpfpfpf"
     *  s1  s2
     *
     *  ....
     *
     */
    public String expressionExpand(String s) {
        /**
         * s1 is used to save num in each level
         */
        Stack<Integer> s1 = new Stack<>();
        /**
         * s2 is used to save string in each level
         */
        Stack<String> s2 = new Stack<>();

        int i = 0;
        int len = s.length();
        /**
         * "res" is like the work table of the current level,
         * save to stack s2 and set to "" when go to the next level.
         * "save and clean table".
         *
         * "res" is only used to save none digit char
         */
        String res = "";

        while (i < len) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                /**
                 * assemble number for next level
                 */
                int num = 0;
                while (i < len && Character.isDigit(s.charAt(i))) {
                    num = num * 10  + s.charAt(i) - '0';
                    i++;
                }
                /** push number **/
                s1.push(num);
            } else if (c == '[') {
                /**
                 * '[' means the start of the next level, save the current one to s2.
                 */
                /** push String **/
                s2.push(res);//!!!
                res = "";//!!!
                i++;
            } else if (c == ']') {
                /**
                 * ']' means the end of the current level, append current level
                 * to the one saved in s1.
                 *
                 * Double pop : pop number and String from both stacks
                 */
                StringBuilder temp = new StringBuilder(s2.pop());//!!!
                int num = s1.pop();
                for (int j = 0; j < num; j++) {
                    temp.append(res);
                }
                res = temp.toString();
                i++;
            } else {
                /**
                 * assemble string in current level
                 */
                res += c;
                i++;
            }
        }

        return res;
    }


    /**
     * Solution 2 : DFS
     * */
    /**
     * !!!
     * Init global variable pos to pass process start position between recursion calls.
     * has to be init here.
     */
    int pos = 0;

    public String decodeStringDFS(String s) {
        StringBuilder sb = new StringBuilder();
        String num = "";

        /**
         * !!!"i = pos"
         */
        for (int i = pos; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                num += c;
            } else if(c == '[') {
                pos = i + 1;//!!!
                String next = decodeStringDFS(s);

                for (int j = 0; j < Integer.valueOf(num); j++) {
                    sb.append(next);
                }
                num = "";//!!!

                /**
                 * now pos has bee changed in the returned recursion call
                 * and points to the location of matching ']'
                 */
                i = pos;                // <-|  pass pos param between recursion calls
            } else if(c == ']') {       //   |
                pos = i;                //___|
                return sb.toString();
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }
}
