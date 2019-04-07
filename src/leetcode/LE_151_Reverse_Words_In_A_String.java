package leetcode;

import java.util.Arrays;
import java.util.Collections;

/**
 * Created by yuank on 3/19/18.
 */
public class LE_151_Reverse_Words_In_A_String {
    /**
        Given an input string, reverse the string word by word.

        For example,
        Given s = "the sky is blue",
        return "blue is sky the".

         Example 1:
         Input: "the sky is blue"
         Output: "blue is sky the"

         Example 2:
         Input: "  hello world!  "
         Output: "world! hello"
         Explanation: Your reversed string should not contain leading or trailing spaces.

         Example 3:
         Input: "a good   example"
         Output: "example good a"
         Explanation: You need to reduce multiple spaces between two words to a single space
                      in the reversed string.
     */

    //Time : O(n), Space : O(n)
    public String reverseWords1(String s) {
        if (s == null || s.length() == 0) return s;

        String[] tokens = s.trim().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (int i = tokens.length - 1; i >= 0; i--) {
            sb.append(tokens[i]).append(" ");
        }

        return sb.toString().trim();
    }

    //Time : O(n), Space : O(n)
    public String reverseWords2(String s) {
         String[] words = s.trim().split(" +");
         Collections.reverse(Arrays.asList(words));
         return String.join(" ", words);
     }

    /**
     * Time  : O(n),
     * Space : O(1)
     **/
    class Solution {
        public String reverseWords3(String s) {
            if (null == s || s.length() == 0) return "";
            int n = s.length();

            char[] ch = s.toCharArray();
            reverse(ch, 0, n - 1);

            int i = 0, j = 0;

            while (i < n) {
                //!!!notice "i<j" and "j<i", a clever way of moving both pointers forward
                while (i < j || i < n && ch[i] == ' ') i++;
                while (j < i || j < n && ch[j] != ' ') j++;
                reverse(ch, i, j - 1);
            }

            return cleanup(ch);
        }

        /**
         * Just keep on space between words
         */
        private String cleanup(char[] ch) {
            int i = 0, j = 0;
            int n = ch.length;

            while (j < n) {
                while (j < n && ch[j] == ' ') j++;
                while (j < n && ch[j] != ' ') ch[i++] = ch[j++];
                while (j < n && ch[j] == ' ') j++;
                //!!!
                if (j < n) ch[i++] = ' ';
            }

            return new String(ch).substring(0, i);
        }

        private void reverse(char[] ch, int i, int j) {
            while (i < j) {
                char temp = ch[i];
                ch[i++] = ch[j];
                ch[j--] = temp;
            }
        }
    }

    /**
     * followup 如果word之间大于一个空格, 保留空格数，可能因为太紧张调来调去都不对，
     * 然后问小哥有没有hint.
     *
     * 结果小哥说了一个很好的方法 把中间的空格也按照word一样reverse 然后在整体reverse.
     *
     * 其实比原题简单，不用最后做cleanup
     */
    class Solution_FollowUp{
        public String reverseWords(String s) {
            if (s == null) {
                return null;
            }

            char[] arr = s.toCharArray();
            reverse(arr, 0, s.length() - 1);

            int left = 0; int right = 0;
            while (left < arr.length && right < arr.length) {
                while (left < arr.length && arr[left] == ' ') {
                    left++; // left is first non-space
                }

                right = left + 1;
                while (right < arr.length && arr[right] != ' ') {
                    right++; // right is first space
                }

                reverse(arr, left, right - 1);
                left = right;
            }

            return new String(arr);
        }

        void reverse(char[] arr, int left, int right) {
            while (left < right) {
                char tmp = arr[left];
                arr[left] = arr[right];
                arr[right] = tmp;

                left++;
                right--;
            }
        }
    }
}
