package leetcode;

import java.util.*;

public class LE_588_Design_In_Memory_File_System {
    /**
     * Design an in-memory file system to simulate the following functions:
     *
     * ls: Given a path in string format. If it is a file path, return a list
     * that only contains this file's name. If it is a directory path, return
     * the list of file and directory names in this directory. Your output
     * (file and directory names together) should in lexicographic order.
     *
     * mkdir: Given a directory path that does not exist, you should make a
     * new directory according to the path. If the middle directories in the
     * path don't exist either, you should create them as well. This function
     * has void return type.
     *
     * addContentToFile: Given a file path and file content in string format.
     * If the file doesn't exist, you need to create that file containing given
     * content. If the file already exists, you need to append given content to
     * original content. This function has void return type.
     *
     * readContentFromFile: Given a file path, return its content in string format.
     *
     * Example:
     *
     * Input:
     * ["FileSystem","ls","mkdir","addContentToFile","ls","readContentFromFile"]
     * [[],["/"],["/a/b/c"],["/a/b/c/d","hello"],["/"],["/a/b/c/d"]]
     *
     * Output:
     * [null,[],null,null,["a"],"hello"]
     *
     * Note:
     *
     * You can assume all file or directory paths are absolute paths which begin
     * with / and do not end with / except that the path is just "/".
     * You can assume that all operations will be passed valid parameters and users
     * will not attempt to retrieve file content or list a directory or file that does not exist.
     * You can assume that all directory names and file names only contain lower-case
     * letters, and same names won't exist in the same directory.
     *
     * Hard
     */

    /**
     * Basically it's a Trie problem, use FileNode System as disguise.
     * Here for path "/dir1/dir2/dir3", we take each dir of file name as key
     * in children map of the FileNode node, just like we take each char in a string
     * as key for next child in Trie Node.
     *
     * ls() and traverse() is the same thing, it is similar to addWord()
     *
     * The core action is in traverse(), it find the targeted FileNode Node.
     *
     * The advantage of this scheme of maintaining the directory structure is that it is expandable
     * to include even more commands easily.
     *
     * For example, "rmdir" to remove a directory given an input
     * directory path. We need to simply reach to the destined directory level and remove the corresponding
     * directory entry from the corresponding dirs keys.
     *
     * Renaming files/directories is also very simple, since all we need to do is to create a temporary
     * copy of the directory structure/file with a new name and delete the last entry.
     *
     * Relocating a hierarchichal subdirectory structure from one directory to the other is also very easy,
     * since, all we need to do is obtain the address for the corresponding subdirectory class, and assign
     * the same at the new position in the new directory structure.
     *
     * If the number of directories is very large, we waste redundant space for "isfile" and "content",
     * which wasn't needed in the first design.
     *
     * A problem with the current design could occur if we want to list only the directories(and not the files),
     * on any given path. In this case, we need to traverse over the whole contents of the current directory,
     * check for each entry, whether it is a file or a directory, and then extract the required data.
     *
     * Complexity analysis see Solution2 in
     * https://leetcode.com/problems/design-in-memory-file-system/solution/
     *
     * Time Complexity:
     * ls() : O(m + n + klogk)
     *        "m" refers to the length of the input string. We need to scan the input string
     *        once to split it and determine the various levels.
     *        "n" refers to the depth of the last directory level in the given input for ls.
     *        This factor is taken becaus we need to enter n levels of the tree structure
     *        to reach the last level.
     *        "k" refers to the number of entries(files+subdirectories) in the last level
     *        directory(in the current input). We need to sort these names giving a factor
     *        of klog(k).
     *
     * mkdir() : O(m + n)
     *
     * addContentToFile : O(m + n)
     *
     * readContentFromFile : O(m + n)
     */
    public class FileSystem {
        class File {
            boolean isFile = false;
            /**
             * Or we can use TreeMap here so that we sort the files/subdirs
             * list in mkdir() (O(l + l * nlogn)), and ls() will only take O(l)
             *
             * l : is the length of the file path(number of subdirs).
             * n : average number of subdirs/files in FileNode Node.
             */
            Map<String, File> children = new HashMap<>();
            String content = "";
        }

        File root = null;

        public FileSystem() {
            root = new File();
        }

        public List<String> ls(String path) {
            String[] dirs = path.split("/");
            File node = root;
            List<String> result = new ArrayList<>();
            String name = "";

            for (String dir : dirs) {
                /**
                 * !!!
                 * After "String[] dirs = path.split("/")", since the path
                 * starts with "/", the first string in dirs is "". We need
                 * to deal with it here. For example, path = "/a/b/c", after
                 * split, dirs[] : ["", "a", "b", "c"]
                 */
                if (dir.length() == 0) continue;

                if (!node.children.containsKey(dir)) {
                    return result;
                }
                node = node.children.get(dir);
                name = dir;
            }

            if (node.isFile) {
                result.add(name);
            } else {
                for (String key : node.children.keySet()) {
                    result.add(key);
                }
            }

            Collections.sort(result);

            return result;
        }

        public void mkdir(String path) {
            traverse(path);
        }

        /**
         * addContentToFile:
         * Given a file path and file content in string format. If the file doesn't exist,
         * you need to create that file containing given content. If the file already exists,
         * you need to append given content to original content. This function has void return type.
         */
        public void addContentToFile(String filePath, String content) {
            File cur = traverse(filePath);
            /**
             * !!!
             * Don't forget to set isFile to true
             */
            cur.isFile = true;
            cur.content += content;
        }

        public String readContentFromFile(String filePath) {
            File cur = traverse(filePath);
            return cur.content;
        }

        private File traverse(String filePath){
            String[] dirs = filePath.split("/");
            File cur = root;

            for (String dir : dirs){
                /**
                 * !!!
                 */
                if (dir.length() == 0) continue;

                cur.children.putIfAbsent(dir, new File());
                cur = cur.children.get(dir);
            }

            return cur;
        }

        /**
         * ------------------Extra Functions-----------------
         */
        private boolean rmdir(String filePath){
            String[] dirs = filePath.split("/");
            File cur = root;

            for (int i = 1; i < dirs.length; i++){
                String dir = dirs[i];
                if (dir.length() == 0) continue;

                if (i == dirs.length - 1) {
                    File  item = cur.children.remove(dir);
                    boolean ret = (item != null);
                    item = null;
                    return ret;
                }

                if (!cur.children.containsKey(dir)) {
                    return false;
                }
                cur = cur.children.get(dir);
            }

            return false;
        }

        private boolean rename(String filePath, String name){
            String[] dirs = filePath.split("/");
            File cur = root;

            for (int i = 1; i < dirs.length; i++){
                String dir = dirs[i];
                if (dir.length() == 0) continue;

                if (i == dirs.length - 1) {
                    File  item = cur.children.remove(dir);
                    if (item == null) return false;
                    cur.children.put(name, item);
                    return true;
                }

                if (!cur.children.containsKey(dir)) {
                    return false;
                }
                cur = cur.children.get(dir);
            }

            return false;
        }
    }

    /**
     * A fast solution from leetcode
     *
     * 35ms
     */
    class FileSystem_1 {
        class FileNode {
            String path;
            boolean isFile;
            String content = "";
            TreeMap<String, FileNode> children;

            public FileNode(String path) {
                this.path = path;
                children = new TreeMap<>();
            }
        }

        FileNode root;

        public FileSystem_1() {
            root = new FileNode("/");
        }

        private FileNode getFile(String path) {
            FileNode cur = root;
            String[] dirs = path.split("/");

            for (String dir : dirs) {
                if (dir.length() == 0) continue;

                if (!cur.children.containsKey(dir)) {
                    cur.children.put(dir, new FileNode(dir));
                }

                cur = cur.children.get(dir);
            }

            return cur;
        }

        public List<String> ls(String path) {
            List<String> res = new ArrayList<>();
            FileNode p = getFile(path);

            if (p.isFile) {
                res.add(p.path);
                return res;
            }

            if (p.children != null) {
                for (String name : p.children.keySet()) {
                    res.add(name);
                }
            }

            return res;
        }

        //test when directory name conflict with file name
        public void mkdir(String path) {
            FileNode p = getFile(path);
        }

        public void addContentToFile(String filePath, String content) {
            FileNode p = getFile(filePath);
            p.isFile = true;
            p.content += content;
        }

        public String readContentFromFile(String filePath) {
            FileNode p = getFile(filePath);
            return p.content;
        }
    }

    /**
     * This solution is more OOP.
     *
     * Splitting the classes and implementing it's own methods (I mean in a real project we
     * could make these 2 public classes) make it easy to reuse in other parts, more readable
     * and friendly to edit or add new functionalities in the future.
     *
     * Single Responsibility principle in SOLID
     */
    public class FileSystem_2 {
        // Private class
        private class FileNode{
            private TreeMap<String, FileNode> children;
            private StringBuilder file;
            private String name;
//            private boolean isFile;

            public FileNode(String name) {
                children = new TreeMap<>();
                file = new StringBuilder();
                this.name = name;
//                this.isFile = isFile;
            }

            public String getContent(){
                return file.toString();
            }

            public String getName(){
                return name;
            }

            public void addContent(String content){
                file.append(content);
            }

            /**
             * With this method, we can't create empty file.
             * Can solve it by adding "isFile" property in FileNode...,
             * Need to communicate with interviewer to clarify.
             */
            public boolean isFile(){
                return file.length() > 0;
            }

            public List<String> getList(){
                List<String> list = new ArrayList<>();

                if(isFile()){
                    list.add(getName());
                }else{
                    list.addAll(children.keySet());
                }

                return list;
            }
        }

        private FileNode root;

        public FileSystem_2() {
            root = new FileNode("");
        }

        public List<String> ls(String path) {
            return findNode(path).getList();
        }

        public void mkdir(String path) {
            findNode(path);
        }

        public void addContentToFile(String filePath, String content) {
            findNode(filePath).addContent(content);
        }

        public String readContentFromFile(String filePath) {
            return findNode(filePath).getContent();
        }

        //-- private method section --//
        private FileNode findNode(String path){
            String[] files = path.split("/");

            FileNode cur = root;
            for(String file : files){
                if(file.length() == 0) continue;

                cur.children.putIfAbsent(file, new FileNode(file));
                cur = cur.children.get(file);

                if(cur.isFile()) break;
            }

            return cur;
        }
    }
}
