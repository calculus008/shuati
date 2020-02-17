package Interviews.Karat;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Friend_Circle {
    /**
     * 1st Question: 输出所有的employee的friendlist -> 就是用一个map存起来然后打印就好了
     * （这个是无向图，e.g: 1和2是朋友，2的列表里也要有1）
     * 2nd Question: 输出每个department里有多少人的朋友是其他部门的
     * ->也就是遍历一遍就好了
     * 3rd Question: 输出是否所有employee都在一个社交圈
     * -> 我当时想的就是随便找一个点，用DFS遍历一遍，如果所有点都被遍历到就return true，不然就是false.
     *
     * 有一个employList
     *     String[] employeesInput = {
     *       "1,Richard,Engineering",
     *       "2,Erlich,HR",
     *       "3,Monica,Business",
     *       "4,Dinesh,Engineering",
     *       "6,Carla,Engineering",
     *       "9,Laurie,Directors"
     *     };
     * 一个friendshipList，friend关系是双向的
     *     String[] friendshipsInput = {
     *       "1,2",
     *       "1,3",
     *       "1,6",
     *       "2,4"
     *     };
     * 1. 写一个函数返回每个人的friend的adjacency list
     * 比如这个例子里返回
     * 1：2 3 6
     * 2：1 4
     * 3：1
     * 4：2
     * 6：1
     * 9：
     *
     * ###
     * 题目是原先OA的拓展，employee的问题。给两个list，第一个list存了每个人的id，名字，公司名称。
     * 第二个list存friendship tuples。
     *
     * 然后有两问，第一问是返回每个人的friendlist。
     * 第二问是返回每个公司有多少员工，以及这些员工中有多少有外公司的朋友。
     * 然后两问都要给时间空间复杂度。图的问题，第二问时间复杂度是O(E+V)
     */

    public static Map<Integer, Set<Integer>> generateMap(String[] employees, String[] friendships) {
        Map<Integer, Set<Integer>> map = new HashMap<>();

        for (String employee : employees) {
            String[] parts = employee.split(",");
            int employeeId = Integer.parseInt(parts[0]);
            map.put(employeeId, new HashSet<>());
        }

        for (String friendship : friendships) {
            String[] parts = friendship.split(",");
            int id1 = Integer.parseInt(parts[0]);
            int id2 = Integer.parseInt(parts[1]);
            map.get(id1).add(id2);
            map.get(id2).add(id1);
        }

        return map;
    }

    public static Map<String, int[]> getDepartmentStat(String[] employees, String[] friendships) {
        Map<String, int[]> res = new HashMap<>();
        Map<Integer, Set<Integer>> friendsMap = generateMap(employees, friendships);

        Map<String, Set<Integer>> departmentsMap = new HashMap<>();
        for (String employee : employees) {
            String[] parts = employee.split(",");
            int employeeId = Integer.parseInt(parts[0]);

            if (!departmentsMap.containsKey(parts[2])) {
                departmentsMap.put(parts[2], new HashSet<>());
                res.put(parts[2], new int[]{0, 0});
            }

            departmentsMap.get(parts[2]).add(employeeId);
            res.get(parts[2])[0]++;
        }

        for (String employee : employees) {
            String[] parts = employee.split(",");
            int employeeId = Integer.parseInt(parts[0]);
            String department = parts[2];
            Set<Integer> employeeSet = departmentsMap.get(department);
            Set<Integer> friendsList = friendsMap.get(employeeId);

            for (int friend : friendsList) {
                if (!employeeSet.contains(friend)) {
                    res.get(department)[1]++;
                    break;
                }
            }
        }

        return res;
    }
}
