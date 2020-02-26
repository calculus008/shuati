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
         * 可以直接在当前的数据结构上implement，于是其实就是求unsorted list的medium，用quick select能O(n)时间得到，面试官表示很满意
         */

        /**
         * LI_461_Kth_Smallest_Number
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
