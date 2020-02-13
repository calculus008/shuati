package Interviews.Karat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Meeting_Room_Practice {
    public static boolean schedule(int[][] meetings, int[] a) {
        for (int[] m : meetings) {
            int start = Math.max(m[0], a[0]);
            int end = Math.min(m[1], a[1]);

            if (start <= end) return false;
        }

        return true;
    }

    /**
     * If given meetings have no overlap
     */
    public static List<int[]> idleTime1(int[][] meetings) {
        List<int[]> list = new ArrayList<>();
        List<int[]> res = new ArrayList<>();

        for (int[] m : meetings) {
            list.add(m);
        }

        /**
         * !!!
         */
        Collections.sort(list, (a, b) -> a[0] - b[0]);

        int[] first = list.get(0);
        if (first[0] > 0) {
            res.add(new int[]{0, first[0]});
        }

        for (int i = 1; i < list.size(); i++) {
            int[] pre = list.get(i - 1);
            int[] cur = list.get(i);

            if (cur[0] > pre[1]) {
                res.add(new int[]{pre[1], cur[0]});
            }
        }

        int[] last = list.get(list.size() - 1);
        if (last[1] < 2400) {
            res.add(new int[]{last[1], 2400});
        }
        return res;
    }

    public static List<int[]> idleTime2(int[][] meetings) {
        List<int[]> res = new ArrayList<>();
        if (null == meetings || meetings.length == 0) return res;

        List<int[]> list = mergeIntervals(meetings);

        int[] first = list.get(0);
        if (first[0] > 0) {
            res.add(new int[]{0, first[0]});
        }

        for (int i = 1; i < list.size(); i++) {
            int[] pre = list.get(i - 1);
            int[] cur = list.get(i);

            if (cur[0] > pre[1]) {
                res.add(new int[]{pre[1], cur[0]});
            }
        }

        int[] last = list.get(list.size() - 1);
        if (last[1] < 2400) {
            res.add(new int[]{last[1], 2400});
        }
        return res;
    }

    private static List<int[]> mergeIntervals(int[][] meetings) {
        List<int[]> res = new ArrayList<>();
        if (null == meetings || meetings.length == 0) return res;

        Arrays.sort(meetings, (a, b) -> a[0] - b[0]);

        int[] last = null;

        for (int[] m : meetings) {
            if (last == null || last[1] < m[0]) {
                res.add(m);
                last = m;
            } else {
                last[1] = Math.max(last[1], m[1]);
            }
        }

        return res;
    }

    public static void printRes(List<int[]> list) {
        for (int[] e : list) {
            System.out.println(Arrays.toString(e));
        }
    }

    public static void main(String[] args) {
        int[][] schedules = new int[][] {{1300,1500}, {930,1200}, {830,845}};
        int[] target1 = new int[] {820,830};
        int[] target2 = new int[] {1450,1500};
        int[] target3 = new int[] {850, 900};
        System.out.println(schedule(schedules, target1));
        System.out.println(schedule(schedules, target2));
        System.out.println(schedule(schedules, target3));

        int[][] schedules1 = new int[][] {{1300,1500}, {1345, 1400}, {1415, 1430}, {930,1200}, {830,845}};

        printRes(idleTime1(schedules));
        printRes(idleTime2(schedules1));

    }
}
