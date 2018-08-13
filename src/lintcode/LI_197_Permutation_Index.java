package lintcode;

/**
 * Created by yuank on 8/13/18.
 */
public class LI_197_Permutation_Index {
    /**
         Given a list of integers, which denote a permutation.

         Find the previous permutation in ascending order.

         Example
         For [1,3,2,3], the previous permutation is [1,2,3,3]

         For [1,2,3,4], the previous permutation is [4,3,2,1]

         Notice
         The list may contains duplicate integers.

         Easy
     */

    /**
     *   https://ksmeow.moe/cantor_expansion/
     *   https://blog.csdn.net/lttree/article/details/24798653
     *
     *   康托展开式
     算法描述
     只需计算有多少个排列在当前排列A的前面即可。如何算呢?举个例子，[3,7,4,9,1]，在它前面的必然是某位置i对应元素比原数组小，
     而i左侧和原数组一样。也即[3,7,4,1,X]，[3,7,1,X,X]，[3,1或4,X,X,X]，[1,X,X,X,X]。
     而第i个元素，比原数组小的情况有多少种，其实就是A[i]右侧有多少元素比A[i]小，乘上A[i]右侧元素全排列数，即A[i]右侧元素数量的阶乘。
     i从右往左看，比当前A[i]小的右侧元素数量分别为1,1,2,1，所以最终字典序在当前A之前的数量为1×1!+1×2!+2×3!+1×4!=39，故当前A的字典序为40。
     */
    public long permutationIndex(int[] A) {
        // write your code here
        long permutation = 1;
        long result = 0;
        for (int i = A.length - 2; i >= 0; --i) {
            int smaller = 0;
            for (int j = i + 1; j < A.length; ++j) {
                if (A[j] < A[i]) {
                    smaller++;
                }
            }
            result += smaller * permutation;
            permutation *= A.length - i;
        }
        return result + 1;
    }
}
