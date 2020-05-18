package Interviews.Nextdoor;

import java.util.Arrays;

public class Frame_Window {
    /**
     * Given an int n, print the *** window frame of the number;
     *
     * Example: input -> n = 6
     * output -> [
     * "********", --> 8 *
     * "* *", -> 2 *加六个' ' (space)
     * "* *",
     * "* *",
     * "* *",
     * "********"
     * ]
     *
     * ********
     * *      *
     * *      *
     * *      *
     * *      *
     * *      *
     * *      *
     * ********
     *
     * Input -> n = 3; Output -> [ "***“，
     * ”* *“，
     * ”*** ]
     */

    public static void frame_window(int n) {
        char[] filled = new char[n];
        Arrays.fill(filled, '*');

        char[] not_filled = new char[n];
        Arrays.fill(not_filled, ' ');
        not_filled[0] = '*';
        not_filled[n - 1] = '*';

        if (n >= 1) {
            for (int i = 0; i < n; i++) {
                // first and last row
                if (i == 0 || i == n - 1) {
                    System.out.println(new String(filled));
                } else {
                    System.out.println(new String(not_filled));
                }
            }
        }
    }

    public static void main(String[] args) {
        frame_window(8);
        System.out.println("Hello World!");
    }
}
