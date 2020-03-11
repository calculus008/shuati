package src.Interviews.Indeed;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;

public class Moving_Average_MultiThread {
    static class Event {
        int val;
        int time;

        public Event(int val, int times) {
            this.val = val;
            this.time = time;
        }
    }

    /**
     * Use JDK concurrent package for queue and integer
     */
    static Queue<Event> queue = new ConcurrentLinkedQueue<>();
    static AtomicInteger sum = new AtomicInteger(0);

    static class Cleaner implements Runnable {
        private boolean isExpired(int curTime, int preTime) {
            return curTime - preTime > 300;
        }

        private int getNow(){return 0;} //dummy method

        private void removeExpiredEvent() {
            while (!queue.isEmpty() && isExpired(getNow(), queue.peek().time)) {
                Event e = queue.poll();
                sum.addAndGet(-e.val);
            }
        }

        @Override
        public void run() {
            try {
                while (true) {
                    removeExpiredEvent();
                    sleep(1000);
                }
            } catch (Exception e) {

            }
        }
    }

    static class Cleaner1 extends Thread {
        private boolean isExpired(int curTime, int preTime) {
            return curTime - preTime > 300;
        }

        private int getNow(){return 0;} //dummy method

        private void removeExpiredEvent() {
            while (!queue.isEmpty() && isExpired(getNow(), queue.peek().time)) {
                Event e = queue.poll();
                sum.addAndGet(-e.val);
            }
        }

        @Override
        public void run() {
            try {
                while (true) {
                    removeExpiredEvent();
                    sleep(1000);
                }
            } catch (Exception e) {

            }
        }
    }

    public static class MoveingAverage1 {
        public int getNow() {
            return 0;
        }

        private boolean isExpired(int curTime, int preTime) {
            return curTime - preTime > 300;
        }


        public void record(int val) {
            Event e = new Event(val, getNow());
            queue.offer(e);
            sum.addAndGet(val);
        }

        public double getAvg() {
            if (!queue.isEmpty()) {
                return (double) sum.intValue() / queue.size();
            }

            return 0.0;
        }
    }

    public static void main(String[] args) {
        MoveingAverage1 test = new MoveingAverage1();

//        ExecutorService executor = Executors.newFixedThreadPool(4);
//        executor.execute(new Cleaner());

        Cleaner1 cleaner1 = new Cleaner1();
        cleaner1.start();
    }
}
