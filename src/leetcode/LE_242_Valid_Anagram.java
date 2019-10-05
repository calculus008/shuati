package leetcode;

import java.util.HashMap;

/**
 * Created by yuank on 4/3/18.
 */
public class LE_242_Valid_Anagram {
    /**
        Given two strings s and t, write a function to determine if t is an anagram of s.

        For example,
        s = "anagram", t = "nagaram", return true.
        s = "rat", t = "car", return false.

        Note:
        You may assume the string contains only lowercase alphabets.

        Follow up:
        What if the inputs contain unicode characters? How would you adapt your solution to such case?
     */

    //Time : O(n), Space : O(1)
    public boolean anagram(String s, String t) {
        /**
         * !!!
         * s.length() != t.length()
         */
        if (s == null || t == null || s.length() != t.length()) return false;

        int[] count = new int[256];
        for (char c : s.toCharArray()) {
            count[c]++;
        }

        for (char c: t.toCharArray()) {
            count[c]--;
        }

        int sum = 0;
        for (int n : count) {
            if (n != 0) return false;
        }

        return true;
    }

    /**
     * HashMap Solution, works for follow up question
     * For unicode character, key is a string, such as '\u0505'
     */
    public boolean isAnagram(String s, String t) {
        if(s.length() != t.length()){
            return false;
        }

        HashMap<Character, Integer> source = new HashMap();

        for(char c : s.toCharArray()){
            if(source.containsKey(c)){
                source.put(c, source.get(c) + 1);
            }else{
                source.put(c, 1);
            }
        }

        for(char c : t.toCharArray()){
            if(source.containsKey(c)){
                source.put(c, source.get(c) - 1);
                if(source.get(c) == 0){
                    source.remove(c);
                }
            }else{
                return false;
            }
        }

        return source.isEmpty();
    }
}
