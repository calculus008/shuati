package src.Interviews.Karat;

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

    public static List<int[]> idleTime(int[][] meetings) {
        List<int[]> list = new ArrayList<>();
        List<int[]> res = new ArrayList<>();

        for (int[] m : meetings) {
            list.add(m);
        }

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
        printRes(idleTime(schedules));
    }
}
