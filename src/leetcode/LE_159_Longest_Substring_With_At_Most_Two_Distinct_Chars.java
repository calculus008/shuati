package leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuank on 3/20/18.
 */
public class LE_159_Longest_Substring_With_At_Most_Two_Distinct_Chars {
    /*
        Given a string, find the length of the longest substring T that contains at most 2 distinct characters.

        For example, Given s = “eceba”,

        T is "ece" which its length is 3.
     */

    //Time and Space : O(n)

    public int lengthOfLongestSubstringTwoDistinct(String s) {
        if (s == null || s.length() == 0) return 0;

        int len = s.length();
        int start = 0;
        int end = 0;
        int res = 0;
        Map<Character, Integer> map = new HashMap<>();

        while (end < len) {
            if (map.size() <= 2) {//Move right side of the sliding window. No inner while loop, each time window just moves one step
                map.put(s.charAt(end), end);
                end++;
            }

            if (map.size() > 2) {//current sliding window moves out of its max position (window is no longer valid(has more than 2 distinct chars)), move left or start side to make the window valid again by removing the char which last appearance is the left most among all chars in the map.
                int leftmost = len;
                for (int num : map.values()) {//!!! map.values()
                    leftmost = Math.min(leftmost, num);
                }
                map.remove(s.charAt(leftmost));//!!!
                start = leftmost + 1;
            }
            res = Math.max(res, end - start); //normally, len should be end - start + 1, but here "end" is already one more than the end position for calculation, therefore just do "end - start"
        }

        return res;
    }
}
