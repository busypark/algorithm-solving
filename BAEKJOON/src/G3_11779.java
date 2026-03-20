import java.io.*;
import java.util.*;

/*
 * 무조건 Min으로 업데이트하고 큐에 다 넣지 말고, 큐에 넣기 전에 검사를 해야 메모리 상한을 안 넘는 문제였음
 * 메모리는 다들 비슷(50,000kb), 속도도 괜찮은데 100ms 정도 빠른 코드 참고
 * 시간상 차이는 아마 결과물 출력에서 ArrayDeque 시간 < ArrayList 시간이라서 그럴 것
 */

public class G3_11779 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder answer = new StringBuilder();
		StringTokenizer st;
		
		int n = Integer.parseInt(br.readLine()); // #city
		int m = Integer.parseInt(br.readLine()); // #bus
		
		List<int[]>[] adjList = new List[n+1];
		for (int i=1; i<=n; i++) {
			adjList[i] = new ArrayList<>();
		}
				
		for (int i=0; i<m; i++) {
			st = new StringTokenizer(br.readLine());
			int s = Integer.parseInt(st.nextToken()); // start
			int e = Integer.parseInt(st.nextToken()); // end
			int c = Integer.parseInt(st.nextToken()); // cost
			
			adjList[s].add(new int[] {e, c});
		}
		
		st = new StringTokenizer(br.readLine());
		int a = Integer.parseInt(st.nextToken());
		int b = Integer.parseInt(st.nextToken());
		
		dijk(n, a, b, adjList);
		
		// trace parents inversely from b to a
		List<Integer> traceback = new ArrayList<>();
		int trace = b;
		while (parents[trace] != 0) {
			traceback.add(trace);
			trace = parents[trace];
		}
		traceback.add(a);
		
		// print
		answer.append(costs[b]).append("\n");
		answer.append(traceback.size()).append("\n");
		for (int i=traceback.size()-1; 0<=i; i--) {
			answer.append(traceback.get(i)).append(" ");
		}
		
		System.out.println(answer);
	}
	
	static int[] costs;
	static int[] parents;
	static void dijk(int n, int a, int b, List<int[]>[] adjList) {
		costs = new int[n+1];
		Arrays.fill(costs, Integer.MAX_VALUE);
		costs[a] = 0;
		
		parents = new int[n+1];
		
		PriorityQueue<int[]> pq = new PriorityQueue<>((x, y) -> (x[1] - y[1]));
		pq.add(new int[] {a, 0});
		while (!pq.isEmpty()) {
			int[] cityInfo = pq.poll();
			int city = cityInfo[0];
			int cost = cityInfo[1];
			
			if (city == b) {
				break;
			}
			
			if (costs[city] < cost) {
				continue;
			}
			
			//confirmed[city] = true;
			for (int[] adj : adjList[city]) {
				int adjCity = adj[0];
				int adjCost = adj[1];
				
				int newCost = costs[city] + adjCost;
				if (newCost < costs[adjCity]) { // 여기가 해결 point. 그리고 confirmed 필요 없음! (이미 최소 보장)
					costs[adjCity] = newCost;
					pq.add(new int[] {adjCity, costs[adjCity]});
					parents[adjCity] = city;
				}
			}
		}
	}
}
