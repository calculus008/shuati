package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuank on 3/13/18.
 */
public class LE_118_Pascal_Triangle {
    /*
        Given numRows, generate the first numRows of Pascal's triangle.

        For example, given numRows = 5,
        Return

        [
             [1],
            [1,1],
           [1,2,1],
          [1,3,3,1],
         [1,4,6,4,1]
        ]
     */

    /*
        row :
        [1]
        [1,1]

        i = 2 :
        [1, 1, 1]
            \  /
              2


        [1,2,1]


        i = 3;
        [1, 1, 2, 1]
            \ / \/
             3   3

        [1, 3, 3, 1]

        i = 4;
        [1, 1, 3, 3, 1]
            \ / \/ \/
             4   6  4

        [1, 4, 6, 4, 1]
     */

    //Time : O(n ^ 2), Space : O(n)
    public static List<List<Integer>> generate(int numRows) {
        List<Integer> row = new ArrayList<>();
        List<List<Integer>> result = new ArrayList<>();

        for (int i = 0; i < numRows; i++) {
            row.add(0, 1);
            for (int j = 1; j < row.size() - 1; j++) {
                //是以插入的方式加进新元素
                row.set(j, row.get(j) + row.get(j + 1));
            }
            //!!!make copy of "row" and add to result, in every for loop, row keeps growing
            //就是说，row是在动态变化的。
            result.add(new ArrayList(row));
        }
        return result;
    }
}
