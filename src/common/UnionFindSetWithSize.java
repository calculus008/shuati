package common;

import java.util.Arrays;

/**
 * Union Find Set that keeps the info of size for each connected component
 */
public class UnionFindSetWithSize {
    int[] id;
    int[] size;

    public UnionFindSetWithSize(int size) {
        this.id = new int[size];
        this.size = new int[size];
        for(int i = 0; i < size; i++) id[i] = i;
        Arrays.fill(this.size, 1);
    }

    int find(int i) {
        while(id[i] != i) {
            id[i] = id[id[i]];
            i = id[i];
        }
        return i;
    }

    void union(int i, int j) {
        int p1 = find(i);
        int p2 = find(j);
        if(p1 != p2) {
            id[p1] = p2;
            size[p2] += size[p1];
        }
    }
}
