import java.io.*;
import java.util.*;

// 플로이드 워셜 풀이
// 선정 누나가 벨만포드 연습으로 가져왔는데 플로이드 워셜로 풀림..
// 근데 플로이드 워셜 아직 공부 제대로 안 했음
// 플로이드 워셜보다 벨만포드로 풀면 시간 거의 1/10

public class G3_1865_floyd {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder answer = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());
		for (int tc=1; tc<=T; tc++) {
			st = new StringTokenizer(br.readLine());
			int N = Integer.parseInt(st.nextToken());
			int M = Integer.parseInt(st.nextToken());
			int W = Integer.parseInt(st.nextToken());
			
			int[][] dist = new int[N+1][N+1];
			for (int r=1; r<=N; r++) {
				for (int c=1; c<=N; c++) {
					if (r == c) dist[r][c] = 0;
					else dist[r][c] = Integer.MAX_VALUE;
				}
			}
			
			for (int i=0; i<M; i++) {
				// not directed roads
				st = new StringTokenizer(br.readLine());
				int s = Integer.parseInt(st.nextToken());
				int e = Integer.parseInt(st.nextToken());
				int t = Integer.parseInt(st.nextToken());
				
				dist[s][e] = Math.min(dist[s][e], t);
				dist[e][s] = Math.min(dist[e][s], t);
			}
			
			for (int i=0; i<W; i++) {
				// directed worm holes
				st = new StringTokenizer(br.readLine());
				int s = Integer.parseInt(st.nextToken());
				int e = Integer.parseInt(st.nextToken());
				int t = Integer.parseInt(st.nextToken());
				
				dist[s][e] = Math.min(dist[s][e], -t);
			}
			
			boolean flag = false;
			floyd:
			for (int k=1; k<=N; k++) {
				for (int i=1; i<=N; i++) {
					for (int j=1; j<=N; j++) {
						if (dist[i][k] != Integer.MAX_VALUE && dist[k][j] != Integer.MAX_VALUE) {
							dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
							if (dist[j][i] != Integer.MAX_VALUE && dist[i][j] + dist[j][i] < 0) {
								flag = true;
								break floyd;
							}
						}
					}
				}
			}
			
			answer.append((flag) ? "YES" : "NO").append("\n");
		}
		
		System.out.print(answer);
	}
}
