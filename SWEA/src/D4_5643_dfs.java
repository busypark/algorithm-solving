import java.io.*;
import java.util.*;

// dfs 접근 자체는 괜찮지만 '한 번에' 다 계산할 수 있을 거라고 착각했음
// 물론 한 번에 하는 것도 Set으로 가능할수도? 있지만.. 일단 플로이드 워셜부터 시도, 그 다음에 Set으로 한 번에 시도해보기

public class D4_5643_dfs {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder answer = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());
		for (int t=1; t<=T; t++) {
			int N = Integer.parseInt(br.readLine());
			int M = Integer.parseInt(br.readLine());
			
			List<Integer>[] fEdges = new List[N+1];
			for (int i=1; i<=N; i++)
				fEdges[i] = new ArrayList<>();
			
			List<Integer>[] bEdges = new List[N+1];
			for (int i=1; i<=N; i++)
				bEdges[i] = new ArrayList<>();
			
			for (int i=0; i<M; i++) {
				st = new StringTokenizer(br.readLine());
				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());
				
				fEdges[a].add(b);
				bEdges[b].add(a);
			}

			int count = 0;
			for (int start=1; start<=N; start++) {
				boolean[] visited = new boolean[N+1];
				int c1 = countChildren(N, M, fEdges, start, visited);
				Arrays.fill(visited, false);
				int c2 = countChildren(N, M, bEdges, start, visited);
				
				if (c1 + c2 == N-1)
					count ++;
			}
			
			answer.append("#").append(t).append(" ").append(count).append("\n");
		}
		
		System.out.print(answer);
	}
	
	static int countChildren(int N, int M, List<Integer>[] edges, int p, boolean[] visited) {
		int count = 0;
		if (!visited[p]) {
			visited[p] = true;
			for (int child : edges[p]) {
				if (visited[child]) continue;
				count += 1 + countChildren(N, M, edges, child, visited);
			}
		}
		
		return count;
	}
}
