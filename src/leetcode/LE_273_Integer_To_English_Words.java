package leetcode;

/**
 * Created by yuank on 4/15/18.
 */
public class LE_273_Integer_To_English_Words {
    /**
     * Convert a non-negative integer to its english words representation. Given input is guaranteed to be less than 231 - 1.
     * <p>
     * For example,
     * 123 -> "One Hundred Twenty Three"
     * 12345 -> "Twelve Thousand Three Hundred Forty Five"
     * 1234567 -> "One Million Two Hundred Thirty Four Thousand Five Hundred Sixty Seven"
     */

    public String numberToWords(int num) {
        if (num == 0) return "Zero";
        return helper(num).trim();
    }

    private String helper(int num) {
        String[] below20 = {"", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten",
                "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen",
                "Eighteen", "Nineteen"};
        String[] below100 = {"", "Ten", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};

        String res = "";
        if (num < 20) {
            res = below20[num];
        } else if (num < 100) {
            /**
             * !!!
             * below100[num/10], not helper(num/100)
             */
            res = below100[num / 10] + " " + helper(num % 10);
        } else if (num < 1000) {
            res = helper(num / 100) + " Hundred " + helper(num % 100);
        } else if (num < 1000000) {
            res = helper(num / 1000) + " Thousand " + helper(num % 1000);
        } else if (num < 1000000000) {
            res = helper(num / 1000000) + " Million " + helper(num % 1000000);
        } else {
            res = helper(num / 1000000000) + " Billion " + helper(num % 1000000000);
        }
        return res.trim();
    }
}
