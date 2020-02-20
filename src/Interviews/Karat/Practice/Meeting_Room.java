package src.Interviews.Karat.Practice;

import java.util.*;

class Meeting_Room {
    /**
     * Question 1
     */
    public static boolean schedule(int[][] meetings, int[] m) {
        for (int[] meeting : meetings) {
            if (Math.max(meeting[0], m[0]) <= Math.min(meeting[1], m[1])) {
                return false;
            }
        }

        return true;
    }

    /**
     * Question 2
     */
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

    public static List<List<String>> scheduleMeetings(String[][] rooms, String[][] meetings) {
        List<List<String>> res = new ArrayList<>();
        if (rooms == null || meetings == null) return res;

        Arrays.sort(rooms, (a, b) -> Integer.parseInt(b[1]) - Integer.parseInt(a[1]));
        Arrays.sort(meetings, (a, b) -> Integer.parseInt(b[1]) - Integer.parseInt(a[1]));

        /**
         * list of meeting schedules, for each list in schedule, it saves
         * index of meeting in meetings[][], in the last for loop, we will
         * use this index to get meeting name and populate res.
         */
        List<List<Integer>> schedule = new ArrayList<>();

        for (String[] room : rooms) {
            String name = room[0];

            List<String> l = new ArrayList<>();
            l.add(name);
            res.add(l);

            List<Integer> s = new ArrayList<>();
            schedule.add(s);
        }

        for (int i = 0; i < meetings.length; i++) {
            int size = Integer.parseInt(meetings[i][1]);

            int j = 0;

            boolean fit = false;
            while (j < rooms.length && size <= Integer.parseInt(rooms[j][1]) && !fit) {
                if (canFit(meetings[i], schedule.get(j), meetings)) {
                    fit = true;
                    schedule.get(j).add(i);
                }
                j++;
            }

            if (!fit) {
                System.out.println(meetings[i][0] + " not fit");
                return new ArrayList<>();
            }
        }

        for (int i = 0; i < schedule.size(); i++) {
            List<Integer> meetingList = schedule.get(i);
            if (meetingList == null || meetingList.size() == 0) continue;

            for (int meetingIdx : meetingList) {
                res.get(i).add(meetings[meetingIdx][0]);
            }
        }

        return res;
    }

    private static boolean canFit(String[] meeting, List<Integer> meetingList, String[][] meetings) {
        if (meetingList == null || meetingList.size() == 0) return true;

        int s1 = Integer.parseInt(meeting[2]);
        int e1 = Integer.parseInt(meeting[3]);

        for (int idx : meetingList) {
            String[] cur = meetings[idx];

            int s2 = Integer.parseInt(cur[2]);
            int e2 = Integer.parseInt(cur[3]);

            if (Math.max(s1, s2) < Math.min(e1, e2)) return false;
        }

        return true;
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

    private static void printRes(List<List<String>> lists) {
        System.out.println("==result==");
        if (lists.size() == 0)
            System.out.println("Impossible");

        for (List<String> l : lists) {
            System.out.println(Arrays.toString(l.toArray()));
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

        printRes(scheduleMeetings(rooms, meetings1));
        printRes(scheduleMeetings(rooms, meetings2));
    }
}

