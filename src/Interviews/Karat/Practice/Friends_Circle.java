package Interviews.Karat.Practice;

import java.util.*;

class Friends_Circle {
    public static Map<Integer, Set<Integer>> getFriends(String[] employees, String[] friendship) {
        Map<Integer, Set<Integer>> map = new HashMap<>();
        if (employees == null || friendship == null) return map;

        for (String employee : employees) {
            String[] parts = employee.split(",");
            int id = Integer.parseInt(parts[0]);

            if (!map.containsKey(id)) {
                map.put(id, new HashSet<>());
            }
        }

        for (String f : friendship) {
            String[] parts = f.split(",");
            int p1 = Integer.parseInt(parts[0]);
            int p2 = Integer.parseInt(parts[1]);

            /**
             * !!!
             * undirected graph
             */
            map.get(p1).add(p2);
            map.get(p2).add(p1);
        }

        return map;
    }

    public static Map<String, Integer> getDepartmentNum(String[] employees, String[] friendship) {
        /**
         * Get friends adjacent list
         */
        Map<Integer, Set<Integer>> map = getFriends(employees, friendship);

        /**
         * map
         * deptName -> employees that in this department
         */
        Map<String, Set<Integer>> dept = new HashMap<>();
        for (String employee : employees) {
            String[] parts = employee.split(",");
            int id = Integer.parseInt(parts[0]);
            String deptName = parts[2];

            if (!dept.containsKey(deptName)) {
                dept.put(deptName, new HashSet<>());
            }

            dept.get(deptName).add(id);
        }

        Map<String, Integer> res = new HashMap<>();
        for (String employee : employees) {
            String[] parts = employee.split(",");
            int id = Integer.parseInt(parts[0]);
            String deptName = parts[2];

            Set<Integer> friends = map.get(id);
            Set<Integer> all = dept.get(deptName);

            for (int f : friends) {
                if (!all.contains(f)) {
                    res.put(deptName, res.getOrDefault(deptName, 0) + 1);
                    /**
                     * only count once!!!
                     */
                    break;
                }
            }
        }

        return res;
    }

    public static boolean isOneCircle(String[] employees, String[] friendship) {
        Map<Integer, Set<Integer>> friends = getFriends(employees, friendship);

        String[] parts = employees[0].split(",");
        int id = Integer.parseInt(parts[0]);

        Set<Integer> set = new HashSet<>();
        getAll(friends, set, id);

        return set.size() == friends.size();
    }

    public static void getAll(Map<Integer, Set<Integer>> friends, Set<Integer> set, int id) {
        if (!set.add(id)) return;

        if (!friends.containsKey(id) || friends.get(id).size() == 0) return;

        for (int f : friends.get(id)) {
            getAll(friends, set, f);
        }
    }



    public static void printRes1(String title, Map<Integer, Set<Integer>> map) {
        System.out.println(title);
        for (int key : map.keySet()) {
            System.out.println(key + "->" + map.get(key).toString());
        }
    }

    public static void printRes2(String title, Map<String, Integer> map) {
        System.out.println(title);
        for (String key : map.keySet()) {
            System.out.println(key + "->" + map.get(key));
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

        /**
         * 1 ->[2, 3, 6]
         * 2 ->[1, 4]
         * 3 ->[1]
         * 4 ->[2]
         * 6 ->[1]
         * 9 ->[]
         * Engineering ->2
         * HR ->1
         * Business ->1
         */

        printRes1("===Friends===", getFriends(employeesInput, friendshipsInput));

        printRes2("===dept===", getDepartmentNum(employeesInput, friendshipsInput));

        System.out.println(isOneCircle(employeesInput, friendshipsInput));

        System.out.println(isOneCircle(employeesInput1, friendshipsInput));
    }

}
