package lintcode;

/**
 * Created by yuank on 8/10/18.
 */
public class LI_211_String_Permutation {
    /**
     Given two strings, write a method to decide if one is a permutation of the other.

     Example
     abcd is a permutation of bcad, but abbe is not a permutation of abe

     Easy
     */

    //Time : O(n)
    public boolean Permutation(String A, String B) {
        int[] map = new int[1000];

        for (char c : A.toCharArray()) {
            map[(int)c]++;
        }

        for (char c : B.toCharArray()) {
            map[(int)c]--;
        }

        for (int i : map ) {
            if (i != 0) {
                return false;
            }
        }

        return true;
    }
}
