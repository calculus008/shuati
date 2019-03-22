package leetcode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by yuank on 3/24/18.
 */
public class LE_187_Repeasted_DNA_Sequences {
    /**
        All DNA is composed of a series of nucleotides abbreviated as A, C, G, and T, for example: "ACGAATTCCG".
        When studying DNA, it is sometimes useful to identify repeated sequences within the DNA.

        Write a function to find all the 10-letter-long sequences (substrings) that occur more than once in a DNA molecule.

        For example,

        Given s = "AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT",

        Return:
        ["AAAAACCCCC", "CCCCCAAAAA"].
     */

    /**
     * Time and Space : O(n)
     * 46 ms
     **/
    public List<String> findRepeatedDnaSequences1(String s) {
        List<String> res = new ArrayList<>();
        if (s == null || s.length() < 10) return res;

        Set<String> set = new HashSet<>();
        //!!! "i < s.length() - 9"
        for (int i = 0 ; i < s.length() - 9; i++) {
            String str = s.substring(i, i + 10);
            if (!set.add(str) && !res.contains(str)) {
                res.add(str);
            }
        }
        return res;
    }

    //Faster, 35 ms
    public List<String> findRepeatedDnaSequences2(String s) {
        List<String> res = new ArrayList<>();
        if (s == null || s.length() < 10) return res;

        Set<String> set = new HashSet<>();
        Set<String> ans = new HashSet<>();

        for (int i = 0 ; i < s.length() - 9; i++) {
            String str = s.substring(i, i + 10);
            /**
             * if set already contains str, it will trun false.
             * So here use "!set.add(str)" we do two things in one line:
             * 1.try to add str to set
             * 2.Tell if it exists in set
             */
            if (!set.add(str)) {
                ans.add(str);
            }
        }
        return new ArrayList<>(ans);
    }
}
