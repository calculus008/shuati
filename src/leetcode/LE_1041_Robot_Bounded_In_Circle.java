package leetcode;

public class LE_1041_Robot_Bounded_In_Circle {
    /**
     * On an infinite plane, a robot initially stands at (0, 0) and faces north. The robot can receive one of three instructions:
     *
     * "G": go straight 1 unit;
     * "L": turn 90 degrees to the left;
     * "R": turn 90 degrees to the right.
     * The robot performs the instructions given in order, and repeats them forever(!!!).
     *
     * Return true if and only if there exists a circle in the plane such that the robot never leaves the circle.
     *
     * Example 1:
     *
     * Input: instructions = "GGLLGG"
     * Output: true
     * Explanation: The robot moves from (0,0) to (0,2), turns 180 degrees, and then returns to (0,0).
     * When repeating these instructions, the robot remains in the circle of radius 2 centered at the origin.
     *
     *
     * Example 2:
     *
     * Input: instructions = "GG"
     * Output: false
     * Explanation: The robot moves north indefinitely.
     * Example 3:
     *
     * Input: instructions = "GL"
     * Output: true
     * Explanation: The robot moves from (0, 0) -> (0, 1) -> (-1, 1) -> (-1, 0) -> (0, 0) -> ...
     *
     * Medium
     */

    /**
     * https://leetcode.com/problems/robot-bounded-in-circle/solution/
     *
     * Tricky part - "..and repeats them forever". So checking if robot returns to starting point after the given
     * sequence is just one of the possibility to meet the requirement.
     *
     * https://leetcode.com/problems/robot-bounded-in-circle/discuss/290856/JavaC%2B%2BPython-Let-Chopper-Help-Explain
     *
     * Starting at the origin and face north (0,1),
     * after one sequence of instructions,
     *
     * if chopper return to the origin, he is obvious in an circle.
     * if chopper finishes with face not towards north,
     * it will get back to the initial status in another one or three sequences.
     *
     * (x,y) is the location of chopper.
     * d[i] is the direction he is facing.
     *
     * i = (i + 1) % 4 will turn right
     * i = (i + 3) % 4 will turn left
     *
     * **
     *
     *
     *
     * Check the final status after instructions.
     */
    class Solution {
        public boolean isRobotBounded(String instructions) {
            int x = 0, y = 0;

            /**
             * This is the efficient and fancy way to map with current direction to the next direction given a turn action (left or right).
             * Given current direction, what is the delta of position will change if tne next sequence is "G".
             * 0 = up, 1 = left, 2 = down, 3 = left
             *
             * Turn left = Turn right * 3
             *
             * If you don't use this fancy method, you can have a mapping function to map from direction char to index...,
             * it also should work.
             */
            int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

            int i = 0;
            for (char c : instructions.toCharArray()) {
                if (c == 'R') {
                    i = (i + 1) % 4;
                } else if (c == 'L') {
                    i = (i + 3) % 4;
                } else {
                    x += dirs[i][0];
                    y += dirs[i][1];
                }
            }

            return (x == 0 && y == 0) || i != 0;
        }
    }

}
