import java.io.*;
import java.util.*;

// 모든 점에서 dfs 시작하는 방식으로 짰는데, 처음엔 시간초과 날 것 같아서 다른 묘수가 있나 싶었지만 통과. 대략 25분 소요
// 피드백 : iterator 순회 -> index 순회로 바꾸니 무려 4MB 절약
// 피드백 : 백트래킹 성질에 따라 굳이 Arrays.fill하지 않고 dfs 이후에 노드 하나만 복원해주면 됨 -> 1MB 절약
// 왜 Arrays.fill이 메모리까지 절약해주는가 : 연산량이 많아질 수록 미리 heap을 끌어오는 JVM 특성때문
// 굳이 순회용 int i, j를 static으로 뺄 필요는 없음. 논리적 오류를 검출하기도 힘들고, 메모리 절약도 겨우 20KB

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
			
			//Arrays.fill(visited, false);
			visited[i] = true;
			nFriend = 1; // including i
				
			dfs(adjList, i);
			visited[i] = false; // 굳이 배열 전체를 false로 초기화하지 않음. 백트래킹의 성질
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
		
		//for (int next : adjList[cur]) {
		for (int i=0; i<adjList[cur].size(); i++) { // iterator 대신 전통적인 index 기준 순회
			int next = adjList[cur].get(i);
			
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
