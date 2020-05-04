package leetcode;

public class LE_1344_Angle_Between_Hands_Of_A_Clock {
    /**
     * Given two numbers, hour and minutes. Return the smaller angle (in degrees)
     * formed between the hour and the minute hand.
     *
     * Constraints:
     *
     * 1 <= hour <= 12
     * 0 <= minutes <= 59
     * Answers within 10^-5 of the actual value will be accepted as correct.
     *
     * Medium
     */

    /**
     * The idea is to calculate separately the angles between 0-minutes vertical line
     * and each hand. The answer is the difference between these two angles.
     */
    class Solution {
        public double angleClock(int hour, int minutes) {
            int oneMinAngle = 360 / 60;
            int oneHourAngle = 360 / 12;

            double minAngle = minutes * oneMinAngle;

            /**
             * !!!
             * "minutes / 60.0" : can't be 60, the result will be int and lose accuracy
             * "hour % 12" : given hour is in 24 hour format
             */
            double hourAngle = (hour % 12 + minutes / 60.0) * oneHourAngle;

            double diff = Math.abs(minAngle - hourAngle);

            return Math.min(360 - diff, diff);
        }
    }
}
