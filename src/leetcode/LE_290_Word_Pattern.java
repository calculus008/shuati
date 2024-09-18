package leetcode;

import java.util.*;

/**
 * Created by yuank on 4/20/18.
 */
public class LE_290_Word_Pattern {
    /**
         Given a pattern and a string str, find if str follows the same pattern.

         Here follow means a full match, such that there is a bijection between a
         letter in pattern and a non-empty word in str.

         Examples:
         pattern = "abba", str = "dog cat cat dog" should return true.
         pattern = "abba", str = "dog cat cat fish" should return false.
         pattern = "aaaa", str = "dog cat cat dog" should return false.
         pattern = "abba", str = "dog dog dog dog" should return false.

         Notes:
         You may assume pattern contains only lowercase letters, and str contains
         lowercase letters separated by a single space.

        Easy
     */

    class Solution_two_maps_clean {
        public boolean wordPattern(String pattern, String s) {
            String[] words = s.split(" ");
            if (pattern.length() != words.length) return false;

            Map<Character, Integer> map1 = new HashMap<>();
            Map<String, Integer> map2 = new HashMap<>();

            for (Integer i = 0; i < pattern.length(); i++) {
                Integer a = map1.put(pattern.charAt(i), i);
                Integer b = map2.put(words[i], i);
                if (a != b) return false;
            }

            return true;
        }
    }

    /**
     * Difference from LE_291_Word_Pattern_II : words are separated by space.
     */

    class Solution {
        //https://leetcode.com/problems/word-pattern/discuss/73402/8-lines-simple-Java
        public boolean wordPattern1(String pattern, String str) {
            String[] words = str.split(" ");
            if(pattern.length() != words.length) {
                return false;
            }

            HashMap map = new HashMap();

            /**
             !!!HashMap.put return value :
             The previous value associated with key, or null if there was no mapping for key.
             (A null return can also indicate that the map previously associated null with key)
             */

            /**
             !!!!! "Integer", NOT "int"
             "i being an Integer object, which allows to compare with just !=
             because there’s no autoboxing-same-value-to-different-objects-problem anymore"

             */
            for (Integer i = 0; i < words.length; i++) {
                if (map.put(pattern.charAt(i), i) != map.put(words[i], i)) {
                    return false;
                }
            }
            return true;
        }

        //using int and 2 maps solution
        public boolean wordPattern(String pattern, String str) {
            String[] words = str.split(" ");
            if(words.length != pattern.length()){
                return false;
            }

            HashMap<String, Integer> map1 = new HashMap<>();
            HashMap<Character, Integer> map2 = new HashMap<>();

            for(int i = 0; i < pattern.length(); i++){
                Integer a = map1.put(words[i], i);
                Integer b = map2.put(pattern.charAt(i), i);

                if (a == null && b == null) continue;
                if ((a != null && b == null) || (a == null && b != null)) return false;

                // if(map1.put(words[i], i) != map2.put(pattern.charAt(i), i) ){
                if (a.intValue() != b.intValue()) {
                    return false;
                }
            }

            return true;
        }

        /**
             Just want to point out one thing about autoboxing. As mentioned by @StefanPochmann, we can try such an example:

             int i = 10;
             Integer a = i;
             Integer b = i;
             System.out.println(a == b); //guess what is the output?
             The output was supposed to be false. However, you can test this and you will find it is true.
             Why?

             Because "The JVM is caching Integer values. == only works for numbers between -128 and 127 "
             Then you can try another code:

             int i = 1000; //greater than 127
             Integer a = i;
             Integer b = i;
             System.out.println(a == b); //this time we got false
             Look, now you get false. And now it explains why we can pass the small cases (because the indices are in the range of -128 and 127).
             We also know why it cannot pass the larger test case.
         */
    }
}
