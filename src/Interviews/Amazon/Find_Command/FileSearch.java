package Interviews.Amazon.Find_Command;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 设计一个查找文件的API，支持模糊搜索，返回所有符合条件的文件名
 * 自己觉得题就是DFS搜索，但是崩在了关于模糊搜索怎么处理regular expression matching，
 * 自己自动带入做题模式，12*对不对得上123，一个一个比较，比到*的时候分叉的思路
 * 面试官直接说难道你不知道regular expression matching怎么用吗，原来是想要让直接用java内置的函数处理，
 * 给我写了Pattern Class的match方法……后来又说我append文件路径的做法也不对，反正就感觉他想要的是一个比起
 * 做题更偏向实际在程序里能work的答案，两个人沟通不咋顺畅，对面不理解我，我也没完全听懂他，就崩掉了
 *
 * This is not about OOD or designing a Trie like in-mem file system, this is to implement
 * a real file search utility using Java APIs.
 *
 * https://mkyong.com/java/search-directories-recursively-for-file-in-java/
 **/

public class FileSearch {
    private String fileNameToSearch;
    private List<String> result = new ArrayList<String>();

    public String getFileNameToSearch() {
        return fileNameToSearch;
    }

    public void setFileNameToSearch(String fileNameToSearch) {
        this.fileNameToSearch = fileNameToSearch;
    }

    public List<String> getResult() {
        return result;
    }

    public void searchDirectory(File directory, String fileNameToSearch) {
        setFileNameToSearch(fileNameToSearch);

        if (directory.isDirectory()) {
            search(directory);
        } else {
            System.out.println(directory.getAbsoluteFile() + " is not a directory!");
        }

    }

    /**
     * This is to do exact search given the real file name.
     * @param file
     */
    private void search(File file) {
        if (file.isDirectory()) {
            System.out.println("Searching directory ... " + file.getAbsoluteFile());

            //do you have permission to read this directory?
            if (file.canRead()) {
                for (File temp : file.listFiles()) {
                    if (temp.isDirectory()) {
                        search(temp);
                    } else {
                        if (getFileNameToSearch().equals(temp.getName().toLowerCase())) {
                            result.add(temp.getAbsoluteFile().toString());
                        }

                    }
                }
            } else {
                System.out.println(file.getAbsoluteFile() + "Permission Denied");
            }
        }
    }

    private File[] listFilesMatching(File root, String regex) {
        if(!root.isDirectory()) {
            throw new IllegalArgumentException(root+" is no directory.");
        }

        final Pattern p = Pattern.compile(regex); // careful: could also throw an exception!
        return root.listFiles(new FileFilter(){
            @Override
            public boolean accept(File file) {
//                System.out.println(file.getName());
                return p.matcher(file.getName()).matches();
            }
        });
    }

    public List<String> fussySearch(File directory, String fileNameToSearch) {
        List<String> res = new ArrayList<>();
        File[] ret = listFilesMatching(directory, fileNameToSearch);

        System.out.println("res size " + ret.length);
        for (File f : ret) {
            res.add(f.getName());
        }

        return res;
    }

    /**----------------Recursive Search With Regex-----------------------**/

    public void helper(File directory, Pattern p) {
        File[] ret = directory.listFiles(new FileFilter(){
            @Override
            public boolean accept(File file) {
                return p.matcher(file.getName()).matches();
            }
        });

        for (File f : ret) {
            this.result.add(f.getName());
        }
    }

    public void fussySearchRecursive(File directory, String pattern) {
        this.result.clear();

        final Pattern p = Pattern.compile(pattern); // careful: could also throw an exception!

        if (directory.isDirectory()) {
            helper(directory, p);

            for (File temp : directory.listFiles()) {
                if (temp.isDirectory()) {
                    helper(temp, p);
                }
            }
        }

        System.out.println("size : " + result.size());
    }

    public static void main(String[] args) {
        FileSearch fileSearch = new FileSearch();

        //try different directory and filename :)
//        fileSearch.searchDirectory(new File("/Users/mkyong/websites"), "post.php");
//
//        int count = fileSearch.getResult().size();
//        if (count == 0) {
//            System.out.println("\nNo result found!");
//        } else {
//            System.out.println("\nFound " + count + " result!\n");
//            for (String matched : fileSearch.getResult()) {
//                System.out.println("Found : " + matched);
//            }
//        }

        fileSearch.fussySearchRecursive(new File("/Users/yuank/Documents/Benefit"),
                "^.*\\.(rar|txt|pdf)$");
        System.out.println(Arrays.toString(fileSearch.result.toArray()));
    }
}

