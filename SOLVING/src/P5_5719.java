import java.io.*;
import java.util.*;

// 최단 경로가 여럿인 경우를 고려 안해서 좀 헤맸음. 그래도 dijkstra로 가닥을 빨리 잡아서 최초 코드는 30분만에 작성
// 50분이나 기다렸는데 시간초과..


public class P5_5719 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder answer = new StringBuilder();
		
		while (true) {
			st = new StringTokenizer(br.readLine());
			int N = Integer.parseInt(st.nextToken());
			int M = Integer.parseInt(st.nextToken());
			
			if (N == 0 && M == 0) break;

			st = new StringTokenizer(br.readLine());
			int S = Integer.parseInt(st.nextToken());
			int E = Integer.parseInt(st.nextToken());
			
			List<int[]>[] adjList = new List[N];
			for (int n=0; n<N; n++) {
				adjList[n] = new ArrayList<>();
			}
			
			for (int m=0; m<M; m++) {
				st = new StringTokenizer(br.readLine());
				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());
				int d = Integer.parseInt(st.nextToken());
				
				adjList[a].add(new int[] {b, d});
			}
			
			// first dijkstra
			int[] parents = new int[N];
			int[] dist = new int[N];
			Arrays.fill(dist, Integer.MAX_VALUE);
			dist[S] = 0;
			PriorityQueue<Integer> pq = new PriorityQueue<>((n1, n2) -> Integer.compare(dist[n1], dist[n2]));
			pq.offer(S);
			while (!pq.isEmpty()) {
				int node = pq.poll();
				if (node == E) break;
				
				for (int i=0; i<adjList[node].size(); i++) {
					int[] next = adjList[node].get(i);
					int newD = dist[node] + next[1];
					
					if (newD < dist[next[0]]) {
						dist[next[0]] = newD;
						pq.offer(next[0]);
						parents[next[0]] = node;
					}
				}
			}
			
			// remove mid edges
			int cur = E;
			while (cur != S) {
				int P = parents[cur];
				int i;
				for (i=0; i<adjList[P].size(); i++) {
					if (adjList[P].get(i)[0] == cur) {
						break;
					}
				}
				
				if (i < adjList[P].size()) {
					adjList[P].remove(i);
				}
				
				cur = parents[cur];
			}
			
			int standardCost = dist[E];
			int nowCost = standardCost;
			// dijkstra again.. continuously..
			while (standardCost == nowCost && standardCost != Integer.MAX_VALUE) {
				Arrays.fill(parents, 0);
				Arrays.fill(dist, Integer.MAX_VALUE);
				dist[S] = 0;
				pq.clear();
				pq.offer(S);
				while (!pq.isEmpty()) {
					int node = pq.poll();
					if (node == E) break;
					
					for (int i=0; i<adjList[node].size(); i++) {
						int[] next = adjList[node].get(i);
						int newD = dist[node] + next[1];
						
						if (newD < dist[next[0]]) {
							dist[next[0]] = newD;
							pq.offer(next[0]);
							parents[next[0]] = node;
						}
					}
				}
				
				// remove mid edges
				cur = E;
				while (cur != S) {
					int P = parents[cur];
					int i;
					for (i=0; i<adjList[P].size(); i++) {
						if (adjList[P].get(i)[0] == cur) {
							break;
						}
					}
					
					if (i < adjList[P].size()) {
						adjList[P].remove(i);
					}
					
					cur = parents[cur];
				}
				
				nowCost = dist[E];
			}
			
			answer.append(dist[E] == Integer.MAX_VALUE ? -1 : dist[E]).append("\n");
		}
		
		System.out.print(answer);
	}
	
}
