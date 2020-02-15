package Interviews.Karat;

import java.util.*;

public class Friend_Circle_Practice {
    public static Map<Integer, Set<Integer>> getFriendsLists(String[] employee, String[] friendship) {
        Map<Integer, Set<Integer>> adj = new HashMap<>();

        for (String e : employee) {
            String[] s = e.split(",");
            int id = Integer.parseInt(s[0]);
            adj.put(id, new HashSet<>());
        }

        for (String f : friendship) {
            String[] s = f.split(",");
            int p1 = Integer.parseInt(s[0]);
            int p2 = Integer.parseInt(s[1]);

            /**
             * Undirected Graph
             */
            adj.get(p1).add(p2);
            adj.get(p2).add(p1);
        }

        return adj;
    }

    public static Map<String, Integer> getDepartment(String[] employee, String[] friendship) {
        Map<String, Integer> res = new HashMap<>();

        /**
         * department name -> set of employees in this department
         */
        Map<String, Set<Integer>> dept = new HashMap<>();
        for (String e : employee)  {
            String[] parts = e.split(",");
            String depName = parts[2];
            int id = Integer.parseInt(parts[0]);

            if (!dept.containsKey(depName)) {
                dept.put(depName, new HashSet<>());
            }

            dept.get(depName).add(id);
        }

        Map<Integer, Set<Integer>> map = getFriendsLists(employee, friendship);

        for (String e : employee) {
            String[] parts = e.split(",");
            String depName = parts[2];
            int id = Integer.parseInt(parts[0]);

            Set<Integer> friends = map.get(id);
            Set<Integer> depSet = dept.get(depName);

            for (int f : friends) {
                if (!depSet.contains(f)) {//friend not in the same department
                    res.put(depName, res.getOrDefault(depName, 0) + 1);
                    break;
                }
            }
        }

        return res;
    }

    /**
     * Start from any id, BFS, see if visited set contains all employees
     */
    public static boolean isInOneCircle(String[] employee, String[] friendship) {
        Map<Integer, Set<Integer>> map = getFriendsLists(employee, friendship);

        Set<Integer> set = new HashSet<>();
        Queue<Integer> q = new LinkedList<>();

        String[] parts = friendship[0].split(",");
        int id = Integer.parseInt(parts[0]);

        q.offer(id);

        while (!q.isEmpty()) {
            int elem = q.poll();
            set.add(elem);
            System.out.println(elem);

            for (int f : map.get(elem)) {
                if (set.contains(f)) continue;

                q.offer(f);
            }
        }

        return set.size() == employee.length;
    }

    private static void printRes1(Map<Integer, Set<Integer>> map) {
        for (int key : map.keySet()) {
            System.out.println(key + " ->" + map.get(key));
        }
    }

    private static void printRes2(Map<String, Integer> map) {
        for (String key : map.keySet()) {
            System.out.println(key + " ->" + map.get(key));
        }
    }

    public static void main(String[] args) {
        String[] employeesInput = {
                "1,Richard,Engineering",
                "2,Erlich,HR",
                "3,Monica,Business",
                "4,Dinesh,Engineering",
                "6,Carla,Engineering",
                "9,Laurie,Directors"
        };

        String[] employeesInput1 = {
                "1,Richard,Engineering",
                "2,Erlich,HR",
                "3,Monica,Business",
                "4,Dinesh,Engineering",
                "6,Carla,Engineering"
        };

        String[] friendshipsInput = {
                "1,2",
                "1,3",
                "1,6",
                "2,4"
        };


        printRes1(getFriendsLists(employeesInput, friendshipsInput));
        printRes2(getDepartment(employeesInput, friendshipsInput));

        System.out.println(isInOneCircle(employeesInput1, friendshipsInput));
    }
}
