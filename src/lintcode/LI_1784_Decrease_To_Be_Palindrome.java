package lintcode;

public class LI_1784_Decrease_To_Be_Palindrome {
    /**
     * Given a string s with a-z. We want to change s into a palindrome with following operations:
     *
     * 1. change 'z' to 'y';
     * 2. change 'y' to 'x';
     * 3. change 'x' to 'w';
     * ................
     * 24. change 'c' to 'b';
     * 25. change 'b' to 'a';
     * Returns the number of operations needed to change s to a palindrome at least.
     *
     * Example 1:
     *
     * Input: "abc"
     * Output: 2
     * Explanation:
     *   1. Change 'c' to 'b': "abb"
     *   2. Change the last 'b' to 'a': "aba"
     * Example 2:
     *
     * Input: "abcd"
     * Output: 4
     *
     * Easy
     */

    /**
     * 使用两根指针遍历, 检查应该相等的两个位置的实际字符的 "距离", 求和即可.
     *
     * 字符的 "距离" 表示至少多少步操作可以把这两个字母变成相同的, 比如 'a' 和 'b' 的距离为 1.
     *
     * 在 C++/Java 中可以直接相减得到 ASCII值 的差
     */
    public int numberOfOperations(String s) {
        char[] str = s.toCharArray();
        int n = s.length();
        int cnt = 0;
        for (int i = 0, j = n - 1; i < j; i++, j--) {
            cnt += Math.abs(str[i] - str[j]);
        }
        return cnt;
    }

    public int numberOfOperations_myversion(String s) {
        if (null == s || s.length() == 0) return 0;

        int l = 0;
        int r = s.length() - 1;

        int sum = 0;
        while (l < r) {
            sum += Math.abs(s.charAt(l) - s.charAt(r));
            l++;
            r--;
        }

        return sum;
    }
}
