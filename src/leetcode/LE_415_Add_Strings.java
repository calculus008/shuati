package leetcode;

public class LE_415_Add_Strings {
    /**
     * Given two non-negative integers num1 and num2 represented as string,
     * return the sum of num1 and num2.
     *
     * Note:
     *
     * The length of both num1 and num2 is < 5100.
     * Both num1 and num2 contains only digits 0-9.
     * Both num1 and num2 does not contain any leading zero.
     * You must not use any built-in BigInteger library or convert the inputs
     * to integer directly.
     *
     * Easy
     */

    /**
     * similar to LE_02_Add_Two_Numbers
     */
    public class Solution1 {
        public String addStrings(String num1, String num2) {
            StringBuilder sb = new StringBuilder();
            int carry = 0;
            for(int i = num1.length() - 1, j = num2.length() - 1; i >= 0 || j >= 0 || carry == 1; i--, j--){
                int x = i < 0 ? 0 : num1.charAt(i) - '0';
                int y = j < 0 ? 0 : num2.charAt(j) - '0';
                sb.append((x + y + carry) % 10);
                carry = (x + y + carry) / 10;
            }
            return sb.reverse().toString();
        }
    }

    class Solution2 {
        public String addStrings(String num1, String num2) {
            int carry = 0;
            int i = num1.length() - 1, j = num2.length() - 1;
            StringBuilder sb = new StringBuilder();

            while (i >= 0 || j >= 0) {
                int n1 = 0, n2 = 0;
                if (i >= 0) {
                    n1 = num1.charAt(i) - '0';
                }
                if (j >= 0) {
                    n2 = num2.charAt(j) - '0';
                }
                int sum = n1 + n2 + carry;
                carry = sum / 10;
                sb.append(sum % 10);
                i--;
                j--;
            }

            if (carry != 0) {
                sb.append(carry);
            }

            return sb.reverse().toString();
        }
    }

    class Solution_Pratice {
        public String addStrings(String num1, String num2) {
            if (num1 == null && num2 == null) return "";

            if (num1 == null) return num2;
            if (num2 == null) return num1;


            /**
             * !!!
             * i, j is index, should be l1 -1 and l2 - 1
             */
            int i = num1.length() - 1;
            int j = num2.length() - 1;

            int sum = 0;

            StringBuilder sb = new StringBuilder();

            while (i >= 0 || j >= 0) {
                if (i >= 0) {
                    sum += num1.charAt(i) - '0';
                    i--;
                }

                if (j >= 0) {
                    sum += num2.charAt(j) - '0';
                    j--;
                }

                sb.append(sum % 10);
                sum /= 10;
            }

            if (sum != 0) sb.append(sum);

            /**
             * !!!
             * Don't forget toString() in the end
             */
            return sb.reverse().toString();
        }
    }
}
