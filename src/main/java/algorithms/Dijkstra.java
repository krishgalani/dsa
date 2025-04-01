package algorithms;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;
import java.util.List;
import data_structures.IPQ;
import data_structures.Pair;

class Dijkstra {
    public static Map<Integer,Integer> run(int[][] g,int n,int src){
        //init
        Map<Integer,Integer> dist = new HashMap<>();
        List<List<Pair<Integer>>> adj = new ArrayList<>();
        boolean[] visited = new boolean[n+1];
        int[] prev = new int[n+1];
        prev[src] = src;
        for(int i=0; i<=n; i++){
            adj.add(new ArrayList<>());
            if(i == src) {
                dist.put(i,0);
            } else {
                dist.put(i,Integer.MAX_VALUE);
                prev[i] = -1;
            }
        }
        for(int[] uvw : g){
            int u = uvw[0];
            int v = uvw[1];
            int w = uvw[2];
            adj.get(u).add(new Pair<Integer>(v,w));
        }
        IPQ<Integer> pq = new IPQ<>(Comparator.naturalOrder());
        pq.insert(src,0);
        while(!pq.isEmpty()){
            int u = pq.pop();
            int costToU = dist.get(u);
            visited[u] = true;
            for(Pair<Integer> vw : adj.get(u)){
                int v = vw.first;
                int w = vw.second;
                if(visited[v]) continue;
                if(costToU + w < dist.get(v)){
                    dist.put(v,costToU + w);
                    prev[v] = u;
                    if(pq.containsKey(v)){
                        pq.decreaseKey(v, dist.get(v));
                    } else {
                        pq.insert(v,dist.get(v));
                    }
                }
            }
        }
        return dist;
    }
}