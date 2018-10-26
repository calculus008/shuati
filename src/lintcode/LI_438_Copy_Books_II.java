package lintcode;

import java.util.PriorityQueue;

/**
 * Created by yuank on 10/24/18.
 */
public class LI_438_Copy_Books_II {
    /**
         Given n books( the page number of each book is the same) and an array of integer
         with size k means k people to copy the book and the ith integer is the time ith
         person to copy one book.

         You must distribute the continuous id books to one people to copy.

         (You can give book A[1],A[2] to one people, but you cannot give book A[1],
         A[3] to one people, because book A[1] and A[3] is not continuous.) Return the number
         of smallest minutes need to copy all the books.

         Example
         Given n = 4, array A = [3,2,4]

         Return 4( First person spends 3 minutes to copy book 1, Second person spends 4
         minutes to copy book 2 and 3, Third person spends 4 minutes to copy book 4. )

         Hard

         跟Copy Book I 区别, I里每个人效率一样，所以每个人其实都需要工作，因为无差别
         而CopyBookII 每个人效率不同了，所以特别慢的辣鸡可以甚至不让他工作

         另外，每本书不区别，完全不在乎顺序和页数，只在乎抄了几本书这个count
     */

    /**
     * Solution 1
     *
     * 二分答案，与copy book的思想相似。
     *
     * 设定一个每个人最多工作的时间t，每步求出在t的限制下这几人能copy最多的books num，
     * 需得得到的是能copy n books的最小t即为答案.
     *
     * t下界为min(times)，此时能copy的book必小于等于n；上界为min(times) * n，
     * 此时只要一个人就能在t内copy n books
     *
     * Time : O(log(range) * 人数)
     */
    public class Solution1 {
        public int copyBooksII(int n, int[] times) {
            if(times == null || times.length == 0){
                return -1;
            }

            int min = Integer.MAX_VALUE;

            for(int i = 0; i < times.length; i++){
                min = Math.min(min, times[i]);
            }
            int left = min, right = min * n;

            while(left + 1 < right){
                int mid = left + (right - left) / 2;

                /**
                 * 能完成的number of books大于等于n, 可以试着
                 * 减少工作时间。
                 */
                if(books(mid, times) >= n){
                    right = mid;
                }else{
                    /**
                     * 不能完成n本书，需要增加时间。
                     */
                    left = mid;
                }

            }

            if(books(left, times) == n){
                return left;
            }
            return right;
        }

        /**
         * 给定时间mid和n个人的工作时间in times[]，看他们能完成copy几本书。
         * O(人数)
         */
        private int books(int limit, int[] times){
            int num = 0;
            for(int i = 0; i < times.length; i++){
                int time = times[i];
                num += limit / time;
            }
            return num;
        }
    }

    /**
     * Solution 2
     * Heap + Greedy
     *
     * 每次取总花费时间最少的人去复印
     * 用堆保持这样一个性质：再给当前这个人一本书，他完成全部工作的总时间。每次从堆顶部取值再更新堆，直到抄完全部
     *
     * 思考过程可以从例举特殊情况的实例，比如工作效率为 2 和 5 两个人,求他们之间的smallest minute.
     * 最终的smallest minute是由每个人的总工作时长决定的。我们要做的是每次挑选【当前已工作时长】+ 【抄写一本书工作时间】最短的人
     * 这样才能保证，最后一次挑选的工作时间最短。???
     *
     * Time : O((人数 + 书的数目)log(人数))
     *
     * Example :
     * Given n = 4, array A = [3,2,4]
     *
     * heap [{2, 1, 2}, {3, 0, 3}, {4, 2, 4}]
     *
     * n = 4, cell = {2 + 2, 1, 2}
     * heap [{3, 0, 3}, {4, 2, 4}, {4, 1, 4}]
     *
     * n = 3, cell = {3 + 3, 0, 3}
     * heap [{4, 2, 4}, {4, 1, 4}, {6, 0, 3}]
     *
     * n = 2, cell = {4 + 4, 2, 4}
     * heap [{4, 1, 4}, {6, 0, 3}, {8, 2, 4}]
     *
     * result heap.poll.val = 4
     *
     */
    public class Solution2 {
        class Cell {
            int val;
            int idx;
            int step;

            public Cell(int v, int i, int step) {
                this.val = v;
                this.idx = i;
                this.step = step;
            }
        }

        public int copyBooksII(int n, int[] times) {
            if (n <= 0) {
                return 0;
            }
            if (times == null || times.length == 0) {
                return Integer.MAX_VALUE; // or return -1
            }

            /**
             * the fastest copier is at the top of the heap
             */
            PriorityQueue<Cell> heap = new PriorityQueue<>((a, b) -> a.val - b.val);

            for (int i = 0; i < times.length; i++) {
                heap.add(new Cell(times[i], i, times[i]));
            }

            while (n > 1) {
                Cell cell = heap.poll();
                cell.val = cell.val + cell.step;
                heap.add(cell);
                n--;
            }

            return heap.poll().val;
        }
    }
}
