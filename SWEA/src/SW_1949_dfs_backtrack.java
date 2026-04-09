import java.io.*;
import java.util.*;

// 이제 그래도 dfs+백트래킹은 마스터했나?
// 근데 이게 위상정렬 문제? 아닌듯..

public class SW_1949_dfs_backtrack {
	static class Coord {
		int r, c;
		Coord(int r, int c) {
			this.r = r;
			this.c = c;
		}
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder answer = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());
		for (int t=1; t<=T; t++) {
			st = new StringTokenizer(br.readLine());
			int N = Integer.parseInt(st.nextToken());
			int K = Integer.parseInt(st.nextToken());
			
			int[][] map = new int[N][N];
			List<Coord> starts = new ArrayList<>();
			int max = Integer.MIN_VALUE;
			for (int r=0; r<N; r++) {
				st = new StringTokenizer(br.readLine());
				for (int c=0; c<N; c++) {
					map[r][c] = Integer.parseInt(st.nextToken());
					if (max < map[r][c]) {
						max = map[r][c];
						starts.clear();
						starts.add(new Coord(r, c));
					} else if (max == map[r][c]) {
						starts.add(new Coord(r, c));
					}
				}
			}
			
			maxLength = 0;
			visited = new boolean[N][N];
			for (Coord start : starts) {
				curLength = 1;
				for (int r=0; r<N; r++) {
					Arrays.fill(visited[r], false);
				}
				visited[start.r][start.c] = true;
				
				backtrack(N, K, map, start.r, start.c, false);
			}
			
			answer.append("#").append(t).append(" ").append(maxLength).append("\n");
		}
		
		System.out.print(answer);
	}
	
	static int curLength, maxLength;
	static boolean[][] visited;
	static int[] dr = {-1, 1, 0, 0};
	static int[] dc = {0, 0, -1, 1};
	static void backtrack(int N, int K, int[][] map, int r, int c, boolean digged) {
		for (int i=0; i<4; i++) {
			int nr = r + dr[i];
			int nc = c + dc[i];
			
			if (0<=nr && nr<N && 0<=nc && nc<N && !visited[nr][nc]) {
				if (map[nr][nc] < map[r][c]) {
					// no need to dig
					visited[nr][nc] = true;
					curLength ++;
					maxLength = Math.max(maxLength, curLength);
					backtrack(N, K, map, nr, nc, digged);
					visited[nr][nc] = false;
					curLength --;
				} else if (!digged && map[nr][nc] - K < map[r][c]) {
					// try digging
					visited[nr][nc] = true;
					curLength ++;
					maxLength = Math.max(maxLength, curLength);
					int backup = map[nr][nc];
					map[nr][nc] = map[r][c] - 1;
					backtrack(N, K, map, nr, nc, true);
					map[nr][nc] = backup;
					visited[nr][nc] = false;
					curLength --;
				}
			}
		}
	}
}












