package src.Interviews.Servicenow;

import java.util.ArrayList;
import java.util.List;

public class Five_Cards_Sum_To_Target {
    public static boolean canReachTarget(int[] nums) {
        if (nums == null || nums.length == 0) return false;

        boolean[] visited = new boolean[nums.length];
        return helper1(nums, new ArrayList<>(), visited, 0, 0, 42, "");
    }

    private static boolean helper1(int[] nums, List<Integer> temp, boolean[] visited, int count, int cur, int target, String s) {
        if (count == nums.length) {
            if (cur == target) {
                System.out.println(s);
                return true;
            } else {
                return false;
            }
        }

        for (int i = 0; i < nums.length; i++) {
            if (visited[i]) continue;

            visited[i] = true;
            temp.add(nums[i]);
            if (helper1(nums, temp, visited, count + 1, count == 0 ? nums[i] : cur + nums[i], target, count == 0 ? s + nums[i] : s + "+" + nums[i])
                    ||  helper1(nums, temp, visited, count + 1 ,count == 0 ? nums[i] : cur - nums[i], target, count == 0 ? s + nums[i] : s + "-" + nums[i])
                    ||  helper1(nums, temp, visited, count + 1, count == 0 ? nums[i] : cur * nums[i], target, count == 0 ? s + nums[i] : s + "*" + nums[i])) {
                return true;
            }
            visited[i] = false;
            temp.remove(temp.size() - 1);
        }

        return false;
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4, 5};
        canReachTarget(nums);
    }
}
