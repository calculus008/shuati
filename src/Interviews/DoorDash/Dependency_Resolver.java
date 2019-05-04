package Interviews.DoorDash;

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
     *
     *     b   d
     *
     *   c  f   e
     *
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
     */

    public static List<String> resolveDependence(String A, Map<String, List<String>> adjlist) {
        List<String> res = new ArrayList<>();

        if (!adjlist.containsKey(A)) return res;

        bfs(A, adjlist, res);

        return res;
    }

    private static void bfs(String A, Map<String, List<String>> adjlist, List<String> res) {
        Queue<String> q = new LinkedList<>();
        q.offer(A);

        Set<String> all = new HashSet<>();

        while (!q.isEmpty()) {
            int size = q.size();

            Set<String> set = new HashSet<>();
            for (int i = 0; i < size; i++) {
                String cur = q.poll();
                set.add(cur);

                List<String> next = adjlist.get(cur);
                if (next == null) {
                    continue;
                }

                for (String s : next) {
                    q.offer(s);
                }
            }

            for (String elem : set) {
                res.add(0, elem);

                if (!all.add(elem)) {
                    throw new RuntimeException("Circular Dependency");
                }
            }
        }
    }


    /**
     * Follow up
     * Print circular dependency
     *
     * LE_261_Graph_Valid_Tree
     */

    public static void main(String[] args) {
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
        f.add("a");
        map.put("f", f);

        List<String> res = resolveDependence("a", map);
        System.out.println(Arrays.toString(res.toArray()));

    }
}
