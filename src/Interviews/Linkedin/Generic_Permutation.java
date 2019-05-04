package Interviews.Linkedin;

import java.util.*;

/**
 * Generic Type is important in LinkedIn
 *
 * https://www.cs.scranton.edu/~mccloske/courses/cmps144/java_generic_sorting.html
 *
 **/

/**
 * !!!
 * <T extends Comparable<T>>
 */
public class Generic_Permutation<T extends Comparable<T>> {

    public static void main(String[] args) {
        Generic_Permutation<Integer> gp = new Generic_Permutation<>();
        Integer[] input = new Integer[]{1,3, 5, 7};
        List<List<Integer>> res = gp.permutate(input);

        for (List l : res) {
            System.out.println(Arrays.toString(l.toArray()));
        }
    }

    public List<List<T>> permutate(T[] arr) {
        List<List<T>> res = new ArrayList<List<T>>();
        List<T> list = new ArrayList<T>();

        /**
         *  Arrays.sort(Object[]) method requires the object type to implement the
         *  Comparable interface so that the array can be sorted according to natural
         *  ordering of its elements. The natural ordering is defined by implementation
         *  of the compareTo() method which determines how the current object (obj1)
         *  is compared to another (obj2) of the same type. The order is based on return
         *  value (an integer number, say x) of the compareTo() method:
         *
         *     obj1 > obj2 if x > 0.
         *     obj1 < obj2 if x < 0.
         *     obj1 equals obj2 if x = 0.
         */
//        Arrays.sort(arr, new Comparator<T>() { // Actually, no need to use this comparator
//            public int compare(T t1, T t2) {
//                return t1.compareTo(t2);
//            }
//        });

        Arrays.sort(arr, (a, b) -> a.compareTo(b));

//        Arrays.sort(arr);

        /**
         * Set saves index, therefore its type is Integer, not T
         */
        Set<Integer> visited = new HashSet<Integer>();
        helper(res, list, visited, arr);
        return res;
    }

    public void helper(List<List<T>> res, List<T> list, Set<Integer> visited, T[] arr) {
        if (arr.length == list.size()) {
            res.add(new ArrayList<T>(list));
            return;
        }

        for (int i = 0; i < arr.length; i++) {
            if (visited.contains(i) || (i != 0 && arr[i - 1] == arr[i] && !visited.contains(i - 1))) {
                continue;
            }

            visited.add(i);
            list.add(arr[i]);

            helper(res, list, visited, arr);

            list.remove(list.size() - 1);
            visited.remove(i);
        }
    }
}
