package leetcode;

/**
 * Created by yuank on 3/4/18.
 */
public class LE_65_Valid_Number {
    /**
        Validate if a given string is numeric.

        Some examples:
        "0" => true
        " 0.1 " => true
        "abc" => false
        "1 a" => false
        "2e10" => true

        Note:
        It is intended for the problem statement to be ambiguous.
        You should gather all requirements up front before implementing one.
     */

    public static boolean isNumber(String s) {
        s = s.trim();
        boolean pointSeen = false;
        boolean numberSeen = false;
        boolean numberAfterE = true;
        boolean eSeen = false;

        /**
         * numberAfterE :  '1e', E must be followed with numbers, therefore init numberAfterE as "true",
         *                 if there's no 'e'appears, we let it pass. Once 'e' appears, we set numberAfterE
         *                 as "false", we must see numbers after we see 'e'  to make it a valid number.
         */
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= '0' && c <= '9') {
                numberSeen = true;
                numberAfterE = true;
            } else if (c == '.') {
                if (eSeen || pointSeen) {
                    return false;
                }
                pointSeen = true;
            } else if (c == 'e') {
                //!numberSeen : if no number appears before 'e'
                if (eSeen || !numberSeen) {
                    return false;
                }
                eSeen = true;
                numberAfterE = false;
            } else if (c == '+' || c == '-') {
                if (i != 0 && s.charAt(i - 1) != 'e') {
                    return false;
                }
            } else {
                return false;
            }
        }

        return numberSeen && numberAfterE;
    }
}
