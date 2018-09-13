package leetcode;

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
     */

    /**
     * Solution 1 : Stack
     *
     *  Key : Use Stack!!!
     *
     *  To get number of token in "[]", we need to keep it until to the deepest level, Stack should be used.
     *
     *  Example : 3[2[ad]3[pf]]xyz
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
        Stack<Integer> s1 = new Stack<>();
        Stack<String> s2 = new Stack<>();

        int i = 0;
        int len = s.length();
        String res = "";

        while (i < len) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                int num = 0;
                while (i < len && Character.isDigit(s.charAt(i))) {
                    num = num * 10  + s.charAt(i) - '0';
                    i++;
                }
                s1.push(num);
            } else if (c == '[') {
                s2.push(res);//!!!
                res = "";//!!!
                i++;
            } else if (c == ']') {
                StringBuilder temp = new StringBuilder(s2.pop());//!!!
                int num = s1.pop();
                for (int j = 0; j < num; j++) {
                    temp.append(res);
                }
                res = temp.toString();
                i++;
            } else {
                res += c;
                i++;
            }
        }

        return res;
    }


    /**
     * Solution 2 : DFS
     * */
    private int pos = 0;
    public String decodeStringDFS(String s) {
        StringBuilder sb = new StringBuilder();
        String num = "";
        for (int i = pos; i < s.length(); i++) {
            if (s.charAt(i) != '[' && s.charAt(i) != ']' && !Character.isDigit(s.charAt(i))) {
                sb.append(s.charAt(i));
            } else if (Character.isDigit(s.charAt(i))) {
                num += s.charAt(i);
            } else if (s.charAt(i) == '[') {
                pos = i + 1;
                String next = decodeStringDFS(s);
                for (int n = Integer.valueOf(num); n > 0; n--) {
                    sb.append(next);
                }
                num = "";
                i = pos;
            } else if (s.charAt(i) == ']') {
                pos = i;
                return sb.toString();
            }
        }
        return sb.toString();
    }
}
