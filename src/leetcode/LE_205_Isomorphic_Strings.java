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

        All occurrences of a character must be replaced with another character while preserving the order of characters. No two characters may dist to the same character but a character may dist to itself.

        For example,
        Given "egg", "add", return true.

        Given "foo", "bar", return false.

        Given "paper", "title", return true.

        Note:
        You may assume both s and t have the same length.

        Same question - LE_890_Find_And_Replace_Pattern
     */


    /**
     * Solution 1 :
     * HashMap
     * 建立s中char和t中char的映射关系。假定s和t是isomorphic, 则s和t在同一位置的char必然有一一对应的关系。
     * 不满足这一条件则必然为false
     *
     * Time  : O(n * l), l is length of s. So this is NOT ideal, best solution is Solution2
     * Space : O(n)
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
                /**
                 * https://stackoverflow.com/questions/16757359/what-is-the-time-complexity-of-hashmap-containsvalue-in-java/16757380
                 * dist.containsValue() takes O(n)
                 */
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

      Case 3 : s = "aab", t = "bbc"
      a[1] = b[2] = 0
      a[1] = b[2] = 2
      a[1] = b[2] = 2
      a[2] = 0, b[2] = 2, false
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

    /**
     * Two strings are isomorphic if the positions of the characters follow the
     * same pattern. So I'm using maps to compare the position patterns.
     *
     * For example:
     *
     * String 1:              A B E A C D B
     * index pattern:         0 1 2 0 4 5 1
     * String 2:              X Y I X H K Y
     * index pattern:         0 1 2 0 4 5 1
     */
    public boolean isIsomorphic3(String s, String t) {
        if (s == null || t == null) return false;
        if (s.length() != t.length()) return false;

        Map<Character, Integer> mapS = new HashMap<Character, Integer>();
        Map<Character, Integer> mapT = new HashMap<Character, Integer>();

        for (int i = 0; i < s.length(); i++) {
            int indexS = mapS.getOrDefault(s.charAt(i), -1);
            int indexT = mapT.getOrDefault(t.charAt(i), -1);

            if (indexS != indexT) {
                return false;
            }

            mapS.put(s.charAt(i), i);
            mapT.put(t.charAt(i), i);
        }

        return true;
    }

    /**
     * Can't just use one dist to dist char in s to char in t, for example :
     *
     * s: "ab"
     * t: "aa"
     *
     * for index 1, no key for 'b', it will just add and pass. But actually, 'a'
     * in t has been mapped to 'a' when index is 0.
     *
     * Therefore we must dist it to a 3rd party value.
     *
     */
    public boolean isIsomorphic4(String s, String t) {
        if (null == s || null == t) return false;

        int m = s.length();
        int n = t.length();

        if (m != n) return false;

        char[] a = new char[256];
        char[] b = new char[256];
        int i = 0;

        while (i < m) {
            if(a[s.charAt(i)] != b[t.charAt(i)]) return false;

            a[s.charAt(i)] = t.charAt(i);
            b[t.charAt(i)] = t.charAt(i);

            i++;
        }

        return true;
    }
}
