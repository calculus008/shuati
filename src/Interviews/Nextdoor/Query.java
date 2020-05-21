package Interviews.Nextdoor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Query {
    /**
     * 给一串数字a，再给一串query，每个是以l, r, x的形式，然后寻找a[l:r+1]之间x出现的次数，
     * query间​​​​​​​​​​​​​​​​​​​累和输出。注意TLE
     * 给一个array和一个matrix。
     * matrix里面每一个vector<int>的形式必定是[l,r,target]，固定只有3个数。 然后要求统计
     * array里 index从l 到 r这个区间出现了多少次target这个数。 比如:
     * array = [1,1,2,3,2]
     * matrix = [[1,2,1], [2,4,2], [0,3,1]]
     * output : 5
     *
     * 因为在matrix[0], array的index 1到2区间出现了1 一次， matrix[1], array的index
     * 2到4区间出现2 两次。 matrx[2], array的index 0到3区间出现1 两次
     * 这个题如果直接暴力解O(n*n)会有两个test case过不了。我是用hashmap<​​​​​​​​​​​​​​​​​​​int, vector<pair<int,int>>>。
     * key是target， value是index区间。 这样走一遍array，每次确定一下当前index在不在区间里就行了。
     */
    public static int find_in_interval(int[] nums, int[][] query) {
        HashMap<Integer, ArrayList<Integer>> map = new HashMap();
        int i = 0;

        /**
         * map:
         * number -> list of indexes that this number appears
         */
        for (int n : nums) {
            if (map.containsKey(n) == false) {
                map.put(n, new ArrayList<Integer>());
            }
            map.get(n).add(i);
            i++;
        }

        int result = 0;
        for (int[] q : query) {
            int l = q[0];
            int r = q[1];
            int t = q[2];

            if (map.containsKey(t) == false) {
                continue;
            } else {
                int left_pos = Collections.binarySearch(map.get(t), l);
                int right_pos = Collections.binarySearch(map.get(t), r);

                if (left_pos >= 0 && right_pos >= 0) {
                    result += (right_pos - left_pos + 1);
                } else {
                    left_pos = (left_pos < 0 ? -(left_pos + 1) : left_pos);
                    right_pos = (right_pos < 0 ? -(right_pos + 1) : right_pos);

                    if (left_pos == right_pos) {
                        continue;
                    } else {
                        // 1 3 5 7 9 (3, 4) -[1, 2]
                        result += (right_pos - left_pos);
                    }
                }

//                left_pos = (left_pos < 0 ? -(left_pos + 1) : left_pos);
//                right_pos = (right_pos < 0 ? -(right_pos + 1) : right_pos);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] array = new int[]{1, 1, 2, 3, 2};
        int[][] query = new int[3][3];
        query[0] = new int[]{1, 2, 1};
        query[1] = new int[]{2, 4, 2};
        query[2] = new int[]{0, 3, 1};
        int result = find_in_interval(array, query);
        System.out.println(result);
    }
}
