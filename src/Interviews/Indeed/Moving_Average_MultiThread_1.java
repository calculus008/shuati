package src.Interviews.Indeed;

import java.util.LinkedList;
import java.util.Queue;

import static java.lang.Thread.sleep;

public class Moving_Average_MultiThread_1 {
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
    static Queue<Event> queue = new LinkedList<>();
    static int sum = 0;

    static class Cleaner implements Runnable {
        private boolean isExpired(int curTime, int preTime) {
            return curTime - preTime > 300;
        }

        private int getNow(){return 0;} //dummy method

        private synchronized void removeExpiredEvent() {
            while (!queue.isEmpty() && isExpired(getNow(), queue.peek().time)) {
                Event e = queue.poll();
                sum -= e.val;
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

        private synchronized void removeExpiredEvent() {
            while (!queue.isEmpty() && isExpired(getNow(), queue.peek().time)) {
                Event e = queue.poll();
                sum -= e.val;
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


        public synchronized void record(int val) {
            Event e = new Event(val, getNow());
            queue.offer(e);
            sum += val;
        }

        public synchronized double getAvg() {
            while (!queue.isEmpty() && isExpired(getNow(), queue.peek().time)) {
                Event e = queue.poll();
                sum -= e.val;
            }

            if (!queue.isEmpty()) {
                return (double) sum / queue.size();
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
