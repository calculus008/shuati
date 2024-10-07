package Interviews.Apple;

import java.util.*;

public class SimpleSchedulerWithSyncPractice {
    static class Task {
        long scheduledTime;
        Runnable task;

        public Task(long scheduledTime, Runnable task) {
            this.scheduledTime = scheduledTime;
            this.task = task;
        }
    }

    PriorityQueue<Task> q = new PriorityQueue<>((a, b) -> Long.compare(a.scheduledTime, b.scheduledTime));
    private boolean isRunning = true;

    public SimpleSchedulerWithSyncPractice() {
        Thread thread = new Thread(this::runScheduler);
        thread.start(); //!!!
    }

    public synchronized void schedule(long scheduleTime, Runnable task) {//synchronized!!!
        q.offer(new Task(scheduleTime, task));
        notify();  //!!!
    }

    public synchronized void stop() {
        isRunning = false;
        notify();
    }

    private void executeTask(Task task) {//synchronized!!!
        new Thread(task.task).start();
    }

    private synchronized void runScheduler() {
        while (isRunning) { //!!1
            try { //!!!
                if (q.isEmpty() && isRunning) {
                    wait();
                }

                Task next = q.peek();
                long curTime = System.currentTimeMillis();

                if (next != null && next.scheduledTime <= curTime) {
                    q.poll();
                    executeTask(next);
                } else {
                    if (next != null) {//!!!!
                        long waitTime = next.scheduledTime - curTime;
                        if (waitTime > 0) {
                            wait(waitTime);
                        }
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("Scheduler stopped");
    }

    public static void main(String[] args) {
        SimpleSchedulerWithSyncPractice scheduler = new SimpleSchedulerWithSyncPractice();

        scheduler.schedule(System.currentTimeMillis() + 2000, () -> System.out.println("Task 1 executed"));
        scheduler.schedule(System.currentTimeMillis() + 1000, () -> System.out.println("Task 2 executed"));
        scheduler.schedule(System.currentTimeMillis() + 3000, () -> System.out.println("Task 3 executed"));
        scheduler.schedule(System.currentTimeMillis() - 1000, () -> System.out.println("Task 4 (late) executed"));

        try {
            Thread.sleep(5000); // Sleep for 5 seconds to allow tasks to execute
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        scheduler.stop();
    }
}
