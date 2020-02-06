package Interviews.Servicenow;

import java.util.ArrayList;
import java.util.List;

/**
 * Given a string of numbers separated by spaces, devise an algorithm to figure out whether or not
 * you can arrive at 42 with the numbers using only addition, subtraction, and multiplication.
 * (variation of LE_282_Expression_Add_Operators)
 *
 * Alice has invented a new card game to play with Bob. Alice made a deck of cards with random values
 * between 1 and 52. Bob picks 5 cards. Then, he has to rearrange the cards so that by utilizing the
 * operations plus, minus, or times, the value of the cards reach Alice's favorite number, 42.
 * More precisely, find operations such that ((((val1 op1 val2) op2 val3) op3 val4) op4 val5) = 42.
 *
 * Help Bob by writing a program to determine whether it is possible to reach 42 given 5 card values.
 *
 * For example, Bob picks 5 cards out of the deck containing 60, 1, 3, 5, and 20. Bob rearranges the cards
 * and supplies four operations, so that 5 * 20 - 60 + 3 - 1 = 42.
 * Input:
 * The input consists of five integers on a line, separated by spaces. Each integer V is 0 <= V <= 52.
 * Output:
 * Print a line containing "YES" if it is possible to reach the value 42 according to the rules of the game,
 * or "NO" otherwise.
 */

/**
 * !!!
 * This is the key:
 * ((((val1 op1 val2) op2 val3) op3 val4) op4 val5) = 42
 *
 * We don't need to worry about backtrack "*".
 *
 * It's different from val1 op1 val2 op2 val3 op3 val4 op4 val5.
 *
 */
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
//        int[] nums = {1, 2, 3, 4, 5};
        int[] nums = {60, 1, 3, 5, 20};
        canReachTarget(nums);
    }
}
