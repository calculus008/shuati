package Linkedin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Multi_Dimension_Array_Sum {
    /**
     * Suppose you are given a class that implements a k-dimensional array
     * interface and you want to perform an operation that requires you to
     * iterate over all elements of the array using its indices. To be
     * specific, let's assume we want to calculate the sum of all elements in
     * the array. The interface only provides you a get(int[]) method which
     * allows one to fetch the element at that location given the indices along
     * each dimension.
     *
     * For e.g, suppose we are dealing with 4D arrays, given [2, 1, 3, 0], the
     * class will provide you array[2][1][3][0].
     *
     * Write a function that given an instance of the k-D array class and size
     * of its dimensions, calculates the sum of all elements.
     *
     * Input:
     * instance of MultiDimArray class that implements a k-D array of
     *        ints which provides a method x.get(int[]) to get the element
     *        located at the indices in the array.
     *
     * array of ints stating the size of each dimension of the k-D array.
     * @return an int which is the sum of all elements in the k-D array
     *
     * example: Given object m that holds a 2x2x3 array
     * a=[[[3, 2, 2], [1, 5, 0]], [[2, 0, 1], [1, 1, -2]]] (Only for illustration
     * purposes. This need not be the internal implementation of our k-D array)
     * the function call arraySum(m, [2, 2, 3]) should return 16
     * (=3+2+2+1+5+2+1+1+1-2)
     *
     *     public interface MultiDimArray {
     *         int get(vector<int> indices);
     *     }
     *
     *
     *     public int arraySum (MultiDimArrayImpl m, int [] dimensions) {
     *     }
     */


    /**
     * !!!
     * 用dfs做所有dimension上的不同index的combination，然后调用那个function求和。
     */

    /**
     * Dummy function
     * @param mArray 多维数组
     * @param dim 每个dim的长度
     * @return
     */
    public int sum(MultiDimensionArray mArray, int[] dim) {
        List<Integer> list = new ArrayList<Integer>(); // List是存的所有可能的下标组合， combination
        return helper(mArray, dim, 0, list);
    }

    /**
     * DFS, get all combinations of dims
     */
    private int helper(MultiDimensionArray mArray, int[] dim, int dimIndex, List<Integer> list) {
        if (dimIndex == dim.length) { // Done with all dimensions, 这是一串下标，唯一决定一个value
            int[] array = list.stream().mapToInt(i -> i).toArray();
            return mArray.getValue(array); // Get the value by indices
        }

        int sum = 0;
        for (int i = 0; i < dim[dimIndex]; i++) { // 一个维的所有下标
            list.add(i);
            sum += helper(mArray, dim, dimIndex + 1, list); // 下一维
            list.remove(list.size() - 1);
        }

        return sum;
    }
}

// Given a multi-dimensional arrays, compute the sum of all values.
// Given API getValue(dn, dn-1.... d0) dn = index at dimension.
class MultiDimensionArray {
    // provided function
    public static int getValue(int[] indexOfDimension) {
        int value = 1; // e.g.
        System.out.println(Arrays.toString(indexOfDimension));
        return value;
    }

    // lengthOfDeminsion: each dimension's length, assume it is valid: lengthOfDeminsion[i]>0.
    public static Integer sum(MultiDimensionArray mArray, int[] lengthOfDeminsion) {
        if (lengthOfDeminsion == null || lengthOfDeminsion.length == 0) {
            return null;
        }
        // O(N)solution. only iterator, no recursion, no extra space
        final int dims = lengthOfDeminsion.length;

        for (int i = 0; i < dims; i++) {
            lengthOfDeminsion[i]--;
        }

        int[] cur = new int[dims], max = lengthOfDeminsion;

        int sum = 0;
        int rightIdx;

        while (true) {
            sum += mArray.getValue(cur);
            if (Arrays.equals(cur, max)) break;

            rightIdx = dims - 1;
            cur[rightIdx]++;

            while (cur[rightIdx] > max[rightIdx]) {
                cur[rightIdx] = 0;
                rightIdx--;
                cur[rightIdx]++;
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        System.out.println(sum(new MultiDimensionArray(), new int[] {3, 4, 2}));
    }
}
