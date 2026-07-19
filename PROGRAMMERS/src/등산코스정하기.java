import java.util.*;

class 등산코스정하기 {
    static class Edge {
        int e, w;
        Edge(int e, int w) {
            this.e = e;
            this.w = w;
        }
    }
    
    public int[] solution(int n, int[][] paths, int[] gates, int[] summits) {
        // transform edge list(paths) to adjacency list
        List<Edge>[] adjList = new List[n+1];
        for (int i=0; i<=n; i++) {
            adjList[i] = new ArrayList<>();
        }
        
        for (int[] path : paths) {
            int a = path[0];
            int b = path[1];
            int w = path[2];
            
            adjList[a].add(new Edge(b, w));
            adjList[b].add(new Edge(a, w));
        }
        
        // add root node(0) as the parent of all summits
        for (int summit : summits) {
            adjList[0].add(new Edge(summit, 0));
            adjList[summit].add(new Edge(0, 0));
        }
        
        // transform gates and summits to HashSet for retrieving by O(1)
        Set<Integer> gateSet = new HashSet<>();
        for (int gate : gates) {
            gateSet.add(gate);
        }
        
        Set<Integer> summitSet = new HashSet<>();
        for (int summit : summits) {
            summitSet.add(summit);
        }
        
        // dijkstra from root(0)
        long[] dist = new long[n+1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[0] = 0;
        
        PriorityQueue<Integer> pq = new PriorityQueue<>((n1, n2)
                                                        -> Long.compare(dist[n1], dist[n2]));
        pq.offer(0);
        
        Set<Edge>[] parent = new Set[n+1];
        for (int i=1; i<=n; i++) {
            parent[i] = new HashSet<>();
        }
        
        while (!pq.isEmpty()) {
            int now = pq.poll();
            for (Edge edge : adjList[now]) {
                long nw = Math.max(dist[now], edge.w);
                if (nw < dist[edge.e]) {
                    dist[edge.e] = nw;
                    pq.offer(edge.e);
                    
                    parent[edge.e].clear();
                    parent[edge.e].add(new Edge(now, edge.w));
                } else if (nw == dist[edge.e]) {
                    parent[edge.e].add(new Edge(now, edge.w));
                }
            }
        }
        
        // derive the answer
        PriorityQueue<int[]> solutions = new PriorityQueue<>((s1, s2)
                                         -> (s1[1] != s2[1]) ? Integer.compare(s1[1], s2[1])
                                          : Integer.compare(s1[0], s2[0]));
        nextGate:
        for (int gate : gates) {
            int intensity = Integer.MIN_VALUE;
            Set<Edge> ps = parent[gate];
            while (ps != null && !ps.isEmpty()) {
                
                intensity = Math.max(intensity, p.w);
                
                if (summitSet.contains(p.e)) {
                    System.out.println("["+p.e+", "+intensity+"]");
                    solutions.offer(new int[] {p.e, intensity});
                    continue nextGate;
                }
                
                p = parent[p.e];
            }
        }
        
        return solutions.poll();
    }
}