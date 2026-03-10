import java.io.*;
import java.util.*;

// 피드백 : 기태 코드를 보니 이미 최대 깊이가 2로 정해진 bfs라서 굳이 루트 노드를 push하지 않았음
//        그리고 계속 bfs를 하는 방식이 아니라 그냥 루트의 자식들에 대해서만 1회 진행(deterministic)
//        dist[] 배열을 쓴 건 약간 dijkstra 방식인데 visited[] 배열만으로도 충분했음

public class D5_5521 {
	static int N, M;
	static List[] adjEdges;
	public static void main(String[] args) throws IOException {
		final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		final StringBuilder answer = new StringBuilder();
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		final int T = Integer.parseInt(st.nextToken());
		for (int t=1; t<=T; t++) {
			// input N, M, and Edges
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			adjEdges = new List[N+1];
			for (int v=1; v<=N; v++)
				adjEdges[v] = new ArrayList<Integer>();
			
			for (int e=0; e<M; e++) {
				st = new StringTokenizer(br.readLine());
				final int n1 = Integer.parseInt(st.nextToken());
				final int n2 = Integer.parseInt(st.nextToken());
				
				adjEdges[n1].add(n2);
				adjEdges[n2].add(n1);
			}
			
			// bfs
			int inviteCount = 0;
			int[] dist = new int[N+1];
			Arrays.fill(dist, 2, N+1, Integer.MAX_VALUE);
			Deque<Integer> q = new ArrayDeque<>();
			q.add(1);
			while (!q.isEmpty()) {
				final int parent = q.pop();
				if (dist[parent] >= 3) break;
				
				final int nAdj = adjEdges[parent].size();
				for (int i=0; i<nAdj; i++) {
					final int adjNode = (int) adjEdges[parent].get(i);
					if (dist[adjNode] == Integer.MAX_VALUE && dist[parent] + 1 <= 2) {
						inviteCount++;
					}
					dist[adjNode] = Math.min(dist[adjNode], dist[parent] + 1);
					q.add(adjNode);
				}
			}
			
			answer.append("#").append(t).append(" ").append(inviteCount).append("\n");
		}
		
		System.out.print(answer);
	}
}
