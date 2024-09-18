package src.leetcode;

import java.util.Iterator;
import java.util.PriorityQueue;

public class LE_855_Exam_Room {
    /**
     * In an exam room, there are N seats in a single row, numbered 0, 1, 2, ..., N-1.
     *
     * When a student enters the room, they must sit in the seat that maximizes the
     * distance to the closest person.  If there are multiple such seats, they sit
     * in the seat with the lowest number.  (Also, if no one is in the room, then
     * the student sits at seat number 0.)
     *
     * Return a class ExamRoom(int N) that exposes two functions: ExamRoom.seat()
     * returning an int representing what seat the student sat in, and ExamRoom.leave(int p)
     * representing that the student in seat number p now leaves the room.  It is guaranteed
     * that any calls to ExamRoom.leave(p) have a student sitting in seat p.
     *
     *
     *
     * Example 1:
     *
     * Input: ["ExamRoom","seat","seat","seat","seat","leave","seat"], [[10],[],[],[],[],[4],[]]
     * Output: [null,0,9,4,2,null,5]
     * Explanation:
     * ExamRoom(10) -> null
     * seat() -> 0, no one is in the room, then the student sits at seat number 0.
     * seat() -> 9, the student sits at the last seat number 9.
     * seat() -> 4, the student sits at the last seat number 4.
     * seat() -> 2, the student sits at the last seat number 2.
     * leave(4) -> null
     * seat() -> 5, the student sits at the last seat number 5.
     *
     * Note:
     *
     * 1 <= N <= 10^9
     * ExamRoom.seat() and ExamRoom.leave() will be called at most 10^4 times across all test cases.
     * Calls to ExamRoom.leave(p) are guaranteed to have a student currently sitting in seat number p.
     *
     * Hard
     */

    /**
     * It's an interval problem
     *
     * Need to measure the distance between seated students: O(n) is trivial, but not as fast.
     * Use PriorityQueue to store the potential candidate as interval, and also calculate the
     * candidate's mid-distance to both side.
     *
     * seat(): pq.poll() to find interval of largest distance. Split and add new intervals back to queue.
     *
     * leave(x): one seat will be in 2 intervals: remove both from pq, and merge to a new interval.
     *
     * int:
     * pq : (-1, N)
     *
     * First seat(), seat = 0, pop (-1, N)
     * pq : (-1, 0), (0, N)
     *
     * Second seat(), seat = N - 1, pop (0, N)
     * pq : (-1, 0), (0, N - 1), (N - 1, N)
     *
     * 3rd seat(), pop (0, N - 1), seat = (N - 1) / 2
     * pq: (-1, 0), (0, (N - 1) / 2), ((N - 1) / 2, N - 1), (N - 1, N)
     */
    class ExamRoom {
        class Interval {
            public int start;
            public int end;
            public int dist;

            public Interval(int start, int end) {
                this.start = start;
                this.end = end;

                /**
                 * Trick: there is no interval when adding for first student, so we need to create
                 * boundary/fake seats [-1, N], which simplifies the edge case a lot.
                 */
                if (start == -1) {//First seat() call
                    this.dist = end;
                } else if (end == N) {//Second seat() call, handles (N - 1, N)
                    this.dist = N - 1 - start;
                } else {
                    this.dist = Math.abs((end - start) / 2);
                }
            }
        }

        PriorityQueue<Interval> pq;
        int N;

        public ExamRoom(int N) {
            this.N = N;
            pq = new PriorityQueue<>((a, b) -> a.dist != b.dist ? b.dist - a.dist : a.start - b.start);
            pq.add(new Interval(-1, N));
        }

        /**
         * O(logn)
         */
        public int seat() {
            int seat = 0;
            Interval interval = pq.poll();
            if (interval.start == -1) {
                seat = 0;
            } else if (interval.end == N) {
                seat = N - 1;
            } else {
                seat = interval.start + (interval.end - interval.start) / 2;
            }

            /**
             * interval is open on both ends:  (start, end)
             */
            pq.offer(new Interval(interval.start, seat));
            pq.offer(new Interval(seat, interval.end));

            return seat;
        }

        /**
         * O(n)
         */
        public void leave(int p) {
            int s = 0, e = 0;
            Iterator<Interval> it = pq.iterator();

            while (it.hasNext()) {
                Interval cur = it.next();

                if (cur.end == p) {
                    s = cur.start;
                    it.remove();
                }

                if (cur.start == p) {
                    e = cur.end;
                    it.remove();
                }
            }

            pq.offer(new Interval(s, e));
        }
    }

/**
 * Your ExamRoom object will be instantiated and called as such:
 * ExamRoom obj = new ExamRoom(N);
 * int param_1 = obj.seat();
 * obj.leave(p);
 */
}
