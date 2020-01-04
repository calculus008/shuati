package leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuank on 5/4/18.
 */
public class LE_316_Remove_Duplicate_Letters {
    /**
         Given a string which contains only lowercase letters, remove duplicate letters so that every letter appear once and only once.
         You must make sure your result is the smallest in lexicographical order among all possible results.

         Example:
         Given "bcabc"
         Return "abc"

         Given "cbacdcbc"
         Return "acdb"

         Hard
     */

    /**
     * https://stackoverflow.com/questions/34531748/how-to-get-the-smallest-in-lexicographical-order
     *
     * "the smallest in lexicographical order":
     *
     * You cannot reorder characters. You can only choose which occurrence to remove in case of duplicated characters.

         bcabc
         We can remove either first b or second b, we can remove either first c or second c. All together four outputs:

         ..abc
         .cab.
         b.a.c
         bca..
         Sort these four outputs lexicographically (alphabetically):

         abc
         bac
         bca
         cab
         And take the first one:

         abc

        Time and Space : O(n)
     */

    /**
     c b a c d c b c
     0 1 2 3 4 5 6 7

     map:
     a  2
     b  6
     c  7
     d  4

     Key:
     1.You cannot reorder characters. You can only choose which occurrence to remove in case of duplicated characters.
     2.The beginning index of the search range should be the index of previous determined letter plus one.
     3.The end index of the search should be updated only when the picked char is the last one in current range.

     c b a c d c b c
     0 1 2 3 4 5 6 7
     _
     c b a
     _
     c d
     _
     d
     _
     c b
     c
     res : a c d b

     */
    class Solution1 {
        public String removeDuplicateLetters(String s) {
            if (s == null || s.length() == 0) return "";

            Map<Character, Integer> map = new HashMap<>();
            for (int i = 0; i < s.length(); i++) {
                map.put(s.charAt(i), i);
            }

            int start = 0;
            int end = findEnd(map);
            char[] res = new char[map.size()];
            int size = map.size();

            for (int i = 0; i < size; i++) {
                char minChar = 'z' + 1;
                for (int j = start; j <= end; j++) {
                    char c = s.charAt(j);
                    if (map.containsKey(c) && c < minChar) {
                        minChar = c;
                        start = j + 1;
                    }
                }

                res[i] = minChar;
                map.remove(minChar);

                /**
                 Only when the removed char is the last one in current range, we seek to find the new end
                 */
                if (s.charAt(end) == minChar) {
                    end = findEnd(map);
                }
            }

            return new String(res);
        }

        public int findEnd(Map<Character, Integer> map) {
            int res = Integer.MAX_VALUE;
            for (int value : map.values()) {
                res = Math.min(res, value);
            }
            return res;
        }
    }

    /**
     * Use StringBuilder as Stack
     *
     * Time  : O(n)
     * Space : O(1)
     *
     * The average time complexity is linear because it iterates through all the characters in the loop for(char s:ch){..},
     * the stack can have at most 26 characters at a time so it is O(n) time and O(n) space (for the character array) solution.
     *
     * Time complexity : O(N).
     * Although there is a loop inside a loop, the time complexity is still O(N).
     * This is because the inner while loop is bounded by the total number of elements added to the stack
     * (each time it fires an element goes). This means that the total amount of time spent in the inner loop
     * is bounded by O(N), giving us a total time complexity of O(N)
     *
     * Space complexity : O(1). At first glance it looks like this is O(N)O(N), but that is not true! seen will
     * only contain unique elements, so it's bounded by the number of characters in the alphabet (a constant).
     * You can only add to stack if an element has not been seen, so stack also only consists of unique elements.
     * This means that both stack and seen are bounded by constant, giving us O(1) space complexity.
     */
    class Solution2 {
        public String removeDuplicateLetters(String s) {
            if (null == s || s.length() == 0) return s;

            StringBuilder sb = new StringBuilder();
            int[] count = new int[26];
            boolean[] used = new boolean[26];

            char[] chs = s.toCharArray();
            for (char c : chs) {
                count[c - 'a']++;
            }

            for (char c : chs) {
                /**
                 * !!!
                 * Important, we decrease count whenever see it, regardless of the rest of the operation.
                 * The count here simply says how many occurrence of this char has left int the string.
                 *
                 * Can't place this line after checking if it is used. Example:
                 *
                 * "bbcaac"
                 *
                 * When we gets to the 2nd 'b', if we put this line after checking used, count['b' - 'a'] will
                 * remain 1, so we will remove 'b', believing there's more of it in the remaining of the string,
                 * then the final result will be "ac", instead of "bac".
                 *
                 */
                count[c - 'a']--;

                if (used[c - 'a']) continue;

                while (sb.length() > 0 && sb.charAt(sb.length() - 1) > c && count[sb.charAt(sb.length() - 1) - 'a'] > 0) {
                    used[sb.charAt(sb.length() - 1) - 'a'] = false;
                    sb.deleteCharAt(sb.length() - 1);
                }

                sb.append(c);
                used[c - 'a'] = true;
            }

            return sb.toString();
        }
    }
}
