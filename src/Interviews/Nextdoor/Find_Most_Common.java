package Interviews.Nextdoor;

import java.util.*;

public class Find_Most_Common {
    /**
     * Find the most common elements in a list.
     */
    public static List<Integer> most_common(int[] A) {
        HashMap<Integer, Integer> count_map = new HashMap();
        int max_count = 0;

        for (int number : A) {
            int count = count_map.getOrDefault(number, 0) + 1;
            count_map.put(number, count);
            max_count = Math.max(max_count, count);
        }

        List<Integer> result = new ArrayList();
        for (Integer n : count_map.keySet()) {
            if (count_map.get(n) == max_count) {
                result.add(n);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        List<Integer> result = most_common(new int[]{2, 2, 3, 3, 4, 4, 1, 0});
        System.out.println(Arrays.toString(result.toArray()));
        System.out.println("Hello World!");
    }
}
