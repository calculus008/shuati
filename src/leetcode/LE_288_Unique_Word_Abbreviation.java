package leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuank on 4/19/18.
 */
public class LE_288_Unique_Word_Abbreviation {
    /**
     *
         An abbreviation of a word follows the form <first letter><number><last letter>.
         Below are some examples of word abbreviations:

         a) it                      --> it    (no abbreviation)

         1
         b) d|o|g                   --> d1g

         1    1  1
         1---5----0----5--8
         c) i|nternationalizatio|n  --> i18n

         1
         1---5----0
         d) l|ocalizatio|n          --> l10n

         Assume you have a dictionary and given a word,
         find whether its abbreviation is unique in the dictionary.

         A word's abbreviation is unique if no other word from the dictionary has the same abbreviation.

         Example:
         Given dictionary = [ "deer", "door", "cake", "card" ]

         isUnique("dear") -> false
         isUnique("cart") -> true
         isUnique("cane") -> false
         isUnique("make") -> true

        Medium

        A possible follow up question - LI_639_Word_Abbreviation
     */

    /**
     * 重要的是理解题意：
     *
     * 当 word -> abbreviation 在字典的所有abbreviation中不存在时， return true
     * 当 word 在字典中存在时, 如果没有其他字典中的词有相同的abbreviation, return true
     * dictionary只是个String Array, 里会有重复的词。
     * Actually, we don't care if there are duplicate words, we only care if the abbreviation is unique.
     */

    class ValidWordAbbr {
        HashMap<String, String> map;

        public ValidWordAbbr(String[] dictionary) {
            map = new HashMap<>();
            for (String s : dictionary) {
                String key = getKey(s);
                if (map.containsKey(key)) {
                    /**
                     * !!!
                     * 这个逻辑是防止dictionary里有重复的词。比如， 字典里有两个"dear"，
                     * 查询"dear"， 应该返回TRUE.
                     *
                     * #1.
                     * 如果value 不是当前的词， 比如：
                     * d2r -> dear
                     *
                     * now "door" comes in, use key "d2r", get "dear".
                     * then we know its abbr is NOT unique, we set value to ""
                     *
                     * #2.如果value 是当前的词，do nothing.
                     */
                    if (!map.get(key).equals(s)) {
                        map.put(key, "");//!!!
                    }
                } else {
                    map.put(key, s);
                }
            }
        }

        public boolean isUnique(String word) {
            return !map.containsKey(getKey(word)) || map.get(getKey(word)).equals(word);
        }

        private String getKey(String s) {
            /**
             * !!!
             * "<= 2" : example in the question itself, "dog" -> "d1g"
             */
            if (s.length() <= 2) {
                return s;
            } else {
                return s.charAt(0) + Integer.toString(s.length() - 2) + s.charAt(s.length() - 1);
            }
        }
    }

    public class ValidWordAbbr_JiuZhang {
        /**
         * two hashmap, save the frequency of original word and its abbreviation.
         *
         * Compare the frequency values for a word and its abbreviation to tell if
         * the abbreviation is unique.
         */
        Map<String, Integer> dict = new HashMap<>();
        Map<String, Integer> abbr = new HashMap<>();

        // @param dictionary a list of word
        public ValidWordAbbr_JiuZhang(String[] dictionary) {
            for (String d : dictionary) {
                dict.put(d, dict.getOrDefault(d, 0) + 1);
                String a = getAbbr(d);
                abbr.put(a, abbr.getOrDefault(a, 0) + 1);
            }
        }

        public boolean isUnique(String word) {
            // Write your code here
            String a = getAbbr(word);
            return dict.get(word) == abbr.get(a);
        }

        String getAbbr(String str) {
            if (str.length() <= 2) {
                return str;
            }
            return "" + str.charAt(0) + (str.length() - 2) + str.charAt(str.length() - 1);
        }
    }
}
