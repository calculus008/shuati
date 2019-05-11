package Interviews.Amazon;

import java.util.TreeMap;
import java.util.TreeSet;

public class TreeMap_Test {
    public static void main(String[] args) {
        TreeMap<Integer, String> map = new TreeMap();

        map.put(100, "100");
        map.put(3, "3");
        map.put(1, "1");
        map.put(50, "50");
        map.put(40, "40");

        for (String s : map.values()) {
            System.out.println(s);
        }

        TreeSet<Integer> set = new TreeSet<>();
        set.add(100);
        set.add(40);
        set.add(55);
        set.add(20);
        set.add(1);
        set.add(34);
        set.add(200);

        for (int n : set) {
            System.out.println(n);
        }
    }

}
