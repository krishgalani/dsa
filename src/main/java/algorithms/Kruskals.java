package algorithms;

import java.util.ArrayList;
import java.util.List;
import data_structures.UnionFind;
import java.util.Arrays;

public class Kruskals {    
    //Assume edges are sorted
    public int[][] run(int[][] edges, int nVertices){
        int[][] ret = new int[nVertices-1][2];
        int edgeIndex = 0;
        UnionFind uf = new UnionFind(nVertices);
        for(int[] edge: edges){
            if(uf.union(edge[0],edge[1])) ret[edgeIndex++] = new int[]{edge[0],edge[1]};
        }
        return ret;
    }
}
