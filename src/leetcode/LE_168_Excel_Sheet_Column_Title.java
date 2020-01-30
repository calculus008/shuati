package leetcode;

/**
 * Created by yuank on 3/21/18.
 */
public class LE_168_Excel_Sheet_Column_Title {
    /**
        Given a positive integer, return its corresponding column title as appear in an Excel sheet.

        For example:

            1 -> A
            2 -> B
            3 -> C
            ...
            26 -> Z
            27 -> AA
            28 -> AB
     */

    //Conversion from other side - LE_171
    /*
        Test case :
        1    A
        26   Z
        28   AB
    */
    public String convertToTitle(int n) {
        StringBuilder sb = new StringBuilder();

        while (n > 0) {
            n--; //!!!make it zero based instead of 1 based
            sb.append((char)('A' + n % 26 ));
            n = n /26;
        }

        return sb.reverse().toString();
    }

}
