package leetcode;

import java.util.*;

/**
 * Created by yuank on 2/28/18.
 */
public class LE_47_Permutation_II {
    /**
        Given a collection of numbers that might contain duplicates, return all possible unique permutations.

        For example,
        [1,1,2] have the following unique permutations:
        [
          [1,1,2],
          [1,2,1],
          [2,1,1]
        ]
     */

    class Solution1 {
        public List<List<Integer>> permuteUnique(int[] nums) {
            List<List<Integer>> res = new ArrayList<>();
            if (nums == null) {
                return res;
            }

            Arrays.sort(nums);
            helper(nums, res, new ArrayList<>(), new boolean[nums.length]);
            return res;
        }

        private void helper(int[] nums, List<List<Integer>> res, List<Integer> temp, boolean[] visited) {
            if (temp.size() == nums.length) {
                res.add(new ArrayList<>(temp));
                return;
            }

            for (int i = 0; i < nums.length; i++) {
                if (visited[i]) {
                    continue;
                }

                /**
                 * For duplicates, we just need to choose one of them as "representative",
                 * the rule we use is to choose the first one (after sort).
                 *
                 * nums[i] == nums[i - 1] : only compare its previous neighbour
                 * i > 0                  : make sure "nums[i - 1]" not out of index boundary.
                 * !visited[i - 1]        : since we always choose the first one as "representative",
                 *                          now with identical char at i and i - 1, the previous one is not used,
                 *                          therefore it is invalid case, just continue.
                 */
                if (i > 0 && nums[i] == nums[i - 1] && !visited[i - 1]) {
                    continue;
                }

                temp.add(nums[i]);
                visited[i] = true;
                helper(nums, res, temp, visited);
                temp.remove(temp.size() - 1);
                visited[i] = false;
            }
        }
    }

    /**
     * With Generic class
     * @param <T>
     */
    class Permutation<T extends Comparable<T>> {
        public List<List<T>> permutation(T[] input) {
            List<List<T>> result = new ArrayList<>();
            if (input == null || input.length == 0) {
                return result;
            }
            Arrays.sort(input, new Comparator<T>() {
                @Override
                public int compare(T t1, T t2) {
                    if (t1.compareTo(t2) > 0) {
                        return 1;
                    }
                    else if (t1.compareTo(t2) < 0) {
                        return -1;
                    }
                    return 0;
                }
            });
            helper(result, new ArrayList<>(), new HashSet<>(), input);
            return result;
        }

        private void helper(List<List<T>> result, List<T> path, Set<Integer> visited, T[] input) {
            if (path.size() == input.length) {
                result.add(new ArrayList<>(path));
                return;
            }
            for (int i = 0; i < input.length; i++) {
                if (visited.contains(i) || (i > 0 && input[i].equals(input[i - 1]) && !visited.contains(i - 1))) {
                    continue;
                }
                path.add(input[i]);
                visited.add(i);
                helper(result, path, visited, input);
                path.remove(path.size() - 1);
                visited.remove(i);
            }
        }
    }
}
