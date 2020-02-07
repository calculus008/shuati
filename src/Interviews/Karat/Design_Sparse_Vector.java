package Interviews.Karat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Design_Sparse_Vector {
    /**
     * Q1: 設計一個 sparse vector class
     *
     * sparseVector v = new sparseVector(100); //size constructor; size is 100.
     *     v.set(0, 1.0);
     *     v.set(3, 2.0);
     *     v.set(80,-4.5);
     *
     *     System.out.println(v.get(80)); //should print -4.5
     *     System.out.println(v.get(50)); //should print 0.0
     *
     *     try {
     *        System.out.println(v.get(101)); //error -- index out of range
     *        throw new IllegalStateException("We should not get here, an exception should have been thrown");
     *     } catch (IndexOutOfBoundsException t) {
     *        // success
     *     }
     *
     *     System.out.println(v.toString()); //should print something like [1.0, 0.0, 0.0, 2.0, 0.0, ...]  
     *
     *
     * Q2:
     *
     * Add these operations to your library: Addition, dot product, and cosine.
     * Formulae for each are provided below; we’re more interested in you writing
     * the code than whether you’ve memorized the formula. For each operation,
     * your code should throw an error if the two input vectors are not equal
     * length.
     *
     * Sample input/output:
     * //Note: This is pseudocode. Your actual syntax will vary by language.
     * v1 = new vector(5)
     * v1[0] = 4.0
     * v1[1] = 5.0
     * v2 = new vector(5)
     * v2[1] = 2.0
     * v2[3] = 3.0. From 1point 3acres bbs
     *
     * v3 = new vector(2)
     * print v1.add(v2) //should print [4.0, 7.0, 0.0, 3.0, 0.0]
     * print v1.add(v3) //error -- vector lengths don’t match
     *
     * print v1.dot(v2) //should print 10
     * print v1.dot(v3) //error -- vector lengths don’t match
     *
     * print v1.cos(v2) //should print 0.433
     * print v1.cos(v3) //error -- vector lengths don’t match
     *
     *
     * Formulae:
     * Addition
     * a.add(b) = [a[0]+b[0], a[1]+b[1], a[2]+b[2], ...]
     * Dot product
     * a.dot(b) = a[0]*b[0] + a[1]*b[1] + a[2]*b[2] + ...
     *
     * Cosine
     * a.cos(b) = a.dot(b) / (norm(a) * norm(b))
     * //norm(a) = sqrt(a[0]^2 + a[1]^2 + a[2]^2 + ...).
     */

    static class sparseVector {
        Map<Integer, Integer> map;
        int size;

        sparseVector(int s) {
            map = new HashMap<>();
            size = s;
        }

        sparseVector(int s, Map<Integer, Integer> m) {
            map = m;
            size = s;
        }

        public void set(int index, int value) {
            map.put(index, value);
        }

        public sparseVector add(sparseVector sv2) {
            if(size != sv2.size) {
                throw new IllegalArgumentException("length doesn't match");
            }
            Map<Integer, Integer> res = new HashMap<>(map);
            for(Integer key: sv2.map.keySet()) {
                res.put(key, res.getOrDefault(key, 0) + sv2.map.get(key));
            }
            sparseVector ret = new sparseVector(size, res);
            printSparseVector("after adding: ", ret);
            return ret;
        }

        public int dot(sparseVector sv2) {
            if(size != sv2.size) {
                throw new IllegalArgumentException("length doesn't match");
            }
            int res = 0;
            for(Integer key: sv2.map.keySet()) {
                res += map.getOrDefault(key, 0) * sv2.map.get(key);
            }
            System.out.println("dot = " + res);
            return res;
        }

        public double norm() {
            double res = 0;
            for(Integer value: map.values()) {
                res += value * value;
            }
            res = Math.sqrt(res);
            System.out.println("norm = " + res);
            return res;
        }

        public double cosine(sparseVector sv2) {
            if(size != sv2.size) {
                throw new IllegalArgumentException("length doesn't match");
            }
            double res = dot(sv2) / (norm() * sv2.norm());
            System.out.println("cosine = " + res);
            return res;
        }
    }

    static void printSparseVector(String s, sparseVector sv) {
        System.out.println(s);
        System.out.println(sv.size);
        printIntMap("", sv.map);
    }

    static void printArray(String s, int[] array) {
        System.out.println(s);
        for(int i: array){
            System.out.print(i + " ");
        }
        System.out.println();
    }

    static void printSet(String s, Set<String> set) {
        System.out.println(s);
        for(String i: set){
            System.out.println(i + " ");
        }
        System.out.println();
    }

    static void printList(String s, List<String> list) {
        System.out.println(s);
        for(String i: list){
            System.out.println(i + " --> ");
        }
        System.out.println();
    }

    static void printListArray(String s, List<int[]> list) {
        System.out.println(s);
        for(int[] i: list){
            printArray("",i);
        }
        System.out.println();
    }

    static void printListInt(String s, List<Integer> list) {
        System.out.println(s);
        for(Integer i: list){
            System.out.println(i + " --> ");
        }
        System.out.println();
    }

    static void printMap(String s, Map<String, List<Integer>> map) {
        System.out.println(s);
        for(String ss: map.keySet()){
            printListInt(ss, map.get(ss));
        }
        System.out.println();
    }

    static void printIntMap(String s, Map<Integer, Integer> map) {
        System.out.println(s);
        for(Integer i: map.keySet()){
            System.out.println("key: " + i + " value: " + map.get(i));
        }
        System.out.println();
    }

    // Driver Code
    public static void main(String args[])
    {
        sparseVector sv1 = new sparseVector(5);
        sv1.set(0,4);
        sv1.set(1,5);
        sparseVector sv2 = new sparseVector(5);
        sv2.set(1,2);
        sv2.set(3,3);
        sparseVector sv3 = new sparseVector(2);
        try{
            sv1.add(sv2);
            // sv1.add(sv3);
            // add(sv1, sv3);
            sv1.dot(sv2);
            // dot(sv1, sv3);
            sv1.cosine(sv2);
            sv1.cosine(sv3);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

