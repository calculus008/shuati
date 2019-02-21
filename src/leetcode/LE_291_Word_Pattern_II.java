package leetcode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by yuank on 4/20/18.
 */
public class LE_291_Word_Pattern_II {
    /**
         Given a pattern and a string str, find if str follows the same pattern.

         Here follow means a full match, such that there is a bijection between a
         letter in pattern and a non-empty substring in str.

         Examples:
         pattern = "abab", str = "redblueredblue" should return true.
         pattern = "aaaa", str = "asdasdasdasd" should return true.
         pattern = "aabb", str = "xyzabcxzyabc" should return false.

         Hard
     */

    /**
         Backtracking
         Time : O(2 ^ n) (not sure), Space : O(n)
         https://leetcode.com/problems/word-pattern-ii/discuss/73664/Share-my-Java-backtracking-solution

         we just have to keep trying to use a character in the pattern to match different length of substrings
         in the input string, keep trying till we go through the input string and the pattern.

         Time complexity: the problem is more like slicing the string into m pieces.
         How many slicing ways? C(n^m). For each slice, it takes O(n) to validate.
         So the total complexity is O(n * C(n^m))
     */
    class Solution1 {
        public boolean wordPatternMatch(String pattern, String str) {
            HashMap<Character, String> map = new HashMap<>();
            Set<String> set = new HashSet<>();
            return isMatch(str, 0, pattern, 0, map, set); //!!! must have "return"
        }

        public boolean isMatch(String str, int i, String pattern, int j, HashMap<Character, String> map, Set<String> set) {
            if (i == str.length() && j == pattern.length()) return true; //!!! "=="
            if (i == str.length() || j == pattern.length()) return false;

            char c = pattern.charAt(j); // get current pattern character
            if (map.containsKey(c)) {// if the pattern character exists
                String s = map.get(c);
                if (!str.startsWith(s, i)) {//!!!"startsWith", NOT "startWith"
                    return false;
                }

                // if it can match, great, continue to match the rest
                return isMatch(str, i + s.length(), pattern, j + 1, map, set);
            }

            // pattern character does not exist in the map
            for (int k = i; k < str.length(); k++) {//try in str, find which substring mapping with c can work out
                String cur = str.substring(i, k + 1);
                /**
                 !!!
                 use a hash set to avoid duplicate matches, if character "a" matches string "red",
                 then character "b" cannot be used to match "red", if duplicate matches is allowed,
                 we can remove the logic of HashSet
                 **/
                if (set.contains(cur)) continue;

                map.put(c, cur);
                set.add(cur);
                if (isMatch(str, k + 1, pattern, j + 1, map, set)) {
                    return true;
                }
                map.remove(c);
                set.remove(cur);
            }

            // we've tried our best but still no luck
            return false;
        }
    }

    //My version with same logic
    class Solution2 {
        public boolean wordPatternMatch_my(String pattern, String str) {
            if (pattern == null || str == null) return false;

            Map<Character, String> map = new HashMap<>();
            Set<String> set = new HashSet<>();
            return helper(pattern, str, 0, 0, map, set);
        }

        private boolean helper(String pattern, String str, int i, int j, Map<Character, String> map, Set<String> set) {
            if (i == pattern.length() && j == str.length()) return true;
            if (i == pattern.length() || j == str.length()) return false;

            char c = pattern.charAt(i);
            if (map.containsKey(c)) {
                String s = map.get(c);
                if (str.startsWith(s, j)) {
                    return helper(pattern, str, i + 1, j + s.length(), map, set);
                } else {/**!!!**/
                    return false;
                }
            }

            for (int k = j; k < str.length(); k++) {
                String t = str.substring(j, k + 1);
                if (set.contains(t)) {
                    continue;
                }

                set.add(t);
                map.put(c, t);
                /**
                 !!! return or not return? 不是直接返回 helper() 的返回值，
                 要先判断，如果helper()返回true, 则返回true, 如果返回false,
                 则回复set和map里的状态，继续回溯。
                 **/
                if (helper(pattern, str, i + 1, j + t.length(), map, set)) {
                    return true;
                } else {
                    set.remove(t);
                    map.remove(c);
                }
            }

            return false;
        }
    }
}
