import java.util.*;

public class 부대복귀 {
    public static int[] solution(int n, int[][] roads, int[] sources, int destination) {
        // transform roads from edge list to adj list
    	List<Integer>[] adj = new List[n+1];
    	for (int i=1; i<=n; i++) {
    		adj[i] = new ArrayList<>();
    	}
    	
    	for (int[] road : roads) {
    		// add as bidirectional adj list
    		adj[road[0]].add(road[1]);
    		adj[road[1]].add(road[0]);
    	}
    	
    	// bfs from destination to all nodes
    	int[] dist = new int[n+1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[destination] = 0;
        
        Queue<Integer> q = new LinkedList<>();
        q.offer(destination);
        while (!q.isEmpty()) {
        	int now = q.poll();
        	for (int next : adj[now]) {
        		int nd = dist[now] + 1;
        		if (nd < dist[next]) {
        			dist[next] = nd;
        			q.offer(next);
        		}
        	}
        }
        
        // extract answer
    	int[] answer = new int[sources.length];
    	for (int i=0; i<sources.length; i++) {
    		answer[i] = (dist[sources[i]] == Integer.MAX_VALUE)? -1 : dist[sources[i]];
    	}
    	
        return answer;
    }
	
	public static void main(String[] args) {
		int[] answer = solution(3, new int[][] {{1, 2}, {2, 3}}, new int[] {2, 3}, 1);
		for (int x : answer) // 1 2
			System.out.print(x+" ");
		System.out.println("");
		
		answer = solution(5, new int[][] {{1, 2}, {1, 4}, {2, 4}, {2, 5}, {4, 5}}, new int[] {1, 3, 5}, 5);
		for (int x : answer) // 2 -1 0
			System.out.print(x+" ");
		System.out.println("");
	}
}
