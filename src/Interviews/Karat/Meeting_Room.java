package Interviews.Karat;

import java.util.*;

public class Meeting_Room {
    /**
     * 第一题：类似meeting rooms，输入是一个int[][] meetings, int start, int end,
     * 每个数都是时间，13：00 =》 1300， 9：30 =》 930， 看新的meeting 能不能安排到meetings
     * ex: {[1300, 1500], [930, 1200],[830, 845]},
     *     新的meeting[820, 830], return true; [1450, 1500] return false;
     * LE_729_My_Calendar_I
     *
     *
     * 第二题：类似merge interval，唯一的区别是输出，输出空闲的时间段，merge完后，再把两两个之间的
     * 空的输出就好，注意要加上0 - 第一个的start time
     * 第二题input是多个人的schedule组成的list，会有重合，要求输出所有空闲的时间段
     *
     * 第三题：是给会议分配房间。已知每个会议的人数、开始时间、结束时间，以及每个房间的容量。
     * 输入：
     *     会议列表：每个会议有名称、人数、开始时间、结束时间
     *     房间列表：每个房间有名称、容量。.本文原创自1point3acres论坛
     *
     *     输出：
     *     每个会议安排在哪个房间，格式是“会议名:房间名”
     *     如果没法都安排，输出"impossible"
     *
     * Variation:
     * You and your friend are going to hang out together, but you are all very busy. Your
     * friends tell you their busy times, you need to design a function to find a spare time
     * which could give all the spare time for everyone.
     * 输⼊入输出格式什什么的，全都⾃自⼰己定义。
     *
     * 我先问了了⼀一下，这个hang out是⼀一天的还是⼏几天的。然后说，先假设所有的 time都是同⼀一天的。
     * 我先写了了⼀一个⼩小函数，在输⼊入都是integer的情况下，格式是 [[1,4],[2,5], [7,8]] 这样，排序之后，
     * ⽤用⼀一个变量量keep latest busy time，如果新的busy time ⼤大于已选 timeslots 的 latest busy time，
     * 就找到⼀一段空闲。 然后她问有没有需要改进的地⽅方，我就写了了time conversion的函数，可以 take 9:40,
     * 11:20这样的输⼊入，转化成整数，再进⾏行行操作。 ⼜又问了了⼀一下时间复杂度，能不不能优化等。
     *
     * 题⽬目很容易易，就是有挺多细节需要注意的。⽐比如输出的时候，如果是 9h 4min，要输出 9:04 ⽽而不不是 9:4。
     * ⽐比如第⼀一个busy 时间是10:00开始busy 的，那10:00之前也要算上空闲。还有edge cases，如果⼤大家都没有busy要
     * 输出全部时间等。做的时候要⾮非常细⼼心。
     */
    static boolean rooms(int[][] schedules, int[] target) {
        List<int[]> list = new ArrayList<int[]>();
        for(int[] schedule: schedules) {
            list.add(schedule);
        }

        Collections.sort(list, new Comparator<int[]>() {
            @Override
            public int compare (int[] i1, int[] i2) {
                return i1[0] - i2[0];
            }
        });

        for(int i = 0; i < list.size(); i++) {
            int[] temp = list.get(i);
            if(temp[0] >= target[1]) {
                if(i == 0) {
                    return true;
                }
                int[] prev = list.get(i-1);
                if(target[0] >= prev[1]) {
                    return true;
                } else {
                    return false;
                }
            }
        }

        int[] last = list.get(list.size()-1);
        if(target[0] >= last[1]) {
            return true;
        }
        return false;
    }

    static List<int[]> available(int[][] schedules) {
        List<int[]> list = new ArrayList<int[]>();
        List<int[]> res = new ArrayList<int[]>();

        for(int[] schedule: schedules) {
            list.add(schedule);
        }

        Collections.sort(list, new Comparator<int[]>() {
            @Override
            public int compare (int[] i1, int[] i2) {
                return i1[0] - i2[0];
            }
        });

        for(int i = 1; i < list.size(); i++) {
            int[] cur = list.get(i);
            int[] prev = list.get(i-1);
            if(prev[1] < cur[0]) {
                res.add(new int[]{prev[1], cur[0]});
            }
        }

        int[] first = list.get(0);
        if(first[0] > 0) {
            res.add(0, new int[]{0, first[0]});
        }

        printListArray("available: ", res);
        return res;
    }

    static void printArray(String s, int[] array) {
        System.out.println(s);
        for(int i: array){
            System.out.print(i + " ");
        }
        System.out.println();
    }

    static void printSet(String s, Set<String> set) {
        System.out.println(s);
        for(String i: set){
            System.out.println(i + " ");
        }
        System.out.println();
    }

    static void printList(String s, List<String> list) {
        System.out.println(s);
        for(String i: list){
            System.out.println(i + " --> ");
        }
        System.out.println();
    }

    static void printListArray(String s, List<int[]> list) {
        System.out.println(s);
        for(int[] i: list){
            printArray("",i);
        }
        System.out.println();
    }

    static void printListInt(String s, List<Integer> list) {
        System.out.println(s);
        for(Integer i: list){
            System.out.println(i + " --> ");
        }
        System.out.println();
    }

    static void printMap(String s, Map<String, List<Integer>> map) {
        System.out.println(s);
        for(String ss: map.keySet()){
            printListInt(ss, map.get(ss));
        }
        System.out.println();
    }

    // Driver Code
    public static void main(String args[])
    {
        int[][] schedules = new int[][] {{1300,1500}, {930,1200}, {830,845}};
        int[] target1 = new int[] {820,830};
        int[] target2 = new int[] {1450,1500};
        System.out.println(rooms(schedules, target1));
        System.out.println(rooms(schedules, target2));
        available(schedules);
    }
}

