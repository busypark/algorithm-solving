// https://www.acmicpc.net/problem/16234
// 260305(목) 명예 A형
// 1시간 30분만에 정답..
// 피드백 : right/down 방향의 경계선을 고려했는데 멍청하게 왼쪽/위 방향 탐색을 해야한다는 걸 까먹고 오른쪽/아래쪽 탐색만 해서 30분 정도 헤맸음.. 설계 단계에서 매 단계 새롭게 생각해야..

import java.io.*;
import java.util.*;

public class G4_16234 {
	public static void main(String[] args) throws IOException {
		final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		// input N, L, R, and map[N][N]
		final int N = Integer.parseInt(st.nextToken());
		final int L = Integer.parseInt(st.nextToken());
		final int R = Integer.parseInt(st.nextToken());
		
		final int[][] map = new int[N][N];
		for (int r=0; r<N; r++) {
			st = new StringTokenizer(br.readLine());
			for (int c=0; c<N; c++) {
				map[r][c] = Integer.parseInt(st.nextToken());
			}
		}
		
		// process
		int day = 0;
		for (;;) {
			boolean[][] right = new boolean[N][N];
			boolean[][] down = new boolean[N][N];
			boolean borderExistence = constructBorder(map, N, L, R, right, down);
			if (!borderExistence) break;
			day ++;
			
			boolean[][] visited = new boolean[N][N];
			for (int r=0; r<N; r++) {
				for (int c=0; c<N; c++) {
					if (!visited[r][c]) {
						Set<Coordinate> path = new HashSet<>();
						int sum = dfs(map, N, r, c, right, down, visited, path);
						int newPop = sum / path.size();
						for (Coordinate country : path) {
							map[country.r][country.c] = newPop;
						}
					}
				}
			}
			
			//printMap(N, map);
		}
		
		System.out.println(day);
	}
	
	static void printMap(int N, int[][] map) {
		System.out.println("====================");
		for (int r=0; r<N; r++) {
			for (int c=0; c<N; c++) {
				System.out.print(" "+map[r][c]);
			}
			System.out.println("");
		}
	}
	
	static boolean constructBorder(int[][] map, int N, int L, int R, boolean[][] right, boolean[][] down) {
		boolean exist = false;
		for (int r=0; r<N; r++) {
			for (int c=0; c<N; c++) {
				if (c < N-1) {
					// right
					int diff = Math.abs(map[r][c] - map[r][c+1]);
					right[r][c] = (L <= diff && diff <= R);
					
					if (right[r][c])
						exist = true;
				}
				
				if (r < N-1) {
					//down
					int diff = Math.abs(map[r][c] - map[r+1][c]);
					down[r][c] = (L <= diff && diff <= R);	
					
					if (down[r][c])
						exist = true;			
				}
			}
		}
		
		return exist;
	}
	
	static int dfs(int[][] map, int N, int r, int c, boolean[][] right, boolean[][] down, boolean[][] visited, Set<Coordinate> path) {
		int sum = map[r][c];
		visited[r][c] = true;
		path.add(new Coordinate(r, c));
		
		if (0 < c && right[r][c-1] && !visited[r][c-1]) {
			sum += dfs(map, N, r, c-1, right, down, visited, path);
		}
		
		if (c < N-1 && right[r][c] && !visited[r][c+1]) {
			sum += dfs(map, N, r, c+1, right, down, visited, path);
		}
		
		if (0 < r && down[r-1][c] && !visited[r-1][c]) {
			sum += dfs(map, N, r-1, c, right, down, visited, path);
		}
		
		if (r < N-1 && down[r][c] && !visited[r+1][c]) {
			sum += dfs(map, N, r+1, c, right, down, visited, path);
		}
		
		return sum;
	}
}

class Coordinate {
	int r, c;
	Coordinate(int r, int c) {
		this.r = r;
		this.c = c;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Coordinate) {
			Coordinate coo = (Coordinate) o;
			return r == coo.r && c == coo.c;
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(r, c);
	}
}
