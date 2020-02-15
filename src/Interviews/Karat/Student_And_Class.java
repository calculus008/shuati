package Interviews.Karat;

import java.util.*;

public class Student_And_Class {
    /**
     * 第一问：
     * You are a developer for a university. Your current project is to develop a system for students
     * to find courses they share with friends. The university has a system for querying courses students
     * are enrolled in, returned as a list of (ID, course) pairs.
     *
     * Write a function that takes in a list of (student ID number, course name) pairs and returns,
     * for every pair of students, a list of all courses they share.
     *
     * Sample Input:
     *
     * student_course_pairs_1 = [
     *   ["58", "Software Design"],
     *   ["58", "Linear Algebra"],
     *   ["94", "Art History"],
     *   ["94", "Operating Systems"],
     *   ["17", "Software Design"],
     *   ["58", "Mechanics"],
     *   ["58", "Economics"],
     *   ["17", "Linear Algebra"],
     *   ["17", "Political Science"],
     *   ["94", "Economics"],
     *   ["25", "Economics"],
     * ]
     *
     * Sample Output (pseudocode, in any order):
     *
     * find_pairs(student_course_pairs_1) =>
     * {
     *   [58, 17]: ["Software Design", "Linear Algebra"]
     *   [58, 94]: ["Economics"]
     *   [58, 25]: ["Economics"]
     *   [94, 25]: ["Economics"]
     *   [17, 94]: []
     *   [17, 25]: []
     * }
     *
     * Additional test cases:
     *
     * Sample Input:
     *
     * student_course_pairs_2 = [
     *   ["42", "Software Design"],
     *   ["0", "Advanced Mechanics"],
     *   ["9", "Art History"],
     * ]
     *
     * Sample output:
     *
     * find_pairs(student_course_pairs_2) =>
     * {
     *   [0, 42]: []
     *   [0, 9]: []
     *   [9, 42]: []
     * }
     *
     * 第二问：
     * 告诉你a是b的先修课，b是c的先修课，问你mid course是啥。并且条件是，只有一种修下来课的顺序，这里是 a -> b ->c
     * 所以mid course就是b
     *
     * #
     * 1.一组 学生id，课程名的数组，需要输出任意两学生的共同课程。
     * 2.一组 课程名，课程名的数组，代表着pre课程，保证有一条线路可以修完，输出n/2位置的课程
     * 3.输入跟刚刚一样，每个课可能有多个pre和多个follow课程，输出所有n/2的课程
     *
     * 第一题花了我30分钟才写完...主要是一开始想用lst写，写到一半发现用set可以省更多时间，然后改着改着名字改乱了，各种syntax error，实际都是typo 太丢人了。
     * 第一题么哈希表，然后记一下所有学生id，然后就双循环intersect各自的Set就行了。
     * 然后第二题写了5分钟。哈希表+一个set，之后用一个循不停remove出现在第二个位置的课程这样就剩下来起头的课程了（反正都是n复杂度怕啥）。
     * 第三题他说没多少时间了你就讲讲思路吧，我瞎扯了一些，然后就结束了。
     *
     * #
     * 3. 第一题，一个list, [[A, math,....],[B, math, yuwen,...],[C, yuwen,tiyu...]],
     *   求每个pair 的student的相同课程，output一个list。我用hash map，然后set(A)&set(B)这样做的。
     *
     * 4. 第二题：是prerequisites course 和 course的题目，让你连成一串（assume是刚好连成一串的）
     *  ，类似leco里JFK airpot Itinerary那题，但没有告诉你出发点，要自己找， 然后返回最中间那个课。
     *
     * 5. 第三题： 第二题的follow up，不只能连成一串，可以连成好几串的，然后找每一串的中间那个课。
     *
     * 不难，每个题有三个case给你跑，不用考虑corner case。要做的快，我猜应该是有无穷无尽的题的，越做多越好，
     * 只不过我只做了三道，第三道刚写完初稿就说停了，我也不知道对错。。
     *
     * 第一题，比如a和b有一门math课一样，c和d有两门一样，分别是math和pe，a和e没有课一样，那么我们的输出是{(a,b):[math], (a,e):[], (c,d):[PE,Math]}
     *
     * 第二题：比如a是b的前置课，b是c的前置课，则连起来是abc，那么中间那么课就是b
     *
     *  感觉第一题只能双重循环，没简便方法了。第二题一串不一定只有三个课对吧？对 不一定 有很多
     *
     *
     *  #
     *  1. 给每个学生选的课，求每两个学生的课交集
     * 2.followup 给出先修课pair 自己需要理出链 找出n//2的课程
     * 和二要领利口很像
     *
     * #
     * Q1. 给一个student id => list of taking courses 这样一个input，求每一对学生之间的commonly taking courses。
     *
     * Q2. 给一个[prerequisite course, next course]的vector，保证有唯一一个starting course和ending course，并且所有非starting course有唯一一个前驱，所有非ending course有唯一一个后继（i.e. prerequisite的关系是一个包含所有课程简单的路径）。然后middle course定义为这个路径的中间那个，如果有偶数个course那么就是中间两个靠前面那个。求middle course。
     *
     * Q3. Q2的follow up，prerequisite的关系是一张有向无环图，求所有可能的middle courses。我的做法是直接对于每个starting course做dfs。
     */

    public static Map<String, List<String>> sharedClasses(String[] records) {
        Map<String, List<String>> res = new HashMap<>();

        Map<String, List<String>> map = new HashMap<>();
        for (String record : records) {
            String[] parts = record.split(",");
            String name = parts[0];
            String className = parts[1];

            if(!map.containsKey(name)) {
                map.put(name, new ArrayList<>());
            }
            map.get(name).add(className);
        }

        List<String> names = new ArrayList<>();
        List<List<String>> classes = new ArrayList<>();

        for (String key : map.keySet()) {
            names.add(key);
            classes.add(map.get(key));
        }

        for (int i = 0; i < names.size() - 1; i++) {
            String s1 = names.get(i);
            List<String> l1 = classes.get(i);

            for (int j = i + 1; j < names.size(); j++) {
                String s2 = names.get(j);
                List<String> l2 = classes.get(j);

                List<String> shared = findShared(l1, l2);
                String pair = s1 + "," + s2;
                res.put(pair,shared);
            }
        }

        return res;
    }

    public static List<String> findShared(List<String> l1, List<String> l2) {
        List<String> res = new ArrayList<>();
        if (l1.size() == 0 || l2.size() == 0) return res;

        Collections.sort(l1, (a, b) -> a.compareTo(b));
        Collections.sort(l2, (a, b) -> a.compareTo(b));

        int m = l1.size();
        int n = l2.size();
        int p1 = 0, p2 = 0;

        while (p1 < m && p2 < n) {
            if (l1.get(p1).compareTo(l2.get(p2)) < 0) {
                p1++;
            } else if (l1.get(p1).compareTo(l2.get(p2)) > 0) {
                p2++;
            } else {
                res.add(l1.get(p1));
                p1++;
                p2++;
            }
        }

        return res;
    }

    public static String midClass(String[] classes) {
        Map<String, String> map = new HashMap<>();
        Set<String> set = new HashSet<>();

        for (String c : classes) {
            String[] parts = c.split(",");
            map.put(parts[0], parts[1]);
            set.add(parts[0]);
            set.add(parts[1]);
        }

        for (String key : map.keySet()) {
            set.remove(map.get(key));
        }

        String start = set.iterator().next();
        System.out.println("start:" + start);
        int size = map.size();

        String cur = start;
        for (int i = 0; i < size / 2; i++) {
           String next =  map.get(cur);
           cur = next;
        }

        return cur;
    }

    /**
     * Question 3
     * Multiple class paths, there are possible multiple start classes.
     *
     * For each start class, dfs to find all possible paths, each path
     * is saved in a list, we can get mid one use index size / 2
     */
    public static List<String> midClass1(String[] classes) {
        List<String> res = new ArrayList<>();

        Map<String, List<String>> map = new HashMap<>();
        Set<String> set = new HashSet<>();

        for (String c : classes) {
            String[] parts = c.split(",");

            if (!map.containsKey(parts[0])) {
                map.put(parts[0], new ArrayList<>());
            }
            map.get(parts[0]).add(parts[1]);

            set.add(parts[0]);
            set.add(parts[1]);
        }

        for (String key : map.keySet()) {
            List<String> l = map.get(key);
            for (String s : l) {
                set.remove(s);
            }
        }

        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            String start = it.next();
            res.addAll(findMid(start, map));
        }

        return res;
    }

    private static List<String> findMid(String start, Map<String, List<String>> map) {
        List<String> res = new ArrayList<>();

        List<List<String>> paths = new ArrayList<>();
        dfs(paths, new ArrayList<>(), start, map);

        for (List<String> path : paths) {
            int size = path.size();
            int mid = size / 2;
            int idx = mid % 2 == 0 ? mid - 1 : mid;
            res.add(path.get(idx));
        }

        return res;
    }

    private static void dfs(List<List<String>> paths, List<String> temp, String cur, Map<String, List<String>> map) {
        if (!map.containsKey(cur)) {
            temp.add(cur)
            paths.add(new ArrayList<>(temp));
            temp.remove(temp.size() - 1);
            return;
        }

        temp.add(cur);
        List<String> l = map.get(cur);

        for (String next : l) {
            dfs(paths, temp, next, map);
        }
        temp.remove(temp.size() - 1);
    }


    private static void printRes1(Map<String, List<String>> map) {
        for (String key : map.keySet()) {
            List<String> l = map.get(key);
            System.out.println(key + " : " + Arrays.toString(l.toArray()));
        }
    }

    public static void main(String[] args) {
        String[] records1 = {
                "58,Software Design",
                "58,Linear Algebra",
                "94,Art History",
                "94,Operating Systems",
                "17,Software Design",
                "58,Mechanics",
                "58,Economics",
                "17,Linear Algebra",
                "17,Political Science",
                "94,Economics",
                "25,Economics"};

        String[] records2 = {
                "42,Software Design",
                "0,Advanced Mechanics",
                "9,Art History"
        };

        printRes1(sharedClasses(records1));
        printRes1(sharedClasses(records2));

        String[] records3 = {
                "1,2",
                "2,3",
                "3,4",
                "4,5"
        };

        System.out.println(midClass(records3));

    }
}
