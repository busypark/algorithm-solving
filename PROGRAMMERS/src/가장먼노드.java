import java.util.*;

public class 가장먼노드 {
    public static int solution(int n, int[][] edge) {
        List<Integer>[] adj = new List[n+1];
    	int E = edge.length;
        for (int e=0; e<E; e++) {
        	int[] ed = edge[e];
        	if (adj[ed[0]] == null) {
        		adj[ed[0]] = new ArrayList<>();
        	} // a->b
        	
        	adj[ed[0]].add(ed[1]);
        	
        	if (adj[ed[1]] == null) {
        		adj[ed[1]] = new ArrayList<>();
        	} // b->a
        	
        	adj[ed[1]].add(ed[0]);
        } // adj[i] == null if it has no edge
        
        int[] distArr = new int[n+1];
        Arrays.fill(distArr, Integer.MAX_VALUE);
        distArr[1] = 0;
        Queue<Integer> q = new LinkedList<>();
        q.offer(1);

        PriorityQueue<Integer> distPQ = new PriorityQueue<>
        	((a, b) -> Integer.compare(distArr[b], distArr[a]));
        while (!q.isEmpty()) {
        	int now = q.poll();
        	distPQ.offer(now);
        	
        	if (adj[now] == null) continue; // no edge for 'now'
        	int adjE = adj[now].size();
        	for (int e=0; e<adjE; e++) {
        		int next = adj[now].get(e);
        		if (distArr[next] == Integer.MAX_VALUE) {
        			q.offer(next);
        			distArr[next] = distArr[now] + 1;
        		}
        	}
        }
        
        int maxDist = distArr[distPQ.peek()];
        int answer = 0;
        while (!distPQ.isEmpty() && distArr[distPQ.poll()] == maxDist) {
        	answer ++;
        }
    	
        return answer;
    }
    
    public static void main(String args[]) {
    	System.out.println(solution(6, new int[][] {
    		{3, 6}, 
    		{4, 3},
    		{3, 2},
    		{1, 3},
    		{1, 2},
    		{2, 4},
    		{5, 2}
    		})); // 3 expected
    }
}
