package leetcode;

/**
 * Created by yuank on 3/4/18.
 */
public class LE_67_Add_Binary {
    /*
        Given two binary strings, return their sum (also a binary string).

        For example,
        a = "11"
        b = "1"
        Return "100".
     */

    public static String addBinary(String a, String b) {
        StringBuilder sb = new StringBuilder();

        int i = a.length() - 1;
        int j = b.length() - 1;
        int remainder = 0;

        //!!! it's "OR", NOT "AND"
        while (i >= 0 || j >= 0) {
            int sum = remainder;

            //!!! don't forget to move the pointers
            if (i >= 0) {
                sum += a.charAt(i) - '0';
                i--;
            }
            if (j >= 0) {
                sum += b.charAt(j) - '0';
                j--;
            }
            sb.append(sum % 2);
            remainder = sum / 2;
        }

        if (remainder != 0) sb.append("1");

        return sb.reverse().toString();
    }
}
