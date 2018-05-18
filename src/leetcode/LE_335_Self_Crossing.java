package leetcode;

/**
 * Created by yuank on 5/17/18.
 */
public class LE_335_Self_Crossing {
    /**
         You are given an array x of n positive numbers. You start at point (0,0) and moves x[0] metres to the north,
         then x[1] metres to the west, x[2] metres to the south, x[3] metres to the east and so on. In other words,
         after each move your direction changes counter-clockwise.

         Write a one-pass algorithm with O(1) extra space to determine, if your path crosses itself, or not.

         Example 1:
         Given x = [2, 1, 1, 2],
         ?????
         ?   ?
         ???????>
         ?

         Return true (self crossing)
         Example 2:
         Given x = [1, 2, 3, 4],
         ????????
         ?      ?
         ?
         ?
         ?????????????>

         Return false (not self crossing)
         Example 3:
         Given x = [1, 1, 1, 1],
         ?????
         ?   ?
         ?????>

         Return true (self crossing)

         Hard
     */

    /**
     http://www.cnblogs.com/grandyang/p/5216856.html

     实际上相交的情况只有以下三种情况：

     x(1)
     ┌───┐
     x(2)│   │x(0)
     └───┼──>
     x(3)│

     第一类是第四条边和第一条边相交的情况，需要满足的条件是第一条边大于等于第三条边，第四条边大于等于第二条边。
     同样适用于第五条边和第二条边相交，第六条边和第三条边相交等等，依次向后类推的情况...



     复制代码
     x(1)
     ┌──────┐
     │      │x(0)
     x(2)│      ^
     │      │x(4)
     └──────│
     x(3)
     复制代码
     第二类是第五条边和第一条边重合相交的情况，需要满足的条件是第二条边和第四条边相等，第五条边大于等于第三条边和第一条边的差值，
     同样适用于第六条边和第二条边重合相交的情况等等依次向后类推...



     复制代码
     x(1)
     ┌──────┐
     │      │x(0)
     x(2)│     <│────│
     │       x(5)│x(4)
     └───────────│
     x(3)
     复制代码
     第三类是第六条边和第一条边相交的情况，需要满足的条件是第四条边大于等于第二条边，第三条边大于等于第五条边，
     第五条边大于等于第三条边和第一条边的差值，第六条边大于等于第四条边和第二条边的差值，同样适用于第七条边和第二条边相交的情况等等依次向后类推...

     **/
    public boolean isSelfCrossing(int[] x) {
        int n = x.length;
        if (n <= 3) return false;

        for (int i = 3; i < n; i++) {
            /**
                 第一类 : 第四条边和第一条边相交
                 x(1)
                 ┌───┐
                 x(2)│   │x(0)
                 └───┼──>
                 x(3)│

                 "x[i - 3] >= x[i - 1]" : 第一条边大于等于第三条边
                 "x[i] >= x[i - 2]"     : 第四条边大于等于第二条边
             **/
            if (x[i - 3] >= x[i - 1] && x[i] >= x[i - 2]) {
                return true;
            }

            /**
                 第二类 : 第五条边和第一条边重合相交
                 x(1)
                 ┌──────┐
                 │      │x(0)
                 x(2)│      ^
                 │      │x(4)
                 └──────│
                 x(3)

                 "x[i - 3] == x[i - 1]"        : 第二条边和第四条边相等
                 "x[i] >= x[i - 2] - x[i - 4]" : 第五条边大于等于第三条边和第一条边的差值
             **/
            if (i >= 4) {
                if (x[i - 3] == x[i - 1] && x[i] >= x[i - 2] - x[i - 4]) {
                    return true;
                }
            }

            /**
                 第三类 : 第六条边和第一条边相交

                 x(1)
                 ┌──────┐
                 │      │x(0)
                 x(2)│     <│────│
                 │       x(5)│x(4)
                 └───────────│
                 x(3)

                 第四条边大于等于第二条边
                 第三条边大于等于第五条边
                 第五条边大于等于第三条边和第一条边的差值
                 第六条边大于等于第四条边和第二条边的差值
             **/
            if (i >= 5) {
                if (x[i - 2] >= x[i - 4] &&
                        x[i - 3] >= x[i - 1] &&
                        x[i - 1] >= x[i - 3] - x[i - 5] &&
                        x[i] >= x[i - 2] - x[i - 4]) {
                    return true;
                }
            }
        }

        return false;
    }
}
