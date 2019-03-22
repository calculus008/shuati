package leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuank on 3/26/18.
 */
public class LE_205_Isomorphic_Strings {
    /**
        Given two strings s and t, determine if they are isomorphic.

        Two strings are isomorphic if the characters in s can be replaced to get t.

        All occurrences of a character must be replaced with another character while preserving the order of characters. No two characters may map to the same character but a character may map to itself.

        For example,
        Given "egg", "add", return true.

        Given "foo", "bar", return false.

        Given "paper", "title", return true.

        Note:
        You may assume both s and t have the same length.
     */


    /**
     * Solution 1 :
     * 建立s中char和t中char的映射关系。假定s和t是isomorphic, 则s和t在同一位置的char必然有一一对应的关系。
     * 不满足这一条件则必然为false
     * Time and Space : O(n)
     * **/
    public boolean isIsomorphic1(String s, String t) {
        if (s == null || t == null) return true;
        Map<Character, Character> map = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {
            char a = s.charAt(i);
            char b = t.charAt(i);
            if (map.containsKey(a)) {
                if (map.get(a) == b) {
                    continue;
                } else {
                    return false;
                }
            } else {
                if (!map.containsValue(b)) {//!!! "containsValue"!!!
                    map.put(a, b);
                } else {
                    return false;
                }
            }
        }
        return true;
    }


    /**
      Solution 2 :
      Time : O(n), Space : O(1)

      Case 1: s = "foo", t = "bar"
      i = 0:  ascii value - f -> 102, b -> 98, a[102] = b[98] = 98
      i = 1:  ascii value - o -> 111, a -> 97, a[111] = b[97] = 97
      i = 2:  ascii value - o -> 111, r -> 114, now a[111] =97, b[114] = 0, not equal, return false.

      Case 2: s = "egg", t = "abb"
      i = 0:  ascii value - e -> 101, a -> 97, a[101] = b[97] = 97
      i = 1:  ascii value - g -> 103, b -> 98, a[103] = b[98] = 98
      i = 2:  ascii value - g -> 103, b -> 98, a[103] = 98, b[98] = 98  equal, continue
      End of for loop, return ture
    */
    public boolean isIsomorphic2(String s, String t) {
        char[] a = new char[256];
        char[] b = new char[256];

        for (int i = 0; i < s.length(); i++) {
            if (a[s.charAt(i)] != b[t.charAt(i)]) {
                return false;
            } else {
                /**
                 * here : a[s.charAt(i)] == b[t.charAt(i)]
                 * 这包括两种情况：
                 * 1.a[] and b[] not set yet, they both have default value 0
                 * 2.a[] and b[] have been set with the following logic.
                 */
                a[s.charAt(i)] = t.charAt(i);
                b[t.charAt(i)] = t.charAt(i);
            }
        }
        return true;
    }
}
