package Interviews.Indeed.References;

import java.util.LinkedList;
import java.util.Queue;

/*
 Given a stream of input, and a API int getNow() to get the current time stamp, Finish two methods:

1. void record(int val) to save the record.
2. double getAvg() to calculate the averaged value of all the records in 5 minutes. 

Solution:
Maintain a sliding window (queue) which stores the elements in 5 minutes. Also maintain the sum of 
the records in the window.

For the record(), add an event into the queue. Remove all expired events from the queue.
For the getAvg(), first remove the expired events from the queue, and then calculate the average. 

 */
public class Movingaverage {
	 private Queue<Event> queue = new LinkedList<>();
	    private int sum = 0;
	     
	    // record an event
	    public void record(int val) {
	        Event event = new Event(getNow(), val);
	        queue.offer(event);
	        sum += event.val;
	         
	        removeExpiredRecords();
	    }
	     
	    private int getNow() {
			return 0;
		}

		private void removeExpiredRecords() {
	        while (!queue.isEmpty() && expired(getNow(), queue.peek().time)) {
	            Event curr = queue.poll();
	            sum -= curr.val;
	        }
	    }
	    private double getAvg() {
	        removeExpiredRecords();
	        if (queue.isEmpty()) {
	            return 0;
	        } else {
	            return (double) sum / queue.size();
	        }
	    }
	                
	    private boolean expired(int currTime, int prevTime) {
	        return currTime - prevTime > 5;
	    }
	     
	    class Event {
	        int time;
	        int val;
	         
	        public Event (int time, int val) {
	            this.time = time;
	            this.val = val;
	        }
	    }
}
