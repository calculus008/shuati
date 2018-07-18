package lintcode;

import java.util.*;

/**
 * Created by yuank on 7/17/18.
 */
public class LI_605_Sequence_Reconstruction {
    /**
         Check whether the original sequence org can be uniquely reconstructed from the sequences in seqs.
         The org sequence is a permutation of the integers from 1 to n, with 1 ≤ n ≤ 10^4. Reconstruction
         means building a shortest common supersequence of the sequences in seqs (i.e., a shortest sequence
         so that all sequences in seqs are subsequences of it). Determine whether there is only one sequence
         that can be reconstructed from seqs and it is the org sequence.

         Example
         Given org = [1,2,3], seqs = [[1,2],[1,3]]
         Return false
         Explanation:
         [1,2,3] is not the only one sequence that can be reconstructed, because [1,3,2] is also a valid sequence that can be reconstructed.

         Given org = [1,2,3], seqs = [[1,2]]
         Return false
         Explanation:
         The reconstructed sequence can only be [1,2].

         Given org = [1,2,3], seqs = [[1,2],[1,3],[2,3]]
         Return true
         Explanation:
         The sequences [1,2], [1,3], and [2,3] can uniquely reconstruct the original sequence [1,2,3].

         Given org = [4,1,5,2,6,3], seqs = [[5,2,6,3],[4,1,5,2]]
         Return true

         Medium
     */

    public boolean sequenceReconstruction(int[] org, int[][] seqs) {
        Map<Integer, Set<Integer>> map = new HashMap<>();
        Map<Integer, Integer> indegree = new HashMap<>();

        for(int[] seq: seqs) {
            if(seq.length == 1) {
                if(!map.containsKey(seq[0])) {
                    map.put(seq[0], new HashSet<>());
                    indegree.put(seq[0], 0);
                }
            } else {
                for(int i = 0; i < seq.length - 1; i++) {
                    if(!map.containsKey(seq[i])) {
                        map.put(seq[i], new HashSet<>());
                        indegree.put(seq[i], 0);
                    }

                    if(!map.containsKey(seq[i + 1])) {
                        map.put(seq[i + 1], new HashSet<>());
                        indegree.put(seq[i + 1], 0);
                    }

                    if(map.get(seq[i]).add(seq[i + 1])) {
                        indegree.put(seq[i + 1], indegree.get(seq[i + 1]) + 1);
                    }
                }
            }
        }

        Queue<Integer> queue = new LinkedList<>();
        for(Map.Entry<Integer, Integer> entry: indegree.entrySet()) {
            if(entry.getValue() == 0) {
                queue.offer(entry.getKey());
            }
        }

        int index = 0;
        while(!queue.isEmpty()) {
            int size = queue.size();
            if(size > 1) return false;

            int curr = queue.poll();
            if(index == org.length || curr != org[index]) {
                return false;
            }

            index++;

            for(int next: map.get(curr)) {
                indegree.put(next, indegree.get(next) - 1);
                if(indegree.get(next) == 0) {
                    queue.offer(next);
                }
            }
        }
        return index == org.length && index == map.size();
    }
}
