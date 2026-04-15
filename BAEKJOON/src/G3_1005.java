import java.util.*;
import java.io.*;

// 역순/dfs로 접근해서 15분만에 코드 짰는데 시간초과
// 한 35분 더 고민하면서 위상정렬로 짰는데 자꾸 미묘하게 안 됐지만 큐에 넣을 조건이 안 되어도 업데이트하도록 바꾸니 작동-통과
// 피드백 : 이 정도면 충분히 빠른듯. 입력 스트림 자체를 건드려서 엄청나게 시간/메모리 절약한 코드 있지만, 그런 건 본질적이진 않음
// 참고 : https://www.acmicpc.net/source/103647342

public class G3_1005 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder answer = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());
		for (int t=1; t<=T; t++) {
			st = new StringTokenizer(br.readLine());
			int N = Integer.parseInt(st.nextToken());
			int K = Integer.parseInt(st.nextToken());
			
			int[] buildingTimes = new int[N];
			List<Integer>[] edges = new List[N];
			
			st = new StringTokenizer(br.readLine());
			for (int n=0; n<N; n++) {
				buildingTimes[n] = Integer.parseInt(st.nextToken());
				edges[n] = new ArrayList<>();
			}
			
			int[] parentCount = new int[N];
			for (int k=0; k<K; k++) {
				st = new StringTokenizer(br.readLine());
				int from = Integer.parseInt(st.nextToken()) - 1;
				int to = Integer.parseInt(st.nextToken()) - 1;
				
				edges[from].add(to);
				parentCount[to] ++;
			}
			
			int W = Integer.parseInt(br.readLine()) - 1;
			
			int[] cumulatedTimes = new int[N];
			
			Queue<Integer> q = new LinkedList<>();
			for (int n=0; n<N; n++) {
				if (parentCount[n] == 0) {
					cumulatedTimes[n] = buildingTimes[n];
					q.offer(n);
				}
			}
			
			while (!q.isEmpty()) {
				int now = q.poll();
				if (now == W) {
					break;
				}
							
				for (int next : edges[now]) {
					parentCount[next] --;
					cumulatedTimes[next] = Math.max(cumulatedTimes[next], cumulatedTimes[now] + buildingTimes[next]);
					if (parentCount[next] == 0) {
						q.offer(next);
					}
				}
			}
			
			answer.append(cumulatedTimes[W]).append("\n");
		}
		
		System.out.print(answer);
	}
}
