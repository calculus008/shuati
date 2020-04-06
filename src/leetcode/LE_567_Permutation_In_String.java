package leetcode;

import java.util.*;

/**
 * Created by yuank on 8/15/18.
 */
public class LE_567_Permutation_In_String {
    /**
     Given two strings s1 and s2, write a function to return true if s2 contains the permutation of s1.
     In other words, one of the first string's permutations is the substring of the second string.

     Example 1:
     Input:s1 = "ab" s2 = "eidbaooo"
     Output:True
     Explanation: s2 contains one permutation of s1 ("ba").

     Example 2:
     Input:s1= "ab" s2 = "eidboaoo"
     Output: False

     Note:
     The input strings only contain lower case letters.
     The length of both given strings is in range [1, 10,000].

     !!! Same as LE_438_Find_All_Anagrams_In_A_String

     Medium
     */


    /**
     * Time : O(l1 + (l2 - l1) * 26), for every sliding window position, "Arrays.equals(map1, map2)" need to
     * iterate through dist, which has size 26, so it's constant.
     */
    class Solution1 {
        public boolean checkInclusion(String s1, String s2) {
            if (null == s1 || null == s2) return false;

            int l1 = s1.length();
            int l2 = s2.length();

            if (l2 < l1) return false;

            int[] map1 = new int[26];
            int[] map2 = new int[26];

            for (char c : s1.toCharArray()) {
                map1[c - 'a']++;
            }

            char[] chars = s2.toCharArray();
            for (int i = 0; i < l2; i++) {
                if (i >= l1) {
                    map2[chars[i - l1] - 'a']--;
                }

                map2[chars[i] - 'a']++;

                if (Arrays.equals(map1, map2)) {
                    return true;
                }
            }

            return false;
        }
    }

    /**
     * Time : O(l1 + l2)
     */
    class Solution2 {
        public boolean checkInclusion(String s1, String s2) {
            if (null == s1 || null == s2) return false;

            int l1 = s1.length();
            int l2 = s2.length();

            if (l2 < l1) return false;

            char[] chs1 = s1.toCharArray();
            char[] chs2 = s2.toCharArray();

            int[] map = new int[26];

            int sum = 0;
            for (int i = 0; i < l1; i++) {
                map[chs1[i] - 'a']--;
                map[chs2[i] - 'a']++;
            }

            for (int n : map) {
                sum += Math.abs(n);
            }

            if (sum == 0) return true;

            for (int i = l1; i < l2; i++) {
                int idx1 = chs2[i - l1] - 'a';
                int idx2 = chs2[i] - 'a';

                sum -= (Math.abs(map[idx1]) + Math.abs(map[idx2]));

                map[idx1]--;
                map[idx2]++;

                sum += (Math.abs(map[idx1]) + Math.abs(map[idx2]));

                if (sum == 0) return true;
            }

            return false;
        }
    }



    /**
     * http://zxi.mytechroad.com/blog/hashtable/leetcode-567-permutation-in-string/
     * <p>
     * Sliding Window + HashMap
     * <p>
     * Time  : O(l1 + (l2 - l1) * 26) = O(l1 + l2)
     * Space : O(26 * 2) = O(1)
     */
    public boolean checkInclusion(String s1, String s2) {
        if (s1 == null || s2 == null) return false;
        if (s1.length() == 0 || s2.length() == 0) return true;

        int l1 = s1.length();
        int l2 = s2.length();

        if (l1 > l2) return false;

        int[] m1 = new int[26];
        int[] m2 = new int[26];

        for (char c : s1.toCharArray()) {
            m1[c - 'a']++;
        }

        //!!! Sliding window
        for (int i = 0; i < l2; ++i) {
            if (i - l1 >= 0) {
                m2[s2.charAt(i - l1) - 'a']--;
            }

            m2[s2.charAt(i) - 'a']++;

            if (Arrays.equals(m1, m2)) return true;
        }

        return false;
    }

    /**
     * Variation
     * 给两个字符串A和B, 返回B里面出现的所有的A的permutation.
     * A = “abc”
     * B = “abcfecabaqebca”
     * output: [“abc”, “cab”, “bca”]
     */
    public static List<String> getAllPerm(String s1, String s2) {
        List<String> res = new ArrayList<>();
        if (s1 == null || s2 == null || s1.length() == 0 || s2.length() == 0) {
            return res;
        }

        int l1 = s1.length();
        int l2 = s2.length();

        if (l1 > l2) {
            return res;
        }

        int[] m1 = new int[26];
        int[] m2 = new int[26];

        for (char c : s1.toCharArray()) {
            m1[c - 'a']++;
        }

        //!!! Sliding window
        for (int i = 0; i < l2; ++i) {
            if (i >= l1) {
                m2[s2.charAt(i - l1) - 'a']--;
            }

            m2[s2.charAt(i) - 'a']++;

            if (Arrays.equals(m1, m2)) {
                res.add(s2.substring(i - l1 + 1, i + 1));
            }
        }

        return res;
    }

    public static void main(String[] args) {
        String s1 = "abc";
        String s2 = "abcfecabaqebca";
        List<String> res = getAllPerm(s1, s2);
        System.out.println(Arrays.toString(res.toArray()));
    }

    public class Solution_JiuZhang_1 {
        public boolean checkInclusion(String s1, String s2) {
            int len1 = s1.length(), len2 = s2.length();
            if (len1 > len2) return false;

            int[] count = new int[26];
            for (int i = 0; i < len1; i++) {
                count[s1.charAt(i) - 'a']++;
                count[s2.charAt(i) - 'a']--;
            }
            if (allZero(count)) return true;

            for (int i = len1; i < len2; i++) {
                count[s2.charAt(i) - 'a']--;
                count[s2.charAt(i - len1) - 'a']++;
                if (allZero(count)) return true;
            }

            return false;
        }

        private boolean allZero(int[] count) {
            for (int i = 0; i < 26; i++) {
                if (count[i] != 0) return false;
            }
            return true;
        }

    }

    class Solution_JiuZhang_2 {
        public boolean checkInclusion(String s1, String s2) {
            if (s1 == null || s2 == null) {
                return false;
            }

            Map<Character, Integer> map = new HashMap<>();
            for (char ch : s1.toCharArray()) {
                map.put(ch, map.getOrDefault(ch, 0) + 1);
            }

            int cnt = map.size();

            int start = 0, end = 0;

            while (end < s2.length()) {
                char chE = s2.charAt(end);
                if (map.containsKey(chE)) {
                    map.put(chE, map.get(chE) - 1);
                    if (map.get(chE) == 0) {
                        cnt--;
                    }
                }
                end++;

                while (cnt == 0) {
                    if (end - start == s1.length()) {
                        return true;
                    }

                    char chS = s2.charAt(start);
                    if (map.containsKey(chS)) {
                        map.put(chS, map.get(chS) + 1);
                        if (map.get(chS) > 0) {
                            cnt++;
                        }
                    }
                    start++;
                }
            }

            return false;
        }
    }



}
