package Interviews.Nextdoor;

public class Sum_Of_String {
    /**
     * 给两串字符串，每个char就是一个digit，然后从后往前加起来，把结果放到一 个字符串输出，挺简单的。​​​​​​​​​​​​​​​​​​​e.g. '99' + '99' = '1818'
     * 如果写Java的话最好用StringBuilder, ​​​​​​​​​​​​​​​​​​​String 会 TLE
     */
    public static String sumOfString(String s1, String s2) {
        if(s1 == null || s1.length() == 0) return s2;
        if(s2 == null || s2.length() == 0) return s1;
        int len1 = s1.length();
        int len2 = s2.length();

        StringBuilder sb = new StringBuilder();
        int idx1 = len1 - 1;
        int idx2 = len2 - 1;

        while(idx1 >= 0 && idx2 >= 0) {
            char c1 = s1.charAt(idx1--);
            char c2 = s2.charAt(idx2--);
            int num1 = c1 - '0';
            int num2 = c2 - '0';
            int sum = num1 + num2;
            sb.insert(0, Integer.toString(sum));
        }

        while(idx1 >= 0) {
            sb.insert(0, s1.charAt(idx1--));
        }

        while(idx2 >= 0) {
            sb.insert(0, s2.charAt(idx2--));
        }

        return sb.toString();
    }
}
