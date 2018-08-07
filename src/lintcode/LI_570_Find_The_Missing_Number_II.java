package lintcode;

/**
 * Created by yuank on 8/7/18.
 */
public class LI_570_Find_The_Missing_Number_II {
    /**
         Giving a string with number from 1-n in random order, but miss 1 number.Find that number.

         Example
         Given n = 20, str = 19201234567891011121314151618

         return 17

         Medium
     */

    /**
     *   此题实际上是680. split_string的follow up。(LI_680_Split_String)
     *
         可以用split string先获得所有切割字符串组合，然后从里面筛选所需的结果，但是如果n比较大会超时， 时间复杂度O（n!）
         因此必须要在获得结集的过程当中进行剪枝优化。一旦发现出现了不合理的组合就无需按照这个方向继续搜下去了，直接continue
         想了一下，有以下几种可能：

         切出来的字符串 > n
         切出来的字符串已经出现过了。
         切出来的字符串开头为0， 比如09，0

         这样下来求出的result:
         1.Check found[], there should be only one that has value "false", its index is the answer, or
         2.把里面的数字求和，和 n * (n+1) / 2, 等差数列求和公式相减，就是所需结果。

         根据题意，最终只会有一种数字组合。
     */
    private int res;

    public int findMissing2(int n, String str) {
        res = -1;
        boolean[] found = new boolean[str.length() + 1];

        //start is the indx, so inital value is 0
        helper(n, str, 0, found);
        return res;
    }

    private void helper(int n, String str, int start, boolean[] found) {
        if (res != -1) {
            return;
        }

        //parse to the end, check status in found
        if (start == str.length()) {
            for (int i = 1; i < found.length; i++) {
                if (!found[i]) {
                    res = i;
                    break;
                }
            }
            return;
        }

        // if first char is 0, the combination is not gonna work
        if (str.charAt(start) == '0') {
            return;
        }

        for (int i = 1; i <= 2 && start + i <= str.length(); i++) {
            int num = Integer.parseInt(str.substring(start, start + i));
            if (num >= 1 && num <= n && !found[num]) {
                found[num] = true;
                helper(n, str, start + i, found);
                found[num] = false;
            }
        }
    }
}
