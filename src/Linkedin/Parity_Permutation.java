package Linkedin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parity_Permutation {
    /**
     * 给你一个整数n, 你被要求返回List<List<Integer>>, 每个List<Integer> 都由1到n组成并且奇偶相间,
     * 按照"lexicographic order"排好。
     *
     * 例：
     * n = 4. you should return:
     * [
     *  [1,2,3,4],
     *  [1,4,3,2],
     *  [2,1,4,3],
     *  [2,3,4,1],
     *  [3,2,1,4],
     *  [3,4,1,2],
     *  [4,1,2,3],
     *  [4,3,2,1]
     * ]
     * 不仅要奇偶相间而且要从小到大排列所有符合的List<Integer>.
     */

    public static List<List<Integer>> permutation(int n) {
        List<List<Integer>> res = new ArrayList<>();
        if (n <= 0) {
            return res;
        }

        helper(n, res, new ArrayList<>(), new boolean[n], 0);
        return res;
    }

    private static void helper(int n, List<List<Integer>> res, List<Integer> temp, boolean[] visited, int pre) {
        if (temp.size() == n) {
//            if (isValid(temp)) {
                res.add(new ArrayList<>(temp));
//            }
            return;
        }

        for (int i = 1; i <= n; i++) {
            if (visited[i - 1])  {
                continue;
            }

            if (pre != 0 && pre % 2 == i % 2) {
                continue;
            }

            temp.add(i);
            visited[i - 1] = true;

            helper(n, res, temp, visited, i);

            temp.remove(temp.size() - 1);
            visited[i - 1] = false;
        }
    }

    public static void main(String[] args) {
        List<List<Integer>> res = permutation(4);

        for (List<Integer> l : res) {
            System.out.println(Arrays.toString(l.toArray()));
        }
    }

}
