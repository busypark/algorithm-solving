import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// https://www.acmicpc.net/problem/17471

public class G3_17471 {
	static int N;
	static int[] pops;
	static int[][] edges;
	static int minPopulationDiff = Integer.MAX_VALUE;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		
		pops = new int[N];
		st = new StringTokenizer(br.readLine());
		for (int i=0; i<N; i++) {
			pops[i] = Integer.parseInt(st.nextToken());
		}
		
		edges = new int[N+1][];
		for (int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			final int count = Integer.parseInt(st.nextToken());
			edges[i+1] = new int[count]; // 구역번호가 바로 엣지 리스트의 번호가 되도록
			for (int j=0; j<count; j++) {
				edges[i+1][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		visited = new boolean[N+1];
		getMinPopDiff(0, Integer.MAX_VALUE);
		if (minPopulationDiff == Integer.MAX_VALUE) 
			minPopulationDiff = -1;
		
		System.out.println(minPopulationDiff);
	}
	
	static boolean[] visited;
	static boolean pathExistence;
	static void getMinPopDiff(int count, int currentPopDiff) {
		if (count == N) { // 모든 구역 다 탐색한 경우 min 갱신 및 종료
			minPopulationDiff = Math.min(minPopulationDiff, currentPopDiff);
		} else { // 아직 다 탐색하지 않음
			for (int i=1; i<=N; i++) {
				if (!visited[i]) {
					visited[i] = true;
					count++;
					pathVisited = new boolean[N-count];
					pathExistence = false;
					
					
					visited[i] = false;
					count--;
				}
			}
		}
	}
	
	static boolean[] pathVisited;
	static void examinePathExistence(int count) {
		if (count == 0) { // 나머지 지점 모두 탐색한 경우 경로가 존재하는 것이므로 pathExistence = true; 더이상 재귀하지 않아도 됨
			pathExistence = true;
		} else { // 아직 덜 탐색
			if (pathExistence) return;
			
			for (int i=0; i<pathVisited.length; i++) {
				if (!pathVisited[i]) {
					pathVisited[i] = true;
					
					pathVisited[i] = false;
				}
			}
		}
	}
}










