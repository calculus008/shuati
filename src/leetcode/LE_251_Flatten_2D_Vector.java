package leetcode;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by yuank on 4/10/18.
 */
public class LE_251_Flatten_2D_Vector {
    /**
        Implement an iterator to flatten a 2d vector.

        For example,
        Given 2d vector =

        [
          [1,2],
          [3],
          [4,5,6]
        ]
        By calling next repeatedly until hasNext returns false, the order of elements returned by next should be: [1,2,3,4,5,6].

        Follow up:
        As an added challenge, try to code it using only iterators in C++ or iterators in Java.
     */

    /**
     * Your Vector2D object will be instantiated and called as such:
     * Vector2D i = new Vector2D(vec2d);
     * while (i.hasNext()) v[f()] = i.next();
     */

    //Solution 1 : Use Iterator
    public class Vector2D1 implements Iterator<Integer> {
        Queue<Iterator> queue;
        //!!!"<Integer>"
        Iterator<Integer> cur;

        public Vector2D1(List<List<Integer>> vec2d) {
            queue = new LinkedList<>();
            for (List<Integer> list : vec2d) {
                queue.offer(list.iterator());
            }
            cur = queue.poll();
        }

        @Override
        public Integer next() {
            if (!cur.hasNext()) {
                cur = queue.poll();
            }
            return cur.next();
        }

        @Override
        public boolean hasNext() {
            while (cur != null) {
                if (cur.hasNext()) {
                    return true;
                } else {
                    cur = queue.poll();
                }
            }

            return false;
        }
    }

    //Solution 2 : use index
    public class Vector2D2 {
        int indexList, indexEle;
        List<List<Integer>> vec;

        public Vector2D2(List<List<Integer>> vec2d) {
            indexList = 0;
            indexEle = 0;
            vec = vec2d;
        }

        public int next() {
            return vec.get(indexList).get(indexEle++);
        }

        public boolean hasNext() {
            while(indexList < vec.size()){
                if(indexEle < vec.get(indexList).size())
                    return true;
                else{
                    indexList++;
                    indexEle = 0;
                }
            }
            return false;
        }
    }

    //Solution 3 : LI_601_Flatten_2D_Vector
}
