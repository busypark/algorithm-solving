import java.io.*;
import java.util.*;

public class S1_1325 {	
	public static void main(String[] args) throws IOException {
		final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		// input N and M
		st = new StringTokenizer(br.readLine());
		final int N = Integer.parseInt(st.nextToken());
		final int M = Integer.parseInt(st.nextToken());
		
		// initialize edges
		List<Integer>[] edges = new List[N+1];
		for (int i=1; i<=N; i++) {
			edges[i] = new ArrayList<>();
		}
		
		// add edges into the directed graph and edge matrix
		for (int i=0; i<M; i++) {
			st = new StringTokenizer(br.readLine());
			final int trust = Integer.parseInt(st.nextToken());
			final int trusted = Integer.parseInt(st.nextToken());
			
			edges[trusted].add(trust);
		}
		
		// bfs
		int maxCount = 0;
		int[] counts = new int[N+1];
		//List<Integer> maxNodes = new ArrayList<>();
		for (int i=1; i<=N; i++) {
			int count = 1;
			
			Queue<Integer> q = new ArrayDeque<>();
			q.add(i);
			boolean[] visited = new boolean[N+1];
			visited[i] = true;
			while (!q.isEmpty()) {
				int node = q.poll();
				for (int edge : edges[node]) {
					if (!visited[edge]) {
						visited[edge] = true;
						count ++;
						q.add(edge);
					}
				}
			}
			
			counts[i] = count;
			maxCount = Math.max(maxCount, count);
		}
		
		// print
		final StringBuilder answer = new StringBuilder();
		for (int i=1; i<=N; i++) {
			if (counts[i] == maxCount)
				answer.append(i).append(" ");
		}
		
		System.out.println(answer);
	}
}
