import java.io.*;
import java.util.*;

// 예비군에서 종이로 풀었는데 한 번에 잘 풂
// 52번줄에 node[0] 적어야 되는데 node[1] 적었다가 10번째 테케만 잘못 나와서 좀 당황...

public class D4_1238 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder answer = new StringBuilder();
		
		for (int t=1; t<=10; t++) {
			st = new StringTokenizer(br.readLine());
			int nEdge = Integer.parseInt(st.nextToken()) / 2;
			int start = Integer.parseInt(st.nextToken());
			
			List<Integer>[] adjList = new List[101];
			for (int i=1; i<=100; i++) {
				adjList[i] = new ArrayList<>();
			}
				
			st = new StringTokenizer(br.readLine());
			for (int i=0; i<nEdge; i++) {
				int from = Integer.parseInt(st.nextToken());
				int to = Integer.parseInt(st.nextToken());
				
				adjList[from].add(to);
			}
			
			boolean[] visited = new boolean[101];
			visited[start] = true;
			Queue<int[]> q = new LinkedList<>();
			q.offer(new int[] {start, 0});
			
			int endNode = -1;
			int endTime = Integer.MIN_VALUE;
			while (!q.isEmpty()) {
				int[] node = q.poll();
				
				int count = 0;
				for (int adj : adjList[node[0]]) {
					if (!visited[adj]) {
						visited[adj] = true;
						count ++;
						q.offer(new int[] {adj, node[1] + 1});
					}
				}
				
				if (count == 0) {
					if (endTime == node[1]) {
						endNode = Math.max(endNode, node[0]);
					} else if (endTime < node[1]) {
						endNode = node[0];
						endTime = node[1];
					}
				}
			}
			
			answer.append("#").append(t).append(" ").append(endNode).append("\n");
		}
		
		System.out.print(answer);
	}
}
