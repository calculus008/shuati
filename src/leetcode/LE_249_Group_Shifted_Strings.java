package leetcode;

import java.util.*;

/**
 * Created by yuank on 4/6/18.
 */
public class LE_249_Group_Shifted_Strings {
    /**
        Given a string, we can "shift" each of its letter to its successive letter,
        for example: "abc" -> "bcd". We can keep "shifting" which forms the sequence:

        "abc" -> "bcd" -> ... -> "xyz"
        Given a list of strings which contains only lowercase alphabets, group all
        strings that belong to the same shifting sequence.

        For example, given: ["abc", "bcd", "acef", "xyz", "az", "ba", "a", "z"],
        A solution is:

        [
          ["abc","bcd","xyz"],
          ["az","ba"],
          ["acef"],
          ["a","z"]
        ]
     */

    public List<List<String>> groupStrings(String[] strings) {
        List<List<String>> res = new ArrayList<>();
        if (strings == null || strings.length == 0) return res;

        /**
            实际上就是扫一遍strings,把同一类的string放在一起。关键是如何产生map中的key, 这里的方法：
            把shifted的string映射到以‘a'为开头的base状态。比如：

            “def", "bcd" : 映射到key "abc"
            "cd", "za" :  映射到key "ab"

            以这种映射，string中的char不必是相邻的。比如：

            “df", "ce" : 映射到key "ac"
        */
        Map<String, List<String>> map = new HashMap<>();
        for (String s : strings) {
            String key = "";
            int offset = s.charAt(0) - 'a';

            for (int i = 0; i < s.length(); i++) {
                char c = (char)(s.charAt(i) - offset);
                /**
                 * For example :
                 * "za", offset = 25, for 2nd char 'a', 'a' - 25 < 'a',
                 * 'a' - 25 + 26 = 'a' + 1 = 'b'
                 **/
                if (c < 'a') {
                    c += 26;
                }
                key += c;
            }
            if (!map.containsKey(key)) {
                map.put(key, new ArrayList<String>());
            }
            map.get(key).add(s);
        }

        for (List<String> list : map.values()) {
            res.add(list);
        }

        return res;
    }

    //另一种写法
    public List<List<String>> groupStrings2(String[] strings) {
        List<List<String>> result = new ArrayList<>();
        Map<String, List<String>> map = new HashMap<>();

        for (String s : strings) {
            if (null != s && !"".equals(s)) {
                String key = getKey(s);
                if (!map.containsKey(key)) {
                    map.put(key, new ArrayList<>());
                }
                map.get(key).add(s);
            }
        }

        for (List<String> v : map.values()) {
            Collections.sort(v);
            result.add(v);
        }

        return result;
    }

    private String getKey(String s) {
        int[] nums = new int[s.length()];

        nums[0] = 0;
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) - s.charAt(0) < 0) {
                nums[i] = (s.charAt(i) - s.charAt(0)) % 26 + 26;
            } else {
                nums[i] = s.charAt(i) - s.charAt(0);
            }
        }

        return Arrays.toString(nums);
    }

}
