package lintcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by yuank on 10/22/18.
 */
public class LI_131_The_Skyline_Problem {
    /**
         Given N buildings in a x-axis，each building is a rectangle and can be represented by
         a triple (start, end, height)，where start is the start position on x-axis, end is the
         end position on x-axis and height is the height of the building. Buildings may overlap
         if you see them from far away，find the outline of them。

         An outline can be represented by a triple, (start, end, height), where start is the
         start position on x-axis of the outline, end is the end position on x-axis and height
         is the height of the outline.

         Super
     */

    public class Solution {
        class Pair{
            int x;
            int h;
            public Pair(int x, int h) {
                this.x = x;
                this.h = h;
            }
        }

        /**
         * @param buildings: A list of lists of integers
         * @return: Find the outline of those buildings
         */
        public List<List<Integer>> buildingOutline(int[][] buildings) {
            List<List<Integer>> res = new ArrayList<>();
            if (buildings == null || buildings.length == 0 || buildings[0].length == 0) return res;

            List<Pair> pairs = new ArrayList<>();
            for (int[] building : buildings) {
                pairs.add(new Pair(building[0], -building[2]));
                pairs.add(new Pair(building[1], building[2]));
            }

            Collections.sort(pairs, (a, b) -> {
                int comp = 0;
                if (a.x == b.x) {
                    comp = Integer.compare(a.h, b.h);
                } else {
                    comp = Integer.compare(a.x, b.x);
                }
                return comp;
            });

            int pre = 0;
            TreeMap<Integer, Integer> map = new TreeMap<>(Collections.reverseOrder());//!!!"Collections.reverseOrder()"
            map.put(0, 1);

            List<int[]> points = new ArrayList<>();
            for (Pair p : pairs) {
                if (p.h < 0) {
                    map.put(-p.h, map.getOrDefault(-p.h, 0) + 1);
                } else {
                    int val = map.get(p.h);
                    if (val == 1) {
                        map.remove(p.h);
                    } else {
                        map.put(p.h, val - 1);
                    }
                }

                int cur = map.firstKey();
                if (pre != cur) {
                    points.add(new int[]{p.x, cur});
                    pre = cur;
                }
            }

            int start = 0, height = 0;
            for (int i = 0; i < points.size(); i++) {
                int[] cur = points.get(i);

                if (height != 0) {
                    List<Integer> temp = new ArrayList<>();
                    temp.add(start);
                    temp.add(cur[0]);
                    temp.add(height);
                    res.add(temp);
                }

                start = cur[0];
                height = cur[1];
            }

            return res;
        }
    }
}
