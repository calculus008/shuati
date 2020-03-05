package Interviews.Indeed.References;

import java.util.*;

/**
 * Created by xiangli on 5/11/17.
 */
public class JobID {
    TreeSet<Interval> expiredJobs = new TreeSet<Interval>(new Comparator<Interval>() {
        @Override
        public int compare(Interval o1, Interval o2) {
            return o1.start - o2.start;
        }
    });
    void expire(int id) {
        //insert interval
        Interval itv = new Interval(id, id);
        if (expiredJobs.size() == 0) {
            expiredJobs.add(itv);
            return;
        }
        Interval less = expiredJobs.floor(itv); // <=

        if (less != null && less.end >= id){
            return;
        }
        if (less != null && less.end + 1 == id){
            itv.start = less.start;
            expiredJobs.remove(less);
        }
        Interval higher = expiredJobs.higher(itv); // >
        if (higher != null && higher.start - 1 == id){
            itv.end = higher.end;
            expiredJobs.remove(higher);
        }

        expiredJobs.add(itv);

    }

    boolean isExpire(int id) {
        //check if it's in an interval
        Interval itv = new Interval(id, id);
        Interval less = expiredJobs.floor(itv);

        if (less != null && less.start <= id && less.end >= id){
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        JobID jb = new JobID();
        jb.expire(3);
        jb.expire(5);
        jb.expire(4);
        jb.expire(8);
        jb.expire(9);
        if (jb.isExpire(9)){
            System.out.println("expired");
        } else {
            System.out.println("no");
        }

	}
}

class Interval{
    int start;
    int end;
    public Interval(int s, int e) {
        start = s;
        end = e;
    }
}
