package google;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Path_Sum {
    /**
     * Follow Up for LE_64_Min_Path_Sum
     */

    /**
     * Follow up 1：要求重建从end 到 start的路径
     * 思路：用另一个额外数组记录每一步选择的parent，dp结束后，从end依次访问它的parent重建路径
     */

    /**
     * Follow up 2: 现在要求空间复杂度为O（1），dp且重建路径
     * 空间复杂度不算返回路径时需要的空间
     *
     * 思路：直接修改原数组，而且带上符号，负号表示从当前cell的左边过来，正号表示从当前cell的上边过来，
     * dp结束后从end 依次访问它的parent重建路径.
     *
     * "数组全是零(或者左上角一块是零)的话就没有办法通过正负号判断来的方向了吧，这样在重构path的时候可能会
     * index out of bound，觉得还是check左边和右边哪个更大就是从那边来的更好，然后注意第一行和第一列的特
     * 殊情况，这样不会出问题"
     *
     *（这个方法是面试官给的hint提示的，原数组应该都是正整数。如果全是0，用正负号表示也可以特殊处理一下第一
     * 行和第一列的情况即可，即遇到i为0时候总是往左走，j为0的时候总是往上走。）
     */

    public List<List<Integer>> maxMoney(int[][] moneys) {
        // assume: moneys is not null, width and length are equal
        int n = moneys.length;
        if (n == 0) {
            return new ArrayList<>();
        }

        // base case
        for (int j = 1; j < n; j++) {
            /**
             * "负号表示从当前cell的左边过来"
             */
            moneys[0][j] = -(Math.abs(moneys[0][j - 1]) + moneys[0][j]);
        }

        for (int i = 1; i < n; i++) {
            moneys[i][0] = moneys[i - 1][0] + moneys[i][0];
        }

        for (int i = 1; i < n; i++) {
            for (int j = 1; j < n; j++) {
                int top = Math.abs(moneys[i - 1][j]) + moneys[i][j];
                int left = Math.abs(moneys[i][j - 1]) + moneys[i][j];
                if (top >= left) {
                    moneys[i][j] = top;
                } else {
                    moneys[i][j] = -left;
                }
            }
        }

        System.out.println("Max path sum = " + Math.abs(moneys[n - 1][n - 1]));

        List<List<Integer>> path = new ArrayList<>();
        int curri = n - 1;
        int currj = n - 1;

        while (curri > 0 || currj > 0) {
            path.add(Arrays.asList(curri, currj));
            if (moneys[curri][currj] < 0) {
                currj -= 1;
            } else {
                curri -= 1;
            }
        }

        path.add(Arrays.asList(0, 0));

        return path;
    }

}
