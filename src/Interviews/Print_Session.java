package Interviews;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class Print_Session {
  /**
   * Phone Interview with Palo Alto Network 2019-03-14
   *
   * Given log file, each line is like :
   *  UserId, Timestamp
   *
   * Session: Collection of consecutive user visits where gap between consecutive visits < 1 hr.
   *
   * Output:  For the session with max # of visits, print in O(1) time,
   * UserId, # of visits, Timestamp of the last visit.
   **/

  class Solution {
      class Pair {
          String id;
          int timestamp;
      }

      class Session {
          String id;
          int visits;
          int lastVisit;


          public Session(String id, int visits, int lastVisits) {
              this.id = id;
              this.visits = visits;
              this.lastVisit = lastVisit;
          }
      }

      Map<String, Session> map;

      PriorityQueue<Session> pq = new PriorityQueue<>((a, b) -> b.visits - a.visits);

      public void printSesseion(List<Pair> log) {
          map = new HashMap<>();
          for (Pair p : log) {
              if(!map.containsKey(p.id)) {
                  createSession(p);
                  continue;
              }

              Session cur = map.get(p.id);
              if (isSameSession(cur.lastVisit, p.timestamp)) {
                  cur.lastVisit = p.timestamp;
                  cur.visits++;
              } else {
                  createSession(p);
              }
          }

          Session res = pq.peek();
          System.out.println(res.id + ", " + res.visits + ", " + res.lastVisit);
      }

      private void createSession(Pair p) {
          Session next = new Session(p.id, 1, p.timestamp);
          map.put(p.id, next);
          pq.offer(next);
      }

      private boolean isSameSession(int a, int b) {
          //check if difference between b and a is more than 1 hour
          //implemenation depends on timestamep format.
          return true;
      }
  }
}
