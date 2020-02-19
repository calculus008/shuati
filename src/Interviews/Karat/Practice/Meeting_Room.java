package Interviews.Karat.Practice;

import java.util.*;

class Meeting_Room {
    public static boolean schedule(int[][] meetings, int[] m) {
        for (int[] meeting : meetings) {
            if (Math.max(meeting[0], m[0]) <= Math.min(meeting[1], m[1])) {
                return false;
            }
        }

        return true;
    }

    public static List<int[]> getIdle(int[][] meetings) {
        List<int[]> res = new ArrayList<>();

        if (meetings == null || meetings.length == 0) {
            res.add(new int[]{0, 2359});
            return res;
        }

        List<int[]> merged = mergeIntervals(meetings);

        int[] first = merged.get(0);
        if (first[0] > 0) {
            res.add(new int[]{0, first[0]});
        }

        int[] pre = first;
        for (int i = 1; i < merged.size(); i++) {
            int[] cur = merged.get(i);
            if (pre[1] < cur[0]) {
                res.add(new int[]{pre[1], cur[0]});
                pre = cur;
            }
        }

        int[] last = merged.get(merged.size() - 1);
        if (last[1] < 2359) {
            res.add(new int[]{pre[1], 2359});
        }

        return res;
    }

    private static List<int[]> mergeIntervals(int[][] meetings) {
        List<int[]> res = new ArrayList<>();

        /**
         * !!!!!!!!
         */
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

    public static String convertTime(int time) {
        int hour = time / 100;
        int min = time % 100;

        String minStr = min < 10 ? "0" + min : String.valueOf(min);
        String hourStr = hour < 10 ? "0" + hour : String.valueOf(hour);

        return hourStr + ":" + minStr;
    }

    public static int timeToInt(String time) {
        String[] parts = time.split(":");

        String hour = parts[0];
        String min = parts[1];

        return Integer.parseInt(hour) * 100 + Integer.parseInt(min);
    }

    public static void printRes(String title, List<int[]> list) {
        System.out.println(title);
        for (int[] e : list) {
            System.out.println("[" + convertTime(e[0]) + ", " + convertTime(e[1]) + "]");
        }
    }

    public static void main(String[] args) {
        /**
         * false
         * false
         * true
         */
        int[][] schedules = new int[][]{{1300, 1500}, {930, 1200}, {830, 845}};
        int[] target1 = new int[]{820, 830};
        int[] target2 = new int[]{1450, 1500};
        int[] target3 = new int[]{850, 900};
        System.out.println(schedule(schedules, target1));
        System.out.println(schedule(schedules, target2));
        System.out.println(schedule(schedules, target3));

        /**
         *         * [0, 830]
         *          * [845, 930]
         *          * [1200, 1300]
         *          * [1500, 2359]
         */
        int[][] schedules1 = new int[][] {{1300,1500}, {1345, 1400}, {1415, 1430}, {930,1200}, {830,845}};
        printRes("===idle time===", getIdle(schedules1));

        System.out.println(timeToInt("00:00"));
        System.out.println(timeToInt("08:00"));
        System.out.println(timeToInt("08:05"));
        System.out.println(timeToInt("20:05"));


    }
}

