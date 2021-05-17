package Interviews.Amazon;

import java.util.ArrayList;
import java.util.List;

public class Linux_Command_Find_2 {
    /**
     * 面经描述：
     *
     * #
     * design linux find OOD
     * OOD - linux command find。有多种filter查找结果的feature，比如文件大小，文件类型等；
     * 之后又问如果要加上别的logistic operation怎么办，
     * 比如OR，AND。这题相当高频了，地里有个大神写过一个很详细的solution
     *
     * #
     * OOD，设计一个算法和class 来实现 一个 find command，这个 find command 有各种filter，
     * 比如 小于特定size，比如名字要有什么样子的匹配，这个使用一个 filter pattern就可以了，
     * 同时要进行parallel的处理
     *
     * #
     * Design a class that implement Linux find command. 主要也是考ood吧，要写代码
     * 我个人感觉是个开放式问题，没有正确答案。具体题目可以看这里
     * https://leetcode.com/discuss/interview-question/369272/Amazon-or-Onsite-or-Linux-Find-Command
     *
     * #
     * ood，设计一个类似linux的find命令，实现找文件大小大于5M以及找所有xml文件
     *
     *
     */

    /**
     * From leetcode discussion
     * https://leetcode.com/discuss/interview-question/369272/Amazon-or-Onsite-or-Linux-Find-Command
     *
     * Implemnet linux find command as an api ,the api will support finding files that has given size
     * requirements and a file with a certain format like
     *
     * find all file >5mb
     * find all xml
     *
     * Assume file class
     * {
     *  get name()
     *  directorylistfile()
     *  getFile()
     * }
     * create a library flexible that is flexible
     * Design clases,interfaces.
     *
     * As for what I would expect (not necessarily all of these):
     *
     * 1.Obviously coming straight to the right design (encapsulating the Filtering logic into its own interface etc...),
     *   with an explanation on why this approach is good. I'm obviously open to alternate approaches as long as they
     *   are as flexible and elegant.
     * 2.Implement boolean logic: AND/OR/NOT, here I want to see if the candidate understands object composition
     * 3.Support for symlinks. Rather than seeing the implementation (which I don't really care about) I want to
     *   understand the trade-offs of adding yet another parameter to the find method VS other options (eg. Constructor).
     *   Keep adding params to a method is usually bad.
     * 4.How to handle the case where the result is really big (millions of files), and you may not be able to put all
     *   of them into a List.(!!!)
     */

    /**
     * This implementation only takes care of #1
     */
    class File {
        String name;
        int size;
        int type;
        boolean isDirectory;
        File[] children;
    }

    abstract class Filter {
        abstract boolean apply(File file);
    }

    class MinSizeFilter extends Filter {
        int minSize;

        public MinSizeFilter(int minSize) {
            this.minSize = minSize;
        }

        @Override
        boolean apply(File file) {
            return file.size > minSize;
        }
    }

    class TypeFilter extends Filter {
        int type;

        public TypeFilter(int type) {
            this.type = type;
        }

        @Override
        boolean apply(File file) {
            return file.type == type;
        }
    }

    class FindCommand {
        public List<File> findWithFilters(File directory, List<Filter> filters) {
            if (!directory.isDirectory) {
//                throw new Exception();
            }
            List<File> output = new ArrayList<>();
            findWithFilters(directory, filters, output);
            return output;
        }

        private void findWithFilters(File directory, List<Filter> filters, List<File> output) {
            if (directory.children == null) {
                return;
            }

            for (File file : directory.children) {
                if (file.isDirectory) {
                    findWithFilters(file, filters, output);
                } else {
                    boolean selectFile = true;
                    for (Filter filter : filters) {
                        if (!filter.apply(file)) {
                            selectFile = false;
                        }
                    }
                    if (selectFile) {
                        output.add(file);
                    }
                }
            }
        }
    }

}
