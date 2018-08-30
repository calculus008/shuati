package leetcode;

/**
 * Created by yuank on 8/29/18.
 */
public class LE_06_ZigZag_Conversion {
    /**
         he string "PAYPALISHIRING" is written in a zigzag pattern on a given number of rows like this:
        (you may want to display this pattern in a fixed font for better legibility)

         P   A   H   N
         A P L S I I G
         Y   I   R
         And then read line by line: "PAHNAPLSIIGYIR"

         Write the code that will take a string and make this conversion given a number of rows:

         string convert(string s, int numRows);
         Example 1:

         Input: s = "PAYPALISHIRING", numRows = 3
         Output: "PAHNAPLSIIGYIR"

         Example 2:

         Input: s = "PAYPALISHIRING", numRows = 4
         Output: "PINALSIGYAHRPI"
         Explanation:

         P     I    N
         A   L S  I G
         Y A   H R
         P     I
     */

    public String convert(String s, int numRows) {
        char[] ch = s.toCharArray();
        int len = ch.length;

        //!!!!
        StringBuilder[] sb = new StringBuilder[numRows];
        for (int i = 0; i < numRows; i++) {
            sb[i] = new StringBuilder();
        }

        int i = 0;
        while (i < len) {
            for (int n = 0; n < numRows && i < len; n++) {
                sb[n].append(ch[i++]);
            }

            //!!! from bottom up
            for (int m = numRows - 2; m > 0 && i < len; m--) {
                sb[m].append(ch[i++]);
            }
        }

        StringBuilder res = new StringBuilder();
        for (int k = 0; k < numRows; k++) {
            res.append(sb[k].toString());
        }

        return res.toString();
    }
}
