package leetcode;

/**
 * Created by yuank on 2/27/18.
 */
public class LE_38_Count_And_Say {
    /*
        The count-and-say sequence is the sequence of integers with the first five terms as following:

        1.     1
        2.     11
        3.     21
        4.     1211
        5.     111221
        1 is read off as "one 1" or 11.
        11 is read off as "two 1s" or 21.
        21 is read off as "one 2, then one 1" or 1211.
        Given an integer n, generate the nth term of the count-and-say sequence.

        Note: Each term of the sequence of integers will be represented as a string.

        Example 1:

        Input: 1
        Output: "1"
        Example 2:

        Input: 4
        Output: "1211"
     */

    public String countAndSay(int n) {
        if(n<=0) return "-1";
        if(n==1) return "1";
        if(n==2) return "11";

        String str="11";

        for(int i=3; i<=n; i++) {
            int count = 1;
            int len = str.length();
            StringBuilder sb = new StringBuilder();

            for(int j=1; j<len; j++) {
                if(str.charAt(j) != str.charAt(j-1)) {
                    sb.append(count).append(str.charAt(j-1));
                    count = 1;
                } else {
                    count++;
                }
            }

            sb.append(count).append(str.charAt(len-1));
            str = sb.toString();
        }

        return str;
    }
}
