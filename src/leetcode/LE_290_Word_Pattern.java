package leetcode;

import java.util.HashMap;

/**
 * Created by yuank on 4/20/18.
 */
public class LE_290_Word_Pattern {
    /**
         Given a pattern and a string str, find if str follows the same pattern.

         Here follow means a full match, such that there is a bijection between a letter in pattern and a non-empty word in str.

         Examples:
         pattern = "abba", str = "dog cat cat dog" should return true.
         pattern = "abba", str = "dog cat cat fish" should return false.
         pattern = "aaaa", str = "dog cat cat dog" should return false.
         pattern = "abba", str = "dog dog dog dog" should return false.
         Notes:
         You may assume pattern contains only lowercase letters, and str contains lowercase letters separated by a single space.

        Easy
     */

    //Time and Space : O(n)
    //https://leetcode.com/problems/word-pattern/discuss/73402/8-lines-simple-Java
    public boolean wordPattern(String pattern, String str) {
        String[] words = str.split(" ");
        if(pattern.length() != words.length) {
            return false;
        }

        HashMap map = new HashMap();

        /**
         !!!HashMap.put() return value :
         Return the previous value associated with key, or null if there was no mapping for key.
         (A null return can also indicate that the map previously associated null with key)
         */
        for (int i = 0; i < words.length; i++) {
            //!!!!
            if (map.put(pattern.charAt(i), i) != map.put(words[i], i)) {
                return false;
            }
        }
        return true;
    }
}
