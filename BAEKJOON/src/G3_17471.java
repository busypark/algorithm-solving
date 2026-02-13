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
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in)) ;
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
		getMinPopDiff(0, 1);
		if (minPopulationDiff == Integer.MAX_VALUE) 
			minPopulationDiff = -1;
		
		System.out.println(minPopulationDiff);
	}
	
	static boolean[] visited;
	static boolean pathExistence = false;
	static void getMinPopDiff(int count, int node) {
		int[] nodes = edges[node];
		
		
	}
	
	static boolean[] pathVisited;
	static void examinePathExistence(int count, int node) {
		if (count == 0) { // 나머지 지점 모두 탐색한 경우 경로가 존재하는 것이므로 pathExistence = true; 더이상 재귀하지 않아도 됨
			pathExistence = true;
		} else { // 아직 덜 탐색
			int[] nodes = edges[node];
			for (int i=0; i<nodes.length; i++) {
				if (pathExistence) return; // 이미 경로 하나라도 발견되었으면 재귀할 필요 없음
				
				if (!visited[nodes[i]] && !pathVisited[nodes[i]]) {
					pathVisited[nodes[i]] = true;
					examinePathExistence(count-1, nodes[i]);
					pathVisited[nodes[i]] = false;
				}
			}
		}
	}
}










