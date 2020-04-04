package leetcode;

import java.util.*;

public class LE_1233_Remove_Sub_Folders_From_The_Filesystem {
    /**
     * Given a list of folders, remove all sub-folders in those folders and
     * return in any order the folders after removing.
     *
     * If a folder[i] is located within another folder[j], it is called a
     * sub-folder of it.
     *
     * The format of a path is one or more concatenated strings of the
     * : / followed by one or more lowercase English letters. For example,
     * /leetcode and /leetcode/problems are valid paths while an empty
     * string and / are not.
     *
     * Example 1:
     * Input: folder = ["/a","/a/b","/c/d","/c/d/e","/c/f"]
     * Output: ["/a","/c/d","/c/f"]
     * Explanation: Folders "/a/b/" is a subfolder of "/a" and "/c/d/e" is inside of
     * folder "/c/d" in our filesystem.
     *
     * Example 2:
     * Input: folder = ["/a","/a/b/c","/a/b/d"]
     * Output: ["/a"]
     * Explanation: Folders "/a/b/c" and "/a/b/d/" will be removed because they are subfolders of "/a".
     *
     * Example 3:
     * Input: folder = ["/a/b/c","/a/b/ca","/a/b/d"]
     * Output: ["/a/b/c","/a/b/ca","/a/b/d"]
     *
     * Constraints:
     * 1 <= folder.length <= 4 * 10^4
     * 2 <= folder[i].length <= 100
     * folder[i] contains only lowercase letters and '/'
     * folder[i] always starts with character '/'
     * Each folder name is unique.
     *
     * Medium
     */

    /**
     * Set, no sort, 21ms
     *
     * Time  : O(n * l), n - number of folders, l - average length of folder names
     * Space : O(n)
     */
    class Solution1 {
        public List<String> removeSubfolders(String[] folder) {
            int n = folder.length;
            // Arrays.sort(folder, (a, b) > a.length() - b.length());
            List<String> res = new ArrayList<>();

            Set<String> set = new HashSet<>();
            for (String f : folder) {
                set.add(f);
            }

            for (String cur : folder) {
                boolean delete = false;

                for (int i = 1; i < cur.length(); i++) {
                    if (cur.charAt(i) == '/') {
                        String s = cur.substring(0, i);

                        if (set.contains(s)) {
                            delete = true;
                            break;
                        }
                    }
                }

                if (!delete) {
                    res.add(cur);
                }
            }

            return res;
        }
    }

    /**
     * Time : O(nlogn)
     */
    class Solution2 {
        public List<String> removeSubfolders(String[] folder) {
            int n = folder.length;
            Arrays.sort(folder);

            List<String> res = new ArrayList<>();

            res.add(folder[0]);

            for (int i = 1; i < n; i++) {
                String last = res.get(res.size() - 1);
                String cur = folder[i];
                if (!cur.startsWith(last + "/")) {
                    res.add(cur);
                }
            }

            return res;
        }
    }
}
