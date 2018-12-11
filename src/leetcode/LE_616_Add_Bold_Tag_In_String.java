package leetcode;

public class LE_616_Add_Bold_Tag_In_String {
    /**
         Given a string s and a list of strings dict, you need to add a closed pair of bold tag <b> and </b>
         to wrap the substrings in s that exist in dict. If two such substrings overlap,
         you need to wrap them together by only one pair of closed bold tag. Also,
         if two substrings wrapped by bold tags are consecutive, you need to combine them.

         Example 1:
         Input:
         s = "abcxyz123"
         dict = ["abc","123"]
         Output:
         "<b>abc</b>xyz<b>123</b>"

         Example 2:
         Input:
         s = "aaabbcc"
         dict = ["aaa","aab","bc"]
         Output:
         "<b>aaabbc</b>c"

         Note:
         The given dict won't contain duplicates, and its length won't exceed 100.
         All the strings in input have length in range [1, 1000].

         Medium
     */

    /**
     * Solution 1 for LE_758_Bold_Words_In_String LTE for this problem.
     *
     * In 758, max length of word is 10, a O(n * l ^ 2) solution can work.
     * But for ths problem, there's no limit for word max length, when l is really
     * long, it will LTE.
     *
     * Time  : O(n * m * l), m is dict size, l is length of word.
     * Space : O(n);
     */

    public String addBoldTag(String s, String[] dict) {
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
            while (j < s.length() && b[j]) j++;
            sb.append("<b>").append(s.substring(i, j)).append("</b>");
            i = j - 1;
        }

        return sb.toString();
    }
}
