package leetcode;

/**
 * Created by yuank on 11/25/18.
 */
public class LE_664_Strange_Printer {
    /**
         There is a strange printer with the following two special requirements:

         The printer can only print a sequence of the same character each time.
         At each turn, the printer can print new characters starting from and
         ending at any places, and will cover the original existing characters.
         Given a string consists of lower English letters only, your job is to
         count the minimum number of turns the printer needed in order to print it.

         Example 1:
         Input: "aaabbb"
         Output: 2
         Explanation: Print "aaa" first and then print "bbb".

         Example 2:
         Input: "aba"
         Output: 2
         Explanation: Print "aaa" first and then print "b" from the second place of the string,
         which will cover the existing character 'a'.

         Hint: Length of the given string will not exceed 100.

         Hard
     */

    /**
         http://zxi.mytechroad.com/blog/dynamic-programming/leetcode-664-strange-printer/

         DP
         t(i, j) = t(i, j - 1) + 1
         if s[k] == s[j], t(i, j) = min {t(i, k) + t(k + 1, j)}

         如果结尾的字符(s[j])和中间某个字符，比如s[k],一样，则可以一次打印从k到j，从而节省一步，比如：

         0 1 2 3 4 5 6
         a a b c c b a

         找和结尾字符一样的位置为cut point:

         s[6] 和 s[1] 相同，helper(aa) + helper(bccb) :

         1.  a a a a a a
         2.  a b b b b a
         3.  a b c c b a
         4.a a b c c b a

         Total steps = 4.

         Also, s[6] == s[0], helper(a) + helper(abccb) :
         1.a a a a a a a
         2.a a b b b b a
         3.a a b c c b a

         Total steps = 3

         取min, therefore ans = 3.

         Time  : O(n ^ 3)
         To fill n ^ 2 in mem, then in each helper call, iterate s to find cut point (O(n)), therefore O(n ^ 3)

         Space : O(n ^ 2)
     **/
    int[][] mem;
    public int strangePrinter(String s) {
        if (null == s || s.length() == 0) {
            return 0;
        }

        int n = s.length();
        mem = new int[n][n];

        return helper(s.toCharArray(), 0, n - 1);
    }

    private int helper(char[] s, int i, int j) {
        if (i > j) {//empty string
            return 0;
        }

        if (mem[i][j] > 0) {
            return mem[i][j];
        }

        int res = helper(s, i , j - 1) + 1;

        for (int k = i; k < j; k++) {
            /**
             !!!
             s[k] (NOT s[i]) == s[j]
             **/
            if (s[k] == s[j]) {
                res = Math.min(res, helper(s, i, k) + helper(s, k + 1, j - 1));
            }
        }

        /**
         * !!!
         * Memization, 千万不能忘！
         */
        mem[i][j] = res;

        return res;
    }
}
