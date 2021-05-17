package leetcode;

import java.util.*;

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
     
        By calling next repeatedly until hasNext returns false, the order of elements returned
        by next should be: [1,2,3,4,5,6].

        Follow up:
        As an added challenge, try to code it using only iterators in C++ or iterators in Java.
     */

    /**
     * Your Vector2D object will be instantiated and called as such:
     * Vector2D i = new Vector2D(vec2d);
     * while (i.hasNext()) v[f()] = i.next();
     */

    /**
     * Difference from questions with NestedInteger :
     * NestedInteger can be nested to multiple levels, that's why we need to use stack to preserve
     * the iterator on each level. For this questions, there are two level, list of list of Integer,
     * then list of Integer. Therefore, no stack needed. Just need to maintain a current Iterator of
     * Integers
     *
     */

    class Vector2D_Two_Pointers {
        int idxRow;
        int idxCol;
        int[][] nums;

        public Vector2D_Two_Pointers(int[][] v) {
            nums = v;
            idxRow = 0;
            idxCol = 0;
        }

        public int next() {
            /**
             * A better way for handling no element case, throw exception instead of returning any value.
             */
            if (!hasNext()) throw new NoSuchElementException();;

            return nums[idxRow][idxCol++];
        }

        public boolean hasNext() {
            /**
             * "idxRow < nums.length" must be in the first place, because the 2nd check uses idxRow value.
             *
             * "idxCol == nums[idxRow].length" : deals with case that we get to the end of current array, this
             *                                   includes the case of empty array.
             */
            while (idxRow < nums.length && idxCol == nums[idxRow].length ) {
                idxRow++;
                idxCol = 0;
            }

            return idxRow < nums.length;
        }
    }


    public class Vector2D_Iterator implements Iterator<Integer> {
        private Iterator<List<Integer>> row_itr;
        private Iterator<Integer> col_itr;

        public Vector2D_Iterator(List<List<Integer>> vec2d) {
            row_itr = vec2d.iterator();

            /**
             * if row_itr doesn't have next, it means vec2d is empty,
             * in this case, col_itr won't get initialized
             * **/
            if(row_itr.hasNext()) {
                col_itr = row_itr.next().iterator();
            }
        }

        public Integer next() {
            /**
             * !!!
             * we won't call next() unless we are sure that hasNext() is true, meaning col_itr.hasNext() is true
             **/
            return col_itr.next();
        }

        public boolean hasNext() {
            /**
             * !!!
             * check whether vec2d is empty, if it is empty, col_itr won't be initialized (see logic in constructor)
             **/
            if (col_itr == null) return false;

            /**
             * important!!! we do update here in hasNext(),
             * update col_itr and row_itr until col_itr.hasNext() or row_itr.hasNext() is false
             * **/
            while (!col_itr.hasNext() && row_itr.hasNext()) {
                col_itr = row_itr.next().iterator();
            }

            return col_itr.hasNext();
        }
    }




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
            //!!!"while"
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


    /**
     * Leetcode changes input from list to 2D array
     */
    class Vector2DArray {
        int idxRow;
        int idxCol;
        int[][] elements;

        public Vector2DArray(int[][] v) {
            elements = v;
            idxRow = 0;
            idxCol = 0;
        }

        public int next() {
            if (hasNext()) {
                return elements[idxRow][idxCol++];
            } else {
                return -1;
            }
        }

        public boolean hasNext() {
            while (idxRow < elements.length) {
                if (elements[idxRow] != null && idxCol < elements[idxRow].length) {
                    return true;
                } else {
                    idxRow++;
                    idxCol = 0;
                }
            }

            return false;
        }
    }

    //Solution 3 : LI_601_Flatten_2D_Vector
}
