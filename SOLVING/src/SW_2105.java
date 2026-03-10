import java.io.*;
import java.util.*;

public class SW_2105 {
	static int N;
	static int[][] map;
	static int[] drArr = {-1, -1, 1, 1};
	static int[] dcArr = {-1, 1, -1, 1};
	
	public static void main(String[] args) throws IOException {
		final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		final StringBuilder answer = new StringBuilder();
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		final int T = Integer.parseInt(st.nextToken());
		for (int t=1; t<=T; t++) {
			// input N and map[N][N]
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			map = new int[N][N];
			for (int r=0; r<N; r++) {
				st = new StringTokenizer(br.readLine());
				for (int c=0; c<N; c++) {
					map[r][c] = Integer.parseInt(st.nextToken());
				}
			}
			
			// start dfs from every feasible (r, c)
			maxDessert = -1;
			for (int r=0; r<N; r++) {
				for (int c=0; c<N; c++) {
					if ((r == 0 && c == 0) || (r == 0 && c == N-1)
					 || (r == N-1 && c == 0) || (r == N-1 && c == N-1)) {
						// skip (at the corner)
					} else {
						// NOT at the corner == feasible initial position
						for (int i=0; i<4; i++) {
							final int dr = r + drArr[i];
							final int dc = c + dcArr[i];
							if (!valid(dr, dc)) continue;
							
							if (map[r][c] != map[dr][dc]) {
								// have outDegree >= 1
								curDessert = 1;
								Arrays.fill(path, false);
								//path[map[r][c]] = true;
								path[map[dr][dc]] = true;
								
								dfs(r, c, dr, dc);

								//path[map[r][c]] = false;
								path[map[dr][dc]] = false;
							}
						}
					}
				}
			}
			
			answer.append("#").append(t).append(" ").append(maxDessert).append("\n");
		}
		
		System.out.print(answer);
	}
	
	static int maxDessert, curDessert;
	static boolean[] path = new boolean[101];
	static void dfs(int startR, int startC, int r, int c) {
		if (startR == r && startC == c) {
			// found
			maxDessert = Math.max(maxDessert, curDessert);
			return;
		}
		
		for (int i=0; i<4; i++) {
			final int dr = r + drArr[i];
			final int dc = c + dcArr[i];
			if (!valid(dr, dc) || path[map[dr][dc]]) continue;
			
			path[map[dr][dc]] = true;
			curDessert ++;
			dfs(startR, startC, dr, dc);
			path[map[dr][dc]] = false;
			curDessert --;
		}
	}
	
	static boolean valid(int r, int c) {
		return 0<=r && r<N && 0<=c && c<N;
	}
}

class Coord {
	int r, c;
	
	Coord(int r, int c) {
		this.r = r;
		this.c = c;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Coord) {
			Coord coo = (Coord)o;
			return coo.r == r && coo.c == c;
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(r, c);
	}
}







