package leetcode;

/**
 * Created by yuank on 3/20/18.
 */
public class LE_165_Compare_Version_Number {
    /**
        Compare two version numbers version1 and version2.
        If version1 > version2 return 1, if version1 < version2 return -1, otherwise return 0.

        You may assume that the version strings are non-empty and contain only digits and the . character.
        The . character does not represent a decimal point and is used to separate number sequences.
        For instance, 2.5 is not "two and a half" or "half way to version three", it is the fifth second-level
        revision of the second first-level revision.

        Here is an example of version numbers ordering:

        0.1 < 1.1 < 1.2 < 13.37
     */
    /**
       there are 12 characters with special meanings:

       the backslash \,
       the caret ^,
       the dollar sign $,
       the period or dot .,
       the vertical bar or pipe symbol |,
       the question mark ?,
       the asterisk or star *,
       the plus sign +,
       the opening parenthesis (,
       the closing parenthesis ),
       and the opening square bracket [,
       the opening curly brace {,

       These special characters are often called "metacharacters".
       Use either backslash \ to escape the individual special character like so split("\\."),
       or use character class [] to represent literal character(s) like so split("[.]"),
       or use Pattern#quote() to escape the entire string like so split(Pattern.quote(".")).
     */

    public int compareVersion1(String version1, String version2) {
        String[] tokens1 = version1.split("\\.");
        String[] tokens2 = version2.split("\\.");

        int len = tokens1.length > tokens2.length ? tokens1.length : tokens2.length;
        for (int i = 0; i < len; i++) {
            /**
              Key to the solution:

              s1 : 1.2.3, s2: 1.2.3.4

              s1 ends at "3", s2 has one more version number after "3". For case like this, just think s1 is "1.2.3.0"
             */
            int v1 = i >= tokens1.length ? 0 : Integer.parseInt(tokens1[i]);
            int v2 = i >= tokens2.length ? 0 : Integer.parseInt(tokens2[i]);
            if (v1 > v2) {
                return 1;
            } else if (v1 < v2) {
                return -1;
            }
        }

        return 0;
    }

    public int compareVersion2(String version1, String version2) {
        String[] n = version1.split("\\.");
        String[] m = version2.split("\\.");

        int len = Math.max(n.length, m.length);

        for(int i=0; i<len; i++) {
            Integer v1 = i<n.length? Integer.parseInt(n[i]):0;
            Integer v2 = i<m.length? Integer.parseInt(m[i]):0;

            //!!!the method is compareTo, not compare
            int res = v1.compareTo(v2);
            if(res!=0) return res;
        }

        return 0;
    }
}
