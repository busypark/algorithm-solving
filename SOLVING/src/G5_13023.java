import java.io.*;
import java.util.*;

// 모든 점에서 dfs 시작하는 방식으로 짰는데, 처음엔 시간초과 날 것 같아서 다른 묘수가 있나 싶었지만 통과. 대략 25분 소요
// 피드백 : 

public class G5_13023 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		
		List<Integer>[] adjList = new List[N];
		for (int n=0; n<N; n++) {
			adjList[n] = new ArrayList<>();
		}
		
		for (int m=0; m<M; m++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			
			// bi-directed
			adjList[a].add(b);
			adjList[b].add(a);
		}
		
		visited = new boolean[N];
		result = false;
		
		for (int i=0; i<N; i++) {
			if (result) break;
			
			Arrays.fill(visited, false);
			visited[i] = true;
			nFriend = 1; // including i
				
			dfs(adjList, i);
		}
		
		System.out.println(result ? 1 : 0);
	}
	
	static boolean[] visited;
	static boolean result;
	static int nFriend;
	static void dfs(List<Integer>[] adjList, int cur) {
		if (nFriend == 5) {
			result = true;
			
			return;
		}
		
		for (int next : adjList[cur]) {
			if (!visited[next]) {
				nFriend ++;
				visited[next] = true;
				dfs(adjList, next);
				nFriend --;
				visited[next] = false;
			}
		}
	}
}
