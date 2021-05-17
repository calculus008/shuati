package leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuank on 3/20/18.
 */
public class LE_159_Longest_Substring_With_At_Most_Two_Distinct_Chars {
    /**
        Given a string, find the length of the longest substring T that contains at most 2 distinct characters.

        For example, Given s = “eceba”,

        T is "ece" which its length is 3.


        LE_340_Longest_Substring_With_At_Most_K_Distinct_Chars
        LE_904_Fruit_Into_Baskets
     */

    //Time and Space : O(n)

    public int lengthOfLongestSubstringTwoDistinct(String s) {
        if (s == null || s.length() == 0) return 0;

        int len = s.length();
        int start = 0;
        int end = 0;
        int res = 0;

        /**
         * map stores char and the index that the char appeared last time
         */
        Map<Character, Integer> map = new HashMap<>();

        /**
         * Move right side of the sliding window. No inner while loop,
         * each time window just moves one step
         **/
        while (end < len) {
            if (map.size() <= 2) {
                map.put(s.charAt(end), end);
                end++;
            }

            /**
             *
             * 1."(map.size() > 2" :
             *    condition to tell if current window is still valid
             *
             * current sliding window moves out of its max position
             * (window is no longer valid(has more than 2 distinct chars)),
             * move left or start side to make the window valid again by
             * removing the char which last appearance is the left most among
             * all chars in the dist.
             *
             * 2.since "map.size() <= 2" is the condition to do dist.put(),
             *  map.size() > 2 will happen.
             *
             **/
            if (map.size() > 2) {
                int leftmost = len;
                for (int num : map.values()) {//!!! map.values()
                    leftmost = Math.min(leftmost, num);
                }
                map.remove(s.charAt(leftmost));//!!!
                start = leftmost + 1;
            }

            /**
             * normally, len should be end - start + 1, but here "end" is already
             * one more than the end position for calculation, therefore just
             * do "end - start"
             **/
            res = Math.max(res, end - start);
        }

        return res;
    }
}
