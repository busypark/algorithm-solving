import java.util.*;

class 합승택시요금 {
    static class Edge {
        int e, fare;
        Edge(int e, int fare) {
            this.e = e;
            this.fare = fare;
        }
    }
    
    public static int solution(int n, int s, int a, int b, int[][] fares) {
        // transform edge list to adjacency list
        List<Edge>[] adjList = new List[n+1];
        for (int i=1; i<=n; i++) {
            adjList[i] = new ArrayList<>();
        }
        
        for (int[] fare : fares) {
            int Es = fare[0];
            int Ee = fare[1];
            int Ef = fare[2];
            
            adjList[Es].add(new Edge(Ee, Ef));
            adjList[Ee].add(new Edge(Es, Ef));
        }
        
        // dijkstra from S, A, B
        int[] dijkS = dijkstra(n, s, adjList);
        int[] dijkA = dijkstra(n, a, adjList);
        int[] dijkB = dijkstra(n, b, adjList);
        
        // look for min sum
        int min = Integer.MAX_VALUE;
        for (int i=1; i<=n; i++) {
            int curSum = dijkS[i] + dijkA[i] + dijkB[i];
            
            min = Math.min(min, curSum);
        }
        
        return min;
    }
    
    static int[] dijkstra(int n, int s, List<Edge>[] adjList) {
        int[] dist = new int[n+1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[s] = 0;
        
        PriorityQueue<Integer> pq = new PriorityQueue<>((n1, n2) -> Integer.compare(dist[n1], dist[n2]));
        pq.offer(s);
        
        while (!pq.isEmpty()) {
            int now = pq.poll();
            
            for (Edge next : adjList[now]) {
                int nr = dist[now] + next.fare;
                if (nr < dist[next.e]) {
                    dist[next.e] = nr;
                    pq.offer(next.e);
                }
            }
        }
        
        return dist;
    }
}