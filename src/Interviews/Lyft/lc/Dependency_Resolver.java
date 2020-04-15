package Interviews.Lyft.lc;

import java.util.*;

public class Dependency_Resolver {
    /**
     * DoorDash
     *
     * dependency resolver
     *
     * input:
     * libraries = [
     *     'z',
     *     'a',
     *     'b',
     *     'c',
     *     'd'
     * ]
     *
     * dependencies = [
     * 'a->b,d',
     * 'b->c,f',
     * 'c->g',
     * 'd->e',
     * 'f->g'
     * ]
     *
     *       a
     *      / |
     *     b   d
     *    / |  |
     *   c  f   e
     *   /
     *  g
     *
     * (possible) output:
     * ['z', 'g', 'f', 'c', 'b', 'e', 'd', 'a']
     *
     * explanation:
     * 题目是问, 要安装library列表里面的所有library. 然后这些library对应的依赖关系在dependency列表里.
     * 箭头前的依赖箭头后的(可能依赖多个). 这些依赖可能还存在更深的依赖关系. 必须装了对应的依赖才能装对应
     * 的library. 无关的依赖的安装顺序没有要求. 但是要按顺序装library里面的.所以, 以上述输入为例.
     * 一来先装z, 发现z无依赖, 直接安装. 然后安装a, 发现a依赖于b和d, 因此要先安装b和d. 但是发现b和d又各有依赖,
     * 因此去安装对应的依赖, so on.
     * ```
     *
     * (dependency graph然后print出来假如要compile A，所有其他文件要compile的顺序。)
     *
     * 拓展： 如何只 print circular dependency.
     *
     * https://stackoverflow.com/questions/14458655/topological-sort-to-find-dependencies-of-a-specific-node
     *
     * 这题实际上是考察BFS, DFS.
     *
     * BFS print dependency level by level
     *
     * Then maintain parent map, once cycle is detected, DFS to backtrack circular path.
     *
     */

    /**
     * Solution with topological sort
     */
    public static List<String> resolveDependence_topological(Map<String, List<String>> dependency, Set<String> files) {
        Map<String, Set<String>> graph = new HashMap<>();
        Map<String, Integer> indegree = new HashMap<>();

        List<String> res = new ArrayList<>();

        //init indegree
        for (String file : files) {
            indegree.put(file, 0);
        }

        //convert input into adjacent list, key is file, value is set of files that depend on key
        for (Map.Entry<String, List<String>> entry : dependency.entrySet()) {
            String key = entry.getKey();
            List<String> list  = entry.getValue();

            for (String s : list) {
                graph.putIfAbsent(s, new HashSet<>());
                graph.get(s).add(key);
            }
            indegree.put(key, indegree.getOrDefault(key, 0) + list.size());//assume no duplicate in list
        }

        Queue<String> q = new LinkedList<>();
        for (String key : indegree.keySet()) {
            if (indegree.get(key) == 0) {
                q.offer(key);
            }
        }

        while (!q.isEmpty()) {
            String cur = q.poll();
            /**
             * add to res when poll
             */
            res.add(cur);

            if (!graph.containsKey(cur)) continue;

            for (String s : graph.get(cur)) {
                indegree.put(s, indegree.get(s) - 1);
                if (indegree.get(s) == 0) {
                    q.offer(s);
                }
            }
        }

        return res.size() == files.size() ? res : new ArrayList<>();
    }

    public static List<String> resolveDependence(String A, Map<String, List<String>> adjlist) {
        List<String> res = new ArrayList<>();

        if (!adjlist.containsKey(A)) return res;

        bfs(A, adjlist, res);

        return res;
    }

    private static void bfs(String A, Map<String, List<String>> adjlist, List<String> res) {
        Queue<String> q = new LinkedList<>();
        q.offer(A);

        Set<String> all = new LinkedHashSet<>();

        /**
         * Used in backTrace() to build circular path
         */
        Map<String, List<String>> parent = new HashMap<>();

        while (!q.isEmpty()) {
            int size = q.size();

//            Set<String> set = new HashSet<>();
            for (int i = 0; i < size; i++) {
                String cur = q.poll();
//                set.add(cur);

                all.add(cur);

                List<String> next = adjlist.get(cur);
                if (next == null) {
                    continue;
                }

                for (String s : next) {
                    if (all.contains(s)) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(cur).append(" ");
                        backTrace(cur, s, parent, sb);
                        throw new RuntimeException("Circular Dependency, s="+s);
                    }

                    parent.putIfAbsent(s, new LinkedList<>());
                    parent.get(s).add(cur);

                    q.offer(s);
                }
            }
        }


        Iterator<String> it = all.iterator();
        while (it.hasNext()) {
            res.add(0, it.next());
        }
    }

    public static List<String> resolveDependence1(String A, List<List<String>> dependencies) {
        List<String> res = new ArrayList<>();

        Map<String, List<String>> adjlist = buildGrpah(dependencies);

        if (!adjlist.containsKey(A)) return res;

        bfs(A, adjlist, res);

        return res;
    }

    private static Map<String, List<String>> buildGrpah(List<List<String>> dependencies) {
        Map<String, List<String>> adjList = new HashMap<>();
        if (dependencies == null || dependencies.size() == 0) return adjList;

        for (List<String> d : dependencies) {
            String parent = d.get(0);
            String child = d.get(1);

            if (!adjList.containsKey(parent)) {
                adjList.put(parent, new ArrayList<>());
            }
            adjList.get(parent).add(child);
        }

        return adjList;
    }

    /**
     * Follow up
     * Print circular dependency
     *
     * LE_261_Graph_Valid_Tree is for checking cycle in undirected graph,
     * here we have a directed graph.
     *
     * https://stackoverflow.com/questions/8922060/how-to-trace-the-path-in-a-breadth-first-search
     */

    private static void backTrace(String start, String end, Map<String, List<String>> parent, StringBuilder sb) {
        System.out.println("start : " + start);
        if (start.equals(end)) {
//            sb.append(start);
            System.out.println(sb.reverse().toString().trim());
            return;
        }

        if (!parent.containsKey(start)) return;

        int n = sb.length();

        List<String> l = parent.get(start);
        for (String s : l) {
            sb.append(s).append(" ");
            backTrace(s, end, parent, sb);
            sb.setLength(n);
        }
    }

    public static void main(String[] args) {
        /**
         *     a
         *    / \
         *   b   d
         *  / \   \
         * c   f  e
         *  \ /
         *  g
         */
        Map<String, List<String>> map = new HashMap<>();

        List<String> a = new ArrayList<>();
        a.add("b");
        a.add("d");
        map.put("a", a);

        List<String> b = new ArrayList<>();
        b.add("c");
        b.add("f");
        map.put("b", b);

        List<String> c = new ArrayList<>();
        c.add("g");
        map.put("c", c);

        List<String> d = new ArrayList<>();
        d.add("e");
        map.put("d", d);

        List<String> f = new ArrayList<>();
        f.add("g");
//        f.add("a");
        map.put("f", f);

//        List<String> res1 = resolveDependence("a", map);
//        System.out.println(Arrays.toString(res1.toArray()));
//
//        String[] files = {"a","b","c","d","e","f","g", "z"};
//        Set<String> set = new HashSet<>(Arrays.asList(files));
//        List<String> res2 = resolveDependence_topological(map, set);
//        System.out.println(Arrays.toString(res2.toArray()));


        List<List<String>> dependencies = new ArrayList<>();
        List<String> l1 = new ArrayList<>();
        l1.add("a");
        l1.add("b");
        List<String> l2 = new ArrayList<>();
        l2.add("a");
        l2.add("d");
        List<String> l3 = new ArrayList<>();
        l3.add("b");
        l3.add("c");
        List<String> l4 = new ArrayList<>();
        l4.add("b");
        l4.add("f");
        List<String> l5 = new ArrayList<>();
        l5.add("c");
        l5.add("g");
        List<String> l6 = new ArrayList<>();
        l6.add("f");
        l6.add("g");
        List<String> l7 = new ArrayList<>();
        l7.add("d");
        l7.add("e");
        List<String> l8 = new ArrayList<>();
        l8.add("y");
        l8.add("z");

        dependencies.add(l1);
        dependencies.add(l2);
        dependencies.add(l3);
        dependencies.add(l4);
        dependencies.add(l5);
        dependencies.add(l6);
        dependencies.add(l7);
        dependencies.add(l8);

        System.out.println(Arrays.toString(resolveDependence1("a", dependencies).toArray()));
    }
}
