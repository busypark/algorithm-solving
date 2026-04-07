import java.io.*;
import java.util.*;

// 옛날에 푼 D4_1249_old 보면 희한하게 푼 것 같은데.. 배우니까 확실히 엄청 쉬워짐

public class D4_1249 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder answer = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());
		for (int t=1; t<=T; t++) {
			int N = Integer.parseInt(br.readLine());
			int[][] map = new int[N][N];
			int[][] dist = new int[N][N];
			for (int r=0; r<N; r++) {
				String row = br.readLine();
				for (int c=0; c<N; c++) {
					map[r][c] = row.charAt(c) - '0';
					dist[r][c] = Integer.MAX_VALUE;
				}
			}
			
			int[] dr = {-1, 1, 0, 0};
			int[] dc = {0, 0, -1, 1};
			
			dist[0][0] = 0;
			PriorityQueue<int[]> pq = new PriorityQueue<>((n1, n2) -> Integer.compare(n1[2], n2[2]));
			pq.offer(new int[] {0, 0, 0});
			while (!pq.isEmpty()) {
				int[] node = pq.poll();
				if (node[0] == N-1 && node[1] == N-1) {
					break;
				}
				
				for (int i=0; i<4; i++) {
					int nr = node[0] + dr[i];
					int nc = node[1] + dc[i];
					
					if (0<=nr && nr<N && 0<=nc && nc<N) {
						int nd = node[2] + map[nr][nc];
						if (nd < dist[nr][nc]) {
							dist[nr][nc] = nd;
							pq.offer(new int[] {nr, nc, nd});
						}
					}
				}
			}
			
			answer.append("#").append(t).append(" ").append(dist[N-1][N-1]).append("\n");
		}
		
		System.out.print(answer);
	}
}
