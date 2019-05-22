package Interviews.Facebook;

import java.util.*;

public class Sparse_Vector_Dot_Product {
    /**
     * sparse vector dot product
     * 给的是两个vector<pair<index， value>>，然后楼主先说hashmap, 要求O（1）space，楼主说排序后双指针。他问有啥corner case，
     * 我说如果一个贼大，可以用binary search，
     *
     * https://www.mathsisfun.com/algebra/vectors-dot-product.html
     * https://www.careercup.com/question?id=5678837729853440
     * https://stackoverflow.com/questions/32753310/how-to-get-dot-product-of-two-sparsevectors-in-omn-where-m-and-n-are-the-nu
     * https://leetcode.com/discuss/interview-question/algorithms/124823/dot-product-of-sparse-vector
     * https://www.glassdoor.com/Interview/Given-two-sparse-Vectors-compute-the-Dot-Product-Input-Format-The-first-line-will-contain-two-numbers-k-and-n-which-QTN_889750.htm
     * https://algs4.cs.princeton.edu/35applications/SparseVector.java.html
     */

    /**
     * Keep a hashtable (Call it h)
     * Keep a sum which represents the dot product value (Call it s)
     *
     * Now, the idea is that we will add to h whatever mapping we encounter when reading the lines.
     * If there is no such mapping for the vector position, we will add the mapping. if there is,
     * then we simply multiply the value by the mapped value and then add it to the sum (s)
     *
     * Return s.
     *
     * For example, we first add the key value pair <1, 4>
     * When we encounter <1, 7>, we see that there already exists a mapping for the key <1>
     * So we look at the value to that key (4) and multply it by our current value (7) and add
     * to the sum. Same thing happens when <5, 3> is originally added - nothing happens, but
     * when we see that we can add <5, 1>, we multply 3 and 1 and add to the sum.
     */
    public class SparseVectorDotProduct {

        public void main(String args[]){

            Scanner in = new Scanner(System.in);
            String noElements = in.nextLine();
            String[] kn = noElements.split("\\s+");
            int firstVector = Integer.parseInt(kn[0]);
            int secondVector = Integer.parseInt(kn[1]);

            Map<Integer, Integer> map = new HashMap<Integer,Integer>();

            int result = 0;

            for(int i=0; i<firstVector; i++) {
                String line = in.nextLine();
                String[] split = line.split("\\s+");
                int key = Integer.parseInt(split[0]);
                int value = Integer.parseInt(split[1]);
                map.put(key, value);
            }

            for(int i=0; i<secondVector; i++) {
                String line = in.nextLine();
                String[] split = line.split("\\s+");
                int key = Integer.parseInt(split[0]);
                int value = Integer.parseInt(split[1]);
                if(map.containsKey(key) && map.get(key)!=null) {
                    int tempvalue = map.get(key) * value;
                    result += tempvalue;
                }
            }

            System.out.print(result);
        }

    }

    /**
     * Require O(1) space
     *
     * l1 = v1.size()
     * l2 = v2.size()
     *
     * 1.Sort two vectors based on its index. O(nlogn + mlogm) (or vectors are already sorted)
     * 2.Two pointers, each one for a vector, traverse the two vectors, when p1.idx == p2.idx,
     *   sum += p1.val + p2.val
     * 3.Corner case :
     *   if l1 is much bigger than l2 or the other way around, scan the shorter one, say l1,
     *   for each idx in l1, do binary search in v2. If found, calculate.
     *   O(l1 * log(l2))
     */

    class Pair {
        int idx;
        int val;

        public Pair(int idx, int val) {
            this.idx = idx;
            this.val = val;
        }
    }

    public int dotProduct(List<Pair> v1, List<Pair> v2) {
        if (v1 == null || v2 == null || v1.size() == 0 || v2.size() == 0) return 0;

        int l1 = v1.size();
        int l2 = v2.size();

        Collections.sort(v1, (a, b) -> a.idx - b.idx);
        Collections.sort(v2, (a, b) -> a.idx - b.idx);

        int p1 = 0;
        int p2 = 0;

        int sum = 0;

        while (p1 < l1 && p2 < l2) {
            Pair elem1 = v1.get(p1);
            Pair elem2 = v2.get(p2);

            if (elem1.idx == elem2.idx) {
                sum += elem1.val * elem2.val;
                p1++;
                p2++;
            } else if (elem1.idx < elem2.idx) {
                p1++;
            } else {
                p2++;
            }
        }

        return sum;
    }
}
