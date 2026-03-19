import java.io.*;
import java.util.*;

public class G3_1238 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		// input N, M, X
		st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		int X = Integer.parseInt(st.nextToken());
		
		// initialize adjList
		List<Edge>[] adjList = new List[N+1];
		for (int i=1; i<=N; i++) {
			adjList[i] = new ArrayList<>();
		}
		
		// input weighted edges as adjacency list
		for (int i=0; i<M; i++) {
			st = new StringTokenizer(br.readLine());
			int nStart = Integer.parseInt(st.nextToken());
			int nEnd = Integer.parseInt(st.nextToken());
			int time = Integer.parseInt(st.nextToken());
			
			adjList[nStart].add(new Edge(nEnd, time));
		}
		
		// from X
		Dijk fromX = dijkstraFromX(N, M, X, adjList);
		
		// to X
		
		
	}
	
	static Dijk dijkstraFromX(int N, int M, int X, List<Edge>[] adjList) {
		Dijk dijk = new Dijk(N);
		dijk.costs[X] = 0;
		boolean[] confirmed = new boolean[N+1];
		PriorityQueue<Node> pq = new PriorityQueue<>();
		pq.add(new Node(X, 0));
		while (!pq.isEmpty()) {
			Node node = pq.poll();
			
			if (dijk.costs[node.n] < node.cost) {
				continue;
			}
			
			dijk.costs[node.n] = node.cost;
			confirmed[node.n] = true;
			
			for (Edge adj : adjList[node.n]) {
				if (!confirmed[adj.end]) {
					dijk.costs[adj.end] = Math.min(dijk.costs[adj.end], dijk.costs[node.n] + adj.cost);
					pq.add(new Node(adj.end, dijk.costs[adj.end]));
				}
			}
		}
		
		return dijk;
	}
	
	static class Dijk {
		int[] costs;
		
		Dijk(int N) {
			costs = new int[N+1];
			Arrays.fill(costs, Integer.MAX_VALUE);
		}
	}
}

class Node implements Comparable<Node> {
	int n, cost;
	Node(int n, int cost) {
		this.n = n;
		this.cost = cost;
	}
	
	@Override
	public int compareTo(Node n) {
		return this.cost - n.cost;
	}
}

class Edge {
	int end, cost;
	Edge(int end, int cost) {
		this.end = end;
		this.cost = cost;
	}
}
