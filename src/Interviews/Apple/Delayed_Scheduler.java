package Interviews.Apple;

import java.util.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.atomic.AtomicInteger;

/*
 * Simple Scheduler to kick off Runnable Tasks.
 * Scheduler kicks off the immediate next task and looks for any other task to schedule.
 * If nothing else, sleeps.
 * Tasks are schedules with past time are run immediately.
 * Clients can schedule new tasks at anytime by calling schedule(Long, Runable)
 *
 */

class Delayed_Scheduler {

    static class Task {
        private long timeToRun;
        int id;
        String name;
        Runnable job;

        public void run() {
            System.out.println(id + " running...");
        }

        public void setId(int id) {
            this.id = id;
        }

        public long getTimeToRun(){
            return timeToRun;
        }

        public void setTimeToRun(long timeToRun) {
            this.timeToRun = timeToRun;
        }

        public Task(long timeToRun, String name, Runnable job) {
            this.timeToRun = timeToRun;
            this.name = name;
            this.job = job;
            System.out.println("create new task : " + name + ", startTime : " + this.timeToRun);
        }

    }

    public static class DelayedScheduler {
        private PriorityQueue<Task> tasks;
        private final Thread taskRunnerThread;
        private final AtomicInteger id;


        /***************Please implement methods below **********************************************************************/

        public DelayedScheduler() {
            tasks = new PriorityQueue<>((a, b) -> Long.compare(a.timeToRun, b.timeToRun));
            taskRunnerThread = new Thread(new TaskRunner());
            id = new AtomicInteger(0); // just for init id field
            taskRunnerThread.start();
        }


        public void schedule(long timeToRun, String taskName, Runnable task) {
            System.out.println("schedule task " + taskName);
            Task t = new Task(timeToRun, taskName, task);
            t.setId(id.incrementAndGet());

            synchronized(DelayedScheduler.this) { // this -> delayedScheduler Instance (a)
                tasks.offer(t);
                this.notify();
            }
        }

        private class TaskRunner implements Runnable {
            public void run() {
                synchronized(DelayedScheduler.this) {  // Class instance DelayedScheduler.this -> class object ?
                    try {
                        System.out.println("run()..");
                        while (true) {
                            while (tasks.isEmpty()) {
                                DelayedScheduler.this.wait();
                            }

//                            long now = System.currentTimeMillis() / 1000 ; /// this is milli seconds but the timetoRun is seconds

                            long now = toEpoch(LocalDateTime.now());
                            Task t = tasks.peek();

                            System.out.println("top task : " + t.name + ", startTime : " + t.getTimeToRun() + ", now : " + now);

                            if (t.getTimeToRun() <= now) {
                                tasks.poll();
                                // t.job.run();  // -> directly calling run() -> this thread will be blocked since t3.run() has sleep(5 mins)

                                System.out.println("Start to run " + t.name);
                                Thread thread = new Thread(t.job);
                                thread.start();
                            } else {
                                DelayedScheduler.this.wait(t.getTimeToRun() - now);
                            }
                        }
                    } catch (InterruptedException e) {
                        //todo
                        Thread.currentThread().interrupt();
                    }

                }

            }
        }
    }

    /***************Please implement methods above **********************************************************************/

  /*
    Test code - drives the Scheduler
  */

    public static void main(String[] args) {

        DelayedScheduler scheduler = new DelayedScheduler();
        LocalDateTime dateTime = LocalDateTime.now();

        // schedule one 5 minutes away
        scheduler.schedule(toEpoch(dateTime.plusSeconds(5)),"t1",() -> System.out.println("Running thread t1"));

        // schedule one 2 minutes away
        scheduler.schedule(toEpoch(dateTime.plusSeconds(2)), "t2",() -> System.out.println("Running thread t2"));

        // schedule one 3 minute away
        scheduler.schedule(toEpoch(dateTime.plusSeconds(3)),"t4", () -> System.out.println("Running thread t4"));
        System.out.println("waiting for scheduler to finish ... Control-C to break");

        // schedule one 1 minute away that runs for 5 minutes
        scheduler.schedule(toEpoch(dateTime.plusSeconds(1)), "t3",
                () -> {
                    try {
                        System.out.println("Running thread t3 for 5 minutes");
                        Thread.sleep(300000); // sleeps 5 mins
                    } catch(Exception e) {}
                });
    }

    private static long toEpoch(LocalDateTime dt) {
        return dt.plusMinutes(5).atZone(ZoneId.of("America/Los_Angeles")).toEpochSecond();
    }
}


