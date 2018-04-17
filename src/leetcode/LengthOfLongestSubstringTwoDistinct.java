package leetcode;

import java.util.HashMap;

/**
 * Created by yuank on 7/19/16.
 */
public class LengthOfLongestSubstringTwoDistinct {

    public int lengthOfLongestSubstringTwoDistinct(String s) {
        if (null == s || s.length() < 1)
            return 0;

        int l = 0, h = 0, max = 0;
        int len = s.length();
        HashMap<Character, Integer> map = new HashMap<>();

        while (h < len) {
            if (map.size() <= 2) {
                map.put(s.charAt(h), h);
            }

            if (map.size() > 2) {
                int leftmost = len;
                for (int n : map.values()) {
                    leftmost = Math.min(leftmost, n);
                }
                map.remove(s.charAt(leftmost));
                l = leftmost + 1;
            }

            max = Math.max(max, h - l + 1);
            h++;
        }

        return max;
    }
}
