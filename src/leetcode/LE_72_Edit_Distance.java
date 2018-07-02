package leetcode;

/**
 * Created by yuank on 3/5/18.
 */
public class LE_72_Edit_Distance {
    /**
        Given two words word1 and word2, find the minimum number of steps required to convert word1 to word2. (each operation is counted as 1 step.)

        You have the following 3 operations permitted on a word:

        a) Insert a character
        b) Delete a character
        c) Replace a character
     **/

    /**
        DP Solution : Time : O(m * n), Space : O(m * n)

        "convert word1 to word2", so all actions (insert, delete and replace) are done on word1
        dp[i][j] : min steps needed to convert word1 (1 ~ i) to word2 (1 ~ j) .

        http://zxi.mytechroad.com/blog/dynamic-programming/leetcode-72-edit-distance/

       D("abbc", "acc")
       = D("abb", "ac")
       = 1 + min ( D("ab", "ac")   //Delete : delete "b" from word1
                   D("abb", "a")   //Insert : if we know how to convert "abb" to "a",
                                   //         then we just need to insert "c" at the end of word1
                   D("ab",  "a"))  //Replace : if we know how to convert "ab" to "a",
                                              then we just need to replace the last "b" in word1 with "c"

        d(i, j) = minDistance(word1[0..i-1], word2[0..j-1])
        1.if j = 0, i
        2.if i = 0, j
        3.if word1[i -1] = word2[j -1]  d(i - i, j - 1)
        4.min (d(i - 1, j), d(i, j - 1), d(i - 1, j - 1) + 1

        d(i, j) 表示把 str1 前 i 个字符编辑成 str2 前 j 个字符所需要的最小 edit distance

        d(2, 1) = 1 + d(1, 1)
        edit("ab", "a") = 1 + edit("a", "a") <- delete "b" from str1

        d(1, 2) = 1 + d(1, 1)
        edit("a", "ab") = 1 + edit("a", "a") <- insert "b" into str1

        d(2, 2) = 1 + d(1, 1)
        edit("ab", "ac") = 1 + edit("a", "a") <- replace "b" with "c" in str1﻿
     **/

/**
    "0" at dp[0]0] represents empty string, the init process is using delete action, for example : abcd, i=0, "a", one delete to get to "";
    i=1, "ab", two deletes to get to "";....

           a b c d
        0  1 2 3 4
     a  1  0 1 2 3
     e  2  1 1 2 3
     f  3  2 2 2 3
 **/

    public static int minDistance(String word1, String word2) {
        int[][] dp = new int[word1.length() + 1][word2.length() + 1];
        int len1 = word1.length();
        int len2 = word2.length();

        //!! "<="
        for (int i = 0; i <= len1; i++) {
            dp[i][0] = i;
        }

        //!! "<="
        for (int i = 0; i <= len2; i++) {
            dp[0][i] = i;
        }

        //!! "<=" and starts at index "1"
        for (int i = 1; i <= len1; i++) {
            //!! "<=" and starts at index "1"
            for (int j = 1; j <= len2; j++) {
                // compare char at "i - 1 and "j - 1"
                if(word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.min(Math.min(dp[i][j - 1], dp[i - 1][j]), dp[i - 1][j - 1]) + 1;
                }
            }
        }

        return dp[len1][len2];
    }
}
