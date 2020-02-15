package Interviews.Karat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Meeting_Room_Practice {
    /**
     * Question 1
     */
    public static boolean schedule(int[][] meetings, int[] a) {
        for (int[] m : meetings) {
            int start = Math.max(m[0], a[0]);
            int end = Math.min(m[1], a[1]);

            if (start <= end) return false;
        }

        return true;
    }


    /**
     * Question 2
     * First merge given interval list.
     * Create idle slot from the merged list, notice the start and end slots should be added
     */
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
        if (last[1] < 2359) {
            res.add(new int[]{last[1], 2359});
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

    /**
     * Question 3
     * Greedy, sorting, place meeting with the large size first
     *
     * m : number of rooms,
     * n : number of meetings
     * Time : O(nlogn + mlogm + n * m * n)
     */
    public static List<List<String>> scheduleMeetings(String[][] rooms, String[][] meetings) {
        List<List<String>> res = new ArrayList<>();

        Arrays.sort(rooms, (a, b) -> Integer.parseInt(b[1]) - Integer.parseInt(a[1]));
        Arrays.sort(meetings, (a, b) -> Integer.parseInt(b[1]) - Integer.parseInt(a[1]));

        System.out.println(Arrays.deepToString(rooms));
        System.out.println(Arrays.deepToString(meetings));

        List<List<Integer>> schedule = new ArrayList<>();

        for (int i = 0; i < rooms.length; i++) {
            List<String> l = new ArrayList<>();
            l.add(rooms[i][0]);
            res.add(l);

            List<Integer> s = new ArrayList<>();
            schedule.add(s);
        }

        for (int i = 0; i < meetings.length; i++) {
            int meetingSize = Integer.parseInt(meetings[i][1]);

            int j = 0;
            boolean fit = false;
            while (j < rooms.length && Integer.parseInt(rooms[j][1]) >= meetingSize && !fit) {
                if (canFit(meetings[i], schedule.get(j), meetings)) {
                    fit = true;
                    schedule.get(j).add(i);
                }
                j++;
            }

            System.out.println(meetings[i][0] + ", fit="+fit);

            if (!fit) return new ArrayList<>();
        }

        for (int i = 0; i < schedule.size(); i++) {
            List<Integer> mlist = schedule.get(i);
            if (mlist == null) continue;

            for (int idx : mlist) {
                res.get(i).add(meetings[idx][0]);
            }
        }

        return res;
    }

    public static boolean canFit(String[] meeting, List<Integer> list, String[][] meetings) {
        if (list == null || list.size() == 0) return true;

        for (int i : list) {
            String[] cur = meetings[i];

            int s1 = Integer.parseInt(meeting[2]);
            int e1 = Integer.parseInt(meeting[3]);
            int s2 = Integer.parseInt(cur[2]);
            int e2 = Integer.parseInt(cur[3]);

            if (Math.max(s1, s2) < Math.min(e1, e2)) {
//                System.out.println("conflict : " + meeting[0] + "-" +s1 + "," + e1 + "," + s2 + "," + e2);
                return false;
            }
        }

        return true;
    }

    private static void printRes1(List<List<String>> lists) {
        System.out.println("==result==");
        if (lists.size() == 0)
            System.out.println("Impossible");

        for (List<String> l : lists) {
            System.out.println(Arrays.toString(l.toArray()));
        }
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

//        printRes(idleTime1(schedules));
        printRes(idleTime2(schedules1));

        String[][] rooms = {
                {"Phone Booth", "2"},
                {"War Room", "6"},
                {"Conference Room", "12"}
        };

        String[][] meetings1 = {
                {"Standup", "4", "1230", "1300"},
                {"Scrum", "6", "1230", "1330"},
                {"UAT", "10", "1300", "1500"}
        };

        String[][] meetings2 = {
                {"Manager 1:1", "2", "900",  "1030"},
                {"Budget",      "4", "900",  "1000"},
                {"Forecasting", "6", "900",  "1100"},
                {"SVP 1:1",     "2", "1000", "1030"},
                {"UX Testing",  "4", "1015", "1130"}
        };

        printRes1(scheduleMeetings(rooms, meetings1));
        printRes1(scheduleMeetings(rooms, meetings2));

    }
}
