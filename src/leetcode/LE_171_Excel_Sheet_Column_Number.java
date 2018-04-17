package leetcode;

/**
 * Created by yuank on 3/21/18.
 */
public class LE_171_Excel_Sheet_Column_Number {
    /*
        Given a column title as appear in an Excel sheet, return its corresponding column number.

        For example:

            A -> 1
            B -> 2
            C -> 3
            ...
            Z -> 26
            AA -> 27
            AB -> 28
     */

    //Conversion from the other side - LE_168
    public int titleToNumber(String s) {
        int res = 0;

        for (char c : s.toCharArray()) {
            res = res * 26 + c - 'A' + 1;
        }

        return res;
    }
}
