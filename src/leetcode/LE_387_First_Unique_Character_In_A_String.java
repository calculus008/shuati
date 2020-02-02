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
                /**
                 * !!!
                 * "count[chars[i]]"
                 */
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

            /**
             * If you check the implementation of LinkedHashMap's iterator.
             * It maintains a DLL internally and next call just returns the
             * head of the Linkedlist which is first element in this case
             * and moves the pointer to next element.
             */
            return map.size() == 0 ? -1 : map.entrySet().iterator().next().getValue();
        }
    }

    /**
     * 1。利用LinkedHashMap的特性，它保留元素的插入顺序。如果当前char已经出现过，则从
     * map中删掉。
     * 2。为什么要用set? 当char出现第二次时，已经被从map中删除，如果char出现第三次，单从map看，我们无法
     * 知道它是否出现过。所以要用set记录出现过的chars.
     * 3。最后，如果map为空，则所有char都重复出现过，返回-1.
     *    如果map不为空，则其iterator返回的第一个元素为答案。
     *
     *
     * One pass solution 可以用于对该问题on-line version的求解。这个解法不算最优，最优解法参照
     * LI_685_First_Unique_Number_In_Stream 中的Solution2.
     *
     * 实际上使用double linkedlist和set保存unique和repeated chars. 如果不要求自己实现double linkedlist,
     * 可以用LinkedHashSet (实际上这里并不需要用LinkedHashMap保存下标）。
     *
     * 这样，并不需要每次都做 set.add().
     */
    class Solution2_Practice {
        public int firstUniqChar(String s) {
            Map<Character, Integer> map = new LinkedHashMap<>();
            Set<Character> set = new HashSet<>();

            char[] ch = s.toCharArray();
            for (int i = 0; i < ch.length; i++) {
                char c = ch[i];
                if (!set.add(c)) {
                    if (map.containsKey(c)) {
                        map.remove(c);
                    }
                } else {
                    map.put(c, i);
                }
            }

            return map.size() == 0 ? -1 : map.entrySet().iterator().next().getValue();
        }
    }

    /**
     * One pass with array
     *
     * Time wise, better than the Solution1, we just need to scan input String once
     *
     * Time optimized
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

    /**
     * Tow pointers
     * Time  : O(n ^ 2)
     * Space : O(1),
     *
     * Optimized for space.
     *
     * fast pointer keeps moving and updates count[]
     * slow pointer moves only when encounter repeated char.
     */
    class Solution4 {
        public int firstUniqChar(String s) {
            if (s == null || s.length() == 0) {
                return -1;
            }

            int len = s.length();
            if (len == 1) {
                return 0;
            }

            char[] cc = s.toCharArray();
            int slow = 0;
            int fast = 1;
            int[] count = new int[256];
            count[cc[slow]]++;

            while (fast < len) {
                count[cc[fast]]++;
                /**
                 * if slow pointer is not a unique character anymore,
                 * move to the next unique one
                 * **/
                while (slow < len && count[cc[slow]] > 1) {
                    slow++;
                }

                if (slow >= len) {
                    return -1; // no unique character exist
                }

                fast++;
            }
            return slow;
        }
    }
}
