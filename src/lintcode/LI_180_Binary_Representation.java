package lintcode;

import java.util.HashSet;

public class LI_180_Binary_Representation {
    /**
     * Given a (decimal - e.g. 3.72) number that is passed in as a string, return the binary
     * representation that is passed in as a string. If the fractional part of the number
     * can not be represented accurately in binary with at most 32 characters, return ERROR.
     *
     * Example 1
     * Input: "3.72"
     * Output: "ERROR"
     * Explanation: (3.72)_{10} = (11.10111000010100011111\cdots)_2(3.72)
     * ​10
     * ​​ =(11.10111000010100011111⋯)
     * ​2
     * ​​  We can't represent it in 32 characters.
     *
     * Example 2
     * Input: "3.5"
     * OUtput: "11.1"
     * Explanation: (3.5)_{10}=(11.1)_2(3.5)
     * ​10
     * ​​ =(11.1)
     * ​2
     * ​​
     */

    /**
     * 我们需要把整数和小数部分分离，整数部分按照短除法进行进制转换，小数部分按照*2∗2看整数部分法进行进制转换，
     * 注意的是，如果乘32次都没有结束，直接返回ERROR
     *
     * 注意不能用Double.parseDouble, 会出现精度错误。正确做法是把其分两段，
     */
    public class Solution {
        private String parseInteger(String str) {
            int n = Integer.parseInt(str);
            if (str.equals("") || str.equals("0")) {
                return "0";
            }

            String binary = "";
            while (n != 0) {
                binary = Integer.toString(n % 2) + binary;
                n = n / 2;
            }
            return binary;
        }

        private String parseFloat(String str) {
            double d = Double.parseDouble("0." + str);
            String binary = "";
            HashSet<Double> set = new HashSet<Double>();

            while (d > 0) {
                if (binary.length() > 32 || set.contains(d)) {
                    return "ERROR";
                }

                set.add(d);
                d = d * 2;

                if (d >= 1) {
                    binary = binary + "1";
                    d = d - 1;
                } else {
                    binary = binary + "0";
                }
            }
            return binary;
        }

        public String binaryRepresentation(String n) {
            if (n.indexOf('.') == -1) {
                return parseInteger(n);
            }

            String[] params = n.split("\\.");
            String flt = parseFloat(params[1]);

            if (flt == "ERROR") {
                return flt;
            }

            if (flt.equals("0") || flt.equals("")) {
                return parseInteger(params[0]);
            }

            return parseInteger(params[0]) + "." + flt;
        }
    }
}
