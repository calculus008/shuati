package Interviews.DoorDash;

import java.util.*;

class File_Dependency_On_Site {
    private static Map<Character, List<Character>> graph;

    public static List<Character> getPath(char[][] dependency) {
        List<Character> res = new ArrayList<>();
        if (dependency == null || dependency.length == 0) return res;

        graph = new HashMap<>();
        Map<Character, Integer> indegree = new HashMap<>();
        Set<Character> set = new HashSet<>();

        for (char[] d : dependency) {
            char key = d[1];
            char val = d[0];

            if (!graph.containsKey(key)) {
                graph.put(key, new ArrayList<>());
            }
            graph.get(key).add(val);

            indegree.put(val, indegree.getOrDefault(val, 0) + 1);
            set.add(key);
            set.add(val);
        }

        if (hasCycle(set)) {
            throw new RuntimeException("cycle detected");
        }

        Queue<Character> q = new LinkedList<>();
        for (char key : indegree.keySet()) {
            if (indegree.get(key) == 0) {
                q.offer(key);
            }
        }

        while (!q.isEmpty()) {
            char cur = q.poll();
            res.add(0, cur);

            if (!graph.containsKey(cur)) continue;

            for (char child : graph.get(cur)) {
                indegree.put(child, indegree.get(child) - 1);
                if (indegree.get(child) == 0) {
                    q.offer(child);
                }
            }
        }

        return res.size() == set.size() ? res : new ArrayList<>();
    }

    public static boolean hasCycle(Set<Character> files) {
        int n = files.size();

        Map<Character, Boolean> visited = new HashMap<>();
        Map<Character, Boolean> visiting = new HashMap<>();

        for (char file : files) {
            visited.put(file, false);
            visiting.put(file, false);
        }

        for (char file : files) {
            if (dfs(file, visited, visiting)) {
                return true;
            }
        }

        return false;

    }

    public static boolean dfs(char cur, Map<Character, Boolean> visited, Map<Character, Boolean> visiting) {
        if (visiting.get(cur)) {
            return true;
        }

        if (visited.get(cur)) {
            return false;
        }

        visiting.put(cur, true);

        // if (!graph.containsKey(cur)) {
        //   return false;
        // }

        for(char c : graph.get(cur)) {
            if (dfs(c, visited, visiting)) {
                return true;
            }
        }

        visited.put(cur, true);
        visiting.put(cur, false);

        return false;
    }

    public static void main(String[] args) {
        char[][] input = {{'b', 'a'},  {'c', 'a'}, {'e', 'b'}, {'e', 'c'}, {'f', 'e'}};

        List<Character> res = getPath(input);

        System.out.println(Arrays.toString(res.toArray()));
    }
}

