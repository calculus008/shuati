package src.Interviews.Servicenow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Expression_Add_Operators_Variation {
    /**
     * Given a string of numbers separated by spaces, devise an algorithm to figure out whether or not
     * you can arrive at 42 with the numbers using only addition, subtraction, and multiplication.
     * <p>
     * Variation of LE_282_Expression_Add_Operators
     *
     *
     * Or the question is:
     * https://stackoverflow.com/questions/29293375/given-5-numbers-by-only-using-addition-multiplication-and-substraction-check-wh
     *
     * Given five numbers between 1-52 check whether you can generate 42 by using operations addition,
     * multiplication and subtraction. You can use these operations any number of times. I got this
     * question during an online test and couldn't do it.
     */

    public static boolean addOperators(String num, int target) {
        if (null == num || num.length() == 0) {
            return false;
        }

        String[] s = num.split(" ");

        int[] nums = new int[s.length];
        for (int i = 0; i < s.length; i++) {
            nums[i] = Integer.parseInt(s[i]);
        }

        List<List<Integer>> perm = getperm(nums);//LE_46_Permutation

        for (List<Integer> p : perm) {
            if (helper(p, target, 0, 0, 0)) return true;
        }

        return false;

//        return helper(nums, target, 0, 0, 0);
    }

    public static List<List<Integer>> getperm(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        if (nums == null || nums.length == 0) return res;

        helper1(res, nums, new ArrayList<>(), new boolean[nums.length]);

        return res;
    }

    private static void helper1(List<List<Integer>> res, int[] nums, List<Integer> temp, boolean[] visited) {
        if (temp.size() == nums.length) {
            res.add(new ArrayList<>(temp));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if (visited[i]) continue;

            visited[i] = true;
            temp.add(nums[i]);
            helper1(res, nums, temp, visited);
            visited[i] = false;
            temp.remove(temp.size() - 1);
        }
    }

    //assume no dup in nums[]
    private static boolean helper(List<Integer> nums, int target, int pos, long val, long pre) {
        if (pos == nums.size()) {
            if (val == target) {
                return true;
            }

            return false;
        }

        for (int i = pos; i < nums.size(); i++) {
            int cur = nums.get(i);

            if (helper(nums, target, i + 1, val + cur, cur)) {
                return true;
            }

            if (helper(nums, target, i + 1, val - cur, -cur)) {
                return true;
            }

            if (helper(nums, target, i + 1, val - pre + pre * cur, pre * cur)) {
                return true;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        String input = "1 2 3 4 5";
        boolean res = addOperators(input, 42);
        System.out.println(res);
    }

}
