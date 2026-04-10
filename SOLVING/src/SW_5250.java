import java.io.*;
import java.util.*;

public class SW_5250 {
	static class Pos {
		int r, c, cost;
		Pos(int r, int c, int cost) {
			this.r = r;
			this.c = c;
			this.cost = cost;
		}
	}
	
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
				st = new StringTokenizer(br.readLine());
				for (int c=0; c<N; c++) {
					map[r][c] = Integer.parseInt(st.nextToken());
					dist[r][c] = Integer.MAX_VALUE;
				}
			}
			
			int[] dr = {-1, 1, 0, 0};
			int[] dc = {0, 0, -1, 1};
			
			dist[0][0] = 0;
			PriorityQueue<Pos> pq = new PriorityQueue<>();
			pq.offer(new Pos(0, 0, 0));
			while (!pq.isEmpty()) {
				Pos p = pq.poll();
				if (p.r == N-1 && p.c == N-1) {
					break;
				}
				
				for (int i=0; i<4; i++) {
					int nr = p.r + dr[i];
					int nc = p.c + dc[i];
					
					if (0<=nr && nr<N && 0<=nc && nc<N) {
						int newCost;
						if ((newCost = dist[p.r][p.c] + 1 + Math.abs(map[nr][nc] - map[p.r][p.c])) < dist[nr][nc]) {
							dist[nr][nc] = newCost;
							pq.offer(new Pos(nr, nc, newCost));
						}
					}
				}
			}
			
			answer.append("#").append(t).append(" ").append(dist[N-1][N-1]).append("\n");
		}
		
		System.out.print(answer);
	}
}
