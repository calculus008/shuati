package leetcode;

import java.util.HashMap;

/**
 * Created by yuank on 7/20/16.
 */
public class LE_340_Longest_Substring_With_At_Most_K_Distinct_Chars {
    /**
        Given a string, find the length of the longest substring T that contains at most k distinct characters.

        For example, Given s = “eceba” and k = 2,

        T is "ece" which its length is 3.
     */

    /**
     * Solution 3
     * Same Sliding window algorithm, use int[] as dist and iterate through char array "chars",
     * fasted running time on lintcode
     */
    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        if (s == null || s.length() == 0) return 0;

        int res = Integer.MIN_VALUE;
        int[] map = new int[256];
        int count = 0;
        char[] chars = s.toCharArray();

        for (int i = 0, j = 0; i < chars.length; i++) {
            if (map[chars[i]] == 0) {
                count++;
            }
            map[chars[i]]++;

            while (count > k) {
                map[chars[j]]--;
                if (map[chars[j]] == 0) {
                    count--;
                }
                /**
                 * !!!
                 * 最后才对j加一。因为上面的logic要reference当前的j值。
                 */
                j++;
            }

            res = Math.max(res, i - j + 1);
        }

        return res;
    }

    /**
     * Sliding Window, Same as LE_159_Longest_Substring_With_At_Most_Two_Distinct_Chars
     **/
    public int lengthOfLongestSubstringKDistinct1(String s, int k) {
        if(null == s || k <=0 )
            return 0;

        int len = s.length();
        if(len <= 1)
            return len;

        int h=0, l=0, max =0;
        HashMap<Character, Integer> map = new HashMap<>();

        while(h < len){
            if(map.size() <= k){
                map.put(s.charAt(h), h);
            }

            if(map.size() > k){
                int leftmost = Integer.MAX_VALUE;
                for(int n : map.values()){
                    leftmost = Math.min(leftmost, n);
                }
                l = leftmost + 1;
                map.remove(s.charAt(leftmost));
            }

            max = Math.max(max, h - l + 1);
            h++;
        }

        return max;
    }

    /**
     * Solution 2
     * Use HashMap wth JiuZhang sliding window template
     */
    public int lengthOfLongestSubstringKDistinct2(String s, int k) {
        if (s == null || s.length() == 0) return 0;

        int res = Integer.MIN_VALUE;
        HashMap<Character, Integer> map = new HashMap<>();

        for (int i = 0, j = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            map.put(c, map.getOrDefault(c, 0) + 1);

            while (map.size() > k) {
                char c1 = s.charAt(j);
                j++;
                int val = map.get(c1) - 1;

                if (val == 0) {
                    map.remove(c1);
                } else {
                    map.put(c1, val);
                }
            }

            res = Math.max(res, i - j + 1);
        }

        return res;
    }


}
