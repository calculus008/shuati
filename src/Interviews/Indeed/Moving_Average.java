package Interviews.Indeed;

import java.util.*;

public class Moving_Average {
    /**
     * Given a stream of input, and a API int getNow() to get the current time stamp,finish two methods:
     * 1. void record(int val) to save the record.
     * 2. double getAvg() to calculate the averaged value of all the records in 5 minutes.
     */
    public class MoveingAverage1 {
        class Event {
            int val;
            int time;

            public Event(int val, int times) {
                this.val = val;
                this.time = time;
            }
        }

        private Queue<Event> queue = new LinkedList<>();
        private int sum = 0;

        /**
         * Given method
         */
        public int getNow() {
            return 0;
        }

        private boolean isExpired(int curTime, int preTime) {
            return curTime - preTime > 300;
        }

        private void removeExpiredEvent() {
            while (!queue.isEmpty() && isExpired(getNow(), queue.peek().time)) {
                Event event = queue.poll();
                sum -= event.val;
            }
        }

        public void record(int val) {
            Event e = new Event(val, getNow());
            queue.offer(e);
            sum += val;

            removeExpiredEvent();
        }

        public double getAvg() {
            removeExpiredEvent();

            if (!queue.isEmpty()) {
                return (double) sum / queue.size();
            }

            return 0.0;
        }
    }

    /**
     * 注意这⾥里里有个问题，如果对record和getaverage的性能要求特别⾼高，不不想出现突刺刺情况，就是某个请求要等⽐比较久
     * 可以把removeExpired函数拿出来专⻔门⽤用⼀一个线程去跑，每秒调⽤用⼀一次
     */

    /**
     * ========Follow Up=======
     * 1.memory不够大怎么办（数据点非常密集，5分钟就把内存爆了）
     * 2.getMedium方法实现
     * 需要注意的是follow up都是在原有的代码基础上做改进。
     *
     * 对于1的方法，数据点密集的话，选择10秒的时间段，合并数据，得到一个10秒的和和数据数量，那么queue
     * size就被一个int变量替换掉，这样丢掉过期数据的时候要更新sum和数据总和。这样会造成一定的偏差，
     * 但是没办法，条件不好内存不够就忍忍吧。
     *
     * 对于2，就是quick select的find kth in an array的方法。复杂度是O(n).
     *
     * 3). 如果内存放不不下，求中位数怎么办。 能做到他问这个follow up 说明这个做题速度可以 如果是单机压内存
     * 就⽤用树状数组(Binary Indexed Tree)，二分做 如果说单机存不不下就多机，也是树状数组 反正我只想到了了这个思路
     *
     * Segment Tree Solution
     * https://www.geeksforgeeks.org/efficiently-design-insert-delete-median-queries-set/
     */
    public class MoveingAverage2 {
        class Event {
            int val;
            int time;
            int size;//!!!

            public Event(int val, int time) {
                this.val = val;
                this.time = time;
                this.size = 1;//!!!
            }
        }

        /**
         * !!!
         * Must use Deque here since we need to do peekFirst() and peekLast() below
         */
        private Deque<Event> queue = new ArrayDeque<>();//!!!
        private long sum = 0;//!!!
        /**
         * count total size
         */
        private long size = 0;//!!!

        public int getNow() {
            return 0;
        }

        private boolean isExpired(int curTime, int preTime) {
            return curTime - preTime > 300;
        }

        private void removeExpiredEvent() {
            while (!queue.isEmpty() && isExpired(getNow(), queue.peekFirst().time)) {//!!!
                Event event = queue.poll();
                sum -= event.val;
                size -= event.size;//!!!update size
            }
        }

        public void record(int val) {
            Event last = queue.peekLast();//!!!
            if (getNow() - last.time < 10) {
                last.size++;
                last.val += val;
            } else {
                Event e = new Event(val, getNow());
                queue.offer(e);
            }

            size++;
            sum += val;

            removeExpiredEvent();
        }

        public double getAvg() {
            removeExpiredEvent();

            if (!queue.isEmpty()) {
                return (double) sum / size;
            }

            return 0.0;
        }

        /**
         * 如果还要getMedium呢？我说用two heap，他说太慢了因为record要o(logN)，说这个getMedium call得很少，
         * 可以直接在当前的数据结构上implement，于是其实就是求unsorted list的medium，用quick select能O(n)时间得到，
         * 面试官表示很满意
         */

        /**
         * 这个题目前面average很简单，重点是follow up找median很麻烦，楼主先提出quick select，然后说是O(N)，问能不能更快
         * ，抛出two heap解法，
         * 楼主知道two heap移除过期元素很麻烦，就先分析了假设不需要移除的情况下的复杂度，面试官表示认同。然后分析了如果要移
         * 除过期元素的话需要重新heapify，用lazy approach更好，面试官表示十分满意。
         *
         * LE_295_Find_Median_From_Data_Stream
         */

        /**
         * LI_461_Kth_Smallest_Number
         *
         * Find_Median_In_Unsorted_Array
         */


        public double getMedian(){
            ArrayList<Integer> list = new ArrayList(queue);
            int[] nums = new int[list.size()];
            for (int i = 0; i < nums.length; i++) {
                nums[i] = list.get(i);
            }

            int n = nums.length;
            if (n % 2 == 0) {
                return 0.5 * (findKth(nums, n / 2, 0, n - 1) + findKth(nums, n / 2 - 1, 0, n - 1));
            }

            return (double)findKth(nums, n / 1, 0, n - 1);
        }

        private int findKth(int[] nums, int k, int start, int end) {
            int pivot = nums[start];
            int l = start;
            int r = end;

            while (l < r) {
                while (l < r && nums[r] > pivot) {
                    r--;
                }
                while (l < r && nums[l] <= pivot) {
                    l++;
                }
                swap(nums, l, r);
            }

            swap(nums, start, r);

            if (k == r) {
                return pivot;
            } else if (k < r) {
                return findKth(nums, k, start, r - 1);
            }

            return findKth(nums, k,r + 1, end);
        }

        private void swap(int[] nums, int l, int r) {
            int temp = nums[l];
            nums[l] = nums[r];
            nums[r] = temp;
        }
    }

}
