import java.io.*;
import java.util.*;

public class D4_5643 {
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
			
			int[] fCount = new int[N+1];
			int[] bCount = new int[N+1];
			
			boolean[] visited = new boolean[N+1];
			countChildren(N, M, fEdges, 1, visited, fCount);
			Arrays.fill(visited, false);
			countChildren(N, M, bEdges, 1, visited, bCount);
			
			int count = 0;
			for (int i=1; i<=N; i++) {
				System.out.println(fCount[i] +" "+ bCount[i]);
				if (fCount[i] + bCount[i] == N-1) {
					count ++;
				}
			}
			
			answer.append("#").append(t).append(" ").append(count).append("\n");
		}
		
		System.out.print(answer);
	}
	
	static void countChildren(int N, int M, List<Integer>[] edges, int p, boolean[] visited, int[] count) {
		if (!visited[p]) {
			visited[p] = true;
			count[p] = edges[p].size();
			for (int child : edges[p]) {
				countChildren(N, M, edges, child, visited, count);
				count[p] += count[child];
			}
		}
	}
}
