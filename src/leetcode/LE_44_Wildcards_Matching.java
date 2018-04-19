package leetcode;

/**
 * Created by yuank on 2/27/18.
 */
public class LE_44_Wildcards_Matching {
    /*
        Implement wildcard pattern matching with support for '?' and '*'.

        '?' Matches any single character.
        '*' Matches any sequence of characters (including the empty sequence).

        The matching should cover the entire input string (not partial).

        The function prototype should be:
        bool isMatch(const char *s, const char *p)

        Some examples:
        isMatch("aa","a") → false
        isMatch("aa","aa") → true
        isMatch("aaa","aa") → false
        isMatch("aa", "*") → true
        isMatch("aa", "a*") → true
        isMatch("ab", "?*") → true
        isMatch("aab", "c*a*b") → false
     */

    //Time : O(m * n).
    //Worst Case : After we get to 'b' (last char in p), we need to move all the way back to the first 'a' in p
    //s = "aaaaaaaaaaaaaaaaaaaa" p = "*aaaaaaaaaaaaaaaaaab"
    public static boolean isMatch(String s, String p) {
        int sp = 0 ;
        int pp = 0;
        int match = 0;
        int star = -1;

        while (sp < s.length()) {
            if (pp < p.length() && (p.charAt(pp) == s.charAt(sp) || p.charAt(pp) == '?')) {
                pp++;
                sp++;
                System.out.println("char match, sp="+sp+", pp="+pp);
            } else if (pp < p.length() && p.charAt(pp) == '*') { //"bbarc", "*c"
                //star saves the indx in p for the last time we see '*'
                star = pp;

                //save the comparison point in s when the last '*" appears, or the index in s when the last '*" appears
                //#1.sp does not increase here
                //reason: try to match case like :
                //       "abcdef"
                //       "abc*def"
                //        as '*' can match to empty string, we don't move sp in case we need to use '*' in p to match empty string.

                //#2.Why use 'match'? For case like:
                //     "aaaa"
                //     "***a"
                //     need to move pp back to the next of the last '*' after pp is already out of array bound by using "pp = start + 1".
                //     when bringing back pp, we need sp to stay at the location in the last loop. Therefore we can't simply increase sp in
                //     "post star" branch, we need to use "match" to remember the last location.

                //output for "sp++"
                //    star, sp=0, pp=1, match=0
                //    star, sp=0, pp=2, match=0
                //    star, sp=0, pp=3, match=0
                //    char match, sp=1, pp=4
                //    post star, sp=2, pp=3, match=1
                //    char match, sp=3, pp=4
                //    post star, sp=4, pp=3, match=2
                //    final pp=3
                //            false

                //output for using "sp = match"
                //    star, sp=0, pp=1, match=0
                //    star, sp=0, pp=2, match=0
                //    star, sp=0, pp=3, match=0
                //    char match, sp=1, pp=4
                //    post star, sp=1, pp=3, match=1
                //    char match, sp=2, pp=4
                //    post star, sp=2, pp=3, match=2
                //    char match, sp=3, pp=4
                //    post star, sp=3, pp=3, match=3
                //    char match, sp=4, pp=4
                //    final pp=4
                //            true

                match = sp;
                pp++;

                System.out.println("star, sp="+sp+", pp="+pp+", match="+match);
            } else if (star != -1) {
                //move to the one nex to '*' in p
                pp = star + 1;
                match++;
                sp = match;
//                sp++;

                System.out.println("post star, sp="+sp+", pp="+pp+", match="+match);
            } else {
                System.out.println("return false, sp="+sp+", pp="+pp);
                return false;
            }
        }

        while (pp < p.length() && p.charAt(pp) == '*') {
            pp++;
        }

        System.out.println("final pp="+pp);

        return pp == p.length();
    }

    public static void main(String [] args)
    {
        System.out.println(isMatch("aaaa", "***a"));
    }


}
