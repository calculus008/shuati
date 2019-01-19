package leetcode;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by yuank on 11/14/18.
 */
public class LE_823_Binary_Trees_With_Factors {
    /**
         Given an array of unique integers, each integer is strictly greater than 1.

         We make a binary tree using these integers and each number may be used for any number of times.

         Each non-leaf node's value should be equal to the product of the values of it's children.
         (根结点的值需为子节点值的乘积）

         How many binary trees can we make?  Return the answer modulo 10 ** 9 + 7.

         Example 1:

         Input: A = [2, 4]
         Output: 3
         Explanation: We can make these trees: [2], [4], [4, 2, 2]
         Example 2:

         Input: A = [2, 4, 5, 10]
         Output: 7
         Explanation: We can make these trees: [2], [4], [5], [10], [4, 2, 2], [10, 2, 5], [10, 5, 2].


         Note:

         1 <= A.length <= 1000.
         2 <= A[i] <= 10 ^ 9.

         Medium
     */

    /**
     * https://zxi.mytechroad.com/blog/dynamic-programming/leetcode-823-binary-trees-with-factors/
     *
     * DP
     *
     * 1.For a, b, c in A, c = a * b
     * dp(c) = number of binary trees rooted at c
     * dp(c) = sum (dp(a) * dp(b)) (a, b are factors for c and a, b, c should all be in A)
     * answer is sum(dp(c))
     *
     * 2.Since root value is the product of its 2 children, from bottom up, root value increases.
     * Therefore, first sort A, we can go from bottom (smaller value) up.
     *
     * 3.Here can't use array as structure to record sub problems, need to use a HashMap.
     * root value is key and number of binary trees rooted with key is value.
     *
     * Time  : O(n ^ 2)
     * Space : O(n)
     */

    public static int numFactoredBinaryTrees(int[] A) {
        long mod = 1000000007;
        Arrays.sort(A);

        HashMap<Integer, Long> map = new HashMap<>();

        for (int i = 0; i < A.length; i++) {
            map.put(A[i], 1L);

            for (int j = 0; j < i; j++) {
                System.out.println("-----------------");
                System.out.println("j = " + j + ", i = " + i);

                int a = A[i] / A[j];
                if (A[i] % A[j] == 0 && map.containsKey(a)) {
                    System.out.println("root : " + A[i] + ", chilredn : " + A[j] + ", " + a);

                    long sum = map.get(A[i]) + (map.get(A[j]) * map.get(a)) % mod;

                    System.out.println("put key:" + A[i] + ", value:" + sum);
                    map.put(A[i], sum);
                }
            }
        }

        long sum = 0;
        for (long value : map.values()) {
            sum += value;
        }

        long res = sum % mod;

        return  (int)res;
    }

    public static void main(String [] args) {
        int[] input = {2, 4, 5, 10};
        numFactoredBinaryTrees(input);
    }
}
