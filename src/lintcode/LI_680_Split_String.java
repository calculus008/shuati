package lintcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuank on 8/2/18.
 */
public class LI_680_Split_String {
    /**
         Give a string, you can choose to split the string after one character or two adjacent characters,
         and make the string to be composed of only one character or two characters. Output all possible results.

         Example
         Given the string "123"
         return [["1","2","3"],["12","3"],["1","23"]]

         Easy
     */

    //DFS
    public List<List<String>> splitString(String s) {
        List<List<String>> res = new ArrayList<>();
        if (s == null) {
            return res;
        }

        if (s.length() == 0) {
            res.add(new ArrayList<>());
            return res;
        }

        helper(s, 0, res, new ArrayList<>());
        return res;
    }

    private void helper(String s, int startIdx, List<List<String>> res, List<String> temp) {
        if (startIdx == s.length()) {
            res.add(new ArrayList<>(temp));
            return;
        }

        for (int i = startIdx; i < startIdx + 2 && i < s.length(); i++) {
            String cur = s.substring(startIdx, i + 1);
            temp.add(cur);
            helper(s, i + 1, res, temp);
            temp.remove(temp.size() - 1);
        }

//        for (int j = 1; j < 3; j ++){
//            if (startIdx + j <= s.length()) {
//                temp.add(s.substring(startIdx, startIdx + j));
//                helper(s, startIdx + j, res, temp);
//                temp.remove(temp.size() - 1);
//            }
//        }
    }

}
