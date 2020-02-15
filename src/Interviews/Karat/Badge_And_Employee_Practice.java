package Interviews.Karat;

import java.util.*;

public class Badge_And_Employee_Practice {

    /**
     * 要分情况讨论
     *
     * ExitWOBadge:
     * #A.Has previous enter record, now entering
     *
     * EnterWOBadge:
     * #B-1.Has previous exit record, now exiting
     * #B-2.Has NO previous record, now exiting
     *
     * We need to use HashMap : name -> previous event
     *
     * We need to know the last event for a person, therefore, we can't use Set
     * and have to use Map
     *
     * !!!!!
     * Need to check the map after it's created, find #A
     *
     */
    public static  void getGroups(String[][] records) {
        if (records == null || records.length == 0) return;

        Set<String> enterWOBadge = new HashSet<>();
        Set<String> exitWOBadge = new HashSet<>();

        Map<String, Integer> map = new HashMap<>();

        for (String[] r : records) {
            if (r == null) continue;

            String name = r[0];
            String event = r[1];

            Integer pre = map.get(name);
            if (event.equals("enter")) {
                if (pre != null && pre == 1) {//#A
                    exitWOBadge.add(name);
                }
                map.put(name, 1);
            } else if (event.equals("exit")) {
                if (pre == null || pre == 0) {//#B-1 and #B-2
                    enterWOBadge.add(name);
                }
                map.put(name, 0);
            }
        }

        for (String name : map.keySet()) {
            if (map.get(name) == 1) {
                exitWOBadge.add(name);//#A
            }
        }

        printSet("enterWOBadge", enterWOBadge);
        printSet("exitWOBadge", exitWOBadge);
    }

    public static Map<String, List<Integer>> security(String[][] records) {
        Map<String, List<Integer>> res = new HashMap<>();
        if (records == null || records.length == 0) return res;

        /**
         * name -> list of times that this person get into rooms
         */
        Map<String, List<Integer>> map = new HashMap<>();

        for (String[] r : records) {
            String name = r[0];
            String time = r[1];

            if (!map.containsKey(name)) {
                map.put(name, new ArrayList<>());
            }
            map.get(name).add(Integer.parseInt(time));
        }

        for (String name : map.keySet()) {
            List<Integer> l = map.get(name);

            if (l.size() >= 3) {
                Collections.sort(l);
//                printList(name, l);

                for (int i = 0; i < l.size(); i++) {
                    int idx = getLen(l, i);
                    if (idx - i + 1 >= 3) {
                        res.put(name, new ArrayList<>());;
                        for (int j = i; j <= idx; j++) {
                            res.get(name).add(l.get(j));
                        }
                        /**
                         * (If there are multiple 1-hour periods where this was true, just return the first one.)
                         */
                        break;
                    }
                }
            }
        }

        return res;
    }

    public static int getLen(List<Integer> l, int start) {
        int time = l.get(start);
        int max = time + 100;

        int i = start;
        while (i < l.size() && l.get(i) <= max) {
            i++;
        }

        i--;

        return i;
    }

    public static void maxGroupTogether(String[] records) {
        Map<String, List<int[]>> map = new HashMap<>();
        for (String r : records) {
            String[] parts = r.split(",");
            String name = parts[0];

            int enter = Integer.parseInt(parts[1]);
            int exit = Integer.parseInt(parts[2]);

            if (!map.containsKey(name)) {
                map.put(name, new ArrayList<>());
            }
            map.get(name).add(new int[]{enter, exit});
        }

        List<List<int[]>> lists = new ArrayList<>();
        List<String> names = new ArrayList<>();

        for (String key : map.keySet()) {
            for(int[] e : map.get(key)) {
                System.out.println(Arrays.toString(e));
            }
        }

        for (String name : map.keySet()) {
            lists.add(map.get(name));
            names.add(name);
        }

//        System.out.println(Arrays.toString(names.toArray()));

        List<int[]> maxIntervals = new ArrayList<>();
        Set<String> maxNames = new HashSet<>();

        for (int i = 0; i < lists.size() - 1; i++) {
            List<int[]> cur = lists.get(i);
            String curName = names.get(i);
            Set<String> set = new HashSet<>();
            set.add(curName);

            for (int j = i + 1; j < lists.size(); j++) {
                if (i == j) continue;

                List<int[]> overlaps = intervalIntersection(cur, lists.get(j));

                if (overlaps.size() < 2) continue;

//                System.out.println(names.get(i) + "," + names.get(j));
                cur = overlaps;
                set.add(names.get(j));
            }

            if (set.size() > 1 && set.size() > maxNames.size()) {
                maxNames = set;
                maxIntervals = cur;
            }
        }

        printSet("name : ", maxNames);
        print1(maxIntervals);
    }

    /**
     * 986. Interval List Intersections
     */
    public static List<int[]> intervalIntersection(List<int[]> A, List<int[]> B) {
        if (A == null || B == null || A.size() == 0 || B.size() == 0) return new ArrayList<>();

        int m = A.size();
        int n = B.size();

        int p1 = 0, p2 = 0;
        List<int[]> res = new ArrayList<>();

        while (p1 < m && p2 < n) {
            int startMax = Math.max(A.get(p1)[0], B.get(p2)[0]);
            int endMin = Math.min(A.get(p1)[1], B.get(p2)[1]);

            if (startMax <= endMin) {
                res.add(new int[]{startMax, endMin});
            }

            if (A.get(p1)[1] == endMin) {
                p1++;
            } else {
                p2++;
            }
        }

        return res;
    }

    static void printSet(String s, Set<String> set) {
        System.out.println(s);
        for (String i : set) {
            System.out.println(i + " ");
        }
        System.out.println();
    }

    static void printMap(String s, Map<String, List<Integer>> map) {
        System.out.println(s);
        for (String ss : map.keySet()) {
            printListInt(ss, map.get(ss));
        }
        System.out.println();
    }

    static void printListInt(String s, List<Integer> list) {
        System.out.println(s);
        for (Integer i : list) {
            System.out.println(i + " --> ");
        }
        System.out.println();
    }

    static void printList(String s, List<Integer> list) {
        System.out.println(s);
        for (int i : list) {
            System.out.println(i + " --> ");
        }
        System.out.println();
    }

    static void print1(List<int[]> list) {
        for (int[] e : list) {
            System.out.println(Arrays.toString(e));
        }
    }

    public static void main(String args[]) {
        String[][] records = new String[][]{
                {"Martha", "exit"},
                {"Paul", "enter"},
                {"Martha", "enter"},
                {"Martha", "exit"},
                {"Jennifer", "enter"},
                {"Paul", "enter"},
                {"Curtis", "enter"},
                {"Paul", "exit"},
                {"Martha", "enter"},
                {"Martha", "exit"},
                {"Jennifer", "exit"},
        };

        /**
         * "We have an unordered list of names and access times over a single day."
         */
        String[][] records2 = new String[][]{
                {"Paul", "1355"},
                {"Jennifer", "1910"},
                {"John", "830"},
                {"Paul", "1315"},
                {"John", "835"},
                {"Paul", "1405"},
                {"Paul", "1630"},

                {"John", "855"},

                {"John", "915"},

                {"John", "930"},

                {"Jennifer", "1335"},

                {"Jennifer", "730"},

                {"John", "1630"},

        };
        getGroups(records);
        printMap("security", security(records2));
        /**
         * secure
         * John
         * 830 -->
         * 835 -->
         * 855 -->
         * 915 -->
         * 930 -->
         *
         * Paul
         * 1315 -->
         * 1355 -->
         * 1405 -->
         */


//        List<int[]> l1 = new ArrayList<>();
//        List<int[]> l2 = new ArrayList<>();
//
//        l1.add(new int[]{800, 900});
//        l1.add(new int[]{1000, 1100});
//        l2.add(new int[]{830, 1030});
//
//        print1(intervalIntersection(l1, l2));

        String[] record3 = {
                "John,800,900",
                "Kevin,830,1100",
                "John,1000,1030",
                "Roger,1030,1200",
                "Novak,830,900",
                "Novak,1000,1030"
        };

        maxGroupTogether(record3);
    }
}
