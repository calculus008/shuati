package lintcode;

/**
 * Created by yuank on 8/13/18.
 */
public class LI_197_Permutation_Index {
    /**
         Given a permutation which contains NO repeated number,
         find its index in all the permutations of these numbers,
         which are ordered in lexicographical order. The index begins at 1.

         Example
         Given [1,2,4], return 1.
     */

    /**
     *   https://ksmeow.moe/cantor_expansion/
     *   https://blog.csdn.net/lttree/article/details/24798653
     *
     *   利用康托展开，我们可以求出一个排列在全排列中的排名，一个排列的康托展开是它的前述排名-1。
     *   因此，康托展开是一个排列到自然数的双射，具有双向的唯一映射关系。
     *
     *   该题是排列到自然数。自然是到排列是LE_60_Permutation_Sequence
     *
         我们令康托展开的结果为X，对于排列Ai，有展开式
         X=a1(n−1)!+a2(n−2)!+⋯+an⋅0!
         展开式中的ai代表，在A1到Ai中没出现过的，不大于Ai的[1,n]中的数的数量。下面我们用一个例子来说明。
         排列1 2 4 3 5的康托展开是X=2，展开式为
         X=0⋅4!+0⋅3!+1⋅2!+0⋅1!+0⋅0


         康托展开式
         算法描述
         只需计算有多少个排列在当前排列A的前面即可。如何算呢?举个例子，[3,7,4,9,1]，在它前面的必然是某位置i对应元素比给定数组小，
         而i左侧和原数组一样。也即[3,7,4,1,X]，[3,7,1,X,X]，[3,1或4,X,X,X]，[1,X,X,X,X]。
         而第i个元素，比原数组小的情况有多少种，其实就是A[i]右侧有多少元素比A[i]小，乘上A[i]右侧元素全排列数，即A[i]右侧元素数量的阶乘。
         i从右往左看，比当前A[i]小的右侧元素数量分别为1,1,2,1

         <----------
         [3,7,4,9,1] : only 1 is smaller than 9    1
                *
         [3,7,4,9,1] : only 1 is smaller than 4    1
              *
         [3,7,4,9,1] : 4 and 9 are smaller than 7  2
            *
         [3,7,4,9,1] : only 1 is smaller than 3    1
          *
        所以最终字典序在当前A之前的数量为:  1x4! + 1x3! + 2x3! + 1x1! + 0x0! = 39，故当前A的字典序为40。



     */
    public long permutationIndex(int[] A) {
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
