package leetcode;

/**
 * Created by yuank on 4/29/18.
 */

/**
 * https://www.youtube.com/watch?v=WbafSgetDDk&t=33s
 *
 * Also called Fenwick tree, solve the problem of improving time complexity of calculating range sum
 *  1.O(logn) for update and query
 *  2.Update and query forms different trees
 *  3.Root nodes is always with index of 2 ^ k
 *  4.Use trick of i & (-i) to get the low bit (lowest bit with 1, 0110, its lowbit is 0010)
 *  5.Tree has depth of log(n)
 *  6.Index for sum is always input array index plus 1
 */
class BinaryIndexedTree {
    public int[] sum;

    public BinaryIndexedTree(int n) {
        sum = new int[n + 1];
    }

    public void update(int i, int delta) {
        while (i < sum.length) {
            sum[i] += delta;
            i += lowBit(i);
        }
    }

    public int query(int i) {
        int res = 0;
        while (i > 0) {
            res += sum[i];
            i -= lowBit(i);
        }
        return res;
    }

    private int lowBit(int i) {
        return i & (-i);
    }
}
