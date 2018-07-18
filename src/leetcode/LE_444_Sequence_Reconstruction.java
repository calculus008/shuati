package leetcode;

import java.util.*;

/**
 * Created by yuank on 7/16/18.
 */
public class LE_444_Sequence_Reconstruction {
    /**
         Check whether the original sequence org can be uniquely reconstructed from the sequences in seqs.
         The org sequence is a permutation of the integers from 1 to n, with 1 ≤ n ≤ 104. Reconstruction
         means building a shortest common supersequence of the sequences in seqs (i.e., a shortest sequence
         so that all sequences in seqs are subsequences of it). Determine whether there is only one sequence that can be reconstructed from seqs and it is the org sequence.

         Example 1:

         Input:
         org: [1,2,3], seqs: [[1,2],[1,3]]

         Output:
         false

         Explanation:
         [1,2,3] is not the only one sequence that can be reconstructed, because [1,3,2] is also a valid sequence that can be reconstructed.
         Example 2:

         Input:
         org: [1,2,3], seqs: [[1,2]]

         Output:
         false

         Explanation:
         The reconstructed sequence can only be [1,2].
         Example 3:

         Input:
         org: [1,2,3], seqs: [[1,2],[1,3],[2,3]]

         Output:
         true

         Explanation:
         The sequences [1,2], [1,3], and [2,3] can uniquely reconstruct the original sequence [1,2,3].
         Example 4:

         Input:
         org: [4,1,5,2,6,3], seqs: [[5,2,6,3],[4,1,5,2]]

         Output:
         true
     */

     /**
      * Topological Sort
      */
     public boolean sequenceReconstruction(int[] org, List<List<Integer>> seqs) {
          if (seqs == null) {
               return false;
          }

          Map<Integer, Set<Integer>> map = new HashMap<>();//save the numbers it points to
          Map<Integer, Integer> indegree = new HashMap<>();//save indegree of a number

          /**
           * This for loop creates :
           * 1.map of indegree for each number appears in seqs
           * 2.map of mapping each number appears in seqs to the numbers it points to.
           *   It serves the same purpose of "ArrayList<DirectedGraphNode> neighbors" in DirectedGraphNode class
           */
          for (List<Integer> seq : seqs) {
               if (seq.size() == 1) {
                    map.putIfAbsent(seq.get(0), new HashSet<>());
                    indegree.putIfAbsent(seq.get(0), 0);
               } else {
                    for (int i = 0; i < seq.size() - 1; i++) {
                         map.putIfAbsent(seq.get(i), new HashSet<>());
                         indegree.putIfAbsent(seq.get(i), 0);

                         map.putIfAbsent(seq.get(i + 1), new HashSet<>());
                         indegree.putIfAbsent(seq.get(i + 1), 0);

                         if (map.get(seq.get(i)).add(seq.get(i + 1))) {//!!!
                              indegree.put(seq.get(i + 1), indegree.get(seq.get(i + 1)) + 1);
                         }
                    }
               }
          }

          /**
           * Tow places of using indgree:
           * 1.Check which node should be put into queue initially
           * 2.After update indegree, if there's 0 indegree, put element into queue
           */
          Queue<Integer> queue = new LinkedList<>();
          for (Map.Entry<Integer, Integer> entry : indegree.entrySet()) {
               if (entry.getValue() == 0) {
                    queue.offer(entry.getKey());
               }
          }

          int index = 0;
          while (!queue.isEmpty()) {
               int size = queue.size();
               if (size > 1) {
                    return false;
               }

               int cur = queue.poll();
               if (index == org.length || org[index] != cur) {
                    return false;
               }

               index++;

               if(!map.containsKey(cur)) continue; //don't forget

               for (int next : map.get(cur)) {
                    indegree.put(next, indegree.get(next) - 1);
                    if (indegree.get(next) == 0) {
                         queue.offer(next);
                    }
               }
          }

          return index == org.length && map.size() == index;
     }
}
