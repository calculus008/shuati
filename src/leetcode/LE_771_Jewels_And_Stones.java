package leetcode;

import java.util.*;

/**
 * Created by yuank on 11/30/18.
 */
public class LE_771_Jewels_And_Stones {
    /**
         You're given strings J representing the types of stones that are jewels,
         and S representing the stones you have.  Each character in S is a type of
         stone you have.  You want to know how many of the stones you have are also jewels.

         The letters in J are guaranteed distinct, and all characters in J and S are letters.
         Letters are case sensitive, so "a" is considered a different type of stone from "A".

         Example 1:

         Input: J = "aA", S = "aAAbbbb"
         Output: 3
         Example 2:

         Input: J = "z", S = "ZZ"
         Output: 0

         Note:
         S and J will consist of letters and have length at most 50.
         The characters in J are distinct.

         Easy
     */

    public int numJewelsInStones(String J, String S) {
        if (J == null || S == null) return 0;

        int[] map = new int[128];

        for (char c : S.toCharArray()) {
            map[(int)c]++;
        }

        int res = 0;

        for (char c : J.toCharArray()) {
            res += map[(int)c];
        }

        return res;
    }


    public int numJewelsInStones_hashmap(String jewels, String stones) {
        if (jewels == null || stones == null) return 0;

        Map<Character, Integer> count = new HashMap<>();

        for (char c : stones.toCharArray()) {
            count.put(c, count.getOrDefault(c, 0) + 1);
        }

        int res = 0;
        for (char c : jewels.toCharArray()) {
            res += count.getOrDefault(c, 0);
        }

        return res;
    }

}
