package leetcode;

/**
 * Created by yuank on 3/4/18.
 */
public class LE_66_Plus_One {
    /**
        Given a non-negative integer represented as a non-empty array of digits, plus one to the integer.

        You may assume the integer do not contain any leading zero, except the number 0 itself.

        The digits are stored such that the most significant digit is at the head of the list.
     */

    /**
       case 1 : 1011 + 1 = 1012
       case 2 : 1019 + 1 = 1020
       case 3 : 9999 + 1 = 10000
     */

    public static int[] plusOne(int[] digits) {
        //从个位开始加，所以从后往前走
        for (int i = digits.length -1 ; i >= 0; i--) {
            if(digits[i] < 9) {//case 1
                digits[i]++;
                return digits;
            }
            digits[i] = 0; //case 2 and 3
        }

        //out of for loop and not returning, for case 3: "999", first one is "1", the rest are all "0".
        int[] res = new int[digits.length + 1];
        res[0] = 1;
        return res;
    }
}
