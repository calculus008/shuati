package Interviews.Apple;

import java.util.*;

public class MySchedulerPracticeSync {
    static class Task {
        long scheduledTime;
        Runnable task;

        public Task(long scheduledTime, Runnable task) {
            this.scheduledTime = scheduledTime;
            this.task = task;
        }
    }

    PriorityQueue<Task> q = new PriorityQueue<>((a, b) -> Long.compare(a.scheduledTime, b.scheduledTime));

    public MySchedulerPracticeSync() {
        Thread thread = new Thread(this::runScheduler);
        thread.start(); //!!!
    }

    public synchronized void schedule(long scheduleTime, Runnable task) {//synchronized!!!
        q.offer(new Task(scheduleTime, task));
        notify();  //!!!
    }

    private void executeTask(Task task) {//synchronized!!!
        new Thread(task.task).start();
    }

    private synchronized void runScheduler() {
        while (true) { //!!1
            try { //!!!
                if (q.isEmpty()) {
                    wait();
                }

                Task next = q.peek();
                long curTime = System.currentTimeMillis();

                if (next != null && next.scheduledTime <= curTime) {
                    q.poll();
                    executeTask(next);
                } else {
                    long waitTime = next.scheduledTime - curTime;
                    if (waitTime > 0) {
                        wait(waitTime);
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) {
        MySchedulerPracticeSync scheduler = new MySchedulerPracticeSync();

        scheduler.schedule(System.currentTimeMillis() + 2000, () -> System.out.println("Task 1 executed"));
        scheduler.schedule(System.currentTimeMillis() + 1000, () -> System.out.println("Task 2 executed"));
        scheduler.schedule(System.currentTimeMillis() + 3000, () -> System.out.println("Task 3 executed"));
        scheduler.schedule(System.currentTimeMillis() - 1000, () -> System.out.println("Task 4 (late) executed"));

        try {
            Thread.sleep(5000); // Sleep for 5 seconds to allow tasks to execute
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
