package leetcode;

/**
 * Created by yuank on 2/27/18.
 */
public class LE_43_Multiply_Strings {
    /*
        Given two non-negative integers num1 and num2 represented as strings, return the product of num1 and num2.

        Note:

        The length of both num1 and num2 is < 110.
        Both num1 and num2 contains only digits 0-9.
        Both num1 and num2 does not contain any leading zero.
        You must not use any built-in BigInteger library or convert the inputs to integer directly.
     */
    //Time O(n * m)
    //Space O(n + m)

    public String multiply(String num1, String num2) {
        if ("0".equals(num1) || "0".equals(num2)) return "0";

        int n = num1.length();
        int m = num2.length();
        int[] res = new int[n + m];

        for (int i = n - 1; i >= 0; i--) {
            for (int j = m - 1; j >= 0; j--) {
                int mul = (num1.charAt(i) - '0') * (num2.charAt(j) - '0');
                int sum = mul + res[i + j + 1];

                res[i + j + 1] = sum % 10;
                res[i + j] += sum / 10;
            }
        }

        StringBuilder sb = new StringBuilder();

        for (int c : res) {
            if (!(sb.length() == 0 && c == 0)) {//!!!remove leading zeros, we append from index=0, therefore sb.length()==0
                sb.append(c);
            }
        }

        return sb.toString();
    }
}
