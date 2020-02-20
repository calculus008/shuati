package src.Interviews.Karat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Manage_Meeting_Rooms {
    /**
     * 第一题找所有人空闲的时间，这是第二题
     *
     *  We are writing a tool to help users manage their calendars at a corporation.
     *  Given a list of meeting times and sizes, and a list of the available meeting
     *  room sizes, find a way to fit all of the meetings in a big enough room.
     *
     *  Sample input:
     *
     *  rooms = {
     *    "Phone Booth":     {"size":  2},
     *    "War Room":        {"size":  6},
     *    "Conference Room": {"size": 12},
     *  }
     *
     *  meetings1 = {
     *    "Standup": {"size":  4, "start": 1230, "end": 1300},
     *    "Scrum":   {"size":  6, "start": 1230, "end": 1330},
     *    "UAT":     {"size": 10, "start": 1300, "end": 1500},
     *  }
     *
     *  meetings2 = {
     *    "Manager 1:1": {"size": 2, "start":  900, "end": 1030},
     *    "Budget":      {"size": 4, "start":  900, "end": 1000},
     *    "Forecasting": {"size": 6, "start":  900, "end": 1100},
     *    "SVP 1:1":     {"size": 2, "start": 1000, "end": 1030},
     *    "UX Testing":  {"size": 4, "start": 1015, "end": 1130},
     *  }
     *
     *  Expected output:
     *
     *  assignRooms(meetings1, rooms)
     *   => { Standup: Conference Room
     *      Scrum: War Room
     *      UAT: Conference Room }
     *
     *  assignRooms(meetings2, rooms)
     *   => Impossible!
     *
     *  m = number of meetings
     *
     *  面试官说可以暴力遍历，贪心或者dp
     */

    /**
     * Greedy, sort meeting rooms and meetings by size, try to fit the meeting with max size
     * to the meeting room with the largest capacity. If there's conflict, move to the next
     * one.
     *
     * m : number of rooms,
     * n : number of meetings
     *
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
            l.add(rooms[i][0]);//meeting room name
            res.add(l);

            /**
             * init schedule
             */
            List<Integer> s = new ArrayList<>();
            schedule.add(s);
        }

        /**
         * Now meetings is sorted by size, try to fit from the largest size into the
         * largest possible rooms.
         */
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

    private static void printRes(List<List<String>> lists) {
        System.out.println("==result==");
        if (lists.size() == 0)
            System.out.println("Impossible");

        for (List<String> l : lists) {
            System.out.println(Arrays.toString(l.toArray()));
        }
    }

    public static void main(String[] args) {
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
