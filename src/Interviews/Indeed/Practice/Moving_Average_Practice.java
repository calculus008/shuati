package Interviews.Indeed.Practice;

import java.util.*;

public class Moving_Average_Practice {
    class Event {
        int val;
        int time;

        public Event(int val, int time) {
            this.val = val;
            this.time = time;
        }
    }

    Queue<Event> q = new LinkedList<>();

    public int getNow() {return 0;}

    int sum = 0;

    private boolean isExpired(int preTime) {
        return getNow() - preTime > 300;
    }

    private void removeExpired() {
        while (!q.isEmpty() && isExpired(q.peek().time)) {
            Event e = q.poll();
            sum -= e.val;
        }
    }

    public void record(int val) {
        removeExpired();

        Event e = new Event(getNow(), val);
        q.offer(e);
        sum += val;
    }

    public double getAvg() {
        removeExpired();

        return q.size() == 0 ? 0.0 : (double)sum / q.size();
    }

    /**
     * -----------
     * Follow up 1
     * 如果对record和getaverage的性能要求特别⾼高，不想出现突刺刺情况，就是某个请求要等⽐比较久
     * 可以把removeExpired函数拿出来专⻔门⽤用⼀一个线程去跑，每秒调⽤用⼀一次
     */

    /**
     * ------------
     * Follow up 2
     * memory不够大怎么办（数据点非常密集，5分钟就把内存爆了）
     */
    class Event1 {
        int val;
        int time;
        int size;

        public Event1 (int val, int time) {
            this.val = val;
            this.time = time;
            this.size = 1;
        }
    }

    Deque<Event1> q1 = new ArrayDeque<>();

    long sum1 = 0;
    long size = 0;

    public void removeExpired1() {
        while (!q1.isEmpty() && isExpired(q1.peekFirst().time)) {
            Event1 e = q1.pollFirst();
            sum -= e.val;
            size -= e.size;
        }
    }

    public void record1(int val) {
        removeExpired1();

        if (!q1.isEmpty()) {
            Event1 last = q1.peekLast();
            if (getNow() - last.time < 10) {
                last.val += val;
                last.size++;
            } else {
                Event1 e = new Event1(val, getNow());
                q1.offer(e);
            }
        } else {
            Event1 e = new Event1(val, getNow());
            q1.offer(e);
        }

        sum += val;
        size++;
    }

    public double getAvg1() {
        removeExpired1();

        return q1.isEmpty() ? 0.0 : (double)sum /size;
    }

    /**
     * ------------
     * Follow up 3
     * getMedian(), quickSelect, O(n)
     *
     * Only for case that memory is enough (1st case)
     */
    public double getMedian() {
        removeExpired();

        if (q.isEmpty()) return 0.0;

        int[] nums = new int[q.size()];
        int i = 0;
        for (Event e : q) {
            nums[i++] = e.val;
        }

        int n = nums.length;
        if (n % 2 == 0) {
            return (quickSelect(nums, n / 2, 0, n - 1) + quickSelect(nums, n / 2 - 1, 0, n - 1)) * 0.5;
        }

        return quickSelect(nums, n / 2, 0, n - 1);
    }

    public int quickSelect(int[] nums, int k, int start, int end) {
        if (start >= end) {
            return nums[start];
        }

        int l = start;
        int r = end;
        int pivot = nums[(start + end) / 2];

        while (l <= r) {
            while (l <= r && nums[l] < pivot) {
                l++;
            }

            while (l <= r && nums[r] > pivot) {
                r--;
            }

            if (l <= r) {
                int temp = nums[l];
                nums[l] = nums[r];
                nums[r] = temp;

                l++;
                r--;
            }
        }

        //!!!
        if (r >= k && r >= start) {
            return quickSelect(nums, k, start, r);
        } else if (l <= k && l <= end) {
            return quickSelect(nums, k, l, end);
        } else {
            return nums[k];
        }
    }

    private void swap(int[] nums, int l, int r) {
        int temp = nums[l];
        nums[l] = nums[r];
        nums[r] = temp;
    }
}
