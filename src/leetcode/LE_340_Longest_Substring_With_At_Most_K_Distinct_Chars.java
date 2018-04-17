package leetcode;

import java.util.HashMap;

/**
 * Created by yuank on 7/20/16.
 */
public class LE_340_Longest_Substring_With_At_Most_K_Distinct_Chars {
    /*
        Given a string, find the length of the longest substring T that contains at most k distinct characters.

        For example, Given s = “eceba” and k = 2,

        T is "ece" which its length is 3.
     */

    //Sliding Window, Same as LE_159
    public int lengthOfLongestSubstringKDistinct(String s, int k) {
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



}
