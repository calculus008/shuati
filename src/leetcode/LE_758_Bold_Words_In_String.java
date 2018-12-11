package leetcode;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class LE_758_Bold_Words_In_String {
    /**
         Given a set of keywords words and a string S, make all appearances of all keywords in S bold.
         Any letters between <b> and </b> tags become bold.

         The returned string should use the least number of tags possible, and of course the tags
         should form a valid combination.

         For example, given that words = ["ab", "bc"] and S = "aabcd", we should return "a<b>abc</b>d".
         Note that returning "a<b>a<b>b</b>c</b>d" would use more tags, so it is incorrect.

         Note:

         words has length in range [0, 50].
         words[i] has length in range [1, 10].
         S has length in range [0, 500].
         All characters in words[i] and S are lowercase letters.

         See LE_616_Add_Bold_Tag_In_String

         Easy
     */

    /**
     *  Solution 1
     *
     *  http://zxi.mytechroad.com/blog/string/leetcode-758-bold-words-in-string/
     *
     *  Time  : O(n * l ^ 2), l is max length of word in words
     *  Space : O(n + d), d is size of dict
     *
     *  21 ms
     */

    public String boldWords1(String[] words, String S) {
        if (null == words || null == S || S.length() == 0) {
            return "";
        }

        Set<String> dict = new HashSet<>();
        int max = 0;
        for (String word : words) {
            if(word != null) {
                max = Math.max(max, word.length());
                dict.add(word);
            }
        }

        int n = S.length();
        boolean[] bold = new boolean[n];

        for (int i = 0; i < n; i++) {
            /**
             * loop for length of word in dict,
             * optimize it by stargting from max length,
             * once we get a word, record it in bold[],
             * then we can break, no longer to check the
             * shorter ones.
             */
            for (int j = max; j >= 1; j--) {
                /**
                 * !!!
                 * Must take care the right boundary of the substring,
                 * otherwise it will out of boundary
                 */
                int end = Math.min(i + j, n);
                String s = S.substring(i, end);
                if (dict.contains(s)) {
                    Arrays.fill(bold, i, end, true);
                    break;
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        char[] c = S.toCharArray();
        for (int i = 0; i < n; i++) {
            /**
             * When decide if adding open or end tag, must consider two cases:
             * 1.Bold char i at the beginning (end) of S.(i = 0 or i = n - 1)
             * 2.Bold char in between (0 < i < n - 1)
             */
            if ((i == 0 && bold[i]) || (i > 0 && bold[i] && !bold[i - 1])) {
                sb.append("<b>");
            }

            sb.append(c[i]);

            if ((i == (n - 1) && bold[i]) || (i < (n - 1) && bold[i] && !bold[i + 1])) {
                sb.append("</b>");
            }
        }

        return sb.toString();
    }

    /**
     * Soluion 2
     * Same as LE_616_Add_Bold_Tag_In_String
     * 12 ms
     *
     * Time : O(n * l)
     */
    public String boldWords(String[] dict, String s) {
        if (null == s) return "";
        if (null == dict || dict.length == 0) return s;

        boolean[] b = new boolean[s.length()];
        int end = 0;
        for (int i = 0; i < s.length(); i++) {
            for (String word : dict) {
                //!!! startsWith(String, int)!!!
                if (s.startsWith(word, i)) {
                    end = Math.max(end, i + word.length());
                }

                if (i < end) {
                    b[i] = true;
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (!b[i]) {
                sb.append(s.charAt(i));
                continue;
            }

            int j = i;
            while (j < s.length() && b[j]) {
                j++;
            }

            sb.append("<b>").append(s.substring(i, j)).append("</b>");

            i = j - 1;
        }

        return sb.toString();
    }

}
