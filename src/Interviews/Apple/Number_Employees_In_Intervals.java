package Interviews.Apple;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

public class Number_Employees_In_Intervals {
    /**
        Your previous Plain Text content is preserved below:

        Given list of tuples :
        [
          ("e1", 1000, 1700),
          ("e2", 1500, 2000),
          ("e3", 1500, 3000),
          ("e4", 1500, 3005),
          ("e5", 2100, 3500)
        ]
        where tuple means (employee id, start_job timestamp, exit_job timestamp).
        Process this data to output list of tuples where each tuple has :
        (total number of employees, start timestamp, end timestamp)

        With above example, the result should be :
        [
          (1, 1000, 1500),
          (4, 1500, 1700),
          (3, 1700, 2000),
          (2, 2000, 2100),
          (3, 2100, 3000),
          (2, 3000, 3005),
          (1, 3005, 3500)
        ]
     **/

    /**
     * A sweep line question in disguise
     */
    static class Entity {
        int count;
        public long start;
        public long end;
    }


    static class Employee {
        public String employeeId;
        public long startTimestamp;
        public long endTimestamp;

        public Employee(String employeeId, long startTimestamp, long endTimestamp) {
            this.employeeId = employeeId;
            this.startTimestamp = startTimestamp;
            this.endTimestamp = endTimestamp;
        }
    }

    public static List<Entity> getEntity(List<Employee> employees) {
        List<Entity> res = new ArrayList<>();
        if (employees == null || employees.size() == 0) return res;

        TreeMap<Long, Integer> map = new TreeMap<>();

        for (Employee e : employees) {
            map.put(e.startTimestamp, map.getOrDefault(e.startTimestamp, 0) + 1);
            map.put(e.endTimestamp, map.getOrDefault(e.endTimestamp, 0) - 1);
        }

        /**
         * Entry in TreeMap with given example input:
         * 1000.   1
         * 1500.   3
         * 1700    -1
         * 2000    -1
         * 2100.   1
         * 3000.   -1
         * 3005.   -1
         * 3500.   -1
         */
        Iterator<Long> it = map.keySet().iterator();

        Entity preEntity = null;
        int preCount = 0;

        while (it.hasNext()) {
            long curTime = it.next();
            int curCount = map.get(curTime);

            if (preEntity != null) {
                preEntity.end = curTime;
                res.add(preEntity);
            }

            Entity curEntity = new Entity();
            curEntity.count = curCount + preCount;
            curEntity.start = curTime;

            preCount = curEntity.count;//!!!
            preEntity = curEntity;//!!!
        }

        return res;
    }

    public static void main(String[] args) {
        Employee e1 = new Employee("e1", 1000, 1700);
        Employee e2 = new Employee("e2", 1500, 2000);
        Employee e3 = new Employee("e3", 1500, 3000);
        Employee e4 = new Employee("e4", 1500, 3005);
        Employee e5 = new Employee("e5", 2100, 3500);

        List<Employee> input = new ArrayList<>();
        input.add(e1);
        input.add(e2);
        input.add(e3);
        input.add(e4);
        input.add(e5);

        List<Entity> res = getEntity(input);

        for (Entity e : res) {
            System.out.println(e.start + ", " + e.end + ", " + e.count);
        }
    }
}
