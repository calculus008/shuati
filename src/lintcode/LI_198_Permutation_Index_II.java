package lintcode;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuank on 8/13/18.
 */
public class LI_198_Permutation_Index_II {
    /**
         Given a permutation which may contain repeated numbers, find its index in all the permutations
         of these numbers, which are ordered in lexicographical order. The index begins at 1.

         Example
         Given the permutation [1, 4, 2, 2], return 3.

         (With duplicates)
     */

    /**
         Q：元素有重复怎么办？
         A：好问题！元素有重复，情况会复杂的多。因为这会影响A[i]右侧元素的排列数，此时的排列数计算方法为总元素数的阶乘，
         除以各元素值个数的阶乘，例如[1,1,1,2,2,3]，排列数为6!÷(3!×2!×1!)。

         为了正确计算阶乘数，需要用哈系表记录A[i]及右侧的元素值个数，并考虑到A[i]与右侧比其小的元素A[k]交换后，要把A[k]的计数减一。
         用该哈系表计算正确的阶乘数。

         而且要注意，右侧比A[i]小的重复元素值只能计算一次，不要重复计算！
     */

    public long permutationIndexII(int[] A) {
        if (A == null || A.length == 0)
            return 0L;

        Map<Integer, Integer> counter = new HashMap<Integer, Integer>();
        long index = 1, fact = 1, multiFact = 1;

        for (int i = A.length - 1; i >= 0; i--) {
            if (counter.containsKey(A[i])) {
                counter.put(A[i], counter.get(A[i]) + 1);
                multiFact *= counter.get(A[i]);
            } else {
                counter.put(A[i], 1);
            }

            int rank = 0;
            for (int j = i + 1; j < A.length; j++) {
                if (A[i] > A[j]) rank++;
            }

            index += rank * fact / multiFact;
            fact *= (A.length - i);
        }

        return index;
    }
}
