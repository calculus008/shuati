package lintcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yuank on 8/11/18.
 */
public class LI_010_String_Permutation_II {
    /**
         Given a string, find all permutations of it without duplicates.

         Example
         Given "abb", return ["abb", "bab", "bba"].

         Given "aabb", return ["aabb", "abab", "baba", "bbaa", "abba", "baab"].

         Easy
     */

    //Time : O(n!), Space : O(n)
    public List<String> stringPermutation2(String str) {
        List<String> res = new ArrayList<>();
        if (str == null) return res;
//        if (str.length() == 0 ) {
//            res.add("");
//            return res;
//        }

        //!!!
        char[] ch = str.toCharArray();
        Arrays.sort(ch);
        //!!!

        helper(ch, res, new StringBuilder(), new boolean[str.length()]);
        return res;
    }

    private void helper(char[] ch, List<String> res, StringBuilder sb, boolean[] visited) {
        if (sb.length() == ch.length) {
            res.add(sb.toString());
            return;
        }

        for (int i = 0; i < ch.length; i++) {
            if (visited[i]) {
                continue;
            }

            if (i > 0 && ch[i] == ch[i - 1] && !visited[i - 1]) {
                continue;
            }

            int len = sb.length();
            visited[i] = true;
            sb.append(ch[i]);
            helper(ch, res, sb, visited);
            visited[i] = false;
            sb.setLength(len);
        }
    }
}
