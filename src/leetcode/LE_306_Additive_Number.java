package leetcode;

/**
 * Created by yuank on 4/29/18.
 */
public class LE_306_Additive_Number {
    /**
         Additive number is a string whose digits can form additive sequence.

         A valid additive sequence should contain at least three numbers. Except for the first two numbers, each subsequent number in the sequence must be the sum of the preceding two.

         For example:
         "112358" is an additive number because the digits can form an additive sequence: 1, 1, 2, 3, 5, 8.

         1 + 1 = 2, 1 + 2 = 3, 2 + 3 = 5, 3 + 5 = 8
         "199100199" is also an additive number, the additive sequence is: 1, 99, 100, 199.
         1 + 99 = 100, 99 + 100 = 199
         Note: Numbers in the additive sequence cannot have leading zeros, so sequence 1, 2, 03 or 1, 02, 3 is invalid.

         Given a string containing only digits '0'-'9', write a function to determine if it's an additive number.

         Follow up:
         How would you handle overflow for very large input integers?

         Medium
     */

    /**
         https://www.youtube.com/watch?v=NtEFza4Jfn0&t=538s

         Length of number:
         num1   num2   num3
         0 - i, i - j, j - n

         num1 : (n - 1) / 2
         num2 : n - j >= i && n - j >= j - i

        Time : O(n ^ 2), not sure
        Space : O(1)

     */
    public boolean isAdditiveNumber(String num) {
        int n = num.length();
        for (int i = 1; i <= n / 2; i++) {
            //"Math.max(i, j) <= n - i - j" : the length of the 3rd number should be bigger or equal to the max length of the first 2
            for (int j = 1; Math.max(i, j) <= n - i - j; j++) {
                if (isValid(i, j, num)) return true;
            }
        }
        return false;
    }

    public boolean isValid(int i, int j, String num) {
        //leading zero cases are not valid
        //First number, if it starts with '0' and length is more than 1
        if (num.charAt(0) == '0' && i > 1) return false;

        //"num.charAt(i) == '0'" : the first char of the 2nd number is 0
        if (num.charAt(i) == '0' && j > 1) return false;

        String sum;

        Long x1 = Long.parseLong(num.substring(0, i));
        Long x2 = Long.parseLong(num.substring(i, i + j));

        //"start" : start position in the num for the 3rd number
        //Rolling foward until the end of the num
        for (int start = i + j; start != num.length(); start += sum.length()) {
            x2 = x2 + x1;
            x1 = x2 - x1;
            sum = x2.toString();
            if (!num.startsWith(sum, start)) {
                return false;
            }
        }

        return true;
    }
}
