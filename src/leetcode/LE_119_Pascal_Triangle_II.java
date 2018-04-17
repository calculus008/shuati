package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuank on 3/13/18.
 */
public class LE_119_Pascal_Triangle_II {
    /*
        Given an index k, return the kth row of the Pascal's triangle.

        For example, given k = 3,
        Return [1,3,3,1].

        Note:
        Could you optimize your algorithm to use only O(k) extra space?
     */

    public static List<Integer> getRow(int rowIndex) {
        List<Integer> result = new ArrayList<>();

        //row number is zerio based, hence use "<="
        for (int i = 0; i <= rowIndex; i++) {
            result.add(0, 1);
            for (int j = 1; j < result.size() - 1; j++) {
                result.set(j, result.get(j) + result.get(j + 1));
            }
        }
        return result;
    }
}
