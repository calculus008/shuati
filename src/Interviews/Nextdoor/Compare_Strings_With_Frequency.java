package Interviews.Nextdoor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Compare_Strings_With_Frequency {
    /**
     * Given two rules to define two strings are close enough.
     * 1. you can swap neighbor char any times. Ex. "abb" -> "bba"
     * 2. If two strings have the same character, then you can change the character into another.
     *
     * Ex. If both strings contain "a" and "b", you can change all "a"s in the first string or change
     * all "b"s in the first string. same as the second string
     * Ex.
     * Input: S1 = "babzccc", S2 = "abbzczz" Output: True
     *
     * compare两个string，只有小写字母。 每个stirng内部可以任意换位置，所以位置不重要。每个 string内部两个letter
     * 出现的频率也可以互换，所以这题只需要两个string每个frequency出现的 次数要一样。比如“babzccc” 和 “bbazzcz”
     * 就返回“true”，因为z和c可以互换频率。 但是 “babzcccm” 和 “bbazzczl” 就不一样，因为m在第一个里出现过，第二个
     * 里没有出现过。
     *
     * Sol: ​HashMap<Character, Integer> counts
     * Check if keySet() equals()
     * HashMap<Integer, Integer> counts of counts, check if the same
     */
    public static boolean compareString(String s1, String s2) {
        if (s1.length() != s2.length()) {
            return false;
        }

        /**
         * #1.Count frequency for s1 and s2 in two hashmaps
         */
        Map<Character, Integer> map1 = new HashMap<Character, Integer>();
        for (int i = 0; i < s1.length(); i++) {
            map1.put(s1.charAt(i), map1.getOrDefault(s1.charAt(i), 0) + 1);
        }

        Map<Character, Integer> map2 = new HashMap<Character, Integer>();
        for (int i = 0; i < s2.length(); i++) {
            map2.put(s2.charAt(i), map2.getOrDefault(s2.charAt(i), 0) + 1);
        }

        /**
         * #2.check keysets in each count map, see if there's any char that only exist in one string, return false
         */
        for (char ch : map1.keySet()) {
            if (!map2.containsKey(ch)) {
                return false;
            }
        }
        for (char ch : map2.keySet()) {
            if (!map1.containsKey(ch)) {
                return false;
            }
        }

        /**
         * #3.Make map to get frequency of frequencies. For example : s1 = "aaabbb", both 'a' and 'b' appear 3 times
         *    so in countS1:
         *    3 -> 2
         */
        Map<Integer, Integer> countS1 = new HashMap<Integer, Integer>();
        for (char ch : map1.keySet()) {
            int freq = map1.get(ch);
            countS1.put(freq, countS1.getOrDefault(freq, 0) + 1);
        }

        Map<Integer, Integer> countS2 = new HashMap<Integer, Integer>();
        for (char ch : map2.keySet()) {
            int freq = map2.get(ch);
            countS2.put(freq, countS2.getOrDefault(freq, 0) + 1);
        }

        for (int freq : countS1.keySet()) {
            if (countS2.containsKey(freq) ||countS1.get(freq) != countS2.get(freq)) {
                return false;
            }
        }

        return true;
    }

    public static boolean compare(String word1, String word2) {
        HashSet<Character> set_1 = new HashSet();
        HashSet<Character> set_2 = new HashSet();

        HashMap<Character, Integer> map1 = new HashMap();
        HashMap<Character, Integer> map2 = new HashMap();

        HashMap<Integer, Integer> feq1 = new HashMap();
        HashMap<Integer, Integer> feq2 = new HashMap();

        char[] character_1 = word1.toCharArray();
        char[] character_2 = word2.toCharArray();

        for (char c : character_1) {
            set_1.add(c);
            if (!map1.containsKey(c)) {
                map1.put(c, 0);
            }
            map1.put(c, map1.get(c) + 1);
        }

        for (char c : character_2) {
            set_2.add(c);
            if (!map2.containsKey(c)) {
                map2.put(c, 0);
            }
            map2.put(c, map2.get(c) + 1);
        }

        // compare two sets
        for (char c1 : set_1) {
            if (!set_2.contains(c1)) {
                return false;
            }
        }

        // compare frequency
        for (Character c : map1.keySet()) {
            int feq = map1.get(c);
            if (!feq1.containsKey(feq)) {
                feq1.put(feq, 0);
            }
            feq1.put(feq, feq1.get(feq) + 1);
        }

        for (Character c : map2.keySet()) {
            int feq = map2.get(c);
            if (!feq2.containsKey(feq)) {
                feq2.put(feq, 0);
            }
            feq2.put(feq, feq2.get(feq) + 1);
        }

        for (Integer feq : feq1.keySet()) {
            if (!feq2.containsKey(feq) || feq1.get(feq) != feq2.get(feq)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        boolean result = compare("abbcdea", "ebbccaa");
        System.out.println(result);
    }

}
