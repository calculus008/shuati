package leetcode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LE_1153_String_Transforms_Into_Another_String {
    /**
     * Given two strings str1 and str2 of the same length, determine whether
     * you can transform str1 into str2 by doing zero or more conversions.
     *
     * In one conversion you can convert all occurrences of one character
     * in str1 to any other lowercase English character.
     *
     * Return true if and only if you can transform str1 into str2.
     *
     * Example 1:
     * Input: str1 = "aabcc", str2 = "ccdee"
     * Output: true
     * Explanation: Convert 'c' to 'e' then 'b' to 'd' then 'a' to 'c'. Note that the order of conversions matter.
     *
     * Example 2:
     * Input: str1 = "leetcode", str2 = "codeleet"
     * Output: false
     * Explanation: There is no way to transform str1 to str2.
     *
     * Note:
     * 1 <= str1.length == str2.length <= 10^4
     * Both str1 and str2 contain only lowercase English letters.
     *
     * Hard
     */

    /**
     * Most tricky part :
     * Suppose we have the simplest string with cycle ab->ba. Suppose you don't have a temp
     * char as bridge, ab->bb->aa (change a to b, and b to a), you'll never get ba. however,
     * if you use c as a temp transition, ab->ac->bc->ba (change b to c, a to b, and then c to a).
     *
     * The key note here is that you must change all occurrences in all middle stages. For example,
     * aabcc->ccbcc->??? If you don't know the first two cc were changed from aa, when you change
     * the last cc to ee, the first two cc also changes.
     *
     * 此题的对应有以下几个关系:
     * 1. 一对一，每一个char互相对应转换即可 a->b
     * 2. 多对一， aabcc,ccdee, a->c, c->e，其实只要有未在target string出现过的char，那么就可以拿来
     * 作为temp char桥梁，比如 a->g->c这样转换就不会同时影响c->e的转换
     * 3. 一对多，a->f, a->g 这样是绝对不可能的，因为char会被同时影响
     *
     * 另外还有一种特殊情况就是，source和target的unique char的数量是一样的时候，如果此时是26个
     * 则说明完全不能转换，因为没有extra的temp char作为转换的桥梁
     */
    class Solution {
        public boolean canConvert(String str1, String str2) {
            if (str1.equals(str2)) return true;

            int m = str1.length();
            int n = str2.length();

            if (m != n) return false;

            Map<Character, Character> map = new HashMap<>();

            for (int i = 0; i < m; i++ ) {
                char c1 = str1.charAt(i);
                char c2 = str2.charAt(i);

                if (!map.containsKey(c1)) {
                    map.put(c1, c2);
                } else {
                    char expected = map.get(c1);
                    if (c2 != expected) return false;
                }
            }

            Set<Character> set = new HashSet<>(map.values());

            return set.size() < 26;
        }
    }
}
