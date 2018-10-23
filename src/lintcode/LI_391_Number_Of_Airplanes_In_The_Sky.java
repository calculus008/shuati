package lintcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import common.Interval;

/**
 * Created by yuank on 10/21/18.
 */
public class LI_391_Number_Of_Airplanes_In_The_Sky {
    /**
         Given an interval list which are flying and landing time of the flight.
         How many airplanes are on the sky at most?

         Example
         For interval list

         [
             (1,10),
             (2,3),
             (5,8),
             (4,7)
         ]
         Return 3

         Notice
         If landing and flying happens at the same time,
         we consider landing should happen at first.
     */

    public class Solution {
        class Pair {
            int x;
            int flag;

            public Pair(int x, int flag){
                this.x = x;
                this.flag = flag;
            }
        }

        public int countOfAirplanes(List<Interval> airplanes) {
            if (airplanes == null || airplanes.size() == 0) return 0;

            List<Pair> list = new ArrayList<>();
            for (Interval airplane : airplanes) {
                list.add(new Pair(airplane.start, 1));
                list.add(new Pair(airplane.end, 0));
            }

            /**
             * start is 1, end is 0, so when x is the same,
             * sort by flag, then 0 (end or land) is in
             * front of 1 (start or take-off)
             */
            Collections.sort(list, (a, b) -> {
                int comp = 0;
                if (a.x == b.x) {
                    comp = a.flag - b.flag;
                } else {
                    comp = a.x - b.x;
                }
                return comp;
            });

            int count = 0;
            int res = Integer.MIN_VALUE;
            for (Pair p : list) {
                if (p.flag == 1) {
                    count++;
                } else {
                    count--;
                }

                res = Math.max(res, count);
            }

            return res;
        }
    }
}
