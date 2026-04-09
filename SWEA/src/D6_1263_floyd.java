import java.io.*;
import java.util.*;

// 플로이드 워셜로 풀었는데, 딱봐도 그럴 것 같아서 적용은 했지만 아직 완전 이해는 못한 상태
// 인접행렬에서 0인 곳은 MAX로 / 1인 곳은 1로 초기화한 다음, 3중 for문으로 완화하는 방식

public class D6_1263_floyd {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder answer = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());
		for (int t=1; t<=T; t++) {
			st = new StringTokenizer(br.readLine());
			int N = Integer.parseInt(st.nextToken());
			int[][] adjMat = new int[N][N];
			int[][] dist = new int[N][N];
			for (int r=0; r<N; r++) {
				Arrays.fill(dist[r], Integer.MAX_VALUE);
				for (int c=0; c<N; c++) {
					adjMat[r][c] = Integer.parseInt(st.nextToken());
					if (r == c)
						dist[r][c] = 0;
					else if (adjMat[r][c] == 1)
						dist[r][c] = 1;
				}
			}
			
			for (int k=0; k<N; k++) {
				for (int i=0; i<N; i++) {
					for (int j=0; j<N; j++) {
						int nd;
						if (dist[i][k] != Integer.MAX_VALUE && dist[k][j] != Integer.MAX_VALUE
							&& (nd = dist[i][k] + dist[k][j]) < dist[i][j]) {
							dist[i][j] = nd;
						}
					}
				}
			}
			
			int minCC = Integer.MAX_VALUE;
			for (int r=0; r<N; r++) {
				int curCC = 0;
				for (int c=0; c<N; c++) {
					curCC += dist[r][c];
					
					if (minCC < curCC) break;
				}
				
				minCC = Math.min(minCC, curCC);
			}
			
			answer.append("#").append(t).append(" ").append(minCC).append("\n");
		}
		
		System.out.print(answer);
	}
}
