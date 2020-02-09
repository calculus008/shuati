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
     * We need to now the last event for a person, therefore, we can't use Set
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
    }
}
