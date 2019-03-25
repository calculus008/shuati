package leetcode;

import java.util.*;

public class LE_387_First_Unique_Character_In_A_String {
    /**
     * Given a string, find the first non-repeating character in
     * it and return it's index. If it doesn't exist, return -1.
     *
     * Examples:
     *
     * s = "leetcode"
     * return 0.
     *
     * s = "loveleetcode",
     * return 2.
     * Note: You may assume the string contain only lowercase letters.
     */

    /**
     * HashMap 2 passes solution
     *
     * Time  : O(n)
     * Space : O(256) -> O(1)
     */
    class Solution1 {
        public int firstUniqChar(String s) {
            if (null == s || s.length() == 0) return -1;

            char[] chars = s.toCharArray();
            int[] count = new int[256];
            for (char c : chars) {
                count[c]++;
            }

            for (int i = 0; i < chars.length; i++) {
                if (count[chars[i]] == 1) {
                    return i;
                }
            }

            return -1;
        }
    }

    /**
     * LinkedHashMap, one pass
     */
    class Solution2 {
        public int firstUniqChar(String s) {
            Map<Character, Integer> map = new LinkedHashMap<>();
            Set<Character> set = new HashSet<>();
            for (int i = 0; i < s.length(); i++) {
                if (set.contains(s.charAt(i))) {
                    if (map.get(s.charAt(i)) != null) {
                        map.remove(s.charAt(i));
                    }
                } else {
                    map.put(s.charAt(i), i);
                    set.add(s.charAt(i));
                }
            }
            return map.size() == 0 ? -1 : map.entrySet().iterator().next().getValue();
        }
    }

    /**
     * One pass with array
     *
     * Time wise, better than the Solution1, we just need to scan input String once
     */
    class Solution3 {
        public int firstUniqChar(String s) {
            int[] pos = new int[26]; // the pos of first occurrence of char, if repeated, not existed, -1;
            Arrays.fill(pos, -1);
            /**
             * need to know if a char has appeared before.
             */
            Set<Character> appeared = new HashSet<>();

            for(int i = 0; i < s.length(); i++) {
                char ch = s.charAt(i);
                if(!appeared.contains(ch)) {
                    appeared.add(ch);
                    pos[ch - 'a'] = i;
                } else {
                    pos[ch - 'a'] = -1;
                }
            }

            int minIndex = Integer.MAX_VALUE;
            for(int i = 0; i < 26; i++) {
                if(pos[i] != -1) {
                    minIndex = Math.min(minIndex, pos[i]);
                }
            }

            return minIndex == Integer.MAX_VALUE ? -1 : minIndex;
        }
    }
}
