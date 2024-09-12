package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuank on 3/13/18.
 */
public class LE_118_Pascal_Triangle {
    /**
     *         Given numRows, generate the first numRows of Pascal's triangle.
     *
     *         For example, given numRows = 5,
     *         Return
     *
     *         [
     *              [1],
     *             [1,1],
     *            [1,2,1],
     *           [1,3,3,1],
     *          [1,4,6,4,1]
     *         ]
     *
     *         Easy
     *
     *         https://leetcode.com/problems/pascals-triangle
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

    public List<List<Integer>> generate_clean(int numRows) {
        List<List<Integer>> triangle = new ArrayList<List<Integer>>();

        triangle.add(new ArrayList<>());
        triangle.get(0).add(1);

        for (int rowNum = 1; rowNum < numRows; rowNum++) {
            List<Integer> row = new ArrayList<>();
            List<Integer> prevRow = triangle.get(rowNum - 1);

            row.add(1);

            for (int j = 1; j < rowNum; j++) {
                row.add(prevRow.get(j - 1) + prevRow.get(j));
            }

            row.add(1);
            triangle.add(row);
        }

        return triangle;
    }

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

    public List<List<Integer>> generate2(int numRows) {
        List<List<Integer>> triangle = new ArrayList<List<Integer>>();

        // Base case; first row is always [1].
        triangle.add(new ArrayList<>());
        triangle.get(0).add(1);

        for (int rowNum = 1; rowNum < numRows; rowNum++) {
            List<Integer> row = new ArrayList<>();
            List<Integer> prevRow = triangle.get(rowNum - 1);

            // The first row element is always 1.
            row.add(1);

            // Each triangle element (other than the first and last of each row)
            // is equal to the sum of the elements above-and-to-the-left and
            // above-and-to-the-right.
            for (int j = 1; j < rowNum; j++) {
                row.add(prevRow.get(j - 1) + prevRow.get(j));
            }

            // The last row element is always 1.
            row.add(1);

            triangle.add(row);
        }

        return triangle;
    }

}
