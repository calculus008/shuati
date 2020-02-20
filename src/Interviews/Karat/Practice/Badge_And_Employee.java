package Interviews.Karat.Practice;

import java.util.*;

class Badge_And_Employee {
    public static void getGroups(String[][] records) {
        Map<String, Integer> map = new HashMap<>();
        Set<String> enterWOBadge = new HashSet<>();
        Set<String> exitWOBadge = new HashSet<>();

        for (String[] r : records) {
            String name = r[0];
            String action = r[1];

            Integer prev = map.get(name);

            /**
             * First if based on event type (enter or exit)
             */
            if (action.equals("enter")) {
                /**
                 * !!!
                 * Must check if prev is null
                 */
                if (prev != null && prev == 1) {
                    exitWOBadge.add(name);
                }
                map.put(name, 1);
            } else {
                if (prev == null || prev == 0) {
                    enterWOBadge.add(name);
                }
                map.put(name, 0);
            }
        }

        /**
         * After a whole day, we know everyone left building,
         * if there's one with last activity remains as "enter",
         * he must have left without using badge.
         */
        for (String name : map.keySet()) {
            if (map.get(name) > 0) {
                exitWOBadge.add(name);
            }
        }

        printSet("===Enter without badge===", enterWOBadge);
        printSet("===Exit without badge===", exitWOBadge);
    }

    public static Map<String, List<Integer>> security(String[][] records) {
        Map<String, List<Integer>> map = new HashMap<>();
        for (String[] r : records) {
            String name = r[0];
            int time = Integer.valueOf(r[1]);

            if (!map.containsKey(name)) {
                map.put(name, new ArrayList<>());
            }
            map.get(name).add(time);
        }

        Map<String, List<Integer>> res = new HashMap<>();
        for (String name : map.keySet()) {
            List<Integer> cur = map.get(name);

            if (cur.size() >= 3) {
                Collections.sort(cur);
                for (int i = 0; i < cur.size(); i++) {
                    int idx = oneHour(cur, i);
                    if (idx - i + 1 >= 3) {
                        res.put(name, new ArrayList<>());

                        for (int j = i; j <= idx; j++) {
                            res.get(name).add(cur.get(j));
                        }

                        break;
                    }
                }
            }

        }

        return res;
    }

    private static int oneHour(List<Integer> list, int start) {
        int i = 0;
        int maxTime = list.get(start) + 100;
        while (i < list.size() && list.get(i) <= maxTime) {
            i++;
        }
        i--;

        return i;
    }

    public static void maxGroup(String[] records) {
        Map<String, List<int[]>> map = new HashMap<>();
        for (String r : records) {
            String[] parts = r.split(",");
            String name = parts[0];
            int start = Integer.parseInt(parts[1]);
            int end = Integer.parseInt(parts[2]);

            if (!map.containsKey(name)) {
                map.put(name, new ArrayList<>());
            }
            map.get(name).add(new int[]{start, end});
        }

        List<String> names = new ArrayList<>();
        List<List<int[]>> lists = new ArrayList<>();
        for (String key : map.keySet()) {
            names.add(key);
            lists.add(map.get(key));
        }

        List<int[]> maxInterval = new ArrayList<>();
        Set<String> maxGroup = new HashSet<>();

        for (int i = 0; i < names.size() - 1; i++) {
            List<int[]> cur = lists.get(i);
            String curName = names.get(i);
            Set<String> set = new HashSet<>();
            set.add(curName);

            System.out.println(curName);

            for (int j = i + 1; j < names.size(); j++) {
                List<int[]> overlaps = getOverlaps(cur, lists.get(j));

                if (overlaps.size() < 2) continue;

                cur = overlaps;
                set.add(names.get(j));
            }

            if (set.size() > 1 && set.size() > maxGroup.size()) {
                maxInterval = cur;
                maxGroup = set;
            }
        }

        printSet("===names===", maxGroup);
        printList(maxInterval);
    }

    private static List<int[]> getOverlaps(List<int[]> A, List<int[]> B) {
        List<int[]> res = new ArrayList<>();
        if (A == null || B == null || A.size() == 0 || B.size() == 0) return res;

        int m = A.size();
        int n = B.size();

        int p1 = 0, p2 = 0;

        while (p1 < m && p2 < n) {
            int maxStart = Math.max(A.get(p1)[0], B.get(p2)[0]);
            int minEnd = Math.min(A.get(p1)[1], B.get(p2)[1]);

            if (maxStart <= minEnd) {
                res.add(new int[]{maxStart, minEnd});
            }

            /**
             * !!!
             * outside the last if logic
             * !!!
             */
            if (A.get(p1)[1] == minEnd) {
                p1++;
            } else {
                p2++;
            }
        }

        return res;
    }

    private static void printList(List<int[]> list) {
        for (int[] elem : list) {
            System.out.println(Arrays.toString(elem));
        }
    }

    private static void printSet(String title, Set<String> set) {
        System.out.println(title);
        System.out.println(set.toString());
    }

    private static void printRes(String title, Map<String, List<Integer>> map) {
        System.out.println(title);
        for (String key : map.keySet()) {
            System.out.println(key + "->" + Arrays.toString(map.get(key).toArray()));
        }
    }

    public static void main(String[] args) {
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
         * enterWOBadge
         * Martha
         *
         * exitWOBadge
         * Curtis
         * Paul
         */
        getGroups(records);

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

        /**
         * ==security===
         * John->[830, 835, 855, 915, 930]
         * Paul->[1315, 1355, 1405]
         */
        printRes("==security===", security(records2));

        String[] record3 = {
                "John,800,900",
                "Kevin,830,1100",
                "John,1000,1030",
                "Roger,1030,1200",
                "Novak,830,900",
                "Novak,1000,1030"
        };

        maxGroup(record3);
    }
}
