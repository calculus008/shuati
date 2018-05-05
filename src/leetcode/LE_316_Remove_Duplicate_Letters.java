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
