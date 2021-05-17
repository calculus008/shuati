package Interviews.Amazon.Find_Command;

import java.util.*;

/**
 * A solution from leetcode user, extended from LE_588_Design_In_Memory_File_System
 *
 * If it's a coding question, not a OOD question, this is more likely to be the answer.
 *
 * Or it can be asked as question : implement a in-mem system that supports :
 *
 * ls
 * delete
 * mkdir
 * rmdir
 * rename
 * addContentToFile
 * readContentFromFile
 *
 * https://leetcode.com/problems/design-in-memory-file-system/discuss/103331/Java-Solution-File-class
 *
 * 设计一个查找文件的API，支持模糊搜索，返回所有符合条件的文件名
 * 自己觉得题就是DFS搜索，但是崩在了关于模糊搜索怎么处理regular expression matching，
 * 自己自动带入做题模式，12*对不对得上123，一个一个比较，比到*的时候分叉的思路
 * 面试官直接说难道你不知道regular expression matching怎么用吗，原来是想要让直接用java内置的函数处理，
 * 给我写了Pattern Class的match方法……后来又说我append文件路径的做法也不对，反正就感觉他想要的是一个比起
 * 做题更偏向实际在程序里能work的答案，两个人沟通不咋顺畅，对面不理解我，我也没完全听懂他，就崩掉了
 */
class File {
    boolean isFile;
    Map<String, File> children;
    /**
     * !!!
     * Key:
     * Need parent pointer for delete(), rename()
     */
    File parent;
    String name;
    String content;

    public File() {
        children = new HashMap<>();
        isFile = false;
        content = "";
    }

    public File(String name) {
        this();
        this.name = name;
    }

    public String getName() {
        return this.name;
    }


    public void addChild(File f) {
        f.parent = this;
        children.put(f.getName(), f);
    }

    public void rename(String name) {
        parent.children.remove(this.name);
        this.name = name;
        parent.addChild(this);
    }

    public void delete() {
        parent.removeChild(name);
    }

    public void removeChild(String s) {
        File child = children.get(s);
        if (child != null) {
            child.parent = null;
        }
        children.remove(s);
    }

    public File getChild(String s) {
        return children.get(s);
    }

    public List<File> getChildren() {
        return new ArrayList<>(children.values());
    }

    public boolean contains(String s) {
        return children.containsKey(s);
    }


    public void addContent(String s) {
        this.content += s;
    }

    public String getContent() {
        return this.content;
    }

    public void setFile() {
        this.isFile = true;
    }

    public boolean isFile() {
        return isFile;
    }


}

public class FileSystem {
    File root;

    public FileSystem() {
        root = new File();
    }

    public List<String> ls(String path) {
        File cur = traverse(path);
        List<String> res = new ArrayList<>();
        if (cur.isFile) {
            res.add(cur.getName());
        } else {
            for (File f : cur.getChildren()) {
                res.add(f.getName());
            }
        }
        Collections.sort(res);
        return res;
    }

    public File traverse(String path) {
        File cur = root;
        String[] paths = path.split("/");
        for (String p : paths) {
            if (p.isEmpty()) {
                continue;
            }
            if (!cur.contains(p)) {
                File f = new File(p);
                cur.addChild(f);
            }
            cur = cur.getChild(p);
        }
        return cur;
    }


    // find all files in the given path
    public List<String> find(String path) {
        File cur = traverse(path);
        List<String> res = new ArrayList<>();
        return find(cur);
    }

    /**
     * DFS
     */
    private List<String> find(File f) {
        List<String> res = new ArrayList<>();
        if (f == null) {
            return res;
        } else if (f.isFile()) {
            res.add(f.getName());
        } else {
            for (File child : f.getChildren()) {
                res.addAll(find(child));
            }
        }
        return res;
    }


    // rename the file in a given path, do nothing if the file doesn't exist
    public void rename(String path, String name) {
        File cur = traverse(path);
        cur.rename(name);
    }

    // delete the file in a given path; delete the entire folder if it is a directory; do nothing if it is not a file
    public void delete(String path) {
        File cur = traverse(path);
        cur.delete();
    }

    public void mkdir(String path) {
        File cur = traverse(path);
    }

    public void addContentToFile(String path, String content) {
        File cur = traverse(path);
        cur.setFile();
        cur.addContent(content);
    }

    public String readContentFromFile(String path) {
        File cur = traverse(path);
        return cur.getContent();
    }

    public static void main(String[] args) {
        FileSystem fs = new FileSystem();
        fs.mkdir("/a/b/c/d");
        fs.addContentToFile("/a/b/c/d/e", "Hello");
        fs.addContentToFile("/a/b/c/d/f", "Hello world");
        fs.addContentToFile("/a/b/c/d/g/h", "Hello world 2");
        fs.rename("/a/b/c/d/e", "newname");
        for (String s : fs.find("/")) {
            System.out.println(s);
        }
    }

}

