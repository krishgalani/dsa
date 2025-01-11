package algorithms;
import data_structures.IPQ;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
//Eager Prim, Assuming Vertices are labeled from 0 and ascending
class Prim {
    boolean[] visited;
    IPQ<Integer> ipq = new IPQ<>((a, b) -> Integer.compare(a, b));
    int[] mostPromisingEdge;
    List<List<Integer>> mstEdges;
    List<Map<Integer,Integer>> G;
    //Assume G is an adjacency list of vertex to pairs of neighboring verteces and their respective cost
    public List<List<Integer>> run(List<Map<Integer,Integer>> adj, int s){
        this.G = adj;
        final int m = G.size()-1;
        mstEdges = new ArrayList<>();
        mostPromisingEdge = new int[G.size()];
        Arrays.fill(mostPromisingEdge,-1);
        visited = new boolean[G.size()];
        int mstCost = 0;
        relaxEdgesAtNode(s);
        while(!ipq.isEmpty()){
            int v = ipq.pop();
            List<Integer> e = Arrays.asList(mostPromisingEdge[v], v);
            mstEdges.add(e);
            mstCost += G.get(mostPromisingEdge[v]).get(v);
            relaxEdgesAtNode(v);       
        }
        if(mstEdges.size() < m){
            return null;
        }
        return mstEdges;
    }
    void relaxEdgesAtNode(int u){
        visited[u] = true;
        Map<Integer,Integer> edges = G.get(u);
        for(Map.Entry<Integer, Integer> entry : edges.entrySet()){
            int v = entry.getKey();
            if(!visited[v]){
                int cost = entry.getValue();
                if (ipq.containsKey(v)) {
                    // We have an existing priority. Compare to see if the new cost is smaller.
                    if (cost < ipq.getPriority(v)) {
                        ipq.decreaseKey(v, cost);
                        mostPromisingEdge[v] = u;
                    }
                } else {
                    ipq.insert(v, cost);
                    mostPromisingEdge[v] = u;
                }
            }
        }
    }
}
