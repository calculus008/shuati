package leetcode;

import java.util.*;

public class LE_1166_Design_File_System {
    /**
     * You are asked to design a file system that allows you to create new paths and associate them with different values.
     *
     * The format of a path is one or more concatenated strings of the form: / followed by one or more lowercase English
     * letters. For example, "/leetcode" and "/leetcode/problems" are valid paths while an empty string "" and "/" are not.
     *
     * Implement the FileSystem class:
     *
     * bool createPath(string path, int value) Creates a new path and associates a value to it if possible and returns true.
     * Returns false if the path already exists or its parent path doesn't exist.
     * int get(string path) Returns the value associated with path or returns -1 if the path doesn't exist.
     *
     * Example 1:
     * Input:
     * ["FileSystem","createPath","get"]
     * [[],["/a",1],["/a"]]
     * Output:
     * [null,true,1]
     * Explanation:
     * FileSystem fileSystem = new FileSystem();
     * fileSystem.createPath("/a", 1); // return true
     * fileSystem.get("/a"); // return 1
     *
     * Example 2:
     * Input:
     * ["FileSystem","createPath","createPath","get","createPath","get"]
     * [[],["/leet",1],["/leet/code",2],["/leet/code"],["/c/d",1],["/c"]]
     * Output:
     * [null,true,true,2,false,-1]
     * Explanation:
     * FileSystem fileSystem = new FileSystem();
     * fileSystem.createPath("/leet", 1); // return true
     * fileSystem.createPath("/leet/code", 2); // return true
     * fileSystem.get("/leet/code"); // return 2
     * fileSystem.createPath("/c/d", 1); // return false because the parent path "/c" doesn't exist.
     * fileSystem.get("/c"); // return -1 because this path doesn't exist.
     *
     *
     * Constraints:
     * The number of calls to the two functions is less than or equal to 104 in total.
     * 2 <= path.length <= 100
     * 1 <= value <= 109
     *
     * Medium
     *
     * https://leetcode.com/problems/design-file-system/
     */

    /**
     * Trie 的变形题。
     */
    class FileSystem1 {
        class Node {
            Map<String, Node> children;
            String name;
            int val;

            public Node (String name, int val) {
                children = new HashMap<>();
                this.name = name;
                this.val = val;
            }
        }

        Node root;

        public FileSystem1() {
            root = new Node("", -1);
        }

        public boolean createPath(String path, int value) {
            String[] names = path.split("/");
            String name = names[names.length - 1];
            Node parent = find(names, false);

            if (parent == null || parent.children.containsKey(name)) return false;

            Node target = new Node(name, value);
            parent.children.put(name, target);

            return true;
        }

        public int get(String path) {
            Node res = find(path.split("/"), true);
            return res == null ? -1 : res.val;
        }

        private Node find(String[] names, boolean fullpath) {
            Node node = root;
            int n = names.length;
            int m = fullpath ? n : n - 1;

            /**
             * !!!
             * For "/a", after split, it is {"", "a"}, so we should start from index 1, not index 0.
             */
            for (int i = 1; i < m; i++) {
                if (!node.children.containsKey(names[i])) return null;
                node = node.children.get(names[i]);
            }

            return node;
        }
    }

    /**
     * Only use HashMap
     */
    class FileSystem2 {
        Map<String, Integer> file = new HashMap<>();

        public FileSystem2() {
            file.put("", -1);
        }

        public boolean create(String path, int value) {
            int idx = path.lastIndexOf("/");
            String parent = path.substring(0, idx);
            if (!file.containsKey(parent)) { return false; }
            return file.putIfAbsent(path, value) == null;
        }

        public int get(String path) {
            return file.getOrDefault(path, -1);
        }
    }

}
