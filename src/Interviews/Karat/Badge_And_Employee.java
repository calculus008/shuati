package Interviews.Karat;

import java.util.*;

public class Badge_And_Employee {
    /**
     * We are working on a security system for a badged-access room in our company's building.
     * Given an ordered list of employees who used their badge to enter or exit the room, write
     * a function that returns two collections:
     *
     * 1. All employees who didn't use their badge while exiting the room
     * – they recorded an enter without a matching exit.
     * 2. All employees who didn't use their badge while entering the room  
     * – they recorded an exit without a matching enter.
     *
     * We want to find employees who badged into our secured room unusually often.
     * We have an unordered list of names and access times over a single day. Access
     * times are given as three or four-digit numbers using 24-hour time, such as "800"
     * or "2250".
     *
     * Write a function that finds anyone who badged into the room 3 or more times in a
     * 1-hour period, and returns each time that they badged in during that period.
     * (If there are multiple 1-hour periods where this was true, just return the first one.)
     *
     *
     * NEW!!!
     * Given a list of people who enter and exit, find the maximal group of people who were inside
     * together at least 2 times and output the group and the times they were in together.
     *
     * All enters/exits are valid.
     *
     * 第三道大概是同时在屋里的最大的group，而且要求group出现两会。输出group而且group在一起的那段时间.
     */

    static void getGroup(String[][] records) {
        Map<String, Integer> map = new HashMap<>();
        Set<String> exitWithoutBadge = new HashSet<>();
        Set<String> enterWithoutBadge = new HashSet<>();

        for (String[] record : records) {
            Integer prev = map.get(record[0]);
            if (record[1].equals("enter")) {
                if (prev != null && prev == 1) {
                    exitWithoutBadge.add(record[0]);
                }
                prev = 1;
            } else {
                if (prev == null || prev == 0) {
                    enterWithoutBadge.add(record[0]);
                }
                prev = 0;
            }
            map.put(record[0], prev);
        }

        for (String person : map.keySet()) {
            if (map.get(person) > 0) {
                exitWithoutBadge.add(person);
            }
        }

        printSet("enter without badge ", enterWithoutBadge);
        printSet("exit without badge ", exitWithoutBadge);
    }

    static Map<String, List<Integer>> security(String[][] records) {
        Map<String, List<Integer>> map = new HashMap<>();
        for (String[] record : records) {
            if (!map.containsKey(record[0])) {
                map.put(record[0], new ArrayList<>());
            }
            map.get(record[0]).add(Integer.parseInt(record[1]));

        }
        Map<String, List<Integer>> res = new HashMap<>();
        for (String person : map.keySet()) {
            if (map.get(person).size() >= 3) {
                List<Integer> cur = map.get(person);
                Collections.sort(cur);
                for (int i = 0; i < cur.size(); i++) {
                    int index = oneHour(cur, i);
                    if (index - i >= 3) {
                        List<Integer> temp = new ArrayList<>();
                        while (i < index) {
                            temp.add(cur.get(i));
                            i++;
                        }
                        res.put(person, temp);
                        break;
                    }
                }
            }
        }
        printMap("secure ", res);
        return res;
    }

    static int oneHour(List<Integer> list, int startIndex) {
        int endVal = list.get(startIndex) + 100;
        int endPos = startIndex;
        while (endPos < list.size()) {
            if (list.get(endPos) <= endVal) {
                endPos++;
            } else {
                break;
            }
        }
        return endPos;
    }

    static void printSet(String s, Set<String> set) {
        System.out.println(s);
        for (String i : set) {
            System.out.println(i + " ");
        }
        System.out.println();
    }

    static void printList(String s, List<String> list) {
        System.out.println(s);
        for (String i : list) {
            System.out.println(i + " --> ");
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

    static void printMap(String s, Map<String, List<Integer>> map) {
        System.out.println(s);
        for (String ss : map.keySet()) {
            printListInt(ss, map.get(ss));
        }
        System.out.println();
    }

    // Driver Code
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
        getGroup(records);
        security(records2);
    }
}

