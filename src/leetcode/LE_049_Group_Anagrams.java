package leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yuank on 2/28/18.
 */
public class LE_49_Group_Anagrams {
    /*
        Given an array of strings, group anagrams together.

        For example, given: ["eat", "tea", "tan", "ate", "nat", "bat"],
        Return:

        [
          ["ate", "eat","tea"],
          ["nat","tan"],
          ["bat"]
        ]
        Note: All inputs will be in lower-case.
     */

    //Time : O(m * n), Space: O(n)  counting sort
    public static List<List<String>> groupAnagrams(String[] strs) {
        List<List<String>> res = new ArrayList<>();
        if(null == strs || strs.length == 0) return res;

        Map<String, List<String>> map = new HashMap<>();

        for (String str : strs) {
            int[] count = new int[26];
            for (char c : str.toCharArray()) {
                count[c - 'a']++;
            }

            String s = "";
            for (int i = 0; i < count.length; i++) {
                if (count[i] > 0) {
                    s += String.valueOf(count[i]) + String.valueOf((char)('a' + i));
                }
            }

            if (!map.containsKey(s)) {
                map.put(s, new ArrayList<String>());
            }
            List<String> al = map.get(s);
            al.add(str);
        }

        return new ArrayList<>(map.values());
    }
}
